package com.muoq.calculator.logic

import java.math.BigDecimal

/**
 * Created by victor on 09/10/2017.
 */
class Expression {

    var numbers = mutableListOf<Pair<Int, BigDecimal>>()
    var operators = mutableListOf<Pair<Int, Operator>>()
    var expression = mutableListOf<Any>()

    var expressionCaret = 0

    fun addNumber(num: BigDecimal) {
        if (numbers.isEmpty()) {
            numbers.add(Pair(expressionCaret, num))
            expression.add(num)
            expressionCaret++
        } else if (operators.isEmpty() || numbers.last().first + 1 == expressionCaret) {
            val magnitudePlus = numbers.last().second.multiply(BigDecimal(10))
            numbers[numbers.lastIndex] = Pair(numbers.last().first, magnitudePlus.add(num))
            expression[numbers[numbers.lastIndex].first] = magnitudePlus.add(num)
        } else if (numbers.last().first < operators.last().first) {
            numbers.add(Pair(expressionCaret, num))
            expression.add(num)
            expressionCaret++
        }
    }

    fun addOperator(operator: Operator) {
        if (numbers.isEmpty() && operator.hierarchy == Operator.PARENTHESIS_HIERARCHY) {
            operators.add(Pair(expressionCaret, operator))
            expression.add(operator)
            expressionCaret++
        } else if (operators.isEmpty()) {
            operators.add(Pair(expressionCaret, operator))
            expression.add(operator)
            expressionCaret++
        } else if (operators.last().first + 1 == expressionCaret) {
            operators[operators.lastIndex] = Pair(operators.last().first, operator)
            expression[operators[operators.lastIndex].first] = operator
        } else if (numbers.last().first + 1 == expressionCaret || operators.last().second.isSequential) {
            operators.add(Pair(expressionCaret, operator))
            expression.add(operator)
            expressionCaret++
        }
    }

    fun clearAll() {
        numbers = mutableListOf()
        operators = mutableListOf()
        expression = mutableListOf()
        expressionCaret = 0

        numbers.add(Pair(0, BigDecimal(0)))
        expression.add(BigDecimal(0))
        expressionCaret++
    }

    fun delete() {
        if (numbers.isNotEmpty()) {
            if (numbers.last().first + 1 == expressionCaret) {
                if (numbers.last().second.toString().length > 1) {
                    val number = numbers.last().second

                    numbers[numbers.lastIndex] = Pair(numbers.last().first, BigDecimal(
                            number.toString().removeRange(
                            number.toString().length - 2, number.toString().length - 1
                            )))

                    expression[expression.lastIndex] = numbers.last().second
                } else if (numbers.last().second.toString().length == 1) {
                    numbers.removeAt(numbers.lastIndex)
                    expression.removeAt(expression.lastIndex)

                    expressionCaret--

                    if (numbers.size == 0) {
                        numbers.add(Pair(0, BigDecimal(0)))
                        expression.add(BigDecimal(0))

                        expressionCaret++
                    }

                    return
                }
            }
        }

        if (operators.isNotEmpty()) {
            if (operators.last().first + 1 == expressionCaret) {
                operators.removeAt(operators.lastIndex)
                expression.removeAt(expression.lastIndex)

                expressionCaret--
            }
        }
    }

    fun getExpressionAsString2(): String {
        var expressionString = ""
        expression = mutableListOf()
        var expressionPosition = 0
        var numbersCtr = 0
        var operatorsCtr = 0
        val combinedListSize = numbers.size + operators.size
        for (i in 0 until combinedListSize) {
            if (numbersCtr < numbers.size && numbers[numbersCtr].first == expressionPosition) {
                expression.add(numbers[numbersCtr])
                expressionString += numbers[numbersCtr].second.toString()
                numbersCtr++
                expressionPosition++
            } else if (operatorsCtr < operators.size && operators[operatorsCtr].first == expressionPosition) {
                expression.add(operators[operatorsCtr])
                expressionString += operators[operatorsCtr].second.toString()
                operatorsCtr++
                expressionPosition++
            }
        }

        return expressionString
    }

    fun getExpressionAsString(): String {

        if (expression.size == 1 && expression[0] == BigDecimal(0))
            return ""

        var expressionString = ""

        expression.forEach {
            expressionString += it.toString()
        }

        return expressionString
    }

}