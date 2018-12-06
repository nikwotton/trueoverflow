package com.gotruemotion.trueoverflow.model

import com.google.gson.annotations.SerializedName

data class Owner(
    @SerializedName("profile_image") val profileImage: String = "",
    @SerializedName("user_type") val userType: String = "",
    @SerializedName("user_id") val userId: Int = 0,
    @SerializedName("link") val link: String = "",
    @SerializedName("reputation") val reputation: Int = 0,
    @SerializedName("display_name") val displayName: String = ""
)
