package com.umutkina.findunfollowersapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.umutkina.findunfollowersapp.modals.Const;
import com.umutkina.findunfollowersapp.modals.ModelUser;
import com.umutkina.findunfollowersapp.modals.UserWrapper;
import com.umutkina.findunfollowersapp.utils.RoundedImageView;
import com.umutkina.findunfollowersapp.utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class VenueDetailForPunchActivity extends BaseActivity {


    ProgressDialog pDialog;


    // Twitter

    RequestToken requestToken;
    @InjectView(R.id.tv_header)
    TextView tvHeader;
    @InjectView(R.id.iv_profile)
    RoundedImageView ivProfile;
    @InjectView(R.id.tv_user_name)
    TextView tvUserName;
    @InjectView(R.id.tv_tweet_list)
    TextView tvTweetList;
    @InjectView(R.id.tv_start_tweet)
    TextView tvStartTweet;
    @InjectView(R.id.tv_hashtag_add)
    TextView tvHashtagAdd;
    @InjectView(R.id.scrll)
    ScrollView scrll;
    @InjectView(R.id.ll)
    LinearLayout ll;
    @InjectView(R.id.tv_start_finding)
    TextView tvStartFinding;
    @InjectView(R.id.adView)
    AdView adView;
    @InjectView(R.id.ll_button)
    LinearLayout llButton;
    ArrayList <Twitter> twitters;

    // Shared Preferences
    private SharedPreferences mSharedPreferences;

    InterstitialAd mInterstitialAd;

    Twitter twitter;
    AdView mAdView;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        ButterKnife.inject(this);
        mSharedPreferences = getApplicationContext().getSharedPreferences("myprefs",
                Context.MODE_PRIVATE);
        boolean instashare = mSharedPreferences.getBoolean("instashare", false);
        if (!instashare) {
//            showInstashareDialog();
        }
        mSharedPreferences.edit().putBoolean("instashare", true).commit();

        AdRequest adRequest = new AdRequest.Builder().build();


        mAdView = (AdView) findViewById(R.id.adView);
        mAdView.setVisibility(View.GONE);
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(adListener2);


        tvStartFinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToTwitter();


            }
        });


    }



    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private void sendScroll() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        scrll.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        if (!isNetworkAvailable()) {
            Utils.showInfoDialog(getContext(), null, getString(R.string.warning), getString(R.string.check_internet_connection));

        } else {

            refreshScreen();
        }


    }

    private void refreshScreen() {
        unfApplication.changeConfig();

        twitters=unfApplication.getTwitters();
        UserWrapper userWrapperFromPrefs = Utils.getUserWrapperFromPrefs(this);

        if (userWrapperFromPrefs == null) {

            tvUserName.setText("Herhangi bir hesap ile login olunmadı");
            return;
        }

        ArrayList<ModelUser> users = userWrapperFromPrefs.getUsers();
        tvUserName.setText("");
        for (ModelUser user : users) {

            String name = user.getName();
            tvUserName.setText(tvUserName.getText() + " " + name);
        }
    }


    public Activity getContext() {
        // TODO Auto-generated method stub
        return this;
    }


