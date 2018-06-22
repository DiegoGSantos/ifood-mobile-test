package com.diego.tweetssentimentsanalyzer.feature.userDetail

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.diego.tweetssentimentsanalyzer.feature.ScreenStatus
import com.diego.tweetssentimentsanalyzer.feature.searchUser.model.User
import com.diego.tweetssentimentsanalyzer.feature.userDetail.data.Tweet
import com.diego.tweetssentimentsanalyzer.feature.userDetail.data.TweetSentimentRepository
import com.diego.tweetssentimentsanalyzer.feature.userDetail.data.UserDetailRemoteDataSource
import com.diego.tweetssentimentsanalyzer.feature.userDetail.data.UserDetailRepository
import com.diego.tweetssentimentsanalyzer.feature.userDetail.view.UserDetailScreenState
import com.diego.tweetssentimentsanalyzer.feature.userDetail.viewModel.UserDetailViewModel
import com.diego.tweetssentimentsanalyzer.mock
import com.google.common.collect.Lists
import com.twitter.sdk.android.core.TwitterApiException
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.spy
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response

class UserDetailUnitTest {
    private lateinit var viewModel: UserDetailViewModel
    @Mock lateinit var mockRepository: UserDetailRepository
    @Mock lateinit var mockSentimentRepository: TweetSentimentRepository
    @Mock lateinit var mockDataSource: UserDetailRemoteDataSource
    private val testScheduler = TestScheduler()
    private val mockObserver = mock<Observer<UserDetailScreenState>>()
    lateinit var tweets: List<Tweet>
    val userName = "nytimes"
    @Mock lateinit var exception: TwitterApiException

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        mockRepository = UserDetailRepository(mockDataSource)
        viewModel = spy(UserDetailViewModel(mockRepository, mockSentimentRepository, testScheduler, testScheduler))
        tweets = Lists.newArrayList(Tweet("", "", "This is a mock tweet", 0),
                Tweet("", "", "This is a mock tweet", 0),
                Tweet("", "", "This is a mock tweet", 0))
    }

    @Test
    fun getUserTweetsSuccess(){
        Mockito.`when`(mockDataSource.getUserTweets(userName)).thenReturn(Observable.just(tweets))
        viewModel.userDetailScreenState.observeForever(mockObserver)

        viewModel.getUserTweets(userName)

        handleLoadingProperly()

        assertEquals("Invalid Status", ScreenStatus.OK.status, viewModel.userDetailScreenState.value?.status)
        assertTrue("No tweets Received", viewModel.userDetailScreenState.value?.tweets?.size!! > 0)
    }

    @Test
    fun getUserTweetsNothingFound(){
        Mockito.`when`(mockDataSource.getUserTweets(userName)).thenReturn(Observable.just(emptyList()))
        viewModel.userDetailScreenState.observeForever(mockObserver)

        viewModel.getUserTweets(userName)

        handleLoadingProperly()

        assertEquals("Invalid Status", ScreenStatus.NO_TWEETS_FOUND.status, viewModel.userDetailScreenState.value?.status)
        assertTrue("Should not find tweets", viewModel.userDetailScreenState.value?.tweets?.size == 0)
    }

    @Test
    fun requestErrorTest(){
        Mockito.`when`(mockDataSource.getUserTweets(userName)).thenReturn(getRequestError())
        viewModel.userDetailScreenState.observeForever(mockObserver)

        viewModel.getUserTweets(userName)

        handleLoadingProperly()

        assertEquals("Invalid Status", ScreenStatus.ERROR.status, viewModel.userDetailScreenState.value?.status)
        assertTrue("Should not receive data", viewModel.userDetailScreenState.value?.tweets == null)
    }

    private fun handleLoadingProperly() {
        viewModel.userDetailScreenState.value?.isLoading()?.let {
            Assert.assertTrue("Is loading", it)
        }

        testScheduler.triggerActions()

        viewModel.userDetailScreenState.value?.isLoading()?.let {
            Assert.assertFalse("Finished loading", it)
        }
    }

    private fun getRequestError(): Observable<List<Tweet>> {
        return Observable.error(HttpException(
                Response.error<TwitterApiException>(400, ResponseBody.create(MediaType.parse("application/json"), "Bad Request"))))
    }
}