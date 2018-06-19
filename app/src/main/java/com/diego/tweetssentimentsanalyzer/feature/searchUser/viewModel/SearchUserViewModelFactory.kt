package com.diego.tweetssentimentsanalyzer.feature.searchUser.viewModel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.diego.tweetssentimentsanalyzer.feature.searchUser.data.SearchUserRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchUserViewModelFactory(private val repository: SearchUserRepository,
                                 val processScheduler: Scheduler = Schedulers.io(),
                                 val androidScheduler: Scheduler = AndroidSchedulers.mainThread())
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchUserViewModel::class.java)) {
            return SearchUserViewModel(repository, processScheduler, androidScheduler) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}