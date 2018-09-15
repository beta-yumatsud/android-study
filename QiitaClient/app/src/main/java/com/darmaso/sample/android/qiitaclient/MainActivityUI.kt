package com.darmaso.sample.android.qiitaclient

import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.ProgressBar
import org.jetbrains.anko.*
import kotlin.properties.Delegates

class MainActivityUI : AnkoComponent<MainActivity> {
    var listView: ListView by Delegates.notNull()
        private set
    var progressBar: ProgressBar by Delegates.notNull()
        private set
    var searchButton: Button by Delegates.notNull()
        private set
    var queryEditText: EditText by Delegates.notNull()
        private set

    override fun createView(ui: AnkoContext<MainActivity>): View = ui.run {
        verticalLayout {
            frameLayout {
                listView = listView().lparams(matchParent, matchParent)
                progressBar = progressBar {
                    //gravity = Gravity.CENTER
                    visibility = View.GONE
                }
            }.lparams(width = matchParent, height = 0, weight = 1f)

            linearLayout {
                queryEditText = editText().lparams(width = 0, weight = 1f)
                searchButton = button("検索")
            }.lparams(width = matchParent) {
                gravity = Gravity.BOTTOM
            }
        }
    }
}