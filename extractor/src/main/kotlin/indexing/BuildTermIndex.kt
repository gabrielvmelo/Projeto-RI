package indexing

//construcao do indice termo documento
class BuildTermIndex {
    private var tokensMap: HashMap<String, ArrayList<Int>>? = null

    //conta a frequencia do token em relacao aos documentos
    private fun countFrequency(){

    }

    //povoa list de acordo com o map do tokenizer
    private fun createList(tokenList: ArrayList<Int>): ArrayList<HashMap<Int, Int>>{
        val documentsList = arrayListOf<HashMap<Int, Int>>()

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

    //conta a frequencia do token no documento especifico
    private fun countFrequencyInDoc(){

    }

    fun build(tokensMap: HashMap<String, ArrayList<Int>>): TermIndex{
        this.tokensMap = tokensMap //saida do tokenizer: String=token List=ids
        val documentsFrequency: Int? = null
        val documentsList: ArrayList<HashMap<Int, Int>>? = null
        for (token in tokensMap){
            createList(token.value)
        }
        val index: HashMap<String, TermFrequency>? = null //String=token TermFrequency= F (docs) e lista com [id, f]

        //criando objetos
        val termFrequency = TermFrequency(documentsFrequency!!, documentsList!!)
        return TermIndex(index!!)
    }
}