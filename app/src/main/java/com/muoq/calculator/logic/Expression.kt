package com.muoq.calculator.logic

/**
 * Created by victo on 20-09-2017.
 */

import android.util.Log
import com.muoq.calculator.MainActivity
import java.math.BigDecimal

class Expression {

    companion object {
        val TAG = "CalculatorExpression"
    }

    var size = 0

    var numbers: MutableList<BigDecimal> = mutableListOf()
    var operators: MutableList<Operator> = mutableListOf()

    var expression: MutableList<MutableList<Any?>> = mutableListOf()
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
        if (expression.size < index){
            throw ArrayIndexOutOfBoundsException()
        } else if (expression[index][1] is BigDecimal) {
            expression[index][1] = numberArg
            numbers[expression[index][0] as Int] = numberArg
        } else {

            var tempIndex = index
            while (expression[tempIndex][1] !is BigDecimal) {
                tempIndex--

                Log.i(TAG, expression[index][1].toString())
            }

            var numbersIndex = expression[tempIndex][0] as Int + 1

            if (numbers.size == numbersIndex) {
                numbers.add(numberArg)
            } else {
                numbers[numbersIndex] = numberArg
            }

            expression[index][0] = numbersIndex
            expression[index][1] = numberArg
        }

    }

    fun setMemberData(i: Int, index: Int,
                      valueBigDecimal: BigDecimal? = null, valueOperator: Operator? = null) {
        if (expression.size < i) {
            throw IndexOutOfBoundsException()
        }

        if (valueBigDecimal != null) {
            expression[i] = mutableListOf(index, valueBigDecimal)
        } else if (valueOperator != null) {
            expression[i] = mutableListOf(index, valueOperator)
        }
    }

    fun setNull(index: Int) {
        expression[index] = mutableListOf(null)
    }

    private fun addMember(index: Int,valueBigDecimal: BigDecimal? = null, valueOperator: Operator? = null) {
        if (valueBigDecimal != null) {
            expression.add(mutableListOf(index, valueBigDecimal))
        } else if (valueOperator != null) {
            expression.add(mutableListOf(index, valueOperator))
        }

        size++
    }

    fun getList(): MutableList<MutableList<Any?>> {
        return expression
    }

    fun getNumber(index: Int): BigDecimal? {
        if (expression[index][1] !is BigDecimal) {
            return null
        } else {
            return expression[index][1] as BigDecimal
        }
    }

    fun getOperator(index: Int): Operator? {
        if (expression[index][1] !is Operator) {
            return null
        } else {
            return expression[index][1] as Operator
        }
    }

    fun solve() {
        var solver = Solver()
        solver.solve(this)
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
