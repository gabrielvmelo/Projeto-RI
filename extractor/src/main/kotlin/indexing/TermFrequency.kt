package indexing

import com.fasterxml.jackson.annotation.JsonProperty

//objeto que engloba:
// frequencia (quantidade de documentos que contem o termo)
// lista de: doc e frequencia no doc
class TermFrequency (
    val documentsFrequency: Int,
    val documentsList: ArrayList<HashMap<ByteArray, Int>>){

    override fun toString(): String {
        return "($documentsFrequency, $documentsList)"
    }
}