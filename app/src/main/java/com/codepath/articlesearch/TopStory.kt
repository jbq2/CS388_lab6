package com.codepath.articlesearch

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class TopStory(
    @SerialName("title")
    val title: String?,

    @SerialName("byline")
    val byline: String?,

    @SerialName("abstract")
    val abstract: String?
)
