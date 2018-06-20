package com.diego.tweetssentimentsanalyzer.feature.userDetail.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.diego.tweetssentimentsanalyzer.feature.searchUser.data.SearchUserRepository
import com.diego.tweetssentimentsanalyzer.feature.userDetail.data.UserDetailRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserDetailViewModelFactory(private val repository: UserDetailRepository,
                                 val processScheduler: Scheduler = Schedulers.io(),
                                 val androidScheduler: Scheduler = AndroidSchedulers.mainThread())
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
            return UserDetailViewModel(repository, processScheduler, androidScheduler) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}