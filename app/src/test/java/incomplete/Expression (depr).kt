package incomplete

/**
 * Created by victo on 20-09-2017.
 */

import android.util.Log
import com.muoq.calculator.logic.Operator
import java.math.BigDecimal
import java.security.InvalidParameterException

class Expression(stringArg: String = "0") {

    companion object {
        val TAG = "CalculatorExpression"
        val SYMBOL_LIMIT = 10
    }

    var size = 0
    var lastOperator = Operator(Operator.MULTIPLY)

    var numbers: MutableList<BigDecimal> = mutableListOf()
    var operators: MutableList<Operator> = mutableListOf()

    private var expression: MutableList<MutableList<Any?>> = mutableListOf()
    var fullExpression: MutableList<MutableList<Any?>> = mutableListOf()
    //TODO: Implement expression modification functions to work with fullExpression (eg. addMember, setMember)

    private var removeQueue: MutableList<Int> = mutableListOf()

    var solution = BigDecimal(0)

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
                    addOperator(Operator(operatorToAdd))
                }
            } else if (stringArg[i] == ' ') {
            } else if (stringArg[i] in listOf('(', ')')) {
                if (prevIsOperator) {
                    addOperator(Operator(operatorToAdd))
                    prevIsOperator = false
                } else if (prevIsNum) {
                    addNumber(BigDecimal(numToAdd.toInt()))
                    prevIsNum = false
                    numToAdd = ""
                }
                addOperator(Operator(stringArg[i]))
            } else {
                operatorToAdd = stringArg[i]
                prevIsOperator = true
                if (prevIsNum) {
                    prevIsNum = false
                    addNumber(BigDecimal(numToAdd.toInt()))
                    numToAdd = ""
                }
            }
        }

        if (prevIsNum) {
            addNumber(BigDecimal(numToAdd.toInt()))
        }

    }

    fun addOperator(operatorArg: Operator) {
        var expressionStringSize = 0
        expression.forEach {
            expressionStringSize += it[1].toString().length
        }

        if (expressionStringSize >= SYMBOL_LIMIT) {
            return
        } else if (operators.isEmpty() || expression.last()[1] !is Operator) {
            operators.add(operatorArg)
            addMember(operators.lastIndex, valueOperator = operatorArg)

        } else if (operatorArg.isSequential || operators.last().isSequential) {
            operators.add(operatorArg)
            addMember(operators.lastIndex, valueOperator = operatorArg)

        } else if (expression.last()[1] is Operator) {
            operators[operators.lastIndex] = operatorArg
            setMemberData(expression.lastIndex, operators.lastIndex, valueOperator = operatorArg)
        }
        lastOperator = operators.last()
    }

    fun addNumber(numberArg: BigDecimal) {
        var expressionStringSize = 0
        expression.forEach {
            expressionStringSize += it[1].toString().length
        }

        if (expressionStringSize >= SYMBOL_LIMIT) {
            return
        } else if (numbers.isEmpty() || expression.last()[1] !is BigDecimal) {
            numbers.add(numberArg)
            addMember(numbers.lastIndex, valueBigDecimal = numberArg)
        } else if (expression.last()[1] is BigDecimal) {
            var magnitudePlus = numbers.last().multiply(BigDecimal(10))
            numbers[numbers.lastIndex] = magnitudePlus.add(numberArg)
            setNumber(expression.lastIndex,  numbers.last())
            fullExpression[fullExpression.lastIndex][1] = numbers.last()
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
        expression[index] = mutableListOf(null, null)
        fullExpression[index] = mutableListOf(null, null)
    }

    private fun addMember(index: Int,valueBigDecimal: BigDecimal? = null, valueOperator: Operator? = null) {
        if (valueBigDecimal != null) {
            expression.add(mutableListOf(index, valueBigDecimal))
            fullExpression.add(mutableListOf(index, valueBigDecimal))
        } else if (valueOperator != null) {
            expression.add(mutableListOf(index, valueOperator))
            fullExpression.add(mutableListOf(index, valueOperator))
        } else {
            throw InvalidParameterException("only one null parameter is permitted in this function.")
        }

        size++
    }

    fun get(index: Int): Any? {
        return expression[index]
    }

    fun getList(): MutableList<MutableList<Any?>> {
        return expression
    }

    fun getValueList(): MutableList<Any?> {
        val returnList: MutableList<Any?> = mutableListOf()

        for (i in 0 until expression.size) {
            returnList.add(expression[i][1])
        }

        return returnList
    }

    fun getFull(): Expression {
        var expressionString = ""

        fullExpression.forEach {
            expressionString += it[1].toString()
        }

        val returnExpression = Expression(expressionString)
        return returnExpression
    }

    fun getNumber(index: Int): BigDecimal? {
        if (expression[index][1] !is BigDecimal) {
            return null
        } else {
            return expression[index][1] as BigDecimal
        }
    }

    fun getOperator(index: Int): Operator? {
        if (index < 0) {
            return null
        }
        else if (expression[index][0] == null) {
            return null
        }
        else if (expression[index][1] !is Operator) {
            return null
        } else {
            return expression[index][1] as Operator
        }
    }

    fun getLast(): Any? {
        if (expression.size == 0)
            return null
        else
            return expression.last()[1]
    }

    fun getOperandIndices(index: Int): Pair<Int, Int>? {
        if (expression[index][1] !is Operator) {
            return null
        }

        var loopCtrX = index
        while (expression[loopCtrX][1] !is BigDecimal) {
            if (loopCtrX == 0 || expression[index][1] == null) {
                return null
            }
            loopCtrX--
        }

        var loopCtrY = index
        while (expression[loopCtrY][1] !is BigDecimal) {
            if (loopCtrY == expression.size - 1 || expression[index][1] == null) {
                return null
            }
            loopCtrY++
        }

        return Pair(loopCtrX, loopCtrY)
    }

    fun queueRemove(index: Int) {
        removeQueue.add(index)
    }

    fun removeQueued(): Int {
        removeQueue.sort()
        removeQueue.reverse()
        for (e in removeQueue) {
            expression.removeAt(e)
            size--
        }

        Log.i(TAG, this.toString())

        val removeQueueSize = removeQueue.size
        removeQueue = mutableListOf()

        return removeQueueSize
    }

    fun clear() {
        numbers = mutableListOf()
        operators = mutableListOf()
        expression = mutableListOf()
        size = 0
        addNumber(BigDecimal(0))
    }

    fun clearAll() {
        fullExpression = mutableListOf()
        clear()
    }

    fun remove(index: Int) {
        if (expression.size == 0) {
            return
        }
        else if (expression[index][1] is Operator) {
            operators.removeAt(expression[index][0] as Int)
        } else {
            numbers.removeAt(expression[index][0] as Int)
        }

        expression.removeAt(index)
        size--
    }

    fun solve() {
        val solver = Solver()
        solution = solver.solve(this)
    }

    fun solveHierarchy(hierarchy: Int): BigDecimal {
        var solveUntil = 0
        var lastOperatorIndex = expression.size - 1

        for (i in expression.size - 1..0) {
            if (i >= hierarchy) {
                solveUntil = i
            } else {
                break
            }
        }

        val expressionCopy = Expression(this.toString())
        val solve = Solver().solve(expressionCopy)
        return solve
    }

    //TODO: make a function that only returns numbers, no operators (for the display)

    override fun toString(): String {
        var returnString = ""

        expression.forEachIndexed {i, e ->
            if (e[1] is Operator) {
                returnString += (e[1] as Operator).toString()
            }
            else if (e[1] is BigDecimal)
                returnString += (e[1] as BigDecimal).toString()
        }

        return returnString
    }

}
