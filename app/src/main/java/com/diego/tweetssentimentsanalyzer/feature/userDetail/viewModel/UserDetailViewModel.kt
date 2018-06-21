package com.diego.tweetssentimentsanalyzer.feature.userDetail.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.diego.tweetssentimentsanalyzer.feature.ScreenStatus
import com.diego.tweetssentimentsanalyzer.feature.userDetail.data.TweetSentimentRepository
import com.diego.tweetssentimentsanalyzer.feature.userDetail.data.UserDetailRepository
import com.diego.tweetssentimentsanalyzer.feature.userDetail.view.UserDetailScreenState
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import java.text.FieldPosition

class UserDetailViewModel(private val repository: UserDetailRepository, private val tweetSentimentRepository: TweetSentimentRepository,
                          private val processScheduler: Scheduler, private val androidScheduler: Scheduler) : ViewModel() {
    var userDetailScreenState: MutableLiveData<UserDetailScreenState> = MutableLiveData()

    private var compositeDisposable = CompositeDisposable()

    fun getUserTweets(userName: String) {

        userDetailScreenState.value =
                UserDetailScreenState(ScreenStatus.LOADING.status, "", null)

        var disposable = repository.getUserTweets(userName)
                .observeOn(androidScheduler)
                .subscribeOn(processScheduler)
                .subscribe({
                    tweets ->
                    userDetailScreenState.value =
                            UserDetailScreenState(ScreenStatus.OK.status, "", tweets)
                }, { error ->
                    userDetailScreenState.value =
                            UserDetailScreenState(ScreenStatus.ERROR.status, "", null)
                })

        compositeDisposable.add(disposable)
    }

    fun analyzeTweetSentiment(position: Int, tweet: String) {
        var disposable = tweetSentimentRepository.analyzeTweet(tweet)
                .observeOn(androidScheduler)
                .subscribeOn(processScheduler)
                .subscribe({
                    result ->
                    userDetailScreenState.value?.tweets?.get(position)?.sentiment =
                            tweetSentimentRepository.getSentimentFromScore(result.documentSentiment.score)

                    userDetailScreenState.value =
                            UserDetailScreenState(ScreenStatus.OK.status, "", userDetailScreenState.value?.tweets)
                }, {
                    error ->
                    error.printStackTrace()
                })

        compositeDisposable.add(disposable)

    }

    fun resetState() {
        userDetailScreenState.value =
                UserDetailScreenState(ScreenStatus.NORMAL.status, "", null)
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