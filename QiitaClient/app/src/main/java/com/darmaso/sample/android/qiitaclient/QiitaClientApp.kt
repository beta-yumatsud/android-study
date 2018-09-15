package com.darmaso.sample.android.qiitaclient

import android.app.Application
import com.darmaso.sample.android.qiitaclient.dagger.AppComponent
import com.darmaso.sample.android.qiitaclient.dagger.DaggerAppComponent

class QiitaClientApp: Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.create()
    }
}