import java.io.*
import java.nio.charset.StandardCharsets
import java.io.PrintWriter
import java.io.IOException
import java.util.*


object 12 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("part2sets.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("part2sets.out", "UTF-8")

        val cont = reader.readLine().split(" ")
        val n = cont[0].toInt()
        val k = cont[1].toInt()
        val fst = ArrayList<ArrayList<Int>>()
        val scnd = ArrayList<ArrayList<Int>>()
        for (i in 0 until k - 1) {
            val a = ArrayList<Int>()
            a.add(i + 1)
            fst.add(a)
            val b = ArrayList<Int>()
            b.add(i + 1)
            scnd.add(b)
        }

        fst.add(ArrayList())
        scnd.add(ArrayList())
        for (i in k..n) {
            fst[fst.size - 1].add(i)
            scnd[scnd.size - 1].add(i)
        }

        var kostya = false
        while (!kostya) {
            nextSep(fst)
            run {
                var i = 0
                while (i < fst.size) {
                    if (fst[i].size == 0) {
                        fst.removeAt(i)
                        i--
                    }
                    i++
                }
            }
            if (fst.size != k) continue
            for (i in fst.indices) {
                kostya = fst[i] == scnd[i]
            }

            for (i in fst.indices) {
                for (j in 0 until fst[i].size) {
                    out.write(fst[i][j].toString() + " ")
                }
                out.write("\n")
            }

            out.write("\n")
        }
        out.close()
    }

    fun nextSep(fst: ArrayList<ArrayList<Int>>): ArrayList<ArrayList<Int>> {
        val ans = ArrayList<Int>()
        var kostya2 = false
        for (i in fst.indices.reversed()) {
            var vala = 1000000000
            var conter = 0
            for (k in ans.indices) {
                conter = if (ans[k] > fst[i][fst[i].size - 1] && ans[k] < vala) k else conter
                vala = if (ans[k] > fst[i][fst[i].size - 1] && ans[k] < vala) ans[k] else vala
            }
            if (ans.size > 0 && vala > fst[i][fst[i].size - 1] && vala != 1000000000) {
                fst[i].add(vala)
                ans.removeAt(conter)
                break
            }
            for (j in fst[i].size - 1 downTo 0) {
                if (j > 0 && ans.size > 0 && ans[ans.size - 1] > fst[i][j]) {
                    var valb = 1000000000
                    var conterb = 0
                    for (k in ans.indices) {
                        conterb = if (ans[k] > fst[i][j] && ans[k] < valb) k else conterb
                        valb = if (ans[k] > fst[i][j] && ans[k] < valb) ans[k] else valb
                    }
                    val c = fst[i][j]
                    fst[i][j] = valb
                    ans[conterb] = c
                    kostya2 = true
                    break
                }
                ans.add(fst[i][j])
                fst[i].removeAt(j)
            }
            if (kostya2) break
        }
        ans.sort()
        for (i in ans.indices) {
            val adding = ArrayList<Int>()
            adding.add(ans[i])
            fst.add(adding)
        }
        return fst
    }

}
