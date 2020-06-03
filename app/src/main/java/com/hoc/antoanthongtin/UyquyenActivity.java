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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hoc.antoanthongtin.adapter.AdapteUser;
import com.hoc.antoanthongtin.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UyquyenActivity extends AppCompatActivity {
    public String username = "";
    public int idVideo = 0;
    private int idtest = 0;
    public String tenVideo = "";
    private Button btnChiase;
    private ArrayList<User> userArrayList;
    private RecyclerView rcvUserUyQuyen;
    private AdapteUser adapteUser;
    String url = "http://192.168.0.103/DoanATTT/getuseruyquyen.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uyquyen);
        init();

        Intent intent = getIntent();
        idVideo = intent.getIntExtra("idvideo", 0);
        tenVideo = intent.getStringExtra("videoname");
        username = intent.getStringExtra("username");
        adapteUser = new AdapteUser( this, userArrayList);
        rcvUserUyQuyen.setAdapter(adapteUser);
        rcvUserUyQuyen.setLayoutManager(new LinearLayoutManager(this));
        getUser2(url);
        btnChiase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(UyquyenActivity.this, idtest + "", Toast.LENGTH_SHORT).show();

            }
        });


    }
    private void init() {
        userArrayList = new ArrayList<>();
        rcvUserUyQuyen = findViewById(R.id.rcv_danhsachuseruyquyen);
        btnChiase = findViewById(R.id.btn_chiase);
    }
    private void getUser2(String url) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        userArrayList.clear();
                        for (int i = 0; i <response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                int id = object.getInt("id");
                                String usernameG = object.getString("username");
                                Log.d("UyquyenActivity", usernameG);
                                int trangthai = object.getInt("trangthai");
                                int idvideo  = object.getInt("idvideo");


                                if(!username.equals(usernameG)) {
                                    userArrayList.add(new User(id, usernameG, trangthai, idvideo));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapteUser.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UyquyenActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

}
