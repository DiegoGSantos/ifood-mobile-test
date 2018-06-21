package com.diego.tweetssentimentsanalyzer.feature.searchUser.model

import com.google.gson.annotations.SerializedName

class User(val name: String,
           @SerializedName("screen_name") val screenName: String,
           @SerializedName("profile_banner_url") val profileBannerUrl: String,
           @SerializedName("profile_image_url_https") val profileImageUrlHttps: String)