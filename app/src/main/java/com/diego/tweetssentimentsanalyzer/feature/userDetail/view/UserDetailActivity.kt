package com.diego.tweetssentimentsanalyzer.feature.userDetail.view

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.diego.tweetssentimentsanalyzer.R
import com.google.gson.Gson
import com.twitter.sdk.android.core.models.User

class UserDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        intent.extras.getString(userExtra).let {
            val user = Gson().fromJson(it, User::class.java)
            val t = ""
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
}
