import org.jsoup.Jsoup

/**
 * Created by lariciamota.
 */
//GET para fazer o download da pagina
class Robot {
    fun parse(url: String){
        Jsoup.connect(url).get().run {
            //2. Parses and scrapes the HTML response
            select("div.rc").forEachIndexed { index, element ->
                val titleAnchor = element.select("h3 a")
                val title = titleAnchor.text()
                val url = titleAnchor.attr("href")
                //3. Dumping Search Index, Title and URL on the stdout.
                println("$index. $title ($url)")
            }
        }
    }
}