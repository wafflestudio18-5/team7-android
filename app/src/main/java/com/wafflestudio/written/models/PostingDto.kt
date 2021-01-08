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
    @Json(name = "created_at")
    val createdAt: String,
    val alignment: String,
    @Json(name = "is_public")
    val isPublic: Boolean
): Parcelable

@Parcelize
data class PostingDtoWriter(
    val id: Int,
    val nickname: String
): Parcelable
