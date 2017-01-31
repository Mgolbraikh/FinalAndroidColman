package com.example.owner.winez.Utils;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by owner on 31-Jan-17.
 */


// TODO : Michael - Complite here all actions on table user and add others tables
public class UserSQL {
    final static String USER_TABLE = "users";
    final static String USER_TABLE_ID = "_id";
    final static String USER_TABLE_NAME = "name";
    final static String USER_TABLE_MAIL = "phone";

    static public void create(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE + " (" +
                USER_TABLE_ID + " TEXT PRIMARY KEY," +
                USER_TABLE_NAME + " TEXT," +
                USER_TABLE_MAIL + " TEXT);");
    }

}
