package com.wafflestudio.written.network.service.retrofit

import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.network.dto.title.TitleGetResponse
import com.wafflestudio.written.network.dto.title.TitlePostingGetByIdResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TitleRetrofitService {

    // Title search
    @GET("titles/")
    fun getTitles(
        @Query("time") time: String,
        @Query("order") order: String,
        @Query("official") official: String,
        @Query("query") query: String,
        @Query("cursor") cursor: String?,
        @Query("page_size") pageSize: String
    ): Single<TitleGetResponse>

    @GET("titles/{title_id}/postings/")
    fun getPostingsByTitleId(
        @Path("title_id") titleId: Long
    ): Single<TitlePostingGetByIdResponse>

}
