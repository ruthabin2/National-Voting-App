package com.example.voteforethiopia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class dashboard extends AppCompatActivity {
    LinearLayout l1,l2,l3,l4,l5,l6;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Context context = getApplicationContext();
// Get the package name using the context object
        String packageName = context.getPackageName();
        String value1 = getIntent().getStringExtra("value1");
        userId=value1;


                l1= findViewById(R.id.register);
                l2= findViewById(R.id.managee);
                l3= findViewById(R.id.Result);
                l4= findViewById(R.id.report);
                l5= findViewById(R.id.duration);
                l6= findViewById(R.id.admins);
                l1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(dashboard.this,myVote.class);
                        startActivity(i);
                    }
                });

                l2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(dashboard.this,Manage.class);
                        startActivity(i);
                    }
                });

                l3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(dashboard.this,Result.class);
                       startActivity(i);
                    }
                });

                l4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       Intent i = new Intent(dashboard.this,report.class);
                       startActivity(i);
                    }
                });

                l5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     //  Intent i = new Intent(dashboard.this,duration.class);
                      //  startActivity(i);
                    }
                });

                l6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     //   Intent i = new Intent(dashboard.this,admins.class);
                      //  startActivity(i);
                    }
                });
            }



        }

