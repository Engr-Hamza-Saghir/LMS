package com.example.lms;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Services extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    public String user_id;
    public boolean flag=false;
   public ArrayList<model_director> holder = new ArrayList<>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d("start", "services start");
dataincome_of_teacher_from_course();
        return START_STICKY;
    }
    public void dataincome_of_teacher_from_course() {
        ArrayList<model_director> holder1 = new ArrayList<>();
        String url_for_get_teacher_courses = "http://192.168.43.30/moodle/webservice/rest/server.php?wstoken=8e6dc0f1606847131b60cc511d36db23&wsfunction=core_enrol_get_enrolled_users&courseid=1&moodlewsrestformat=json";
        Log.d("start", "For loop started++aqib"+url_for_get_teacher_courses);

        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url_for_get_teacher_courses, null, response -> {
            Log.d("start", "For loop started++aqib");
            Log.d("uma", "dataincome_of_teacher_from_course: " + response.length());

            for (int i = 39; i < response.length(); i++) {
                try {
                    JSONObject object = response.getJSONObject(i);

                    model_director m;
                    m = new model_director();
                    boolean f = true;
                    String kshi = object.getString("username");
                    if (kshi.contains("biit") || kshi.contains("arid")) {

                    }
                    user_id=object.getString("id");
                    Log.d("ajkal", "dataincome_of_teacher_from_course: " + user_id);
                    m.setT_img(object.getString("profileimageurlsmall"));
                    m.setT_name(object.getString("fullname").toString());
                    m.setT_email(object.getString("email"));
                    m.setT_id(user_id);
                    holder.add(m);

                    Log.d("poyo", "umer"+m.getT_email());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            flag=true;




            Log.d("KAL", "Json Data processed "+holder.size());


        }, error -> {
            Log.d("LOL", "dataincome_of_teacher_from_course: "+error);

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        jsonArray.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArray);
    }

}
