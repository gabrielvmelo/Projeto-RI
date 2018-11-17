package indexing

//construcao do indice termo documento
class BuildTermIndex {
    private var tokensMap: HashMap<String, ArrayList<String>>? = null

    //conta a frequencia do token em relacao aos documentos
    private fun countFrequency(documentsList: ArrayList<HashMap<String, Int>>): Int{
        return documentsList.size
    }

    //povoa list de acordo com o map do tokenizer
    private fun createList(tokenList: ArrayList<String>): ArrayList<HashMap<String, Int>>{
        val documentsList = arrayListOf<HashMap<String, Int>>()

        var count = 1
        for (id1 in 0 until (tokenList.size-1)){
            val id2 = id1+1
            if (tokenList[id1] == tokenList[id2]){
                count++
                if (tokenList.lastIndex == id2){
                    documentsList.add(hashMapOf(tokenList[id1] to count))
                }
            } else {
                documentsList.add(hashMapOf(tokenList[id1] to count))
                if (tokenList.lastIndex == id2){
                    documentsList.add(hashMapOf(tokenList[id2] to 1))
                }
                count = 1
            }
        }
        if (tokenList.size == 1){
            documentsList.add(hashMapOf(tokenList[0] to 1))
        }

        return documentsList
    }

    fun build(tokensMap: HashMap<String, ArrayList<String>>): TermIndex{
        this.tokensMap = tokensMap //saida do tokenizer: String=token List=ids
        var documentsList: ArrayList<HashMap<String, Int>>
        var termFrequency: TermFrequency
        val index = hashMapOf<String, TermFrequency>() //String=token TermFrequency= F (docs) e lista com [id, f]

        for (token in tokensMap){
            documentsList = createList(token.value)
            termFrequency = TermFrequency(countFrequency(documentsList), documentsList)
            index[token.key] = termFrequency
        }

        return TermIndex(index)
    }
}