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
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link teachers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class teachers extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView_dir_all_profs;
    private adapter_for_ecourse adp;
    private Adapter_for_director myadapter;
    private String token;
    private String user_id;
    ShimmerFrameLayout shimmerFrameLayout;
    private int cids;
    public teachers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment teachers.
     */
    // TODO: Rename and change types and number of parameters
    public static teachers newInstance(String param1, String param2) {
        teachers fragment = new teachers();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_teachers, container, false);
        recyclerView_dir_all_profs =(RecyclerView) view.findViewById(R.id.rcview_for_director_all_prof);
        recyclerView_dir_all_profs.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        shimmerFrameLayout=(ShimmerFrameLayout)view.findViewById(R.id.simmer_for_allcourses);
        shimmerFrameLayout.startShimmer();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TOKEN_ID", Context.MODE_PRIVATE);
        token=sharedPreferences.getString("Token","");
        Log.d("jwa", "onCreateView: "+token);

        /*        dataincome_of_director();*/
        dataincome_of_teacher_from_course();
        return  view;
    }
    public void dataincome_of_teacher_from_course() {





        ArrayList<model_director> holder = new ArrayList<>();
        ArrayList<model_director> holder1 = new ArrayList<>();
        String url_for_get_teacher_courses = "http://192.168.43.30/moodle/webservice/rest/server.php?wstoken=" + token + "&wsfunction=core_enrol_get_enrolled_users&courseid=1&moodlewsrestformat=json";

        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url_for_get_teacher_courses, null, response -> {
            Log.d("checking", "For loop started");
            Log.d("uma", "dataincome_of_teacher_from_course: " + response.length());
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            recyclerView_dir_all_profs.setVisibility(View.VISIBLE);
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
                    user_id=object.getString("id");
                    m.setT_img(object.getString("profileimageurlsmall"));
                    m.setT_name(object.getString("fullname").toString());
                    m.setT_email(object.getString("email"));
                    m.setT_id(user_id);
                    holder.add(m);

                    Log.d("poyo", "umer"+m);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            myadapter = new Adapter_for_director(holder, getActivity().getApplicationContext(),token,user_id,0);
            recyclerView_dir_all_profs.setAdapter(myadapter);
            Log.d("checking", "Json Data processed ");


        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        jsonArray.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArray);
    }
    /*@Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.serching, menu);
        MenuItem menuItem = menu.findItem(R.id.search_item);
        SearchView actionView = (SearchView) MenuItemCompat.getActionView(menuItem);
        actionView.setIconified(true);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        actionView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        actionView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(myadapter!=null)
                {myadapter.getFilter().filter(s);}

                return false;
            }
        });
    }*/

}