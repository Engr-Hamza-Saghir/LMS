package com.example.lms;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_for_director extends RecyclerView.Adapter<view_holder_director> implements Filterable
{
    ArrayList<model_director>model_directors;
    ArrayList<model_director>backup;
    String token;
    Context context;
    String falto;
    String userid;
    String name,email;
    static String class_name;
    Integer course_id;
    public Adapter_for_director(@NonNull ArrayList<model_director> arrayList, Context context, String token, String user_id, Integer course_id)
    {
        this.model_directors=arrayList;
        this.token=token;
        this.falto =user_id;
        this.context=context;
        this.course_id=course_id;
        backup=new ArrayList<>(model_directors);
        class_name=context.getClass().getSimpleName();
    }
    @NonNull
    @Override
    public view_holder_director onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.singlerow_for_director, parent, false);
        return new view_holder_director(view);
    }

    @Override
    public void onBindViewHolder(@NonNull view_holder_director holder, int position)
    {
        final model_director temp = model_directors.get(position);

        holder.dire_person_name.setText(model_directors.get(position).getT_name());
        holder.dire_person_email.setText(model_directors.get(position).getT_email());
        name=model_directors.get(position).getT_name();
        email=model_directors.get(position).getT_email();
        Log.d("saaad", ""+class_name);
        Log.d("saaad", ""+course_id);
        Picasso.get().load(model_directors.get(position).getT_img()).placeholder(R.drawable.ic_baseline_person_24).into(holder.dire_person_img);
        holder.dire_person_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (context.getClass().getSimpleName().equals("Directore_Screen"))
                {
                    Intent intent = new Intent(context, Courses_weeks.class);
                    intent.putExtra("my_ecno", "cno");
                    intent.putExtra("my_ecid", course_id);
                    intent.putExtra("my_ecname", "Weeks");
                    intent.putExtra("p_id", userid);
                    context.startActivity(intent);

                }
                else {
                    userid = model_directors.get(position).getT_id();
                    Log.d("axy", "dataIncome_of_teacher_from_course: " + userid);
                    Intent intent = new Intent(context, show_user_record.class);
                    intent.putExtra("Token", token);
                    intent.putExtra("ID", userid);
                    intent.putExtra("pname", model_directors.get(position).getT_name());
                    intent.putExtra("pemai", model_directors.get(position).getT_email());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return model_directors.size();
    }

    @Override
    public Filter getFilter()
    {
        return filter;
    }
    Filter filter=new Filter()
    {
        @Override
        //compelete on bakground thread
        protected FilterResults performFiltering(CharSequence search_keyword)
        {
            ArrayList<model_director> filtered_data=new ArrayList<>();
            if (search_keyword.toString().equals(""))
            {
                filtered_data.addAll(backup);
            }
            else
            {
                for(model_director model:backup)
                {
                    if (model.getT_name().toString().toLowerCase().contains(search_keyword.toString().toLowerCase()))
                    {
                        filtered_data.add(model);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filtered_data;
            return filterResults;
        }

        @Override
        //compelete on main_ui thread
        protected void publishResults(CharSequence charSequence, FilterResults filterResults)
        {
            model_directors.clear();
            model_directors.addAll((ArrayList<model_director>)filterResults.values);
            notifyDataSetChanged();
        }
    };
}
