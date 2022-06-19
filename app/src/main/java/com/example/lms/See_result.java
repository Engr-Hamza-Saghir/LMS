package com.example.lms;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


public class See_result extends AppCompatActivity
{
    String assignment_id,course_id;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        assignment_id=getIntent().getStringExtra("assignment_id");
        course_id=getIntent().getStringExtra("course_id");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_result);
        Log.d("assign_id", "Assignment_id= "+assignment_id);
        Log.d("assign_id", "course_id= "+course_id);
    }
}