package com.example.owner.winez.Model;

import android.graphics.PorterDuff;

import com.example.owner.winez.MyApplication;
import com.example.owner.winez.Utils.ModelSQL.UserSQL;
import com.example.owner.winez.Utils.ModelSQL.WineSQL;
import com.example.owner.winez.Utils.WinesLocalDB;
import com.example.owner.winez.Utils.WinezDB;

/**
 * Created by owner on 01-Feb-17.
 */


public class Model {
    private final static Model instance = new Model();
    WinezDB modelRemoteSql;
    WinesLocalDB modelLocalSql;

    private Model() {
        modelLocalSql = new WinesLocalDB(MyApplication.getAppContext());
        modelRemoteSql = WinezDB.getInstance();
    }

    public Model getInstance(){
        return instance ;
    }

    /**
     * Get a user by user uid
     * @param id
     * @param getOnCompleteResult
     */
    public void getUser(String id, WinezDB.GetOnCompleteResult<User> getOnCompleteResult){
        this.modelRemoteSql.getSingle(User.class.getSimpleName(), User.class,id, getOnCompleteResult);
    }

    /**
     * Get a wine by wine uid
     * @param id
     * @param getOnCompleteResult
     */
    public void getWine(String id, WinezDB.GetOnCompleteResult<Wine> getOnCompleteResult){
        this.modelRemoteSql.getSingle(Wine.class.getSimpleName(), Wine.class,id, getOnCompleteResult);
    }

    /**
     * Get all users from the remote DB
     * @param getOnCompleteResults
     */
    public void getAllUsersAsynch(final WinezDB.GetOnCompleteResults<User> getOnCompleteResults){
        //1. get the last update date
        final double lastUpdateDate = UserSQL.getLastUpdateDate(modelLocalSql.getReadbleDB());

        //2. get all records that where updated since last update date
        modelRemoteSql.getAll(User.class.getSimpleName(), User.class, lastUpdateDate, getOnCompleteResults);

        //modelRemoteSql.getCollection()
    }


    /**
     * Get all Wines from the remote DB
     * @param getOnCompleteResults
     */
    public void getAllWinesAsynch(final WinezDB.GetOnCompleteResults<Wine> getOnCompleteResults){
        //1. get the last update date
         final double lastUpdateDate = WineSQL.getLastUpdateDate(modelLocalSql.getReadbleDB());

        //2. get all records that where updated since last update date
        modelRemoteSql.getAll(Wine.class.getSimpleName(), Wine.class, lastUpdateDate, getOnCompleteResults);

        //modelRemoteSql.getCollection()
    }
}
