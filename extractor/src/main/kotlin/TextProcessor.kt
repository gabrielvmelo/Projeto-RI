import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.*

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

    private fun parse(html: String): Document {
        return Jsoup.parse(html)
    }

    fun getMetadata(html: String, domain: String){ //attributes
        val parsedHTML = parse(html)
        //um wrapper para cada site
        when(domain) {
            "rottentomatoes" -> getMetadataRotten(parsedHTML)
            "imdb" -> getMetadataIMDB(parsedHTML)
            "themoviedb" -> getMetadataMovieDB(parsedHTML)
            "trakt" -> getMetadataTrakt(html, domain)
            "tvguide" -> getMetadataTVGuide(html, domain)
            "metacritic" -> getMetadataMetacritic(html, domain)
            "thetvdb" -> getMetadataTVDB(html, domain)
            "tv" -> getMetadataTV(html, domain)
            else -> println("Dominio nao encontrado, nao eh possivel pegar metadata")
        }
    }

    private fun getMetadataRotten(parsedHTML: Document){
        //title
        var title = parsedHTML.getElementsByClass("movie_title")
        attributeValueMap["title"] = title.text().substringBeforeLast(":")
        if (attributeValueMap["title"] == "") {
            title = parsedHTML.getElementsByClass("title no-trailer-title")
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
        println(attributeValueMap)

    }

    fun getMetadataIMDB(parsedHTML: Document){
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

        println(attributeValueMap)
    }

    fun getMetadataMovieDB(parsedHTML: Document){

    }

    fun getMetadataTrakt(html: String, domain: String){

    }

    fun getMetadataTVGuide(html: String, domain: String){

    }

    fun getMetadataMetacritic(html: String, domain: String){

    }

    fun getMetadataTVDB(html: String, domain: String){

    }

    fun getMetadataTV(html: String, domain: String){

    }
}