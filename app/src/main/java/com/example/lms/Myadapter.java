package com.example.lms;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Myadapter extends RecyclerView.Adapter<myviewholder>  {
    ArrayList<Model> data;
    String url_of_task;
    String name_of_task;
    String url_img;
    String token;
    Integer cid;
    Context context;



    public Myadapter(ArrayList<Model> data, Context context,String token,Integer cid)
    {
        this.data = data;
        this.token=token;
        this.cid=cid;
        Log.d("arsize", "size of arraay list =" + data.size());
        this.context = context;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.singlerow, parent, false);
        return new myviewholder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        final Model model = data.get(position);
        int p=position;
        holder.editText.setText(data.get(position).getWeekedit());
        holder.editText.setText(data.get(position).getWeekedit());
        holder.imagebtn.setImageResource(data.get(position).getImgname());

        holder.imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Log.d("huza", "onClick: "+position);
                if(position==0&&token.equals("8e6dc0f1606847131b60cc511d36db23"))
                {
                    Intent intent=new Intent(context,General_act.class);
                    intent.putExtra("position", p);
                    intent.putExtra("token", token);
                    intent.putExtra("cid", cid);
                    intent.putExtra("name", model.getWeekedit());
                    context.startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, Weekdata.class);
                    intent.putExtra("position", p);
                    intent.putExtra("token", token);
                    intent.putExtra("cid", cid);
                    intent.putExtra("name", model.getWeekedit());
                    context.startActivity(intent);
                }
/*
            selectListner.onItemClicked(data.get(position));
*/


            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}
