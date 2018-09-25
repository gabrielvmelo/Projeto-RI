import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.util.*
import java.awt.SystemColor.text



/**
 * Created by lariciamota.
 */
//Processar o texto: rodar o parser HTML para encontrar os links presentes na pagina
class TextProcessor {
    var listaLinks: LinkedList<String> = LinkedList()

    fun parse(html: String){
        val doc = Jsoup.parse(html)
        val links = doc.select("a").forEachIndexed { index, element ->
            val link = element.attr("href")
            listaLinks.add(link)
        }
    }

    fun getLinks(html: String): LinkedList<String> {
        parse(html)
        return listaLinks
    }
}