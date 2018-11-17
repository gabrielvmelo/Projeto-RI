package indexing

//objeto que representa o indice de campos
class FieldIndex(
    val index: HashMap<String, ArrayList<String>>) {

    override fun toString(): String {
        return index.toString()
    }
}