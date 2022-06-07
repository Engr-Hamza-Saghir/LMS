package com.example.lms;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Directore_Screen extends AppCompatActivity implements SelectListner {
    RecyclerView recyclerView;

    Adapter_for_director myadapter;
    Adapter_for_director myadapter1;
    String specific_cname, specific_cno, token, person_id;
ShimmerFrameLayout shimmerFrameLayout;
    Integer specific_cid;
    String url;
    String temp;
    String puser_id;
    TextView t_teacher;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directore_screen);
getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        specific_cname = getIntent().getStringExtra("my_ecname");
        specific_cno = getIntent().getStringExtra("my_ecno");
        token = getIntent().getStringExtra("token");
        specific_cid = getIntent().getIntExtra("my_ecid", 0);
        setTitle(specific_cname);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_for_total_teachers);
        t_teacher=(TextView)findViewById(R.id.total_users);
        radioGroup=(RadioGroup)findViewById(R.id.radiogroup_for_members);
        shimmerFrameLayout=(ShimmerFrameLayout)findViewById(R.id.simmer_all_couursesdata);
        shimmerFrameLayout.startShimmer();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                radioButton=(RadioButton)findViewById(i);
                Log.d("umer", "onCheckedChanged: "+radioButton.getId());
                if (radioButton.getId() == R.id.select_teacher) {
                    dataincome_of_teacher_from_course();

                } else {
                    dataincome_of_teacher_from_course1();
                }

            }
        });

        recyclerView.hasFixedSize();
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Log.d("checkings", "For loop started"+token);

    }

    @Override
    public void onItemClicked(Model model) {

    }

    public void dataincome_of_teacher_from_course() {
        ArrayList<model_director> holder = new ArrayList<>();
        ArrayList<model_director> holder1 = new ArrayList<>();
        String url_for_get_teacher_courses = "http://192.168.43.30/moodle/webservice/rest/server.php?wstoken=" + token + "&wsfunction=core_enrol_get_enrolled_users&courseid=" + specific_cid + "&moodlewsrestformat=json";

        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url_for_get_teacher_courses, null, response -> {
            Log.d("checkings", "For loop started"+token);
                Log.d("uma", "dataincome_of_teacher_from_course: " + response.length());
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            for (int i = 0; i < response.length(); i++) {
                try {
                    Log.d("uma", "dataincome_of_teacher_from_course: " + response.length());
                    JSONObject object = response.getJSONObject(i);

                    model_director m;
                    m = new model_director();
                    boolean f = true;
                    JSONArray array = object.getJSONArray("roles");
                    for (int k = 0; k < array.length(); k++) {
                        JSONObject jsonObject = array.getJSONObject(k);
                        temp = jsonObject.getString("shortname");
                        if (temp.equals("editingteacher")) {
                            user_id=object.getString("id");
                            m.setT_img(object.getString("profileimageurlsmall"));
                            m.setT_name(object.getString("fullname").toString());
                            m.setT_email(object.getString("email"));
                            m.setT_id(user_id);
                            holder.add(m);
                            Log.d("axz", "dataincome_of_teacher_from_course: "+m.getT_id());
                            t_teacher.setText("Teachers :"+holder.size());


                        }
                    }
                    String t = object.get("fullname").toString();
//                    Log.d("teach", "dataincome_of_teacher_from_course: "+object.getString("fullname"));
//                    Log.d("checking", "Size of array list is " + holder.size());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            myadapter = new Adapter_for_director(holder, Directore_Screen.this, token,user_id);
            recyclerView.setAdapter(myadapter);

            Log.d("checking", "Json Data processed ");


        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        jsonArray.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArray);
    }
    public void dataincome_of_teacher_from_course1() {
        ArrayList<model_director> holder = new ArrayList<>();
        ArrayList<model_director> holder1 = new ArrayList<>();
        String url_for_get_teacher_courses = "http://192.168.43.30/moodle/webservice/rest/server.php?wstoken=" + token + "&wsfunction=core_enrol_get_enrolled_users&courseid=" + specific_cid + "&moodlewsrestformat=json";

        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url_for_get_teacher_courses, null, response -> {
            Log.d("checking", "For loop started");
            Log.d("uma", "dataincome_of_teacher_from_course: " + response.length());


            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            for (int i = 0; i < response.length(); i++) {
                try {
                    Log.d("uma", "dataincome_of_teacher_from_course: " + response.length());
                    JSONObject object = response.getJSONObject(i);

                    model_director m;
                    m = new model_director();
                    boolean f = true;
                    JSONArray array = object.getJSONArray("roles");
                    for (int k = 0; k < array.length(); k++) {
                        JSONObject jsonObject = array.getJSONObject(k);
                        temp = jsonObject.getString("shortname");
                        if (temp.equals("student")) {
                             puser_id=object.getString("id");
                            Log.d("axz", "dataincome_of_teacher_from_course: "+puser_id);
                            m.setT_img(object.getString("profileimageurlsmall"));
                            m.setT_name(object.getString("fullname").toString());
                            m.setT_email(object.getString("email"));
                            m.setT_id(puser_id);
                            holder.add(m);
                            Log.d("axz", "dataincome_of_teacher_from_course: "+m.getT_id());
                            t_teacher.setText("Students:"+holder.size());


                        }
                    }
                    String t = object.get("fullname").toString();
//                    Log.d("teach", "dataincome_of_teacher_from_course: "+object.getString("fullname"));
//                    Log.d("checking", "Size of array list is " + holder.size());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            myadapter = new Adapter_for_director(holder, Directore_Screen.this, token,puser_id);
            recyclerView.setAdapter(myadapter);

            Log.d("checking", "Json Data processed ");


        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        jsonArray.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
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