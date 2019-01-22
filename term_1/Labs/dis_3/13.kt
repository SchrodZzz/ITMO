import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter
import java.util.ArrayList


object 13 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("num2perm.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("num2perm.out", "UTF-8")

        val container = reader.readLine().split(" ")
        val n = Integer.parseInt(container[0])
        val k = java.lang.Long.parseLong(container[1])
        val toAnsPr = perms(n, k)
        for (i in toAnsPr.indices) {
            out.print(toAnsPr[i])
            if (i != toAnsPr.size - 1) {
                out.print(" ")
            }
        }
        out.close()
    }

    fun sciFi(n: Long): Long {
        var ans: Long = 1
        for (i in 2..n) {
            ans *= i
        }
        return ans
    }

    fun perms(n: Int, k: Long): ArrayList<Int> {
        var k = k
        val permutation = ArrayList<Int>()
        val was = BooleanArray(n + 1)
        for (i in 1..n) {
            for (j in 1..n) {
                if (was[j]) {
                    continue
                } else {
                    if (k >= sciFi((n - i).toLong())) {
                        k -= sciFi((n - i).toLong())
                        continue
                    } else {
                        permutation.add(j)
                        was[j] = true
                        break
                    }
                }
            }
        }
        return permutation
    }
}
