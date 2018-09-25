import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.*

/**
 * Created by lariciamota.
 */
//Processar o texto: rodar o parser HTML para encontrar os links presentes na pagina
class TextProcessor {
    var listaLinks: LinkedList<String> = LinkedList()

    private fun parse(html: String): Document {
        return Jsoup.parse(html)
    }

    fun getLinks(html: String): LinkedList<String> {
        parse(html).select("a").forEachIndexed { _ , element ->
            val link = element.attr("href")
            listaLinks.add(link)
        }
        return listaLinks
    }
}