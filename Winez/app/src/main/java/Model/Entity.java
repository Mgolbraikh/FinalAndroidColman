package Model;

import com.google.android.gms.tasks.Task;

import Utils.WinezDB;

/**
 * Created by Ziv on 28/01/2017.
 */

public abstract class Entity {
    private String uid;
    public String getUid() { return uid; }
    public Entity(String uid){
        this.uid = uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public Task<Void> save(){
        return WinezDB.getInstance().getCollection(this.getClass()).child(this.uid).setValue(this);
    }
}
