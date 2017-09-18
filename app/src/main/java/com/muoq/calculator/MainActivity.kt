package com.muoq.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.muoq.calculator.logic.Expression

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = "MainCalcApp"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val exp = Expression()

        exp.main()
    }
}
