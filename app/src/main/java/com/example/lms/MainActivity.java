package com.example.lms;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    MaterialButton login;

    TextInputLayout helperText, helpertext2;
    EditText user_name, password;
    String str_user_name = "";
    String mytoken = "abc";
    String str_password = "";
    String profile_url;
    String id = "i";
    String url;
    String p_name,p_emil,p_img;
    String token_forcheck;
    String p_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        if (checkSharedPrefences())
        {
            if(token_forcheck.equals("8e6dc0f1606847131b60cc511d36db23")) {
                Intent intent = new Intent(getApplicationContext(), director_main_screen.class);
                intent.putExtra("Token", token_forcheck);
                intent.putExtra("ID", id);
                intent.putExtra("pname", p_name);
                intent.putExtra("pemai", p_emil);
                intent.putExtra("pimg", p_img);
                intent.putExtra("puser", p_username);
                Log.d("Info1", "Info =  " + p_name);
                Log.d("Info1", "Info =  " + p_emil);
                Log.d("Info1", "Info =  " + p_img);
                Log.d("Info1", "Info =  " + id);
                Log.d("Info1", "Info =  " + token_forcheck);
                Log.d("Info1", "Info =  " + p_username);
                startActivity(intent);
                finish();
            }
            else
            {Intent intent = new Intent(getApplicationContext(), main_afterlogin.class);
                intent.putExtra("Token", token_forcheck);
                intent.putExtra("ID", id);
                intent.putExtra("pname", p_name);
                intent.putExtra("pemai", p_emil);
                intent.putExtra("pimg", p_img);
                intent.putExtra("puser", p_username);
                Log.d("Info1", "Info =  " + p_name);
                Log.d("Info1", "Info =  " + p_emil);
                Log.d("Info1", "Info =  " + p_img);
                Log.d("Info1", "Info =  " + id);
                Log.d("Info1", "Info =  " + token_forcheck);
                Log.d("Info1", "Info =  " + p_username);
                startActivity(intent);
                finish();

            }
        }

        login = findViewById(R.id.login_btn);
        user_name = findViewById(R.id.update_email);
        password = findViewById(R.id.password_string);
        helperText = findViewById(R.id.textInputLayout);
        helpertext2 = findViewById(R.id.upname);
        //assign values to string


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str_user_name = (String) user_name.getText().toString();
                str_password = (String) password.getText().toString();
                String service = "moodle_mobile_app";
                if (str_password.equals("")) {
                    helperText.setHelperText("Required");
                } else {
                    url = "http://192.168.43.30/moodle/login/token.php?username=" + str_user_name + "&password=" + str_password + "&service=" + service + "";
/*
http://192.168.100.25/moodle/login/token.php?username=BIIT20200170&password=123@abc&service=moodle_mobile_app
                    http://localhost/moodle/login/token.php?username=BIIT20200170&password=123@abc&service=moodle_mobile_app
*/
                    processdata();

                    /* */
                }


            }
        });

    }

    private boolean checkSharedPrefences()
    {

        SharedPreferences preferences = getSharedPreferences("TOKEN_ID", MODE_PRIVATE);
        token_forcheck = preferences.getString("Token", "");

        SharedPreferences sharedPreferences = getSharedPreferences("TOKEN_ID", MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences("TOKEN_ID", MODE_PRIVATE);
        SharedPreferences sharedPreferences2= getSharedPreferences("TOKEN_ID", MODE_PRIVATE);
        SharedPreferences sharedPreferences3 = getSharedPreferences("TOKEN_ID", MODE_PRIVATE);
        SharedPreferences sharedPreferences4 = getSharedPreferences("TOKEN_ID", MODE_PRIVATE);
        id = sharedPreferences.getString("ID", "");
        p_name = sharedPreferences1.getString("pname", "");
        p_emil = sharedPreferences2.getString("pemai", "");
        p_img = sharedPreferences3.getString("pimg", "");
        p_username = sharedPreferences4.getString("puser", "");
        Log.d("sp", "checkSharedPrefences: "+p_name);
        Log.d("sp", "checkSharedPrefences: "+p_emil);
        Log.d("sp", "checkSharedPrefences: "+p_img);

        if (token_forcheck ==""||id=="")
        {
            return false;
        }
        return true;
    }

    public void getid() {
        Log.d("profile", "profile url=" + profile_url);

        JsonArrayRequest idreq = new JsonArrayRequest(Request.Method.GET, profile_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray myid) {
                try {
                    for (int i = 0; i < myid.length(); i++) {
                        JSONObject obj = myid.getJSONObject(i);
                        p_name=obj.getString("fullname");
                        p_emil=obj.getString("email");
                        p_username=obj.getString("username");
                        p_img=obj.getString("profileimageurl");
                        id = obj.getString("id");

                    }
                    Log.d("idf", "id=" + id);
                    Log.d("idf", "id=" + p_name);
                    Log.d("idf", "id=" + p_emil);
                    Log.d("idf", "id=" + p_img);
                    Log.d("idf", "id=" + p_username);


                    SharedPreferences.Editor editor = getSharedPreferences("TOKEN_ID", MODE_PRIVATE).edit();
                    SharedPreferences.Editor editor1 = getSharedPreferences("TOKEN_ID", MODE_PRIVATE).edit();
                    SharedPreferences.Editor editor2 = getSharedPreferences("TOKEN_ID", MODE_PRIVATE).edit();
                    SharedPreferences.Editor editor3 = getSharedPreferences("TOKEN_ID", MODE_PRIVATE).edit();
                    SharedPreferences.Editor editor4 = getSharedPreferences("TOKEN_ID", MODE_PRIVATE).edit();
                    editor.putString("ID",id);
                    editor1.putString("pname",p_name);
                    editor2.putString("pemai",p_emil);
                    editor3.putString("pimg",p_img);
                    editor4.putString("puser",p_username);
                    Log.d("checking", "hah" + id+p_name+p_emil+p_img);
                    editor.apply();
                    editor1.apply();
                    editor2.apply();
                    editor3.apply();
                    editor4.apply();

                    if(mytoken.equals("8e6dc0f1606847131b60cc511d36db23"))
                    {
                        Intent intent=new Intent(getApplicationContext(),director_main_screen.class);
                        Log.d("datashow", "datashow = " + mytoken);
                        intent.putExtra("Token", mytoken);
                        intent.putExtra("ID", id);
                        intent.putExtra("pname", p_name);
                        intent.putExtra("pemai", p_emil);
                        intent.putExtra("puser", p_username);
                        intent.putExtra("pimg", p_img);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Intent intent = new Intent(getApplicationContext(), main_afterlogin.class);
                        Log.d("datashow", "datashow = " + mytoken);
                        intent.putExtra("Token", mytoken);
                        intent.putExtra("ID", id);
                        intent.putExtra("pname", p_name);
                        intent.putExtra("pemai", p_emil);
                        intent.putExtra("puser", p_username);
                        intent.putExtra("pimg", p_img);
                        Log.d("Info", "Info =  " + p_name);
                        Log.d("Info", "Info =  " + p_emil);
                        Log.d("Info", "Info =  " + p_img);
                        startActivity(intent);
                        finish();


                    }

                } catch (JSONException e) {
                    Log.d("e", "" + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError er) {
                Log.d("id_er", "Id error =", er);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        idreq.setRetryPolicy(new DefaultRetryPolicy(2000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(idreq);
    }

    public void processdata() {
        Log.d("tag", "URL is : " + url);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    mytoken = response.getString("token");
                    SharedPreferences.Editor ed = getSharedPreferences("TOKEN_ID", MODE_PRIVATE).edit();
                    ed.putString("Token", mytoken);
                    Log.d("checking", "Token =  "+mytoken);
                    ed.apply();
                    profile_url = "http://192.168.43.30/moodle/webservice/rest/server.php?wstoken=" + mytoken + "&wsfunction=core_user_get_users_by_field&field=username&values[0]=" + str_user_name.toLowerCase() + "&moodlewsrestformat=json";
                    getid();


                } catch (JSONException e) {
                    if (mytoken.equals("abc")) {
                        Log.d("hamza", mytoken);

                        helperText.setHelperText("Invalid Password*");
                        helpertext2.setHelperText("Invalid Username*");


                    }
                    e.printStackTrace();
                }
                Log.d("Response", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());

                user_name.setText("error" + error.toString());
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(2000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }
}