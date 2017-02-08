package com.example.owner.winez;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.owner.winez.Model.Model;
import com.example.owner.winez.Model.User;
import com.example.owner.winez.Utils.WinezAuth;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    Log.d("Main","create");

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
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");

            super.onBackPressed();
        }
    }

    @Override
    public void onResume(){

        if (getFragmentManager().getBackStackEntryCount() ==0) {
            if (!Model.getInstance().isAuthenticated()) {
                showRegistration();
            } else if (Model.getInstance().getCurrentUser() != null) {
                buildTabs();
            }

            // Setting event for after authentication is complete
            Model.getInstance().setOnAuthChangeListener(new WinezAuth.OnAuthChangeListener() {
                @Override
                public void onLogin(User usr) {
                    buildTabs();
                }

                @Override
                public void onLogout() {
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    showRegistration();
                }
            });
        }
        super.onResume();
        Log.d("Main","resume");
    }



}
