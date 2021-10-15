package playfair

/**
 * Aplica operaciones de formateo antes de aplicar el algoritmo Playfair
 * */
class PlayfairFormatter() {

    /**
     * Genera los diagramas que utilizará Playfair, en caso de que en un diagrama existan 2 letras iguales, las divide
     * y agrega una "X" entre ellas
     * */
    fun preprocessDiagrams(mcla: String): List<String> {
        val formattedMcla = mcla.replace(" ", "")
        var newMcla = ""
        var lastLetter = '-'
        formattedMcla.chunked(2).forEach { diagram ->
            if (diagram.length == 2) {
                newMcla += "${diagram[0]}"
                if (diagram[0] == diagram[1]) {
                    newMcla += "X"
                }
                newMcla += "${diagram[1]}"
                lastLetter = diagram[1]
            } else {
                if(lastLetter == diagram[0]) {
                    newMcla += "X"
                }
                newMcla += diagram[0]
            }
        }
        // Añade una "X" en caso de que falte un elemento en el último diagrama
        if(newMcla.length % 2 != 0) {
            newMcla += 'X'
        }

        // Divide la mezcla en conjuntos de 2 letras
        return newMcla.chunked(2)
    }

    /**
     * Reemplaza las Is y Js en una cadena por diagonales
     * */
    fun removeIAndJ(key: String) = key.uppercase().replace('I', '/').replace('J', '/')
}