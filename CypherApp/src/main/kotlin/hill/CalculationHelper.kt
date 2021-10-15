package hill

import kotlin.math.abs
import kotlin.math.pow

/**
 * Esta clase nos ayuda con los cálculos involucrados en el cifrado Hill como son:
 * cálculo de determinantes, matriz inversa modular e inverso multiplicativo modular
 * */

class CalculationHelper {

    /**
     * Cálcula el determinante de una matriz
     * */
    fun calculateMatrixDeterminant(kMatrix: Array<Array<Int>>): Int {
        var determinant = 0
        var a: Int
        var sign: Int
        var subdeterminant: Int
        kMatrix[0].forEachIndexed { index, _ ->
            a = kMatrix[0][index]
            sign = (-1F).pow(1 + (index + 1)).toInt()
            subdeterminant = calculateSubDeterminant(getCofactorsMatrix(kMatrix, 0, index))
            determinant += (sign*a*subdeterminant)
        }
        return determinant
    }

    /**
     * Aplica la operación módulo a todos los elementos de la matriz de entrada
     * */
    fun applyModMatrixOp(matrix: Array<Array<Int>>, mod: Int): Array<Array<Int>> {
        val result = Array(matrix[0].size) { Array(matrix[0].size) { -1 } }
        var subResult: Int
        (matrix.indices).forEach { indexR ->
            (matrix.indices).forEach { indexC ->
                subResult = matrix[indexR][indexC] % mod
                if (subResult < 0) {
                    subResult += mod
                }
                result[indexR][indexC] = subResult
            }
        }
        return result
    }

    /**
     * Crea la matriz de cofactores para la matriz de entrada
     * */
    fun createCofactorsMatrix(kMatrix: Array<Array<Int>>): Array<Array<Int>> {
        val cofactorsMatrix = Array(kMatrix[0].size) { Array(kMatrix[0].size) { -1 } }
        kMatrix.forEachIndexed { indexR, row ->
            row.forEachIndexed { indexC, _ ->
                cofactorsMatrix[indexR][indexC] = calculateSubDeterminant(
                    getCofactorsMatrix(kMatrix, indexR, indexC)
                ) * (-1F).pow((indexR + 1) + (indexC + 1)).toInt()
            }
        }
        return cofactorsMatrix
    }

    /**
     * Reduce la matriz del orden indicado a matrices de orden 2
     * */
    private fun calculateSubDeterminant(subKMatrix: Array<Array<Int>>): Int {
        val order = subKMatrix[0].size; var currentOrder = order
        val allCofactorsArrays = mutableListOf(subKMatrix)
        val cofactorsArraysTemp = mutableListOf<Array<Array<Int>>>()
        val m = mutableListOf<Int>()
        while (currentOrder > 2) {
            allCofactorsArrays.forEach { matrix ->
                matrix[0].forEachIndexed { indexR, r ->
                    if (currentOrder > 2) {
                        m.add(r*((-1F).pow(indexR + 2).toInt()))
                    }
                    val newM = getCofactorsMatrix(matrix, 0, indexR)
                    cofactorsArraysTemp.add(newM)
                }
            }
            allCofactorsArrays.apply {
                clear()
                addAll(cofactorsArraysTemp)
            }

            cofactorsArraysTemp.clear()

            currentOrder --
        }
        return calculateFormulaElementMatrix2x2 (allCofactorsArrays, m)
    }

    /**
     * Genera la matriz de cofactores para el índice especificado
     * */
    private fun getCofactorsMatrix(kMatrix: Array<Array<Int>>, currentRow: Int, currentColumn: Int): Array<Array<Int>>{
        val order = kMatrix[0].size
        val cofactorsMatrix = Array(order - 1) { Array(order - 1) { -1 } }
        var countRows = 0
        kMatrix.forEachIndexed { indexR, row ->
            if (indexR != currentRow) {
                var countColumns = 0
                row.forEachIndexed { indexC, _ ->
                    if (indexC != currentColumn) {
                        cofactorsMatrix[countRows][countColumns] = kMatrix[indexR][indexC]
                        countColumns ++
                    }
                }
                countRows ++
            }
        }
        countRows = 0
        return cofactorsMatrix
    }

    /**
     * Cálcula el determinante realizando la suma de los resultados parciales al aplicar el método de cofactores
     * */
    private fun calculateFormulaElementMatrix2x2 (
        cofactorsArrays: List<Array<Array<Int>>>,
        factors: List<Int>
    ): Int {
        var subDeterminant = 0
        cofactorsArrays.forEachIndexed { index, matrix2x2 ->
            subDeterminant += if (factors.isNotEmpty()) {
                calculateSubMatrixDeterminant(matrix2x2, factors[index])
            } else {
                calculateSubMatrixDeterminant(matrix2x2, 1)
            }
        }
        return subDeterminant
    }

    /**
     * Calcula el determinante de una matriz cuadrada de orden 2
     * */
    private fun calculateSubMatrixDeterminant(matrix2x2: Array<Array<Int>>, a: Int): Int {
        return ((matrix2x2[0][0]*matrix2x2[1][1]) - (matrix2x2[1][0]*matrix2x2[0][1]))*a
    }

    /**
     *Aplica el algoritmo extendido de Euclides para obtener el inverso multiplicativo
     * */
    fun extendedEuclidesAlgoritm(determinant: Int, alphabetSize: Int = 27): Int {
        return if (determinant > alphabetSize) {
            getReverse(determinant, alphabetSize)
        } else {
            getReverse(alphabetSize, determinant)
        }
    }

    /**
     * Obtiene el inverso multiplicativo de los 2 números especificados, basandose en la fórmula:
     *
     * sa + tb = mcd(max, min)
     * c = mcd
     * t = c2/(getSign(min))*(getSign(c))
     * s = c1/(getSign(max))*(getSign(c))
     * */
    private fun getReverse(max: Int, min: Int): Int {
        var c = abs(max); var d = abs(min)
        var c1 = 1; var d1 = 0
        var c2 = 0; var d2 = 1
        while (d != 0) {
            val q = c.div(d); val r = c - (q*d)
            val r1 = c1 - (q*d1); val r2 = c2 - (q*d2)
            c = d; c1 = d1; c2 = d2
            d = r; d1 = r1; d2 = r2
        }
        return c2/(getSign(min))*(getSign(c))
    }

    /**
     * Obtiene el signo del número indicado
     * */
    private fun getSign(number: Int): Int {
        return if(number < 0) {
            -1
        } else {
            1
        }
    }
}