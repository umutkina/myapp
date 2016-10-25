package com.umutkina.findunfollowersapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.umutkina.findunfollowersapp.R;
import com.umutkina.findunfollowersapp.modals.ModelUser;
import com.umutkina.findunfollowersapp.utils.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by umutkina on 23/10/16.
 */
public class UserAdapter extends BaseAdapter {


    Context context;

    ArrayList<ModelUser> modelUsers = new ArrayList<>();


    public UserAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return modelUsers.size();
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

        final ModelUser user = modelUsers.get(position);
        holder.tvTweet.setText(position + " : " + user.getName());

        holder.tvRemoveTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modelUsers.remove(user);
                Utils.removeUserWrapper(context, user);
                notifyDataSetChanged();


//

            }
        });


        return convertView;
    }

    public ArrayList<ModelUser> getModelUsers() {
        return modelUsers;
    }

    public void setModelUsers(ArrayList<ModelUser> modelUsers) {
        this.modelUsers = modelUsers;
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

