package com.umutkina.findunfollowersapp.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;

import com.umutkina.findunfollowersapp.UnfApplication;
import com.umutkina.findunfollowersapp.modals.Const;
import com.umutkina.findunfollowersapp.modals.FenomenAccount;
import com.umutkina.findunfollowersapp.modals.FenomenAccountsWrapper;
import com.umutkina.findunfollowersapp.modals.HashTag;
import com.umutkina.findunfollowersapp.modals.HashTagListWraper;
import com.umutkina.findunfollowersapp.modals.TweetItem;
import com.umutkina.findunfollowersapp.modals.TweetList;
import com.umutkina.findunfollowersapp.modals.TweetListWrapper;
import com.umutkina.findunfollowersapp.modals.YdsWord;
import com.umutkina.findunfollowersapp.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import twitter4j.IDs;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * Created by mac on 03/04/16.
 */
public class TweetServiceReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    public static final String ACTION = "com.codepath.example.servicesdemo.alarm";

    // Triggered by the Alarm periodically (starts the service to run task)
    ArrayList<Twitter> twitters;
    int woeId = 23424969;
    Context context;
    ArrayList<YdsWord> ydsWords;


    int currentTweetPosition;
    int currentHashTagPosition;
    int currentAccountPosition;

//    HashTagListWraper hashTagListWraper;
    //    int sabahattinaliId=752817655;
//    long sabahattinaliId = 2227028862L;

    //    long getSabahattinaliId=2190585744L;
//    long oguzAtayId = 549338736;
//    long nazimHikmetId = 1345040852;
//    long kitaplardan = 2195318344L;

