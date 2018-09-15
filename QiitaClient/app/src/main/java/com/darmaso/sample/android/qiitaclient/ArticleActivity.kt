package com.darmaso.sample.android.qiitaclient

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import com.darmaso.sample.android.qiitaclient.model.Article
import com.darmaso.sample.android.qiitaclient.view.ArticleView
import kotlin.jvm.java

class ArticleActivity: AppCompatActivity() {
    companion object {
        private const val ARTICLE_EXTRA: String = "article"

        fun intent(context: Context, article: Article): Intent =
                Intent(context, ArticleActivity::class.java).putExtra(ARTICLE_EXTRA, article)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val articleView = findViewById<ArticleView>(R.id.article_view)
        val webView = findViewById<WebView>(R.id.web_view)

        val article: Article = intent.getParcelableExtra(ARTICLE_EXTRA)
        articleView.setArticle(article)
        webView.loadUrl(article.url)
    }
}