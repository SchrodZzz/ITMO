import java.io.*
import java.nio.charset.StandardCharsets
 
object 7 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("permutations.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("permutations.out", "UTF-8")

        val n = Integer.parseInt(reader.readLine())
        val permutation = IntArray(n)
        var i: Int
        i = 0
        while (i < n) {
            permutation[i] = i + 1
            i++
        }
        do {
            for (z in 0 until n) {
                out.print(permutation[z].toString() + " ")
            }
            out.println()
            i = n - 2
            while (i >= 0 && permutation[i] >= permutation[i + 1]) {
                i--
            }
            if (i >= 0) {
                var j = i + 1
                while (j < n - 1 && permutation[i] < permutation[j + 1]) {
                    j++
                }
                val temp = permutation[i]
                permutation[i] = permutation[j]
                permutation[j] = temp
            }
            var j = i + 1
            var k = n - 1
            while (j < k) {
                val temp = permutation[j]
                permutation[j] = permutation[k]
                permutation[k] = temp
                j += 1
                k -= 1
            }
        } while (i >= 0)
        out.close()
    }

}
