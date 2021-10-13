import hill.HillCypher
import playfair.PlayfairCypher
import series.TranspositionSeriesCypher

class CypherManager {
    private val hillCypher = HillCypher()
    private val playfairCypher = PlayfairCypher()
    private val transpositionSeriesCypher = TranspositionSeriesCypher()

    fun encryptOrDecrypt(
        isDecrypting: Boolean,
        mcla: String,
        playfairKey: String,
        hillKey: String,
        functionData: List<Pair<Int,Int>>
    ) {
        if (isDecrypting) {
            println("DESENCRIPTANDO...")
        } else {
            println("ENCRIPTANDO...")
        }
        println("INICIA TRANSPOSICIÓN POR SERIES")
        val message = mcla.replace(" ", "")
        val functions = mutableListOf<List<Int>>()
        println(functionData)
        println("FUNCIONES:")
        for (values in functionData) {
            functions.add(transpositionSeriesCypher.createFunction(values.first, message.length, values.second))
        }
        val seriesResult = transpositionSeriesCypher.encrypt(mcla, functions)
        println("RESULTADO: $seriesResult")
        println("TERMINA TRANSPOSICIÓN POR SERIES")

        println("INICIA PLAYFAIR")
        val playfairResult = playfairCypher.playfair(playfairKey,seriesResult, isDecrypting)
        println("RESULTADO: $playfairResult")
        println("TERMINA PLAYFAIR")

        println("INICIA CIFRADO HILL")
        val hillResult = hillCypher.decrypt(hillKey, seriesResult)
        println("\nRESULTADO: $hillResult")
        println("\nTERMINA CIFRADO HILL")
    }
}