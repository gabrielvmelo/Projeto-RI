import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.*

/**
 * Created by lariciamota.
 */
//Processar o texto: rodar o parser HTML para encontrar os links presentes na pagina

class TextProcessor {
    private var listLinks: LinkedList<String> = LinkedList()

    private fun parse(html: String): Document {
        return Jsoup.parse(html)
    }

    fun getLinks(html: String, domain: String): LinkedList<String> {
        parse(html).select("a").forEachIndexed { _ , element ->
            var link = element.attr("href")
            val regex = ("^(?:[A-Za-z]+:)?//.*".toRegex())
            if(!link.matches(regex)){
                link = domain + link
            }
            if(link[0] == '/' && link[1] == '/') {
                link = "http:$link"
            }

            listLinks.add(link)
        }
        return listLinks
    }

}