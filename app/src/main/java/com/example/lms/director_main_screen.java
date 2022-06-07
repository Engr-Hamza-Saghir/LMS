package com.example.lms;

import android.os.Bundle;

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
        all_teachers=(TabItem) findViewById(R.id.tab_allcourses);
        all_students=(TabItem) findViewById(R.id.tab_students);
        all_courses=(TabItem) findViewById(R.id.tab_teachers);
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
}