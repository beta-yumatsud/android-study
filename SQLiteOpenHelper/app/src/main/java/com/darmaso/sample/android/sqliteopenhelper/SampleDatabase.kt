package com.darmaso.sample.android.sqliteopenhelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val DB_NAME = "SampleDatabase"
private const val DB_VERSION = 1

class SampleDBOpenHelper(context: Context) :
        SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        // テーブルの作成
        db?.execSQL("CREATE TABLE texts ( " +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " text TEXT NOT NULL, " +
                " created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // バージョン更新時のSQL発行
    }

}

fun queryTexts(context: Context) : List<String> {
    // 読み込み用のDBを開く
    val database = SampleDBOpenHelper(context).readableDatabase
    // DBから全件検索
    val cursor = database.query(
            "texts", null, null, null, null, null, "created_at DESC"
    )

    val texts = mutableListOf<String>()
    cursor.use {
        // カーソルで順次処理
        while (cursor.moveToNext()) {
            // 保存テキストを取得
            val text = cursor.getString(cursor.getColumnIndex("text"))
            texts.add(text)
        }
    }
    database.close()
    return texts
}

fun insertText(context: Context, text: String) {
    // 書き込み用のDBを開く
    val database = SampleDBOpenHelper(context).readableDatabase

    database.use {
        // 挿入レコード
        val record = ContentValues().apply {
            put("text", text)
        }
        it.insert("texts", null, record)
    }
}