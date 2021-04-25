package com.satyaki.medtech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelStore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView textRegister;
    TextInputEditText editEmail,editPass;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textRegister=findViewById(R.id.textRegis_Acc);
        editEmail=findViewById(R.id.textEdit_Email_Login);
        editPass=findViewById(R.id.textEdit_Pass_Login);
        mAuth=FirebaseAuth.getInstance();


    }

    public void onNewAccount(View view){

        Intent intent_Acc=new Intent(this,RegisterActivity.class);
        startActivity(intent_Acc);
    }

    public void onClickLogin(View view){

        mAuth.signInWithEmailAndPassword(editEmail.getText().toString(),editPass.getText().toString()).
               addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {

                       if(task.isSuccessful()){
                           Toast.makeText(MainActivity.this, "User SignIn Successful", Toast.LENGTH_SHORT).show();
                           Intent intent_Home=new Intent(MainActivity.this,HomeActivity.class);
                           startActivity(intent_Home);
                       }
                       else{
                           Toast.makeText(MainActivity.this, "Error occurred while Login", Toast.LENGTH_SHORT).show();
                       }
                   }
               });

    }
}
