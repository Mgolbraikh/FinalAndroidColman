package com.example.owner.winez;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import com.example.owner.winez.Model.User;

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
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),
                                                     username.getText().toString())
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Registration failed", Toast.LENGTH_SHORT).show();
                                }else{

                                    User usrToAdd = new User(username.getText().toString(), email.getText().toString(), task.getResult().getUser().getUid());
                                    usrToAdd.save().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Registration failed", Toast.LENGTH_SHORT).show();
                                            } else{
                                                Toast.makeText(getActivity(), "Successful registration!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });

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
