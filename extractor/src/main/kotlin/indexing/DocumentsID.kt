package indexing

//criacao dos IDs dos documentos(paginas)
class DocumentsID {
    var documentsIDs = hashMapOf<String, String>()

    fun createIDs(URLs: Array<String>): HashMap<String, String>{
        for((counter, URL) in URLs.withIndex()){
            if(!documentsIDs.contains(URL)) documentsIDs[URL] = Integer.toBinaryString(counter)
        }

        return documentsIDs
    }
}