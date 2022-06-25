package com.example.lms;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class See_result extends AppCompatActivity
{
    String assignment_id,course_id,assignment_due_date;
    RadioGroup radioGroup;
    ShimmerFrameLayout shimmerFrameLayout;
    RadioButton radioButton;
    Adapter_submission_assinments adp;
    TextView total_stds;
    RecyclerView rcv_of_submit_stds;
    String token,status;
    int int_assignment_due_date,submit_date;
    String director_url;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Result");
        assignment_id=getIntent().getStringExtra("assignment_id");
        course_id=getIntent().getStringExtra("course_id");
        assignment_due_date=getIntent().getStringExtra("assignment_due_date");
        SharedPreferences sharedPreferences = getSharedPreferences("TOKEN_ID", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("Token", "");
        int_assignment_due_date=Integer.parseInt(assignment_due_date);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_result);
        Log.d("assign_id", "Assignment_id= "+assignment_id);
        Log.d("assign_id", "course_id= "+course_id);
        Log.d("assign_id", "course_id= "+int_assignment_due_date);

        radioGroup = (RadioGroup)findViewById(R.id.radiogroup_for_due_dates);
        rcv_of_submit_stds = (RecyclerView)findViewById(R.id.rcv_for_submissions);
        rcv_of_submit_stds.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        total_stds = (TextView)findViewById(R.id.total_submitted_std);
        shimmerFrameLayout=(ShimmerFrameLayout)findViewById(R.id.simmer_for_allsubmissions);
        shimmerFrameLayout.startShimmer();
        director_url = "http://192.168.43.30/moodle/webservice/rest/server.php?wsfunction=mod_assign_get_submissions&wstoken="+token+"&moodlewsrestformat=json&assignmentids[0]="+assignment_id;
        Log.d("myclass", "data_income_of_inline_stds: "+director_url);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);

                if (radioButton.getId() == R.id.select_inline) {
                    Log.d("myclass", "data_income_of_inline_stds: "+R.id.select_inline);
                    Log.d("myclass", "data_income_of_inline_stds: "+R.id.select_late);
                    shimmerFrameLayout.startShimmer();
                    data_income_of_inline_stds();

                } else if (radioButton.getId() == R.id.select_late){

                    shimmerFrameLayout.startShimmer();
                    data_income_of_overdue_stds();
                }

                // checkedId is the RadioButton selected
            }
        });
    }
    public void data_income_of_inline_stds()
    {
        Log.d("myclass", "data_income_of_inline_stds: started");

        ArrayList<Model_submissions> arrayList = new ArrayList<>();
        JsonObjectRequest jsonArray = new JsonObjectRequest(Request.Method.GET, director_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                rcv_of_submit_stds.setVisibility(View.VISIBLE);
                Log.d("myclass", "data_income_of_inline_stds: gone");

                for (int i = 0; i < 1; i++) {
                    try {
                        JSONArray array = response.getJSONArray("assignments");
                        for (int bc=0;bc<array.length();bc++) {
                            JSONObject id = array.getJSONObject(bc);
                            Log.d("arrays", "objects ="+id.length());

                            JSONArray submissions = id.getJSONArray("submissions");
                            for (int j = 0; j < submissions.length(); j++)
                            {
                                Log.d("rafaiy", "lenght = " + submissions.length());
                                Log.d("rafaiy", "lenght = " + j);
                                JSONObject userid = submissions.getJSONObject(j);
                                String user_id=userid.getString("userid");
                                Log.d("userid", "user id ="+user_id);


                                status=userid.getString("status");
                                Log.d("userid", "user id ="+status);
                                if (status.equals("submitted"))
                                {
                                 JSONArray plugins=userid.getJSONArray("plugins");
                                 for (int k=0;k<1;k++)
                                 {
                                     JSONObject file=plugins.getJSONObject(k);
                                     JSONArray fileareas=file.getJSONArray("fileareas");
                                     for (int fa=0;fa<fileareas.length();fa++)
                                     {
                                         JSONObject file_read=fileareas.getJSONObject(fa);
                                         JSONArray files=file_read.getJSONArray("files");
                                         for (int file_loop=0;file_loop<files.length();file_loop++)
                                         {
                                             JSONObject timemodify=files.getJSONObject(file_loop);
                                             submit_date=Integer.parseInt(timemodify.getString("timemodified"));
                                             if (int_assignment_due_date>submit_date)
                                             {
                                                 String name = "",email="";
                                                 for (int mn=0;mn<Utility.allUsers.size();mn++)
                                                 {
                                                     if (Utility.allUsers.get(mn).getT_id().equals(user_id))
                                                     {
                                                         Model_submissions model_submissions;
                                                         model_submissions=new Model_submissions();
                                                         name=Utility.allUsers.get(mn).getT_name();
                                                         email=Utility.allUsers.get(mn).getT_email();
                                                         Log.d("User__Name", ""+Utility.allUsers.get(mn).getT_id());
                                                         Log.d("User__Name", ""+Utility.allUsers.get(mn).getT_name());
                                                         Log.d("User__Name", ""+Utility.allUsers.get(mn).getT_email());
                                                         model_submissions.setSub_std_name(Utility.allUsers.get(mn).getT_name());
                                                         model_submissions.setSub_std_email(Utility.allUsers.get(mn).getT_email());
                                                         arrayList.add(model_submissions);
                                                         break;
                                                     }

                                                     /*if(Utility.allUsers.get(mn).getT_id()==user_id)
                                                     {
                                                         name=Utility.allUsers.get(mn).getT_name();
                                                         email = Utility.allUsers.get(mn).getT_email();

                                                         break;
                                                     }*/
                                                 }
/*                                                 for (model_director user_biit:Utility.allUsers
                                                 ) {
                                                     Log.d("User__Name", ""+Utility.allUsers.get(Integer.parseInt(user_id)).getT_name());
                                                     Log.d("User__Name", ""+Utility.allUsers.get(Integer.parseInt(user_id)).getT_id());
                                                     Log.d("User__Name", ""+Utility.allUsers.get(Integer.parseInt(user_id)).getT_email());

                                                    *//* if(user_biit.getT_id()==user_id)
                                                     {
                                                         name=user_biit.getT_name();
                                                         email = user_biit.getT_email();

                                                         break;
                                                     }*//*
                                                 }*/

total_stds.setText("Students:"+arrayList.size());
                                                 Log.d("userid_mh", "user id ="+name);
                                                 Log.d("userid_mh", "user id ="+email);
                                                 Log.d("user_id", " you ="+user_id+" submit assignment on time");
                                             }


                                         }
                                     }
                                 }
                                }

                            }

                            Log.d("myclasses", "data_income_of_inline_stds: " + id.getString("assignmentid"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("myclass", "data_income_of_inline_stds: " + response.length());





                }

                adp = new Adapter_submission_assinments(arrayList);
                rcv_of_submit_stds.setAdapter(adp);

                Log.d("checking", "Json Data processed ");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("errore", "onErrorResponse: ."+error.toString());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        jsonArray.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArray);

    }
    public void data_income_of_overdue_stds()
    {
        Log.d("myclass", "data_income_of_inline_stds: started");

        ArrayList<Model_submissions> arrayList = new ArrayList<>();
        JsonObjectRequest jsonArray = new JsonObjectRequest(Request.Method.GET, director_url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                rcv_of_submit_stds.setVisibility(View.VISIBLE);
                Log.d("myclass", "data_income_of_inline_stds: gone");

                for (int i = 0; i < 1; i++) {
                    try {
                        JSONArray array = response.getJSONArray("assignments");
                        for (int bc=0;bc<array.length();bc++) {
                            JSONObject id = array.getJSONObject(bc);
                            Log.d("arrays", "objects ="+id.length());

                            JSONArray submissions = id.getJSONArray("submissions");
                            for (int j = 0; j < submissions.length(); j++)
                            {
                                Log.d("rafaiy", "lenght = " + submissions.length());
                                Log.d("rafaiy", "lenght = " + j);
                                JSONObject userid = submissions.getJSONObject(j);
                                String user_id=userid.getString("userid");
                                Log.d("userid", "user id ="+user_id);


                                status=userid.getString("status");
                                Log.d("userid", "user id ="+status);
                                if (status.equals("submitted"))
                                {
                                    JSONArray plugins=userid.getJSONArray("plugins");
                                    for (int k=0;k<1;k++)
                                    {
                                        JSONObject file=plugins.getJSONObject(k);
                                        JSONArray fileareas=file.getJSONArray("fileareas");
                                        for (int fa=0;fa<fileareas.length();fa++)
                                        {
                                            JSONObject file_read=fileareas.getJSONObject(fa);
                                            JSONArray files=file_read.getJSONArray("files");
                                            for (int file_loop=0;file_loop<files.length();file_loop++)
                                            {
                                                JSONObject timemodify=files.getJSONObject(file_loop);
                                                submit_date=Integer.parseInt(timemodify.getString("timemodified"));
                                                if (int_assignment_due_date<submit_date)
                                                {
                                                    String name = "",email="";
                                                    for (int mn=0;mn<Utility.allUsers.size();mn++)
                                                    {
                                                        if (Utility.allUsers.get(mn).getT_id().equals(user_id))
                                                        {
                                                            Model_submissions model_submissions;
                                                            model_submissions=new Model_submissions();
                                                            name=Utility.allUsers.get(mn).getT_name();
                                                            email=Utility.allUsers.get(mn).getT_email();
                                                            Log.d("User__Name", ""+Utility.allUsers.get(mn).getT_id());
                                                            Log.d("User__Name", ""+Utility.allUsers.get(mn).getT_name());
                                                            Log.d("User__Name", ""+Utility.allUsers.get(mn).getT_email());
                                                            model_submissions.setSub_std_name(Utility.allUsers.get(mn).getT_name());
                                                            model_submissions.setSub_std_email(Utility.allUsers.get(mn).getT_email());
                                                            arrayList.add(model_submissions);

                                                            break;
                                                        }

                                                     /*if(Utility.allUsers.get(mn).getT_id()==user_id)
                                                     {
                                                         name=Utility.allUsers.get(mn).getT_name();
                                                         email = Utility.allUsers.get(mn).getT_email();

                                                         break;
                                                     }*/
                                                    }
/*                                                 for (model_director user_biit:Utility.allUsers
                                                 ) {
                                                     Log.d("User__Name", ""+Utility.allUsers.get(Integer.parseInt(user_id)).getT_name());
                                                     Log.d("User__Name", ""+Utility.allUsers.get(Integer.parseInt(user_id)).getT_id());
                                                     Log.d("User__Name", ""+Utility.allUsers.get(Integer.parseInt(user_id)).getT_email());

                                                    *//* if(user_biit.getT_id()==user_id)
                                                     {
                                                         name=user_biit.getT_name();
                                                         email = user_biit.getT_email();

                                                         break;
                                                     }*//*
                                                 }*/


                                                    Log.d("userid_mh", "user id ="+name);
                                                    Log.d("userid_mh", "user id ="+email);
                                                    Log.d("user_id", " you ="+user_id+" submit assignment on time");
                                                }


                                            }
                                        }
                                    }
                                }

                            }

                            Log.d("myclasses", "data_income_of_inline_stds: " + id.getString("assignmentid"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("myclass", "data_income_of_inline_stds: " + response.length());





                }

                adp = new Adapter_submission_assinments(arrayList);
                rcv_of_submit_stds.setAdapter(adp);

                Log.d("checking", "Json Data processed ");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("errore", "onErrorResponse: ."+error.toString());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        jsonArray.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
    }
}