package com.example.owner.winez.Model;

import com.example.owner.winez.MyApplication;
import com.example.owner.winez.Utils.ApiClasses.WineApiClass;
import com.example.owner.winez.Utils.ModelSQL.UserSQL;
import com.example.owner.winez.Utils.ModelSQL.WineSQL;
import com.example.owner.winez.Utils.WineApi;
import com.example.owner.winez.Utils.WinesLocalDB;
import com.example.owner.winez.Utils.WinezAuth;
import com.example.owner.winez.Utils.WinezDB;
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
    /**
     * Get all users from the remote DB
     * @param getOnCompleteResults
     */
    public void getAllUsersAsynch(final WinezDB.GetOnCompleteResults<User> getOnCompleteResults){
        //1. get the last update date
        final double lastUpdateDate = UserSQL.getLastUpdateDate(modelLocalSql.getReadbleDB());

        //2. get all records that where updated since last update date
        //modelRemoteSql.getAll(User.class.getSimpleName(), User.class, lastUpdateDate, getOnCompleteResults);

        //modelRemoteSql.getCollection()
    }

    public void saveComment(Comment cmt){
        this.mRemoteDB.saveChildWithoutId(Comment.class.getSimpleName(), cmt.getWineID(), cmt);
    }

    /**
     * Get all Wines from the remote DB
     * @param getOnCompleteResults
     */
    public void getAllWinesAsynch(final WinezDB.GetOnCompleteResults<Wine> getOnCompleteResults){
        //1. get the last update date
         final double lastUpdateDate = WineSQL.getLastUpdateDate(modelLocalSql.getReadbleDB());

        //2. get all records that where updated since last update date
        //modelRemoteSql.getAll(Wine.class.getSimpleName(), Wine.class, lastUpdateDate, getOnCompleteResults);

        //modelRemoteSql.getCollection()
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
        this.getCurrentUser().getUserWines().put(wineToAdd.getId(), wineToAdd.getName());
        this.saveCurrentUser(this.getCurrentUser());
    }
}
