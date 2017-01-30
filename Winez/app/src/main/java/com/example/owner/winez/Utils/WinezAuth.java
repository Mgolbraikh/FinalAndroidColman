package com.example.owner.winez.Utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.owner.winez.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Ziv on 29/01/2017.
 */

public class WinezAuth {
    private FirebaseAuth mAuth;
    private static WinezAuth _instance;
    private User currentUser;


    private WinezAuth(){
        this.mAuth = FirebaseAuth.getInstance();

        // Listening for auth changes
        this.mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser usr = firebaseAuth.getCurrentUser();
                if (usr != null){

                    // Getting current user from db
                    WinezDB.getInstance().getUpdates(User.class, usr.getUid(), new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User gotCurrentUser = dataSnapshot.getValue(User.class);

                            // Making sure we got the right user
                            if (gotCurrentUser !=null && gotCurrentUser.getUid() == usr.getUid()){
                                currentUser =  gotCurrentUser;

                                // Removing event listener
                                WinezDB.getInstance().removeListener(this);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {

                }
            }
        });

    }

    public static WinezAuth getInstance(){
        if(_instance == null){
            _instance = new WinezAuth();
        }

        return  _instance;
    }

    public User getCurrentUser(){
        return this.currentUser;
    }

    public void registerUser(String email,
                             String password,
                             @NonNull Activity activity,
                             @NonNull OnCompleteListener<AuthResult> onComplete){
        this.mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(activity, onComplete);

    }

    public void addAuthStateListener(FirebaseAuth.AuthStateListener authStateListener){
        this.mAuth.addAuthStateListener(authStateListener);
    }

    public void removeAuthStateListener(FirebaseAuth.AuthStateListener authStateListener){
        this.mAuth.removeAuthStateListener(authStateListener);
    }
}
