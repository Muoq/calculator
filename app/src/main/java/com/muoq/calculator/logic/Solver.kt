package com.muoq.calculator.logic

/**
 * Created by victor on 20/09/2017.
 */

import java.math.BigDecimal

class Solver {

    fun solve(expressionArg: Expression): BigDecimal {

        var expression = expressionArg

        for (i in 0 until expression.size) {
            if (expression[i][1] == Operator.O_PARENTHESIS) {
                var parenthesisSolve = solveParentheses(expression, i)
            }
        }


    }

    // oPIndex = opening parenthesis index
    fun solveParentheses(expressionArg: Expression, oPIndex: Int): BigDecimal {

        var expression = expressionArg.getExpressionList()



    }

}
