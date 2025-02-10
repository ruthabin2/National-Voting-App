package com.example.ethiopiannationalvoting;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Report extends AppCompatActivity {
    Spinner sp,sp1;
    String value;
    String[] RepName= {"Total Vote"};
    Button b1;
    String[] RepArea={"Gender", "Registered Vote/Register not vote"};
    DatabaseReference newTableRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        sp = findViewById(R.id.spName);
        sp1 = findViewById(R.id.spArea);
        b1=findViewById(R.id.genert);




        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_file, RepName);
        adapter.setDropDownViewResource(R.layout.item_file);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.item_file, RepArea);
        adapter1.setDropDownViewResource(R.layout.item_file);
        sp1.setAdapter(adapter1);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                value = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (value == "Gender") {
            // Initialize the Firebase app
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference votersRef = database.getReference("voters");
            newTableRef = FirebaseDatabase.getInstance().getReference().child("report");

            // Create a ValueEventListener to fetch the voter data
            ValueEventListener voteEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Create counters for females and males
                    int femaleVotes = 0;
                    int maleVotes = 0;

                    // Iterate over the voters
                    for (DataSnapshot voterSnapshot : dataSnapshot.getChildren()) {
                        // Get the voter's gender and vote status
                        String gender = voterSnapshot.child("sex").getValue(String.class);
                        String voteFor = voterSnapshot.child("VoteFor").getValue(String.class);
                        System.out.println(voteFor);



                        // Check if the voter has voted
                        if(voteFor != null) {
                            // Increment the corresponding gender counter
                            if ("Female".equals(gender)) {
                                femaleVotes++;
                            } else if ("Male".equals(gender)) {
                                maleVotes++;
                            }
                        }else {
                            System.out.println("No voter voted");
                        }
                    }

                    // Display the report
                    System.out.println("Female Votes: " + femaleVotes);
                    System.out.println("Male Votes: " + maleVotes);

                  newTableRef.child("FemaleVoter").setValue(femaleVotes);
                    newTableRef.child("MaleVoter").setValue(maleVotes);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle any errors
                    System.err.println("Database error: " + databaseError.getMessage());
                }
            };

            // Attach the ValueEventListener to the voters reference
            votersRef.addListenerForSingleValueEvent(voteEventListener);
        }




        if (value == "Registered Vote/Register not vote") {


                    // Initialize the Firebase app


            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference votersRef = database.getReference("voters");
            DatabaseReference reportRef = database.getReference("report"); // Reference to the report node in the database

            // Create final counters for voters and non-voters
            final int[] voterCount = {0};
            final int[] nonVoterCount = {0};

            // Create a ValueEventListener to fetch the voter data
            ValueEventListener voteEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Iterate over the voters
                    for (DataSnapshot voterSnapshot : dataSnapshot.getChildren()) {
                        // Get the voter's vote status
                        String voteFor = voterSnapshot.child("VoteFor").getValue(String.class);

                        // Check if the voter has voted
                        if (voteFor == null) {
                            // Increment the voter count
                            nonVoterCount[0]++;
                        } else {
                            // Increment the non-voter count

                            voterCount[0]++;
                        }
                    }
                    Toast.makeText(Report.this, ""+voterCount[0]+" "+nonVoterCount[0], Toast.LENGTH_SHORT).show();

                    // Store the counts in the Firebase database
                    reportRef.child("Voters").setValue(voterCount[0]);
                    reportRef.child("NonVoters").setValue(nonVoterCount[0]);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle any errors
                    System.err.println("Database error: " + databaseError.getMessage());
                }
            };

            // Attach the ValueEventListener to the voters reference
            votersRef.addListenerForSingleValueEvent(voteEventListener);
        }
    }


    }
);

        }

    }
