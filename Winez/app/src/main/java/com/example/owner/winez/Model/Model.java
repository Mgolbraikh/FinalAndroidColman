package com.example.owner.winez.Model;

import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;

import com.example.owner.winez.Manifest;
import com.example.owner.winez.MyApplication;
import com.example.owner.winez.Utils.ApiClasses.WineApiClass;
import com.example.owner.winez.Utils.ModelSQL.UserSQL;
import com.example.owner.winez.Utils.ModelSQL.WineSQL;
import com.example.owner.winez.Utils.WineApi;
import com.example.owner.winez.Utils.WinesLocalDB;
import com.example.owner.winez.Utils.WinezAuth;
import com.example.owner.winez.Utils.WinezDB;
import com.example.owner.winez.Utils.WinezStorage;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.List;

/**
 * Created by owner on 01-Feb-17.
 */


public class Model {
    private static Model instance;
    WinezDB mRemoteDB;
    WinesLocalDB modelLocalSql;

    private Model() {
        modelLocalSql = new WinesLocalDB(MyApplication.getAppContext());
        mRemoteDB = WinezDB.getInstance();
    }

    public static Model getInstance(){
        if (instance == null){
            instance = new Model();
        }
        return instance ;
    }

    /**
     * Get a user by user uid
     * @param id
     * @param getOnCompleteResult
     */
    public void getUser(String id, WinezDB.GetOnCompleteResult<User> getOnCompleteResult){
        this.mRemoteDB.getSingle(User.class.getSimpleName(), User.class,id, getOnCompleteResult);
    }

    /**
     * Get a wine by wine uid
     * @param id
     * @param getOnCompleteResult
     */
    public void getWine(String id, WinezDB.GetOnCompleteResult<Wine> getOnCompleteResult){
        this.mRemoteDB.getSingle(Wine.class.getSimpleName(), Wine.class,id, getOnCompleteResult);
    }

    public void getCommentsForWine(String wineId,
                                   WinezDB.GetOnCompleteResult<List<Comment>> getOnCompleteResult){
        this.mRemoteDB.getAllChildren(Comment.class.getSimpleName(), Comment.class, wineId, getOnCompleteResult);
    }

    public Task<Void> saveCurrentUser(User toSave){
        return this.mRemoteDB.saveWithId(User.class.getSimpleName(),toSave);
    }


    public void saveComment(Comment cmt){
        this.mRemoteDB.saveChildWithoutId(Comment.class.getSimpleName(), cmt.getWineID(), cmt);
    }

    public Task<AuthResult> authenticate(String email, String password) {
        return WinezAuth.getInstance().authenticate(email,password);
    }

    public void signOut() {
        WinezAuth.getInstance().signOut();
    }

    public void setOnAuthChangeListener(WinezAuth.OnAuthChangeListener onAuthChangeListener){
        WinezAuth.getInstance().setOnAuthChangeListener(onAuthChangeListener);
    }

    public boolean isAuthenticated() {
        return WinezAuth.getInstance().isAuthenticated();
    }

    public User getCurrentUser() {
        return WinezAuth.getInstance().getCurrentUser();
    }

    public void addWine(WineApiClass wineToAdd)
    {
        this.mRemoteDB.saveWithId(Wine.class.getSimpleName(), new Wine(wineToAdd));
        this.getCurrentUser().getUserWines().put(wineToAdd.getId(), wineToAdd.getName());
        this.saveCurrentUser(this.getCurrentUser());
    }

    public void removeWine(WineApiClass toRemove){
        this.getCurrentUser().getUserWines().remove(toRemove.getId());
        this.saveCurrentUser(this.getCurrentUser());
    }

    public void saveImage(Bitmap image, String url, WinezStorage.OnSaveCompleteListener onSaveCompleteListener) {
        WinezStorage.getInstance().saveImage(image,url,onSaveCompleteListener);
    }

    public void getDBUser() {
        WinezAuth.getInstance().fetchUser();
    }
}
