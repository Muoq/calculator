package com.muoq.calculator

import android.graphics.Path
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.muoq.calculator.logic.Expression
import com.muoq.calculator.logic.Operator
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = "CalculatorApp"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        var exp1 = Expression("7+8-23*(19/8*(12 + 3 + 14*2) - 25) + 31")
//
//        Log.i(TAG, "exp1 size: " + exp1.mutableExpression.size.toString())
//
//        var logString = ""
//
//        exp1.expression.forEach {logString += it}
//
//        Log.i(TAG, logString)
//
//        println("\n===========")
//
////        Log.i(TAG, "===========")
//
//        val answer = exp1.solve()
//
//        logString = ""
//
//        exp1.mutableExpression.forEach {logString += it}
//
//        Log.i(TAG, logString)
//
//        Log.i(TAG, "Answer: " + answer)
//
//        Log.i(TAG, "exp1 size: " + exp1.mutableExpression.size.toString())

        var op1: Operator = Operator(Operator.DIVIDE)

        Log.i(TAG, op1.operation(BigDecimal(8), BigDecimal(13)).toString())

        Log.i(TAG, op1.hierarchy.toString())

        var exp1 = Expression()

        exp1.addOperator(Operator(Operator.ADD))
        exp1.addNumber(BigDecimal(314))
        exp1.addNumber(BigDecimal(315))
        exp1.addOperator(Operator(Operator.MULTIPLY))
        exp1.addOperator(Operator(Operator.DIVIDE))
        exp1.addOperator(Operator(Operator.O_PARENTHESIS))
        exp1.addOperator(Operator(Operator.SUBTRACT))

        Log.i(TAG, exp1.toString());

//        Log.i(TAG, (exp1.expression[0][1] is Operator).toString())

//        exp1.expression.forEach {Log.i(TAG, it.toString())}

    }
}
