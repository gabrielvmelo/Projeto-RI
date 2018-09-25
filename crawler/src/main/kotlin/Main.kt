import com.sun.xml.internal.fastinfoset.util.StringArray

/**
 * Created by lariciamota.
 */
class Main {
    companion object{
        @JvmStatic
        fun main(args: Array<String>){
            //URLs
            var URLs = arrayOf (
                "https://www.rottentomatoes.com/",
                "https://www.imdb.com/",
                "https://www.themoviedb.org/",
                "https://trakt.tv/",
                "https://www.tvguide.com/",
                "http://www.metacritic.com/",
                "https://www.thetvdb.com/",
                "http://www.tv.com/"
            )
            //Salvar robots.txt

            //Passar pelos 8 sites e coletar 1000 links de cada
            for(item in URLs){
                var fronteira = Frontier()
                fronteira.addURL(item)

                while (!fronteira.vazia()){
                    //Analisar robots.txt

                    //Passar pagina pelo parser
                    var rbt = Robot()
                    rbt.downloadPage(fronteira.remove())
                    //Enquanto lista da fronteira nao esta vazia
                    //Faz o download da prox pagina na fronteira com Robot
                    //Passa a pagina para o TextProcessor e encontra novos links
                }

            }



        }
    }
}