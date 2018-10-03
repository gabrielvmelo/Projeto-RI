import sun.rmi.runtime.Log
import java.io.File
import java.util.*

/**
 * Created by lariciamota.
 */
//Armazenar as paginas visitadas
class PageRepository {
    fun storePage(html: String, domain: String, position: Int){
        val dirName = domain.substringAfter(".").substringBefore(".")
        val fileName = "$dirName$position"
        val path = "src/repository/$dirName/$fileName.html"
        val myFile = File(path)

        myFile.writeText(html)
    }
}