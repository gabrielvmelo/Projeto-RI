package indexing

import java.util.*

//criacao dos IDs dos documentos(paginas)
class DocumentsID {
    var documentsIDs = hashMapOf<String, Int>()

    fun createIDs(URLs: Array<String>): HashMap<String, Int>{
        val random = Random().nextInt(10000000-1000000) + 1000000
        for((counter, URL) in URLs.withIndex()){
            if(!documentsIDs.contains(URL)) documentsIDs[URL] = counter + random
        }

        return documentsIDs
    }
}