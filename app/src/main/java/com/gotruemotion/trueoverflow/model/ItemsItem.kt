package com.gotruemotion.trueoverflow.model

import com.google.gson.annotations.SerializedName

data class ItemsItem(
    @SerializedName("owner") val owner: Owner,
    @SerializedName("score") val score: Int = 0,
    @SerializedName("link") val link: String = "",
    @SerializedName("last_activity_date") val lastActivityDate: Int = 0,
    @SerializedName("is_answered") val isAnswered: Boolean = false,
    @SerializedName("creation_date") val creationDate: Long = 0,
    @SerializedName("answer_count") val answerCount: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("question_id") val questionId: Int = 0,
    @SerializedName("view_count") val viewCount: Int = 0,
    @SerializedName("tags") val tags: List<String>?,
    @SerializedName("last_edit_date") val lastEditDate: Int = 0
)
