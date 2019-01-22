import java.io.*
import java.nio.charset.StandardCharsets
import java.util.ArrayList




object 6 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("vectors.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("vectors.out", "UTF-8")

        val conteiner = reader.readLine().split(" ")
        val n = Integer.parseInt(conteiner[0])
        val pat = Integer.toString(worker(n) - 1, 2)
        val container = ArrayList<String>()

        for (i in 0 until worker(n)) {
            var f = false
            var output = StringBuilder(Integer.toString(i, 2))
            for (j in 1 until output.length) {
                if (output[j] == '1' && output[j - 1] == '1') {
                    f = true
                    continue
                }
            }
            if (f) continue
            val addition = StringBuilder()
            for (j in 0 until pat.length - output.length) {
                addition.append(0)
            }
            output = addition.append(output)
            container.add(output.toString())
        }

        out.write(container.size.toString() + "\n")
        for (i in container.indices) {
            out.write(container[i] + "\n")
        }
        out.close()
    }

    fun worker(n: Int): Int {
        var a = 1
        for (i in 0 until n) {
            a *= 2
        }
        return a
    }
}
