import java.io.*
import java.nio.charset.StandardCharsets
import java.util.ArrayList



object 2 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("gray.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("gray.out", "UTF-8")

        val n = Integer.parseInt(reader.readLine())
        val vals = ArrayList<String>()
        vals.add(StringBuilder().append(0).toString())
        vals.add(StringBuilder().append(1).toString())

        for (j in 0 until n - 1) {
            for (i in vals.indices.reversed()) {
                vals.add(vals[i])
            }
            for (i in 0 until vals.size / 2) {
                vals[i] = StringBuilder("0").append(vals[i]).toString()
            }
            for (i in vals.size / 2 until vals.size) {
                vals[i] = StringBuilder("1").append(vals[i]).toString()
            }
        }

        for (i in vals.indices) {
            out.write(vals[i] + "\n")
        }

        out.close()
    }
}
