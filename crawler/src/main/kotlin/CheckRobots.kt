import org.jsoup.Jsoup
import java.lang.Exception
import java.util.*

class CheckRobots {

    fun readTxt(url: String): LinkedList<String> {
        val rbt = Robot()
        try {
            val txt = Jsoup.parse(rbt.downloadPage(url)).select("body").toString().split(" ".toRegex())
            var lista = LinkedList<String>()

            for(i in txt.indices) {
                if(txt[i] == "Disallow:" && txt[i+1][0] == '/') {
                    lista.add(txt[i+1])
                }
            }

            return lista
        } catch (e: Exception) {
            return LinkedList()
        }

    }
}