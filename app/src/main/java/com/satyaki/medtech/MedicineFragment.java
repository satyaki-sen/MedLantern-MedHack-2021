package com.satyaki.medtech;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class MedicineFragment extends Fragment {


    AutoCompleteTextView autoDay,autoMonth;
    TextInputEditText textMorning,textAfternoon,textEvening,textNight;
    Button btnStore;
    ArrayList<String> arrayDay,arrayMonth;
    ArrayAdapter<String> adapterDay,adapterMonth;
    FirebaseAuth mAuth;
    FirebaseFirestore db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_medicine, container, false);
        autoDay=view.findViewById(R.id.autoEdit_Day);
        autoMonth=view.findViewById(R.id.autoEdit_Month);
        textMorning=view.findViewById(R.id.textEdit_Morning);
        textAfternoon=view.findViewById(R.id.textEdit_Afternoon);
        textEvening=view.findViewById(R.id.textEdit_Evening);
        textNight=view.findViewById(R.id.textEdit_Night);
        btnStore=view.findViewById(R.id.btnSaveRecord);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        
        arrayDay=new ArrayList<>();
        arrayMonth=new ArrayList<>();
        
        adapterDay=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,arrayDay);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterMonth=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,arrayMonth);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        onWriteDays();
        
        
        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkNull();
                insertDataMedicine();
            }
        });

        return view;
    }

    public void insertDataMedicine(){

        MedicineClass medicineClass=new MedicineClass(autoDay.getText().toString(),autoMonth.getText().toString(),textMorning.getText().toString(),
                textAfternoon.getText().toString(),textEvening.getText().toString(),textNight.getText().toString());

        db.collection("Medicines").document(mAuth.getCurrentUser().getUid())
                .collection(autoMonth.getText().toString()).document(autoDay.getText().toString()).set(medicineClass)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "Medications Saved Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "Error occurred while Saving.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    public void checkNull(){

        if(textMorning.getText().toString().equals("")){
            textMorning.setText("N/A");
        }
        if(textAfternoon.getText().toString().equals("")){
            textAfternoon.setText("N/A");
        }
        if(textEvening.getText().toString().equals("")){
            textEvening.setText("N/A");
        }
        if(textNight.getText().toString().equals("")){
            textNight.setText("N/A");
        }

    }

    public void onWriteDays(){
        
        for(int i=1;i<=31;i++){
            arrayDay.add(String.valueOf(i));
        }
        
        autoDay.setAdapter(adapterDay);
        adapterDay.notifyDataSetChanged();
        
        arrayMonth.add("January");
        arrayMonth.add("February");
        arrayMonth.add("March");
        arrayMonth.add("April");
        arrayMonth.add("May");
        arrayMonth.add("June");
        arrayMonth.add("July");
        arrayMonth.add("August");
        arrayMonth.add("September");
        arrayMonth.add("October");
        arrayMonth.add("November");
        arrayMonth.add("December");
        
        autoMonth.setAdapter(adapterMonth);
        adapterMonth.notifyDataSetChanged();
    }
    


}