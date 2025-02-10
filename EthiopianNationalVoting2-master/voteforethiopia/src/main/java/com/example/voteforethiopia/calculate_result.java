package com.example.voteforethiopia;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class calculate_result extends Thread {
    @Override
    public void run() {
        // Background logic goes here
        // This code will run in a background thread

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("voters");

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Map to store column values and their counts
                Map<String, Integer> columnCounts = new HashMap<>();
                Map<String, String> columnNameMap = new HashMap<>();
                // Iterate over the dataSnapshot to count occurrences
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String columnValue = childSnapshot.child("VoteFor").getValue(String.class);

                    if (columnCounts.containsKey(columnValue)) {
                        int count = columnCounts.get(columnValue);
                        columnCounts.put(columnValue, count + 1);
                    } else {
                        columnCounts.put(columnValue, 1);
                    }
                    columnNameMap.put(columnValue, childSnapshot.child("PartyName").getValue(String.class)); // Replace "name" with the actual attribute name in your data structure

                }

                // Create a new data structure to store the results
                DatabaseReference newTableRef = FirebaseDatabase.getInstance().getReference().child("resultcheck");

                // Save the results in the new data structure
                for (Map.Entry<String, Integer> entry : columnCounts.entrySet()) {
                    newTableRef.child(entry.getKey()).setValue(entry.getValue());
                   // newTableRef.child("PartName").setValue(columnNameMap);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

}