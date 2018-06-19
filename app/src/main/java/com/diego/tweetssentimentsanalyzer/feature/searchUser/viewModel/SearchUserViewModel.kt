package com.diego.tweetssentimentsanalyzer.feature.searchUser.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.diego.tweetssentimentsanalyzer.feature.ScreenStatus
import com.diego.tweetssentimentsanalyzer.feature.searchUser.data.SearchUserRepository
import com.diego.tweetssentimentsanalyzer.feature.searchUser.view.SearchUserScreenState
import com.diego.tweetssentimentsanalyzer.feature.searchUser.view.SearchUserScreenStatus
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class SearchUserViewModel(private val repository: SearchUserRepository, private val processScheduler: Scheduler,
                          private val androidScheduler: Scheduler) : ViewModel() {
    var searchUserScreenState: MutableLiveData<SearchUserScreenState> = MutableLiveData()

    fun searchUser(userName: String) {
        when {
            userName.isEmpty() -> searchUserScreenState.value =
                    SearchUserScreenState(SearchUserScreenStatus.SHOULD_PROVIDE_USER.status, "", null)
            userName.split(" ").size > 1 -> searchUserScreenState.value =
                    SearchUserScreenState(SearchUserScreenStatus.INVALID_USER.status, "", null)
            else -> findUser(userName)
        }
    }

    private var compositeDisposable = CompositeDisposable()

    fun findUser(userName: String) {

        searchUserScreenState.value =
                SearchUserScreenState(ScreenStatus.LOADING.status, "", null)

        var disposable = repository.getUser(userName)
                .observeOn(androidScheduler)
                .subscribeOn(processScheduler)
                .subscribe({
                    user ->
                    searchUserScreenState.value =
                            SearchUserScreenState(ScreenStatus.OK.status, "", user)
                }, { error ->
                    searchUserScreenState.value =
                            SearchUserScreenState(ScreenStatus.ERROR.status, "", null)
                })

        compositeDisposable.add(disposable)
    }

    fun resetState() {
        searchUserScreenState.value =
                SearchUserScreenState(ScreenStatus.NORMAL.status, "", null)
    }

    private fun unSubscribeFromObservable() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun reset() {
        unSubscribeFromObservable()
        compositeDisposable.clear()
    }

    override fun onCleared() {
        reset()
        super.onCleared()
    }
}