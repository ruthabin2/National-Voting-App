package com.example.ethiopiannationalvoting;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Add_admin extends AppCompatActivity {

    ProgressDialog pd;
    FirebaseDatabase db;
    DatabaseReference dr;
    StorageReference fs;
    Button add_button;
    private CheckBox cbAdd, cbDelete, cbEdit, cbNotify;

    EditText user_nam, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        user_nam = findViewById(R.id.et_admin_name);
        pass = findViewById(R.id.et_admin_pass);
        cbAdd = findViewById(R.id.checkbox_add);
        cbDelete = findViewById(R.id.checkbox_delete);
        cbEdit = findViewById(R.id.checkbox_edit);
        cbNotify = findViewById(R.id.checkbox_notify);
        List<String> roles = new ArrayList<>();
        if (cbAdd.isChecked()) {
            roles.add("Add");
        }
        if (cbDelete.isChecked()) {
            roles.add("Delete");
        }
        if (cbEdit.isChecked()) {
            roles.add("Edit");
        }
        if (cbNotify.isChecked()) {
            roles.add("Notify");
        }

        add_button = findViewById(R.id.btn_add_admin);

        db = FirebaseDatabase.getInstance();
        dr = db.getReference().child("Admins");
        fs = FirebaseStorage.getInstance().getReference();
        pd= new ProgressDialog(this);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =user_nam.getText().toString().trim();
                String object= pass.getText().toString().trim();


                if(!(name.isEmpty() && object.isEmpty())){
                    pd.setTitle("uploading...");
                    pd.show();


                    DatabaseReference newpost=dr.push();
                    newpost.child("UserName").setValue(name);
                    newpost.child("Roles").setValue(roles);
                    newpost.child("Password").setValue(object).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Add_admin.this, "Admin Created", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                            } else {
                                Toast.makeText(Add_admin.this, "Failed to Create Admin", Toast.LENGTH_SHORT).show();

                            }

                        }

                    });
                }
            }
        });
    }}



