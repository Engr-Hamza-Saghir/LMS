package com.example.lms;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapter_for_ecourse extends RecyclerView.Adapter<vholder_enrol_courses> implements Filterable {
    Context context;
    ArrayList<model_for_ecourse> arrayList;
    ArrayList<model_for_ecourse> backup;
    String cno;
    Integer cid;
    String cname;
    String token;
    String my_id;

    public adapter_for_ecourse(@NonNull ArrayList<model_for_ecourse> arrayList, Context context, String token,String id) {
        this.arrayList = arrayList;
        this.token = token;
        Log.d("sagh", "onClick: " + token);
this.my_id=id;
backup=new ArrayList<>(arrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public vholder_enrol_courses onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.enroll_single_row, parent, false);
        return new vholder_enrol_courses(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vholder_enrol_courses holder, int position) {
        final model_for_ecourse temp = arrayList.get(position);

        holder.jecours_no.setText(arrayList.get(position).getEc_no());
        holder.jecours_name.setText(arrayList.get(position).getEc_name());
        holder.jencourse_img.setImageResource(arrayList.get(position).getEc_img());
        holder.jecours_name.setOnClickListener(view -> Toast.makeText(context, "" + temp.getEc_name(), Toast.LENGTH_LONG).show());
        holder.jencourse_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cno = temp.getEc_no().toString();
                cid = temp.getCid();
                Log.d("thisid", "THew " + cid);
                cname = temp.getEc_name().toString();
                Log.d("TAG", "id nd name : " + cid + " / " + cno + " |" + cname);



                    Log.d("aqib", "onClick1: ");
                    Intent intent = new Intent(context, Courses_weeks.class);
                    intent.putExtra("my_ecno", cno);
                    intent.putExtra("my_ecid", cid);
                    intent.putExtra("my_ecname", cname);
                    intent.putExtra("p_id", my_id);
                    context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {

        return arrayList.size();
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
            ArrayList<model_for_ecourse> filtered_data=new ArrayList<>();
            if (search_keyword.toString().equals(""))
            {
                filtered_data.addAll(backup);
            }
            else
            {
                for(model_for_ecourse model:backup)
                {
                    if (model.getEc_no().toString().toLowerCase().contains(search_keyword.toString().toLowerCase()))
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
            arrayList.clear();
            arrayList.addAll((ArrayList<model_for_ecourse>)filterResults.values);
            notifyDataSetChanged();
        }
    };
}
