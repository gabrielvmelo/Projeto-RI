import java.io.*
import com.fasterxml.jackson.module.kotlin.*
import de.undercouch.bson4jackson.BsonFactory
import indexing.FieldIndex
import indexing.TermIndex
import java.io.ByteArrayOutputStream
import com.fasterxml.jackson.databind.ObjectMapper
import indexing.DocumentsID


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

    fun storeDataInBSON(data: Any, fileName: String){
        val path = "repository/$fileName.bson"
        val myFile = File(path)
        val mapperBSON = ObjectMapper(BsonFactory())

        mapperBSON.writeValue(myFile, data)
    }

    fun retrieveDOCIDFromBSON(fileName: String): DocumentsID {
        val mapperBSON = ObjectMapper(BsonFactory())
        val path = "repository/$fileName.bson"
        val myFile = File(path)
        return mapperBSON.readValue(myFile, DocumentsID::class.java)
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