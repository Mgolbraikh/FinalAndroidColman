package com.example.owner.winez.Utils;


import android.support.annotation.NonNull;


import com.example.owner.winez.Model.Comment;
import com.example.owner.winez.Model.Entity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;

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

                getOnCompleteResult.onResult(getChildren(tClass,dataSnapshot));
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

    public Task<Void> saveWithId(String entityName, Entity toSave){
        toSave.setSaveTimeStamp(new Date().getTime());
        return this.mDatabase.getReference(entityName).child(toSave.getUid()).updateChildren(toSave.toMap());
    }


    public Task<Void> saveWithoutId(String entityName, Entity toSave) {
        toSave.setSaveTimeStamp(new Date().getTime());
        DatabaseReference ref = this.mDatabase.getReference(entityName).push();
        String key = ref.getKey();
        toSave.setUid(key);
        return ref.setValue(key,toSave.toMap());
    }

    public void saveChild(String entityName, String parentId, Entity toSave){
        toSave.setSaveTimeStamp(new Date().getTime());
        this.getCollection(entityName).child(parentId).child(toSave.getUid()).setValue(toSave.toMap());
    }

    public Task<Void> saveChildWithoutId(String entityName, String parentId, Entity toSave){
        toSave.setSaveTimeStamp(new Date().getTime());
        DatabaseReference ref = this.getCollection(entityName).child(parentId).push();
        String key = ref.getKey();
        toSave.setUid(key);
        return ref.setValue(toSave.toMap());
    }

    public <C extends Entity> void getUpdatesForChildren(String entityName, final Class<C> classC, String id, final OnChildEventListener<C> onChildEventListener) {
        this.getChild(entityName,id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                onChildEventListener.onChildAdded(dataSnapshot.getValue(classC), s);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                onChildEventListener.onChildChanged(dataSnapshot.getValue(classC),s);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                onChildEventListener.onChildRemoved(dataSnapshot.getValue(classC));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                onChildEventListener.onChildMoved(dataSnapshot.getValue(classC),s);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onChildEventListener.onCancelled(databaseError.getMessage());
            }
        });
    }

    /**
     * Generic complete listener
     * @param <T>
     */
    public interface GetOnCompleteResult<T>{
        void onResult(T data);
        void onCancel(String err);
    }

    public interface OnChildEventListener<T>{
        void onChildAdded(T child, String previousChildName);
        void onChildChanged(T child, String previousChildName);
        void onChildRemoved(T child);
        void onChildMoved(T child,String previousChildName);
        void onCancelled(String err);

    }

}
