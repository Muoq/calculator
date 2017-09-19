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

        var exp1 = Expression("5 + 5 * 5 - (5 * (6- 3) + 7 * 8)")

        Log.i(TAG, "exp1 size: " + exp1.expression.size.toString())

        exp1.expression.forEach { Log.i(TAG, it.toString())}

        Log.i(TAG, "===========")

        exp1.solve()

        exp1.expression.forEach { Log.i(TAG, it.toString());}

        Log.i(TAG, "exp1 size: " + exp1.expression.size.toString());

    }
}
