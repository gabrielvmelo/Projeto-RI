package indexing

//objeto que engloba:
// frequencia (quantidade de documentos que contem o termo)
// lista de: doc e frequencia no doc
class TermFrequency (
    private val documentsFrequency: Int,
    private val documentsList: ArrayList<HashMap<Int, Int>>){

    override fun toString(): String {
        return "($documentsFrequency, $documentsList)"
    }
}