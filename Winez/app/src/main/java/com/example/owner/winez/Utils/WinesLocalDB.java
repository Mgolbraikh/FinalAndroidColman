package com.example.owner.winez.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.owner.winez.Utils.ModelSQL.UserSQL;
import com.example.owner.winez.Utils.ModelSQL.WineSQL;

/**
 * Created by owner on 31-Jan-17.
 */

public class WinesLocalDB {
    final static int VERSION = 7;

    Helper sqlDb;


    public WinesLocalDB(Context context){
        sqlDb = new Helper(context);
    }

    public SQLiteDatabase getWritableDB(){
        return sqlDb.getWritableDatabase();
    }

    public SQLiteDatabase getReadbleDB(){
        return sqlDb.getReadableDatabase();
    }

    class Helper extends SQLiteOpenHelper {
        public Helper(Context context) {
            super(context, "database.db", null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            UserSQL.getInstance().create(db);
            WineSQL.getInstance().create(db);
            LastUpdateSql.create(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO : Michael - complete here all operations on table
            //StudentSql.drop(db);
            //LastUpdateSql.drop(db);
            onCreate(db);
        }
    }

}
