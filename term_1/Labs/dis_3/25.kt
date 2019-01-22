import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter
import java.util.Arrays


object 25 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("nextchoose.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("nextchoose.out", "UTF-8")

        var buf = reader.readLine().split(" ")
        val n = Integer.parseInt(buf[0])
        val k = Integer.parseInt(buf[1])
        buf = reader.readLine().split(" ")
        var choose = IntArray(k + 1)
        val origin = IntArray(k + 1)
        for (i in 0 until k) {
            choose[i] = Integer.parseInt(buf[i])
            origin[i] = Integer.parseInt(buf[i])
        }
        choose[k] = n + 1
        origin[k] = n + 1
        choose = body(n, k, choose)
        if (Arrays.equals(origin, choose)) {
            out.print("-1")
        } else {
            for (j in 0 until k - 1) {
                out.print(choose[j].toString() + " ")
            }
            out.print(choose[k - 1])
        }
        out.close()
    }

    fun body(n: Int, k: Int, choose: IntArray): IntArray {
        for (j in k - 1 downTo 0) {
            if (choose[j + 1] - choose[j] >= 2) {
                choose[j]++
                for (l in j + 1 until k) {
                    choose[l] = choose[l - 1] + 1
                }
                break
            }
        }
        return choose
    }

}
