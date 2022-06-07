package com.example.lms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class main_afterlogin extends AppCompatActivity {
    RecyclerView rcv_for_enroll_courses;
    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    String url;
    String myid, token;
    Integer cids;
    TextView person_name;
    TextView person_gmail;
    ImageView person_img;
    ShimmerFrameLayout shimmerFrameLayout;
    String string_person_name, string_person_email, string_person_img,username;
    adapter_for_ecourse adp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        token = getIntent().getStringExtra("Token");
        myid = getIntent().getStringExtra("ID");
        string_person_name = getIntent().getStringExtra("pname");
        username = getIntent().getStringExtra("puser");
        string_person_email = getIntent().getStringExtra("pemai");
        string_person_img = getIntent().getStringExtra("pimg");


        Log.d("datashow", "datashow = " + token);
        Log.d("datashow", "datashow = " + username);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_afterlogin);


        url = "http://192.168.43.30/moodle/webservice/rest/server.php?wsfunction=core_enrol_get_users_courses&userid=" + myid + "&wstoken=" + token + "&moodlewsrestformat=json";

        nav = (NavigationView) findViewById(R.id.nav_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        shimmerFrameLayout=(ShimmerFrameLayout)findViewById(R.id.shimmi);
        shimmerFrameLayout.startShimmer();

        View view = nav.getHeaderView(0);


        person_name = (TextView) view.findViewById(R.id.student_name);
        person_gmail = (TextView) view.findViewById(R.id.student_email);
        person_img = (ImageView) view.findViewById(R.id.student_image);
        Log.d("Info", "Info =  " + string_person_name);
        Log.d("Info", "Info =  " + string_person_email);
        Log.d("Info", "Info =  " + string_person_img);
        person_name.setText(string_person_name.toString());
        person_gmail.setText(string_person_email.toString());
        Picasso.get().load(string_person_img).into(person_img);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rcv_for_enroll_courses = (RecyclerView) findViewById(R.id.recyclefor_enroll);
        rcv_for_enroll_courses.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        checkdirectortoken();
        Log.d("dir", "onCreate: " + checkdirectortoken());
        if (checkdirectortoken()) {
dataIncome();
        } else {
            dataIncome();

        }


        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dashboard:
                        Toast.makeText(getApplicationContext(), "DashBoard Open", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.mycourse:
                        Toast.makeText(getApplicationContext(), " MY courses!", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.profile:
                        Intent intent = new Intent(getApplicationContext(), editprofile.class);
                        intent.putExtra("Token",token);
                        intent.putExtra("pname",username);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.logout:
                        exitapp();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });
        SharedPreferences.Editor editor = getSharedPreferences("TOKEN_ID", MODE_PRIVATE).edit();


    }


    public boolean checkdirectortoken() {
        if (token.equals("8e6dc0f1606847131b60cc511d36db23"))
            return true;
        return false;
    }

    public void dataincome_of_director() {
        ArrayList<model_for_ecourse> arrayList = new ArrayList<>();

        String director_url = "http://192.168.43.30/moodle/webservice/rest/server.php?wstoken=" + token + "&wsfunction=core_course_get_courses&moodlewsrestformat=json";

        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, director_url, null, response -> {

            for (int i = 0; i < response.length(); i++) {
                try {
                    Log.d("cs", "For loop started" + response.length());

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

            adp = new adapter_for_ecourse(arrayList, main_afterlogin.this,token,myid);
            rcv_for_enroll_courses.setAdapter(adp);
            Log.d("checking", "Json Data processed ");


        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        jsonArray.setRetryPolicy(new DefaultRetryPolicy(2000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArray);
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

            adp = new adapter_for_ecourse(arrayList, main_afterlogin.this,token,myid);
            rcv_for_enroll_courses.setAdapter(adp);
            Log.d("checking", "Json Data processed ");


        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        jsonArray.setRetryPolicy(new DefaultRetryPolicy(2000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArray);

    }

    public void exitapp() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Are you sure you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                SharedPreferences preferences = getSharedPreferences("TOKEN_ID", 0);
                                preferences.edit().remove("Token").apply();
                                preferences.edit().remove("ID").apply();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();


                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}