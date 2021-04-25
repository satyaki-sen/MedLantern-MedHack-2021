package com.satyaki.medtech;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireBaseServices {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    FireBaseServices(){
        mAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
    }

    public boolean createUser(final String name, final String email, final String pass, final String userName){

        final Boolean[] retCreate=null ;

        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    Log.i("Success","Create User");
                    if(!setDataUser(name, email, pass, userName, user)){
                        retCreate[0] =true;
                    }
                }
                else{
                    Log.i("Error","Create User");
                    Log.i("er",task.getException().getMessage());
                    retCreate[0]=false;
                }
            }
        });

        Log.i("Data",retCreate[0].toString());

            return retCreate[0];
    }

    public boolean signInUser(String email,String pass){

        final Boolean[] retSign = {false};
        mAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                retSign[0]=true;

            }
        });


        Log.i("Data",retSign[0].toString());
        return retSign[0];
    }

    public boolean setDataUser(String name,String email,String pass,String userName,FirebaseUser firebaseUser){

        final Boolean[] ret = {false};
        User user=new User(name,userName,pass,email);
        db.collection("Users").document(firebaseUser.getUid()).set(user).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            ret[0] =true;
                        }
                        else{
                            Log.i("er",task.getException().getMessage());
                        }

                    }
                });

        Log.i("Data",ret[0].toString());
        return ret[0];
    }



}
