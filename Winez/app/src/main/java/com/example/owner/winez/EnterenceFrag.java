package com.example.owner.winez;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EnterenceFrag extends Fragment {

    interface Delegate{
        void onCancel();
        void onSave(Text st);
    }

    public EnterenceFrag() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Bundle StudentIdBundle = getArguments();
        View view = inflater.inflate(R.layout.fragment_enterence, container, false);
                return view;
    }


}
