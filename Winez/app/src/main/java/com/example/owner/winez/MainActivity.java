package com.example.owner.winez;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EnterenceFrag WinezMainFrag= new EnterenceFrag();

        FragmentTransaction ftr = getFragmentManager().beginTransaction();
        ftr.replace(R.id.WinezActivityMainView, WinezMainFrag);
        ftr.show(WinezMainFrag);
        ftr.commit();

    }
}
