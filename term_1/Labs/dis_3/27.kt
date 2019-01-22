import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter


object 27 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("nextbrackets.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("nextbrackets.out", "UTF-8")

        val s = reader.readLine()
        out.println(body(s))
        out.close()
    }

    fun body(cur: String): String {
        var big = 0
        var smoll = 0
        var pivot = 0
        for (i in cur.length - 1 downTo 0) {
            if (cur[i] == '(') {
                smoll++
                if (big > smoll) {
                    pivot = i
                    break
                }
            } else {
                big++
            }
        }
        val ans = StringBuilder(cur.substring(0, pivot))
        if (ans.length == 0) {
            return "-"
        }
        ans.append(')')
        for (i in 0 until smoll) {
            ans.append('(')
        }
        for (i in 0 until big - 1) {
            ans.append(')')
        }
        return ans.toString()
    }

}
