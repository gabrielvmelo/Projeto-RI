import java.lang.Exception
import java.net.URI
import java.util.*
import kotlin.collections.HashMap
import java.net.URISyntaxException



/**
 * Created by lariciamota.
 */
class Main {
    companion object{
        @JvmStatic
        fun main(args: Array<String>){
            //URLs
            val URLs = arrayOf (
                "https://www.rottentomatoes.com",
                "https://www.imdb.com",
                "https://www.themoviedb.org",
                "https://www.trakt.tv",
                "https://www.tvguide.com",
                "http://www.metacritic.com",
                "https://www.thetvdb.com",
                "http://www.tv.com"
            )

            //Salvar robots.txt
            val mapRobots = HashMap<String, LinkedList<String>>()

            //Passar pelos 8 sites e coletar 1000 links de cada
            for(item in URLs){
                val fronteira = Frontier()
                fronteira.addURL(item)

                val domain = getDomainName(item)
                print("Domain: $domain\n")

                val robots = CheckRobots()
                if(!mapRobots.contains(item)) mapRobots[item] = robots.readTxt(item+"robots.txt")

                var contador = 0

                while (!fronteira.vazia() && contador < 1000){
                    val url = fronteira.remove()
                    print("URL:$url\n")

                    //Analisar se link esta no dominio

                    if(domain != getDomainName(url)){
                        continue
                    }

                    //Analisar robots.txt
                    if(checkRules(mapRobots[item]!!, url)){
                        print("Entrou checkRules")
                        continue
                    }

                    //Analisar se eh uma pagina html e fazer download da pagina se for
                    val rbt = Robot()
                    var html: String
                    try {
                        val ishtml = rbt.downloadPage(url)
                        if (ishtml != null){
                            html = ishtml
                        } else {
                            print("Nao eh html\n")
                            continue
                        }
                    } catch (e: Exception) {
                        print("Nao esta nos tipos permitidos\n")
                        continue
                    }

                    //Analisar se pagina ja foi visitada previamente
                    val dirName = domain.substringBefore(".")
                    val formattedURL = formatURL(url)
                    val repo = PageRepository(dirName, formattedURL)

                    if (repo.checkPage()){
                        continue
                    }

                    //Armazena pagina visitada
                    repo.storePage(html)

                    //Passar pagina pelo parser para pegar links existentes
                    val txtProcessor = TextProcessor()
                    val links = txtProcessor.getLinks(html, item)
                    print("links: " + links + " ")

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

        @Throws(URISyntaxException::class)
        fun getDomainName(url: String): String {
            val uri = URI(url)
            val domain = uri.host
            return if (domain.startsWith("www.")) domain.substring(4) else domain
        }

        fun formatURL(url: String): String {
            return url.replace("/", "")
        }

    }

}