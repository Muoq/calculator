package com.muoq.calculator.logic

import java.math.BigDecimal
import kotlin.*
import kotlin.collections.*

/**
 * Created by victo on 18-09-2017.
 */

class Expression(stringArg: String = "") {

    companion object {
        val TAG = "CalculatorApp"
    }

    var size = 0

    var mutableExpression: MutableList<Any> = mutableListOf()
    
    var expression: MutableList<Any> = mutableListOf()

    init {
        var prevIsNum = false
        var prevIsOperator = false
        var numToAdd = ""
        var operatorToAdd = '*'

        for (i in 0 until stringArg.length) {
            if (stringArg[i].toInt() in 48..57) {
                numToAdd += stringArg[i]
                prevIsNum = true
                if (prevIsOperator) {
                    prevIsOperator = false
                    addOperator(operatorToAdd)
                }
            } else if (stringArg[i] == ' ') {
            } else if (stringArg[i] in listOf('(', ')')) {
                if (prevIsOperator) {
                    addOperator(operatorToAdd)
                    prevIsOperator = false
                } else if (prevIsNum) {
                    addBD(BigDecimal(numToAdd.toInt()))
                    prevIsNum = false
                    numToAdd = ""
                }
                addOperator(stringArg[i])
            } else {
                operatorToAdd = stringArg[i]
                prevIsOperator = true
                if (prevIsNum) {
                    prevIsNum = false
                    addBD(BigDecimal(numToAdd.toInt()))
                    numToAdd = ""
                }
            }
        }

        if (prevIsNum) {
            addBD(BigDecimal(numToAdd.toInt()))
        }

    }

    fun addBD(addition: BigDecimal) {
        if (expression.size > 0 && expression.last() is BigDecimal)
            return

        mutableExpression.add(addition)
        expression.add(addition)
        size++
    }

    fun addOperator(addition: Char) {
        if (expression.size > 0 && addition !in listOf('(', ')') &&
                expression.last() !in listOf('(', ')') && expression.last() is Char) {
            mutableExpression[mutableExpression.size - 1] = addition
            expression[expression.size - 1] = addition
        } else {
            mutableExpression.add(addition)
            expression.add(addition)
        }
    }

    fun solve(): BigDecimal {

        for (i: Int in 0 until mutableExpression.size) {
            if (mutableExpression[i] == '(') {
                val (parenthesisSolve, closingIndex) = Solver.solveParentheses(mutableExpression, i)

                mutableExpression[i] = parenthesisSolve
                for (j in (i + 1)..(i + 1 + closingIndex)) {
                    mutableExpression[j] = "null"
                }
            }
        }
        mutableExpression.removeAll { e -> e == "null"}
        
        val answer = Solver.solveSimple(mutableExpression)
        
        return answer
    }

}
