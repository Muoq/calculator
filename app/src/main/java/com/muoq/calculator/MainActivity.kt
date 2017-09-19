package com.muoq.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.muoq.calculator.logic.Expression

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = "CalculatorApp"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var exp1 = Expression("5*5*(5*5*9*1*(4/2/(1+1)) - 122)")

        Log.i(TAG, "exp1 size: " + exp1.mutableExpression.size.toString())

        var logString = ""

        exp1.expression.forEach {logString += it}

        Log.i(TAG, logString)

        println("\n===========")

//        Log.i(TAG, "===========")

        val answer = exp1.solve()

        logString = ""

        exp1.mutableExpression.forEach {logString += it}

        Log.i(TAG, logString);

        Log.i(TAG, "Answer: " + answer);

        Log.i(TAG, "exp1 size: " + exp1.mutableExpression.size.toString());

    }
}
