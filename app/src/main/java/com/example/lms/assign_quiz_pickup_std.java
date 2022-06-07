package com.example.lms;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class assign_quiz_pickup_std extends AppCompatActivity {

    ImageView pick;
    Button save_pick;
    Button cancel_pick;
    TextView path;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_quiz_pickup_std);
setTitle("File Picker");
        pick=findViewById(R.id.add_file_btn);
        save_pick=findViewById(R.id.submit_task);
        cancel_pick=findViewById(R.id.cancel_task);
        path=findViewById(R.id.filepath);

        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });

        save_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(assign_quiz_pickup_std.this, "Saved", Toast.LENGTH_SHORT).show();

                finish();

            }
        });

        cancel_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }
}