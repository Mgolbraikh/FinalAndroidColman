package com.example.owner.winez;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;

import com.example.owner.winez.Model.Model;
import com.example.owner.winez.Model.User;
import com.example.owner.winez.Utils.WinezAuth;


public class MainActivity extends Activity {
    private static final int REQUEST_WRITE_STORAGE = 112;

    // TODO : check if there is internet connection via google client - if there isnt check local db for user/.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean hasPermission = (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);

        // Requesting permission if needed
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }

        if (!Model.getInstance().isAuthenticated()) {
            showRegistration();
        } else if (Model.getInstance().getCurrentUser() != null) {
            buildTabs();
        }

        // Setting event for after authentication is complete
        Model.getInstance().setOnAuthChangeListener(new WinezAuth.OnAuthChangeListener() {
            @Override
            public void onLogin(User usr) {
                //Save remote user to local db
                Model.getInstance().saveCurrentUserLocal(usr);
                buildTabs();
            }

            @Override
            public void onLogout() {
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                showRegistration();
            }
        });

    }

    private void showRegistration() {
        RegisterFrag registrationFrag = new RegisterFrag();
        getFragmentManager().beginTransaction().add(R.id.WinezActivityMainView, registrationFrag)
                .addToBackStack(null)
                .commit();
    }

    private void buildTabs() {
        FragmentManager fm = getFragmentManager();
            TabControlFragment tabs = new TabControlFragment();
            fm.beginTransaction().add(R.id.WinezActivityMainView, tabs)
                    .addToBackStack(null)
                    .commit();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:{
                onBackPressed();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0 &&
                (fm.findFragmentById(R.id.WinezActivityMainView) instanceof RegisterFrag ||
                 fm.findFragmentById(R.id.WinezActivityMainView) instanceof TabControlFragment)) {
            getFragmentManager().popBackStack();
            this.finish();
        } else if (fm.getBackStackEntryCount() > 0) {
                Log.i("MainActivity", "popping backstack");
                fm.popBackStack();
        }else{
            Log.i("MainActivity", "nothing on backstack, calling super");

            super.onBackPressed();
        }
    }

    // TODO : Local DB to do
    @Override
    public void onResume(){
        super.onResume();
        Log.d("Main","resume");

    }



}
