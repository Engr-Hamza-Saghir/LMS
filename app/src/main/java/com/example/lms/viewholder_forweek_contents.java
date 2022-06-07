package com.example.lms;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class viewholder_forweek_contents extends RecyclerView.ViewHolder
{
    ImageView view;
    TextView textView;
    TextView txt;


    public viewholder_forweek_contents(@NonNull View itemView)
    {
        super(itemView);
        view=(ImageView) itemView.findViewById(R.id.week_content_img);
        textView=(TextView)itemView.findViewById(R.id.week_content_url);
        txt=(TextView)itemView.findViewById(R.id.week_content_name);
        //for make edit text ineditable
//        textView.setInputType(InputType.TYPE_NULL);
//        textView.setKeyListener(null);
    }


}
