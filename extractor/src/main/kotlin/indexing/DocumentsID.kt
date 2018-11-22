package indexing

import java.nio.ByteBuffer
import java.util.*

//criacao dos IDs dos documentos(paginas)
class DocumentsID {
    var documentsIDs = hashMapOf<String, ByteArray>()

    fun createIDs(URLs: Array<String>){
        val random = Random().nextInt(10000000-1000000) + 1000000
        var bb: ByteBuffer

        for((counter, URL) in URLs.withIndex()){
            if(!documentsIDs.contains(URL)) {
                bb = ByteBuffer.allocate(4)
                bb.putInt(counter + random)
                documentsIDs[URL] = bb.array()
            }
//            println("numero ${counter+random}  e ${ByteBuffer.wrap(documentsIDs[URL]).int}")
        }
    }
}