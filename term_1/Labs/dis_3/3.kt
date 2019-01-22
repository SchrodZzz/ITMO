import java.io.*
import java.nio.charset.StandardCharsets
import java.util.*


object 3 {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = BufferedReader(
                InputStreamReader(
                        FileInputStream("antigray.in"),
                        StandardCharsets.UTF_8))
        val out = PrintWriter("antigray.out", "UTF-8")

        val n = Integer.parseInt(reader.readLine())
        val container = ArrayList<String>()
        container.add("0")

        for (j in 0 until n) {
            run {
                var counter = 0
                while (counter < container.size) {
                    val nowPlusOne = StringBuilder()
                    val nowPlusTwo = StringBuilder()
                    for (k in 0 until container[counter].length) {
                        nowPlusOne.append((Integer.parseInt(container[counter][k] + "") + 1) % 3)
                        nowPlusTwo.append((Integer.parseInt(container[counter][k] + "") + 2) % 3)
                    }
                    container.add(counter + 1, nowPlusOne.toString())
                    container.add(counter + 2, nowPlusTwo.toString())
                    counter += 2
                    ++counter
                }
            }
            if (j == n - 1) {
                for (i in container.indices) {
                    out.write(container[i] + "\n")
                }
            }
            for (i in container.indices) {
                container[i] = StringBuilder("0").append(container[i]).toString()
            }
            container.sort()
        }

        out.close()
    }

    internal fun worker(n: Int): Int {
        var tmpAns = 1
        for (i in 0 until n) {
            tmpAns *= 2
        }
        return tmpAns
    }
}
