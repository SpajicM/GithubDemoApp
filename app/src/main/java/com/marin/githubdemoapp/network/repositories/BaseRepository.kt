package com.marin.githubdemoapp.network.repositories

import android.util.Log
import com.marin.githubdemoapp.utils.Result
import retrofit2.Response

abstract class BaseRepository {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.Success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Result<T> {
        Log.e("Network call error: ", message)
        return Result.Error(Exception("Network call has failed: $message"))
    }
}