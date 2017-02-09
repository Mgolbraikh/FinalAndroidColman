package com.example.owner.winez.Utils.ModelSQL;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.owner.winez.Model.Wine;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by owner on 01-Feb-17.
 */

public class WineSQL extends EntitySQL<Wine> {

    private static WineSQL _instance;

    private WineSQL(){}
    public static WineSQL getInstance(){
        if(_instance == null){
            _instance = new WineSQL();
        }
        return _instance;
    }

    @Override
    protected String getTable() {
        return "wines";
    }

    @Override
    protected String getTableName() {
        return "name";
    }

    @Override
    protected String getTableID() {
        return "_id";
    }

    String getTablePicture() {
        return "picture";
    }

    public void create(SQLiteDatabase db) {
        db.execSQL("create table " + this.getTable() + " (" +
                this.getTableID() + " TEXT PRIMARY KEY," +
                this.getTableName() + " TEXT," +
                this.getTablePicture() + " TEXT);");
    }

    @Override
    public List<Wine> getAllEntities(SQLiteDatabase db) {
        Cursor cursor = db.query(this.getTable(), null, null , null, null, null, null);
        List<Wine> wines = new LinkedList<Wine>();

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(this.getTableID());
            int nameIndex = cursor.getColumnIndex(this.getTableName());
            int pictureIndex = cursor.getColumnIndex(this.getTablePicture());
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

    @Override
    public void addEntity(SQLiteDatabase db, Wine wine) {
        ContentValues values = new ContentValues();
        values.put(this.getTableID(), wine.getUid());
        values.put(this.getTableName(), wine.getName());
        values.put(this.getTablePicture(), wine.getPicture());
        db.insertWithOnConflict(this.getTable(), this.getTableID(), values,SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void add(SQLiteDatabase db, Map<String,String> wines) {
        for (Map.Entry<String,String> wine:  wines.entrySet()){
            this.addEntity(db, new Wine(wine.getKey(), wine.getValue()));
        }
    }

    public void delete(SQLiteDatabase writableDB, String id) {
        writableDB.delete(this.getTable(),this.getTableID() + " = " + id,null);
    }
}
