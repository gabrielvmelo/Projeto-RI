package indexing

//criacao dos IDs dos documentos(paginas)
class DocumentsID {
    var documentsIDs = hashMapOf<String, Int>()

    fun createIDs(URLs: Array<String>): HashMap<String, Int>{
        for((counter, URL) in URLs.withIndex()){
            if(!documentsIDs.contains(URL)) documentsIDs[URL] = counter
        }

        return documentsIDs
    }
}