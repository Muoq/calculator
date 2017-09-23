package com.muoq.calculator.logic

/**
 * Created by victor on 20/09/2017.
 */

import android.util.Log
import java.math.BigDecimal

class Solver {

    companion object {
        val TAG = "CalculatorSolver"
    }

    fun solve(expression: Expression): BigDecimal {

        expression.addOperator(Operator(Operator.C_PARENTHESIS))

        val (parenthesisSolve, prevCIndex) = solveParentheses(expression, 0)

        return parenthesisSolve
    }

    // oPIndex = opening parenthesis index
    fun solveParentheses2(expressionArg: Expression, oIndex: Int): Pair<BigDecimal, Int>{

        var cIndex = 0
        var expression = expressionArg

        for (i in oIndex + 1 until expression.size) {
            if (expression.getOperator(i) != null) {
                if (expression.getOperator(i)!!.ID == Operator.C_PARENTHESIS) {
                    cIndex = i
                    break
                }
                else if (expression.getOperator(i)!!.ID == Operator.O_PARENTHESIS) {

                    val (parenthesisSolve, prevCIndex) = solveParentheses2(expression, i)
                    expression.setNumber(i, parenthesisSolve)

                    for (j in i + 1..prevCIndex) {
                        expression.setNull(j)
                    }
                }
            }
        }

        return Pair(BigDecimal(0), cIndex)
    }

    fun solveParentheses(expressionArg: Expression, oIndex: Int): Pair<BigDecimal, Int> {

        var cIndex = 0;
        var expressionList = expressionArg.getValueList().filterIndexed {index, _ ->
            index > oIndex + 1
        }.toMutableList()

        for (i in oIndex + 1 until expressionArg.size) {
            val operatorAtI = expressionArg.getOperator(i)

            if (operatorAtI != null) {
                if (operatorAtI.ID == Operator.O_PARENTHESIS) {
                    cIndex = i
                    break
                } else if (operatorAtI.ID == Operator.C_PARENTHESIS) {
                    val (parenthesisSolve, prevCIndex) = solveParentheses(expressionArg, i)
                    expressionArg.setNumber(i, parenthesisSolve)

                    for (j in i + 1..prevCIndex) {
                        expressionArg.queueRemove(j)
                    }
                }
            }
        }
        cIndex -= expressionArg.removeQueued()

        expressionList = expressionList.filterIndexed({index, _ -> index < cIndex}).toMutableList()
        val answer = solveSimple(expressionList)

        return Pair(answer, cIndex)
    }

    fun solveSimple(expressionArg: MutableList<Any?>): BigDecimal {
        /*TODO: Fix function by comparing hierarchies of operators and performing operations accordingly*/

        val expression = expressionArg.filter{_ -> true}.toMutableList()

        if (expression.size == 3) {
            val operator = expression[1]
            if (operator is Operator) {
                return operator.operation(expression[0] as BigDecimal, expression[2] as BigDecimal)
            }
        }

        for (i in 0 until expression.size) {
            val valueAtI = expression[i]

            if (valueAtI != null) {
                if (valueAtI is Operator) {
                    Log.i(TAG, "valAtI is an operator!")

                    expression[i] = valueAtI.operation(expression[i - 1] as BigDecimal,
                                                        expression[i + 1] as BigDecimal)
                    expression[i] = null
                    expression[i + 1] = null
                }
            }

        }
        expression.removeAll {it == null}
        solveSimple(expression)

        return expressionArg[0] as BigDecimal
    }

}
