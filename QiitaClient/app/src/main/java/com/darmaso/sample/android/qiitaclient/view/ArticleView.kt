package com.darmaso.sample.android.qiitaclient.view

import android.content.Context
import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.databinding.DataBindingUtil
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.darmaso.sample.android.qiitaclient.R
import com.darmaso.sample.android.qiitaclient.databinding.ViewArticleBinding
import com.darmaso.sample.android.qiitaclient.model.Article

@BindingMethods(BindingMethod(type = Article::class, attribute = "bind:article", method = "setArticle"))
class ArticleView: FrameLayout {
    constructor(context: Context?): super(context)
    constructor(context: Context?, attrs: AttributeSet?): super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?,
                defStyleAttr: Int): super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?,
                defStyleAttr: Int, defStyleRes: Int): super(context, attrs, defStyleAttr, defStyleRes)

    val binding: ViewArticleBinding

    /*
    val profileImageView: ImageView by lazy {
        findViewById(R.id.profile_image_view) as ImageView
    }
    val titleTextView: TextView by lazy {
        findViewById(R.id.title_text_view) as TextView
    }
    val userNameTextView: TextView by bindView(R.id.user_name_text_view)
    */

    init {
        //LayoutInflater.from(context).inflate(R.layout.view_article, this)
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_article, this, true)
    }

    fun setArticle(article: Article) {
        //titleTextView.text = article.title
        //title_text_view.text = article.title
        //userNameTextView.text = article.user.name
        //Glide.with(context).load(article.user.profileImageUrl).into(profileImageView)
        binding.article = article
    }
}