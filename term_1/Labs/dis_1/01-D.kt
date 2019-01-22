/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 11.2018
 */

import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.exitProcess

object Input {
    private var reader = StringTokenizer("")

    fun nextInt(): Int = nextLong().toInt()

    fun nextLong(): Long {
        if (!reader.hasMoreTokens()) reader = StringTokenizer(readLine()!!)
        return reader.nextToken().toLong()
    }
}

interface Var {
    val calculated: Long
}

data class BitWiseAnd(val op1: Var, val op2: Var) : Var {
    override val calculated: Long
        get() = op1.calculated and op2.calculated

    override fun toString() = "($op1)&($op2)"

    override fun hashCode() = calculated.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other !is Var) return false
        return calculated == other.calculated
    }
}

data class BitWiseOr(val op1: Var, val op2: Var) : Var {
    override val calculated = op1.calculated or op2.calculated

    override fun toString() = "($op1)|($op2)"

    override fun hashCode() = calculated.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other !is Var) return false
        return calculated == other.calculated
    }
}

data class BitWiseNot(val op: Var) : Var {
    override val calculated = op.calculated.inv()

    override fun toString() = "(~$op)"

    override fun hashCode() = calculated.hashCode()

    override fun equals(other: Any?): Boolean {
        if (other !is Var) return false
        return calculated == other.calculated
    }
}

fun solver(mask: Long, dopMask: Long): Boolean {
    for (i in 0 until 60) {
        if (((dopMask shr i) and 1) > 0) {
            if (((mask shr i) and 1) == 0.toLong()) return false
        }
    }
    return true
}

data class Constant(override val calculated: Long, val index: Int) : Var {
    override fun toString() = "$index"

    override fun hashCode() = toString().hashCode()

    override fun equals(another: Any?): Boolean {
        if (another !is Var) return false
        return calculated == another.calculated
    }
}

fun main(args: Array<String>) {
    val n = Input.nextInt()
    val init = LongArray(n) { Input.nextLong() }
    val expResult = Input.nextLong()

    val combs = ArrayList<Var>(n * 2)
    init.forEachIndexed { idx, it ->
        combs.add(Constant(it, idx + 1))
        combs.add(BitWiseNot(combs.last()))
    }

    for (mask in 0 until (1 shl n * 2)) {
        var currentVal: Var? = null
        for (idx in 0 until n * 2) {
            if (((mask shr idx) and 1) > 0) {
                currentVal = if (currentVal == null) {
                    combs[idx]
                } else {
                    BitWiseAnd(currentVal, combs[idx])
                }
            }
        }

        if (currentVal != null) {
            combs.add(currentVal)
        }
    }

    var finalResult: Var? = null
    combs.forEach {
        if (solver(expResult, it.calculated)) {
            finalResult = if (finalResult == null) it
            else BitWiseOr(finalResult!!, it)
        }
    }

    if (finalResult != null && finalResult!!.calculated == expResult) {
        println(finalResult)
        exitProcess(0)
    }

    println("Impossible")
}
