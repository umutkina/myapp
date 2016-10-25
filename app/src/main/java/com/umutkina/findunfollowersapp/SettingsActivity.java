package com.umutkina.findunfollowersapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.umutkina.findunfollowersapp.modals.Const;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SettingsActivity extends BaseActivity {
    boolean accountFollowStatus;
    boolean hashtagFollowStatus;
    boolean hashtagFavStatus;
    boolean autoTweetStatus;
    @InjectView(R.id.tv_fav_opened)
    TextView tvFavOpened;
    @InjectView(R.id.tv_tweet_opened)
    TextView tvTweetOpened;
    @InjectView(R.id.tv_hashtag_follow_opened)
    TextView tvHashtagFollowOpened;
    @InjectView(R.id.tv_account_follow_opened)
    TextView tvAccountFollowOpened;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.inject(this);

        refresStatuses();

        tvTweetOpened.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoTweetStatus=!autoTweetStatus;
                mSharedPreferences.edit().putBoolean(Const.AUTO_TWEEET_STATUS,autoTweetStatus).commit();
                refresStatuses();
            }
        });

        tvFavOpened.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hashtagFavStatus=!hashtagFavStatus;
                mSharedPreferences.edit().putBoolean(Const.HASHTAG_FAV_STATUS,hashtagFavStatus).commit();
                refresStatuses();
            }
        });

        tvAccountFollowOpened.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountFollowStatus=!accountFollowStatus;
                mSharedPreferences.edit().putBoolean(Const.ACCOUNT_FOLLOW_STATUS,accountFollowStatus).commit();
                refresStatuses();
            }
        });

        tvHashtagFollowOpened.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hashtagFollowStatus=!hashtagFollowStatus;
                mSharedPreferences.edit().putBoolean(Const.HASH_TAG_FOLLOW_STATUS,hashtagFollowStatus).commit();
                refresStatuses();
            }
        });






    }

    public void refresStatuses() {
        accountFollowStatus = mSharedPreferences.getBoolean(Const.ACCOUNT_FOLLOW_STATUS, true);
        hashtagFavStatus = mSharedPreferences.getBoolean(Const.HASHTAG_FAV_STATUS, true);
        hashtagFollowStatus = mSharedPreferences.getBoolean(Const.HASH_TAG_FOLLOW_STATUS, true);
        autoTweetStatus = mSharedPreferences.getBoolean(Const.AUTO_TWEEET_STATUS, true);

        tvAccountFollowOpened.setText(accountFollowStatus?"Account Followed enabled":"Account Followed Disabled");
        tvFavOpened.setText(hashtagFavStatus?"hastag fav enabled":"hastag fav disabled");
        tvHashtagFollowOpened.setText(hashtagFollowStatus?"hastag follow enabled":"hastag follow disabled");
        tvTweetOpened.setText(autoTweetStatus?"auto tweet enabled":"auto tweet disabled");

    }
}
