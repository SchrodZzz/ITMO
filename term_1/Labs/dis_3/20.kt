import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter
import java.io.IOException
import java.math.BigInteger

object 20 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("brackets2num2.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("brackets2num2.out", "UTF-8")

        val data = reader.readLine()
        val bras = "()[]"
        val n = data.length / 2

        val nol = BigInteger.ZERO
        val odin = BigInteger.ONE
        val arr = Array<Array<BigInteger?>>(n * 2 + 1) { arrayOfNulls(n * 2 + 1) }

        var conter = -1
        while (++conter < 2 * n + 1) {
            var j = -1
            while (++j < 2 * n + 1) {
                arr[conter][j] = nol
            }
        }

        arr[0][0] = odin
        conter = -1

        while (++conter < 2 * n + 1) {
            var j = -1
            while (++j < 2 * n + 1) {
                if (j > 0 && conter > 0) {
                    arr[conter][j] = arr[conter][j]!!.add(arr[conter - 1][j - 1])
                }
                if (j < n * 2 && conter > 0) {
                    arr[conter][j] = arr[conter][j]!!.add(arr[conter - 1][j + 1])
                }
            }
        }

        var passion = 0
        val cur = CharArray(n * 2)
        var result = BigInteger("0")
        conter = n * 2
        while (--conter >= 0) {
            if (data[n * 2 - conter - 1] == '(') {
                cur[passion++] = bras[0]
            } else {
                result = result.add(arr[conter][passion + 1]!!.shiftLeft((conter - passion - 1) / 2))
                if (passion > 0 && cur[passion - 1] == '('
                        && data[n * 2 - 1 - conter] == ')') {
                    passion--
                } else {
                    if (passion > 0 && cur[passion - 1] == '(') {
                        result = result.add(arr[conter][passion - 1]!!.shiftLeft((conter - passion + 1) / 2))
                    }
                    if (data[2 * n - conter - 1] == '[') {
                        cur[passion++] = bras[2]
                    } else {
                        result = result.add(arr[conter][passion + 1]!!.shiftLeft((conter - passion-- - 1) / 2))
                    }
                }
            }
        }
        out.println(result)
        out.close()
    }
}
