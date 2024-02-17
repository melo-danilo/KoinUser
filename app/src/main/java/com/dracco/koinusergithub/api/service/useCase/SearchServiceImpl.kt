package com.dracco.koinusergithub.api.service.useCase

import com.dracco.koinusergithub.api.model.response.SearchResponse
import com.dracco.koinusergithub.api.service.SearchService
import retrofit2.Response

class SearchServiceImpl(
    private val searchService: SearchService
) : SearchService {

    override suspend fun searchUsers(query: String): Response<SearchResponse> {
        return searchService.searchUsers(query)
    }

}