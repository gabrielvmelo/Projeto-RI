import Main.Companion.ATTR1
import Main.Companion.ATTR2
import Main.Companion.ATTR3
import Main.Companion.ATTR4
import com.fasterxml.jackson.core.type.TypeReference
import java.io.*
import com.fasterxml.jackson.module.kotlin.*
import indexing.TermFrequency
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

}