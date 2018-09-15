package com.darmaso.sample.android.calculator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val price = intent.getIntExtra("price", 0)
        val discount = intent.getIntExtra("discount", 0)

        val expression = findViewById<TextView>(R.id.expression_label)
        expression.text = getString(R.string.expression, price, discount)

        val discountedPrice = price * (100 - discount) / 100

        val result = findViewById<TextView>(R.id.result_label)
        result.text = getString(R.string.result, discountedPrice)
    }
}
