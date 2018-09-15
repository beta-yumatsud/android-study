package com.darmaso.sample.android.qiitaclient

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.ProgressBar
import com.darmaso.sample.android.qiitaclient.client.ArticleClient
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : RxAppCompatActivity() {

    @Inject
    lateinit var articleClient: ArticleClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as QiitaClientApp).component.inject(this)
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.list_view) as ListView
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val queryEditText = findViewById<EditText>(R.id.query_edit_text)
        val searchButton = findViewById<Button>(R.id.search_button)

        val listAdapter = ArticleListAdapter(applicationContext)
        listView.adapter = listAdapter
        listView.setOnItemClickListener { parent, view, position, id ->
            val article = listAdapter.articles[position]
            ArticleActivity.intent(this, article).let { startActivity(it) }
        }

        searchButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            articleClient.search(queryEditText.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate {
                        progressBar.visibility = View.GONE
                    }
                    .bindToLifecycle(this)
                    .subscribe({
                        queryEditText.text.clear()
                        listAdapter.articles = it
                        listAdapter.notifyDataSetChanged()
                    }, {
                        toast("エラー: $it")
                    })
        }
    }
}
