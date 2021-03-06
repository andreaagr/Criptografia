package utils

import java.io.File

/**
 * Esta clase nos permite realizar la lectura y escritura en archivos
 * */

class FileHelper {
    fun readFile(path: String): String {
        return try {
            val myFile = File(path)
            val lines = myFile.readLines()
            var content = ""
            lines.forEach { line ->
                content += line
            }
            content
        } catch (error: Throwable) {
            println(error.message)
            ""
        }
    }

    fun writeFile(path: String, message: String) {
        File(path).printWriter().use { out -> out.println(message) }
    }
}