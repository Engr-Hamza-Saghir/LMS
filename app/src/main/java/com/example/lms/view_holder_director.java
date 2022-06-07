package com.example.lms;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class view_holder_director extends RecyclerView.ViewHolder
{
    ImageView dire_person_img;
    TextView dire_person_name,dire_person_email;
    CardView cardView;
    public view_holder_director(@NonNull View itemView)
    {
        super(itemView);

        dire_person_img=(ImageView)itemView.findViewById(R.id.dir_person_img);
        dire_person_name=(TextView)itemView.findViewById(R.id.dir_person_name);
        dire_person_email=(TextView)itemView.findViewById(R.id.dir_person_email);
        cardView=(CardView)itemView.findViewById(R.id.cardView13);
    }
}
