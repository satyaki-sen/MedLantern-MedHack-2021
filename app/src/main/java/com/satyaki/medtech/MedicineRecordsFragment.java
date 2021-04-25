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


public class MedicineRecordsFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    ArrayList<String> arrayDayRec,arrayMonthRec;
    ArrayAdapter<String> adapterDayRec,adapterMonthRec;
    AutoCompleteTextView autoDayRec,autoMonthRec;
    TextInputEditText textMorningRec,textAfternoonRec,textEveningRec,textNightRec;
    Button btnRetrieveRec;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_medicine_records, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        autoDayRec=view.findViewById(R.id.autoEdit_Day_Records);
        autoMonthRec=view.findViewById(R.id.autoEdit_Month_Records);
        textMorningRec=view.findViewById(R.id.textEdit_Morning_Records);
        textAfternoonRec=view.findViewById(R.id.textEdit_Afternoon_Records);
        textEveningRec=view.findViewById(R.id.textEdit_Evening_Records);
        textNightRec=view.findViewById(R.id.textEdit_Night_Records);
        btnRetrieveRec=view.findViewById(R.id.btnRetrieveRecords);

        textMorningRec.setVisibility(View.GONE);
        textAfternoonRec.setVisibility(View.GONE);
        textEveningRec.setVisibility(View.GONE);
        textNightRec.setVisibility(View.GONE);

        arrayDayRec=new ArrayList<>();
        arrayMonthRec=new ArrayList<>();

        adapterDayRec=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,arrayDayRec);
        adapterDayRec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterMonthRec=new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,arrayMonthRec);
        adapterMonthRec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        onWriteAdapter();

        btnRetrieveRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DocumentReference documentReference=db.collection("Medicines").document(mAuth.getCurrentUser().getUid())
                        .collection(autoMonthRec.getText().toString()).document(autoDayRec.getText().toString());
                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if(task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            MedicineClass medicineClass = document.toObject(MedicineClass.class);
                            textMorningRec.setVisibility(View.VISIBLE);
                            textAfternoonRec.setVisibility(View.VISIBLE);
                            textEveningRec.setVisibility(View.VISIBLE);
                            textNightRec.setVisibility(View.VISIBLE);
                            textMorningRec.setText(medicineClass.getMorning());
                            textAfternoonRec.setText(medicineClass.getAfternoon());
                            textEveningRec.setText(medicineClass.getEvening());
                            textNightRec.setText(medicineClass.getNight());

                            Log.i("OKK",medicineClass.getNight());
                        }

                    }
                });



            }
        });


        return view;
    }


    public void onWriteAdapter(){

        for(int i=1;i<=31;i++){
            arrayDayRec.add(String.valueOf(i));
        }

        autoDayRec.setAdapter(adapterDayRec);
        adapterDayRec.notifyDataSetChanged();

        arrayMonthRec.add("January");
        arrayMonthRec.add("February");
        arrayMonthRec.add("March");
        arrayMonthRec.add("April");
        arrayMonthRec.add("May");
        arrayMonthRec.add("June");
        arrayMonthRec.add("July");
        arrayMonthRec.add("August");
        arrayMonthRec.add("September");
        arrayMonthRec.add("October");
        arrayMonthRec.add("November");
        arrayMonthRec.add("December");

        autoMonthRec.setAdapter(adapterMonthRec);
        adapterMonthRec.notifyDataSetChanged();
    }

}

