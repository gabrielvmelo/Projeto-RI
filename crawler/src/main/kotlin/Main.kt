import java.lang.Exception
import java.lang.IllegalArgumentException
import java.net.URI
import java.util.*
import kotlin.collections.HashMap
import java.net.URISyntaxException


/**
 * Created by lariciamota.
 */
class Main {
    companion object{
        val NUMBER_ATTEMPTS = 3
        val NUMBER_COUNT = 100
        val STRATEGY_CODE = 1 // 1 eh baseline, 2 eh heuristica com palavras positivas e 3 eh heuristica com palavras positivas e negativas

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

//            val tokenizerP = TokenizerPositive()
//            print("1 Relevante: ${tokenizerP.scoreURL("https://www.rottentomatoes.com/tv/the_sinner/s02")}\n")
//            print("2 Relevante: ${tokenizerP.scoreURL("https://www.rottentomatoes.com/tv/game_of_thrones?")}\n")
//            print("3 Relevante: ${tokenizerP.scoreURL("https://www.themoviedb.org/tv/1402-the-walking-dead")}\n")
//            print("4 Relevante: ${tokenizerP.scoreURL("https://www.imdb.com/title/tt0903747/?pf_rd_m=A2FGELUUNOQJNL&pf_rd_p=12230b0e-0e00-43ed-9e59-8d5353703cce&pf_rd_r=X9XS2RYN19FPDXH72GTN&pf_rd_s=center-1&pf_rd_t=15506&pf_rd_i=toptv&ref_=chttvtp_tt_5")}\n")
//            print("5 Relevante: ${tokenizerP.scoreURL("https://trakt.tv/shows/the-big-bang-theory")}\n")
//            print("6 Relevante: ${tokenizerP.scoreURL("https://www.metacritic.com/tv/the-walking-dead/season-9")}\n")
//            print("7 Relevante: ${tokenizerP.scoreURL("https://www.thetvdb.com/series/stranger-things")}\n")
//            print("8 Relevante: ${tokenizerP.scoreURL("http://www.tv.com/shows/the-dr-oz-show/")}\n")
//            print("9 Relevante: ${tokenizerP.scoreURL("http://www.tv.com/shows/bleach/")}\n")
//            print("1 Nao Relevante Movie: ${tokenizerP.scoreURL("https://www.rottentomatoes.com/m/a_simple_favor")}\n")
//            print("2 Nao Relevante Movie: ${tokenizerP.scoreURL("https://www.themoviedb.org/movie/335983-venom?language=en-US")}\n")
//            print("3 Nao Relevante Movie: ${tokenizerP.scoreURL("https://www.imdb.com/title/tt1270797/?ref_=inth_ov_tt")}\n")
//            print("4 Nao Relevante Game: ${tokenizerP.scoreURL("https://www.metacritic.com/game/playstation-4/assassins-creed-odyssey")}\n")
//            print("5 Nao Relevante Movie: ${tokenizerP.scoreURL("https://www.metacritic.com/movie/smallfoot")}\n")
//            print("1 Nao Relevante: ${tokenizerP.scoreURL("https://www.rottentomatoes.com/")}\n")
//            print("2 Nao Relevante: ${tokenizerP.scoreURL("https://www.imdb.com/registration/signin?u=https%3A%2F%2Fwww.imdb.com%2Flist%2Fwatchlist%3Fref_%3Dnv_wl_all_0")}\n")
//            print("3 Nao Relevante: ${tokenizerP.scoreURL("https://trakt.tv/shows/trending")}\n")
//            print("4 Nao Relevante: ${tokenizerP.scoreURL("https://www.themoviedb.org/discover/movie")}\n")
//            print("5 Nao Relevante: ${tokenizerP.scoreURL("https://trakt.tv/calendars/shows")}\n")
//            print("6 Nao Relevante: ${tokenizerP.scoreURL("https://www.tvguide.com/news/")}\n")
//            print("7 Nao Relevante: ${tokenizerP.scoreURL("https://www.thetvdb.com/series/supergirl/seasons/1")}\n\n")
//            val tokenizer = Tokenizer()
//            print("1 Relevante: ${tokenizer.scoreURL("https://www.rottentomatoes.com/tv/the_sinner/s02")}\n")
//            print("2 Relevante: ${tokenizer.scoreURL("https://www.rottentomatoes.com/tv/game_of_thrones?")}\n")
//            print("3 Relevante: ${tokenizer.scoreURL("https://www.themoviedb.org/tv/1402-the-walking-dead")}\n")
//            print("4 Relevante: ${tokenizer.scoreURL("https://www.imdb.com/title/tt0903747/?pf_rd_m=A2FGELUUNOQJNL&pf_rd_p=12230b0e-0e00-43ed-9e59-8d5353703cce&pf_rd_r=X9XS2RYN19FPDXH72GTN&pf_rd_s=center-1&pf_rd_t=15506&pf_rd_i=toptv&ref_=chttvtp_tt_5")}\n")
//            print("5 Relevante: ${tokenizer.scoreURL("https://trakt.tv/shows/the-big-bang-theory")}\n")
//            print("6 Relevante: ${tokenizer.scoreURL("https://www.metacritic.com/tv/the-walking-dead/season-9")}\n")
//            print("7 Relevante: ${tokenizer.scoreURL("https://www.thetvdb.com/series/stranger-things")}\n")
//            print("8 Relevante: ${tokenizer.scoreURL("http://www.tv.com/shows/the-dr-oz-show/")}\n")
//            print("9 Relevante: ${tokenizer.scoreURL("http://www.tv.com/shows/bleach/")}\n")
//            print("1 Nao Relevante Movie: ${tokenizer.scoreURL("https://www.rottentomatoes.com/m/a_simple_favor")}\n")
//            print("2 Nao Relevante Movie: ${tokenizer.scoreURL("https://www.themoviedb.org/movie/335983-venom?language=en-US")}\n")
//            print("3 Nao Relevante Movie: ${tokenizer.scoreURL("https://www.imdb.com/title/tt1270797/?ref_=inth_ov_tt")}\n")
//            print("4 Nao Relevante Game: ${tokenizer.scoreURL("https://www.metacritic.com/game/playstation-4/assassins-creed-odyssey")}\n")
//            print("5 Nao Relevante Movie: ${tokenizer.scoreURL("https://www.metacritic.com/movie/smallfoot")}\n")
//            print("1 Nao Relevante: ${tokenizer.scoreURL("https://www.rottentomatoes.com/")}\n")
//            print("2 Nao Relevante: ${tokenizer.scoreURL("https://www.imdb.com/registration/signin?u=https%3A%2F%2Fwww.imdb.com%2Flist%2Fwatchlist%3Fref_%3Dnv_wl_all_0")}\n")
//            print("3 Nao Relevante: ${tokenizer.scoreURL("https://trakt.tv/shows/trending")}\n")
//            print("4 Nao Relevante: ${tokenizer.scoreURL("https://www.themoviedb.org/discover/movie")}\n")
//            print("5 Nao Relevante: ${tokenizer.scoreURL("https://trakt.tv/calendars/shows")}\n")
//            print("6 Nao Relevante: ${tokenizer.scoreURL("https://www.tvguide.com/news/")}\n")
//            print("7 Nao Relevante: ${tokenizer.scoreURL("https://www.thetvdb.com/series/supergirl/seasons/1")}\n")

//            val pageClass = PageClassifier()
//            val rbt = Robot()
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
            val pageScoreMap = hashMapOf<String, Int>()

            //Passar pelos 8 sites e coletar 1000 links de cada
            for(item in URLs){
                val frontier = Frontier()
                frontier.addURL(item)

                val domain = getDomainName(item)
                print("Domain: $domain\n")

                val robots = CheckRobots()
                if(!mapRobots.contains(item)) mapRobots[item] = robots.readTxt(item+"robots.txt")

                var count = 0
                var relevant = 0

                while (!frontier.vazia() && count < NUMBER_COUNT){
                    val url = frontier.remove()
//                    print("URL:$url\n")

                    //Analisar se link esta no dominio

                    if(domain == null || domain != getDomainName(url)){
                        continue
                    }

                    //Analisar robots.txt
                    if(checkRules(mapRobots[item]!!, url)){
                        print("Entrou checkRules")
                        continue
                    }

                    //Analisar se eh uma pagina html e fazer download da pagina se for
                    val rbt = Robot()
                    var html: String? = null

                    var success = false
                    var countFail = 0
                    while (!success && countFail < NUMBER_ATTEMPTS){
                        try {
                            html = rbt.downloadPage(url)
                            success = true
                        } catch (e: Exception) {
                            println(e.message + " $url")
                            countFail += 1
                        }
                    }

                    if (!success || html == null) continue

                    //Analisar se pagina ja foi visitada previamente
                    val dirName = domain.substringBefore(".")
                    var formattedURL =  formatURL(url)
                    if(formattedURL.length >= 255){
                        formattedURL =  formatURL(url).substring(0, 255)
                    }
                    val repo = PageRepository(dirName, formattedURL)

                    if (repo.checkPage()){
                        continue
                    }

                    //Armazena pagina visitada
                    repo.storePage(html)

                    //Calcula score da pagina
                    val pageClass = PageClassifier().scorePage(html)
                    if (pageClass > 250){ //pagina considerada relevante
                        relevant += 1
//                        println("$relevant $url $pageClass")
                    }
//                    else {
//                        println("NAO RELEVANTE $url $pageClass")
//                    }

                    //Passar pagina pelo parser para pegar links existentes
                    val txtProcessor = TextProcessor()
                    val links = txtProcessor.getLinks(html, item)

                    //Atualiza fronteira de acordo com estrategia escolhida
                    when(STRATEGY_CODE){
                        1 -> { //baseline
                            for (link in links){
                                frontier.addURL(link)
                            }
                        }
                        2 -> { //heuristica com palavras positivas
                            val tokenizerPositive = TokenizerPositive()
                            for (link in links){
                                val value = tokenizerPositive.scoreURL(link)
                                if(value >= 10){
                                    frontier.addURL(link)
                                }
                            }
                        }
                        3 -> { //heuristica com palavras positivas e negativas
                            val tokenizer = Tokenizer()
                            for (link in links){
                                val value = tokenizer.scoreURL(link)
                                if(value >= 10){
                                    frontier.addURL(link)
                                }
                            }
                        }
                    }

                    count += 1
//                    println("\n" + count + "\n")
                }
                println("Dominio: $item. Quantidade de links visitados: $count. Quantidade de links relevantes: $relevant")
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

        @Throws(URISyntaxException::class)
        fun getDomainName(url: String): String? {
            var uri: URI? = null
            var domain: String? = null
            try{
//                println("URL: $url")
                uri = URI(url)
//                println("uri: $uri")
                domain = uri.host
            } catch (e: URISyntaxException){
                println(e.message)
                return null
            }
//            println("Domain fora: $domain")
            if(domain == null){
                println("WARN - invalid url detected: $url")
                return null
            }
            if (domain.startsWith("www.")) {
//                println("Domain startswith: ${domain.substring(4)}\n")
                return domain.substring(4)
            } else {
//                println("Domain sem: $domain\n")
               return domain
            }
        }

        private fun formatURL(url: String): String {
            return url.replace("/", "")
        }

    }

}