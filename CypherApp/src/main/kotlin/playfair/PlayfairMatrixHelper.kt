package playfair

import MatrixHelper

class PlayfairMatrixHelper() {
    private val matrix = Array(5) { Array(5) {'-'} }
    private val matrixHelper = MatrixHelper()
    private var alphabet = ""

    fun createMatrix(key: String): String {
        initAlphabet()
        val nextPosition = setMatrixValues(key.toSet().chunked(5), 0, 0)
        val restChunked = createListChunked(4 - nextPosition.second)
        setMatrixValues(restChunked, nextPosition.first, nextPosition.second + 1)
        println("PLAYFAIR MATRIX: \n")
        matrixHelper.printMatrix(matrix)
        println()
        return alphabet
    }

    fun findInMatrix(char: Char): Pair<Int, Int> {
        matrix.forEachIndexed { indexRow, element ->
            val indexColumn = element.indexOf(char)
            if (indexColumn != -1) {
                return Pair(indexRow, indexColumn)
            }
        }
        return Pair(-1, -1)
    }

    fun getValuesForSameRow(
        x1: Int,
        y1: Int,
        x2: Int,
        y2: Int,
        isDecrypting: Boolean
    ) = "${matrix[y1][circularMovement(x1, isDecrypting)]}${matrix[y2][circularMovement(x2, isDecrypting)]}"

    fun getValuesForSameColumn(
        x1: Int,
        y1: Int,
        x2: Int,
        y2: Int,
        isDecrypting: Boolean
    ) = "${matrix[circularMovement(y1, isDecrypting)][x1]}${matrix[circularMovement(y2, isDecrypting)][x2]}"

    fun getValuesNonMatchRowAndColumn(
        x1: Int,
        y1: Int,
        x2: Int,
        y2: Int
    ) = "${matrix[y1][x2]}${matrix[y2][x1]}"

    private fun circularMovement(index: Int, isDecrypting: Boolean): Int {
        return if (isDecrypting) {
            if(index - 1 < 0) {
                4
            } else {
                index - 1
            }
        } else {
            if(index + 1 > 4) {
                0
            } else {
                index + 1
            }
        }
    }

    private fun initAlphabet() {
        alphabet = "ABCDEFGH/KLMNOPQRSTUVWXYZ"
    }

    private fun setMatrixValues(chunks: List<List<Char>>,initRow: Int, initColumn: Int): Pair<Int,Int> {
        var row = initRow
        var column = initColumn
        val lastIndexChunk = chunks.size - 1
        chunks.forEach { chunk ->
            chunk.forEach { char ->
                matrix[row][column] = char
                alphabet = alphabet.replace(char, '-')
                column = column + 1
            }
            column = 0
            row = row + 1
        }
        alphabet = alphabet.filter {it -> it != '-'}
        return Pair(lastIndexChunk, chunks[lastIndexChunk].size - 1)
    }

    private fun createListChunked(splitIndex: Int): List<List<Char>> {
        val firstChunk = alphabet.substring(0, splitIndex).toList()
        val rest = alphabet.substring(splitIndex).toList().chunked(5)
        return mutableListOf<List<Char>>().apply {
            add(firstChunk)
            addAll(rest)
        }
    }
}


