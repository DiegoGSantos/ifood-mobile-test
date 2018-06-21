package com.tweet.data.remote.naturalLanguage

import com.diego.tweetssentimentsanalyzer.feature.userDetail.model.SentimentAnalyzerRequest
import com.diego.tweetssentimentsanalyzer.feature.userDetail.model.SentimentAnalyzerResponse
import com.diego.tweetssentimentsanalyzer.feature.userDetail.sentimentAnalyzer.interceptors.SentimentAnalyzerInterceptor
import com.diego.tweetssentimentsanalyzer.restClient.RetrofitInstance
import io.reactivex.Observable

class SentimentAnalyzerService : RetrofitInstance(){

    private val naturalLanguage: SentimentAnalyzerApi =
            getRetrofit("https://language.googleapis.com", SentimentAnalyzerInterceptor()).create(SentimentAnalyzerApi::class.java)

    fun analyzerTweet(request: SentimentAnalyzerRequest) : Observable<SentimentAnalyzerResponse> = naturalLanguage.analyzeTweet(request)

}