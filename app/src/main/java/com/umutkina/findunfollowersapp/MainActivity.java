package com.umutkina.findunfollowersapp;

import android.content.Intent;
import android.os.Bundle;


public class MainActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (isTwitterLoggedInAlready()) {
            startActivity(new Intent(MainActivity.this, VenueDetailForPunchActivity.class));
            finish();
//        }
//        else{
//            loginToTwitter();
//        }

    }


}
