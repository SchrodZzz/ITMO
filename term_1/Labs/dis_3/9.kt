import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter


object 9 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("brackets.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("brackets.out", "UTF-8")

        val n = Integer.parseInt(reader.readLine())
        var sb = StringBuilder("")
        for (i in 0 until n) {
            sb.append('(')
        }
        for (i in 0 until n) {
            sb.append(')')
        }
        out.println(sb.toString())
        while (redun(sb.toString()) != "nope") {
            sb = StringBuilder(redun(sb.toString()))
            out.println(sb)
        }
        out.close()
    }

    fun redun(s: String): String {
        var big = 0
        var smoll = 0
        var pivot = 0
        for (i in s.length - 1 downTo 0) {
            if (s[i] == '(') {
                smoll++
                if (big > smoll) {
                    pivot = i
                    break
                }
            } else {
                big++
            }
        }
        val next = StringBuilder(s.substring(0, pivot))
        if (next.isEmpty()) {
            return "nope"
        }
        next.append(')')
        for (i in 0 until smoll) {
            next.append('(')
        }
        for (i in 0 until big - 1) {
            next.append(')')
        }
        return next.toString()
    }
}
