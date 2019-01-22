import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter
 
object 17 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("num2brackets.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("num2brackets.out", "UTF-8")

        val cont = reader.readLine().split(" ")
        val n = Integer.parseInt(cont[0])
        val k = java.lang.Long.parseLong(cont[1])
        val dub = Array(2 * n) { LongArray(n + 1) }
        dub[0][0] = 1
        for (i in 1 until 2 * n) {
            for (j in 0 until n) {
                if (j != 0) {
                    dub[i][j] = dub[i - 1][j - 1] + dub[i - 1][j + 1]
                } else {
                    dub[i][j] = dub[i - 1][j + 1]
                }
            }
        }
        out.println(body(n, k, dub))
        out.close()
    }

    fun body(n: Int, k: Long, d: Array<LongArray>): String {
        var tmpK = k
        var glubina = 0
        val ans = StringBuilder("")
        if (tmpK == 0L) {
            for (i in 0 until n) {
                ans.append('(')
            }
            for (i in 0 until n) {
                ans.append(')')
            }
        } else {
            for (i in 0 until 2 * n) {
                if (glubina + 1 <= n && d[2 * n - i - 1][glubina + 1] >= tmpK) {
                    ans.append('(')
                    glubina++
                } else if (glubina + 1 <= n && d[2 * n - i - 1][glubina + 1] < tmpK) {
                    ans.append(')')
                    tmpK -= d[2 * n - i - 1][glubina + 1]
                    glubina--
                }
            }
        }
        return ans.toString()
    }

}
