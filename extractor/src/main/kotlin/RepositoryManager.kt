import java.io.*
import com.fasterxml.jackson.module.kotlin.*
import indexing.FieldIndex
import indexing.TermIndex

/**
 * Created by lariciamota.
 */
//Armazenar as paginas visitadas
class RepositoryManager {
    val mapper = jacksonObjectMapper()

    fun storeDataInJSON(data: Any, fileName: String){
        val path = "repository/$fileName.json"
        val myFile = File(path)

        mapper.writerWithDefaultPrettyPrinter().writeValue(myFile, data)
    }

    fun retrieveDataFromJSON(fileName: String): Any{
        val path = "repository/$fileName.json"
        val myFile = File(path)
        return mapper.readValue(myFile)
    }

    fun retrieveTermIndex(fileName: String): TermIndex{
        val path = "repository/$fileName.json"
        val myFile = File(path)
        return mapper.readValue(myFile)
    }

    fun retrieveFieldIndex(fileName: String): FieldIndex {
        val path = "repository/$fileName.json"
        val myFile = File(path)
        return mapper.readValue(myFile)
    }
}