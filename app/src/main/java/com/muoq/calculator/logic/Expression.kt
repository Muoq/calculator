package com.muoq.calculator.logic

/**
 * Created by victo on 20-09-2017.
 */

import android.util.Log
import com.muoq.calculator.MainActivity
import java.math.BigDecimal

class Expression {

    var numbers: MutableList<Any> = mutableListOf(BigDecimal(1))
    var operators: MutableList<Operator> = mutableListOf()

    var expression: MutableList<MutableList<Any>> = mutableListOf()
    var operatorIndices: MutableList<Int> = mutableListOf()
    var numberIndices: MutableList<Int> = mutableListOf()

    init {

    }

    fun addOperator(operatorArg: Operator) {
        if (operators.isEmpty() || expression.last()[1] !is Operator) {
            operators.add(operatorArg)
            addMember(operators.lastIndex, valueOperator = operatorArg)

        } else if (operatorArg.sequential || operators.last().sequential) {
            operators.add(operatorArg)
            addMember(operators.lastIndex, valueOperator = operatorArg)

        } else if (expression.last()[1] is Operator) {
            operators[operators.lastIndex] = operatorArg
            setMemberData(expression.lastIndex, operators.lastIndex, valueOperator = operatorArg)
        }

    }

    fun addNumber(numberArg: BigDecimal) {
        if (numbers.isEmpty() || expression.last()[1] !is BigDecimal) {
            numbers.add(numberArg)
            addMember(numbers.lastIndex, valueBigDecimal = numberArg)
        } else if (expression.last()[1] is BigDecimal) {
            numbers[numbers.lastIndex] = numberArg
            setMemberData(expression.lastIndex, numbers.lastIndex, valueBigDecimal = numberArg)
        }
    }

    fun setNumber(index: Int, numberArg: BigDecimal) {
        if (expression[index][1] is BigDecimal) {
            expression[index][1] = numberArg
            numbers[expression[index][0] as Int] = numberArg
        } else {

            var tempIndex = index
            while (expression[tempIndex][1] !is BigDecimal) {
                tempIndex--
            }

            var numbersIndex = expression[tempIndex][0] as Int + 1

            numbers[numbersIndex] = numberArg
            expression[index][0] = numbersIndex
            expression[index][1] = numberArg
        }

    }

    fun setMemberData(i: Int, index: Int,
                      valueBigDecimal: BigDecimal? = null, valueOperator: Operator? = null) {
        if (expression.size < i) {
            return
        }

        if (valueBigDecimal != null) {
            expression[i] = mutableListOf(index, valueBigDecimal)
        } else if (valueOperator != null) {
            expression[i] = mutableListOf(index, valueOperator)
        }
    }

    fun addMember(index: Int,valueBigDecimal: BigDecimal? = null, valueOperator: Operator? = null) {
        if (valueBigDecimal != null) {
            expression.add(mutableListOf(index, valueBigDecimal))
        } else if (valueOperator != null) {
            expression.add(mutableListOf(index, valueOperator))
        }
    }

    fun getExpressionList(): MutableList<MutableList<Any>> {
        return expression
    }

    override fun toString(): String {
        var returnString = ""

        expression.forEachIndexed {i, e ->
            if (e[1] is Operator) {
                Log.i(MainActivity.TAG, "true")
                returnString += (e[1] as Operator).toString()
            }
            else if (e[1] is BigDecimal)
                returnString += (e[1] as BigDecimal).toString()
        }

        return returnString
    }

}
