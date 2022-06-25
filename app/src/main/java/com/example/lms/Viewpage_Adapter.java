package com.example.lms;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Viewpage_Adapter extends FragmentPagerAdapter {
    int tabcount;

    public Viewpage_Adapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount=behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0: return new director_all_courses();
            case 1: return new teachers();
            case 2: return new director_all_students();
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
