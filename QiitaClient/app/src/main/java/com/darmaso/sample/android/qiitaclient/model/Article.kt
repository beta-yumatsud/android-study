package com.darmaso.sample.android.qiitaclient.model

import android.os.Parcel
import android.os.Parcelable

data class Article(val id: String,
                   val title: String,
                   val url: String,
                   val user: User) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeParcelable(user, flags)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Article> = object : Parcelable.Creator<Article> {
            override fun createFromParcel(parcel: Parcel): Article = parcel.run {
                Article(readString(), readString(), readString(), readParcelable(Article::class.java.classLoader))
            }

            override fun newArray(size: Int): Array<Article?> = arrayOfNulls(size)
        }
    }
}