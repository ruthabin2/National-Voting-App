package com.example.voteforethiopia;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class timing extends AppCompatActivity {
    private DatabaseReference votingTimeRef;
    private DatabaseReference voteButtonEnabledRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                votingTimeRef = database.getReference("votingTime");
                voteButtonEnabledRef = database.getReference("voteButtonEnabled");

                voteButtonEnabledRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean voteButtonEnabled = dataSnapshot.getValue(Boolean.class);
                        // Enable or disable the vote button based on the value in Firebase
                        // You can access and modify the vote button in your user activity layout
                        // For example: voteButton.setEnabled(voteButtonEnabled);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle any errors
                    }
                });

                votingTimeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long endTimeMillis = dataSnapshot.getValue(Long.class);
                        long currentTimeMillis = System.currentTimeMillis();

                        // Check if voting time is up
                        if (currentTimeMillis >= endTimeMillis) {
                            // Disable the vote button by updating the value in Firebase
                            voteButtonEnabledRef.setValue(false);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle any errors
                    }
                });
            }
        }


