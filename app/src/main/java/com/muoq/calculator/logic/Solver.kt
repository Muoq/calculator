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

    fun solve(expressionArg: Expression): BigDecimal {

        var expression = expressionArg.getList().filter { n -> true}

        expression.forEachIndexed {index, e ->
            if (e[1] == Operator.O_PARENTHESIS) {
                val (parenthesisSolve, prevCIndex) = solveParentheses(expressionArg, index)

                expressionArg.setNumber(index, parenthesisSolve)
//                for (j in )
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
                        expression.getList().forEach {
                            Log.i(TAG, it.toString())
                        }
                    }
                }
            }
        }

        return Pair(BigDecimal(0), cIndex)

    }

}
