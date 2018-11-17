package indexing

import Main.Companion.downloadPage
import org.jsoup.Jsoup
import java.lang.Exception

//criacao de map com tokens e lista de documentos em que estao presentes
class Tokenizer {
    private val NUMBER_ATTEMPTS = 5
    fun termTokens(documentsID: HashMap<String, String>): HashMap<String, ArrayList<String>>{
        val tokensMap = hashMapOf<String, ArrayList<String>>()
        var html: String? = null
        var tokens: List<String>?

        for (url in documentsID.keys){
            var success = false
            var countFail = 0
            while (!success && countFail < NUMBER_ATTEMPTS){
                try {
                    html = downloadPage(url)
                    success = true
                } catch (e: Exception) {
                    println(e.message + " $url")
                    countFail += 1
                }
            }
            tokens = pageTokenizer(html!!).map { token -> token.toLowerCase() }
            for (token in tokens){
                if (!tokensMap.contains(token)) {
                    tokensMap[token] = arrayListOf(documentsID[url]!!)
                } else {
                    tokensMap[token]!!.add(documentsID[url]!!)
                }
            }
            println(url)
        }

        for (docIDs in tokensMap.values){
            docIDs.sort()
        }
        return tokensMap
    }

    private fun pageTokenizer(html: String): List<String>{
        val textHTML = Jsoup.parse(html).text()
        return stringTokenizer(textHTML)
    }

    private fun stringTokenizer(text: String): List<String>{
        val regex = "[^A-Za-z0-9]+".toRegex()
        return text.split(regex)
    }

    fun fieldTokens(extractorData: HashMap<String, HashMap<String, String>>, documentsID: HashMap<String, String>): HashMap<String, HashMap<String, Array<String>>>{
        val tokensMap = hashMapOf<String, HashMap<String, Array<String>>>() //int=numero doc, string=attr, array=values
        var tokensList: Array<String>

        for (url in extractorData.keys){
            val id = documentsID[url]
            tokensMap[id!!] = hashMapOf()
            for (attr in extractorData[url]!!.keys){
                tokensList = stringTokenizer(extractorData[url]!![attr]!!).map { token -> token.toLowerCase() }.toTypedArray()
                tokensMap[id]!![attr] = tokensList
            }
        }

        return tokensMap
    }

}