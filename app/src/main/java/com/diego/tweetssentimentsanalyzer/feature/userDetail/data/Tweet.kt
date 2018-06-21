package com.diego.tweetssentimentsanalyzer.feature.userDetail.data

import com.google.gson.annotations.SerializedName

data class Tweet(@SerializedName("created_at") val createdAt :String,
                 val id:String,
                 val text:String,
                 var sentiment:Int = 0)