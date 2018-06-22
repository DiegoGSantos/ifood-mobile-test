package com.diego.tweetssentimentsanalyzer.twitterRestClient

import com.diego.tweetssentimentsanalyzer.util.IS_UNDER_TEST
import okhttp3.*
import java.io.IOException
import com.diego.tweetssentimentsanalyzer.util.TWITTER_TWEETS_URL
import com.diego.tweetssentimentsanalyzer.util.TWITTER_USER_URL

internal class MockInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!IS_UNDER_TEST) {
            return chain.proceed(chain.request())
        }
        val request = chain.request()
        val path = getPath(request)

        var body = ""

        if (path == TWITTER_USER_URL) {
            body = getUserJson()
        } else if (path == TWITTER_TWEETS_URL) {
            body = getTweetsJson(8)
        }

        val mimeType = "application/json"

        return Response.Builder()
                .addHeader("content-type", mimeType)
                .body(ResponseBody.create(MediaType.parse(mimeType), body))
                .code(200)
                .message("Mock response from res/raw/")
                .protocol(Protocol.HTTP_1_0)
                .request(chain.request())
                .build()

//        return response
    }

    private fun getTweetsJson(numberOfItems: Int): String {
        var tweets = "["

        for (i in 1..numberOfItems) {
            tweets += getTweet()
            tweets += if (i < numberOfItems) "," else ""
        }

        tweets += "]"

        return tweets
    }

    private fun getTweet(): String {
        return "{\n" +
                "  \"created_at\": \"Wed Jun 20 16:02:13 +0000 2018\",\n" +
                "  \"id\": 1.0094664578922e+18,\n" +
                "  \"id_str\": \"1009466457892245512\",\n" +
                "  \"text\": \"One mother fled El Salvador with her 2 young children after their father, a gang member, violently threatened to ta\\u2026 https:\\/\\/t.co\\/yXRf8T0Ars\",\n" +
                "  \"truncated\": true,\n" +
                "  \"entities\": {\n" +
                "    \"hashtags\": [\n" +
                "      \n" +
                "    ],\n" +
                "    \"symbols\": [\n" +
                "      \n" +
                "    ],\n" +
                "    \"user_mentions\": [\n" +
                "      \n" +
                "    ],\n" +
                "    \"urls\": [\n" +
                "      {\n" +
                "        \"url\": \"https:\\/\\/t.co\\/yXRf8T0Ars\",\n" +
                "        \"expanded_url\": \"https:\\/\\/twitter.com\\/i\\/web\\/status\\/1009466457892245512\",\n" +
                "        \"display_url\": \"twitter.com\\/i\\/web\\/status\\/1\\u2026\",\n" +
                "        \"indices\": [\n" +
                "          117,\n" +
                "          140\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"source\": \"<a href=\\\"http:\\/\\/www.socialflow.com\\\" rel=\\\"nofollow\\\">SocialFlow<\\/a>\",\n" +
                "  \"in_reply_to_status_id\": null,\n" +
                "  \"in_reply_to_status_id_str\": null,\n" +
                "  \"in_reply_to_user_id\": null,\n" +
                "  \"in_reply_to_user_id_str\": null,\n" +
                "  \"in_reply_to_screen_name\": null,\n" +
                "  \"user\": {\n" +
                "    \"id\": 807095,\n" +
                "    \"id_str\": \"807095\",\n" +
                "    \"name\": \"The New York Times\",\n" +
                "    \"screen_name\": \"nytimes\",\n" +
                "    \"location\": \"New York City\",\n" +
                "    \"description\": \"Where the conversation begins. Follow for breaking news, special reports, RTs of our journalists and more. Visit https:\\/\\/t.co\\/ghL9OoYKMM to share news tips.\",\n" +
                "    \"url\": \"http:\\/\\/t.co\\/ahvuWqicF9\",\n" +
                "    \"entities\": {\n" +
                "      \"url\": {\n" +
                "        \"urls\": [\n" +
                "          {\n" +
                "            \"url\": \"http:\\/\\/t.co\\/ahvuWqicF9\",\n" +
                "            \"expanded_url\": \"http:\\/\\/www.nytimes.com\\/\",\n" +
                "            \"display_url\": \"nytimes.com\",\n" +
                "            \"indices\": [\n" +
                "              0,\n" +
                "              22\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      \"description\": {\n" +
                "        \"urls\": [\n" +
                "          {\n" +
                "            \"url\": \"https:\\/\\/t.co\\/ghL9OoYKMM\",\n" +
                "            \"expanded_url\": \"http:\\/\\/nyti.ms\\/2FVHq9v\",\n" +
                "            \"display_url\": \"nyti.ms\\/2FVHq9v\",\n" +
                "            \"indices\": [\n" +
                "              113,\n" +
                "              136\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    },\n" +
                "    \"protected\": false,\n" +
                "    \"followers_count\": 42230256,\n" +
                "    \"friends_count\": 886,\n" +
                "    \"listed_count\": 195502,\n" +
                "    \"created_at\": \"Fri Mar 02 20:41:42 +0000 2007\",\n" +
                "    \"favourites_count\": 17086,\n" +
                "    \"utc_offset\": null,\n" +
                "    \"time_zone\": null,\n" +
                "    \"geo_enabled\": true,\n" +
                "    \"verified\": true,\n" +
                "    \"statuses_count\": 323921,\n" +
                "    \"lang\": \"en\",\n" +
                "    \"contributors_enabled\": false,\n" +
                "    \"is_translator\": false,\n" +
                "    \"is_translation_enabled\": true,\n" +
                "    \"profile_background_color\": \"131516\",\n" +
                "    \"profile_background_image_url\": \"http:\\/\\/abs.twimg.com\\/images\\/themes\\/theme14\\/bg.gif\",\n" +
                "    \"profile_background_image_url_https\": \"https:\\/\\/abs.twimg.com\\/images\\/themes\\/theme14\\/bg.gif\",\n" +
                "    \"profile_background_tile\": true,\n" +
                "    \"profile_image_url\": \"http:\\/\\/pbs.twimg.com\\/profile_images\\/942784892882112513\\/qV4xB0I3_normal.jpg\",\n" +
                "    \"profile_image_url_https\": \"https:\\/\\/pbs.twimg.com\\/profile_images\\/942784892882112513\\/qV4xB0I3_normal.jpg\",\n" +
                "    \"profile_banner_url\": \"https:\\/\\/pbs.twimg.com\\/profile_banners\\/807095\\/1522172276\",\n" +
                "    \"profile_link_color\": \"607696\",\n" +
                "    \"profile_sidebar_border_color\": \"FFFFFF\",\n" +
                "    \"profile_sidebar_fill_color\": \"EFEFEF\",\n" +
                "    \"profile_text_color\": \"333333\",\n" +
                "    \"profile_use_background_image\": true,\n" +
                "    \"has_extended_profile\": false,\n" +
                "    \"default_profile\": false,\n" +
                "    \"default_profile_image\": false,\n" +
                "    \"following\": null,\n" +
                "    \"follow_request_sent\": null,\n" +
                "    \"notifications\": null,\n" +
                "    \"translator_type\": \"none\"\n" +
                "  },\n" +
                "  \"geo\": null,\n" +
                "  \"coordinates\": null,\n" +
                "  \"place\": null,\n" +
                "  \"contributors\": null,\n" +
                "  \"is_quote_status\": false,\n" +
                "  \"retweet_count\": 23,\n" +
                "  \"favorite_count\": 34,\n" +
                "  \"favorited\": false,\n" +
                "  \"retweeted\": false,\n" +
                "  \"possibly_sensitive\": false,\n" +
                "  \"lang\": \"en\"\n" +
                "}"
    }

    private fun getPath(request: Request): String {
        var path = request.url().encodedPath()
        if ("/" == path) {
            path = request.url().toString()
        }
        return path
    }

    private fun getUserJson(): String {
        return "{  \n" +
                "   \"contributors_enabled\":false,\n" +
                "   \"created_at\":\"Fri Mar 02 20:41:42 +0000 2007\",\n" +
                "   \"default_profile\":false,\n" +
                "   \"default_profile_image\":false,\n" +
                "   \"description\":\"Where the conversation begins. Follow for breaking news, special reports, RTs of our journalists and more. Visit https://t.co/ghL9OoYKMM to share news tips.\",\n" +
                "   \"entities\":{  \n" +
                "      \"description\":{  \n" +
                "         \"urls\":[  \n" +
                "            {  \n" +
                "               \"display_url\":\"nyti.ms/2FVHq9v\",\n" +
                "               \"expanded_url\":\"http://nyti.ms/2FVHq9v\",\n" +
                "               \"url\":\"https://t.co/ghL9OoYKMM\",\n" +
                "               \"indices\":[  \n" +
                "                  113,\n" +
                "                  136\n" +
                "               ]\n" +
                "            }\n" +
                "         ]\n" +
                "      },\n" +
                "      \"url\":{  \n" +
                "         \"urls\":[  \n" +
                "            {  \n" +
                "               \"display_url\":\"nytimes.com\",\n" +
                "               \"expanded_url\":\"http://www.nytimes.com/\",\n" +
                "               \"url\":\"http://t.co/ahvuWqicF9\",\n" +
                "               \"indices\":[  \n" +
                "                  0,\n" +
                "                  22\n" +
                "               ]\n" +
                "            }\n" +
                "         ]\n" +
                "      }\n" +
                "   },\n" +
                "   \"favourites_count\":17088,\n" +
                "   \"follow_request_sent\":false,\n" +
                "   \"followers_count\":42233380,\n" +
                "   \"friends_count\":886,\n" +
                "   \"geo_enabled\":true,\n" +
                "   \"id\":807095,\n" +
                "   \"id_str\":\"807095\",\n" +
                "   \"is_translator\":false,\n" +
                "   \"lang\":\"en\",\n" +
                "   \"listed_count\":195302,\n" +
                "   \"location\":\"New York City\",\n" +
                "   \"name\":\"The New York Times\",\n" +
                "   \"profile_background_color\":\"131516\",\n" +
                "   \"profile_background_image_url\":\"http://abs.twimg.com/images/themes/theme14/bg.gif\",\n" +
                "   \"profile_background_image_url_https\":\"https://abs.twimg.com/images/themes/theme14/bg.gif\",\n" +
                "   \"profile_background_tile\":true,\n" +
                "   \"profile_banner_url\":\"https://pbs.twimg.com/profile_banners/807095/1522172276\",\n" +
                "   \"profile_image_url\":\"http://pbs.twimg.com/profile_images/942784892882112513/qV4xB0I3_normal.jpg\",\n" +
                "   \"profile_image_url_https\":\"https://pbs.twimg.com/profile_images/942784892882112513/qV4xB0I3_normal.jpg\",\n" +
                "   \"profile_link_color\":\"607696\",\n" +
                "   \"profile_sidebar_border_color\":\"FFFFFF\",\n" +
                "   \"profile_sidebar_fill_color\":\"EFEFEF\",\n" +
                "   \"profile_text_color\":\"333333\",\n" +
                "   \"profile_use_background_image\":true,\n" +
                "   \"protected\":false,\n" +
                "   \"screen_name\":\"nytimes\",\n" +
                "   \"show_all_inline_media\":false,\n" +
                "   \"status\":{  \n" +
                "      \"created_at\":\"Thu Jun 21 02:17:02 +0000 2018\",\n" +
                "      \"display_text_range\":[  \n" +
                "\n" +
                "      ],\n" +
                "      \"entities\":{  \n" +
                "         \"hashtags\":[  \n" +
                "\n" +
                "         ],\n" +
                "         \"media\":[  \n" +
                "\n" +
                "         ],\n" +
                "         \"symbols\":[  \n" +
                "\n" +
                "         ],\n" +
                "         \"urls\":[  \n" +
                "\n" +
                "         ],\n" +
                "         \"user_mentions\":[  \n" +
                "            {  \n" +
                "               \"id\":1767741,\n" +
                "               \"id_str\":\"1767741\",\n" +
                "               \"name\":\"NYT National News\",\n" +
                "               \"screen_name\":\"NYTNational\",\n" +
                "               \"indices\":[  \n" +
                "                  3,\n" +
                "                  15\n" +
                "               ]\n" +
                "            }\n" +
                "         ]\n" +
                "      },\n" +
                "      \"extended_entities\":{  \n" +
                "         \"hashtags\":[  \n" +
                "\n" +
                "         ],\n" +
                "         \"media\":[  \n" +
                "\n" +
                "         ],\n" +
                "         \"symbols\":[  \n" +
                "\n" +
                "         ],\n" +
                "         \"urls\":[  \n" +
                "\n" +
                "         ],\n" +
                "         \"user_mentions\":[  \n" +
                "\n" +
                "         ]\n" +
                "      },\n" +
                "      \"favorite_count\":0,\n" +
                "      \"favorited\":false,\n" +
                "      \"id\":1009621184802148352,\n" +
                "      \"id_str\":\"1009621184802148352\",\n" +
                "      \"in_reply_to_status_id\":0,\n" +
                "      \"in_reply_to_user_id\":0,\n" +
                "      \"lang\":\"en\",\n" +
                "      \"possibly_sensitive\":false,\n" +
                "      \"quoted_status_id\":0,\n" +
                "      \"quoted_status_id_str\":\"0\",\n" +
                "      \"retweet_count\":4,\n" +
                "      \"retweeted\":false,\n" +
                "      \"retweeted_status\":{  \n" +
                "         \"created_at\":\"Thu Jun 21 00:40:09 +0000 2018\",\n" +
                "         \"display_text_range\":[  \n" +
                "\n" +
                "         ],\n" +
                "         \"entities\":{  \n" +
                "            \"hashtags\":[  \n" +
                "\n" +
                "            ],\n" +
                "            \"media\":[  \n" +
                "\n" +
                "            ],\n" +
                "            \"symbols\":[  \n" +
                "\n" +
                "            ],\n" +
                "            \"urls\":[  \n" +
                "               {  \n" +
                "                  \"display_url\":\"twitter.com/i/web/status/1…\",\n" +
                "                  \"expanded_url\":\"https://twitter.com/i/web/status/1009596799961763843\",\n" +
                "                  \"url\":\"https://t.co/OnbsBwBxKp\",\n" +
                "                  \"indices\":[  \n" +
                "                     117,\n" +
                "                     140\n" +
                "                  ]\n" +
                "               }\n" +
                "            ],\n" +
                "            \"user_mentions\":[  \n" +
                "\n" +
                "            ]\n" +
                "         },\n" +
                "         \"extended_entities\":{  \n" +
                "            \"hashtags\":[  \n" +
                "\n" +
                "            ],\n" +
                "            \"media\":[  \n" +
                "\n" +
                "            ],\n" +
                "            \"symbols\":[  \n" +
                "\n" +
                "            ],\n" +
                "            \"urls\":[  \n" +
                "\n" +
                "            ],\n" +
                "            \"user_mentions\":[  \n" +
                "\n" +
                "            ]\n" +
                "         },\n" +
                "         \"favorite_count\":4,\n" +
                "         \"favorited\":false,\n" +
                "         \"id\":1009596799961763843,\n" +
                "         \"id_str\":\"1009596799961763843\",\n" +
                "         \"in_reply_to_status_id\":0,\n" +
                "         \"in_reply_to_user_id\":0,\n" +
                "         \"lang\":\"en\",\n" +
                "         \"possibly_sensitive\":false,\n" +
                "         \"quoted_status_id\":0,\n" +
                "         \"quoted_status_id_str\":\"0\",\n" +
                "         \"retweet_count\":4,\n" +
                "         \"retweeted\":false,\n" +
                "         \"source\":\"\\u003ca href\\u003d\\\"http://www.socialflow.com\\\" rel\\u003d\\\"nofollow\\\"\\u003eSocialFlow\\u003c/a\\u003e\",\n" +
                "         \"text\":\"The Secret Service is “aware” of a tweet attacking President Trump’s adolescent son that was posted by the actor Pe… https://t.co/OnbsBwBxKp\",\n" +
                "         \"truncated\":true,\n" +
                "         \"withheld_copyright\":false,\n" +
                "         \"withheld_in_countries\":[  \n" +
                "\n" +
                "         ]\n" +
                "      },\n" +
                "      \"source\":\"\\u003ca href\\u003d\\\"http://www.socialflow.com\\\" rel\\u003d\\\"nofollow\\\"\\u003eSocialFlow\\u003c/a\\u003e\",\n" +
                "      \"text\":\"RT @NYTNational: The Secret Service is “aware” of a tweet attacking President Trump’s adolescent son that was posted by the actor Peter Fon…\",\n" +
                "      \"truncated\":false,\n" +
                "      \"withheld_copyright\":false,\n" +
                "      \"withheld_in_countries\":[  \n" +
                "\n" +
                "      ]\n" +
                "   },\n" +
                "   \"statuses_count\":323966,\n" +
                "   \"url\":\"http://t.co/ahvuWqicF9\",\n" +
                "   \"utc_offset\":0,\n" +
                "   \"verified\":true\n" +
                "}"
    }
}