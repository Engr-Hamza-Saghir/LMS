package com.example.lms;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class vholder_enrol_courses extends RecyclerView.ViewHolder
{
ImageView jencourse_img;
TextView jecours_no,jecours_name;
    public vholder_enrol_courses(@NonNull View itemView)
    {
        super(itemView);

        jencourse_img=(ImageView)itemView.findViewById(R.id.subject_img);
        jecours_no=(TextView)itemView.findViewById(R.id.subject_no);
        jecours_name=(TextView)itemView.findViewById(R.id.subjet_name);

    }
}
