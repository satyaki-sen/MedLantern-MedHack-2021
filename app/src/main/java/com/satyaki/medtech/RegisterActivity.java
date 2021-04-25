package com.satyaki.medtech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText textEmailReg, textPassReg, textPassCon, textNameReg, textUserReg;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textEmailReg = findViewById(R.id.textEdit_Email_Register);
        textPassReg = findViewById(R.id.textEdit_Pass_Register);
        textPassCon = findViewById(R.id.textEdit_Con_Pass_Register);
        textNameReg = findViewById(R.id.textEdit_Name_Register);
        textUserReg = findViewById(R.id.textEdit_userName_Register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

    }

    public void onAddData(String email, String pass, String userName, String name,FirebaseUser firebaseUser) {

        User user=new User(name,userName,pass,email);
        db.collection("Users").document(firebaseUser.getUid()).set(user).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Error Occurred while Adding Data", Toast.LENGTH_SHORT).show();
                            Log.i("er",task.getException().getMessage());
                        }

                    }
                });

    }


    public void onClickRegister(View view) {

        if (textPassCon.getText().toString().equals(textPassReg.getText().toString())) {
            mAuth.createUserWithEmailAndPassword(textEmailReg.getText().toString(), textPassReg.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        onAddData(textEmailReg.getText().toString(),textPassReg.getText().toString(),textUserReg.getText().toString()
                                ,textNameReg.getText().toString(),user);
                        Log.i("Success", "Create User");


                    } else {
                        Toast.makeText(RegisterActivity.this, "Error occurred while Registering", Toast.LENGTH_SHORT).show();
                        Log.i("Error", "Create User");
                        Log.i("er", task.getException().getMessage());
                    }
                }
            });

        }

    }
}
