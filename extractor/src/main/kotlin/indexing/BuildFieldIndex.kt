package indexing

//construcao do indice de campos
class BuildFieldIndex {

    //montagem dos nomes de campo na forma value.attribute
    private fun fieldName(){

    }

    fun build(tokensMap: HashMap<Int, HashMap<String, ArrayList<String>>>): FieldIndex{
        val index = hashMapOf<String, ArrayList<Int>>()


        return FieldIndex(index)
    }
}