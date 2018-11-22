package indexing

import java.nio.ByteBuffer
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.reflect.jvm.internal.impl.protobuf.Internal.toByteArray
import java.math.BigInteger



//transformar lista de documentos representados pelos seus IDs em
//lista de documentos com o ID do primeiro e intervalo nos seguintes
//Ex.: [1, 4, 5] em [1, 3, 1]
class Compressor {

    private fun compress(choose: Int, list: ArrayList<ByteArray>): ArrayList<ByteArray>{
        return when(choose){
            1 -> compressSimple(list)
            2 -> compressVariable(list)
            else -> compressSimple(list)
        }
    }

    private fun compressSimple(list: ArrayList<ByteArray>): ArrayList<ByteArray>{
        val compressedList = arrayListOf(list[0])
        for (i in 1..list.lastIndex){
            val bb = ByteBuffer.allocate(4)
            bb.putInt(ByteBuffer.wrap(list[i]).int-ByteBuffer.wrap(list[i-1]).int)
            compressedList.add(bb.array())
        }

        return compressedList
    }

    private fun compressVariable(list: ArrayList<ByteArray>): ArrayList<ByteArray>{
        val newList = compressSimple(list)
        val compressedList = arrayListOf<ByteArray>()

        for (i in newList){
            compressedList.add(codifyNumber(i))
        }

        return compressedList
    }

    private fun codifyNumber(number: ByteArray): ByteArray{
        var binary = Integer.toBinaryString(ByteBuffer.wrap(number).int)
        var c: Int
        val last = binary.lastIndex
        var code = String()
        var coded: ByteArray

        while (binary.isNotEmpty()){
            for (i in binary.lastIndex downTo 0 step 7){
                var counter = 0
                for (j in 0..6){
                    val index = i-j
                    if (i-j >= 0){
                        code = binary[index] + code
                        binary = binary.take(index)
                        counter++
                    } else{
                        continue
                    }
                }
                while (counter != 7){
                    code = "0$code"
                    counter++
                }
                if (i==last){
                    c = 1
                    code = "$c$code"
                } else {
                    c = 0
                    code = "$c$code"
                }
            }
        }
        val length = code.length/8
        coded = ByteArray(length)
        for ((counter, i) in (code.indices step 8).withIndex()){
            val split = code.substring(i, i+8)
            coded[counter] = toByteArrayFromBitString(split, 8)[0]
        }
        return coded
    }

    fun compressTermIndex(choose: Int, termIndex: TermIndex): TermIndex{
        val index = termIndex.index
        val oldIDs = arrayListOf<ByteArray>()
        val oldFrequencies = arrayListOf<Int>()
        var id: ByteArray

        for (termFrequencies in index.values){
            //limpar listas antigas
            oldIDs.clear()
            oldFrequencies.clear()

            //construir listas com dados antigos
            val list = termFrequencies.documentsList
            for (element in list){
                id = element.keys.first()
                oldIDs.add(id)
                oldFrequencies.add(element[id]!!)
            }

            //comprimir lista antiga de ids
            val compressedList = compress(choose, oldIDs)
            val newArray = arrayListOf<HashMap<ByteArray, Int>>()

            //atualizar documentsList
            for (i in list.indices){
                val newID = compressedList[i]
                val frequency = oldFrequencies[i]
                newArray.add(hashMapOf(newID to frequency))
            }

            //limpar array e colocar o novo
            termFrequencies.documentsList.clear()
            termFrequencies.documentsList.addAll(newArray)
        }

        return TermIndex(index)
    }

    fun compressFieldIndex(choose: Int, fieldIndex: FieldIndex): FieldIndex{
        val index = fieldIndex.index
        for (term in index.keys){
            index[term] = compress(choose, index[term]!!)
        }

        return FieldIndex(index)
    }

    private fun toByteArrayFromBitString(
        bits: String,
        bitCount: Int
    ): ByteArray {
        if (bitCount < 0) {
            return ByteArray(0)
        }
        val byteCount = (bitCount + 7) / 8
        val srcBytes: ByteArray
        if (bits.isNotEmpty()) {
            val bigInt = BigInteger(bits, 2)
            srcBytes = bigInt.toByteArray()
        } else {
            srcBytes = ByteArray(0)
        }
        val dest = ByteArray(byteCount)

        val bytesToCopy = Math.min(byteCount, srcBytes.size)
        System.arraycopy(
            srcBytes,
            srcBytes.size - bytesToCopy,
            dest,
            dest.size - bytesToCopy,
            bytesToCopy
        )
        return dest
    }

}