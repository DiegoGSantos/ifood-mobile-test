package com.diego.tweetssentimentsanalyzer.feature.userDetail.view.adapter

import android.content.Context
import android.support.text.emoji.EmojiCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diego.tweetssentimentsanalyzer.R
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.android.synthetic.main.view_tweet.view.*
import java.util.*


/**
 * Created by Rafaela Araujo
 * on 08/05/2018.
 */
class TweetsAdapter(private val listener: (Int, Tweet) -> Unit) : RecyclerView.Adapter<TweetsAdapter.ViewHolder>() {

    private var tweetsList: List<Tweet> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.view_tweet, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(tweetsList[position], listener)

    override fun getItemCount(): Int = tweetsList.size

    fun setList(list: List<Tweet>) {
        tweetsList = list
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var context: Context = itemView.context

        internal fun bind(item: Tweet, listener: (Int, Tweet) -> Unit) = with(itemView) {

            tweetText.text = item.text
//            retweeters.text = item.retweet_count.toNumberFormat()
//            favorites.text = item.favorite_count.toNumberFormat()
            tweetDate.text = item.createdAt

//            loadTweetEmotion(item.sentiment, verify_emotion)
            analyzeSentiment.setOnClickListener {
//                if (item.sentiment == 0) {
//                    verify_emotion.startAnimation()
                    listener(adapterPosition, item)
//                }
            }
        }
    }

//        private fun loadTweetEmotion(sentiment: Int, verify_emotion: CircularProgressButton) {
//            when (sentiment) {
//                SentimentsEnum.NEUTRAL.code -> setTweetEmotion(verify_emotion, R.string.neutral_emoji,R.drawable.neutral_sentiment_bg)
//                SentimentsEnum.SAD.code -> setTweetEmotion(verify_emotion, R.string.sad_emoji,R.drawable.sad_sentiment_bg)
//                SentimentsEnum.HAPPY.code -> setTweetEmotion(verify_emotion, R.string.happy_emoji,R.drawable.happy_sentiment_bg)
//                else -> verify_emotion.text = context.getString(R.string.verify_tweet_text)
//            }
//        }

//        private fun setTweetEmotion(verify_emotion: CircularProgressButton, idText:Int, idresource:Int){
//            verify_emotion.text = EmojiCompat.get().process(context.getString(idText))
//            verify_emotion.setBackgroundDrawable(ContextCompat.getDrawable(context,idresource))
//        }

}