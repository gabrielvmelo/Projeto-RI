package indexing

//objeto que representa o indice termo documento
class TermIndex(
    val index: Map<String, TermFrequency>){

    override fun toString(): String {
        return index.toString()
    }
}