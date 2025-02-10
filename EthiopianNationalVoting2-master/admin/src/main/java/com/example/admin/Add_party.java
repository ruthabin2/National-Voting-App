package com.example.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;


public class Add_party extends AppCompatActivity {
    private static final int Gallery_Code = 1;
    Uri imageUrl = null;
    ProgressDialog pd;
    FirebaseDatabase db;
    DatabaseReference dr;
    StorageReference fs;
    Button add_button;
    private Spinner spinner,spinner11;
    private List<String> itemsList;
    EditText nam;
    FloatingActionButton picky;
    private String selectedItemValue,value;
    String[] RepName= {"Total Vote" , "Winner Parties ", "Highest candidate's vote" };
    ImageView symbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nam = findViewById(R.id.name);
        spinner = findViewById(R.id.spinner);
        spinner11 = findViewById(R.id.spinner1);
        symbol = findViewById(R.id.photo);
        picky = findViewById(R.id.pick);
        add_button = findViewById(R.id.add_button);

        db = FirebaseDatabase.getInstance();
        dr = db.getReference().child("Candidates");
        fs = FirebaseStorage.getInstance().getReference();
       pd= new ProgressDialog(this);
//////////////////////////////////////////////////////////////////////
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Parties");

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.item_file,RepName);
        adapter.setDropDownViewResource(R.layout.item_file);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               value = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                ArrayAdapter<String> adapter = new ArrayAdapter<>(Add_party.this,
                        android.R.layout.simple_spinner_dropdown_item, itemsList);
                spinner11.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        spinner11.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

/////////////////////////////////////////////////////////////////////////////////////////////////////

        picky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, Gallery_Code);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== Gallery_Code && resultCode==RESULT_OK) {
            imageUrl = data.getData();
            symbol.setImageURI(imageUrl);
        }
            add_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name =nam.getText().toString().trim();
                    String educ= value;
                    String party= selectedItemValue;


                    if(!(name.isEmpty() && educ.isEmpty() && imageUrl!=null && party.isEmpty())){
                        pd.setTitle("uploading...");
                        pd.show();

                        StorageReference filepath=fs.child("imagepost2").child(imageUrl.getLastPathSegment());
                        filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> downloadUrl=taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        String t=task.getResult().toString();
                                        DatabaseReference newpost=dr.push();

                                        newpost.child("Name").setValue(name);
                                        newpost.child("Education").setValue(educ);
                                        newpost.child("Party").setValue(party);
                                        newpost.child("Symbol").setValue(task.getResult().toString());
                                        pd.dismiss();

                                        Intent i= new Intent(Add_party.this, Manage.class);
                                        startActivity(i);
                                    }
                                });
                            }
                        });
                    }
                }
            });


    }
    }

