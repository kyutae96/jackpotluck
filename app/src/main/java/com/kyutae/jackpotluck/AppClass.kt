package com.kyutae.jackpotluck

import android.app.Application
import com.kyutae.jackpotluck.DataCenter.PreferenceUtil

class AppClass : Application() {

    companion object {

        lateinit var application: Application
        lateinit var prefs: PreferenceUtil

//        fun ApplicationContext(): Context {
//            return application.applicationContext       // application context 관리
//        }

    }


    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
        application = this
    }


}