package com.example.owner.winez.Utils;


import android.support.annotation.NonNull;

import com.example.owner.winez.Model.Entity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Ziv on 28/01/2017.
 */

public class WinezDB {
    private FirebaseDatabase mDatabase;
    private static WinezDB _instance;
    private WinezDB(){
        this.mDatabase = FirebaseDatabase.getInstance();
    }

    public static WinezDB getInstance(){
        if(_instance == null){
            _instance = new WinezDB();
        }
        return _instance;
    }

    public DatabaseReference getCollection(String entityName){
        return this.mDatabase.getReference(entityName);
    }

    /**
     *
     * @param entityName The name of the entity in the DB
     * @param tclass the returned class
     * @param id The wanted id
     * @param getOnCompleteResult The callback interface
     * @param <C> Returned class
     */
    public <C extends Entity> void getSingle(String entityName, final Class<C> tclass, String id, final GetOnCompleteResult<C> getOnCompleteResult){
        getChild(entityName, id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getOnCompleteResult.onResult(dataSnapshot.getValue(tclass));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getOnCompleteResult.onCancel(databaseError.getMessage());
            }
        });
    }

    public <C extends Entity> void getAll(final String entityName, final Class<C> tClass, final GetOnCompleteResult<List<C>> getOnCompleteResult){
        getCollection(entityName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                getOnCompleteResult.onResult((List<C>)getChildren(tClass,dataSnapshot));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getOnCompleteResult.onCancel(databaseError.getMessage());
            }
        });
    }

    public <C extends Entity> void getAllChildren(final String entityName,
                                                  final Class<C> tClass,
                                                  String parentID,
                                                  final GetOnCompleteResult<List<C>> getOnCompleteResult){
        getChild(entityName,parentID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<C> toReturn = getChildren(tClass, dataSnapshot);
                getOnCompleteResult.onResult(toReturn);
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {
                getOnCompleteResult.onCancel(databaseError.getMessage());
            }
        });
    }

    private <C extends Entity> List<C> getChildren(Class<C> tClass ,DataSnapshot dataSnapshot) {
        List<C> toReturn = new ArrayList<C>();
        for (DataSnapshot snap : dataSnapshot.getChildren()){
            toReturn.add(snap.getValue(tClass));
        }
        return toReturn;
    }
    private DatabaseReference getChild(String entityName, String id) {
        return this.getCollection(entityName).child(id);
    }

    public void saveWithId(String entityName, Entity toSave){
        toSave.setSaveTimeStamp(new Date().getTime());
        this.mDatabase.getReference(entityName).setValue(toSave.getUid(),toSave);
    }

    public void saveWithoutId(String entityName, Entity toSave) {
        toSave.setSaveTimeStamp(new Date().getTime());
        DatabaseReference ref = this.mDatabase.getReference(entityName).push();
        String key = ref.getKey();
        ref.setValue(key,toSave);
        toSave.setUid(key);
    }

    public void saveChild(String entityName, String parentId, Entity toSave){
        toSave.setSaveTimeStamp(new Date().getTime());
        this.getCollection(entityName).child(parentId).setValue(toSave.getUid(),toSave);
    }

    public void saveChildWithoutId(String entityName, String parentId, Entity toSave){
        toSave.setSaveTimeStamp(new Date().getTime());
        DatabaseReference ref = this.getCollection(entityName).child(parentId).push();
        String key = ref.getKey();
        ref.setValue(key,toSave);
        toSave.setUid(key);
    }
    public interface GetOnCompleteResult<T>{
        public void onResult(T data);
        public void onCancel(String err);
    }
}
