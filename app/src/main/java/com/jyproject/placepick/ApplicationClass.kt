package com.jyproject.placepick

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}