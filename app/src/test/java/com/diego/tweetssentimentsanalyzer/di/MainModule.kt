package com.diego.tweetssentimentsanalyzer.di

import com.diego.tweetssentimentsanalyzer.feature.searchUser.data.SearchUserRemoteDataSource
import com.diego.tweetssentimentsanalyzer.feature.searchUser.data.SearchUserRepository
import com.diego.tweetssentimentsanalyzer.feature.searchUser.viewModel.SearchUserViewModel
import com.diego.tweetssentimentsanalyzer.feature.searchUser.viewModel.SearchUserViewModelFactory
import com.diego.tweetssentimentsanalyzer.feature.userDetail.data.TweetSentimentRepository
import com.diego.tweetssentimentsanalyzer.feature.userDetail.data.UserDetailRemoteDataSource
import com.diego.tweetssentimentsanalyzer.feature.userDetail.data.UserDetailRepository
import com.diego.tweetssentimentsanalyzer.feature.userDetail.viewModel.UserDetailViewModelFactory
import com.diego.tweetssentimentsanalyzer.manager.NetManager
import com.diego.tweetssentimentsanalyzer.twitterRestClient.CustomTwitterApiClient
import com.diego.tweetssentimentsanalyzer.twitterRestClient.MockInterceptor
import com.tweet.data.remote.naturalLanguage.SentimentAnalyzerService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

val module : Module = applicationContext {
//    factory { SearchUserViewModelFactory(get()) }
    factory { SearchUserViewModel(get(), get(), get()) }
    bean { SearchUserRepository(get()) }
    bean { NetManager() }
    bean { SearchUserRemoteDataSource(get()) }

    factory { UserDetailViewModelFactory(get(), get()) }
    bean { UserDetailRepository(get()) }
    bean { UserDetailRemoteDataSource(get()) }
    bean { TweetSentimentRepository(get()) }
    bean { SentimentAnalyzerService() }

    bean { CustomTwitterApiClient(get()) }
    val interceptor = MockInterceptor()
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    bean { OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(interceptor)
            .build() }
}