package com.example.lms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class editprofile extends AppCompatActivity {
Button save;
FloatingActionButton gallery;
ImageView profile_pic;
Button cancel;
String token;
String url;
TextInputEditText name;
TextInputEditText email;
EditText discription;
String p_name;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        token=getIntent().getStringExtra("Token");
        p_name=getIntent().getStringExtra("pname");
        Log.d("TAG", "onCreate: =="+p_name);
        setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        cancel=findViewById(R.id.update_cancel_btn);
        gallery=findViewById(R.id.editproflebtn);
        profile_pic=findViewById(R.id.profile_picture);
        name=(TextInputEditText)findViewById(R.id.update_name);
        email=(TextInputEditText)findViewById(R.id.update_email);
        discription=(EditText)findViewById(R.id.update_description);
        url="http://192.168.43.30/moodle/webservice/rest/server.php?wstoken="+token+"&wsfunction=core_user_get_users_by_field&field=username&values[0]="+p_name.toLowerCase()+"&moodlewsrestformat=json";
        Log.d("TAG", "onCreate:= "+url);
        name.setEnabled(false);
        name.setCursorVisible(false);
        name.setKeyListener(null);
        email.setEnabled(false);
        email.setCursorVisible(false);
        email.setKeyListener(null);
        discription.setEnabled(false);
        discription.setCursorVisible(false);
        discription.setKeyListener(null);

        name.setFocusableInTouchMode(false);
        email.setFocusableInTouchMode(false);
        discription.setFocusableInTouchMode(false);
       dataIncome();


       /* gallery.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,3);
            }
        });*/

        /*save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
*//*
for invisible button
save.setVisibility(View.GONE);
*//*
            }
        });*/
   cancel.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view)
       {
           finish();
       }

   });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK&&data!=null)
        {
            Uri selectimg=data.getData();
            profile_pic.setImageURI(selectimg);

        }
    }
    public void dataIncome() {


        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            Log.d("checking", "For loop started");

            for (int i = 0; i < response.length(); i++) {
                try
                {

                    JSONObject object = response.getJSONObject(i);

                    name.setText(object.getString("fullname"));
                    email.setText(object.getString("email"));
                    String temp=object.getString("description");
                    Log.d("TAG", "pics: "+temp);

                    discription.setText("Nothing to Show!");
                    Log.d("TAG", "pics: "+object.getString("description"));

                    Picasso.get().load(object.getString("profileimageurlsmall")).placeholder(R.drawable.ic_baseline_person_24).into(profile_pic);
                    Log.d("dat", "dataIncome: "+name);
                    Log.d("dat", "dataIncome: "+email);
                    Log.d("dat", "dataIncome: "+discription);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }




        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        jsonArray.setRetryPolicy(new DefaultRetryPolicy(2000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArray);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}