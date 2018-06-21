package com.tweet.data.remote.naturalLanguage

import com.diego.tweetssentimentsanalyzer.feature.userDetail.model.SentimentAnalyzerRequest
import com.diego.tweetssentimentsanalyzer.feature.userDetail.model.SentimentAnalyzerResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface SentimentAnalyzerApi {

    @POST("/v1/documents:analyzeSentiment")
    fun analyzeTweet(@Body request: SentimentAnalyzerRequest): Observable<SentimentAnalyzerResponse>
}