import extractor.TextProcessor
import indexing.*
import org.jsoup.Jsoup
import java.lang.Exception
import java.net.URI
import java.net.URISyntaxException

class Main {

    companion object {
        // 1: extrator, 2: pre processamento indexador, 3: indexador termo doc, 4: indexador de campos
        // 5: compressor do indice termo doc, 6: compressor do indice de campos
        // 7: compressor com cod variavel do indice termo doc, 8: compressor com cod variavel do indice de campos
        private val CHOOSER = 8

        private val URLs = arrayOf (
            "https://www.metacritic.com/tv/anne-with-an-e",
            "https://www.metacritic.com/tv/westworld",
            "https://www.metacritic.com/tv/gossip-girl",
            "https://www.metacritic.com/tv/gilmore-girls",
            "https://www.metacritic.com/tv/narcos-mexico",
            "https://www.metacritic.com/tv/breaking-bad",
            "https://www.metacritic.com/tv/house-of-cards-2013",
            "https://www.metacritic.com/tv/bojack-horseman",
            "https://www.metacritic.com/tv/freaks-and-geeks",
            "https://www.metacritic.com/tv/suits",
            "https://www.metacritic.com/tv/the-simpsons",
            "https://www.metacritic.com/tv/weeds",
            "https://www.metacritic.com/tv/the-following",
            "https://www.metacritic.com/tv/altered-carbon",
            "https://www.metacritic.com/tv/orphan-black",
            "https://www.metacritic.com/tv/the-good-wife",
            "https://www.metacritic.com/tv/stranger-things",
            "https://www.rottentomatoes.com/tv/better_call_saul/",
            "https://www.rottentomatoes.com/tv/the_voice/",
            "https://www.rottentomatoes.com/tv/the_big_bang_theory/",
            "https://www.rottentomatoes.com/tv/the_good_doctor/",
            "https://www.rottentomatoes.com/tv/young_sheldon/",
            "https://www.rottentomatoes.com/tv/bull/",
            "https://www.rottentomatoes.com/tv/how_i_met_your_mother",
            "https://www.rottentomatoes.com/tv/how_to_get_away_with_murder",
            "https://www.rottentomatoes.com/tv/game_of_thrones?",
            "https://www.rottentomatoes.com/tv/westworld",
            "https://www.rottentomatoes.com/tv/mr_robot",
            "https://www.rottentomatoes.com/tv/will_grace?",
            "https://www.rottentomatoes.com/tv/once_upon_a_time",
            "https://www.rottentomatoes.com/tv/pretty_little_liars",
            "https://www.rottentomatoes.com/tv/vikings_2013",
            "https://www.rottentomatoes.com/tv/the_blacklist",
            "https://www.rottentomatoes.com/tv/scrubs",
            "https://www.rottentomatoes.com/tv/grey_s_anatomy",
            "https://www.rottentomatoes.com/tv/the_o_c_",
            "https://www.rottentomatoes.com/tv/the_oa",
            "https://www.rottentomatoes.com/tv/flash",
            "https://www.rottentomatoes.com/tv/skins_uk_",
            "https://www.rottentomatoes.com/tv/alcatraz_the_complete_series/s01",
            "https://www.rottentomatoes.com/tv/the_big_bang_theory",
            "https://www.imdb.com/title/tt7549864/",
            "https://www.imdb.com/title/tt0944947/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt0475784/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt0460649/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt0108778/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt1844624/?ref_=nv_sr_2",
            "https://www.imdb.com/title/tt0903747/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt6315640/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt0460637/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt0229889/?ref_=nv_sr_2",
            "https://www.imdb.com/title/tt1845307/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt6048596/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt1819545/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt5420376/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt2085059/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt5707802/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt0273855/?ref_=nv_sr_2",
            "https://www.imdb.com/title/tt0098800/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt0496424/?ref_=nv_sr_2",
            "https://www.imdb.com/title/tt0898266/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt3061046/?ref_=nv_sr_2",
            "https://www.imdb.com/title/tt2628232/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt3655448/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt2649356/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt5834204/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt2372162/?ref_=nv_sr_1",
            "https://www.imdb.com/title/tt0362359/?ref_=nv_sr_1",
            "https://www.themoviedb.org/tv/1399-game-of-thrones?language=en-US",
            "https://www.themoviedb.org/tv/63247-westworld?language=en-US",
            "https://www.themoviedb.org/tv/1100-how-i-met-your-mother?language=en-US",
            "https://www.themoviedb.org/tv/1668-friends?language=en-US",
            "https://www.themoviedb.org/tv/1402-the-walking-dead?language=en-US",
            "https://www.themoviedb.org/tv/10545-true-blood?language=en-US",
            "https://www.themoviedb.org/tv/69050-riverdale?language=en-US",
            "https://www.themoviedb.org/tv/61664-sense8?language=en-US",
            "https://www.themoviedb.org/tv/1408-house?language=en-US",
            "https://www.themoviedb.org/tv/1425-house-of-cards?language=en-US",
            "https://www.themoviedb.org/tv/19885-sherlock?language=en-US",
            "https://www.themoviedb.org/tv/63174-lucifer?language=en-US",
            "https://www.themoviedb.org/tv/46786-bates-motel?language=en-US",
            "https://www.themoviedb.org/tv/1434-family-guy?language=en-US",
            "https://www.themoviedb.org/tv/1421-modern-family?language=en-US",
            "https://www.tvguide.com/tvshows/skins/309170/",
            "https://www.tvguide.com/tvshows/la-casa-de-papel/1155946/",
            "https://www.tvguide.com/tvshows/3/874982/",
            "https://www.tvguide.com/tvshows/altered-carbon/1040368/",
            "https://www.tvguide.com/tvshows/the-rain/1165673/",
            "https://www.tvguide.com/tvshows/nikita/304040/",
            "https://www.tvguide.com/tvshows/desperate-housewives/100118/",
            "https://www.tvguide.com/tvshows/house/100213/",
            "https://www.tvguide.com/tvshows/gotham/638885/",
            "https://www.tvguide.com/tvshows/the-flash/644014/",
            "https://www.tvguide.com/tvshows/gossip-girl/288136/",
            "https://www.tvguide.com/tvshows/new-girl/325438/",
            "https://www.tvguide.com/tvshows/better-call-saul/590385/",
            "https://www.tvguide.com/tvshows/arrow/375406/",
            "https://www.tvguide.com/tvshows/everybody-hates-chris/192250/",
            "https://trakt.tv/shows/game-of-thrones",
            "https://trakt.tv/shows/westworld",
            "https://trakt.tv/shows/the-good-doctor",
            "https://trakt.tv/shows/friends",
            "https://trakt.tv/shows/brooklyn-nine-nine",
            "https://trakt.tv/shows/nikita",
            "https://trakt.tv/shows/money-heist",
            "https://trakt.tv/shows/vikings",
            "https://trakt.tv/shows/desperate-housewives",
            "https://trakt.tv/shows/santa-clarita-diet",
            "https://trakt.tv/shows/the-big-bang-theory",
            "https://trakt.tv/shows/mr-robot",
            "https://trakt.tv/shows/3-2016",
            "https://trakt.tv/shows/arrow",
            "https://www.thetvdb.com/series/lost",
            "https://www.thetvdb.com/series/supergirl",
            "https://www.thetvdb.com/series/game-of-thrones",
            "https://www.thetvdb.com/series/stranger-things",
            "https://www.thetvdb.com/series/izombie",
            "https://www.thetvdb.com/series/general-hospital",
            "https://www.thetvdb.com/series/modern-family",
            "http://www.tv.com/shows/the-big-bang-theory/",
            "https://www.thetvdb.com/series/gravity-falls",
            "http://www.tv.com/shows/law-order/",
            "http://www.tv.com/shows/blackish/",
            "http://www.tv.com/shows/greys-anatomy/",
            "http://www.tv.com/shows/buffy-the-vampire-slayer/",
            "http://www.tv.com/shows/supernatural/",
            "http://www.tv.com/shows/doctor-who-2005/",
            "http://www.tv.com/shows/the-simpsons/",
            "http://www.tv.com/shows/days-of-our-lives/",
            "http://www.tv.com/shows/will-and-grace/"
        )
        val DOM1 = "imdb"
        val DOM2 = "metacritic"
        val DOM3 = "rottentomatoes"
        val DOM4 = "themoviedb"
        val DOM5 = "thetvdb"
        val DOM6 = "trakt"
        val DOM7 = "tv"
        val DOM8 = "tvguide"

        val ATTR1 = "title"
        val ATTR2 = "storyline"
        val ATTR3 = "premiere_date"
        val ATTR4 = "network"

        val DOCID = "DocumentsID"
        val DOCID_BINARY = "DocumentsIDBinary"
        val EXTRACTOR_DATA = "ExtractorData"
        val TERMINDEX = "TermIndex"
        val TERMTOKEN = "TermTokenizer"
        val FIELDINDEX = "FieldIndex"
        val FIELDTOKEN = "FieldTokenizer"
        val TERMINDEX_COMPRESSED = "TermIndexCompressed"
        val FIELDINDEX_COMPRESSED = "FieldIndexCompressed"
        val TERMINDEX_COMPRESSED_VARIABLE = "TermIndexCompressedVariable"
        val FIELDINDEX_COMPRESSED_VARIABLE = "FieldIndexCompressedVariable"
        val TERMINDEX_BINARY = "TermIndexBinary"
        val FIELDINDEX_BINARY = "FieldIndexBinary"
        val TERMINDEX_COMPRESSED_BINARY = "TermIndexCompressedBinary"
        val FIELDINDEX_COMPRESSED_BINARY = "FieldIndexCompressedBinary"
        val TERMINDEX_COMPRESSED_VARIABLE_BINARY = "TermIndexCompressedVariableBinary"
        val FIELDINDEX_COMPRESSED_VARIABLE_BINARY = "FieldIndexCompressedVariableBinary"

        val repo = RepositoryManager()
        private val NUMBER_ATTEMPTS = 5

        private fun extractor(URL: String, extractorData: HashMap<String, HashMap<String, String>>): HashMap<String, HashMap<String, String>>{
            val domain = getDomainName(URL)?.substringBefore(".")
            val txtProcessor = TextProcessor()

            var html: String? = null
            var success = false
            var countFail = 0
            while (!success && countFail < NUMBER_ATTEMPTS){
                try {
                    html = downloadPage(URL)
                    success = true
                } catch (e: Exception) {
                    println(e.message + " $URL")
                    countFail += 1
                }
            }

            var data: HashMap<String, String>? = null

            if (domain != null && html != null) data = txtProcessor.getMetadata(html, domain)

            if (data != null) {
                if(!extractorData.contains(URL)) extractorData[URL] = data
            }
            return extractorData
        }

        private fun indexer(URLs: Array<String>){
            //criando os IDs dos documentos
            val documentsID = DocumentsID()
            documentsID.createIDs(URLs)
            repo.storeDataInJSON(documentsID, DOCID)
            repo.storeDataInBSON(documentsID, DOCID_BINARY)
        }

        private fun termIndexer(documentsID: HashMap<String, ByteArray>){
            //tokenizando o html das paginas
            val termTokenizer = Tokenizer().termTokens(documentsID)
            repo.storeDataInJSON(termTokenizer, TERMTOKEN)

            //construindo indice termo documento
            val index = BuildTermIndex().build(termTokenizer)
            repo.storeDataInJSON(index, TERMINDEX)
            repo.storeDataInBSON(index, TERMINDEX_BINARY)
        }

        private fun fieldIndexer(extractorData: HashMap<String, HashMap<String, String>>, documentsID: HashMap<String, ByteArray>){
            //tokenizando os dados do extrator
            val fieldTokenizer = Tokenizer().fieldTokens(extractorData, documentsID)
            repo.storeDataInJSON(fieldTokenizer, FIELDTOKEN)

            //construindo indice de campos
            val index = BuildFieldIndex().build(fieldTokenizer)
            repo.storeDataInJSON(index, FIELDINDEX)
            repo.storeDataInBSON(index, FIELDINDEX_BINARY)
        }

        @JvmStatic
        fun main(args: Array<String>){
            var extractorData = hashMapOf<String, HashMap<String, String>>()
            val documentsID: HashMap<String, ByteArray>
            val termIndex: TermIndex
            val fieldIndex: FieldIndex
            val compressor = Compressor()

            when(CHOOSER){
                1 -> { //extrator
                    for (i in 0 until URLs.size) {
                        extractorData = extractor(URLs[i], extractorData)
                    }
                    repo.storeDataInJSON(extractorData, EXTRACTOR_DATA)
                }
                2 -> { //pre processamento do indexador
                    indexer(URLs)
                }
                3-> { //indice termo documento
                    documentsID = repo.retrieveDOCIDFromBSON(DOCID_BINARY).documentsIDs
                    termIndexer(documentsID)
                    termIndex = repo.retrieveTermIndex(TERMINDEX)
                }
                4 -> { //indice de campos
                    extractorData = repo.retrieveDataFromJSON(EXTRACTOR_DATA) as HashMap<String, HashMap<String, String>>
                    documentsID = repo.retrieveDOCIDFromBSON(DOCID_BINARY).documentsIDs
                    fieldIndexer(extractorData, documentsID)
                }
                5 -> { //compressor do termo documento
                    termIndex = compressor.compressTermIndex(1, repo.retrieveTermIndex(TERMINDEX))
                    repo.storeDataInJSON(termIndex, TERMINDEX_COMPRESSED)
                    repo.storeDataInBSON(termIndex, TERMINDEX_COMPRESSED_BINARY)
                }
                6 -> { //compressor do campos
                    fieldIndex = compressor.compressFieldIndex(1, repo.retrieveFieldIndex(FIELDINDEX))
                    repo.storeDataInJSON(fieldIndex, FIELDINDEX_COMPRESSED)
                    repo.storeDataInBSON(fieldIndex, FIELDINDEX_COMPRESSED_BINARY)
                }
                7 -> { //cod de tamanho variavel termo doc
                    termIndex = compressor.compressTermIndex(2, repo.retrieveTermIndex(TERMINDEX))
                    repo.storeDataInJSON(termIndex, TERMINDEX_COMPRESSED_VARIABLE)
                    repo.storeDataInBSON(termIndex, TERMINDEX_COMPRESSED_VARIABLE_BINARY)
                }
                8 -> { //cod de tamanho variavel campos
                    fieldIndex = compressor.compressFieldIndex(2, repo.retrieveFieldIndex(FIELDINDEX))
                    repo.storeDataInJSON(fieldIndex, FIELDINDEX_COMPRESSED_VARIABLE)
                    repo.storeDataInBSON(fieldIndex, FIELDINDEX_COMPRESSED_VARIABLE_BINARY)
                }
            }


        }


        @Throws(URISyntaxException::class)
        fun getDomainName(url: String): String? {
            var uri: URI? = null
            var domain: String? = null
            try{
                uri = URI(url)
                domain = uri.host
            } catch (e: URISyntaxException){
                println(e.message)
                return null
            }
            if(domain == null){
                println("WARN - invalid url detected: $url")
                return null
            }
            if (domain.startsWith("www.")) {
                return domain.substring(4)
            } else {
                return domain
            }
        }

        fun downloadPage(url: String): String? {
            val resp = Jsoup.connect(url).header("Accept-Language", "en").timeout(100 * 1000).execute()
            val contentType = resp.contentType()

            if (contentType.contains("application/xhtml+xml") || contentType.contains("text/html")){
                return resp.parse().outerHtml()
            }
            return null
        }
    }
}