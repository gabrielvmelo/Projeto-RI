package indexing

//construcao do indice de campos
class BuildFieldIndex {

    //montagem dos nomes de campo na forma value.attribute
    private fun fieldName(attr: String, value: String): String{
        return "$value.$attr"
    }

    fun build(tokensMap: HashMap<String, HashMap<String, Array<String>>>): FieldIndex{
        val index = hashMapOf<String, ArrayList<String>>() //string=campo array=ids doc
        var field: String

        for (id in tokensMap.keys){
            val attributes = tokensMap[id]
            for (attr in attributes!!.keys){
                val values = attributes[attr]
                for (value in values!!){
                    if (value != ""){
                        field = fieldName(attr, value)
                        if (index.contains(field)){
                            if (!index[field]!!.contains(id)) index[field]!!.add(id)
                        } else{
                            index[field] = arrayListOf(id)
                        }
                    }
                }
            }
        }

        return FieldIndex(index)
    }
}