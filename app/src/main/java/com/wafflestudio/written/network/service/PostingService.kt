package com.wafflestudio.written.network.service

import com.wafflestudio.written.network.dto.posting.PostingUpdateRequest
import com.wafflestudio.written.network.dto.posting.PostingWriteRequest
import com.wafflestudio.written.network.service.retrofit.PostingRetrofitService

class PostingService (
    private val postingRetrofitService: PostingRetrofitService
) {

    // Posting get Today
    fun getPostingToday() = postingRetrofitService.getPostingToday()

    // Posting write
    fun writePosting(
        title: String,
        content: String,
        alignment: String
    ) = postingRetrofitService.writePosting(PostingWriteRequest(title, content, alignment))

    // Posting get {posting_id}
    fun getPostingById(
        postingId: Long
    ) = postingRetrofitService.getPostingById(postingId)

    // Posting update
    fun updatePosting(
        postingId: Long,
        content: String,
        alignment: String
    ) = postingRetrofitService.updatePosting(
        postingId,
        PostingUpdateRequest(content, alignment)
    )

    // Posting delete
    fun deletePosting(postingId: Long) = postingRetrofitService.deletePosting(postingId)

    // Posting scrap
    fun scrapPosting(postingId: Long) = postingRetrofitService.scrapPosting(postingId)

    // Posting unscrap
    fun unscrapPosting(postingId: Long) = postingRetrofitService.unscrapPosting(postingId)

    // Posting get scrapped
    fun getScrappedPostings(
        cursor: String?,
        pageSize: Int = 5
    ) = postingRetrofitService.getScrappedPostings(cursor, pageSize.toString())

    // Posting get subscribed
    fun getSubscribedPostings(
        cursor: String?,
        pageSize: Int = 5
    ) = postingRetrofitService.getSubscribedPostings(cursor, pageSize.toString())
}
