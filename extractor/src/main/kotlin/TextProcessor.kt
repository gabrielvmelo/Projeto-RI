import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * Created by lariciamota.
 */
//Extrair instancias/informacoes de series (metadata)

class TextProcessor {
    private var attributeValueMap = hashMapOf(
        "title" to "",
        "storyline" to "",
        "premiere_date" to "",
        "network" to ""
    )
    private var domain: String? = null

    private fun parse(html: String): Document {
        return Jsoup.parse(html)
    }

    fun getMetadata(html: String, domain: String): HashMap<String, String>?{ //attributes
        val parsedHTML = parse(html)
        this.domain = domain

        //um wrapper para cada site
        when(domain) {
            "rottentomatoes" -> getMetadataRotten(parsedHTML)
            "imdb" -> getMetadataIMDB(parsedHTML)
            "themoviedb" -> getMetadataMovieDB(parsedHTML)
            "trakt" -> getMetadataTrakt(parsedHTML)
            "metacritic" -> getMetadataMetacritic(parsedHTML)
            "thetvdb" -> getMetadataTVDB(parsedHTML)
            "tv" -> getMetadataTV(parsedHTML)
            "tvguide" -> getMetadataTVGuide(parsedHTML)
            else -> {
                println("Dominio nao encontrado, nao eh possivel pegar metadata")
                return null
            }
        }

        return attributeValueMap
    }

    private fun store(){
        //salvando em arquivo
        val repo = MetadataRepository(domain!!)
        val row = "${attributeValueMap["title"]} ## ${attributeValueMap["storyline"]} ## ${attributeValueMap["premiere_date"]} ## ${attributeValueMap["network"]}\n"

        repo.storeDataInFile(row)
    }

    private fun getMetadataRotten(parsedHTML: Document){
        //title
        var title = parsedHTML.getElementsByClass("movie_title")
        attributeValueMap["title"] = title.text().substringBeforeLast(":")
        if (attributeValueMap["title"] == "") {
            title = parsedHTML.getElementsByClass("title no-trailer-title")
            attributeValueMap["title"] = title.text().substringBeforeLast(" (")
        }
        if (attributeValueMap["title"] == "") {
            title = parsedHTML.select("div.seriesHeader h1.title")
            attributeValueMap["title"] = title.text().substringBeforeLast(" (")
        }

        //storyline
        val storyline = parsedHTML.getElementById("movieSynopsis")
        attributeValueMap["storyline"] = storyline.text()

        //premiere date and network
        val value = parsedHTML.getElementsByClass("meta-value")
        var label = parsedHTML.getElementsByClass("meta-label subtle")
        if (value != null && value.size > 0) {
            for (i in 0 until label.size){
                if(label[i].text().contains("Premiere Date")){
                    attributeValueMap["premiere_date"] = value[i].text()
                    continue
                }
                if (label[i].text().contains("Network")){
                    attributeValueMap["network"] = value[i].text()
                    continue
                }
            }
        } else {
            label = parsedHTML.select(".panel-body.content_body table tr td")
            for (i in 0 until label.size){
                if (label[i].text().contains("Premiere Date")){
                    attributeValueMap["premiere_date"] = label[i+1].text()
                    continue
                }
                if (label[i].text().contains("Network")){
                    attributeValueMap["network"] = label[i+1].text()
                    continue
                }
            }
        }

        store()
    }

    private fun getMetadataIMDB(parsedHTML: Document){
        //title
        val title = parsedHTML.select(".title_wrapper h1")
        attributeValueMap["title"] = title.text()

        //storyline
        val storyline = parsedHTML.select(".inline.canwrap p span")
        attributeValueMap["storyline"] = storyline.text()

        //premiere date
        val premiereDate = parsedHTML.select("div.txt-block")
        for (i in premiereDate.indices){
            if (premiereDate[i].getElementsByClass("inline").text().contains("Release Date")){
                attributeValueMap["premiere_date"] = premiereDate[i].text().substringAfter("Release Date: ").substringBefore(" (")
            }
        }

        store()
    }

