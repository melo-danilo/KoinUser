package com.dracco.koinusergithub.repository

import com.dracco.koinusergithub.api.model.response.SearchResponse
import com.dracco.koinusergithub.api.model.response.User
import com.dracco.koinusergithub.api.service.useCase.SearchServiceImpl
import com.dracco.koinusergithub.api.service.useCase.UserServiceImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

class SearchRepository(
    private val service: SearchServiceImpl,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getSearch(q: String): Result<SearchResponse?> =
        withContext(dispatcher){
            try {
                val response = service.searchUsers(q)
                when {

                    response.isSuccessful -> {
                        Result.success(response.body())
                    }

                    else -> {
                        Result.failure(Throwable(response.message()))
                    }
                }
            }catch (e: Exception){
                Result.failure(Throwable(e.message))
            }
        }

}