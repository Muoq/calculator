package com.muoq.calculator.logic

import android.util.Log
import java.math.BigDecimal

/**
 * Created by victo on 18-09-2017.
 */

class Solver {

    companion object {
        val TAG = "CalculatorApp"
    }

    //oPIndex = openParenthesisIndex
    fun solveParentheses(expressionArg: MutableList<Any>, oPIndex: Int): Pair<BigDecimal, Int> {
        //closeParenthesisIndex
        var cPIndex = 0

        var expression: MutableList<Any> = expressionArg.filter({n -> true}).toMutableList()

        for (i in 0 until expression.size) {
            if (expression[i] == ")") {
                cPIndex = i
            }
            else if (expression[i] == "(") {
                val tempList = expression.filterIndexed({index, _ ->
                    index in i + 1 until expression.size
                }).toMutableList()

                val (parenthesisSolve, prevClosingIndex) = solveParentheses(tempList, i)

                expression[i] = parenthesisSolve
                for (j in i + 1 until prevClosingIndex) {
                    expression[j] = "null"
                }
            }
        }
        expression.removeAll {e -> e == "null"}

        expression = expression.filterIndexed({index, _ ->
            index in oPIndex + 1 until cPIndex
        }).toMutableList()

        return Pair(Expression.solveSimple(expression), cPIndex)
    }

    val multiply = {x: BigDecimal, y: BigDecimal-> x.multiply(y)}

    val divide = {x: BigDecimal, y: BigDecimal-> x.divide(y)}

    val add = {x: BigDecimal, y: BigDecimal-> x.add(y)}

    val subtract = {x: BigDecimal, y: BigDecimal-> x.subtract(y)}

}
