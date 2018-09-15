package com.darmaso.sample.android.memo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.io.File

class FileAdapter(private val context: Context,
                  private val files: List<File>, // ファイルの一覧
                  private val onFileClicked: (File) -> Unit) // タップ時のcallback
    : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): FileViewHolder {
        val view = inflater.inflate(R.layout.list_itme_row, parent, false)
        val viewHolder = FileViewHolder(view)
        view.setOnClickListener {
            // タップされた位置に対応するメモを得る
            val file = files[viewHolder.adapterPosition]
            // コールバックを呼ぶ
            onFileClicked(file)
        }
        return viewHolder
    }

    override fun getItemCount(): Int = files.size

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = files[position]
        holder.title.text = file.name
        holder.updatedTime.text = context.getString(R.string.last_modified, file.lastModified())
    }

    private val inflater = LayoutInflater.from(context)

    class FileViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.title)
        val updatedTime = view.findViewById<TextView>(R.id.lastModified)
    }
}