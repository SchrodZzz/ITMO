import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter
import java.util.ArrayList


object 14 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("perm2num.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("perm2num.out", "UTF-8")

        val n = Integer.parseInt(reader.readLine())
        val conteiner = reader.readLine().split(" ")
        val permss = ArrayList<Int>()
        for (i in 0 until n) {
            permss.add(Integer.parseInt(conteiner[i]))
        }
        out.println(perms(permss, n))
        out.close()
    }

    fun sciFi(n: Long): Long {
        var tmp: Long = 1
        for (i in 2..n) {
            tmp *= i
        }
        return tmp
    }

    fun perms(tmp: ArrayList<Int>, n: Int): Long {
        var num: Long = 0
        val was = BooleanArray(n + 1)
        for (i in 1..n) {
            for (j in 1 until tmp[i - 1]) {
                if (!was[j]) {
                    num += sciFi((n - i).toLong())
                }
            }
            was[tmp[i - 1]] = true
        }
        return num
    }
}
