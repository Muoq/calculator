package com.muoq.calculator

import android.graphics.Path
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridView

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = "CalculatorApp"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
