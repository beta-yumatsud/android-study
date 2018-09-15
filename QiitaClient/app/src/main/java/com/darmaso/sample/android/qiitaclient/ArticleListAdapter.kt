package com.darmaso.sample.android.qiitaclient

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.darmaso.sample.android.qiitaclient.model.Article
import com.darmaso.sample.android.qiitaclient.view.ArticleView

class ArticleListAdapter(private val context: Context): BaseAdapter() {
    var articles: List<Article> = emptyList()
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View =
            ((convertView as? ArticleView) ?: ArticleView(context)).apply {
                setArticle(articles[position])
            }

    override fun getItem(position: Int): Any? = articles[position]

    override fun getItemId(position: Int): Long = 0

    override fun getCount(): Int = articles.size
}