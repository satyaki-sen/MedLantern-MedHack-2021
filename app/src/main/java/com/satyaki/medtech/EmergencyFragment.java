package com.satyaki.medtech;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class EmergencyFragment extends Fragment {

    TextInputEditText editName,editNumber,editCondition,editStatus,editAddress;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String name,mail,cond,address;

    public EmergencyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_emergency, container, false);

        editName=view.findViewById(R.id.textEdit_Name_Emerg);
        editNumber=view.findViewById(R.id.textEdit_Number_Emer);
        editCondition=view.findViewById(R.id.textEdit_Cond_Emer);
        editStatus=view.findViewById(R.id.textEdit_Amb_Status);
        editAddress=view.findViewById(R.id.textEdit_Address_Emer);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        onGetRequests();

        return view;
    }

    public void onGetRequests(){

        DocumentReference documentReference=db.collection("Requests").document(mAuth.getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    DocumentSnapshot document = task.getResult();
                    UserEmergency userEmergency=document.toObject(UserEmergency.class);
                    editName.setText(userEmergency.getName());
                    editAddress.setText(userEmergency.getAddress());
                    editCondition.setText(userEmergency.getCondition());
                    editNumber.setText(userEmergency.getNumber());
                    editStatus.setText("On its Way");
                }
                else{
                    Toast.makeText(getActivity(), "No requests found..", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
