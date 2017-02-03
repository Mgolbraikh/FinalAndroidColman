package com.example.owner.winez.Model;

import com.example.owner.winez.Utils.WinezDB;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.author;

/**
 * Created by Ziv on 28/01/2017.
 */
public class Comment extends Entity {
    private String wineID;
    private String userID;
    private String text;
    private String userName;

    public Comment(){

    }
    public Comment(String wineID, String userID, String text, String userName) {
        this.wineID = wineID;
        this.userID = userID;
        this.text = text;
        this.userName = userName;
    }

    public String getUserID(){
        return this.userID;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }

    public String getText(){
        return this.text;
    }

    public void setText(String text){
        this.text = text;
    }
    public String getWineID() {
        return wineID;
    }

    public void setWineID(String wineID) {
        this.wineID = wineID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
