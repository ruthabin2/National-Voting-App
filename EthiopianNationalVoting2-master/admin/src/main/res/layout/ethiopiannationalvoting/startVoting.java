package com.example.ethiopiannationalvoting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class startVoting extends AppCompatActivity {
    private DatabaseReference votingTimeRef;
    private DatabaseReference voteButtonEnabledRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_voting);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                votingTimeRef = database.getReference("votingTime");
                voteButtonEnabledRef = database.getReference("voteButtonEnabled");

                Button startButton = findViewById(R.id.startButton);
                startButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startVotingTime();
                    }
                });
            }

            private void startVotingTime() {
                // Set voting time in milliseconds (e.g., 10 minutes)
                long votingTimeMillis = 10 * 60 * 1000;
                long endTimeMillis = System.currentTimeMillis() + votingTimeMillis;

                // Start the voting time by setting the end time in Firebase
                votingTimeRef.setValue(endTimeMillis);

                // Enable the vote button initially
                voteButtonEnabledRef.setValue(true);
            }
        }


