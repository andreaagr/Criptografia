fun main() {
    // SERIES, HILL Y PLAYFAIR
    println("""
          __^__                                      __^__
         ( ___ )------------------------------------( ___ )
          | / |                                      | \ |
          | / |        CRIPTOGRAFÍA: PROYECTO 1      | \ |
          |___|                                      |___|
         (_____)------------------------------------(_____) 
    """.trimIndent())
    println("Encriptar = 1, Desencriptar = 2")
    val isDecrypting = readLine()?.toInt() ?: 1
    val cypherManager = CypherManager()
    println("Mensaje:")
    val msg = readLine() ?: ""
    println("TRANSPOSICIÓN POR SERIES----------------------------")
    print("Número de funciones: ")
    val numFunctions = readLine()?.toInt() ?: 3
    println(
        """Menú para generar funciones:
            1) Números naturales
            2) Números pares
            3) Números impares
            4) Múltiplos
            5) Números primos
            6) Otro
    """)
    val functions = mutableListOf<Pair<Int,Int>>()
    var option: Int
    var mult = 2
    for (num in 0 until numFunctions) {
        println("Función ${num+1}:")
        print("Ingrese la opción de función a generar: ")
        option = readLine()?.toInt() ?: 1
        if (option == 4) {
            print("Valor del que se generaron los múltiplos: ")
            mult = readLine()?.toInt() ?: 2
        }
        functions.add(Pair(option-1, mult))
    }
    println("----------------------------------------------------")
    println("PLAYFAIR--------------------------------------------")
    println("Llave a usar en el cifrado Playfair:")
    val playfairKey = readLine() ?: ""
    println("----------------------------------------------------")
    println("HILL--------------------------------------------")
    println("Llave a usar en el cifrado Hill:")
    val hillKey = readLine() ?: ""
    println("----------------------------------------------------")
    cypherManager.encryptOrDecrypt(isDecrypting == 2, msg, playfairKey, hillKey, functions)
}