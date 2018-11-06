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
                "https://www.rottentomatoes.com/tv/maniac",
                "https://www.imdb.com/title/tt0903747/?pf_rd_m=A2FGELUUNOQJNL&pf_rd_p=12230b0e-0e00-43ed-9e59-8d5353703cce&pf_rd_r=HX0Z12EBE1TM84TC778W&pf_rd_s=center-1&pf_rd_t=15506&pf_rd_i=toptv&ref_=chttvtp_tt_5",
                "https://www.imdb.com/title/tt1475582/?pf_rd_m=A2FGELUUNOQJNL&pf_rd_p=12230b0e-0e00-43ed-9e59-8d5353703cce&pf_rd_r=SAG9557X6GS13548BB2R&pf_rd_s=center-1&pf_rd_t=15506&pf_rd_i=toptv&ref_=chttvtp_tt_15"
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