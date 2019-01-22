import java.io.*
import java.nio.charset.StandardCharsets
import java.util.HashSet




object 4 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("chaincode.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("chaincode.out", "UTF-8")

        val n = Integer.parseInt(reader.readLine())
        val container = HashSet<String>()

        val ans = arrayOfNulls<String>(worker(n))
        for (i in 0 until worker(n)) {
            ans[i] = ""
        }
        for (i in 0 until n) {
            ans[0] += "0"
        }
        out.write(ans[0] + "\n")
        for (i in 1 until worker(n)) {
            ans[i] = ans[i - 1]!!.substring(1, ans[i - 1]!!.length)
            val patternOne = ans[i] + "1"
            val patternZero = ans[i] + "0"

            if (!container.contains(patternOne)) {
                ans[i] = StringBuilder(ans[i]).append(1).toString()
                out.write(ans[i] + "\n")
                container.add(ans[i].toString())
            } else if (!container.contains(patternZero)) {
                ans[i] = StringBuilder(ans[i]).append(0).toString()
                out.write(ans[i] + "\n")
                container.add(ans[i].toString())
            }
        }

        out.close()
    }

    internal fun worker(n: Int): Int {
        var tmpAns = 1
        for (i in 0 until n) {
            tmpAns *= 2
        }
        return tmpAns
    }
}
