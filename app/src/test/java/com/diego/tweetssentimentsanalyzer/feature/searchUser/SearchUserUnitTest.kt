package com.diego.tweetssentimentsanalyzer.feature.searchUser

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.diego.tweetssentimentsanalyzer.feature.ScreenStatus
import com.diego.tweetssentimentsanalyzer.feature.searchUser.data.SearchUserRemoteDataSource
import com.diego.tweetssentimentsanalyzer.feature.searchUser.data.SearchUserRepository
import com.diego.tweetssentimentsanalyzer.feature.searchUser.model.User
import com.diego.tweetssentimentsanalyzer.feature.searchUser.view.SearchUserScreenState
import com.diego.tweetssentimentsanalyzer.feature.searchUser.view.SearchUserScreenStatus
import com.diego.tweetssentimentsanalyzer.feature.searchUser.viewModel.SearchUserViewModel
import com.diego.tweetssentimentsanalyzer.mock
import com.twitter.sdk.android.core.TwitterApiException
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.*
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

class SearchUserUnitTest {
    private lateinit var viewModel: SearchUserViewModel
    @Mock lateinit var mockRepository: SearchUserRepository
    @Mock lateinit var mockDataSource: SearchUserRemoteDataSource
    private val testScheduler = TestScheduler()
    private val mockObserver = mock<Observer<SearchUserScreenState>>()
    val user = User("The New York Times", "nytimes", "", "")
    val userName = "nytimes"
    @Mock lateinit var exception: TwitterApiException

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        mockRepository = SearchUserRepository(mockDataSource)
        viewModel = spy(SearchUserViewModel(mockRepository, testScheduler, testScheduler))
    }

    @Test
    fun searchValidUserTest(){
        Mockito.`when`(mockDataSource.getUser(userName)).thenReturn(Observable.just(user))
        viewModel.searchUserScreenState.observeForever(mockObserver)

        viewModel.searchUser(userName)

        handleLoadingProperly()

        assertEquals("Invalid Status", ScreenStatus.OK.status, viewModel.searchUserScreenState.value?.status)
        assertTrue("No data found", viewModel.searchUserScreenState.value?.user != null)
    }

    @Test
    fun searchNoUserTest(){
        viewModel.searchUserScreenState.observeForever(mockObserver)

        viewModel.searchUser("")

        assertEquals("Invalid Status", SearchUserScreenStatus.SHOULD_PROVIDE_USER.status, viewModel.searchUserScreenState.value?.status)
        assertTrue("Should not receive data", viewModel.searchUserScreenState.value?.user == null)
    }

    @Test
    fun searchInValidUserTest(){
        viewModel.searchUserScreenState.observeForever(mockObserver)

        viewModel.searchUser("ny times")

        assertEquals("Invalid Status", SearchUserScreenStatus.INVALID_USER.status, viewModel.searchUserScreenState.value?.status)
        assertTrue("Should not receive data", viewModel.searchUserScreenState.value?.user == null)
    }

    @Test
    fun requestErrorTest(){
        Mockito.`when`(mockRepository.getUser(userName)).thenReturn(getRequestError())
        viewModel.searchUserScreenState.observeForever(mockObserver)

        viewModel.searchUser(userName)

        handleLoadingProperly()

        assertEquals("Invalid Status", ScreenStatus.ERROR.status, viewModel.searchUserScreenState.value?.status)
        assertTrue("Should not receive data", viewModel.searchUserScreenState.value?.user == null)
    }

    private fun handleLoadingProperly() {
        viewModel.searchUserScreenState.value?.isLoading()?.let {
            assertTrue("Is loading", it)
        }

        testScheduler.triggerActions()

        viewModel.searchUserScreenState.value?.isLoading()?.let {
            assertFalse("Finished loading", it)
        }
    }

    private fun getRequestError(): Observable<User> {
        return Observable.error(HttpException(
                Response.error<TwitterApiException>(400, ResponseBody.create(MediaType.parse("application/json"), "Bad Request"))))
    }
}
