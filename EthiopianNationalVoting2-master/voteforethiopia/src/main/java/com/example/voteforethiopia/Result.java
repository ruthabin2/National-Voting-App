
package com.example.voteforethiopia;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("resultcheck");

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Sort the children by key in ascending order
                List<DataSnapshot> children = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    children.add(childSnapshot);
                }
                Collections.sort(children, new Comparator<DataSnapshot>() {
                    @Override
                    public int compare(DataSnapshot snapshot1, DataSnapshot snapshot2) {
                        return snapshot1.getKey().compareTo(snapshot2.getKey());
                    }
                });

                // Create rows and cells in the table layout
                TableLayout tableLayout = findViewById(R.id.tableLayout);

                for (int i = 0; i < children.size(); i += 2) {
                    TableRow row = new TableRow(com.example.voteforethiopia.Result.this);
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(layoutParams);

                    DataSnapshot child1 = children.get(i);
                    DataSnapshot child2 = (i + 1 < children.size()) ? children.get(i + 1) : null;

                    // Create the first cell
                    TextView cell1 = new TextView(com.example.voteforethiopia.Result.this);
                    cell1.setText(child1.getKey() + ": " + child1.getValue());
                    row.addView(cell1);

                    // Create the second cell if available
                    if (child2 != null) {
                        TextView cell2 = new TextView(com.example.voteforethiopia.Result.this);
                        cell2.setText(child2.getKey() + ": " + child2.getValue());
                        row.addView(cell2);
                    }

                    // Add the row to the table layout
                    tableLayout.addView(row);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });


    }
}