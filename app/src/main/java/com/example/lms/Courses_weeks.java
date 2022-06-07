package com.example.lms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Courses_weeks extends AppCompatActivity implements SelectListner {
    RecyclerView recyclerView;
    Myadapter myadapter;
    String specific_cname, specific_cno, token, person_id;
    ShimmerFrameLayout shimmerFrameLayout;
    Integer specific_cid;
    String url;
    String temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_weeks);


        specific_cname = getIntent().getStringExtra("my_ecname");
        specific_cno = getIntent().getStringExtra("my_ecno");
        specific_cid = getIntent().getIntExtra("my_ecid", 0);
        setTitle(specific_cname);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences preferences = getSharedPreferences("TOKEN_ID", MODE_PRIVATE);
        token = preferences.getString("Token", "");
        Log.d("logg", "onCreate: " + specific_cid);

        SharedPreferences sharedPreferences = getSharedPreferences("TOKEN_ID", MODE_PRIVATE);
        if (token.equals("8e6dc0f1606847131b60cc511d36db23")) {
            person_id = getIntent().getStringExtra("p_id");
            Log.d("logg", "onCreate: " + person_id);
        } else {
            person_id = preferences.getString("ID", "");
            Log.d("loggi", "onCreate: " + person_id);

        }
        url = "http://192.168.43.30/moodle/webservice/rest/server.php?wstoken=" + token + "&wsfunction=core_course_get_contents&courseid=" + specific_cid + "&moodlewsrestformat=json&options[0][name]=excludemodules&options[0][value]=false";

        Log.d("mytag", "specific id name and no  =  " + specific_cid + " | " + specific_cno + " | " + specific_cname + " \ntoken=" + token + " and person id =" + person_id);

        recyclerView = (RecyclerView) findViewById(R.id.recview);
        shimmerFrameLayout = (ShimmerFrameLayout) findViewById(R.id.shimmer1);
        shimmerFrameLayout.startShimmer();
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        checkdirectortoken();
        Log.d("dir", "onCreate: " + checkdirectortoken());
        if (checkdirectortoken()) {
            dataque();
        } else {
            dataque();
        }

/*myadapter=new Myadapter(dataque(),this);
recyclerView.setAdapter(myadapter);*/

    }

    public boolean checkdirectortoken() {
        if (token.equals("8e6dc0f1606847131b60cc511d36db23"))
            return true;
        return false;
    }

    public void dataincome_of_teacher_from_course() {
        ArrayList<Model> holder = new ArrayList<>();
        String url_for_get_teacher_courses = "http://192.168.43.30/moodle/webservice/rest/server.php?wstoken=" + token + "&wsfunction=core_enrol_get_enrolled_users&courseid=" + specific_cid + "&moodlewsrestformat=json";

        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url_for_get_teacher_courses, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("checking", "For loop started");
                Log.d("uma", "dataincome_of_teacher_from_course: " + response.length());


                for (int i = 0; i < response.length(); i++) {
                    try {

                        Log.d("uma", "dataincome_of_teacher_from_course: " + response.length());
                        JSONObject object = response.getJSONObject(i);

                        Model m;
                        m = new Model();
                        m.setImgname(R.drawable.ic_baseline_person_pin_24);
                        boolean f = true;
                        String t = object.get("fullname").toString();
                        if (t.contains("BIIT")) {
                            f = false;
                        }

                        m.setWeekedit(object.getString("fullname").toString());
//                    Log.d("teach", "dataincome_of_teacher_from_course: "+object.getString("fullname"));
                        holder.add(m);
//                    Log.d("checking", "Size of array list is " + holder.size());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


                myadapter = new Myadapter(holder, Courses_weeks.this, token, specific_cid);
                recyclerView.setAdapter(myadapter);

                Log.d("checking", "Json Data processed ");


            }
        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        jsonArray.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArray);
    }

    public void dataque() {
        ArrayList<Model> holder = new ArrayList<>();

        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            Log.d("checking", "For loop started");
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            for (int i = 0; i < response.length(); i++) {
                try {

                    JSONObject object = response.getJSONObject(i);

                    Model m;
                    m = new Model();
                    m.setImgname(R.drawable.ic_baseline_folder_24);
                    m.setWeekedit(object.getString("name").toString());

                    temp = object.getString("modules");
                    Log.d("TAG", "dataque: " + object);


                    holder.add(m);
                    Log.d("checking", "Size of array list is " + holder.size());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            myadapter = new Myadapter(holder, Courses_weeks.this, token, specific_cid);
            recyclerView.setAdapter(myadapter);
            Log.d("checking", "Json Data processed ");


        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        jsonArray.setRetryPolicy(new DefaultRetryPolicy(2000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArray);


    }

    @Override
    public void onItemClicked(Model model) {
        Intent intent = new Intent(Courses_weeks.this, rcv_test.class);
        startActivity(intent);
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