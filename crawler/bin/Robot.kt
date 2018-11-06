import org.jsoup.Jsoup

/**
 * Created by lariciamota.
 */
//GET para fazer o download da pagina
class Robot {
    fun downloadPage(url: String): String? {
        val resp = Jsoup.connect(url).timeout(10 * 1000).execute()
        val contentType = resp.contentType()

        if (contentType.contains("application/xhtml+xml") || contentType.contains("text/html")){
            return resp.parse().outerHtml()
        }
        return null
    }
}