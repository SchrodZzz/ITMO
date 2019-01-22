import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter


object 23 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("nextvector.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("nextvector.out", "UTF-8")

        val cont = reader.readLine()
        val n = cont.length
        val arr = IntArray(n)
        for (i in 0 until n) {
            arr[i] = Integer.parseInt(cont.substring(i, i + 1))
        }
        val a = prv(n, arr).toString()
        val b = nxt(n, arr).toString()
        out.print(a+"\n")
        out.print(b+"\n")
        out.close()
    }

    fun prv(n: Int, ans: IntArray): StringBuilder {
        var tmp = n
        val tmpA = IntArray(tmp)
        for (i in 0 until tmp) {
            tmpA[i] = ans[i]
        }
        val answerR = StringBuilder()
        tmp--
        while (tmp >= 0 && ans[tmp] == 0) {
            tmpA[tmp] = 1
            tmp--
        }
        if (tmp < 0) {
            answerR.append("-")
            return answerR
        } else {
            tmpA[tmp] = 0
            for (i in tmpA.indices) {
                answerR.append(tmpA[i])
            }
            return answerR
        }
    }

    fun nxt(n: Int, ans: IntArray): StringBuilder {
        var tmp = n
        val tmpA = IntArray(tmp)
        for (i in 0 until tmp) {
            tmpA[i] = ans[i]
        }
        val answerL = StringBuilder()
        tmp--
        while (tmp >= 0 && ans[tmp] == 1) {
            tmpA[tmp] = 0
            tmp--
        }
        if (tmp < 0) {
            answerL.append("-")
            return answerL
        } else {
            tmpA[tmp] = 1
            for (i in ans.indices) {
                answerL.append(tmpA[i])
            }
            return answerL
        }
    }

}
