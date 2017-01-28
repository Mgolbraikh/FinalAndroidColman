package Utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


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
}
