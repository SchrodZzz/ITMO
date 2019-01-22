import java.io.*
import java.nio.charset.StandardCharsets

object 1 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("allvectors.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("allvectors.out", "UTF-8")

        val n = Integer.parseInt(reader.readLine())
        val pattern = Integer.toString(worker(n) - 1, 2)

        for (i in 0 until worker(n)) {
            var output = StringBuilder(Integer.toString(i, 2))
            val addition = StringBuilder()
            for (j in 0 until pattern.length - output.length) {
                addition.append(0)
            }
            output = addition.append(output)
            out.write(output.toString() + "\n")
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
