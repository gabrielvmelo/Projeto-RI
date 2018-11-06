import java.lang.Exception
import java.net.URI
import java.util.*
import kotlin.collections.HashMap
import java.net.URISyntaxException
import java.util.concurrent.Executors


/**
 * Created by lariciamota.
 */
class Main {
    companion object{
        val NUMBER_ATTEMPTS = 3
        val NUMBER_COUNT = 1000
        val STRATEGY_CODE = 3 // 1 eh baseline, 2 eh heuristica com palavras positivas e 3 eh heuristica com palavras positivas e negativas

        private fun crawler(URL: String){

            //Salvar robots.txt
            val mapRobots = HashMap<String, LinkedList<String>>()

            //Passar pelos 8 sites e coletar 1000 links de cada
           // for(URL in URLs){
                val frontier = Frontier()
                frontier.addURL(URL)

                val domain = getDomainName(URL)
                print("Domain: $domain\n")

                val robots = CheckRobots()
                if(!mapRobots.contains(URL)) mapRobots[URL] = robots.readTxt(URL+"robots.txt")

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
                    if(checkRules(mapRobots[URL]!!, url)){
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
                    }

                    //Passar pagina pelo parser para pegar links existentes
                    val txtProcessor = TextProcessor()
                    val links = txtProcessor.getLinks(html, URL)

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
                println("Dominio: $URL. Quantidade de links visitados: $count. Quantidade de links relevantes: $relevant")

        }

        @JvmStatic
        fun main(args: Array<String>){
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
            val executor = Executors.newFixedThreadPool(8)
            for (i in 0..7) {
                val worker = Runnable { crawler(URLs[i]) }
                executor.execute(worker)
            }
            executor.shutdown()
            while (!executor.isTerminated) {
            }
            println("Finished all threads")
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
                uri = URI(url)
                domain = uri.host
            } catch (e: URISyntaxException){
                println(e.message)
                return null
            }
            if(domain == null){
                println("WARN - invalid url detected: $url")
                return null
            }
            if (domain.startsWith("www.")) {
                return domain.substring(4)
            } else {
               return domain
            }
        }

        private fun formatURL(url: String): String {
            return url.replace("/", "")
        }

    }

}