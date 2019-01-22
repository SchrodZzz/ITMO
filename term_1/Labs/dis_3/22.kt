import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter
import java.io.IOException

object 22 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("part2num.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("part2num.out", "UTF-8")

        var data = reader.readLine()
        data += "+"
        var n = 0
        var cnt = 0
        var nibba = ""
        for (i in 0 until data.length) {
            if (Character.isDigit(data.get(i))) {
                nibba += data[i]
            } else {
                n += Integer.parseInt(nibba)
                nibba = ""
                cnt++
            }
        }

        val tmp = Array(n + 1) { IntArray(n + 1) }
        for (i in 0..n) {
            tmp[i][i] = 1
        }

        for (i in 2..n) {
            for (j in 1..n) {
                var answer = 0
                var tmm = j
                while (tmm < n && i - j > 0) {
                    answer += tmp[i - j][tmm]
                    tmm++
                }
                tmp[i][j] = if (answer > tmp[i][j]) answer else tmp[i][j]
            }
        }

        var cun = 1
        var res: Long = 0
        var la = 0
        for (i in 0 until data.length) {
            if (Character.isDigit(data.get(i))) {
                nibba += data.get(i)
            } else {
                val now = Integer.parseInt(nibba)
                while (cun < now) {
                    res += tmp[n - la][cun].toLong()
                    cun++
                }
                la += now
                nibba = ""
            }
        }
        out.println(res)
        out.close()
    }
}
