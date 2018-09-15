package com.darmaso.sample.android.qiitaclient.dagger

import com.darmaso.sample.android.qiitaclient.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [(ClientModule::class)])
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}
