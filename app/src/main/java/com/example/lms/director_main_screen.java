package com.example.lms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class director_main_screen extends AppCompatActivity
{
    TabLayout tabLayout;
    TabItem all_courses;
    TabItem all_teachers;
    TabItem all_students;
    TabItem all_late_submissions;
    ViewPager viewPager;
    Viewpage_Adapter viewpage_adapter;
    String token,myid,string_person_name,username,string_person_email,string_person_img;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setTitle("DashBoard");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_director_main_screen);
        tabLayout=(TabLayout) findViewById(R.id.tab_for_director);
        all_courses=(TabItem) findViewById(R.id.tab_allcourses);
        all_teachers=(TabItem) findViewById(R.id.tab_teachers);
        Log.d("TAGi", "onCreate: "+all_students);
        all_students=(TabItem) findViewById(R.id.tab_students);
        all_late_submissions=(TabItem) findViewById(R.id.tab_latesubmissions);
        viewPager=(ViewPager) findViewById(R.id.viewpager_for_director);

        token = getIntent().getStringExtra("Token");
        myid = getIntent().getStringExtra("ID");
        string_person_name = getIntent().getStringExtra("pname");
        username = getIntent().getStringExtra("puser");
        string_person_email = getIntent().getStringExtra("pemai");
        string_person_img = getIntent().getStringExtra("pimg");

        viewpage_adapter=new Viewpage_Adapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(viewpage_adapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition()==0||tab.getPosition()==1||tab.getPosition()==2||tab.getPosition()==3)
                    viewpage_adapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.dir_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int item_id=item.getItemId();
        if (item_id==R.id.dir_profile)
        {
            Intent intent = new Intent(getApplicationContext(), editprofile.class);
            intent.putExtra("Token",token);
            intent.putExtra("pname",username);
            startActivity(intent);
        }
        else if (item_id==R.id.dir_logout)
        {
            exitapp();
        }
        return true;
    }
    public void exitapp() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Logout?");
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