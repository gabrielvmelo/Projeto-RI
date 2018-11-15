package indexing

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.MapSerializer

//objeto que representa o indice termo documento
class TermIndex(
    val index: Map<String, TermFrequency>){

    override fun toString(): String {
        return index.toString()
    }
}