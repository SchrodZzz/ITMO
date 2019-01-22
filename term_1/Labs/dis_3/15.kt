import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter
import java.util.ArrayList


object 15 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("num2choose.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("num2choose.out", "UTF-8")

        val cnotainer = reader.readLine().split(" ")
        val n = Integer.parseInt(cnotainer[0])
        val k = Integer.parseInt(cnotainer[1])
        val m = java.lang.Long.parseLong(cnotainer[2])
        for (number in progBodyCh(n, k, m)) {
            out.print(number.toString() + " ")
        }
        out.close()
    }

    fun sciFi(n: Long): Long {
        var tmp: Long = 1
        for (i in 2..n) {
            tmp *= i
        }
        return tmp
    }

    fun progBodyCh(a: Int, b: Int, c: Long): ArrayList<Int> {
        var tmpa = a
        var tmpb = b
        var tmpc = c
        val choose = ArrayList<Int>()
        var nn = 1
        while (tmpb > 0) {
            val temp = chooo(tmpa - tmpb + 1, tmpa - 1, tmpb - 1)
            if (tmpc < temp) {
                choose.add(nn)
                tmpb--
            } else {
                tmpc -= temp
            }
            tmpa--
            nn++
        }
        return choose
    }

    fun chooo(l: Int, r: Int, div: Int): Long {
        var ans: Long = 1
        for (i in l..r) {
            ans *= i.toLong()
        }
        ans /= sciFi(div.toLong())
        return ans
    }

}
