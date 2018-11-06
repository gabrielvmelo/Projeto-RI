import org.jsoup.Jsoup
import java.net.URI
import java.net.URISyntaxException
import java.util.concurrent.Executors

class Main {
    companion object {

        private fun extractor(URL: String){
            val domain = getDomainName(URL)?.substringBefore(".")
            val txtProcessor = TextProcessor()
            val html = downloadPage(URL)
            if (domain != null && html != null) txtProcessor.getMetadata(html, domain)

        }

        @JvmStatic
        fun main(args: Array<String>){

            //lista de URLs rotuladas positivamente
            val URLs = arrayOf (
                "https://www.rottentomatoes.com/tv/maniac/s01",
                "https://www.rottentomatoes.com/tv/maniac"
            )
            val executor = Executors.newFixedThreadPool(URLs.size)
            for (i in 0 until URLs.size) {
                val worker = Runnable { extractor(URLs[i]) }
                executor.execute(worker)
            }
            executor.shutdown()
            while (!executor.isTerminated) {
            }
            println("Finished all threads")
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

        private fun downloadPage(url: String): String? {
            val resp = Jsoup.connect(url).timeout(10 * 1000).execute()
            val contentType = resp.contentType()

            if (contentType.contains("application/xhtml+xml") || contentType.contains("text/html")){
                return resp.parse().outerHtml()
            }
            return null
        }
    }
}