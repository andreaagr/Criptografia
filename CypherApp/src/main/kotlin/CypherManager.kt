import hill.HillCypher
import playfair.PlayfairCypher
import series.TranspositionSeriesCypher
import utils.FileHelper

class CypherManager {
    private val hillCypher = HillCypher()
    private val playfairCypher = PlayfairCypher()
    private val transpositionSeriesCypher = TranspositionSeriesCypher()
    private val fileHelper = FileHelper()

    /**
     * Aplica el encriptado o desencriptado según el orden establecido por el proyecto
     * */
    fun encryptOrDecrypt(
        isDecrypting: Boolean,
        mcla: String,
        playfairKey: String,
        hillKey: String,
        functionData: List<Pair<Int,Int>>,
        path: String
    ) {
        val seriesResult: String
        val playfairResult: String
        val hillResult: String
        val message = mcla.replace(" ", "")
        val functions = mutableListOf<List<Int>>()
        println("FUNCIONES:")
        for (values in functionData) {
            functions.add(transpositionSeriesCypher.createFunction(values.first, message.length, values.second))
        }

        if (isDecrypting) {
            println("DESENCRIPTANDO...")
            println("INICIA PLAYFAIR")
            playfairResult = playfairCypher.playfair(playfairKey, message, isDecrypting)
            println("RESULTADO: $playfairResult")
            println("TERMINA PLAYFAIR")

            println("INICIA DESCIFRADO HILL")
            hillResult = hillCypher.decrypt(hillKey, playfairResult)
            println("\nRESULTADO: $hillResult")
            println("\nTERMINA CIFRADO HILL")

            println("INICIA TRANSPOSICIÓN POR SERIES")
            seriesResult = transpositionSeriesCypher.decrypt(mcla, functions)
            println("RESULTADO: $seriesResult")
            println("TERMINA TRANSPOSICIÓN POR SERIES")
        } else {
            println("ENCRIPTANDO...")
            println("INICIA TRANSPOSICIÓN POR SERIES")
            seriesResult = transpositionSeriesCypher.encrypt(mcla, functions)
            println("RESULTADO: $seriesResult")
            println("TERMINA TRANSPOSICIÓN POR SERIES")

            println("INICIA CIFRADO HILL")
            hillResult = hillCypher.encrypt(hillKey, seriesResult)
            println("\nRESULTADO: $hillResult")
            println("\nTERMINA CIFRADO HILL")

            println("INICIA PLAYFAIR")
            playfairResult = playfairCypher.playfair(playfairKey,hillResult, isDecrypting)
            println("RESULTADO: $playfairResult")
            println("TERMINA PLAYFAIR")
        }
        fileHelper.writeFile(path, playfairResult)
    }
}