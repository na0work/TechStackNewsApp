package com.example.news.data

import com.example.news.data.RequestResult.*

interface MergeStrategy<E> {

    fun merge(right: E, left: E): E
}

internal class RequestResponseMergeStrategy<T: Any> : MergeStrategy<RequestResult<T>> {

    override fun merge(right: RequestResult<T>, left: RequestResult<T>): RequestResult<T> {
        return when {
            right is InProgress && left is InProgress -> merge(right, left)
            right is InProgress && left is Success -> merge(right, left)
            right is InProgress && left is Error -> merge(right, left)
            right is Success && left is InProgress -> merge(right, left)
            right is Success && left is Success -> merge(right, left)
            right is Success && left is Error -> merge(right, left)
            right is Error && left is InProgress -> merge(right, left)
            right is Error && left is Success -> merge(right, left)
            right is Error && left is Error -> merge(right, left)

            else -> error("Unimplemented branch  right=$right & left=$left")
        }
    }

    private fun merge(
        cache: InProgress<T>,
        server: InProgress<T>
    ): RequestResult<T> {
        return when {
            server.data != null ->
                InProgress(server.data)
            else ->
                InProgress(cache.data)
        }
    }

    private fun merge(
        cache: InProgress<T>,
        server: Success<T>
    ): RequestResult<T> {
        return InProgress(server.data)
    }

    private fun merge(
        cache: InProgress<T>,
        server: Error<T>
    ): RequestResult<T> {
        return Error(server.data ?: cache.data, server.error)
    }
}