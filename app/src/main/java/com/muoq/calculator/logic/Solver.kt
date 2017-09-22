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

        expression.getList().forEachIndexed {index, e ->
            if (e[1] is Operator && (e[1] as Operator).ID == Operator.O_PARENTHESIS) {

                val (parenthesisSolve, prevCIndex) = solveParentheses(expression, index)
                expression.setNumber(index, parenthesisSolve)

                for (i in index + 1..prevCIndex) {
                    expression.setNull(i)
                }

                for (i in index + 1 until expression.size) {
                    if (expression.get(i) == null) {
                        expression.queueRemove(i)
                    }
                }

                expression.removeQueued()
            }
        }

        return BigDecimal(0)
    }

    // oPIndex = opening parenthesis index
    fun solveParentheses(expressionArg: Expression, oIndex: Int): Pair<BigDecimal, Int>{

        var cIndex = 0
        var expression = expressionArg

        for (i in oIndex + 1 until expression.size) {
            if (expression.getOperator(i) != null) {
                if (expression.getOperator(i)!!.ID == Operator.C_PARENTHESIS) {
                    cIndex = i
                    break
                }
                else if (expression.getOperator(i)!!.ID == Operator.O_PARENTHESIS) {

                    val (parenthesisSolve, prevCIndex) = solveParentheses(expression, i)
                    expression.setNumber(i, parenthesisSolve)

                    for (j in i + 1..prevCIndex) {
                        expression.setNull(j)
                    }
                }
            }
        }

        return Pair(BigDecimal(0), cIndex)
    }

}
