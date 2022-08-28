package com.example.leboncoin

import com.example.leboncoin.modules.NetworkModule
import dagger.Component

@Component(modules = [
    NetworkModule::class])
interface ApplicationComponent {

    fun inject(activity: MusicLibraryActivity)
}