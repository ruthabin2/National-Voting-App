package com.example.voteforethiopia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    DatabaseReference dr= FirebaseDatabase.getInstance().getReferenceFromUrl("https://ethiopian-national-voting-default-rtdb.firebaseio.com/");

    public EditText id;
    EditText pass;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    Button b;
        TextView b1;
    public String idd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        id=findViewById(R.id.na_id);
       // idd= id.getText().toString();
        pass=findViewById(R.id.psw);
        b=findViewById(R.id.log);
        b1=findViewById(R.id.sign);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,registr_voter.class);
                startActivity(intent);
            }
        });
        ////////////////////////////////////////////////////////////////////////////////


// Assuming you have a valid context object, such as in an Activity or Application class
        Context context = getApplicationContext();

// Get the package name using the context object
        String packageName = context.getPackageName();


        ///////////////////////////////////////////////////////////////////////////////

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        mFirestore = FirebaseFirestore.getInstance();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idd= id.getText().toString();
                String password=pass.getText().toString();
                ////////////////////////////////////////////
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("EditTextValue", idd);
                editor.apply();
                ///////////////////////////////////////////

                if (idd.isEmpty() || password.isEmpty()){
                    Toast.makeText(Login.this, "Please enter every detail! ", Toast.LENGTH_SHORT).show();
                }
                else{
                    dr.child("voters").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(idd)){
                                String getpass= snapshot.child(idd).child("password").getValue(String.class);

                                if (getpass.equals(password)){
                                    Toast.makeText(Login.this, "Succesfully login "+idd, Toast.LENGTH_SHORT).show();


                                    //startActivity(new Intent(login.this,check.class));
                                    //finish();

                                    Intent intent = new Intent(Login.this,dashboard.class);

                                    // Put the EditText values as extras in the Intent
                                    intent.putExtra("value1", idd);


                                    // Start the target activity
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(Login.this, "You have entered wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(Login.this, "You have entered wrong National ID", Toast.LENGTH_SHORT).show();
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