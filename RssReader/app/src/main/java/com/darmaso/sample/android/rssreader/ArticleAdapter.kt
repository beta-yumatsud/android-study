package com.darmaso.sample.android.rssreader

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ArticleAdapter(
        private val context: Context,
        private val articles: List<Article>,
        private val onArticleClicked: (Article) -> Unit): RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ArticleViewHolder {
        val view = inflater.inflate(R.layout.grid_article_cell, parent, false)
        val viewHolder = ArticleViewHolder(view)

        view.setOnClickListener {
            val position = viewHolder.adapterPosition
            val article = articles[position]
            onArticleClicked(article)
        }
        return viewHolder
    }

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(viewHolder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        viewHolder.title.text = article.title
        viewHolder.pubDate.text = context.getString(R.string.pubDate, article.pubDate)
    }

    class ArticleViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.title)
        val pubDate = view.findViewById<TextView>(R.id.pubDate)
    }
}