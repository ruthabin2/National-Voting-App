package com.example.voteforethiopia;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.database.*;

public class report extends Activity {
    private TextView textViewFemaleVotes;
    private TextView textViewMaleVotes;
    private TextView reg_Votes;
    private TextView nonreg_Votes;
    private DatabaseReference reportRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        textViewFemaleVotes = findViewById(R.id.textViewFemaleVotes);
        textViewMaleVotes = findViewById(R.id.textViewMaleVotes);
       reg_Votes = findViewById(R.id.regi_Votes);
        nonreg_Votes = findViewById(R.id.nonregi_Votes);

        // Initialize the Firebase app
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        reportRef = database.getReference("report");

        // Add a ValueEventListener to fetch the report data
        ValueEventListener reportEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve the voter count and update the TextViews
                   Long female_voters = dataSnapshot.child("FemaleVoter").getValue(Long.class);
                  Long male_voters = dataSnapshot.child("MaleVoter").getValue(Long.class);
                   Long voters = dataSnapshot.child("Voters").getValue(Long.class);
                   Long nonVoters = dataSnapshot.child("NonVoters").getValue(Long.class);

                    textViewFemaleVotes.setText("Female Votes: " + female_voters);
                    textViewMaleVotes.setText("Male Votes: " + male_voters);
                    reg_Votes.setText("Registred Votes: " + voters);
                    nonreg_Votes.setText("Registered but not voted: " + nonVoters);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
                System.err.println("Database error: " + databaseError.getMessage());
            }
        };

        // Attach the ValueEventListener to the report reference
        reportRef.addListenerForSingleValueEvent(reportEventListener);
    }
}
