package com.diego.tweetssentimentsanalyzer.di

import com.diego.tweetssentimentsanalyzer.feature.searchUser.data.SearchUserRemoteDataSource
import com.diego.tweetssentimentsanalyzer.feature.searchUser.data.SearchUserRepository
import com.diego.tweetssentimentsanalyzer.feature.searchUser.viewModel.SearchUserViewModelFactory
import com.diego.tweetssentimentsanalyzer.feature.userDetail.data.UserDetailRemoteDataSource
import com.diego.tweetssentimentsanalyzer.feature.userDetail.data.UserDetailRepository
import com.diego.tweetssentimentsanalyzer.feature.userDetail.viewModel.UserDetailViewModelFactory
import com.diego.tweetssentimentsanalyzer.manager.NetManager
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

val module : Module = applicationContext {
    factory { SearchUserViewModelFactory(get()) }
    bean { SearchUserRepository(get()) }
    bean { NetManager() }
    bean { SearchUserRemoteDataSource() }

    factory { UserDetailViewModelFactory(get()) }
    bean { UserDetailRepository(get()) }
    bean { UserDetailRemoteDataSource() }
}