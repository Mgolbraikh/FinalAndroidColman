package com.example.owner.winez.Utils.ModelSQL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.example.owner.winez.Model.User;
import com.example.owner.winez.Utils.LastUpdateSql;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by owner on 31-Jan-17.
 */


// TODO : Michael - Complite here all actions on table user and add others tables
public class UserSQL {
    final static String USER_TABLE = "users";
    final static String USER_TABLE_ID = "_id";
    final static String USER_TABLE_NAME = "name";
    final static String USER_TABLE_MAIL = "email";

    static public void create(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE + " (" +
                USER_TABLE_ID + " TEXT PRIMARY KEY," +
                USER_TABLE_NAME + " TEXT," +
                USER_TABLE_MAIL + " TEXT);");
    }

    public static void drop(SQLiteDatabase db) {
        db.execSQL("drop table " + USER_TABLE + ";");
    }

    public static List<User> getAllStudents(SQLiteDatabase db) {
        Cursor cursor = db.query(USER_TABLE, null, null , null, null, null, null);
        List<User> users = new LinkedList<User>();

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(USER_TABLE_ID);
            int nameIndex = cursor.getColumnIndex(USER_TABLE_NAME);
            int emailIndex = cursor.getColumnIndex(USER_TABLE_MAIL);
            do {
                String id = cursor.getString(idIndex);
                String name = cursor.getString(nameIndex);
                String email = cursor.getString(emailIndex);
                User st = new User(name,email, id);
                users.add(st);
            } while (cursor.moveToNext());
        }
        return users;
    }

    @Nullable
    public static User getUserById(SQLiteDatabase db, String id) {
        String where = USER_TABLE_ID + " = ?";
        String[] args = {id};
        Cursor cursor = db.query(USER_TABLE, null, where, args, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(USER_TABLE_ID);
            int nameIndex = cursor.getColumnIndex(USER_TABLE_NAME);
            int mailIndex = cursor.getColumnIndex(USER_TABLE_MAIL);
            String _id = cursor.getString(idIndex);
            String name = cursor.getString(nameIndex);
            String mail = cursor.getString(mailIndex);
            User usr = new User(name, mail, _id);
            return usr;
        }

        return null;
    }

    public static void add(SQLiteDatabase db, User usr) {
        ContentValues values = new ContentValues();
        values.put(USER_TABLE_ID, usr.getUid());
        values.put(USER_TABLE_NAME, usr.getName());
        values.put(USER_TABLE_MAIL, usr.getEmail());
        db.insertWithOnConflict(USER_TABLE, USER_TABLE_ID, values,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static double getLastUpdateDate(SQLiteDatabase db){
        return LastUpdateSql.getLastUpdate(db,USER_TABLE);
    }
    public static void setLastUpdateDate(SQLiteDatabase db, double date){
        LastUpdateSql.setLastUpdate(db,USER_TABLE, date);
    }
}
