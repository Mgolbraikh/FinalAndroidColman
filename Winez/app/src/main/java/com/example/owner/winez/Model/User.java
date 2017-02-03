package com.example.owner.winez.Model;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by owner on 28-Jan-17.
 */

public class User extends Entity {

    private String Id;
    private String name;
    private String email;
    private Map<String,String> userWines;

    public User(){
        this.userWines = new HashMap<>();

        // Put timestemp of the entity
        //userWines.put("lastUpdated", ServerValue.TIMESTAMP);
    }
    public User(String name, String email, String uid) {
        super(uid);
        this.name = name;
        this.email = email;
        this.userWines = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    /**
     * Key is wine uid and value is wine title
     * @return
     */
    public Map<String,String> getUserWines() {
        return userWines;
    }


}
