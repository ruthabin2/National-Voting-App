package com.example.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    TextView su;
    private EditText nid;
    private EditText pass;
    private Button login;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       su = findViewById(R.id.sign);



        nid = findViewById(R.id.na_id);
        pass = findViewById(R.id.psw);
        login = findViewById(R.id.log);


      login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i1 = new Intent(Login.this, dashboard.class);
               startActivity(i1);
           }
       });
    }
   /* public void login(View view) {
        String username = nid.getText().toString();
        String password = pass.getText().toString();

        boolean isAuthenticated = db.checkCredentials(username, password);

        if (isAuthenticated) {
            // Authentication successful

            Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, voter_view_parties.class);
            startActivity(i);
            // Proceed to the next activity or perform any required action
        } else {
            // Authentication failed
            Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, dashboard.class);
            startActivity(i);
        }

*/

    }



