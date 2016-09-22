package com.umutkina.findunfollowersapp.modals;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by umutkina on 28/08/16.
 */
public class UserWrapper implements Serializable{

    private static final long serialVersionUID = -5632309488811794671L;
    ArrayList<ModelUser> users;

    public ArrayList<ModelUser> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<ModelUser> users) {
        this.users = users;
    }
}
