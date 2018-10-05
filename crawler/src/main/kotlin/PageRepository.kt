import java.io.File

/**
 * Created by lariciamota.
 */
//Armazenar as paginas visitadas
class PageRepository {
    fun storePage(html: String, domain: String, position: Int){
        val dirName = domain.substringBefore(".")
        val fileName = "$dirName$position"
        val path = "src/repository/$dirName/$fileName.html"
        val myFile = File(path)

        myFile.writeText(html)
    }
}