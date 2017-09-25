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

        val (parenthesisSolve, prevCIndex) = solveParentheses(expression, -1)

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
            index >= oIndex + 1
        }.toMutableList()

        for (i in oIndex + 1 until expressionArg.size) {
            val operatorAtI = expressionArg.getOperator(i)

            if (operatorAtI != null) {
                if (operatorAtI.ID == Operator.C_PARENTHESIS) {
                    cIndex = i
                    break
                } else if (operatorAtI.ID == Operator.O_PARENTHESIS) {
                    val (parenthesisSolve, prevCIndex) = solveParentheses(expressionArg, i)
                    expressionArg.setNumber(i, parenthesisSolve)
                    expressionList[i - oIndex - 1] = parenthesisSolve

                    for (j in i + 1..prevCIndex) {
                        expressionArg.queueRemove(j)
                    }

                    expressionList = expressionList.filterIndexed {index, _ ->
                        index < i - oIndex || index >= prevCIndex - oIndex - 1
                    }.toMutableList()
                }
            }
        }
        cIndex -= expressionArg.removeQueued()

        expressionList = expressionArg.getValueList().filterIndexed({index, _ ->
            index > oIndex && index < cIndex
        }).toMutableList()

        val answer = solveSimple(expressionList)

        return Pair(answer, cIndex)
    }

    fun solveSimple(expressionArg: MutableList<Any?>,
                    operatorHierarchy: Int = Operator.PARENTHESIS_HIERARCHY): BigDecimal {
        /*TODO: Fix function by comparing hierarchies of operators and performing operations accordingly*/

        if (operatorHierarchy < 0) {
            return expressionArg[0] as BigDecimal
        }

        var expression = expressionArg.filter({_ -> true}).toMutableList()

        var expressionSize = expression.size
        var i = 0
        while (i < expressionSize) {
            val operator = expression[i]

            if (operator is Operator && operator.hierarchy == operatorHierarchy) {
                expression[i - 1] = operator.operation(expression[i - 1]as BigDecimal,
                                                        expression[i + 1] as BigDecimal)
                expression.removeAt(i + 1)
                expression.removeAt(i)
                expressionSize -= 2
                i--
            }

            i++
        }

        val solution = solveSimple(expression, operatorHierarchy - 1)

        return solution
    }

}
