package com.dracco.koinusergithub.api.service

import com.dracco.koinusergithub.api.model.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String
    ): Response<SearchResponse>
}