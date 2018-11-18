package indexing

//transformar lista de documentos representados pelos seus IDs em
//lista de documentos com o ID do primeiro e intervalo nos seguintes
//Ex.: [1, 4, 5] em [1, 3, 1]
class Compressor {

    private fun compress(list: ArrayList<Int>): ArrayList<Int>{
        val compressedList = arrayListOf(list[0])
//        var sum = 0

        for (i in 1..list.lastIndex){
            compressedList.add(list[i]-list[i-1])
        }

        return compressedList
    }

    fun compressTermIndex(termIndex: TermIndex): TermIndex{
        val index = termIndex.index
        val termFrequency = index.values
        val list = arrayListOf<Int>()
        for (row in termFrequency){
            for (element in row.documentsList){
                list.add(element.keys.first())
            }
        }

        val compressedList = compress(list)
        var counter = 0
        for (row in termFrequency){
            for (element in row.documentsList){
                element[element.keys.first()] = compressedList[counter]
            }
            counter += 1
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