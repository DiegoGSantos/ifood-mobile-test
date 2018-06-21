package com.diego.tweetssentimentsanalyzer.feature.userDetail.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.View.*
import com.bumptech.glide.Glide
import com.diego.tweetssentimentsanalyzer.R
import com.diego.tweetssentimentsanalyzer.customViews.RoundedCornersTransformation
import com.diego.tweetssentimentsanalyzer.feature.userDetail.data.Tweet
import com.diego.tweetssentimentsanalyzer.feature.userDetail.view.adapter.TweetsAdapter
import com.diego.tweetssentimentsanalyzer.feature.userDetail.viewModel.UserDetailViewModel
import com.diego.tweetssentimentsanalyzer.feature.userDetail.viewModel.UserDetailViewModelFactory
import com.google.gson.Gson
import com.twitter.sdk.android.core.models.User
import kotlinx.android.synthetic.main.activity_user_detail.*
import org.koin.android.ext.android.inject

class UserDetailActivity : AppCompatActivity() {
    private val viewModelFactory: UserDetailViewModelFactory by inject()
    lateinit var tweetsAdapter: TweetsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        setListeners()
        setRecyclerView()

        intent.extras.getString(userExtra)?.let {
            val user = Gson().fromJson(it, User::class.java)
            Glide.with(this).load(user.profileBannerUrl).into(userBanner)
            Glide.with(this)
                .load(user.profileImageUrlHttps.replace("_normal", "_bigger"))
                .bitmapTransform(RoundedCornersTransformation(this,35, 2))
                .into(userPhoto)
            name.text = user.name
            userName.text = "@" + user.screenName
            setSupportActionBar(toolbar)
            setToolbar(user)

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)

            toolbar.setNavigationOnClickListener { onBackPressed() }

            viewModel()?.getUserTweets(user.screenName)
        }
    }

    companion object {
        const val userExtra = "USER_EXTRA"

        fun starter(context: Context, user: User?) {
            user.let {
                val userJson = Gson().toJson(user)
                val intent = Intent(context, UserDetailActivity::class.java)
                intent.putExtra(userExtra, userJson)
                context.startActivity(intent)
            }
        }
    }

    private fun setListeners() {
        viewModel()?.userDetailScreenState?.observe(this, Observer<UserDetailScreenState> {  userDetailScreenState ->
            userDetailScreenState?.let {
                when {
                    it.isInitialState() -> {

                    }
                    it.isStatusOk() -> {
                        showTweets(it.tweets)
                    }
                    it.isLoading() -> {
                        showLoading()
                    }
                    it.isThereError() -> {
                        showRequestError(getString(R.string.api_error))
                    }
                }
            }
        })
    }

    private fun showTweets(tweetsList: List<Tweet>?) {
        progress.visibility = INVISIBLE
        tweets.visibility = VISIBLE

        tweetsList?.let { tweetsAdapter.setList(it) }
    }

    private fun setRecyclerView() {
        tweets.apply {
            setHasFixedSize(true)

            tweetsAdapter = TweetsAdapter { position: Int, tweet: Tweet -> viewModel()?.analyzeTweetSentiment(position,tweet.text)}
            adapter = tweetsAdapter

            val linearLayoutManager = LinearLayoutManager(this.context)
            layoutManager = linearLayoutManager
        }
    }

    private fun showLoading() {
        progress.visibility = VISIBLE
        tweets.visibility = INVISIBLE
    }

    private fun showRequestError(errorMessage: String?) {
        error.text = errorMessage
        error.visibility = VISIBLE
        progress.visibility = GONE
    }

    private fun setToolbar(user: User?) {
        collapsingToolbar.title = user?.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        collapsingToolbar.setExpandedTitleColor(resources.getColor(android.R.color.transparent))
    }

    private fun viewModel(): UserDetailViewModel? {
        return ViewModelProviders.of(this, viewModelFactory)
                .get(UserDetailViewModel::class.java)
    }
}
