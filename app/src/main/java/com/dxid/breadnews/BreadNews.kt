package com.dxid.breadnews

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BreadNews : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}