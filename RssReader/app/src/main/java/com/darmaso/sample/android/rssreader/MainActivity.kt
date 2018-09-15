package com.darmaso.sample.android.rssreader

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Rss> {
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Rss> = RssLoader(this)

    override fun onLoadFinished(loader: Loader<Rss>, data: Rss?) {
        if (data != null) {
            val recyclerView = findViewById<RecyclerView>(R.id.articles)
            val adapter = ArticleAdapter(this, data.articles) {
                // タップした時の処理
                val intent = CustomTabsIntent.Builder().build()
                intent.launchUrl(this, Uri.parse(it.link))
            }
            recyclerView.adapter = adapter

            val layoutManager = GridLayoutManager(this, 2)
            recyclerView.layoutManager = layoutManager
        }
    }

    override fun onLoaderReset(p0: Loader<Rss>) {
        // nothing to do
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportLoaderManager.initLoader(1, null, this)

        createChannel(this)

        // 定期的に新しい記事がないかチェックするジョブ
        val fetchJob = JobInfo.Builder(1, ComponentName(this, PollingJob::class.java))
                .setPeriodic(TimeUnit.HOURS.toMillis(6)) // 6時間ごとに実行
                .setPersisted(true) // 端末を再起動しても有効
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // ネットワークに接続されていること
                .build()

        // ジョブを登録する
        getSystemService(JobScheduler::class.java).schedule(fetchJob)
    }
}
