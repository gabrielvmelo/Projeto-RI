import Main.Companion.ATTR1
import Main.Companion.ATTR2
import Main.Companion.ATTR3
import Main.Companion.ATTR4
import java.io.*

/**
 * Created by lariciamota.
 */
//Armazenar as paginas visitadas
class RepositoryManager {

    fun storeDataInFile(data: String, fileName: String){
        val path = "repository/$fileName"
        val myFile = File(path)
        val fileWriter = FileWriter(myFile, true)

        val bufferedWriter = BufferedWriter(fileWriter)
        bufferedWriter.write(data)
        bufferedWriter.close()
    }

    fun retrieveDataFromFile(fileName: String): HashMap<String, HashMap<String, String>>{
        val path = "repository/$fileName"
        val myFile = File(path)
        val fileReader = FileReader(myFile)
        val bufferedReader = BufferedReader(fileReader)

        val data = hashMapOf<String, HashMap<String, String>>()
        var url: String? = null

        for (i in bufferedReader.lines()){
            val line = bufferedReader.readLine()
            if (line != null){
                val values: List<String> = line.split(" ## ")
                for (j in values.indices){
                    when(j){
                        0 -> {
                            url = values[j]
                            if(!data.contains(url)) data[url] = hashMapOf()
                        }
                        1 -> data[url]!![ATTR1] = values[j]
                        2 -> data[url]!![ATTR2] = values[j]
                        3 -> data[url]!![ATTR3] = values[j]
                        4 -> data[url]!![ATTR4] = values[j]
                    }
                }
            }
        }
        return data
    }

}