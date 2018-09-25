import org.jsoup.Jsoup
import java.util.*

/**
 * Created by lariciamota.
 */
//Processar o texto: rodar o parser HTML para encontrar os links presentes na pagina
class TextProcessor {
    var listaLinks: LinkedList<String> = LinkedList()

    fun parse(url: String){
        Jsoup.connect(url).get().run {
            select("div.rc").forEachIndexed { index, element ->
                val linkAnchor = element.select("a")
                val link = linkAnchor.attr("href")
                listaLinks.add(link)
            }
        }
    }

    fun getLinks(url: String): LinkedList<String> {
        parse(url)
        return listaLinks
    }
}