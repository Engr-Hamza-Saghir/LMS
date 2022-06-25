package com.example.lms;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class General_act extends AppCompatActivity {
    String week_title;
    String url;
    String fileurl;
    String token;
    Integer cid;
    TextView textView;
    TextView result;
    boolean flag = false;
    int position;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        week_title = getIntent().getStringExtra("name");
        token = getIntent().getStringExtra("token");
        cid = getIntent().getIntExtra("cid", 0);
        position = getIntent().getIntExtra("position", 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        url = "http://192.168.43.30/moodle/webservice/rest/server.php?wstoken=" + token + "&wsfunction=core_course_get_contents&courseid=" + cid.toString() + "&moodlewsrestformat=json&options[0][name]=excludemodules&options[0][value]=false";
        textView=(TextView)findViewById(R.id.download_result);
        webView=(WebView)findViewById(R.id.webpage_for_result);
        Log.d("bla", "onCreate: "+position);


        dataque();

    }
    public void dataque() {
        Log.d("bla", "onCreate: function started");


        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            Log.d("chunk", "For loop started" + position);


            try {

                JSONObject object = response.getJSONObject(position);
                Log.d("wekdata", "dataque: " + object.getString("modules"));
                JSONArray jsonArray1 = object.getJSONArray("modules");
                for (int j = 1; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject = jsonArray1.getJSONObject(j);

                    Log.d("zzz", "if started " + jsonObject.getString("contents"));
                    String temp = jsonObject.getString("contents");
                    Log.d("chkif", "if started:" + temp);

                    Log.d("chkif", "if started:" + jsonObject.getString("modicon"));
                    if (temp != null) {
                        Log.d("chkif", "dataque: " + jsonObject.getString("contents"));
                        JSONArray jsonArray2 = jsonObject.getJSONArray("contents");
                        for (int k = 0; k < jsonArray2.length(); k++) {
                            JSONObject innerobject = jsonArray2.getJSONObject(k);
                            Log.d("flag", "dataque: " + flag);
                            String t = innerobject.getString("type");
                            fileurl=jsonObject.getString("url");
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view)
                                {
                                   /* Uri uri = Uri.parse(fileurl);
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(intent);*/
                                    webView.loadUrl(fileurl);
                                    WebSettings webSettings=webView.getSettings();
                                    webSettings.setJavaScriptEnabled(true);
                                    webView.setWebViewClient(new WebViewClient());
                                    webView.getSettings().setAllowFileAccess(true);
                                    webView.getSettings().setRenderPriority (WebSettings.RenderPriority. HIGH);
                                    webView.getSettings().setCacheMode (WebSettings.LOAD_CACHE_ELSE_NETWORK);
                                    webView.getSettings().setAppCacheEnabled(true);
/*
                                    webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
*/
                                    webSettings.setDomStorageEnabled(true);
                                    webSettings.setLayoutAlgorithm (WebSettings. LayoutAlgorithm.NARROW_COLUMNS);
                                    webSettings.setUseWideViewPort(true);
                                    webSettings.setSavePassword(true);
                                    webSettings.setSaveFormData(true);
                                    webSettings.setEnableSmoothTransition (true);
                                    webSettings.getAllowContentAccess();
                                }
                            });

                           /* DownloadManager.Request request = new DownloadManager.Request (Uri.parse(fileurl));
                            String title=URLUtil.guessFileName(fileurl,null,null);
                            request.setTitle(title);
                            request.setDescription("Downloading File please wait.....");
                            String cookie = CookieManager.getInstance().getCookie(fileurl);

                            request.addRequestHeader( "cookie", cookie);

                            request.setNotificationVisibility (DownloadManager.Request. VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir (Environment.DIRECTORY_DOWNLOADS, title);

                            DownloadManager downloadManager = (DownloadManager) getSystemService (DOWNLOAD_SERVICE);

                            downloadManager.enqueue(request);*/

                        }
                    } else {
                    }

                    Log.d("success", "dataque: " + jsonObject.getString("name"));

                    flag = false;
                    Log.d("weekdata", "weekdata : " + jsonObject.getString("url") + "  | " + R.drawable.youtube);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("checking", "Json Data processed ");
        }, error -> {

        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        jsonArray.setRetryPolicy(new DefaultRetryPolicy(2000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArray);


    }

        public List<String> getTextFromWeb(String urlString)
        {
            URLConnection feedUrl;
            List<String> placeAddress = new ArrayList<>();

            try
            {
                feedUrl = new URL(urlString).openConnection();
                InputStream is = feedUrl.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String line = null;

                while ((line = reader.readLine()) != null) // read line by line
                {
                    placeAddress.add(line); // add line to list
                }
                is.close(); // close input stream

                return placeAddress; // return whatever you need
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }
    }

