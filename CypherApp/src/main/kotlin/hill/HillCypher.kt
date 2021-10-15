package hill

import utils.MatrixHelper

class HillCypher {
    private var alphabet = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ"
    private val matrixHelper = MatrixHelper()
    private val calculationHelper = CalculationHelper()

    fun hillAlgoritm(key: String, mclaOrCriptogram: String, isDecrypting: Boolean): String {
        return if (isDecrypting) {
            decrypt(key, mclaOrCriptogram)
        } else {
            encrypt(key, mclaOrCriptogram)
        }
    }

    private fun encrypt(key: String, mcla: String): String {
        val matrixOrder = doMeetRequirements(key)
        return if (matrixOrder > 0) {
            val mArrays = createMArrays(fillMessageWithX(matrixOrder, mcla), matrixOrder)
            println("ARREGLOS M:\n")
            mArrays.forEachIndexed { index, array ->
                println("M$index:")
                println(array)
            }
            val kMatrix = createKMatrix(matrixOrder, key)
            println("\nK MATRIX:\n")
            matrixHelper.printMatrix(kMatrix)
            println()
            multiplyArrays(mArrays, kMatrix)
        } else {
            println(parseError(matrixOrder))
            mcla
        }
    }

    private fun decrypt(key: String, criptogram: String): String {
        val matrixOrder = doMeetRequirements(key)
        return if (matrixOrder > 0) {
            val mArrays = createMArrays(fillMessageWithX(matrixOrder, criptogram), matrixOrder)
            val kMatrix = createKMatrix(matrixOrder, key)
            println("\nMATRIZ ORIGINAL\n")
            matrixHelper.printMatrix(kMatrix)
            println()
            // Calcular el inverso modular
            val modularInv = canDecryptCriptogram(kMatrix)
            // Transponer la matrix
            val kTInv = calculationHelper.createCofactorsMatrix(matrixHelper.transponeMatrix(kMatrix))
            println("\nMATRIZ INVERSA DE LA TRANSPUESTA\n")
            matrixHelper.printMatrix(kTInv)
            val kInv = calculationHelper.applyModMatrixOp(matrixHelper.multiplyMatrixWithEscalar(kTInv, modularInv),alphabet.length)
            println("\nMATRIZ INVERSA MODULAR\n")
            matrixHelper.printMatrix(kInv)
            multiplyArrays(mArrays, kInv)
        } else {
            println(parseError(matrixOrder))
            criptogram
        }
    }

    private fun doMeetRequirements(key: String): Int {
        val matrixOrder = canCreateASquareMatrix(key)
        return if ( matrixOrder == -1) {
            -1
        } else {
            matrixOrder
        }
    }

    private fun parseError(code: Int): String{
        return when (code) {
            -1 -> "La clave no forma una matriz cuadrada"
            else -> "Decode error"
        }
    }

    private fun multiplyArrays(mArrays: List<List<Int>>, kMatrix: Array<Array<Int>>): String {
        var criptogram = ""
        var total = 0
        mArrays.forEach { mMatrix ->
            kMatrix.forEachIndexed { indexR, row ->
                row.forEachIndexed { indexC, _ ->
                    val subtotal = (kMatrix[indexR][indexC] * mMatrix[indexC])
                    total += subtotal
                }
                criptogram += alphabet[total % alphabet.length]
                total = 0
            }
        }
        return criptogram
    }


    private fun createKMatrix(order: Int, key: String): Array<Array<Int>> {
        val kMatrix = Array(order) { Array(order) { -1 } }
        var currentRow = 0
        var currentColumn = 0
        key.forEach { letter ->
            kMatrix[currentRow][currentColumn] = alphabet.indexOf(letter)
            if (currentColumn + 1 > order - 1) {
                currentColumn = 0
                currentRow ++
            } else {
                currentColumn ++
            }
        }
        return kMatrix
    }

    private fun createMArrays(mcla: String, matrixOrder: Int): List<List<Int>> {
        val indices = mutableListOf<Int>()
        mcla.forEach { letter ->
            indices.add(alphabet.indexOf(letter))
        }
        return indices.chunked(matrixOrder)
    }

    private fun canCreateASquareMatrix(key: String): Int {
        val keyLength = key.length
        if (keyLength > 1) {
            (2 until keyLength).forEach { order ->
                if(order * order == keyLength) {
                    return order
                }
            }
        }
        return -1
    }

    private fun canDecryptCriptogram(kMatrix: Array<Array<Int>>): Int{
        var determinant = calculationHelper.calculateMatrixDeterminant(kMatrix)
        println("DETERMINANTE ANTES DE APLICARLE EL MÓDULO $determinant")
        determinant = determinant % alphabet.length
        println("DETERMINANTE DESPUÉS DE APLICARLE EL MÓDULO $determinant")
        val modularInv: Int
        return if (determinant == 0) {
            -1
        } else {
            // Calcular inverso modular
            modularInv = calculationHelper.extendedEuclidesAlgoritm(determinant)
            println("INVERSO MODULAR $modularInv")
            if (modularInv == -1) {
                -2
            } else {
                modularInv
            }
        }
    }

    private fun fillMessageWithX(matrixOrder: Int, message: String): String {
        val lengthMcla = message.length
        val mod = lengthMcla % matrixOrder
        return if (mod != 0) {
            message.padEnd((matrixOrder - mod) + lengthMcla, 'X')
        } else {
            message
        }
    }
}
