import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter


object 28 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("nextmultiperm.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("nextmultiperm.out", "UTF-8")

        val n = Integer.parseInt(reader.readLine())
        val cont = reader.readLine().split(" ")
        var multi: IntArray? = IntArray(n)
        for (i in 0 until n) {
            multi!![i] = Integer.parseInt(cont[i])
        }
        multi = nMult(n, multi!!)
        if (multi == null) {
            for (i in 0 until n) {
                out.print("0 ")
            }
        } else {
            for (i in 0 until n) {
                out.print(multi[i])
                if (i != n - 1) {
                    out.print(" ")
                }
            }
        }
        out.close()
    }

    fun nMult(n: Int, perms: IntArray): IntArray? {
        var cnt = n - 2
        while (cnt >= 0 && perms[cnt] >= perms[cnt + 1]) {
            cnt--
        }
        if (cnt >= 0) {
            var j = cnt + 1
            while (j < n - 1 && perms[cnt] < perms[j + 1]) {
                j++
            }
            var tmp = perms[cnt]
            perms[cnt] = perms[j]
            perms[j] = tmp
            var a = cnt + 1
            var b = n - 1
            while (a < b) {
                tmp = perms[a]
                perms[a] = perms[b]
                perms[b] = tmp
                a += 1
                b -= 1
            }
            return perms
        }
        return null
    }

}
