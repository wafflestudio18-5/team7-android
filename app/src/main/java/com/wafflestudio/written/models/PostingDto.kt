package com.wafflestudio.written.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostingDto(
    val writer: PostingDtoWriter,
    val id: Int,
    val title: String,
    val content: String,
    @field:Json(name = "created_at")
    val createdAt: String
): Parcelable

@Parcelize
data class PostingDtoWriter(
    val id: Int,
    val nickname: String
): Parcelable
