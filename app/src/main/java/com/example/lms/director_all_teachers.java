package com.example.lms;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link director_all_teachers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class director_all_teachers extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView_dir_all_teachers;
    adapter_for_ecourse adp;
    Adapter_for_director myadapter;
String token;
int cids;
String user_id;


    public director_all_teachers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment director_all_teachers.
     */
    // TODO: Rename and change types and number of parameters
    public static director_all_teachers newInstance(String param1, String param2) {
        director_all_teachers fragment = new director_all_teachers();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_director_all_teachers, container, false);
        recyclerView_dir_all_teachers=(RecyclerView) view.findViewById(R.id.rcview_for_director_all_teacher);
        recyclerView_dir_all_teachers.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TOKEN_ID", Context.MODE_PRIVATE);
        token=sharedPreferences.getString("Token","");
        Log.d("jwa", "onCreateView: "+token);
/*        dataincome_of_director();*/
        dataincome_of_teacher_from_course();
        return view;

    }
    public void dataincome_of_director() {
        ArrayList<model_for_ecourse> arrayList = new ArrayList<>();

        String director_url = "http://192.168.43.30/moodle/webservice/rest/server.php?wstoken=" + token + "&wsfunction=core_course_get_courses&moodlewsrestformat=json";

        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, director_url, null, response -> {

            for (int i = 0; i < response.length(); i++) {
                try {
                    Log.d("cs", "For loop started" + response.length());

                    JSONObject object = response.getJSONObject(i);
                    model_for_ecourse m;
                    m = new model_for_ecourse();
                    cids = Integer.valueOf(object.getString("id"));
                    Log.d("uff", "dataIncome: " + cids);
                    user_id=object.getString("id");
                    m.setCid(cids);
                    m.setEc_img(R.drawable.sunject);
                    m.setEc_no(object.getString("shortname").toString());
                    m.setEc_name(object.getString("displayname").toString());
                    arrayList.add(m);
                    Log.d("ac", "IDS = " + cids);


                    Log.d("checking", "Courses = " + arrayList.get(i).getEc_name());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            adp = new adapter_for_ecourse(arrayList, getActivity(),token,user_id);
            recyclerView_dir_all_teachers.setAdapter(adp);
            Log.d("checking", "Json Data processed ");


        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        jsonArray.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArray);
    }
    public void dataincome_of_teacher_from_course() {
        ArrayList<model_director> holder = new ArrayList<>();
        ArrayList<model_director> holder1 = new ArrayList<>();
        String url_for_get_teacher_courses = "http://192.168.45.30/moodle/webservice/rest/server.php?wstoken=" + token + "&wsfunction=core_enrol_get_enrolled_users&courseid=1&moodlewsrestformat=json";

        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url_for_get_teacher_courses, null, response -> {
            Log.d("checking", "For loop started");
            Log.d("uma", "dataincome_of_teacher_from_course: " + response.length());

            for (int i = 2; i < 39; i++) {
                try {
                    Log.d("uma", "dataincome_of_teacher_from_course: " + response.length());
                    JSONObject object = response.getJSONObject(i);

                    model_director m;
                    m = new model_director();
                    boolean f = true;
                    String kshi = object.getString("username");
                    if (kshi.contains("biit") || kshi.contains("arid")) {

                    }
                    m.setT_img(object.getString("profileimageurlsmall"));
                    m.setT_name(object.getString("fullname").toString());
                    m.setT_email(object.getString("email"));
                    holder.add(m);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            myadapter = new Adapter_for_director(holder, getActivity().getApplicationContext(), token,user_id);
            recyclerView_dir_all_teachers.setAdapter(myadapter);

            Log.d("checking", "Json Data processed ");


        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        jsonArray.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArray);
    }

}