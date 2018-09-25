import java.util.*

/**
 * Created by lariciamota.
 */
//Recebe os links do TextProcessor e os guarda numa lista para saber o proximo a ser visitado
class Frontier(var fila: LinkedList<String> = LinkedList()) {

    fun addURL(url: String){
        fila.add(url)
    }

    fun remove(): String{
        return fila.poll()
    }

    fun vazia(): Boolean{
        return fila.isEmpty()
    }
}