package com.hoc.antoanthongtin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hoc.antoanthongtin.adapter.AdapteVideoDuocShare;
import com.hoc.antoanthongtin.model.User;
import com.hoc.antoanthongtin.model.VideoInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class DuocchiaseActivity extends AppCompatActivity {
    private final String urlgetid = "http://192.168.0.103/DoanATTT/getidvdduavaousername.php";
    private final String urlgetVideo = "http://192.168.0.103/DoanATTT/getvideonamebyidvideo.php";
    private ArrayList<User> userArrayList;
    private AdapteVideoDuocShare adapteVideoDuocShare;
    String username = "";
    ArrayList<VideoInfo> videoInfoArrayListArrayList;

    private int id = 0;
    private RecyclerView rcvDuocShare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duocchiase);
        init();

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        adapteVideoDuocShare = new AdapteVideoDuocShare(this, videoInfoArrayListArrayList);
        rcvDuocShare.setAdapter(adapteVideoDuocShare);
        rcvDuocShare.setLayoutManager(new LinearLayoutManager(this));
        getIdVideo(urlgetid);

     //   getVideoById(urlgetVideo);



    }
    private void init() {
        userArrayList = new ArrayList<>();
        rcvDuocShare = findViewById(R.id.rcv_duocchiase);
        videoInfoArrayListArrayList = new ArrayList<>();

    }



    private void getIdVideo(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i <response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                int idvideo  = object.getInt("idvideo");
                                String usernameT = object.getString("username");
                                if (username.equals(usernameT)) {
                                    id = idvideo;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("iddd", id + "");
                        getVideoById(urlgetVideo, id);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DuocchiaseActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        } );
        requestQueue.add(jsonArrayRequest);

    }


    private void getVideoById(String url,final int idtam) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        videoInfoArrayListArrayList.clear();
                        for (int i = 0; i <response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String tenVideo = object.getString("tenvideo");
                                int id = object.getInt("id");
                                if(id == idtam) {
//tao mot doi tuong chua id
                                    Log.d("tenvideo", tenVideo);
                                    videoInfoArrayListArrayList.add(new VideoInfo(id, tenVideo));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapteVideoDuocShare.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DuocchiaseActivity.this, error.toString() + "thu2", Toast.LENGTH_SHORT).show();
            }
        } );
        requestQueue.add(jsonArrayRequest);
    }

}

