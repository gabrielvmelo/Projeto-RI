package indexing

import Main.Companion.downloadPage
import RepositoryManager
import org.jsoup.Jsoup
import java.lang.Exception

//criacao de map com tokens e lista de documentos em que estao presentes
class Tokenizer {
    private val NUMBER_ATTEMPTS = 5
    fun termTokens(documentsID: HashMap<String, Int>): HashMap<String, ArrayList<Int>>{
        val tokensMap = hashMapOf<String, ArrayList<Int>>()
        var html: String? = null
        var tokens: List<String>?
        val repo = RepositoryManager()

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
        }

        for (docIDs in tokensMap.values){
            docIDs.sort()
        }
        repo.storeDataInFile(tokensMap.toString(), "tokenizer")
        return tokensMap
    }

    private fun pageTokenizer(html: String): List<String>{
        val textHTML = Jsoup.parse(html).text()
        val regex = "[^A-Za-z0-9]+".toRegex()
        return textHTML.split(regex)
    }

}