package com.muoq.calculator.logic

import java.math.BigDecimal
import kotlin.*
import kotlin.collections.*

/**
 * Created by victo on 18-09-2017.
 */

class Expression {

    companion object {
        val solver = Solver()

        fun solveSimple(expressionArg: MutableList<Any>): BigDecimal {
            var expression = expressionArg

            for (i in 0..expressionArg.size - 1) {
                    if (expressionArg[i] == "*" && i != 0) {
                        val temp = solver.multiply(expressionArg[i - 1] as BigDecimal, expressionArg[i + 1] as BigDecimal)
                        expression[i - 1] = temp
                        expression.removeAt(i)
                        expression.removeAt(i + 1)
                    } else if (expressionArg[i] == "/" && i != 0) {
                            val temp = solver.divide(expressionArg[i - 1] as BigDecimal, expressionArg[i + 1] as BigDecimal)
                        expression[i - 1] = temp
                        expression.removeAt(i)
                        expression.removeAt(i + 1)
                    }
            }

            for (i in 0..expression.size -1) {
                
            }
        }
    }

    val TAG = "CalculatorApp"

    var size = 0

    var expression: MutableList<Any> = mutableListOf()

    fun addBD(addition: BigDecimal) {
        if (expression.last() is BigDecimal) {
            return
        }
        expression.add(Int)
        size++
    }

    fun main() {
        expression = solver.solveParentheses(expression, 0)
        for (i in 0..expression.size - 1) {

        }
    }

}
