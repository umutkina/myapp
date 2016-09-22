package com.umutkina.findunfollowersapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.umutkina.findunfollowersapp.modals.Const;
import com.umutkina.findunfollowersapp.modals.ModelUser;
import com.umutkina.findunfollowersapp.modals.UserWrapper;
import com.umutkina.findunfollowersapp.utils.Utils;

import java.util.ArrayList;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by mac on 21/04/15.
 */
public class UnfApplication extends Application {

    private SharedPreferences mSharedPreferences;


  private   ArrayList <Twitter> twitters=new ArrayList<>();
//    ArrayList<Long> followerList;
//    ArrayList<Long> followingList;

    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPreferences = getApplicationContext().getSharedPreferences("myprefs",
                Context.MODE_PRIVATE);



        changeConfig();
//        if (android.os.Build.VERSION.SDK_INT > 8) {
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//                    .permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//        }

    }

    public void changeConfig() {

        twitters.clear();
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(Const.CONSUMER_KEY);
        configurationBuilder.setOAuthConsumerSecret(Const.CONSUMER_SECRET);
        Configuration configuration = configurationBuilder.build();
        UserWrapper userWrapperFromPrefs = Utils.getUserWrapperFromPrefs(this);
        TwitterFactory twitterFactory = new TwitterFactory(configuration);

        if (userWrapperFromPrefs != null) {


            ArrayList<ModelUser> users = userWrapperFromPrefs.getUsers();
            for (ModelUser user1 : users) {
                twitters.add(twitterFactory.getInstance(new AccessToken(user1.getToken(), user1.getSecret())));
            }
        }



    }


    public Twitter getInstance() {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(Const.CONSUMER_KEY);
        configurationBuilder.setOAuthConsumerSecret(Const.CONSUMER_SECRET);
        Configuration configuration = configurationBuilder.build();

        TwitterFactory twitterFactory = new TwitterFactory(configuration);


       Twitter instance = twitterFactory.getInstance();
        return instance;
    }


    public SharedPreferences getmSharedPreferences() {
        return mSharedPreferences;
    }

    public void setmSharedPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public ArrayList<Twitter> getTwitters() {
        return twitters;
    }

    public void setTwitters(ArrayList<Twitter> twitters) {
        this.twitters = twitters;
    }
}
