package com.example.owner.winez.Model;

import android.graphics.PorterDuff;

import com.example.owner.winez.MyApplication;
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

    public interface GetUser{
        public void onResult(User usr);
        public void onCancel();
    }
    public interface GetWine{
        public void onResult(Wine wine);
        public void onCancel();
    }
}
