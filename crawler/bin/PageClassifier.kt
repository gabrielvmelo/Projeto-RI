import org.jsoup.Jsoup

//Analisando pagina para definir se eh relevante ou nao a partir das palavras presentes
class PageClassifier {
    val hashMap = hashMapOf(
            "season" to 15, "seasons" to 15, "tv" to 10, "episodes" to 15, "series" to 15,"serie" to 15, "episode" to 15, "guide" to 5, "status" to 7, "returning" to 7,
            "ended" to 7, "show" to 15, "continuing" to 7, "info" to 3, "genre" to 3, "network" to 3, "premiere" to 3, "date" to 3, "exec" to 3,
            "executive" to 3, "producers" to 3, "critic" to 3, "features" to 3, "stars" to 3, "watchers" to 3, "plays" to 3, "rating" to 3, "reviews" to 3,
            "fresh" to 3, "rotten" to 3, "tomatometer" to 3, "critics" to 3, "audience" to 3, "score" to 3, "ratings" to 3, "videos" to 3, "photos" to 3,
            "cast" to 3, "netflix" to 1, "creators" to 1, "starring" to 1, "details" to 1, "trailers" to 1, "pictures" to 1,
            "creator" to 1, "crew" to 1, "popularity" to 1, "storyline" to 1, "plot" to 1, "genres" to 1, "taglines" to 1, "certificate" to 1, "parents" to 1,
            "country" to 1, "language" to 1, "filming" to 1, "locations" to 1, "company" to 1, "credits" to 1, "technical" to 1, "specs" to 1, "sound" to 1,
            "color" to 1, "runtime" to 1, "quotes" to 1, "soundtracks" to 1, "user" to 1, "overview" to 1, "featured" to 1, "play" to 1, "facts" to 1,
            "type" to 1, "keywords" to 1, "media" to 1, "current" to 1, "released" to 1, "collected" to 1, "comments" to 1, "lists" to 1, "votes" to 1,
            "airs" to 1, "premieres" to 1, "recent" to 1, "watchlist" to 1, "view" to 1, "rank" to 1, "premise" to 1, "full" to 1, "summary" to 1, "genre(s)" to 1, "credits" to 1,
            "director" to 1, "awards" to 1, "positive" to 1, "mixed" to 1, "negative" to 1, "imdb" to 1, "thetvdb" to 1, "posters" to 1, "banners" to 1,
            "film" to (-10), "tickets" to (-10), "showtimes" to (-10), "movie" to (-7), "movies" to (-7), "theaters" to (-10), "dvd" to (-10), "albums" to (-10), "record" to (-5),
            "cheats" to (-5), "controllers" to (-5), "developer" to (-5), "playstation" to (-10), "log" to (-5), "account" to (-5), "donate" to (-5), "interviews" to (-1),
            "sponsors" to (-5), "supporting" to (-5), "community" to (-5), "feed" to (-5), "near" to (-5), "sign" to (-5), "news" to (-5), "new" to (-1),
            "login" to (-5), "member" to (-5), "developers" to (-5), "blu" to (-10), "ray" to (-10), "calendar" to (-5), "football" to (-5), "league" to (-5),
            "sports" to (-5), "studio" to (-3), "label" to (-3), "platform" to (-3), "players" to (-3), "apps" to (-3), "devices" to (-3), "report" to (-3),
            "budget" to (-1), "revenue" to (-1), "category" to (-1), "channels" to (-1), "listings" to (-1), "people" to (-1), "showing" to (-1), "sorted" to (-1)
    )

    fun scorePage(html: String): Int{
        val scoresMap = hashMapOf<String, Int>()
        val tokens = pageTokenizer(html).map { token -> token.toLowerCase() }

        val wordLimitMap = hashMapOf(
                "tv" to 100, "channel" to 50, "network" to 15, "rate" to 15, "watchlist" to 30, "watch" to 25, "released" to 100, "episode" to 75, "episodes" to 75,
                "guide" to 30, "season" to 60, "date" to 60, "genres" to 50, "runtime" to 50, "info" to 20, "rank" to 15
        )

        for (token in tokens){
            val value = scoreToken(token)
            if (value == 0) continue
            if (!scoresMap.contains(token)) scoresMap[token] = value
            else {
                scoresMap[token] = scoresMap[token]!! + value
            }
        }

        for(word in wordLimitMap.keys){
            if (scoresMap.contains(word) && (scoresMap[word]!!/hashMap[word]!!) > wordLimitMap[word]!!) scoresMap[word] = -2000
        }

        var score = 0
        for (value in scoresMap.values){
            score += value
        }

        return score
    }

    private fun pageTokenizer(html: String): List<String>{
        val textHTML = Jsoup.parse(html).text()
        val regex = ":|/|\\.|-|_|&| |;|,|=|\\?".toRegex()
        return textHTML.split(regex)
    }

    private fun scoreToken(token: String): Int{
        var score = 0

        if (hashMap.contains(token)){
            score = hashMap.getValue(token)
        }

        return score
    }
}