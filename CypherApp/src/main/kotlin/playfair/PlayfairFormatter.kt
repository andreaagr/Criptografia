package playfair

class PlayfairFormatter() {
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

        if(newMcla.length % 2 != 0) {
            newMcla += 'X'
        }

        return newMcla.chunked(2)
    }

    fun removeIAndJ(key: String) = key.uppercase().replace('I', '/').replace('J', '/')
}