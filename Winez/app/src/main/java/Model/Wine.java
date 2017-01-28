package Model;

/**
 * Created by owner on 28-Jan-17.
 */

public class Wine {

    public Wine(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    private String name;
    private  String picture;
}
