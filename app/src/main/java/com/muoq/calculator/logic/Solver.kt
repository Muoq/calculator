package com.muoq.calculator.logic

import android.util.Log
import java.math.BigDecimal

/**
 * Created by victo on 18-09-2017.
 */

class Solver {

    companion object {
        val TAG = "CalculatorApp"

        fun solveSimple(expressionArg: MutableList<Any>): BigDecimal {
            var expression = expressionArg.filter({e -> true}).toMutableList()

            for (i in 0 until expression.size) {
                val debugInt = i
                if (expression[i] == '*' && expression[i - 1] is BigDecimal) {
                    expression = solveOperation(expression, multiply, i)
                } else if (expression[i] == '/' && expression[i - 1] is BigDecimal) {
                    expression = solveOperation(expression, divide, i)
                }
            }
            expression.removeAll({it == "null"})

            for (i in 0 until expression.size) {
                val debugInt = i
                if (expression[i] == '+' && i != 0) {
                    expression = solveOperation(expression, add, i)
                } else if (expression[i] == '-' && i != 0) {
                    expression = solveOperation(expression, subtract, i)
                }
            }
            expression.removeAll({it == "null"})

            // If error occurs here, an operator is left in the mutableExpression
            return expression[0] as BigDecimal
        }

        private fun solveOperation(expressionArg: MutableList<Any>,
                                   operation: (BigDecimal, BigDecimal) -> BigDecimal,
                                   index: Int): MutableList<Any> {

            var varOneIndex = index - 1
            var varTwoIndex = index + 1

            while (expressionArg[varOneIndex] == "null") {
                varOneIndex--
            }
            while (expressionArg[varTwoIndex] == "null") {
                varTwoIndex++
            }

            val temp = operation(expressionArg[varOneIndex] as BigDecimal, expressionArg[varTwoIndex] as BigDecimal)
            var expression: MutableList<Any> = expressionArg.filter({_ -> true}).toMutableList()
            expression[varOneIndex] = temp
            expression[index] = "null"
            expression[varTwoIndex] = "null"

            return expression
        }

        //oPIndex = openParenthesisIndex
        fun solveParentheses(expressionArg: MutableList<Any>, oPIndex: Int): Pair<BigDecimal, Int> {
            //closeParenthesisIndex
            var cPIndex = 0

            var expression: MutableList<Any> = expressionArg.filterIndexed({index, _ -> index > oPIndex}).toMutableList()

            for (i in 0 until expression.size) {
                var debugInt = i
                if (expression[i] == ')') {
                    cPIndex = i
                    break
                }
                else if (expression[i] == '(') {

                    val (parenthesisSolve, prevClosingIndex) = solveParentheses(expression, i)

                    expression[i] = parenthesisSolve
                    for (j in (i + 1)..(i + 1 + prevClosingIndex)) {
                        val debugIntJ = j
                        expression[j] = "null"
                        cPIndex--
                    }
                }
            }
            expression.removeAll({it == "null"})

            expression = expression.filterIndexed({index, _ ->
                index in 0 until cPIndex
            }).toMutableList()

            var tempBD = solveSimple(expression)

            return Pair(tempBD, cPIndex)
        }

        val multiply = {x: BigDecimal, y: BigDecimal-> x.multiply(y)}

        val divide = {x: BigDecimal, y: BigDecimal-> x.divide(y)}

        val add = {x: BigDecimal, y: BigDecimal-> x.add(y)}

        val subtract = {x: BigDecimal, y: BigDecimal-> x.subtract(y)}
    }



}
