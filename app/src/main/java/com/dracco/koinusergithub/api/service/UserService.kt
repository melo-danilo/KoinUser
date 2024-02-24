package com.dracco.koinusergithub.api.service

import com.dracco.koinusergithub.api.model.response.Repository
import com.dracco.koinusergithub.api.model.response.User
import com.dracco.koinusergithub.api.model.response.UserDetail
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

    @GET("/users/{username}")
    suspend fun getUserByUsername(
        @Path("username") username: String
    ): Response<UserDetail>

    @GET("/users/{username}/repos")
    suspend fun getReposByUsername(
        @Path("username") username: String
    ): Response<List<Repository>>

}