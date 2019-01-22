import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter


object 19 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("num2brackets2.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("num2brackets2.out", "UTF-8")

        val cont = reader.readLine().split(" ")
        val n = Integer.parseInt(cont[0])
        val k = java.lang.Long.parseLong(cont[1])
        val dsds = Array(2 * n + 1) { LongArray(n + 1) }
        dsds[0][0] = 1
        for (i in 0 until 2 * n) {
            for (j in 0..n) {
                if (j + 1 <= n) {
                    dsds[i + 1][j + 1] += dsds[i][j]
                }
                if (j > 0) {
                    dsds[i + 1][j - 1] += dsds[i][j]
                }
            }
        }
        out.println(body(n, k + 1, dsds))
        out.close()
    }

    fun body(n: Int, k: Long, d: Array<LongArray>): String {
        var tmpK = k
        var glubina = 0
        val ans = StringBuilder("")
        val pudge = CharArray(2 * n)
        var cnt = 0
        if (tmpK == 0L) {
            for (i in 0 until n) {
                ans.append('(')
            }
            for (i in 0 until n) {
                ans.append(')')
            }
        } else {
            for (i in 2 * n - 1 downTo 0) {
                var temp: Long = 0
                if (glubina + 1 <= n) {
                    temp = d[i][glubina + 1] * Math.pow(2.0, ((i - glubina - 1) / 2).toDouble()).toLong()
                }
                if (temp >= tmpK) {
                    ans.append('(')
                    pudge[cnt++] = '('
                    glubina++
                    continue
                }
                tmpK -= temp
                if (cnt > 0 && pudge[cnt - 1] == '(' && glubina >= 1) {
                    temp = d[i][glubina - 1] * Math.pow(2.0, ((i - glubina + 1) / 2).toDouble()).toLong()
                } else {
                    temp = 0
                }
                if (temp >= tmpK) {
                    ans.append(')')
                    cnt--
                    glubina--
                    continue
                }
                tmpK -= temp
                if (glubina + 1 <= n) {
                    temp = d[i][glubina + 1] * Math.pow(2.0, ((i - glubina - 1) / 2).toDouble()).toLong()
                } else {
                    temp = 0
                }
                if (temp >= tmpK) {
                    ans.append('[')
                    pudge[cnt++] = '['
                    glubina++
                    continue
                }
                tmpK -= temp
                ans.append(']')
                cnt--
                glubina--
            }
        }
        return ans.toString()
    }

}
