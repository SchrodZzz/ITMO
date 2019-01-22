import java.io.*
import java.nio.charset.StandardCharsets


object 8 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("choose.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("choose.out", "UTF-8")

        val buf = reader.readLine().split(" ")
        val n = Integer.parseInt(buf[0])
        val k = Integer.parseInt(buf[1])
        val d = IntArray(k + 1)
        for (i in 0 until k) {
            d[i] = i + 1
        }
        d[k] = n + 1
        while (true) {
            var possOnL = true
            for (j in 0 until k - 1) {
                out.print(d[j].toString() + " ")
            }
            out.print(d[k - 1])
            out.println()
            for (j in k - 1 downTo 0) {
                if (Math.abs(d[j] - d[j + 1]) >= 2) {
                    possOnL = false
                    d[j]++
                    var temp = d[j] + 1
                    for (l in j + 1 until k) {
                        d[l] = temp
                        temp++
                    }
                    break
                }
            }
            if (possOnL) {
                break
            }
        }
        out.close()
    }

}
