package com.example.owner.winez;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.transition.Transition;
import android.util.Log;
import android.view.MenuItem;

import com.example.owner.winez.Model.Comment;
import com.example.owner.winez.Model.Model;
import com.example.owner.winez.Model.User;
import com.example.owner.winez.Utils.WinezAuth;
import com.example.owner.winez.Utils.WinezDB;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting event for after authentication is complete
        WinezAuth.getInstance().setOnUserGetComplete(new WinezAuth.OnUserGetComplete() {
            @Override
            public void onComplete(User user) {
                buildTabs();
            }
        });
        if (!WinezAuth.getInstance().isAuthenticated()) {
            showRegistration();
        }

        // TODO: Sign out and stuff
    }

    @Override
    public void onStart(){
        super.onStart();
    }
    private void showRegistration() {
        RegisterFrag registrationFrag = new RegisterFrag();
        getFragmentManager().beginTransaction()
                .replace(R.id.WinezActivityMainView, registrationFrag)
                .addToBackStack(null)
                .commit();
    }

    private void buildTabs() {
        ActionBar bar =  getActionBar();

        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        final MyWinesListFragment myWines = new MyWinesListFragment();
        final AllWinesFragment allWines = new AllWinesFragment();
        bar.addTab(bar.newTab().setText("My Wines").setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.add(R.id.WinezActivityMainView,myWines);
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.remove(myWines);
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        }));

        bar.addTab(bar.newTab().setText("All Wines").setTabListener(new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.add(R.id.WinezActivityMainView,allWines);
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.remove(allWines);
            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        }));
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
