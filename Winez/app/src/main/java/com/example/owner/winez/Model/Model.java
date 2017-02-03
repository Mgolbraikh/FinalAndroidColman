package com.example.owner.winez.Model;

import android.graphics.PorterDuff;
import android.util.Log;

import com.example.owner.winez.MyApplication;
import com.example.owner.winez.Utils.ModelSQL.UserSQL;
import com.example.owner.winez.Utils.ModelSQL.WineSQL;
import com.example.owner.winez.Utils.WinesLocalDB;
import com.example.owner.winez.Utils.WinezDB;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * Get all users from the remote DB
     * @param getOnCompleteResult
     */
    public void getAllUsersAsynch(final WinezDB.GetOnCompleteResult<User> getOnCompleteResult){
        //1. get the last update date
        // final double lastUpdateDate = UserSQL.getLastUpdateDate(modelLocalSql.getReadbleDB());

        //2. get all records that where updated since last update date
        //modelRemoteSql.getCollection(User.class.getSimpleName() ,lastUpdateDate);

        //modelRemoteSql.getCollection()
    }


    /**
     * Get all Wines from the remote DB
     * @param getOnCompleteResult
     */
    public void getAllWinesAsynch(final WinezDB.GetOnCompleteResult<User> getOnCompleteResult){
        //1. get the last update date
         final double lastUpdateDate = WineSQL.getLastUpdateDate(modelLocalSql.getReadbleDB());

        //2. get all records that where updated since last update date
        //modelRemoteSql.getCollection(User.class.getSimpleName() ,lastUpdateDate);

        //modelRemoteSql.getCollection()
    }
}
