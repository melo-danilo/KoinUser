package com.dracco.koinusergithub.repository

import com.dracco.koinusergithub.api.model.response.Repository
import com.dracco.koinusergithub.api.model.response.User
import com.dracco.koinusergithub.api.model.response.UserDetail
import com.dracco.koinusergithub.api.service.useCase.UserServiceImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception

class UserRepository(
    private val service: UserServiceImpl,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun getUsers(): Result<List<User>?> =
        withContext(dispatcher){
            try {
                val response = service.getUsers()
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

    suspend fun getUserByUsername(userName: String): Result<UserDetail?> =
        withContext(dispatcher){
            try {
                val response = service.getUserByUsername(userName)
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

    suspend fun getReposByUsername(userName: String): Result<List<Repository>?> =
        withContext(dispatcher){
            try {
                val response = service.getReposByUsername(userName)
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