package indexing

import java.nio.ByteBuffer

//construcao do indice de campos
class BuildFieldIndex {

    //montagem dos nomes de campo na forma value.attribute
    private fun fieldName(attr: String, value: String): String{
        return "$value.$attr"
    }

    fun build(tokensMap: HashMap<ByteArray, HashMap<String, Array<String>>>): FieldIndex{
        val index = hashMapOf<String, ArrayList<ByteArray>>() //string=campo array=ids doc
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

                        //sort
                        val aux = arrayListOf<Int>()
                        for (docID in index[field]!!){
                            aux.add(ByteBuffer.wrap(docID).int)
                        }
                        aux.sort()
                        index[field]!!.clear()
                        for (docID in aux){
                            index[field]!!.add(ByteBuffer.allocate(4).putInt(docID).array())
                        }

                    }
                }
            }
        }

        return FieldIndex(index)
    }
}