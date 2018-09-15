package com.darmaso.sample.android.rssreader

import java.io.BufferedInputStream
import java.io.InputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

fun httpGet(url: String): InputStream? {
    // 通信接続用のオプジェクトを作る
    val con = URL(url).openConnection() as HttpsURLConnection

    // 接続の設定を行う
    con.apply {
        requestMethod = "GET"
        connectTimeout = 3000
        readTimeout = 5000
        instanceFollowRedirects = true
    }

    // 接続
    con.connect()

    // ステータスコードの確認
    if (con.responseCode in 200 .. 299) {
        return BufferedInputStream(con.inputStream)
    }

    return null
}