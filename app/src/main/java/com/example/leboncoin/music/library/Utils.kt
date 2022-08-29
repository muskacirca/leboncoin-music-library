package com.example.leboncoin.music.library

import android.view.View

sealed interface FetchResult<T>
class FetchSuccess<T>(val items: T) : FetchResult<T>
class FetchError<T>(val message: String, cause: T? = null): FetchResult<T>

interface OnItemClickListener<T> {
    fun onItemClick(view: View, item: T)
}