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
            val mapRobots = HashMap<String, LinkedList<String>>()

            //Passar pelos 8 sites e coletar 1000 links de cada
            for(item in URLs){
                val fronteira = Frontier()
                fronteira.addURL(item)

                val robots = CheckRobots()
                if(!mapRobots.contains(item)) mapRobots[item] = robots.readTxt(item+"robots.txt")

                var contador = 0

                while (!fronteira.vazia() && contador < 1000){
                    var url = fronteira.remove()
                    print("URL:" + url + "\n")
                    //Analisar robots.txt
                    if(checkRules(mapRobots[item]!!, url)){
                        continue
                    }
                    //Fazer download da pagina para pegar seu html
                    val rbt = Robot()
                    var html: String
                    try {
                        html = rbt.downloadPage(url)
                    } catch (e: Exception) {
                        continue
                    }

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
                    print("\n" + contador + "\n")
                }

            }

        }

        fun checkRules(rules: LinkedList<String>, url: String): Boolean{
            for(rule in rules){
                if (url.contains(rule)){
                    return true
                }
            }
            return false
        }

    }

}