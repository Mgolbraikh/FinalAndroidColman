package com.example.owner.winez.Utils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by Ziv on 28/01/2017.
 */

public class WinezDB {
    private DatabaseReference mDatabase;
    private static WinezDB _instance;
    private WinezDB(){
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static WinezDB getInstance(){
        if(_instance == null){
            _instance = new WinezDB();
        }
        return _instance;
    }

    public DatabaseReference getCollection(Class classT){
        return this.mDatabase.child(classT.getSimpleName());
    }

    public void getSingle(Class classT, String id, ValueEventListener valueEventListener){
        getChild(classT, id).addListenerForSingleValueEvent(valueEventListener);
    }

    private DatabaseReference getChild(Class classT, String id) {
        return this.getCollection(classT).child(id);
    }

    public ValueEventListener getUpdates(Class classT,String id,ValueEventListener valueEventListener){
        return this.getChild(classT,id).addValueEventListener(valueEventListener);
    }

    public void removeListener(ValueEventListener valueEventListener){
        this.mDatabase.removeEventListener(valueEventListener);
    }
}
