package com.example.lms;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class show_user_record extends AppCompatActivity {

    RecyclerView rcv_for_enroll_courses;
    String url;
    String myid, token;
    Integer cids;
    ShimmerFrameLayout shimmerFrameLayout;
    String string_person_name, string_person_email, string_person_img, username;
    adapter_for_ecourse adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = getIntent().getStringExtra("Token");
        myid = getIntent().getStringExtra("ID");
        string_person_name = getIntent().getStringExtra("pname");
        username = getIntent().getStringExtra("puser");
        string_person_email = getIntent().getStringExtra("pemai");
        string_person_img = getIntent().getStringExtra("pimg");

        setContentView(R.layout.activity_show_user_record);
        setTitle("Courses");
        url = "http://192.168.43.30/moodle/webservice/rest/server.php?wsfunction=core_enrol_get_users_courses&userid=" + myid + "&wstoken=" + token + "&moodlewsrestformat=json";
        rcv_for_enroll_courses = (RecyclerView) findViewById(R.id.recv_for_userrecord);
        rcv_for_enroll_courses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        shimmerFrameLayout=(ShimmerFrameLayout) findViewById(R.id.simmer_specific_courses);
        shimmerFrameLayout.startShimmer();
        dataIncome();
    }

    public void dataIncome() {
        ArrayList<model_for_ecourse> arrayList = new ArrayList<>();


        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            Log.d("checking", "For loop started");
shimmerFrameLayout.stopShimmer();
shimmerFrameLayout.setVisibility(View.GONE);
rcv_for_enroll_courses.setVisibility(View.VISIBLE);
            for (int i = 0; i < response.length(); i++) {
                try {

                    JSONObject object = response.getJSONObject(i);
                    model_for_ecourse m;
                    m = new model_for_ecourse();
                    cids = Integer.valueOf(object.getString("id"));
                    Log.d("uff", "dataIncome: " + cids);
                    m.setCid(cids);
                    m.setEc_img(R.drawable.sunject);
                    m.setEc_no(object.getString("shortname").toString());
                    m.setEc_name(object.getString("displayname").toString());
                    arrayList.add(m);
                    Log.d("ac", "IDS = " + cids);


                    Log.d("checking", "Courses = " + arrayList.get(i).getEc_name());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            adp = new adapter_for_ecourse(arrayList, show_user_record.this, token, myid);
            rcv_for_enroll_courses.setAdapter(adp);
            Log.d("checking", "Json Data processed ");


        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        jsonArray.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArray);

    }

}