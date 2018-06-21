package com.diego.tweetssentimentsanalyzer.feature.userDetail.model

data class SentimentAnalyzerResponse(val documentSentiment: DocumentSentiment)

data class DocumentSentiment(val magnitude: Double,
                             val score: Double)