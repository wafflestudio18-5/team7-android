package com.wafflestudio.written.network.service.retrofit

import com.wafflestudio.written.models.PostingDto
import com.wafflestudio.written.network.dto.posting.PostingTodayResponse
import com.wafflestudio.written.network.dto.posting.PostingUpdateRequest
import com.wafflestudio.written.network.dto.posting.PostingWriteRequest
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface PostingRetrofitService {

    // Posting get Today
    @GET("postings/today")
    fun getPostingToday(): Single<PostingTodayResponse>

    // Posting write
    @POST("postings")
    fun writePosting(
        @Body Body: PostingWriteRequest
    ): Single<PostingDto>

    // Posting get {posting_id}
    @GET("postings/{posting_id}")
    fun getPostingById(
        @Path("posting_id") postingId: Long
    ): Single<PostingDto>

    // Posting update
    @PUT("postings/{posting_id}")
    fun updatePosting(
        @Path("posting_id") postingId: Long,
        @Body Body: PostingUpdateRequest
    ): Single<PostingDto>

    // TODO : api document is not completed yet
    // Posting delete
    @DELETE("postings/{posting_id}")
    fun deletePosting()

}
