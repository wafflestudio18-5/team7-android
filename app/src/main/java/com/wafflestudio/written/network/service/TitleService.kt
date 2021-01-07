package com.wafflestudio.written.network.service

import com.wafflestudio.written.network.service.retrofit.TitleRetrofitService

class TitleService(
    private val titleRetrofitService: TitleRetrofitService
) {

    fun getTitles(
        time: String,
        order: String,
        official: String,
        query: String,
        cursor: String?,
        pageSize: Int = 10
    ) = titleRetrofitService.getTitles(time, order, official, query, cursor, pageSize.toString())

    fun getPostingsByTitleId(
        titleId: Long,
        cursor: String?
    ) = titleRetrofitService.getPostingsByTitleId(titleId, cursor)

}
