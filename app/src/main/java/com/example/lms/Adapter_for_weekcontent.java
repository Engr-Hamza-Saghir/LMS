package com.example.lms;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_for_weekcontent extends RecyclerView.Adapter<viewholder_forweek_contents> {
    ArrayList<Model_for_week_content> model;
    Context context;
    int i=0;
    boolean Flag;

    public Adapter_for_weekcontent(ArrayList<Model_for_week_content> model, Context context, boolean flag) {
        this.context = context;
        this.model = model;
        i++;
        this.Flag = flag;
//        Log.d("fg", "Adapter_for_weekcontent: constructor "+Flag +" | "+i);

    }

    @NonNull
    @Override
    public viewholder_forweek_contents onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.singlerow_for_weekcontents, parent, false);
        return new viewholder_forweek_contents(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder_forweek_contents holder, int position) {
        final Model_for_week_content model_for_week_content = model.get(position);
        holder.textView.setText(model.get(position).getWeek_content());
        holder.txt.setText(model.get(position).getWeek_content_name());

//        Glide.with(context).load("http://192.168.43.30/moodle/theme/image.php/boost/core/1646906479/f/pdf-24").into(holder.view);
        Picasso.get().load(model.get(position).getWeek_content_img()).placeholder(R.drawable.ic_baseline_insert_link_24).into(holder.view);
        Log.d("picasso", "onBindViewHolder: "+model.get(position).getWeek_content_img());
        Log.d("picasso", "onBindViewHolder: "+position);

        Log.d("fg", "Adapter_for_weekcontent: "+ model_for_week_content.isF()+" | "+i);

       /* if (!model_for_week_content.isF())
        {
            Log.d("umer", "click = false: ");

            holder.textView.setLinksClickable(false);
            holder.textView.setFocusable(false);


        }*/

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                if (!model_for_week_content.isF())
                {
                    DownloadManager manager;
                    manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(holder.textView.getText().toString());
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                    long reference = manager.enqueue(request);
                }
                else
                {
                    Uri uri = Uri.parse(model_for_week_content.getWeek_content());
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return model.size();
    }
}
