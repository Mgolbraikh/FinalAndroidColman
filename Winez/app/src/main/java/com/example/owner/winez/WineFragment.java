package com.example.owner.winez;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.owner.winez.Model.Model;
import com.example.owner.winez.Model.Wine;
import com.example.owner.winez.Utils.Consts;
import com.example.owner.winez.Utils.WinezDB;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WineFragment extends Fragment {
    Wine wine;
    public WineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle WineIdBundle = getArguments();
        // Inflate the layout for this fragment
        final View view = inflater.inflate (R.layout.fragment_wine, container, false);

        Model.getInstance().getWine(WineIdBundle.getString(Consts.WINE_BUNDLE_ID), new WinezDB.GetOnCompleteResult<Wine>() {
            @Override
            public void onResult(Wine data) {
                wine = data;

                EditText edPrice = (EditText) view.findViewById(R.id.wine_price);
                edPrice.setText(Double.toString(wine.getPrice()));
                EditText edType = (EditText) view.findViewById(R.id.wine_type);
                edType.setText(wine.getType());
                EditText edYear = (EditText) view.findViewById(R.id.wine_vintage_year);
                edYear.setText(wine.getVintage());

            }

            @Override
            public void onCancel(String err) {

            }
        });

        return  view;
    }

}
