package com.dracco.koinusergithub.api.service.useCase

import com.dracco.koinusergithub.api.model.response.User
import com.dracco.koinusergithub.api.service.UserService
import retrofit2.Response

class UserServiceImpl(
    private val getUser: UserService
): UserService {

    override suspend fun getUsers(): Response<List<User>> {
        return getUser.getUsers()
    }

    override suspend fun getUser(login: String): Response<User> {
        return getUser.getUser(login)
    }
}