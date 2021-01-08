package com.wafflestudio.written.network.service.retrofit

import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.network.dto.posting.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface PostingRetrofitService {

    // Posting get Today
    @GET("titles/today/")
    fun getPostingToday(): Single<PostingTodayResponse>

    @POST("titles/")
    fun createTitle(
        @Body Body: PostingTitleRequest
    ): Single<PostingTitleResponse>

    // Posting write
    @POST("postings/")
    fun writePosting(
        @Body Body: PostingWriteRequest
    ): Single<PostingDto>

    // Posting get {posting_id}
    @GET("postings/{posting_id}/")
    fun getPostingById(
        @Path("posting_id") postingId: Long
    ): Single<PostingDto>

    // Posting update
    @PUT("postings/{posting_id}/")
    fun updatePosting(
        @Path("posting_id") postingId: Long,
        @Body Body: PostingUpdateRequest
    ): Single<PostingDto>

    // Posting delete
    @DELETE("postings/{posting_id}/")
    fun deletePosting(
        @Path("posting_id") postingId: Long
    ): Completable

    // Posting scrap
    @POST("postings/{posting_id}/scrap/")
    fun scrapPosting(
        @Path("posting_id") postingId: Long
    ): Completable

    // Posting unscrap
    @POST("postings/{posting_id}/unscrap/")
    fun unscrapPosting(
        @Path("posting_id") postingId: Long
    ): Completable

    // Posting get scrapped
    @GET("postings/scrapped/")
    fun getScrappedPostings(
        @Query("cursor") cursor: String?,
        @Query("page_size") pageSize: String
    ): Single<PostingScrappedResponse>

    // Posting get subscribed
    @GET("postings/subscribed/")
    fun getSubscribedPostings(
        @Query("cursor") cursor: String?,
        @Query("page_size") pageSize: String
    ): Single<PostingSubscribedResponse>
}
