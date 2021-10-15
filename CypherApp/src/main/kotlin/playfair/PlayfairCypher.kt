package playfair

/**
 * Clase con las reglas y métodos para aplicar el cifrado Playfair
 * */
class PlayfairCypher() {
    private val playfairFormatter = PlayfairFormatter()

    private val matrixHelper = PlayfairMatrixHelper()

    fun playfair(key: String, mclaOrCriptogram: String, isDecrypting: Boolean): String {
        matrixHelper.createMatrix(playfairFormatter.removeIAndJ(key))
        return encryptOrDecrypt(playfairFormatter.removeIAndJ(mclaOrCriptogram.uppercase()), isDecrypting)
    }

    private fun encryptOrDecrypt (mclaOrCriptogram: String, isDecrypting: Boolean): String {
        var result = ""
        val diagrams = if (isDecrypting) {
            playfairFormatter.removeIAndJ(mclaOrCriptogram).replace(" ", "").chunked(2)
        } else {
            playfairFormatter.preprocessDiagrams(mclaOrCriptogram)
        }
        println("FORMACIÓN DE DIAGRAMAS:")
        println(diagrams)
        var numDiagram = 1
        diagrams.forEach { diagram ->
            println("Diagrama #$numDiagram")
            println(diagram)
            println()
            val firstChar = matrixHelper.findInMatrix(diagram[0])
            val secondChar = matrixHelper.findInMatrix(diagram[1])
            println("Positions:---------------")
            println("${diagram[0]}: (${firstChar.first}, ${firstChar.second})")
            println("${diagram[1]}: (${secondChar.first}, ${secondChar.second})")
            println("-------------------------")
            println()
            result += applyRules(
                firstChar.second,
                firstChar.first,
                secondChar.second,
                secondChar.first,
                isDecrypting
            )
            result += " "
            numDiagram ++
        }
        return result
    }

    private fun applyRules(x1: Int, y1: Int, x2: Int, y2: Int, isDecrypting: Boolean): String{
        // Misma fila
        return if (y1 == y2) {
            matrixHelper.getValuesForSameRow(x1, y1, x2, y2, isDecrypting)
        // Misma columna
        } else if (x1 == x2) {
            matrixHelper.getValuesForSameColumn(x1, y1, x2, y2, isDecrypting)
        // Diferente fila y columna
        } else if (x1 != x2 && y1 != y2) {
            matrixHelper.getValuesNonMatchRowAndColumn(x1, y1, x2, y2)
        } else {
            ""
        }
    }
}

