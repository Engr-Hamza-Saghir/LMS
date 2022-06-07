package com.example.lms;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class view_std_assignment extends AppCompatActivity {
TextView view;
Button submission_std;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_std_assignment);
        setTitle("Assignment#1");
Bundle bundle=getIntent().getExtras();
String sunbjectname=bundle.getString("subject_name");


        submission_std=findViewById(R.id.add_subm_std_btn);


        submission_std.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(),assign_quiz_pickup_std.class);
                startActivity(intent);
            }
        });
    }
}