package com.example.admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Manage extends AppCompatActivity {

    // retrive the datas



    RecyclerView rc;
   // PartyAdapter pa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);




        rc = findViewById(R.id.recyclev_id);
        // rc.setHasFixedSize(true);
        rc.setLayoutManager(new LinearLayoutManager(this));

    /*    FirebaseRecyclerOptions<PartyMode> options =
                new FirebaseRecyclerOptions.Builder<PartyMode>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("Parties"), PartyMode.class)
                        .build();

        pa = new PartyAdapter(options,this);
        rc.setAdapter(pa);

    }
    @Override
    protected void onStart() {
        super.onStart();
        pa.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        pa.stopListening();
    }

*/

}}