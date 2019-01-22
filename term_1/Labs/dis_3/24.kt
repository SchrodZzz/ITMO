import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter
import java.util.Arrays


object 24 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("nextperm.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("nextperm.out", "UTF-8")

        val n = Integer.parseInt(reader.readLine())
        val cont = reader.readLine().split(" ")
        val ans = IntArray(n)
        val input = IntArray(n)
        val end = IntArray(n)
        for (i in 0 until n) {
            ans[i] = Integer.parseInt(cont[i])
            input[i] = i + 1
            end[i] = n - i
        }
        var pr = IntArray(n)
        var ne = IntArray(n)
        for (i in 0 until n) {
            pr[i] = ans[i]
            ne[i] = ans[i]
        }
        pr = prev(n, pr)
        if (Arrays.equals(end, pr)) {
            for (i in 0 until n) {
                out.print("0 ")
            }
        } else {
            for (i in 0 until n) {
                out.print(pr[i].toString() + " ")
            }
        }
        out.println()
        ne = next(n, ne)
        if (Arrays.equals(input, ne)) {
            for (i in 0 until n) {
                out.print("0 ")
            }
        } else {
            for (i in 0 until n) {
                out.print(ne[i].toString() + " ")
            }
        }
        out.close()
    }

    fun prev(n: Int, ans: IntArray): IntArray {
        var conter = n - 2
        while (conter >= 0 && ans[conter] <= ans[conter + 1]) {
            conter--
        }
        if (conter >= 0) {
            var cnt = conter + 1
            while (cnt < n - 1 && ans[conter] > ans[cnt + 1]) {
                cnt++
            }
            val tmp = ans[conter]
            ans[conter] = ans[cnt]
            ans[cnt] = tmp
        }
        var cnnt = conter + 1
        var koef = n - 1
        while (cnnt < koef) {
            val temp = ans[cnnt]
            ans[cnnt] = ans[koef]
            ans[koef] = temp
            cnnt += 1
            koef -= 1
        }
        return ans
    }

    fun next(n: Int, ans: IntArray): IntArray {
        var conter = n - 2
        while (conter >= 0 && ans[conter] >= ans[conter + 1]) {
            conter--
        }
        if (conter >= 0) {
            var cnt = conter + 1
            while (cnt < n - 1 && ans[conter] < ans[cnt + 1]) {
                cnt++
            }
            val tmp = ans[conter]
            ans[conter] = ans[cnt]
            ans[cnt] = tmp
        }
        var cnnt = conter + 1
        var koef = n - 1
        while (cnnt < koef) {
            val temp = ans[cnnt]
            ans[cnnt] = ans[koef]
            ans[koef] = temp
            cnnt += 1
            koef -= 1
        }
        return ans
    }

}
