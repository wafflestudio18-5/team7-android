package com.wafflestudio.written.network.dto.title

import com.squareup.moshi.Json
import com.wafflestudio.written.models.TitleDto

data class TitleGetResponse(
    val titles: List<TitleDto>,
    @field:Json(name = "has_next")
    val hasNext: Boolean,
    val cursor: String?
)
