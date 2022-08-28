package com.example.leboncoin.music.library

sealed interface FetchResult<T>
class FetchSuccess<T>(val items: T) : FetchResult<T>
class FetchError<T>(val message: String, cause: T? = null): FetchResult<T>