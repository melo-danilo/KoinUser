package com.dracco.koinusergithub.api.service

import com.dracco.koinusergithub.api.model.response.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("users/{login}")
    suspend fun getUser(
        @Path("login") login: String
    ): Response<User>
}