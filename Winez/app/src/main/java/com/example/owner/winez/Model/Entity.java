package com.example.owner.winez.Model;


import com.example.owner.winez.Utils.WinezDB;
import com.google.android.gms.tasks.Task;

import java.util.Date;
import java.util.StringTokenizer;
import java.util.UUID;


/**
 * Created by Ziv on 28/01/2017.
 */

public abstract class Entity {
    private String uid;
    private long saveTimeStamp;

    public Entity(){
        this.setUid("");
    }
    public Entity(String uid){
        this.uid = uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getUid() { return uid; }

    public long getSaveTimeStamp(){
        return this.saveTimeStamp;
    }

    public void setSaveTimeStamp(long timeStamp){
        this.saveTimeStamp = timeStamp;
    }

    public Task<Void> save(){
        this.setSaveTimeStamp(new Date().getTime());
        if (this.uid == ""){
            this.setUid(WinezDB.getInstance().getCollection(this.getClass().getSimpleName()).push().getKey());
        }
        return WinezDB.getInstance().getCollection(this.getClass().getSimpleName()).child(this.uid).setValue(this);
    }
}
