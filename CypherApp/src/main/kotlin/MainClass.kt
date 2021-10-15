import hill.HillCypher
import utils.FileHelper

/**
 * Proyecto 1
 * Materia: Criptografía
 * Integrantes:
 *      -García Ramírez Irma
 *      -García Ruiz Andrea
 *      -Miranda Cortés Yak Balam
 * */


fun main() {
        var exit: String
        do {
            val file = FileHelper()
            println("""
                __^__                                             __^__
               ( ___ )-------------------------------------------( ___ )
                | / |           CRIPTOGRAFÍA: PROYECTO 1          | \ |
                | / |  TRANSPOSICIÓN POR SERIES, HILL Y PLAYFAIR  | \ |
                |___|                                             |___|     
                |___|                  EQUIPO 7                   |___|
                |___|                                             |___|   
                (_____)-------------------------------------------(_____) 
            """.trimIndent())
            println("Encriptar = 1, Desencriptar = 2")
            val isDecrypting = readLine()?.toInt() ?: 1
            val cypherManager = CypherManager()
            println("Escribe la ruta de tu archivo:")
            val route = readLine() ?: ""
            val msg = file.readFile(route)
            if (msg != "") {
                println("TRANSPOSICIÓN POR SERIES----------------------------")
                print("Número de funciones: ")
                val numFunctions = readLine()?.toInt() ?: 3
                println("""Menú para generar funciones:
                1) Números naturales
                2) Números pares
                3) Números impares
                4) Múltiplos
                5) Números primos
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
                println("HILL--------------------------------------------")
                println("Llave a usar en el cifrado Hill:")
                val hillKey = readLine() ?: ""
                println("----------------------------------------------------")
                println("PLAYFAIR--------------------------------------------")
                println("Llave a usar en el cifrado Playfair:")
                val playfairKey = readLine() ?: ""
                println("----------------------------------------------------")
                println("Indique la ruta y el nombre de su archivo resultante:")
                val path = readLine() ?: "resultado"
                cypherManager.encryptOrDecrypt(isDecrypting == 2, msg, playfairKey, hillKey, functions, path)
            }
            println("\n\nTECLEA SALIR (EN MAYÚSCULAS) PARA FINALIZAR O CUALQUIER OTRA PALABRA PARA CONTINUAR: ")
            exit = readLine() ?: "SALIR"
        } while (exit != "SALIR")
}
