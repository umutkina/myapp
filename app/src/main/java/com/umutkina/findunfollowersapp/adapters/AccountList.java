package com.umutkina.findunfollowersapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.umutkina.findunfollowersapp.AccountAddActivity;
import com.umutkina.findunfollowersapp.R;
import com.umutkina.findunfollowersapp.modals.FenomenAccount;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by umutkina on 25/10/16.
 */
public class AccountList extends BaseAdapter {


    Context context;

    ArrayList<FenomenAccount> fenomenAccounts = new ArrayList<>();


    public AccountList(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return fenomenAccounts.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.tweet_list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        final FenomenAccount user = fenomenAccounts.get(position);
        holder.tvTweet.setText(position + " : " + user.getName());

        holder.tvRemoveTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fenomenAccounts.remove(user);
//                notifyDataSetChanged();
//

                AccountAddActivity addTweetListItemActivity = (AccountAddActivity) context;
                addTweetListItemActivity.setFenomenAccountItem(fenomenAccounts);
            }
        });


        return convertView;
    }

    public ArrayList<FenomenAccount> getFenomenAccounts() {
        return fenomenAccounts;
    }

    public void setFenomenAccounts(ArrayList<FenomenAccount> fenomenAccounts) {
        this.fenomenAccounts = fenomenAccounts;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_tweet)
        TextView tvTweet;
        @InjectView(R.id.tv_remove_tweet)
        TextView tvRemoveTweet;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
