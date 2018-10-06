import org.jsoup.Jsoup

//Analisando pagina para definir se eh relevante ou nao a partir das palavras presentes
class PageClassifier {
    
    fun scorePage(html: String): Int{

        val listScores = arrayListOf<Int>()
        for (token in pageTokenizer(html)){
            listScores.add(scoreToken(token.toLowerCase()))
        }

        var score = 0
        for (value in listScores){
            score += value
        }

        return score
    }

    private fun pageTokenizer(html: String): List<String>{
        val text_html = Jsoup.parse(html).text()
        return text_html.split(":|/|.|-|_|&| |;|,|=|\\?".toRegex())
    }

    private fun scoreToken(token: String): Int{
        var score = 0

        val hashMap = hashMapOf(
                "season" to 5, "seasons" to 5, "tv" to 5, "episodes" to 5, "series" to 5, "episode" to 5, "guide" to 5, "status" to 5, "returning" to 5,
                "ended" to 5, "show" to 5, "continuing" to 5, "info" to 3, "genre" to 3, "network" to 3, "premiere" to 3, "date" to 3, "exec" to 3,
                "executive" to 3, "producers" to 3, "critic" to 3, "features" to 3, "stars" to 3, "watchers" to 3, "plays" to 3, "rating" to 3, "reviews" to 3,
                "fresh" to 3, "rotten" to 3, "tomatometer" to 3, "critics" to 3, "audience" to 3, "score" to 3, "ratings" to 3, "videos" to 3, "photos" to 3,
                "cast" to 3, "netflix" to 3, "news" to 1, "interviews" to 1, "creators" to 1, "starring" to 1, "details" to 1, "trailers" to 1, "pictures" to 1,
                "creator" to 1, "crew" to 1, "popularity" to 1, "storyline" to 1, "plot" to 1, "genres" to 1, "taglines" to 1, "certificate" to 1, "parents" to 1,
                "country" to 1, "language" to 1, "filming" to 1, "locations" to 1, "company" to 1, "credits" to 1, "technical" to 1, "specs" to 1, "sound" to 1,
                "color" to 1, "runtime" to 1, "quotes" to 1, "soundtracks" to 1, "user" to 1, "overview" to 1, "featured" to 1, "play" to 1, "facts" to 1,
                "type" to 1, "keywords" to 1, "media" to 1, "current" to 1, "released" to 1, "collected" to 1, "comments" to 1, "lists" to 1, "votes" to 1,
                "airs" to 1, "premieres" to 1, "recent" to 1, "watchlist" to 1, "premise" to 1, "full" to 1, "summary" to 1, "genre(s)" to 1, "credits" to 1,
                "director" to 1, "awards" to 1, "positive" to 1, "mixed" to 1, "negative" to 1, "imdb" to 1, "posters" to 1, "banners" to 1,
                "tickets" to (-5), "showtimes" to (-5), "movie" to (-5), "theaters" to (-5), "dvd" to (-5), "albums" to (-5), "record" to (-5), "game" to (-5),
                "cheats" to (-5), "controllers" to (-5), "developer" to (-5), "playstation" to (-5), "log" to (-5), "account" to (-5), "donate" to (-5),
                "sponsors" to (-5), "supporting" to (-5), "community" to (-5), "feed" to (-5), "tickets" to (-5), "showtimes" to (-5), "near" to (-5), "sign" to (-5),
                "login" to (-5), "member" to (-5), "developers" to (-5), "blu" to (-5), "ray" to (-5), "calendar" to (-5), "football" to (-5), "league" to (-5),
                "sports" to (-5), "studio" to (-3), "label" to (-3), "platform" to (-3), "players" to (-3), "apps" to (-3), "devices" to (-3), "report" to (-3),
                "budget" to (-1), "revenue" to (-1), "category" to (-1), "channels" to (-1), "listings" to (-1), "people" to (-1), "showing" to (-1), "sorted" to (-1)
        )
        if (hashMap.contains(token)){
            score = hashMap.getValue(token)
        }

        return score
    }
}