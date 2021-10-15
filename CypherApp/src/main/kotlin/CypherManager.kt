import hill.HillCypher
import playfair.PlayfairCypher
import series.TranspositionSeriesCypher
import utils.FileHelper

class CypherManager {
    private val hillCypher = HillCypher()
    private val playfairCypher = PlayfairCypher()
    private val transpositionSeriesCypher = TranspositionSeriesCypher()
    private val fileHelper = FileHelper()

    fun encryptOrDecrypt(
        isDecrypting: Boolean,
        mcla: String,
        playfairKey: String,
        hillKey: String,
        functionData: List<Pair<Int,Int>>,
        path: String
    ) {
        if (isDecrypting) {
            println("DESENCRIPTANDO...")
        } else {
            println("ENCRIPTANDO...")
        }
        println("INICIA TRANSPOSICIÓN POR SERIES")
        val message = mcla.replace(" ", "")
        val functions = mutableListOf<List<Int>>()
        println("FUNCIONES:")
        for (values in functionData) {
            functions.add(transpositionSeriesCypher.createFunction(values.first, message.length, values.second))
        }
        val seriesResult = transpositionSeriesCypher.startTransposicion(mcla, functions, isDecrypting)
        println("RESULTADO: $seriesResult")
        println("TERMINA TRANSPOSICIÓN POR SERIES")

        println("INICIA CIFRADO HILL")
        val hillResult = hillCypher.hillAlgoritm(hillKey, seriesResult, isDecrypting)
        println("\nRESULTADO: $hillResult")
        println("\nTERMINA CIFRADO HILL")

        println("INICIA PLAYFAIR")
        val playfairResult = playfairCypher.playfair(playfairKey,hillResult, isDecrypting)
        println("RESULTADO: $playfairResult")
        println("TERMINA PLAYFAIR")

        fileHelper.writeFile(path, playfairResult)
    }
}