//    long sarki=2195318344L;
//    long sabahattinaliId = 2226509852<;

    //kelime deryası,gafebesi,gogeBAKALIM ,SiirSokaktaaa,Sabahattin_Ali_,siirsokakta,AtayOguz_,N_Hikmet_Ran

    //    long accounts[] = {3121168263L,130494271L,2190585744L,2227028862L,752817655,1728743684,549338736,1345040852};
    SharedPreferences sharedPreferences;

    @Override
    public void onReceive(Context context, Intent intent) {


        Intent intentNew = new Intent(context, TweetService.class);
        intentNew.putExtra("foo", "bar");
        context.startService(intentNew);
        this.context = context;

        final UnfApplication application = (UnfApplication) context.getApplicationContext();
        twitters = application.getTwitters();
        sharedPreferences = application.getmSharedPreferences();

        currentTweetPosition = sharedPreferences.getInt(Const.TwEET_POSITION, 0);
        currentAccountPosition = sharedPreferences.getInt(Const.ACCOUNT_POSITION, 0);
        currentHashTagPosition = sharedPreferences.getInt(Const.HASHTAG_POSITION, 0);


//        if (hashTagListWraper != null) {
//            Resources res = context.getResources();
////
//////
//            String[] planets = res.getStringArray(R.array.hashtag_list_T_uyar);
//            Random random = new Random();
////
//            ArrayList<HashTag> hashTagArrayList = hashTagListWraper.getHashTagArrayList();
//            int k = random.nextInt(hashTagArrayList.size());
//            Query currentQuery = new Query(hashTagArrayList.get(k).getHashTag());
//            search(currentQuery);
//        }


//////


////
        // turguy uyar account

////
//


        search();


        Calendar cal = Calendar.getInstance();
        int hourofday = cal.get(Calendar.HOUR_OF_DAY);

// burak burada saat kontrolü var 1 ile 9 arasında atmaması için retun diyorum
        if (hourofday < 8 && hourofday > 2) {
            return;
        }

//            Random random = new Random();

        boolean accountFollowStatus = sharedPreferences.getBoolean(Const.ACCOUNT_FOLLOW_STATUS, true);
        boolean autoTweetStatus = sharedPreferences.getBoolean(Const.AUTO_TWEEET_STATUS, true);


        if (autoTweetStatus) {
            new MentionReq().execute();
        }


        if (accountFollowStatus) {
            new GetFollowersTask().execute();
        }


        //burak bu kısım string.xml den çekmek için

//        Calendar cal = Calendar.getInstance();
//        int hourofday = cal.get(Calendar.HOUR_OF_DAY);
//
//// burak burada saat kontrolü var 1 ile 9 arasında atmaması için retun diyorum
//        if (hourofday < 9 && hourofday > 1) {
//            return;
//        }
//
//
//        new MentionReqForRandomTweet().execute();

//

//        new GetLocaion().execute();


    }

    class MentionReq extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * getting Places JSON
         */
        protected String doInBackground(String... args) {

            String user = "";

            int i = 0;


            Iterator<Twitter> iterator = twitters.iterator();
            while (iterator.hasNext()) {

                Twitter twitter = iterator.next();

                String string = sharedPreferences.getString(Const.TWEET_LIST, null);

                TweetListWrapper tweetListWrapper = (TweetListWrapper) Utils.getObject(string, TweetListWrapper.class);
                if (tweetListWrapper == null) {
                    return null;
                }
                String name = sharedPreferences.getString(Const.SELECTED_TWEET_NAME, null);
                ArrayList<TweetList> tweetLists = tweetListWrapper.getTweetLists();
                TweetList currentTwetList = null;
                for (TweetList tweetList : tweetLists) {
                    if (tweetList.getName().equalsIgnoreCase(name)) {
                        currentTwetList = tweetList;
                        break;
                    }
                }


                ArrayList<TweetItem> tweetItems = currentTwetList.getTweetItems();
                int tweetPosition = currentTweetPositionByAccount(i, tweetItems.size());
                String tweet = tweetItems.get(tweetPosition).getTweet();
                try {
                    twitter.updateStatus(tweet);
                    Thread.sleep(1000);
                } catch (TwitterException e) {
                    e.printStackTrace();
                    user = null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }


            currentTweetPosition++;
            sharedPreferences.edit().putInt(Const.TwEET_POSITION, currentTweetPosition).commit();

            return user;
        }

        /**
         * After completing background task Dismiss the progress dialog and show
         * the data in UI Always use runOnUiThread(new Runnable()) to update UI
         * from background thread, otherwise you will get error
         * *
         */
        protected void onPostExecute(String user) {
            // dismiss the dialog after getting all products


            // updating UI from Background Thread
        }

    }


    // updating UI from Background Thread


    public void search() {


        ;
        new SearchRequest().execute();
    }

    class SearchRequest extends AsyncTask<Query, ArrayList<QueryResult>, ArrayList<QueryResult>> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        /**
         * getting Places JSON
         */
        protected ArrayList<QueryResult> doInBackground(Query... args) {
            ArrayList<QueryResult> queries = new ArrayList<>();


            try {

//                followersList = twitter.getFollowersList(args[0], args[1]);

//                Long[] ids = new Long[args[0].size()];
//                ids=args[0].toArray(ids);
                String hashTagJson = sharedPreferences.getString(Const.HASH_TAG_LIST, null);

                HashTagListWraper hashTagListWraper = (HashTagListWraper) Utils.getObject(hashTagJson, HashTagListWraper.class);
                ArrayList<HashTag> hashTagArrayList = hashTagListWraper.getHashTagArrayList();
                int i = 0;
                for (Twitter twitter : twitters) {

                    Query currentQuery = new Query(hashTagArrayList.get(currentHashTagOrAaccountPositionByAccount(i, currentHashTagPosition, hashTagArrayList.size())).getHashTag());
                    QueryResult search = twitter.search(currentQuery);
                    queries.add(search);
                    Thread.sleep(1000);

                    i++;
                }

                // beynimin sadakasını nasıl vereceğim ?

                currentHashTagPosition++;
                sharedPreferences.edit().putInt(Const.HASHTAG_POSITION, currentHashTagPosition);

            } catch (TwitterException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return queries;
        }

        /**
         * After completing background task Dismiss the progress dialog and show
         * the data in UI Always use runOnUiThread(new Runnable()) to update UI
         * from background thread, otherwise you will get error
         * *
         */
        protected void onPostExecute(ArrayList<QueryResult> result) {
            // dismiss the dialog after getting all products
            for (QueryResult queryResult : result) {
                List<twitter4j.Status> tweets = queryResult.getTweets();
//            currentQuery = result.nextQuery();
                for (final twitter4j.Status l : tweets) {
                    final User user = l.getUser();
                    Random random = new Random();
                    int i = random.nextInt(10000);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            // tweet follow kısmı

                            boolean hashtagFavStatus = sharedPreferences.getBoolean(Const.HASHTAG_FAV_STATUS, true);
                            boolean hashtagFollowStatus = sharedPreferences.getBoolean(Const.HASH_TAG_FOLLOW_STATUS, true);

                            if (hashtagFavStatus) {
                                new RequestTask().execute(l.getId());
                            }

                            if (hashtagFollowStatus) {
                                new RequestTaskFollow().execute(user.getId());

                            }

                            // tweeti atan kişiyi takip ediyor.


                        }
                    }, i);


                }
            }


            // updating UI from Background Thread
        }

    }

    class RequestTask extends AsyncTask<Long, String, String> {

        @Override
        protected String doInBackground(Long... uri) {
            try {

                Iterator<Twitter> iterator = twitters.iterator();
                while (iterator.hasNext()) {
                    Twitter next = iterator.next();
                    next.createFavorite(uri[0]);
                    Thread.sleep(1000);
                }


            } catch (TwitterException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
        }
    }

    class RequestTaskFollow extends AsyncTask<Long, String, String> {

        @Override
        protected String doInBackground(Long... uri) {
            try {
                Iterator<Twitter> iterator = twitters.iterator();

                while (iterator.hasNext()) {
                    Twitter next = iterator.next();
                    next.createFriendship(uri[0]);
                    Thread.sleep(1000);
                }


            } catch (TwitterException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Do anything with response..
        }
    }


    class GetFollowersTask extends AsyncTask<Long, ArrayList<IDs>, ArrayList<IDs>> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        /**
         * getting Places JSON
         */
        protected ArrayList<IDs> doInBackground(Long... args) {
            ArrayList<IDs> iDses = new ArrayList<>();

            try {

//                followersList = twitter.getFollowersList(args[0], args[1]);


                ArrayList<FenomenAccount> accounts = getAccountList();

                for (int i = 0; i < twitters.size(); i++) {
                    int index = currentHashTagOrAaccountPositionByAccount(i, currentAccountPosition, accounts.size());
                    long account = accounts.get(index).getId();

                    IDs followersList = twitters.get(i).getFollowersIDs(account, -1);
                    iDses.add(followersList);
                }


            } catch (TwitterException e1) {
                e1.printStackTrace();
            }

            currentAccountPosition++;
            sharedPreferences.edit().putInt(Const.ACCOUNT_POSITION, currentAccountPosition).commit();
            return iDses;
        }

        /**
         * After completing background task Dismiss the progress dialog and show
         * the data in UI Always use runOnUiThread(new Runnable()) to update UI
         * from background thread, otherwise you will get error
         * *
         */
        protected void onPostExecute(ArrayList<IDs> iDses) {
            // dismiss the dialog after getting all products

            if (iDses == null || iDses.size() < 1) {
                return;

            } else {

                for (IDs iDs : iDses) {
                    long[] iDs1 = iDs.getIDs();

                    for (int i = 0; i < 20; i++) {
                        final long l = iDs1[i];
                        Random random = new Random();
                        int randomInt = random.nextInt(10000);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

//                            new RequestTask().execute(l);
                                new RequestTaskFollow().execute(l);

                            }
                        }, randomInt);

                    }
                }


            }


        }

    }


    public int currentTweetPositionByAccount(int i, int arrayMaxSize) {

        int position = 0;
        final int tweetInterval = 28;


        position = currentTweetPosition + i * tweetInterval;


        if (position >= arrayMaxSize) {

            position = position % arrayMaxSize;
        }


        return position;


    }

    public ArrayList<FenomenAccount> getAccountList() {
        String string = sharedPreferences.getString(Const.ACCOUNT_LIST, null);
        FenomenAccountsWrapper tweetListWrapper = (FenomenAccountsWrapper) Utils.getObject(string, FenomenAccountsWrapper.class);
        if (tweetListWrapper != null) {
            return tweetListWrapper.getFenomenAccounts();
        }

        return new ArrayList<>();

    }

    public int currentHashTagOrAaccountPositionByAccount(int i, int currentHashTagOrAccountPosition, int arrayMaxSize) {

        int position = 0;
        final int tweetInterval = 1;


        position = currentHashTagOrAccountPosition + i * tweetInterval;


        if (position >= arrayMaxSize) {

            position = position % arrayMaxSize;
        }


        return position;


    }

}
