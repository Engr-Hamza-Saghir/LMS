package com.example.lms;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_to_show_dir_courses extends RecyclerView.Adapter<vholder_enrol_courses>
{
    Context context;
    ArrayList<model_for_ecourse> arrayList;
    String cno;
    Integer cid;
    String cname;
    String token;
    public Adapter_to_show_dir_courses(@NonNull ArrayList<model_for_ecourse> arrayList, Context context, String token) {
        this.arrayList = arrayList;
        this.token = token;
        Log.d("sagh", "onClick: " + token);
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
    public void onBindViewHolder(@NonNull vholder_enrol_courses holder, int position)
    {
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
                Log.d("aqib", "onClick: ");
                Intent intent = new Intent(context, Directore_Screen.class);
                intent.putExtra("my_ecno", cno);
                intent.putExtra("my_ecid", cid);
                intent.putExtra("token", token);
                intent.putExtra("my_ecname", cname);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
