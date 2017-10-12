package com.muoq.calculator

import android.graphics.Path
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView
import com.muoq.calculator.logic.Expression
import com.muoq.calculator.logic.Operator
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = "CalculatorApp"
    }

    val expression: Expression = Expression()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

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

        Log.i(TAG, exp1.getExpressionAsString())

        val numButtonList: List<Button> = listOf(findViewById(R.id.btn_0), findViewById(R.id.btn_1),
                                                    findViewById(R.id.btn_2), findViewById(R.id.btn_3),
                                                    findViewById(R.id.btn_4), findViewById(R.id.btn_5),
                                                    findViewById(R.id.btn_6), findViewById(R.id.btn_7),
                                                    findViewById(R.id.btn_8), findViewById(R.id.btn_9))

        for (i in numButtonList) {
            i.setOnClickListener(NumButtonListener())
        }

        val acButton: Button = findViewById(R.id.btn_ac)
        acButton.setOnClickListener(ACButtonListener())

        val delButton: Button = findViewById(R.id.btn_del)
        delButton.setOnClickListener(DelButtonListener())

        val operatorButtonsList: List<Button> = listOf(findViewById(R.id.btn_multiply), findViewById(R.id.btn_divide),
                                                        findViewById(R.id.btn_add), findViewById(R.id.btn_subtract))
        for (i in operatorButtonsList) {
            i.setOnClickListener(OperatorButtonsListener())
        }

        val equalsButton: Button = findViewById(R.id.btn_equals)
        equalsButton.setOnClickListener(EqualsButtonListener())
    }

    fun updateText(viewFinderText: String = "") {
        findViewById<TextView>(R.id.expression_input_view).setText(viewFinderText)

        findViewById<TextView>(R.id.expression_view).setText(expression.getExpressionAsString())
    }

    inner class DelButtonListener : View.OnClickListener {

        override fun onClick(v: View?) {
//
//            if (expression.size > 0) {
//                if (expression.getLast() is BigDecimal) {
//                    if ((expression.getLast() as BigDecimal).toString().length == 1) {
//                        expression.remove(expression.size - 1)
//
//                        if (expression.size == 0) {
//                            expression.addNumber(BigDecimal(0))
//                        }
//                    } else {
//                        var expressionString = expression.numbers.last().toString()
//                        expressionString = expressionString.filterIndexed { index, _ ->
//                            index < expressionString.length - 1 }
//
//                        expression.numbers[expression.numbers.lastIndex] = BigDecimal(expressionString)
//                        expression.setNumber(expression.size - 1, expression.numbers.last())
//                    }
//                } else {
//                    expression.remove(expression.size - 1)
//                }
//            }
//
            expression.delete()

            updateText()
        }

    }

    inner class EqualsButtonListener : View.OnClickListener {

        override fun onClick(v: View?) {

        }

    }

    inner class OperatorButtonsListener : View.OnClickListener {
        override fun onClick(v: View?) {
            if (v != null) {
                when (v.id) {
                    R.id.btn_multiply -> expression.addOperator(Operator(Operator.MULTIPLY))
                    R.id.btn_divide -> expression.addOperator(Operator(Operator.DIVIDE))
                    R.id.btn_add -> expression.addOperator(Operator(Operator.ADD))
                    R.id.btn_subtract -> expression.addOperator(Operator(Operator.SUBTRACT))
                }

                updateText(expression.solveHierarchy(expression.operators.last().second.hierarchy).toString())
            }
        }
    }

    inner class ACButtonListener : View.OnClickListener {
        override fun onClick(v: View?) {
            expression.clearAll()
            updateText()
        }
    }

    inner class NumButtonListener : View.OnClickListener {

        override fun onClick(v: View?) {
            if (v != null) {
                when (v.id) {
                    R.id.btn_0 -> expression.addNumber(BigDecimal(0))
                    R.id.btn_1 -> expression.addNumber(BigDecimal(1))
                    R.id.btn_2 -> expression.addNumber(BigDecimal(2))
                    R.id.btn_3 -> expression.addNumber(BigDecimal(3))
                    R.id.btn_4 -> expression.addNumber(BigDecimal(4))
                    R.id.btn_5 -> expression.addNumber(BigDecimal(5))
                    R.id.btn_6 -> expression.addNumber(BigDecimal(6))
                    R.id.btn_7 -> expression.addNumber(BigDecimal(7))
                    R.id.btn_8 -> expression.addNumber(BigDecimal(8))
                    R.id.btn_9 -> expression.addNumber(BigDecimal(9))
                }

                updateText()
            }
        }

    }
}
