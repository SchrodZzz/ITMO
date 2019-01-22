import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter
import java.util.ArrayList


object 29 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("nextpartition.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("nextpartition.out", "UTF-8")

        val cont = reader.readLine().split("=")
        val l = cont[0]
        val r = cont[1]
        if (l.length == r.length) {
            out.println("No solution")
        } else {
            val before = ArrayList<Int>()
            val part = r.split("[^0-9]".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            for (i in part.indices) {
                before.add(Integer.parseInt(part[i]))
            }
            out.print("$l=")
            val answer = runner(before)
            for (i in answer.indices) {
                out.print(answer[i])
                if (i != answer.size - 1) {
                    out.print("+")
                }
            }
        }
        out.close()
    }

    fun runner(tmp: ArrayList<Int>): ArrayList<Int> {
        tmp[tmp.size - 1] = tmp[tmp.size - 1] - 1
        tmp[tmp.size - 2] = tmp[tmp.size - 2] + 1
        if (tmp[tmp.size - 2] > tmp[tmp.size - 1]) {
            tmp[tmp.size - 2] = tmp[tmp.size - 2] + tmp[tmp.size - 1]
            tmp.removeAt(tmp.size - 1)
        } else {
            while (tmp[tmp.size - 2] * 2 <= tmp[tmp.size - 1]) {
                val temp = tmp[tmp.size - 1] - tmp[tmp.size - 2]
                tmp.removeAt(tmp.size - 1)
                tmp.add(tmp[tmp.size - 1])
                tmp.add(temp)
            }
        }
        return tmp
    }

}
