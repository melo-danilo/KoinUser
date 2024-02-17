package com.dracco.koinusergithub.api.service

import com.dracco.koinusergithub.api.model.response.SearchResponse
import retrofit2.Response

class SearchServiceImpl(
    private val searchService: SearchService
) : SearchService {

    override suspend fun searchUsers(query: String): Response<SearchResponse> {
        return searchService.searchUsers(query)
    }

}