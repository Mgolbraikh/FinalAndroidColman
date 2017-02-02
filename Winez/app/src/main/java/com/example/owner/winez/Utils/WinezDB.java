package com.example.owner.winez.Utils;


import android.util.Log;

import com.example.owner.winez.Model.Entity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;

import java.util.LinkedList;
import java.util.List;


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
                getOnCompleteResult.onResult((C)dataSnapshot.getValue(tclass));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                getOnCompleteResult.onCancel();
            }
        });
    }
    private DatabaseReference getChild(String entityName, String id) {
        return this.getCollection(entityName).child(id);
    }

    public void saveWithId(String entityName, Entity toSave){
        this.mDatabase.getReference(entityName).setValue(toSave.getUid(),toSave);
    }

    public <C extends Entity> void getAll(final String entityName, final Class<C> tclass, String id,
                                          final GetOnCompleteResults<C> GetOnCompleteResults, double lastUpdateDate ){
        getCollection(entityName).orderByChild("lastUpdated").startAt(lastUpdateDate)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //getOnCompleteResult.onResult((C)dataSnapshot.getValue(tclass));
                final List<C> entityList = new LinkedList<C>();
                for (DataSnapshot entitySnapshot : dataSnapshot.getChildren()) {
                    C entity = entitySnapshot .getValue(tclass);
                    Log.d("TAG", entity.getUid());
                    entityList.add(entity);
                }
                GetOnCompleteResults.onResult(entityList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                GetOnCompleteResults.onCancel();
            }
        });
    }

    public void saveWithoutId(String entityName, Entity toSave) {
        DatabaseReference ref = this.mDatabase.getReference(entityName).push();
        String key = ref.getKey();
        ref.setValue(key,toSave);
        toSave.setUid(key);
    }

    public interface GetOnCompleteResult<T>{
        public void onResult(T data);
        public void onCancel();
    }

    /**
     *
     * @see this is fir recieving lists
     */
    public interface GetOnCompleteResults<T>{
        public void onResult(List<T> data);
        public void onCancel();
    }
}
