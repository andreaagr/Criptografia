class MatrixHelper {
    fun printMatrix(matrix: Array<Array<Char>>) {
        for(row in matrix) {
            for (character in row) {
                print("$character\t")
            }
            print("\n")
        }
    }

    fun printMatrix(matrix: Array<Array<Int>>) {
        for(row in matrix) {
            for (character in row) {
                print("$character\t")
            }
            print("\n")
        }
    }

    fun transponeMatrix(matrix: Array<Array<Int>>): Array<Array<Int>> {
        val transposed = Array(matrix[0].size) { Array(matrix[0].size) { -1 } }
        (matrix.indices).forEach { indexR ->
            (matrix.indices).forEach { indexC ->
                transposed[indexR][indexC] = matrix[indexC][indexR]
            }
        }
        return transposed
    }

    fun multiplyMatrixWithEscalar(matrix: Array<Array<Int>>, escalar: Int): Array<Array<Int>> {
        val result = Array(matrix[0].size) { Array(matrix[0].size) { -1 } }
        (matrix.indices).forEach { indexR ->
            (matrix.indices).forEach { indexC ->
                result[indexR][indexC] = matrix[indexR][indexC] * escalar
            }
        }
        return result
    }
}