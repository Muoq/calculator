package com.muoq.calculator.logic

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Created by victo on 18-09-2017.
 */

class Operator(operationIDArg: Int = 1) {

    companion object {
        val PARENTHESIS_FUN = {x: BigDecimal, y: BigDecimal -> x}
        val MULTIPLY_FUN = {x: BigDecimal, y: BigDecimal -> x.multiply(y)}
        val DIVIDE_FUN = {x: BigDecimal, y: BigDecimal -> x.divide(y, 15, RoundingMode.HALF_UP)}
        val ADD_FUN = {x: BigDecimal, y: BigDecimal -> x.add(y)}
        val SUBTRACT_FUN = {x: BigDecimal, y: BigDecimal -> x.subtract(y)}

        val PARENTHESIS_HIERARCHY = 2
        val MULTIPLICATION_HIERARCHY = 1
        val ADDITION_HIERARCHY = 0

        val O_PARENTHESIS = 5
        val C_PARENTHESIS = 4
        val MULTIPLY = 3
        val DIVIDE = 2
        val ADD = 1
        val SUBTRACT = 0
    }

    var operation = MULTIPLY_FUN
    var hierarchy = MULTIPLICATION_HIERARCHY
    var ID = MULTIPLY

    var sequential = false

    init {
        if (operationIDArg != MULTIPLY) {
            setOperation(operationIDArg)
        }
    }

    fun setOperation(operationIDArg: Int) {
        ID = operationIDArg
        hierarchy = operationIDArg / 2

        sequential = ID == O_PARENTHESIS || ID == C_PARENTHESIS

        when (ID) {
            O_PARENTHESIS, C_PARENTHESIS-> operation = PARENTHESIS_FUN
            MULTIPLY -> operation = MULTIPLY_FUN
            DIVIDE -> operation = DIVIDE_FUN
            ADD -> operation = ADD_FUN
            SUBTRACT -> operation = SUBTRACT_FUN
        }
    }

    override fun toString(): String {
        when (ID) {
            O_PARENTHESIS -> return "("
            C_PARENTHESIS -> return  ")"
            MULTIPLY -> return "*"
            DIVIDE -> return "/"
            ADD -> return "+"
            SUBTRACT -> return "-"
        }
        return ""
    }

}
