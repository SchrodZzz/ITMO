import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter
import java.util.ArrayList

object 10 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("partition.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("partition.out", "UTF-8")

        val n = Integer.parseInt(reader.readLine())
        val frst = ArrayList<Int>()
        for (i in 0 until n - 1) {
            frst.add(1)
            out.print(1.toString() + "+")
        }
        frst.add(1)
        out.print(1)
        out.println()
        var afer: ArrayList<Int>
        afer = cnt(frst)
        while (afer.size != 1) {
            for (i in 0 until afer.size - 1) {
                out.print(afer[i].toString() + "+")
            }
            out.print(afer[afer.size - 1])
            out.println()
            afer = cnt(frst)
        }
        out.print(n)
        out.close()
    }

    fun cnt(ans: ArrayList<Int>): ArrayList<Int> {
        ans[ans.size - 1] = ans[ans.size - 1] - 1
        ans[ans.size - 2] = ans[ans.size - 2] + 1
        if (ans[ans.size - 2] > ans[ans.size - 1]) {
            ans[ans.size - 2] = ans[ans.size - 2] + ans[ans.size - 1]
            ans.removeAt(ans.size - 1)
        } else {
            while (ans[ans.size - 2] * 2 <= ans[ans.size - 1]) {
                val temp = ans[ans.size - 1] - ans[ans.size - 2]
                ans.removeAt(ans.size - 1)
                ans.add(ans[ans.size - 1])
                ans.add(temp)
            }
        }
        return ans
    }

}
