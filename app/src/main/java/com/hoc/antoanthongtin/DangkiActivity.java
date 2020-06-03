package com.hoc.antoanthongtin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DangkiActivity extends AppCompatActivity {

    ArrayList<String> arrayListUsername;
    private final String urlGetUser = "http://192.168.0.103/DoanATTT/getuser.php";
    private final String urlDangKi = "http://192.168.0.103/DoanATTT/dangki.php";
    private EditText edtDKUsername, edtDKPassword, edtDKXacNhanPassword;
    private Button btnDKDangKi, btnDKHuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangki);
        init();
        arrayListUsername = new ArrayList<>();
        getUser(urlGetUser);

        btnDKDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtDKUsername.getText().toString().trim();
                String password = edtDKPassword.getText().toString().trim();
                String xacnhanPassword = edtDKXacNhanPassword.getText().toString().trim();
                int check = 0;

                if (username.isEmpty() || password.isEmpty() || xacnhanPassword.isEmpty()) {
                    Toast.makeText(DangkiActivity.this, "Nhập chưa đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if(!password.equals(xacnhanPassword)) {
                    Toast.makeText(DangkiActivity.this, "Không khớp mật khẩu", Toast.LENGTH_SHORT).show();

                } else if(password.equals(xacnhanPassword)) {
                    for(int i = 0; i < arrayListUsername.size(); i++) {
                        if(username.equals(arrayListUsername.get(i))) {
                            check = 1;
                            Toast.makeText(DangkiActivity.this, "Tài Khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    if(check == 1) {

                    } else {
                        dangki(urlDangKi, username, password);
                    }

                }


            }
        });

        btnDKHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void init() {
        edtDKUsername = findViewById(R.id.edt_DKusername);
        edtDKPassword = findViewById(R.id.edt_DKpassword);
        edtDKXacNhanPassword = findViewById(R.id.edt_DKxacnhanpassword);

        btnDKDangKi = findViewById(R.id.btn_DKdangki);
        btnDKHuy = findViewById(R.id.btn_DKhuy);
    }
    private void getUser(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        arrayListUsername.clear();
                        for (int i = 0; i <response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String username = object.getString("username");

                                arrayListUsername.add(username);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DangkiActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void dangki(String url, final String username, final String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success")) {
                    Intent intent = new Intent(DangkiActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else if(response.trim().equals("error")) {
                    Toast.makeText(DangkiActivity.this, "Lỗi Đăng Kí CSDL", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DangkiActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();

                param.put("username", username);
                param.put("password", password);
                return param;
            }
        };
        requestQueue.add(stringRequest);

    }

}
