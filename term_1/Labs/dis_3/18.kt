import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter


object 18 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("brackets2num.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("brackets2num.out", "UTF-8")

        val cont = reader.readLine().trim()
        val n = cont.length / 2
        val dsds = Array(2 * n) { LongArray(2 * n) }
        dsds[0][0] = 1
        for (j in 1 until 2 * n) {
            dsds[0][j] = 0
        }
        for (i in 1 until 2 * n) {
            for (j in 0 until 2 * n - 1) {
                if (j != 0) {
                    dsds[i][j] = dsds[i - 1][j - 1] + dsds[i - 1][j + 1]
                } else {
                    dsds[i][j] = dsds[i - 1][j + 1]
                }
            }
        }
        out.println(body(n, cont, dsds))
        out.close()
    }

    fun body(n: Int, brackets: String, d: Array<LongArray>): Long {
        var ans: Long = 0
        var dess = 1
        if (n != 1) {
            for (i in 0 until 2 * n) {
                if (brackets[i] == '(') {
                    dess++
                } else {
                    ans += d[2 * n - i - 1][dess]
                    dess--
                }
            }
        }
        return ans
    }

}
