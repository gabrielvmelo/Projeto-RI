import java.io.File
import java.io.BufferedWriter
import java.io.FileWriter



/**
 * Created by lariciamota.
 */
//Armazenar as paginas visitadas
class MetadataRepository(fileName: String) {
    val path = "repository/$fileName"
    val myFile = File(path)

    fun storeData(data: String){
        val fileWriter = FileWriter(myFile, true)

        val bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.write(data)
        bufferedWriter.close()
    }

}