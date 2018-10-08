import java.net.URI

//Tokenizar as URLs para adicionar pesos as palavras
class Tokenizer {
    val hashMap = hashMapOf(
            "s01" to 10, "s1" to 10, "s2" to 10, "s02" to 10, "s3" to 10, "s03" to 10, "s4" to 10, "s04" to 10, "s5" to 10,"s05" to 10,
            "s6" to 10, "s06" to 10, "s7" to 10, "s07" to 10, "s8" to 10, "s08" to 10, "s9" to 10, "s09" to 10, "s10" to 10,
            "season" to 10, "seasons" to 3, "shows" to 10, "show" to 10, "tvshows" to 10, "tvshow" to 10, "series" to 10,
            "tv" to 10, "toptv" to 3, "title" to 10, "episode" to 1, "episodes" to 1, "watch" to 3, "cast" to 1, "actors" to 1, "person" to 1, "people" to 1, "reviews" to 1,
            "release" to 1, "date" to 1, "photos" to 1, "galleries" to 1, "gallery" to 1, "videogallery" to 1,
            "mediaindex" to 1, "videos" to 1, "images" to 1, "awards" to 1, "top" to 1, "streaming" to 1,
            "score" to 1, "metascore" to 1,  "critic" to 1, "watchlist" to (-10), "listings" to (-10), "lists" to (-10), "discover" to (-10), "calendar" to (-10), "calendars" to (-10), "special" to 1,
            "m" to (-15), "movie" to (-30), "movies" to (-30), "moviemeter" to (-30), "oscar" to (-30), "dvd" to (-30), "theater" to (-30), "theaters" to (-30),
            "login" to (-40), "account" to (-30), "signup" to (-40), "user" to (-40), "users" to (-40), "registration" to (-40), "signin" to (-40), "trending" to (-10),
            "game" to (-10), "games" to (-20), "company" to (-20), "music" to (-30), "albums" to (-30), "donate" to (-30), "sponsors" to (-30), "contribute" to (-30),
            "showtimes" to (-20), "apps" to (-10), "sports" to (-30), "tech" to (-20), "community" to (-20), "trivia" to (-20),
            "news" to (-30), "publication" to (-3), "discuss" to (-20), "documentation" to (-20), "talk" to (-20),
            "trailers" to (-10), "leaderboard" to (-10), "vip" to (-1), "browse" to (-1), "post" to (-10), "posts" to (-10), "home" to (-3)
    )

    fun scoreURL(url: String, domain: String): Int{
        val scoresMap = hashMapOf<String, Int>()
        val tokens = urlTokenizer(url).map { token -> token.toLowerCase() }

        for (token in tokens){
            val value = scoreToken(token)
            if (value == 0) continue
            if (!scoresMap.contains(token)) scoresMap[token] = value
            else {
                scoresMap[token] = scoresMap[token]!! + value
            }
        }
        var score = 0

        if (domain.contains("tv")) score - 10

        for (value in scoresMap.values){
            score += value
        }

        return score
    }

    private fun urlTokenizer(url: String): List<String>{
        return url.split(":|/|\\.|-|_|&|=|\\?".toRegex())
    }

    private fun scoreToken(token: String): Int{
        var score = 0

        if (hashMap.contains(token)){
            score = hashMap.getValue(token)
        }

        return score
    }
}