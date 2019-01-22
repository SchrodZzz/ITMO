import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter
import java.io.IOException


object 21 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("num2part.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("num2part.out", "UTF-8")

        val cont = reader.readLine().split(" ")
        val n = cont[0].toInt()
        var r = cont[1].toLong()+1

        val ddsa = Array(n + 1) { IntArray(n + 1) }
        for (i in 0..n) {
            ddsa[i][i] = 1
        }

        for (i in 2..n) {
            for (j in 1..n) {
                var answer = 0
                var conter = j
                while (conter < n && i - j > 0) {
                    answer += ddsa[i - j][conter]
                    conter++
                }
                ddsa[i][j] = if (answer > ddsa[i][j]) answer else ddsa[i][j]
            }
        }

        val strToAns = StringBuilder()
        var plus = 0
        var current = plus + 1
        while (current <= n && r > 0) {
            if (ddsa[n - plus][current] < r) {
                r -= ddsa[n - plus][current++].toLong()
            } else if (ddsa[n - plus][current] >= r) {
                strToAns.append(current).append("+")
                plus += current
            }
        }
        out.write(strToAns.toString().substring(0, strToAns.length - 1))
        out.close()
    }
}
