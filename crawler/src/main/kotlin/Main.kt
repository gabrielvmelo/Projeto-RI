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
                "https://www.metacritic.com",
                "https://www.thetvdb.com",
                "http://www.tv.com"
            )
            val pageClass = PageClassifier()
            val rbt = Robot()
//            print("1 Relevante: ${pageClass.scorePage(rbt.downloadPage("https://www.rottentomatoes.com/tv/the_sinner/s02")!!)}\n")
//            print("2 Relevante: ${pageClass.scorePage(rbt.downloadPage("https://www.rottentomatoes.com/tv/game_of_thrones?")!!)}\n")
//            print("3 Relevante: ${pageClass.scorePage(rbt.downloadPage("https://www.themoviedb.org/tv/1402-the-walking-dead")!!)}\n")
//            print("4 Relevante: ${pageClass.scorePage(rbt.downloadPage("https://www.imdb.com/title/tt0903747/?pf_rd_m=A2FGELUUNOQJNL&pf_rd_p=12230b0e-0e00-43ed-9e59-8d5353703cce&pf_rd_r=X9XS2RYN19FPDXH72GTN&pf_rd_s=center-1&pf_rd_t=15506&pf_rd_i=toptv&ref_=chttvtp_tt_5")!!)}\n")
//            print("5 Relevante: ${pageClass.scorePage(rbt.downloadPage("https://trakt.tv/shows/the-big-bang-theory")!!)}\n")
//            print("6 Relevante: ${pageClass.scorePage(rbt.downloadPage("https://www.metacritic.com/tv/the-walking-dead/season-9")!!)}\n")
//            print("7 Relevante: ${pageClass.scorePage(rbt.downloadPage("https://www.thetvdb.com/series/stranger-things")!!)}\n")
//            print("8 Relevante: ${pageClass.scorePage(rbt.downloadPage("http://www.tv.com/shows/the-dr-oz-show/")!!)}\n")
//            print("9 Relevante: ${pageClass.scorePage(rbt.downloadPage("http://www.tv.com/shows/bleach/")!!)}\n")
//            print("1 Nao Relevante Movie: ${pageClass.scorePage(rbt.downloadPage("https://www.rottentomatoes.com/m/a_simple_favor")!!)}\n")
//            print("2 Nao Relevante Movie: ${pageClass.scorePage(rbt.downloadPage("https://www.themoviedb.org/movie/335983-venom?language=en-US")!!)}\n")
//            print("3 Nao Relevante Movie: ${pageClass.scorePage(rbt.downloadPage("https://www.imdb.com/title/tt1270797/?ref_=inth_ov_tt")!!)}\n")
//            print("4 Nao Relevante Game: ${pageClass.scorePage(rbt.downloadPage("https://www.metacritic.com/game/playstation-4/assassins-creed-odyssey")!!)}\n")
//            print("5 Nao Relevante Movie: ${pageClass.scorePage(rbt.downloadPage("https://www.metacritic.com/movie/smallfoot")!!)}\n")
//            print("1 Nao Relevante: ${pageClass.scorePage(rbt.downloadPage("https://www.rottentomatoes.com/")!!)}\n")
//            print("2 Nao Relevante: ${pageClass.scorePage(rbt.downloadPage("https://www.imdb.com/registration/signin?u=https%3A%2F%2Fwww.imdb.com%2Flist%2Fwatchlist%3Fref_%3Dnv_wl_all_0")!!)}\n")
//            print("3 Nao Relevante: ${pageClass.scorePage(rbt.downloadPage("https://trakt.tv/shows/trending")!!)}\n")
//            print("4 Nao Relevante: ${pageClass.scorePage(rbt.downloadPage("https://www.themoviedb.org/discover/movie")!!)}\n")
//            print("5 Nao Relevante: ${pageClass.scorePage(rbt.downloadPage("https://trakt.tv/calendars/shows")!!)}\n")
//            print("6 Nao Relevante: ${pageClass.scorePage(rbt.downloadPage("https://www.tvguide.com/news/")!!)}\n")
//            print("7 Nao Relevante: ${pageClass.scorePage(rbt.downloadPage("https://www.thetvdb.com/series/supergirl/seasons/1")!!)}\n")


            //Salvar robots.txt
            val mapRobots = HashMap<String, LinkedList<String>>()

            //Salvar links e seus scores
            val urlScoreMap = hashMapOf<String, Int>()

            //Passar pelos 8 sites e coletar 1000 links de cada
            for(item in URLs){
                val fronteira = Frontier()
                fronteira.addURL(item)

                val domain = getDomainName(item)
                print("Domain: $domain\n")

                val robots = CheckRobots()
                if(!mapRobots.contains(item)) mapRobots[item] = robots.readTxt(item+"robots.txt")

                var contador = 0

                while (!fronteira.vazia() && contador < 25){
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
                            println("Nao eh html\n")
                            continue
                        }
                    } catch (e: Exception) {
                        println(e.message)
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

                    //Calcula score e salva no map
                    val pageClassifier = PageClassifier()
                    val score = pageClassifier.scorePage(html)
                    urlScoreMap[url] = score

                    //Passar pagina pelo parser para pegar links existentes
                    val txtProcessor = TextProcessor()
                    val links = txtProcessor.getLinks(html, item)
                    println("links: " + links + " ")

                    //Atualiza fronteira
                    for (link in links){
                        fronteira.addURL(link)
                    }
                    contador += 1
                    println("\n" + contador + "\n")
                }

            }
//            println(urlScoreMap)

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