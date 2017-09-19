package com.muoq.calculator.logic

import android.util.Log
import java.math.BigDecimal
import kotlin.*
import kotlin.collections.*

/**
 * Created by victo on 18-09-2017.
 */

class Expression(stringArg: String = "") {

    companion object {
        val solver = Solver()

        fun solveSimple(expressionArg: MutableList<Any>): BigDecimal {
            var expression = expressionArg

            for (i in 0 until expressionArg.size) {
                    if (expressionArg[i] == "*" && expressionArg[i - 1] is BigDecimal) {
                        expression = solveOperation(expression, solver.multiply, i)
                    } else if (expressionArg[i] == "/" && expressionArg[i - 1] is BigDecimal) {
                        expression = solveOperation(expression, solver.divide, i)
                    }
            }
            expression.removeAll {n -> n == "null"}

            for (i in 0 until expression.size) {
                if (expressionArg[i] == "+" && i != 0) {
                    expression = solveOperation(expression, solver.add, i)
                } else if (expressionArg[i] == "-" && i != 0) {
                    expression = solveOperation(expression, solver.subtract, i)
                }
            }
            expression.removeAll {n -> n == "null"}

            // If error occurs here, an operator is left in the expression
            return expression[0] as BigDecimal
        }

        private fun solveOperation(expressionArg: MutableList<Any>,
                           operation: (BigDecimal, BigDecimal) -> BigDecimal,
                           index: Int): MutableList<Any> {
            val temp = operation(expressionArg[index - 1] as BigDecimal, expressionArg[index + 1] as BigDecimal)
            var expression: MutableList<Any> = expressionArg.filter({_ -> true}).toMutableList()
            expression[index - 1] = temp
            expression[index] = "null"
            expression[index + 1] = "null"

            return expression
        }
    }

    val TAG = "CalculatorApp"

    var size = 0

    var expression: MutableList<Any> = mutableListOf()

    init {
        stringArg.forEach {c ->
            if (c.toInt() in 48..57) {
                addBD(BigDecimal(c.toInt() - 48))
            } else if (c == ' ') {
            }
            else {
                addOperator(c)
            }
        }
    }

    fun addBD(addition: BigDecimal) {
        if (expression.size > 0 && expression.last() is BigDecimal)
            return

        expression.add(addition)
        size++
    }

    fun addOperator(addition: Char) {
        if (expression.size > 0 && addition !in listOf('(', ')') &&
                expression.last() !in listOf('(', ')') && expression.last() is Char)
                expression[expression.size - 1] = addition
        else
            expression.add(addition)
    }

    fun solve() {

        for (i: Int in 0 until expression.size) {
            if (expression[i] == '(') {
                val (parenthesisSolve, closingIndex) = solver.solveParentheses(expression, i)

                expression[i] = parenthesisSolve
                for (j in i + 1 until closingIndex) {
                    expression[j] = "null"
                }
            }
        }
        expression.removeAll {e -> e == "null"}

        Log.i("SOLVE", "In solve call");
    }

}