//    class GetUser extends AsyncTask<ArrayList<Long>, ResponseList<User>, ResponseList<User>> {
//
//        /**
//         * Before starting background thread Show Progress Dialog
//         */
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//
//        }
//
//        /**
//         * getting Places JSON
//         */
//        protected ResponseList<User> doInBackground(ArrayList<Long>... args) {
//            ResponseList<User> users = null;
//            try {
//
////                followersList = twitter.getFollowersList(args[0], args[1]);
//
////                Long[] ids = new Long[args[0].size()];
////                ids=args[0].toArray(ids);
//                users = twitter.lookupUsers(convertIntegers(args[0]));
//            } catch (TwitterException e1) {
//                e1.printStackTrace();
//            }
//            return users;
//        }
//
//        /**
//         * After completing background task Dismiss the progress dialog and show
//         * the data in UI Always use runOnUiThread(new Runnable()) to update UI
//         * from background thread, otherwise you will get error
//         * *
//         */
//        protected void onPostExecute(ResponseList<User> users) {
//            // dismiss the dialog after getting all products
//            if (users != null) {
//                user = users.get(0);
//
//                Utils.addUserWrapper(getContext(),);
//                String name = user.getName();
//                String biggerProfileImageURL = user.getBiggerProfileImageURL();
//                tvUserName.setText(name + " / @" + user.getScreenName());
////            Picasso.with(VenueDetailForPunchActivity.this).load(biggerProfileImageURL).into(ivProfile);
//                ivProfile.setImageUrlRounded(biggerProfileImageURL);
//
//            }
//
//
//            // updating UI from Background Thread
//        }
//
//    }

    public long[] convertIntegers(List<Long> longs) {
        long[] ret = new long[longs.size()];
        Iterator<Long> iterator = longs.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next().longValue();
        }
        return ret;
    }


    private void loginToTwitter() {
        // Check if already logged in

        LoginTwitterTask task = new LoginTwitterTask();
        task.execute();


    }

    /**
     * Function to update status
     */
    private String askOAuth() {


        try {
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeSessionCookie();
            twitter = unfApplication.getInstance();
            requestToken = twitter
                    .getOAuthRequestToken(Const.CALLBACK_URL_PUNCH );
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        if (requestToken != null) {
            return requestToken.getAuthenticationURL();
        }
        return null;

    }

    class LoginTwitterTask extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(VenueDetailForPunchActivity.this);
            pDialog.setMessage(getString(R.string.loading));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting Places JSON
         */
        protected String doInBackground(String... args) {

            return askOAuth();
        }

        /**
         * After completing background task Dismiss the progress dialog and show
         * the data in UI Always use runOnUiThread(new Runnable()) to update UI
         * from background thread, otherwise you will get error
         * *
         */
        protected void onPostExecute(String authenticationUrl) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread

            if (authenticationUrl != null) {
                Intent intent = new Intent(VenueDetailForPunchActivity.this,
                        WebViewActivity.class);
                intent.putExtra("url", authenticationUrl);
                startActivityForResult(intent, 1);
            } else {
                Utils.showInfoDialog(getContext(), null, getString(R.string.warning), getString(R.string.check_internet_connection));
            }


            // VenueDetailForPunchActivity.this.startActivity(new Intent(
            // Intent.ACTION_VIEW, Uri.parse(authenticationUrl)));
        }

    }

    class TwitterGetAccesTokenTask extends
            AsyncTask<String, String, AccessToken> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(VenueDetailForPunchActivity.this);
            pDialog.setMessage(getString(R.string.loading));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        protected AccessToken doInBackground(String... args) {
            AccessToken accessToken = null;
            Uri uri = Uri.parse(args[0]);
            // oAuth verifier
            String verifier = uri
                    .getQueryParameter(Const.URL_TWITTER_OAUTH_VERIFIER);

            try {
                // Get the access token

                accessToken =twitter.getOAuthAccessToken(requestToken,
                        verifier);

                // Displaying in xml ui

            } catch (Exception e) {
                // Check log for login errors
                Log.e("Twitter Login Error", "> " + e.getMessage());
            }
            // save changes

            return accessToken;
        }


        /**
         * After completing background task Dismiss the progress dialog and show
         * the data in UI Always use runOnUiThread(new Runnable()) to update UI
         * from background thread, otherwise you will get error
         * *
         */
        protected void onPostExecute(AccessToken accessToken) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            // updating UI from Background Thread
            // Shared Preferences
            if (accessToken == null) {
                return;
            }

            ModelUser modelUser = new ModelUser();

            modelUser.setName(accessToken.getScreenName());
            modelUser.setUserId(accessToken.getUserId() + "");
            modelUser.setToken(accessToken.getToken());
            modelUser.setSecret(accessToken.getTokenSecret());

            Utils.addUserWrapper(getContext(), modelUser);

