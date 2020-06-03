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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private final String urlLogin = "http://192.168.0.103/DoanATTT/dangnhap.php";
    private EditText edtUsername, edtPassword;
    private Button btnDangNhap, btnDangKi;
    String username = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edtUsername.getText().toString().trim();
                password = edtPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Nhập chưa đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    login(urlLogin, username, password);
                }

            }
        });

        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DangkiActivity.class);
                startActivity(intent);
            }
        });

    }
    private void login(String url, final String username, final String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Log.d("n2", edtUsername.getText().toString().trim());
                    intent.putExtra("username", edtUsername.getText().toString().trim());
                    startActivity(intent);
                } else if(response.trim().equals("error")) {
                    Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu !", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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
    private void init() {
        edtUsername = findViewById(R.id.edt_usename);
        edtPassword = findViewById(R.id.edt_password);

        btnDangNhap = findViewById(R.id.btn_dangnhap);
        btnDangKi = findViewById(R.id.btn_dangki);

    }
}
