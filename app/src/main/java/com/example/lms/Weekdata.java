package com.example.lms;

import android.app.DownloadManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Weekdata extends AppCompatActivity {

    String week_title;
    String url;
    String username;
    String token;
    Integer cid;
    boolean flag = false;
    int position;
    Button add_assignment;
    RecyclerView recyclerView;
    Adapter_for_weekcontent adapterForWeekcontent;
    DownloadManager manager;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekdata);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        recyclerView = findViewById(R.id.my_rcv);
        recyclerView.hasFixedSize();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        cardView=(CardView)findViewById(R.id.assignment_card);
        SharedPreferences editor=getSharedPreferences("TOKEN_ID",MODE_PRIVATE);
        username=editor.getString("puser","");
        Log.d("user", "onCreate: "+username);



        week_title = getIntent().getStringExtra("name");
        token = getIntent().getStringExtra("token");
        cid = getIntent().getIntExtra("cid", 0);
        position = getIntent().getIntExtra("position", 0);
        url = "http://192.168.43.30/moodle/webservice/rest/server.php?wstoken=" + token + "&wsfunction=core_course_get_contents&courseid=" + cid.toString() + "&moodlewsrestformat=json&options[0][name]=excludemodules&options[0][value]=false";
        Log.d("TAG", "url = : " + url);
        Log.d("TAG", "onCreate: " + position);

        setTitle(week_title);
        Log.d("abd", "onCreate: "+token);

        cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });

        dataque();


    }

    public void dataque() {
        ArrayList<Model_for_week_content> holder = new ArrayList<>();

        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            Log.d("chunk", "For loop started" + position);


            try {

                JSONObject object = response.getJSONObject(position);
                Log.d("wekdata", "dataque: " + object.getString("modules"));
                JSONArray jsonArray1 = object.getJSONArray("modules");
                for (int j = 0; j < jsonArray1.length(); j++) {
                    Model_for_week_content m = new Model_for_week_content();
                    JSONObject jsonObject = jsonArray1.getJSONObject(j);

                    Log.d("zzz", "if started " + jsonObject.getString("contents"));
                    String temp = jsonObject.getString("contents");
                    Log.d("chkif", "if started:" + temp);

                    m.setWeek_content_img(jsonObject.getString("modicon"));
                    Log.d("chkif", "if started:" + jsonObject.getString("modicon"));
                    if (temp != null) {
                        Log.d("chkif", "dataque: " + jsonObject.getString("contents"));
                        JSONArray jsonArray2 = jsonObject.getJSONArray("contents");
                        for (int k = 0; k < jsonArray2.length(); k++) {
                            JSONObject innerobject = jsonArray2.getJSONObject(k);
                            Log.d("flag", "dataque: " + flag);
                            String t = innerobject.getString("type");

                            if (t.equals("file"))
                            {
                                m.setWeek_content(jsonObject.getString("url"));

                                m.setF(false);
                                        Log.d("flag", "dataque: " + flag);
                            }
                            else
                            {
                                m.setWeek_content(innerobject.getString("fileurl"));

                                m.setF(true);
                            }

                        }
                    } else {
                        m.setWeek_content_name(jsonObject.getString("name"));
                        m.setWeek_content(jsonObject.getString("url"));
                    }
                    m.setWeek_content_name(jsonObject.getString("name"));

                    Log.d("success", "dataque: " + jsonObject.getString("name"));
                    holder.add(m);
                    Log.d("fg", "dataque: " + m.isF() + "addapter called");
                    adapterForWeekcontent = new Adapter_for_weekcontent(holder, Weekdata.this, flag);
                    recyclerView.setAdapter(adapterForWeekcontent);
                    flag = false;
                    Log.d("weekdata", "weekdata : " + jsonObject.getString("url") + "  | " + R.drawable.youtube);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("checking", "Json Data processed ");
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