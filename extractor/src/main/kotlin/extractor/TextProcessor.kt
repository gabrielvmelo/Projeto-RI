package extractor

import Main.Companion.ATTR1
import Main.Companion.ATTR2
import Main.Companion.ATTR3
import Main.Companion.ATTR4
import Main.Companion.DOM1
import Main.Companion.DOM2
import Main.Companion.DOM3
import Main.Companion.DOM4
import Main.Companion.DOM5
import Main.Companion.DOM6
import Main.Companion.DOM7
import Main.Companion.DOM8
import RepositoryManager
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * Created by lariciamota.
 */
//Extrair instancias/informacoes de series (metadata)

class TextProcessor {
    private var attributeValueMap = hashMapOf(
        ATTR1 to "",
        ATTR2 to "",
        ATTR3 to "",
        ATTR4 to ""
    )
    private var domain: String? = null
    private var url: String? = null

    private fun parse(html: String): Document {
        return Jsoup.parse(html)
    }

    fun getMetadata(url: String, html: String, domain: String): HashMap<String, String>?{ //attributes
        val parsedHTML = parse(html)
        this.domain = domain
        this.url = url

        //um wrapper para cada site
        when(domain) {
            DOM1 -> getMetadataIMDB(parsedHTML)
            DOM2 -> getMetadataMetacritic(parsedHTML)
            DOM3 -> getMetadataRotten(parsedHTML)
            DOM4 -> getMetadataMovieDB(parsedHTML)
            DOM5 -> getMetadataTVDB(parsedHTML)
            DOM6 -> getMetadataTrakt(parsedHTML)
            DOM7 -> getMetadataTV(parsedHTML)
            DOM8 -> getMetadataTVGuide(parsedHTML)
            else -> {
                println("Dominio nao encontrado, nao eh possivel pegar metadata")
                return null
            }
        }

        return attributeValueMap
    }

    private fun store(){
        //salvando em arquivo
        val repo = RepositoryManager()
        val row = "$url ## ${attributeValueMap[ATTR1]} ## ${attributeValueMap[ATTR2]} ## ${attributeValueMap[ATTR3]} ## ${attributeValueMap[ATTR4]}\n"

        println(url)
        repo.storeDataInFile(row, "attribute-value/${domain!!}")
    }

    private fun getMetadataRotten(parsedHTML: Document){
        //title
        var title = parsedHTML.getElementsByClass("movie_title")
        attributeValueMap[ATTR1] = title.text().substringBeforeLast(":")
        if (attributeValueMap[ATTR1] == "") {
            title = parsedHTML.getElementsByClass("title no-trailer-title")
            attributeValueMap[ATTR1] = title.text().substringBeforeLast(" (")
        }
        if (attributeValueMap[ATTR1] == "") {
            title = parsedHTML.select("div.seriesHeader h1.title")
            attributeValueMap[ATTR1] = title.text().substringBeforeLast(" (")
        }

        //storyline
        val storyline = parsedHTML.getElementById("movieSynopsis")
        attributeValueMap[ATTR2] = storyline.text()

        //premiere date and network
        val value = parsedHTML.getElementsByClass("meta-value")
        var label = parsedHTML.getElementsByClass("meta-label subtle")
        if (value != null && value.size > 0) {
            for (i in 0 until label.size){
                if(label[i].text().contains("Premiere Date")){
                    attributeValueMap[ATTR3] = value[i].text()
                    continue
                }
                if (label[i].text().contains("Network")){
                    attributeValueMap[ATTR4] = value[i].text()
                    continue
                }
            }
        } else {
            label = parsedHTML.select(".panel-body.content_body table tr td")
            for (i in 0 until label.size){
                if (label[i].text().contains("Premiere Date")){
                    attributeValueMap[ATTR3] = label[i+1].text()
                    continue
                }
                if (label[i].text().contains("Network")){
                    attributeValueMap[ATTR4] = label[i+1].text()
                    continue
                }
            }
        }

        store()
    }

    private fun getMetadataIMDB(parsedHTML: Document){
        //title
        val title = parsedHTML.select(".title_wrapper h1")
        attributeValueMap[ATTR1] = title.text()

        //storyline
        val storyline = parsedHTML.select(".inline.canwrap p span")
        attributeValueMap[ATTR2] = storyline.text()

        //premiere date
        val premiereDate = parsedHTML.select("div.txt-block")
        for (i in premiereDate.indices){
            if (premiereDate[i].getElementsByClass("inline").text().contains("Release Date")){
                attributeValueMap[ATTR3] = premiereDate[i].text().substringAfter("Release Date: ").substringBefore(" (")
            }
        }

        store()
    }

