package com.example.owner.winez.Utils.ModelSQL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.owner.winez.Model.User;
import com.example.owner.winez.Model.Wine;
import com.example.owner.winez.Utils.LastUpdateSql;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by owner on 01-Feb-17.
 */

public class WineSQL {

    final static String WINE_TABLE = "wines";
    final static String WINE_TABLE_ID = "_id";
    final static String WINE_TABLE_NAME = "name";
    final static String WINE_TABLE_PICTURE = "picture";

    static public void create(SQLiteDatabase db) {
        db.execSQL("create table " + WINE_TABLE + " (" +
                WINE_TABLE_ID + " TEXT PRIMARY KEY," +
                WINE_TABLE_NAME + " TEXT," +
                WINE_TABLE_PICTURE + " TEXT);");
    }

    public static void DeleteAll(SQLiteDatabase db) {
        db.execSQL("delete from  " + WINE_TABLE + ";");
    }

    public static void drop(SQLiteDatabase db) {
        try {


            Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", WINE_TABLE});
            if (cursor.moveToFirst()) {
                db.execSQL("drop table " + WINE_TABLE + ";");
            }
            cursor.close();
        }catch (Exception e){
            Log.d("Exception:", e.getMessage());
        }
    }

    public static List<Wine> getAllWines(SQLiteDatabase db) {
        Cursor cursor = db.query(WINE_TABLE, null, null , null, null, null, null);
        List<Wine> wines = new LinkedList<Wine>();

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(WINE_TABLE_ID);
            int nameIndex = cursor.getColumnIndex(WINE_TABLE_NAME);
            int pictureIndex = cursor.getColumnIndex(WINE_TABLE_PICTURE);
            do {
                String id = cursor.getString(idIndex);
                String name = cursor.getString(nameIndex);
                String picture = cursor.getString(pictureIndex);
                Wine wine = new Wine(id,name);
                wines.add(wine);
            } while (cursor.moveToNext());
        }
        return wines;
    }

    @Nullable
    public static User getUserById(SQLiteDatabase db, String id) {
        String where = WINE_TABLE_ID + " = ?";
        String[] args = {id};
        Cursor cursor = db.query(WINE_TABLE, null, where, args, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(WINE_TABLE_ID);
            int nameIndex = cursor.getColumnIndex(WINE_TABLE_NAME);
            int mailIndex = cursor.getColumnIndex(WINE_TABLE_PICTURE);
            String _id = cursor.getString(idIndex);
            String name = cursor.getString(nameIndex);
            String mail = cursor.getString(mailIndex);
            User usr = new User(name, mail, _id);
            return usr;
        }

        return null;
    }

    public static void add(SQLiteDatabase db, Wine wine) {
        ContentValues values = new ContentValues();
        values.put(WINE_TABLE_ID, wine.getUid());
        values.put(WINE_TABLE_NAME, wine.getName());
        values.put(WINE_TABLE_PICTURE, wine.getPicture());
        db.insertWithOnConflict(WINE_TABLE, WINE_TABLE_ID, values,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public static double getLastUpdateDate(SQLiteDatabase db){
        return LastUpdateSql.getLastUpdate(db, WINE_TABLE);
    }
    public static void setLastUpdateDate(SQLiteDatabase db, double date){
        LastUpdateSql.setLastUpdate(db, WINE_TABLE, date);
    }

    public static void add(SQLiteDatabase db, Map<String,String> wines) {
        for (Map.Entry<String,String> wine:  wines.entrySet()){
            add(db, new Wine(wine.getKey(), wine.getValue()));
        }
        //this.add(db,);
    }
}
