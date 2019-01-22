import java.io.*
import java.nio.charset.StandardCharsets
import java.util.*

object 26 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {

        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("nextsetpartition.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("nextsetpartition.out", "UTF-8")

        var tokin: StringTokenizer
        val arr = ArrayList<ArrayList<Int>>()
        var curVars: String

        while (true) {
            try {
                curVars = reader.readLine()
            } catch (e: Exception) {
                break
            }
            //println(currArgs)
            if (curVars == "" || curVars == "0 0") {
                continue
            }
            tokin = StringTokenizer(curVars)

            val n = tokin.nextToken().toInt()
            val k = tokin.nextToken().toInt()

            for (i in 0 until k) {
                arr.add(ArrayList())
                curVars = reader.readLine()
                tokin = StringTokenizer(curVars)
                while (tokin.hasMoreTokens()) {
                    arr[i].add(tokin.nextToken().toInt())
                }
            }
            nextSep(arr)
            run {
                var i = 0
                while (i < arr.size) {
                    if (arr[i].size == 0) {
                        arr.removeAt(i)
                        i--
                    }
                    i++
                }
            }
            out.write(n.toString() + " " + arr.size + "\n")
            for (i in arr.indices) {
                for (j in 0 until arr[i].size) {
                    out.write(arr[i][j].toString() + " ")
                }
                out.write("\n")
            }
            out.write("\n")
            arr.clear()
        }
        out.close()
    }


    fun nextSep(result: ArrayList<ArrayList<Int>>): ArrayList<ArrayList<Int>> {
        val elements = ArrayList<Int>()
        var `break` = false
        for (i in result.indices.reversed()) {
            var maxVal = 1000000001
            var maxIdx = 0
            for (k in elements.indices) {
                maxIdx = if (elements[k] > result[i][result[i].size - 1] && elements[k] < maxVal) k else maxIdx
                maxVal = if (elements[k] > result[i][result[i].size - 1] && elements[k] < maxVal) elements[k] else maxVal
            }
            if (elements.size > 0 && maxVal > result[i][result[i].size - 1] && maxVal != 1000000001) {
                result[i].add(maxVal)
                elements.removeAt(maxIdx)
                break
            }
            for (j in result[i].size - 1 downTo 0) {
                if (j > 0 && elements.size > 0 && elements[elements.size - 1] > result[i][j]) {
                    var maxNum = 1000000001
                    var maxNumIdx = 0
                    for (k in elements.indices) {
                        maxNumIdx = if (elements[k] > result[i][j] && elements[k] < maxNum) k else maxNumIdx
                        maxNum = if (elements[k] > result[i][j] && elements[k] < maxNum) elements[k] else maxNum
                    }
                    val tmp = result[i][j]
                    result[i][j] = maxNum
                    elements[maxNumIdx] = tmp
                    `break` = true
                    break
                }
                elements.add(result[i][j])
                result[i].removeAt(j)
            }
            if (`break`) break
        }

        elements.sort()
        for (i in elements.indices) {
            val tmpArr = ArrayList<Int>()
            tmpArr.add(elements[i])
            result.add(tmpArr)
        }

        return result
    }
}
