import java.io.*
import java.nio.charset.StandardCharsets


object 5 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("telemetry.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("telemetry.out", "UTF-8")

        val conteiner = reader.readLine().split(" ")
        val n = Integer.parseInt(conteiner[0])
        val k = Integer.parseInt(conteiner[1])
        val d = IntArray(n)
        val m = IntArray(n)
        val tmp = IntArray(n)
        for (j in 0 until n) {
            d[j] = 0
            m[j] = 1
            tmp[j] = 1
        }
        var counter = 0
        while (counter < Math.pow(k.toDouble(), n.toDouble())) {
            for (j in 0 until n) {
                out.print(d[j])
            }
            out.println()
            for (j in 0 until n) {
                if (tmp[j] == Math.pow(k.toDouble(), j.toDouble()).toInt()) {
                    d[j] += 1 * m[j]
                    if (d[j] == k) {
                        d[j] = k - 1
                        m[j] = -1
                    }
                    if (d[j] == -1) {
                        d[j] = 0
                        m[j] = 1
                    }
                    tmp[j] = 1
                } else {
                    tmp[j]++
                }
            }
            counter++
        }
        out.close()
    }
}
