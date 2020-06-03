package com.hoc.antoanthongtin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hoc.antoanthongtin.adapter.AdapteDanhSachVideo;
import com.hoc.antoanthongtin.model.VideoTen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DanhsachActivity extends AppCompatActivity {

    private final String urlgetTenVideo = "http://192.168.0.103/DoanATTT/getvideoname.php";

    private ArrayList<VideoTen> videoTenArrayList;
    private RecyclerView rcvDanhSach;
    private AdapteDanhSachVideo adapteDanhSachVideo;
    public String username = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach);
        init();
        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        //videoTenArrayList = new ArrayList<>();
        adapteDanhSachVideo = new AdapteDanhSachVideo(this, videoTenArrayList);
        rcvDanhSach.setAdapter(adapteDanhSachVideo);
        rcvDanhSach.setLayoutManager(new LinearLayoutManager(this));
        getDanhSachVideo(urlgetTenVideo);

    }
    private void init() {

        videoTenArrayList = new ArrayList<>();
        rcvDanhSach = findViewById(R.id.rcv_danhsach);
    }
    private void getDanhSachVideo(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        videoTenArrayList.clear();
                        for (int i = 0; i <response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                int id = object.getInt("id");
                                String tenvideo = object.getString("tenvideo");
                                String usernameG = object.getString("username");
                                Toast.makeText(DanhsachActivity.this, tenvideo, Toast.LENGTH_SHORT).show();
                                if(username.equals(usernameG)) {
                                    videoTenArrayList.add(new VideoTen(id, tenvideo, usernameG));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapteDanhSachVideo.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DanhsachActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

}
