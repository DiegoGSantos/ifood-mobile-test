package com.diego.tweetssentimentsanalyzer.feature.userDetail.data

import com.diego.tweetssentimentsanalyzer.feature.userDetail.model.Document
import com.diego.tweetssentimentsanalyzer.feature.userDetail.model.SentimentAnalyzerRequest
import com.diego.tweetssentimentsanalyzer.feature.userDetail.model.SentimentAnalyzerResponse
import com.diego.tweetssentimentsanalyzer.feature.userDetail.sentimentAnalyzer.naturalLanguage.Sentiments
import com.tweet.data.remote.naturalLanguage.SentimentAnalyzerService

class TweetSentimentRepository(private val sentimentAnalyzerService: SentimentAnalyzerService) {
    private val NEUTRAL_RANGE = -0.25..0.25
    private val SAD_RANGE = -1.0..-0.25
    private val HAPPY_RANGE = 0.25..1.0

    fun analyzeTweet(tweet: String): io.reactivex.Observable<SentimentAnalyzerResponse> {
        val request = SentimentAnalyzerRequest(Document(tweet))
        return sentimentAnalyzerService.analyzerTweet(request)
    }

    fun getSentimentFromScore(score: Double): Int {
        return when (score) {
            in NEUTRAL_RANGE -> Sentiments.NEUTRAL.sentiment
            in SAD_RANGE -> Sentiments.SAD.sentiment
            in HAPPY_RANGE -> Sentiments.HAPPY.sentiment
            else -> Sentiments.NEUTRAL.sentiment
        }
    }
}