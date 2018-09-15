package com.darmaso.sample.android.sqliteopenhelper

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val editText = findViewById<EditText>(R.id.editText)
            insertText(this, editText.text.toString())
            show()
        }
        show()
    }

    private fun show() {
        // DBに登録されている文字列の一覧を得る
        val texts = queryTexts(this)
        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = ArrayAdapter<String>(this, R.layout.list_text_row, R.id.textView, texts)
    }
}