//            unfApplication.changeConfig();

            refreshScreen();
//            getUser(accessToken);


        }

    }

//
//    public String forceLogin() {
//
//        String token = mSharedPreferences.getString(Const.PREF_KEY_OAUTH_TOKEN, null);
//
//        return token == null ? "force_login=true" : "";
//
//    }

//    private void logout() {
//
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.removeSessionCookie();
//
//
//
//        tvUserName.setText("ModelUser Name");
//        ivProfile.setImageResource(R.drawable.profile);
//
//
//
//        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
//        configurationBuilder.setOAuthConsumerKey(Const.CONSUMER_KEY);
//        configurationBuilder.setOAuthConsumerSecret(Const.CONSUMER_SECRET);
//
//        tvStartFinding.setText(getString(R.string.login));
//        tvStartFinding.setBackgroundColor(getResources().getColor(R.color.Blue));
//
//
//    }


//    private boolean isTwitterLoggedInAlready() {
//        // return twitter login status from Shared Preferences
//        return mSharedPreferences.getBoolean(Const.PREF_KEY_TWITTER_LOGIN,
//                false);
//    }


//    private void getUser(AccessToken accessToken) {
//
//
//        ArrayList<Long> longs = new ArrayList<>();
//        longs.add(Long.parseLong(accessToken.getUserId() + ""));
//
//        new GetUser().execute(longs);
//
//
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if (data != null && data.getStringExtra("url") != null) {

                String stringExtra = data.getStringExtra("url");
                Uri uri = Uri.parse(stringExtra);
                if (uri != null
                        && uri.toString().startsWith(
                        Const.CALLBACK_URL_PUNCH)) {
                    TwitterGetAccesTokenTask accesTokenTask = new TwitterGetAccesTokenTask();
                    accesTokenTask.execute(stringExtra);
                }
            }
        }

    }

    AdListener adListener = new AdListener() {
        @Override
        public void onAdClosed() {
            super.onAdClosed();
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            super.onAdFailedToLoad(errorCode);
        }

        @Override
        public void onAdLeftApplication() {
            super.onAdLeftApplication();
        }

        @Override
        public void onAdOpened() {
            super.onAdOpened();
        }

        @Override
        public void onAdLoaded() {
            super.onAdLoaded();

            int i = Utils.randInt(3);
            if (i == 2) {
                mInterstitialAd.show();

            }

        }
    };

    @OnClick(R.id.tv_tweet_list)
    public void tweetList() {
        startActivity(new Intent(getContext(), TweetListActivity.class));
    }

    @OnClick(R.id.tv_hashtag_add)
    public void hashTagAdd() {
        startActivity(new Intent(getContext(), HashTagAddActivity.class));
    }

    @OnClick(R.id.tv_start_tweet)
    public void startTweet() {
        boolean login = isTwitterLoggedInAlready();

        if (login) {

            sendLocation();
            Toast.makeText(VenueDetailForPunchActivity.this, "starting tweet", Toast.LENGTH_SHORT).show();
        } else {
            loginToTwitter();

            Toast.makeText(VenueDetailForPunchActivity.this, getString(R.string.shoult_login), Toast.LENGTH_SHORT).show();

        }

    }

    private boolean isTwitterLoggedInAlready() {

        UserWrapper userWrapperFromPrefs = Utils.getUserWrapperFromPrefs(this);
        return userWrapperFromPrefs!=null;
    }

    AdListener adListener2 = new AdListener() {
        @Override
        public void onAdClosed() {
            super.onAdClosed();
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            super.onAdFailedToLoad(errorCode);
        }

        @Override
        public void onAdLeftApplication() {
            super.onAdLeftApplication();
        }

        @Override
        public void onAdOpened() {
            super.onAdOpened();
        }

        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    mAdView.setVisibility(View.VISIBLE);
                    ;
                }
            }, 2000);

        }
    };


}
