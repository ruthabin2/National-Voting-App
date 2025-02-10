package com.example.ethiopiannationalvoting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class Add_party extends AppCompatActivity {
    private static final int Gallery_Code = 1;
    Uri imageUrl = null;
    ProgressDialog pd;
    FirebaseDatabase db;
    DatabaseReference dr;
    StorageReference fs;
    Button add_button;

    EditText nam, objective;
    FloatingActionButton picky;
    ImageView symbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nam = findViewById(R.id.name);
        objective = findViewById(R.id.object);
        symbol = findViewById(R.id.photo);
        picky = findViewById(R.id.pick);
        add_button = findViewById(R.id.add_button);

        db = FirebaseDatabase.getInstance();
        dr = db.getReference().child("Parties");
        fs = FirebaseStorage.getInstance().getReference();
       pd= new ProgressDialog(this);

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
                    String object= objective.getText().toString().trim();


                    if(!(name.isEmpty() && object.isEmpty() && imageUrl!=null)){
                        pd.setTitle("uploading...");
                        pd.show();

                        StorageReference filepath=fs.child("imagepost").child(imageUrl.getLastPathSegment());
                        filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> downloadUrl=taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        String t=task.getResult().toString();
                                        DatabaseReference newpost=dr.push();

                                        newpost.child("PartyName").setValue(name);
                                        newpost.child("Objective").setValue(object);
                                        newpost.child("Symbol").setValue(task.getResult().toString());
                                        pd.dismiss();

                                        Intent i= new Intent(Add_party.this,Manage.class);
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

