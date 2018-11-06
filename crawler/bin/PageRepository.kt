import java.io.File

/**
 * Created by lariciamota.
 */
//Armazenar as paginas visitadas
class PageRepository(dirName: String, fileName: String) {
    val path = "src/repository/$dirName/$fileName"
    val myFile = File(path)

    fun storePage(html: String){
        myFile.writeText(html)
    }

    fun checkPage(): Boolean{
        return myFile.exists()
    }

}