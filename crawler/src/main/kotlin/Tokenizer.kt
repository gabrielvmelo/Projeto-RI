import kotlin.collections.ArrayList

//Tokenizar as URLs para adicionar pesos as palavras
class Tokenizer {

    fun scoreURL(url: String): Int{
        val listScores = arrayListOf<Int>()

        for (token in urlTokenizer(url)){
            listScores.add(scoreToken(token.toLowerCase()))
        }

        var score = 0
        for (value in listScores){
            score += value
        }

        return score
    }

    private fun urlTokenizer(url: String): List<String>{
        return url.split(":|/|.|-|_|&|=|\\?".toRegex())
    }

    private fun scoreToken(token: String): Int{
        var score = 0

        val hashMap = hashMapOf(
                "s0" to 5, "s1" to 5, "s2" to 5, "s3" to 5, "s4" to 5, "s5" to 5,"s6" to 5, "s7" to 5, "s8" to 5, "s9" to 5, "s10" to 5,
                "season" to 5, "seasons" to 5, "shows" to 5, "show" to 5, "tvshows" to 5, "series" to 5,
                "tv" to 3, "toptv" to 3, "title" to 3, "episodes" to 3, "watch" to 3, "cast" to 3, "actors" to 3, "person" to 3, "people" to 3, "reviews" to 3,
                "release" to 1, "date" to 1, "photos" to 1, "galleries" to 1, "gallery" to 1, "videogallery" to 1,
                "mediaindex" to 1, "videos" to 1, "images" to 1, "awards" to 1, "top" to 1, "streaming" to 1,
                "score" to 1, "metascore" to 1,  "critic" to 1, "watchlist" to 1, "listings" to 1,  "calendar" to 1, "special" to 1,
                "m" to (-5), "movie" to (-5), "movies" to (-5), "moviemeter" to (-5), "oscar" to (-5), "dvd" to (-5), "theaters" to (-5),
                "login" to (-5), "account" to (-5), "signup" to (-5), "user" to (-5), "registration" to (-5), "signin" to (-5),
                "game" to (-5), "games" to (-5), "company" to (-5), "music" to (-5), "albums" to (-5), "donate" to (-5), "sponsors" to (-5), "contribute" to (-5),
                "showtimes" to (-5), "apps" to (-5), "sports" to (-5), "tech" to (-5), "community" to (-5), "trivia" to (-5),
                "news" to (-3), "publication" to (-3), "discuss" to (-3), "documentation" to (-3), "talk" to (-3),
                "trailers" to (-1), "leaderboard" to (-1), "vip" to (-1), "browse" to (-1)
        )
        if (hashMap.contains(token)){
            score = hashMap.getValue(token)
        }

        return score
    }
}