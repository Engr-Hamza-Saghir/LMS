package com.example.lms;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class myviewholder extends RecyclerView.ViewHolder
{
    ImageView imagebtn;
    public CardView view;
    EditText editText;
    public myviewholder(@NonNull View item)
    {
        super(item);
        imagebtn=(ImageView) item.findViewById(R.id.week_btn1);
        editText=(EditText)item.findViewById(R.id.weeknum_edit);
        view=item.findViewById(R.id.carde);
        //for make edit text ineditable
        editText.setInputType(InputType.TYPE_NULL);
        editText.setKeyListener(null);

       /* imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

            }
        });*/

    }
}
