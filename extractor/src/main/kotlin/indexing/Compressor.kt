package indexing

//transformar lista de documentos representados pelos seus IDs em
//lista de documentos com o ID do primeiro e intervalo nos seguintes
//Ex.: [1, 4, 5] em [1, 3, 1]
class Compressor {

    private fun compress(list: ArrayList<Int>): ArrayList<Int>{
        val compressedList = arrayListOf(list[0])

        for (i in 1..list.lastIndex){
            compressedList.add(list[i]-list[i-1])
        }

        return compressedList
    }

    fun compressTermIndex(termIndex: TermIndex): TermIndex{
        val index = termIndex.index
        val oldIDs = arrayListOf<Int>()
        val oldFrequencies = arrayListOf<Int>()
        var id: Int

        for (termFrequencies in index.values){
            //limpar listas antigas
            oldIDs.clear()
            oldFrequencies.clear()

            //construir listas com dados antigos
            val list = termFrequencies.documentsList
            for (element in list){
                id = element.keys.first()
                oldIDs.add(id)
                oldFrequencies.add(element[id]!!)
            }

            //comprimir lista antiga de ids
            val compressedList = compress(oldIDs)
            val newArray = arrayListOf<HashMap<Int, Int>>()

            //atualizar documentsList
            for (i in list.indices){
                val newID = compressedList[i]
                val frequency = oldFrequencies[i]
                newArray.add(hashMapOf(newID to frequency))
            }

            //limpar array e colocar o novo
            termFrequencies.documentsList.clear()
            termFrequencies.documentsList.addAll(newArray)
        }

        return TermIndex(index)
    }

    fun compressFieldIndex(fieldIndex: FieldIndex): FieldIndex{
        val index = fieldIndex.index
        for (term in index.keys){
            index[term] = compress(index[term]!!)
        }

        return FieldIndex(index)
    }
}