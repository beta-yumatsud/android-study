package com.darmaso.sample.android.calculator

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.calculator)
        button.setOnClickListener {
            var isValid = true

            val priceEditText = findViewById<EditText>(R.id.price)
            val priceText = priceEditText.text.toString()
            if (priceText.isEmpty()) {
                // 定価が未入力だぜ
                priceEditText.error = getString(R.string.price_error)
                isValid = false
            }

            val discountEditText = findViewById<EditText>(R.id.discount)
            val discountText = discountEditText.text.toString()
            if (discountText.isEmpty()) {
                // 割引率が未入力だっちゃね
                discountEditText.error = getString(R.string.discount_error)
                isValid = false
            }

            if (isValid) {
                // 文字列を整数型に変換
                val price = Integer.parseInt(priceText)
                val discount = Integer.parseInt(discountText)

                // 画面遷移
                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra("price", price)
                intent.putExtra("discount", discount)
                startActivity(intent)
            }
        }
    }
}
