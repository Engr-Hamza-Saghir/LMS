package com.example.lms;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MenuItemCompat;
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
 * Use the {@link director_all_courses#newInstance} factory method to
 * create an instance of this fragment.
 */
public class director_all_courses extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RadioGroup radioGroup;
    ShimmerFrameLayout shimmerFrameLayout;
    RadioButton radioButton;
    Adapter_to_show_dir_courses adp;
    TextView total_courses;
    RecyclerView rcv_for_all_courses;
    String token;
    int cids;
    ArrayList<model_for_ecourse> arrayList = new ArrayList<>();


    public director_all_courses() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment director_all_courses.
     */
    // TODO: Rename and change types and number of parameters
    public static director_all_courses newInstance(String param1, String param2) {
        director_all_courses fragment = new director_all_courses();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_director_all_courses, container, false);
        radioGroup = (RadioGroup) view.findViewById(R.id.radiogroup);
        rcv_for_all_courses = (RecyclerView) view.findViewById(R.id.rcview_for_director_all_courses);
        rcv_for_all_courses.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        total_courses = (TextView) view.findViewById(R.id.total_subjects);
        shimmerFrameLayout=(ShimmerFrameLayout)view.findViewById(R.id.simmer_for_allcourses);
        shimmerFrameLayout.startShimmer();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TOKEN_ID", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("Token", "");
        Log.d("jwa", "onCreateView: " + token);
        String name = "",email="";

       /* for (model_director user:Utility.allUsers
        ) {


                name=user.getT_name();
                email = user.getT_email();
                break;

        }

        Log.d("userid_mh", "user id ="+email);*/
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton) view.findViewById(checkedId);

                Toast.makeText(getActivity(), "" + checkedId, Toast.LENGTH_SHORT).show();
                if (radioButton.getId() == R.id.select_current) {
                    shimmerFrameLayout.startShimmer();
                    dataincome_of_director1();

                } else {

                    shimmerFrameLayout.startShimmer();
                    dataincome_of_director();
                }

                // checkedId is the RadioButton selected
            }
        });
        return view;
    }

    public void dataincome_of_director() {
        ArrayList<model_for_ecourse> arrayList = new ArrayList<>();

        String director_url = "http://192.168.43.30/moodle/webservice/rest/server.php?wstoken=" + token + "&wsfunction=core_course_get_courses&moodlewsrestformat=json";

        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, director_url, null, response -> {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            rcv_for_all_courses.setVisibility(View.VISIBLE);
            for (int i = 1; i < 52; i++) {
                try {
                    Log.d("cs", "For loop started" + response.length());

                    JSONObject object = response.getJSONObject(i);
                    model_for_ecourse m;
                    m = new model_for_ecourse();
                    cids = Integer.valueOf(object.getString("id"));
                    Log.d("uff", "dataIncome: " + cids);
                    m.setCid(cids);
                    String t = object.getString("categoryid");
                    Log.d("pari", "dataincome_of_director: " + t);
                    m.setEc_img(R.drawable.sunject);
                    m.setEc_no(object.getString("shortname").toString());
                    m.setEc_name(object.getString("displayname").toString());
                    arrayList.add(m);
                    total_courses.setText("Courses:" + arrayList.size());

                    Log.d("ac", "IDS = " + cids);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            adp = new Adapter_to_show_dir_courses(arrayList, getActivity(), token);
            rcv_for_all_courses.setAdapter(adp);

            Log.d("checking", "Json Data processed ");


        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        jsonArray.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArray);
    }

    public void dataincome_of_director1() {

        String director_url = "http://192.168.43.30/moodle/webservice/rest/server.php?wstoken=" + token + "&wsfunction=core_course_get_courses&moodlewsrestformat=json";

        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, director_url, null, response -> {
shimmerFrameLayout.stopShimmer();
shimmerFrameLayout.setVisibility(View.GONE);
rcv_for_all_courses.setVisibility(View.VISIBLE);
            for (int i = 52; i < response.length(); i++) {
                try {
                    Log.d("cs", "For loop started" + response.length());

                    JSONObject object = response.getJSONObject(i);
                    model_for_ecourse m;
                    m = new model_for_ecourse();
                    cids = Integer.valueOf(object.getString("id"));
                    Log.d("uff", "dataIncome: " + cids);
                    m.setCid(cids);
                    String t = object.getString("categoryid");
                    Log.d("pari", "dataincome_of_director: " + t);
                    m.setEc_img(R.drawable.sunject);
                    m.setEc_no(object.getString("shortname").toString());
                    m.setEc_name(object.getString("displayname").toString());
                    arrayList.add(m);
                    total_courses.setText("Courses:" + arrayList.size());

                    Log.d("ac", "IDS = " + m);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            adp = new Adapter_to_show_dir_courses(arrayList, getActivity(), token);
            rcv_for_all_courses.setAdapter(adp);
            Log.d("checking", "Json Data processed ");


        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        jsonArray.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArray);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.serching,menu);
        MenuItem menuItem=menu.findItem(R.id.search_item);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setIconified(true);
        SearchManager searchManager=(SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                adp.getFilter().filter(s);
                return false;
            }
        });
        /*SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s)
            {
                adp.getFilter().filter(s);
                return false;
            }
        });*/
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle saveinstance) {
        super.onSaveInstanceState(saveinstance);
        if (saveinstance!=null)
        {

        }
    }


}
