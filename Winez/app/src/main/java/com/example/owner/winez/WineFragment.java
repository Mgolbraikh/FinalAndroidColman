package com.example.owner.winez;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.owner.winez.Utils.ApiClasses.WineApiClass;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WineFragment extends Fragment {

    public WineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wine, container, false);
    }

}
