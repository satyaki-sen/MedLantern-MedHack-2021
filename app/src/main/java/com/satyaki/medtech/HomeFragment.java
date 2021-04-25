package com.satyaki.medtech;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    TextInputEditText textAddress;
    AutoCompleteTextView autoNumber,autoCondition;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String email,name;
    Button btnSendAmb;
    String number;
    ArrayList<String> arrayCondition,arrayNumber;
    ArrayAdapter<String> adapterCondition,adapterNumber;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);
        autoCondition=view.findViewById(R.id.autoEdit_Cond);
        textAddress=view.findViewById(R.id.textEdit_Address);
        btnSendAmb=view.findViewById(R.id.btnSendAmb);
        autoNumber=view.findViewById(R.id.autoEdit_Number);

        arrayNumber=new ArrayList<>();
        arrayCondition=new ArrayList<>();

        adapterNumber=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,arrayNumber);
        adapterNumber.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        adapterCondition=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,arrayCondition);
        adapterCondition.setDropDownViewResource(android.R.layout.simple_spinner_item);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        writeDataList();
        getDataUser();

        btnSendAmb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address=" FA Block, Sector III, Bidhannagar, Kolkata, West Bengal 700097",condition;
                condition=autoCondition.getText().toString();
                number=autoNumber.getText().toString();

                Log.i("Cond",autoCondition.getText().toString());
                Log.i("Num",autoNumber.getText().toString());


                UserEmergency userEmergency=new UserEmergency(email,condition,address,name,number);
                db.collection("Requests").document(mAuth.getCurrentUser().getUid()).set(userEmergency)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(), "Sent to the nearest hospital.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(getActivity(), "Please Try Again", Toast.LENGTH_SHORT).show();
                                    Log.i("Error",task.getException().toString());
                                }
                            }
                        });
            }
        });


        return view;
    }


    public void writeDataList(){

        arrayCondition.add("Emergency");
        arrayCondition.add("Others");

        for(int i=1;i<=15;i++){

            arrayNumber.add(String.valueOf(i));
        }

        autoNumber.setAdapter(adapterNumber);
        adapterNumber.notifyDataSetChanged();

        autoCondition.setAdapter(adapterCondition);
        adapterCondition.notifyDataSetChanged();

    }

    public void getDataUser(){

        Log.i("User",mAuth.getCurrentUser().getUid());
        DocumentReference documentReference=db.collection("Users").document(mAuth.getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    User user=document.toObject(User.class);
                    email=user.getEmail();
                    name=user.getName();

                    Log.i("Name",email);
                    Log.i("User",name);
                }
                else{
                    Log.i("Error",task.getException().toString());
                }

            }
        });

    }

}
