package com.example.owner.winez;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.owner.winez.Utils.WinezAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import com.example.owner.winez.Model.User;

public class RegisterFrag extends Fragment {

    public RegisterFrag() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_register, container, false);
        final EditText username = (EditText)view.findViewById(R.id.reg_username);
        final EditText email = (EditText)view.findViewById(R.id.reg_email);
        final EditText password = (EditText)view.findViewById(R.id.reg_password);
        final Button enter = (Button)view.findViewById(R.id.reg_enter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Disabling button
                enter.setEnabled(false);
                WinezAuth.getInstance().
                        registerUser(email.getText().toString(),
                                     password.getText().toString(),
                                     getActivity(),
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                // Checking if add was successful
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getActivity(),
                                                   "Registration failed",
                                                   Toast.LENGTH_SHORT).show();
                                    enter.setEnabled(true);
                                }else{

                                    // Adding user to db
                                    User usrToAdd =
                                            new User(username.getText().toString(),
                                                     email.getText().toString(),
                                                    task.getResult().getUser().getUid());
                                    usrToAdd.save().addOnCompleteListener(getActivity(),
                                                            new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getActivity(),
                                                    "Successful registration!",
                                                     Toast.LENGTH_SHORT).show();

                                            getActivity().getFragmentManager().popBackStack();
                                        }
                                    });
                                }
                            }
                        });

            }
        });
        return view;
    }

}
