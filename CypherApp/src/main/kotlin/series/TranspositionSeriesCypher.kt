package series

class TranspositionSeriesCypher {
    private var mcla = ""

    private var availableIndices = mutableListOf<Int>()

    fun startTransposicion(mclaOrCriptogram: String, functions: List<List<Int>>, isDecrypting: Boolean): String {
        return if (isDecrypting) {
            decrypt(mclaOrCriptogram, functions)
        } else {
            encrypt(mclaOrCriptogram, functions)
        }
    }

    private fun encrypt(m: String, functions: List<List<Int>>): String {
        availableIndices = MutableList(m.length) { -1 }
        mcla = m.uppercase().replace(" ", "")
        var criptogram = ""
        println("Funciones: ")
        functions.forEach { function ->
            println(function)
            criptogram += generateSubmessage(function)
        }
        return criptogram
    }

    private fun decrypt(criptogram: String, functions: List<List<Int>>): String {
        mcla = criptogram.uppercase().replace(" ", "")
        val indices = mutableSetOf<Int>()
        functions.forEach { function ->
            function.forEach { index ->
                indices.add(index - 1)
            }
        }
        return generateMcla(indices)
    }

    private fun generateMcla(indices: Set<Int>): String {
        var result = ""
        (mcla.indices).forEach { index ->
            result += mcla[indices.indexOf(index)]
        }
        return result
    }

    private fun generateSubmessage(function: List<Int>): String {
        var submessage = ""
        function.forEach { index ->
            val realIndex = index - 1
            if (availableIndices[realIndex] == -1) {
                submessage += mcla[realIndex]
                availableIndices[realIndex] = 0
            }
        }
        return submessage
    }

    private fun isPrime(number: Int): Boolean {
        val divisors = mutableListOf<Int>()
        (1..number).forEach { divisor ->
            if (number % divisor == 0) {
                divisors.add(divisor)
            }
        }
        return divisors.size == 2
    }

    fun createFunction(type: Int, size: Int, multiple: Int = 1): List<Int> {
        return (1..size).filter { number ->
            when (type) {
                // Números naturales
                0 -> number >= 0
                // Números pares
                1 -> number % 2 == 0
                // Números impares
                2 -> number % 2 != 0
                // Múltiplo de un número
                3 -> number % multiple == 0
                // Número primo
                else -> isPrime(number)
            }
        }
    }
}