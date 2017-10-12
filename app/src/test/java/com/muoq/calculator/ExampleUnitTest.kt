package com.muoq.calculator

import org.junit.Test

import org.junit.Assert.*

import com.muoq.calculator.logic.Expression
import com.muoq.calculator.logic.Operator
import java.math.BigDecimal

class ExampleUnitTest {

    @Test
    fun createExpression() {
        val exp1 = Expression()

        exp1.addNumber(BigDecimal(7))
        exp1.addOperator(Operator(Operator.MULTIPLY))
        exp1.addNumber(BigDecimal(7))
        exp1.addOperator(Operator(Operator.ADD))
        exp1.addNumber(BigDecimal(7))
        exp1.addOperator(Operator(Operator.SUBTRACT))
        exp1.addNumber(BigDecimal(7))
        exp1.addOperator(Operator(Operator.DIVIDE))
        exp1.addNumber(BigDecimal(7))
        exp1.addNumber(BigDecimal(7))

        println(exp1.getExpressionAsString())
    }

}
