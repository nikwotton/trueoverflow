package com.gotruemotion.trueoverflow.model

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("quota_max") val quotaMax: Int = 0,
    @SerializedName("quota_remaining") val quotaRemaining: Int = 0,
    @SerializedName("has_more") val hasMore: Boolean = false,
    @SerializedName("items") val items: List<ItemsItem>?
)
