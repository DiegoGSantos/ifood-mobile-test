package com.diego.tweetssentimentsanalyzer.feature.userDetail.view

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.diego.tweetssentimentsanalyzer.R
import com.diego.tweetssentimentsanalyzer.customViews.RoundedCornersTransformation
import com.google.gson.Gson
import com.twitter.sdk.android.core.models.User
import kotlinx.android.synthetic.main.activity_user_detail.*

class UserDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        intent.extras.getString(userExtra).let {
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

    private fun setToolbar(user: User?) {
        collapsingToolbar.title = user?.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        collapsingToolbar.setExpandedTitleColor(resources.getColor(android.R.color.transparent))
    }
}
