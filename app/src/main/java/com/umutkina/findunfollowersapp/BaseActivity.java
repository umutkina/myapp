package com.umutkina.findunfollowersapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.umutkina.findunfollowersapp.modals.Const;
import com.umutkina.findunfollowersapp.modals.TweetList;
import com.umutkina.findunfollowersapp.modals.TweetListWrapper;
import com.umutkina.findunfollowersapp.services.TweetServiceReceiver;
import com.umutkina.findunfollowersapp.utils.Utils;

import java.util.ArrayList;

/**
 * Created by mac on 01/05/15.
 */
public class BaseActivity extends Activity {
    UnfApplication unfApplication;
  public   SharedPreferences mSharedPreferences;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        long l = System.currentTimeMillis();



        unfApplication = (UnfApplication) getApplication();


        mSharedPreferences = unfApplication.getmSharedPreferences();


    }

   public ArrayList<TweetList> getTweetLists() {
        String string = mSharedPreferences.getString(Const.TWEET_LIST, null);
        TweetListWrapper tweetListWrapper = (TweetListWrapper) Utils.getObject(string, TweetListWrapper.class);
        if (tweetListWrapper != null) {
            return tweetListWrapper.getTweetLists();
        }

        return null;

    }

    public void sendLocation() {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), TweetServiceReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, TweetServiceReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                3600000, pIntent);
//        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
//                60000, pIntent);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}
