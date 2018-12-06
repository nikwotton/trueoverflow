package com.gotruemotion.trueoverflow.service

import com.gotruemotion.trueoverflow.model.SearchResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("/2.2/search")
    fun search(
        @Query("intitle") query: String,
        @Query("order") order: String,
        @Query("sort") sort: String,
        @Query("pagesize") pageSize: Int = 10,
        @Query("site") site: String = "stackoverflow"
    ): Single<SearchResult>
}
