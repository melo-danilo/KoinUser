package com.dracco.koinusergithub.api.service.useCase

import android.content.Context
import com.dracco.koinusergithub.api.model.response.Repository
import com.dracco.koinusergithub.api.model.response.User
import com.dracco.koinusergithub.api.model.response.UserDetail
import com.dracco.koinusergithub.api.service.SearchService
import com.dracco.koinusergithub.api.service.UserService
import retrofit2.Response

class UserServiceImpl(
    private val getUser: UserService,
): UserService {

    override suspend fun getUsers(): Response<List<User>> {
        return getUser.getUsers()
    }

    override suspend fun getUser(login: String): Response<User> {
        return getUser.getUser(login)
    }

    override suspend fun getUserByUsername(username: String): Response<UserDetail> {
        return getUser.getUserByUsername(username)
    }

    override suspend fun getReposByUsername(username: String): Response<List<Repository>> {
        return getUser.getReposByUsername(username)
    }


}