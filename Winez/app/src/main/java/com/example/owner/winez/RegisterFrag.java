package com.example.owner.winez;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class RegisterFrag extends Fragment {

    private FirebaseAuth mAuth;
    interface Delegate{
        void onCancel();
        void onSave(Text st);
    }

    public RegisterFrag() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Bundle StudentIdBundle = getArguments();
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        final EditText username = (EditText)view.findViewById(R.id.reg_username);
        final EditText email = (EditText)view.findViewById(R.id.reg_email);
        EditText password = (EditText)view.findViewById(R.id.reg_password);
        Button enter = (Button)view.findViewById(R.id.reg_enter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  mAuth.createUserWithEmailAndPassword(email.getText().toString(), username.getText().toString()).add

            }
        });
        return view;
    }


    /*
    * This updates only when the fragment starts
    * */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.uppermenu,menu);
        getActivity().setTitle("Winez");
        menu.findItem(R.id.AddStudentButton).setVisible(true);
        menu.findItem(R.id.EditStudentButton).setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
