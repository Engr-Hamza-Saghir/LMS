package com.example.lms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_submission_assinments extends RecyclerView.Adapter<view_holder_director>
{
    ArrayList<Model_submissions> model_directors;
    String token;
    Context context;
    String falto;
    String userid;
    String name,email;

    public Adapter_submission_assinments(ArrayList<Model_submissions> arrayList)
    {
        this.model_directors=arrayList;
    }
    @NonNull
    @Override
    public view_holder_director onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.singlerow_for_director, parent, false);
        return new view_holder_director(view);    }

    @Override
    public void onBindViewHolder(@NonNull view_holder_director holder, int position) {
        holder.dire_person_name.setText(model_directors.get(position).getSub_std_name());
        holder.dire_person_email.setText(model_directors.get(position).getSub_std_email());


    }

    @Override
    public int getItemCount() {
        return model_directors.size();
    }
}
