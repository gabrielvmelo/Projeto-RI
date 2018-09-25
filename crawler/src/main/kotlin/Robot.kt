import org.jsoup.Jsoup

/**
 * Created by lariciamota.
 */
//GET para fazer o download da pagina
class Robot {
    fun downloadPage(url: String): String {
        val page = Jsoup.connect(url).get()
        return page.outerHtml()
    }
}