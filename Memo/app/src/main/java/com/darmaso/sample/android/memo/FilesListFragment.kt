package com.darmaso.sample.android.memo

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.io.File

class FilesListFragment : Fragment() {
    // メモをタップした時のコールバックインターフェース
    interface OnFileSelectListener {
        fun onFileSelected(file: File)
    }
    private lateinit var recyclerView: RecyclerView

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        // Activitiyがコールバックを実装しているかチェック
        if (context !is OnFileSelectListener)
            throw RuntimeException("$context must implement OnFileSelectListener")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.findViewById(R.id.filesList)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        show()
        return view
    }

    fun show() {
        // contextのnullチェック
        val ctx = context ?: return
        // ファイル一覧を表示するアダプター
        val adapter = FileAdapter(ctx, getFiles()) {
            // タップされたらコールバックを呼ぶ
            (context as OnFileSelectListener).onFileSelected(it)
        }
        recyclerView.adapter = adapter
    }
}