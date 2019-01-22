import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter
import java.util.ArrayList

object 16 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("choose2num.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("choose2num.out", "UTF-8")

        var container = reader.readLine().split(" ")
        val n = Integer.parseInt(container[0])
        val k = Integer.parseInt(container[1])
        val ch = ArrayList<Int>()
        ch.add(0)
        container = reader.readLine().split(" ")
        for (i in 0 until k) {
            ch.add(Integer.parseInt(container[i]))
        }
        out.print(body(ch, n, k))
        out.close()
    }

    fun form(n: Long): Long {
        var ans: Long = 1
        for (i in 2..n) {
            ans *= i
        }
        return ans
    }

    fun body(choose: ArrayList<Int>, n: Int, k: Int): Long {
        var num: Long = 0
        for (i in 1..k) {
            for (j in choose[i - 1] + 1 until choose[i]) {
                num += switch(n - j - k + i + 1, n - j, k - i)
            }
        }
        return num
    }

    fun switch(begin: Int, end: Int, divider: Int): Long {
        var ans: Long = 1
        for (i in begin..end) {
            ans *= i.toLong()
        }
        ans /= form(divider.toLong())
        return ans
    }

}
