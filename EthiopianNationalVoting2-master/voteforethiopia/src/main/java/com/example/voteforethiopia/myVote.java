package com.example.voteforethiopia;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class myVote extends AppCompatActivity {
    private Spinner spinner;
    private List<String> itemsList;
    private String selectedItemValue;
    String userId;
    ImageView vot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vote);

         vot = findViewById(R.id.votee);
        spinner = findViewById(R.id.spinner);
        itemsList = new ArrayList<>();

//////////// Initialize Firebase Realtime Database////////////////////////
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Parties");
/////////////////////////////////////////////////////////////////////////////////

        Context context = getApplicationContext();
        String packageName = context.getPackageName();
        // Access the stored value from shared preferences
        SharedPreferences preferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String editTextValue = preferences.getString("EditTextValue", "");
        userId=editTextValue;

///////////////////////////////////////////////////////////////////////////////////////////////////



        // Retrieve items from the Firebase database
        Query query = FirebaseDatabase.getInstance().getReference("Parties").orderByChild("PartyName");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Get the item name from the snapshot
                    String itemName = snapshot.child("PartyName").getValue(String.class);

                    // Add the item name to the list
                    itemsList.add(itemName);
                }

                // Set up the spinner adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(myVote.this,
                        android.R.layout.simple_spinner_dropdown_item, itemsList);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item value
                selectedItemValue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected
            }
        });

        vot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Store the selected value in the Firebase Realtime Database

                //String userId = "id2"; // Replace with the actual user ID
                String newValue =  selectedItemValue; // The new value to be added

                // Get a reference to the specific location in the database where the user's table value is stored
                DatabaseReference userTableRef = FirebaseDatabase.getInstance().getReference().child("voters").child(userId).child("VoteFor");

                // Retrieve the existing table value for the user from the database

                userTableRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            //String existingTableValue = dataSnapshot.getValue(String.class);

                            // Modify the existing table value to include the new value
                            // String updatedTableValue = existingTableValue + ", " + newValue;

                            // Store the updated table value back to the database
                            userTableRef.setValue(newValue)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Value successfully stored in the database
                                            Toast.makeText(myVote.this, "You have sucessfully vote", Toast.LENGTH_SHORT).show();

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Handle the error case
                                            Toast.makeText(myVote.this, "Failed to vote", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }else{
                            Toast.makeText(myVote.this, "Failed to acess database "+userId, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle any errors that occurred while retrieving the existing table value
                        Toast.makeText(myVote.this, "Failed to retrieve existing table value", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}