    private fun getMetadataMovieDB(parsedHTML: Document){
        //title
        val title = parsedHTML.select("div.title span a h2")
        attributeValueMap[ATTR1] = title.text()

        //storyline
        val storyline = parsedHTML.select("div.overview p")
        attributeValueMap[ATTR2] = storyline.text()

        //premiere date
        val premiereDate = parsedHTML.select("div.title span span.release_date")
        attributeValueMap[ATTR3] = premiereDate.text().substringAfter("(").substringBefore(")")

        //network
        val network = parsedHTML.select("ul.networks li a img")
        attributeValueMap[ATTR4] = network.attr("alt").substringAfter("from ").substringBefore("...")

        store()
    }

    private fun getMetadataTrakt(parsedHTML: Document){
        //title
        val title = parsedHTML.select("h2.section strong")
        attributeValueMap[ATTR1] = title.text()

        //storyline
        val storyline = parsedHTML.select("div#overview p")
        attributeValueMap[ATTR2] = storyline.text()

        //premiere date and network
        val label = parsedHTML.select("ul.additional-stats li")

        for (i in label.indices){
            val labelName = label[i].select("label").first().text()
            if (labelName.contains("Premiered")){
                attributeValueMap[ATTR3] = label[i].select("span").text().substringBefore("T")
            }
            if (labelName.contains("Network")){
                attributeValueMap[ATTR4] = label[i].text().substringAfter("Network").substringBefore(" (")
            }
            if (attributeValueMap[ATTR4] == ""){
                if (labelName.contains("Airs")){
                    attributeValueMap[ATTR4] = label[i].text().substringAfter("on ").substringBefore(" (")
                }
            }
        }

        store()
    }

    private fun getMetadataTVGuide(parsedHTML: Document){
        //title
        val title = parsedHTML.select("a.tvobject-masthead-head-link")
        attributeValueMap[ATTR1] = title.text()

        //storyline
        val storyline = parsedHTML.select("p.tvobject-masthead-description-text span span.notruncation")
        attributeValueMap[ATTR2] = storyline.text()
        if (attributeValueMap[ATTR2] == "") attributeValueMap[ATTR2] = parsedHTML.select("p.tvobject-masthead-description-text").text()

        //premiere date
        val label = parsedHTML.select("li.tvobject-overview-about-line")
        for (i in label.indices){
            if(label[i].select("strong").text().contains("Premiered")){
                attributeValueMap[ATTR3] = label[i].text().removePrefix("Premiered: ")
            }
        }

        store()
    }

    private fun getMetadataMetacritic(parsedHTML: Document){
        //title
        val title = parsedHTML.select("div.product_title a.hover_none span span h1")
        attributeValueMap[ATTR1] = title.text()

        //storyline
        val storyline = parsedHTML.select("li.summary_detail.product_summary span.data span")
        attributeValueMap[ATTR2] = storyline.text()

        //premiere date
        val premiereDate = parsedHTML.select("li.summary_detail.release_data span.data")
        attributeValueMap[ATTR3] = premiereDate.first().text()

        //network
        val network = parsedHTML.select("li.summary_detail.network.publisher span.data a span")
        attributeValueMap[ATTR4] = network.text()

        store()
    }

    private fun getMetadataTVDB(parsedHTML: Document){
        //title
        val title = parsedHTML.select("h1#series_title")
        attributeValueMap[ATTR1] = title.text()

        //storyline
        val storyline = parsedHTML.select("div.change_translation_text p")
        attributeValueMap[ATTR2] = storyline.first().text()

        //premiere date and network
        val label = parsedHTML.select("li.list-group-item.clearfix")
        for (i in label.indices){
            if(label[i].select("strong").text().contains("First Aired")){
                attributeValueMap[ATTR3] = label[i].select("span").text()
            }
            if(label[i].select("strong").text().contains("Network")){
                attributeValueMap[ATTR4] = label[i].select("span").text().substringBefore(" (")
            }
        }

        store()
    }

    private fun getMetadataTV(parsedHTML: Document){
        //title
        val title = parsedHTML.select("div.m.show_head h1")
        attributeValueMap[ATTR1] = title.text()

        //storyline
        val storyline = parsedHTML.select("div.description")
        attributeValueMap[ATTR2] = storyline.text()

        //network
        var network = parsedHTML.select("div.tagline").text()
        if (network.contains("on")){
            network = network.substringAfter("on ").substringBefore(" (")
            if(network.contains("Premiered")){
                network = network.substringBefore(" Premiered").substringBefore(" (")
            }
        }
        if(network.contains("Premiered")){
            network = network.substringBefore(" Premiered").substringBefore(" (")
        }
        attributeValueMap[ATTR4] = network

        store()
    }
}