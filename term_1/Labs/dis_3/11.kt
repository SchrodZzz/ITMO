import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter
import java.io.IOException

object 11 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("subsets.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("subsets.out", "UTF-8")

        val n = reader.readLine().toInt()
        val main = IntArray(n + 1)
        gen(n, main, 1,out)
        out.close()
    }

    fun gen(n: Int, input: IntArray, pos: Int, out: PrintWriter) {
        for (i in 1 until pos) {
            out.write(input[i].toString() + " ")
        }
        out.write("\n")
        for (i in pos..n) {
            input[pos] = i
            if (pos > 1 && input[pos - 1] >= input[pos]) continue
            gen(n, input, pos + 1, out)
        }
    }

}
