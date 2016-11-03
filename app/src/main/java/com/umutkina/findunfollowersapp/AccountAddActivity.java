package com.umutkina.findunfollowersapp;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.umutkina.findunfollowersapp.adapters.AccountList;
import com.umutkina.findunfollowersapp.modals.Const;
import com.umutkina.findunfollowersapp.modals.FenomenAccount;
import com.umutkina.findunfollowersapp.modals.FenomenAccountsWrapper;
import com.umutkina.findunfollowersapp.utils.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AccountAddActivity extends BaseActivity {

    @InjectView(R.id.tv_tweet_list_name)
    TextView tvTweetListName;
    @InjectView(R.id.et_account_name)
    EditText etAccountName;
    @InjectView(R.id.et_account_id)
    EditText etAccountId;
    @InjectView(R.id.tv_submit_tweet)
    TextView tvSubmitTweet;
    @InjectView(R.id.lv_tweet_item_list)
    ListView lvTweetItemList;


    ArrayList<FenomenAccount> fenomenAccounts;
    AccountList accountList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_add);
        ButterKnife.inject(this);

        fenomenAccounts = getAccountLists();

        tvTweetListName.setText("HashTag");


        accountList = new AccountList(this);

        accountList.setFenomenAccounts(fenomenAccounts);
        lvTweetItemList.setAdapter(accountList);

    }

    public ArrayList<FenomenAccount> getAccountLists() {
        String string = mSharedPreferences.getString(Const.ACCOUNT_LIST, null);
        FenomenAccountsWrapper tweetListWrapper = (FenomenAccountsWrapper) Utils.getObject(string, FenomenAccountsWrapper.class);
        if (tweetListWrapper != null) {
            return tweetListWrapper.getFenomenAccounts();
        }

        return new ArrayList<>();

    }

    @OnClick(R.id.tv_submit_tweet)
    public void submitTweet(){
        String accountIdStr = etAccountId.getText().toString();
        String accountName = etAccountName.getText().toString();
        etAccountId.setText("");
        FenomenAccount fenomenAccount=new FenomenAccount();
        fenomenAccount.setName(accountName);
        fenomenAccount.setId(Long.parseLong(accountIdStr));

        fenomenAccounts.add(fenomenAccount);

        ;

        saveTweetList(fenomenAccounts);

        accountList.setFenomenAccounts(fenomenAccounts);
        accountList.notifyDataSetChanged();

    }
    public void setFenomenAccountItem(ArrayList<FenomenAccount> hashTags){


        fenomenAccounts=hashTags;

        saveTweetList(fenomenAccounts);

        accountList.setFenomenAccounts(fenomenAccounts);
        accountList.notifyDataSetChanged();
    }

    public void saveTweetList(ArrayList<FenomenAccount> hashTags) {
        FenomenAccountsWrapper hashTagListWraper= new FenomenAccountsWrapper();
        hashTagListWraper.setFenomenAccounts(hashTags);
        String json = Utils.getJson(hashTagListWraper);

        mSharedPreferences.edit().putString(Const.ACCOUNT_LIST,json).commit();
    }
}
