package com.example.owner.winez;

import android.app.Activity;
import android.app.FragmentManager;
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

        // Setting event for after authentication is complete
        Model.getInstance().setOnAuthChangeListener(new WinezAuth.OnAuthChangeListener() {
            @Override
            public void onLogin(User usr) {
                buildTabs();
            }

            @Override
            public void onLogout() {
                getFragmentManager().popBackStack();
                showRegistration();
            }
        });
        if (!Model.getInstance().isAuthenticated()) {
            showRegistration();
        }
    }

    private void showRegistration() {
        RegisterFrag registrationFrag = new RegisterFrag();
        getFragmentManager().beginTransaction()
                .replace(R.id.WinezActivityMainView, registrationFrag)
                .addToBackStack(null)
                .commit();
    }

    private void buildTabs() {
        TabControlFragment tabs = new TabControlFragment();
        getFragmentManager().beginTransaction().replace(R.id.WinezActivityMainView, tabs)
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
                fm.findFragmentById(R.id.WinezActivityMainView) instanceof RegisterFrag) {
            this.finish();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }
}
