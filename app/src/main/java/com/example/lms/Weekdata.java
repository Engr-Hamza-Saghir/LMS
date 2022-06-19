package com.example.lms;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

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
    String s_cid;
    ImageView assignment_imag;
    boolean flag = false;
    int position;
    Button add_assignment;
    RecyclerView recyclerView;
    Adapter_for_weekcontent adapterForWeekcontent;
    DownloadManager manager;
    TextView assgn_name,assgn_url;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        week_title = getIntent().getStringExtra("name");
        token = getIntent().getStringExtra("token");
        cid = getIntent().getIntExtra("cid", 0);
        position = getIntent().getIntExtra("position", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekdata);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
s_cid=cid.toString();

        recyclerView = findViewById(R.id.my_rcv);
        recyclerView.hasFixedSize();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        cardView=(CardView)findViewById(R.id.assignment_card);
        assgn_name=(TextView)findViewById(R.id.assignment_name);
        assignment_imag=(ImageView)findViewById(R.id.assign_imag);
        assgn_url=(TextView)findViewById(R.id.assignment_url);
        SharedPreferences editor=getSharedPreferences("TOKEN_ID",MODE_PRIVATE);
        username=editor.getString("puser","");
        Log.d("useree", "onCreate: "+cid);




        url = "http://192.168.43.30/moodle/webservice/rest/server.php?wstoken=" + token + "&wsfunction=core_course_get_contents&courseid=" + cid.toString() + "&moodlewsrestformat=json&options[0][name]=excludemodules&options[0][value]=false";
        Log.d("useree", "url = : " + url);
        Log.d("TAG", "onCreate: " + position);

        setTitle(week_title);
        Log.d("abd", "onCreate: "+token);
        Log.d("infini", "onCreate: "+cid);


        dataque();


    }

    public void dataque() {
        ArrayList<Model_for_week_content> holder = new ArrayList<>();

        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            Log.d("chunk", "For loop started" + position);


            try {

                JSONObject object = response.getJSONObject(position);
                JSONArray jsonArray1 = object.getJSONArray("modules");
                for (int j = 0; j <= jsonArray1.length(); j++)
                {
                    Log.d("wekdata", "dataque: " + jsonArray1.length());

                    Model_for_week_content m = new Model_for_week_content();
                    JSONObject jsonObject = jsonArray1.getJSONObject(j);
                    if (jsonObject.getString("name").contains("Assignment"))
                    {
                        assgn_name.setText(jsonObject.getString("name"));
                        assgn_url.setText(jsonObject.getString("url"));
                        if (token.equals("8e6dc0f1606847131b60cc511d36db23"))
                        {
                            cardView.setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view)
                                {
                                    Intent intent=new Intent(getApplicationContext(),See_result.class);
                                    try {
                                        intent.putExtra("assignment_id",jsonObject.getString("instance"));
                                        Log.d("inifini", "onClick: "+cid);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    intent.putExtra("course_id",s_cid);
                                    startActivity(intent);
                                }
                            });

                        }
                        Log.d("point", "dataque: "+jsonObject.getString("name"));
                        Log.d("point", "dataque: "+jsonObject.getString("url"));

                    }

                    Log.d("anjum", "dataque: "+jsonObject);

                    String temprary=jsonObject.getString("name");
                    Log.d("paper", "dataque: "+temprary);
                    String temp = jsonObject.getString("contents");

                    m.setWeek_content_img(jsonObject.getString("modicon"));

                    if (temp != null) {
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