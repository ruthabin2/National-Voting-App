package com.example.voteforethiopia;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class registr_voter extends AppCompatActivity {
    ProgressDialog pd;
    FirebaseDatabase db;
    DatabaseReference dr;
    StorageReference fs;

    EditText nid,fname,psw,repsw;
    String voterId,repsw_holder,sex_holder,city_holder,region_holder,psw_holder;
    private String selectedOption = "NULL"; // Initialize with a default value
    RadioButton s1,s2;
    Spinner sp,sp1;
    Cursor cursor;
    String City_value, Region_value;

    String[] City= {"AA" , "Hawassa", "Dilla","Wolaita Sodo","Nekemtee","Wollo","Bahir Dar","Gonder","Mekele","Axum" };
    String[] Region={"01", "02", "03","04","05"};
    Button Register;
    String selectedGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registr_voter);

        Register = (Button)findViewById(R.id.regis);
        nid = (EditText)findViewById(R.id.nid);

        sp= findViewById(R.id.city);
        sp1= findViewById(R.id.rgn);
        //s1= findViewById(R.id.m);
        //s2= findViewById(R.id.f);
        psw=findViewById(R.id.psw);
        repsw=findViewById(R.id.rpsw);
        /////////////////////////////////////////////////////////


        /////////////////////////////////////////////////////////

        db = FirebaseDatabase.getInstance();
        dr = db.getReference().child("voters");
        fs = FirebaseStorage.getInstance().getReference();
        pd= new ProgressDialog(this);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.item_file,City);
        adapter.setDropDownViewResource(R.layout.item_file);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                City_value = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this, R.layout.item_file,Region);
        adapter1.setDropDownViewResource(R.layout.item_file);
        sp1.setAdapter(adapter1);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Region_value = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






       Register.setOnClickListener(new View.OnClickListener() {
  @Override
 public void onClick(View v) {
 voterId = nid.getText().toString();
  repsw_holder = repsw.getText().toString();
  psw_holder = psw.getText().toString();
  city_holder = City_value;
  region_holder = Region_value;
      RadioGroup radioGroupGender = findViewById(R.id.radioGroupGender);


      int selectedRadioButtonId = radioGroupGender.getCheckedRadioButtonId();
      if (selectedRadioButtonId != -1) {
          RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
          selectedGender = selectedRadioButton.getText().toString();

      }
      sex_holder=selectedGender;

  if (voterId.isEmpty() || sex_holder.isEmpty() || city_holder.isEmpty() || region_holder.isEmpty() || psw_holder.isEmpty() || repsw_holder.isEmpty()) {
 Toast.makeText(registr_voter.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
 } else if (!psw_holder.equals(repsw_holder)) {
 Toast.makeText(registr_voter.this, "password not matching", Toast.LENGTH_SHORT).show();
 } else {
 dr.child("voters").addListenerForSingleValueEvent(new ValueEventListener() {
 @Override
 public void onDataChange(@NonNull DataSnapshot snapshot) {
 if (snapshot.hasChild(voterId)) {
   Toast.makeText(registr_voter.this, "user already registered", Toast.LENGTH_SHORT).show();
  } else {
 dr.child(voterId).child("sex").setValue(sex_holder);
 dr.child(voterId).child("city").setValue(city_holder);
 dr.child(voterId).child("region").setValue(region_holder);
 dr.child(voterId).child("password").setValue(psw_holder);
 dr.child(voterId).child("VoteFor").setValue("null");

 Toast.makeText(registr_voter.this, "user registered", Toast.LENGTH_SHORT).show();
 finish();
                                                       }
                                                   }

 @Override
 public void onCancelled(@NonNull DatabaseError error) {

                                                   }
 });

  }

                                       }
                                   });

    }


}

