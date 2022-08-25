package com.example.leboncoin

import android.app.Application

class MusicLibraryApplication : Application() {

    val appComponent = DaggerApplicationComponent.create()
}