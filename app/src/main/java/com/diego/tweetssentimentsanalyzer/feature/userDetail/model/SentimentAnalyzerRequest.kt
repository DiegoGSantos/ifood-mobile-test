package com.diego.tweetssentimentsanalyzer.feature.userDetail.model

data class Document(val content: String, val type: String = "PLAIN_TEXT")

data class SentimentAnalyzerRequest(val document: Document, val encodingType: String = "UTF8")