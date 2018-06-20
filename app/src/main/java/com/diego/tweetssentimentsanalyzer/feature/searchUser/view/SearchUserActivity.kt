package com.diego.tweetssentimentsanalyzer.feature.searchUser.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import com.diego.tweetssentimentsanalyzer.R
import com.diego.tweetssentimentsanalyzer.feature.searchUser.viewModel.SearchUserViewModel
import com.diego.tweetssentimentsanalyzer.feature.searchUser.viewModel.SearchUserViewModelFactory
import kotlinx.android.synthetic.main.activity_search_user.*
import org.koin.android.ext.android.inject
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.diego.tweetssentimentsanalyzer.manager.NetManager
import android.text.Editable
import com.diego.tweetssentimentsanalyzer.feature.userDetail.view.UserDetailActivity
import com.twitter.sdk.android.core.models.User

class SearchUserActivity : AppCompatActivity() {
    private var startingActicity = true
    private val viewModelFactory: SearchUserViewModelFactory by inject()
    private val netManager: NetManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_user)
        setListeners()
    }

    private fun setListeners() {
        viewModel()?.searchUserScreenState?.observe(this, Observer<SearchUserScreenState> { movieDetail ->
            movieDetail?.let {
                when {
                    it.isInitialState() -> {
                        resetScreen()
                        clearErrors()
                    }
                    it.isStatusOk() -> {
                        openUserDetail(it.user)
                    }
                    it.isLoading() -> {
                        showLoading()
                    }
                    it.isThereError() -> {
                        showRequestError(getString(R.string.api_error))
                    }
                    it.isUserInvalid() -> {
                        showFormValidationError(getString(R.string.user_invalid))
                    }
                    it.isUserNotProvided() -> {
                        showFormValidationError(getString(R.string.user_not_provided))
                    }
                    it.isUserNotFound() -> {
                        showFormValidationError(getString(R.string.user_not_found))
                    }
                }
            }
        })

        findUser.setOnClickListener({
            searchUser()
        })

        userName.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchUser()
                return@OnKeyListener true
            }
            false
        })

        userName.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (!startingActicity) {
                    viewModel()?.resetState()
                }
                startingActicity = false
            }
        })
    }

    private fun openUserDetail(user: User?) {
        viewModel()?.resetState()
        user.let {
            UserDetailActivity.starter(this, user)
        }
    }

    private fun clearErrors() {
        userName.error = null
    }

    private fun showLoading() {
        findUser.visibility = INVISIBLE
        progress.visibility = VISIBLE
    }

    private fun resetScreen() {
        findUser.visibility = VISIBLE
        progress.visibility = INVISIBLE
    }

    private fun searchUser() {
        if (netManager.isConnectedToInternet) {
            viewModel()?.searchUser(userName.text.toString())
        } else {
            viewModel()?.resetState()
            showRequestError(getString(R.string.no_connection_error))
        }
    }

    private fun showFormValidationError(message: String) {
        closeKeyboard()
        userName.error = message
    }

    private fun showRequestError(message: String) {
        clearErrors()
        Snackbar.make(mainLayout, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
        }
    }

    private fun viewModel(): SearchUserViewModel? {
        return ViewModelProviders.of(this, viewModelFactory)
                .get(SearchUserViewModel::class.java)
    }
}
