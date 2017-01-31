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

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showRegistration();


        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //final ActionBar actionbar = getActionBar();
//        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//        actionbar.addTab(actionbar.newTab().setText("My winez").setTabListener(new ActionBar.TabListener() {
//            @Override
//            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//                fragmentTransaction.show(registrationFrag);
//            }
//
//            @Override
//            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//                fragmentTransaction.hide(registrationFrag);
//
//            }
//
//            @Override
//            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
//
//            }
//        }));

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
        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() == 0) {
                    buildTabs();
                    getFragmentManager().removeOnBackStackChangedListener(this);
                }
            }

        });
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
                fragmentTransaction.show(allWines);
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                fragmentTransaction.hide(allWines);
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
