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
        postingId: Int
    ) = postingRetrofitService.getPostingById(postingId.toString())

    // Posting update
    fun updatePosting(
        postingId: Int,
        content: String,
        alignment: String
    ) = postingRetrofitService.updatePosting(
        postingId.toString(),
        PostingUpdateRequest(content, alignment)
    )

    // TODO : api document is not completed yet
    // Posting delete
    fun deletePosting() = postingRetrofitService.deletePosting()


}
