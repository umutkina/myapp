package com.umutkina.findunfollowersapp;

import android.os.Bundle;
import android.widget.ListView;

import com.umutkina.findunfollowersapp.adapters.UserAdapter;
import com.umutkina.findunfollowersapp.modals.ModelUser;
import com.umutkina.findunfollowersapp.modals.UserWrapper;
import com.umutkina.findunfollowersapp.utils.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class UsersActivity extends BaseActivity {

    @InjectView(R.id.lv_user_list)
    ListView lvUserList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ButterKnife.inject(this);

        UserWrapper userWrapperFromPrefs = Utils.getUserWrapperFromPrefs(this);
        ArrayList<ModelUser> users = userWrapperFromPrefs.getUsers();

        UserAdapter userAdapter=new UserAdapter(this);
        userAdapter.setModelUsers(users);
        lvUserList.setAdapter(userAdapter);
    }
}