    private fun getMetadataMovieDB(parsedHTML: Document){
        //title
        val title = parsedHTML.select("div.title span a h2")
        attributeValueMap["title"] = title.text()

        //storyline
        val storyline = parsedHTML.select("div.overview p")
        attributeValueMap["storyline"] = storyline.text()

        //premiere date
        val premiereDate = parsedHTML.select("div.title span span.release_date")
        attributeValueMap["premiere_date"] = premiereDate.text().substringAfter("(").substringBefore(")")

        //network
        val network = parsedHTML.select("ul.networks li a img")
        attributeValueMap["network"] = network.attr("alt").substringAfter("from ").substringBefore("...")

        store()
    }

    private fun getMetadataTrakt(parsedHTML: Document){
        //title
        val title = parsedHTML.select("h2.section strong")
        attributeValueMap["title"] = title.text()

        //storyline
        val storyline = parsedHTML.select("div#overview p")
        attributeValueMap["storyline"] = storyline.text()

        //premiere date and network
        val label = parsedHTML.select("ul.additional-stats li")

        for (i in label.indices){
            val labelName = label[i].select("label").first().text()
            if (labelName.contains("Premiered")){
                attributeValueMap["premiere_date"] = label[i].select("span").text().substringBefore("T")
            }
            if (labelName.contains("Network")){
                attributeValueMap["network"] = label[i].text().substringAfter("Network").substringBefore(" (")
            }
            if (attributeValueMap["network"] == ""){
                if (labelName.contains("Airs")){
                    attributeValueMap["network"] = label[i].text().substringAfter("on ").substringBefore(" (")
                }
            }
        }

        store()
    }

    private fun getMetadataTVGuide(parsedHTML: Document){
        //title
        val title = parsedHTML.select("a.tvobject-masthead-head-link")
        attributeValueMap["title"] = title.text()

        //storyline
        val storyline = parsedHTML.select("p.tvobject-masthead-description-text span span.notruncation")
        attributeValueMap["storyline"] = storyline.text()
        if (attributeValueMap["storyline"] == "") attributeValueMap["storyline"] = parsedHTML.select("p.tvobject-masthead-description-text").text()

        //premiere date
        val label = parsedHTML.select("li.tvobject-overview-about-line")
        for (i in label.indices){
            if(label[i].select("strong").text().contains("Premiered")){
                attributeValueMap["premiere_date"] = label[i].text().removePrefix("Premiered: ")
            }
        }

        store()
    }

    private fun getMetadataMetacritic(parsedHTML: Document){
        //title
        val title = parsedHTML.select("div.product_title a.hover_none span span h1")
        attributeValueMap["title"] = title.text()

        //storyline
        val storyline = parsedHTML.select("li.summary_detail.product_summary span.data span")
        attributeValueMap["storyline"] = storyline.text()

        //premiere date
        val premiereDate = parsedHTML.select("li.summary_detail.release_data span.data")
        attributeValueMap["premiere_date"] = premiereDate.first().text()

        //network
        val network = parsedHTML.select("li.summary_detail.network.publisher span.data a span")
        attributeValueMap["network"] = network.text()

        store()
    }

    private fun getMetadataTVDB(parsedHTML: Document){
        //title
        val title = parsedHTML.select("h1#series_title")
        attributeValueMap["title"] = title.text()

        //storyline
        val storyline = parsedHTML.select("div.change_translation_text p")
        attributeValueMap["storyline"] = storyline.first().text()

        //premiere date and network
        val label = parsedHTML.select("li.list-group-item.clearfix")
        for (i in label.indices){
            if(label[i].select("strong").text().contains("First Aired")){
                attributeValueMap["premiere_date"] = label[i].select("span").text()
            }
            if(label[i].select("strong").text().contains("Network")){
                attributeValueMap["network"] = label[i].select("span").text().substringBefore(" (")
            }
        }

        store()
    }

    private fun getMetadataTV(parsedHTML: Document){
        //title
        val title = parsedHTML.select("div.m.show_head h1")
        attributeValueMap["title"] = title.text()

        //storyline
        val storyline = parsedHTML.select("div.description")
        attributeValueMap["storyline"] = storyline.text()

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
        attributeValueMap["network"] = network

        store()
    }
}