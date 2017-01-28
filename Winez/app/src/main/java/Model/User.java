package Model;

/**
 * Created by owner on 28-Jan-17.
 */

public class User extends Entity {

    private String name;
    private String email;

    public User(String name, String email, String uid) {
        super(uid);
        this.name = name;
        this.email = email;
    }

    public String getNAme() {
        return name;
    }

    public void setNAme(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
