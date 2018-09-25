import org.jsoup.Jsoup
import java.lang.Exception
import java.util.*

/**
 * Created by lariciamota.
 */
class Main {
    companion object{
        @JvmStatic
        fun main(args: Array<String>){
            //URLs
            val URLs = arrayOf (
                "https://www.rottentomatoes.com/",
                "https://www.imdb.com/",
                "https://www.themoviedb.org/",
                "https://trakt.tv/",
                "https://www.tvguide.com/",
                "http://www.metacritic.com/",
                "https://www.thetvdb.com/",
                "http://www.tv.com/"
            )
            //Salvar robots.txt
            val robots = HashMap<String, LinkedList<String>>()

            //Passar pelos 8 sites e coletar 1000 links de cada
            for(item in URLs){
                val fronteira = Frontier()
                fronteira.addURL(item)
                print("URL:" + item + " ")

                if(!robots.contains(item)) robots[item] = robotsTxt(item+"robots.txt")

                var contador = 0

                while (!fronteira.vazia() && contador < 1000){
                    var url = fronteira.remove()
                    //Analisar robots.txt
                    if(checkRules(robots[item]!!, url)){
                        continue
                    }
                    //Fazer download da pagina para pegar seu html
                    val rbt = Robot()
                    val html = rbt.downloadPage(url)

                    //Passar pagina pelo parser para pegar links existentes
                    val txtProcessor = TextProcessor()
                    val links = txtProcessor.getLinks(html, item)
                    print("links: " + links + " ")

//                    //Armazena pagina visitada
//                    val repo = PageRepository()
//                    repo.storePage(page)
//
                    //Atualiza fronteira
                    for (link in links){
                        fronteira.addURL(link)
                    }

                    contador += 1
                }

            }

        }

        private fun checkRules(rules: LinkedList<String>, url: String): Boolean{
            for(rule in rules){
                if (url.contains(rule)){
                    return true
                }
            }
            return false
        }

        private fun robotsTxt(url: String): LinkedList<String>{
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


}