package com.example.owner.winez.Utils;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.owner.winez.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

/**
 * Created by Ziv on 29/01/2017.
 */

public class WinezAuth {
    private FirebaseAuth mAuth;
    private static WinezAuth _instance;
    private User currentUser;
    private OnUserGetComplete onUsergetComplete;

    private WinezAuth() {
        this.mAuth = FirebaseAuth.getInstance();
        FirebaseUser fireUser = this.mAuth.getCurrentUser();

        if (fireUser != null) {
            getCurrentUser(fireUser);
        } else {
            // Listening for auth changes
            this.mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    final FirebaseUser usr = firebaseAuth.getCurrentUser();
                    if (usr != null) {
                        getCurrentUser(usr);
                    }
                }
            });
        }
    }

    public static WinezAuth getInstance() {
        if (_instance == null) {
            _instance = new WinezAuth();
        }

        return _instance;
    }

    /**
     * Current connected user
     *
     * @return
     */
    public User getCurrentUser() {
        return this.currentUser;
    }

    public void registerUser(String email,
                             String password,
                             @NonNull Activity activity,
                             @NonNull OnCompleteListener<AuthResult> onComplete) {
        this.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, onComplete);

    }

    public void addAuthStateListener(FirebaseAuth.AuthStateListener authStateListener) {
        this.mAuth.addAuthStateListener(authStateListener);
    }

    public void removeAuthStateListener(FirebaseAuth.AuthStateListener authStateListener) {
        this.mAuth.removeAuthStateListener(authStateListener);
    }

    public boolean isAuthenticated() {
        return this.mAuth.getCurrentUser() != null;
    }

    private void getCurrentUser(final FirebaseUser usr) {
        // Getting current user from db
        WinezDB.getInstance().getSingle(User.class.getSimpleName(), User.class, usr.getUid(), new WinezDB.GetOnCompleteResult<User>(){
            @Override
            public void onResult(User data) {
                // Making sure we got the right user
                if (data != null && data.getUid().compareTo(usr.getUid()) == 0) {
                    currentUser = data;
                    onUsergetComplete.onComplete(currentUser);
                }
            }

            @Override
            public void onCancel(String err) {

            }
        });
    }


    /**
     * Add listener for end of get user
     * @param onUserGetComplete
     */
    public void setOnUserGetComplete(OnUserGetComplete onUserGetComplete){
        this.onUsergetComplete = onUserGetComplete;

        // Checks if listener was already activated
        if(this.currentUser != null){
            onUserGetComplete.onComplete(this.currentUser);
        }
    }
    
    public interface OnUserGetComplete{
        void onComplete(User user);
    }
}
