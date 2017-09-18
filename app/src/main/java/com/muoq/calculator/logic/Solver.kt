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

    fun parse(expression: Expression): BigDecimal {

        return BigDecimal(0.0)
    }

    //oPIndex = openParenthesisIndex
    fun solveParentheses(expressionArg: MutableList<Any>, oPIndex: Int): Expression {

        //closeParenthesisIndex
        var cPIndex = 0

        for (i in 0..expressionArg.size - 1) {
            if (expressionArg[i] == ")") {
                cPIndex = i
            }
            else if (expressionArg[i] == "(") {
                var tempList = mutableListOf<Any>(expressionArg.filterIndexed({index, _->
                    index in (oPIndex + 1)..(cPIndex - 1)
                }))

                tempList.forEach() {e -> Log.i(TAG, e.toString())}

                solveParentheses(tempList, i)
            }
        }

        var expression = expressionArg.filterIndexed({index, _ ->
            index in (oPIndex + 1)..(cPIndex - 1)
        })



    }

    fun multiply(x: BigDecimal, y: BigDecimal) = x.multiply(y)

    fun divide(x: BigDecimal, y: BigDecimal) = x.divide(y)

    fun add(x: BigDecimal, y: BigDecimal) = x.add(y)

    fun subtract(x: BigDecimal, y: BigDecimal) = x.subtract(y)

}
