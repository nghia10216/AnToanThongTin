package com.hoc.antoanthongtin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hoc.antoanthongtin.thuattoan.AES;
import com.hoc.antoanthongtin.thuattoan.Blowfish;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class TaixuongActivity extends AppCompatActivity {
    private AES aes;
    private Blowfish blowfish;
    private final String url = "http://192.168.0.103/DoanATTT/getmahoa.php";
    private Button btnTaixuongAES, btnTaixuongBlow, btnTaixuongAESBlow;
    private TextView txtTenvideoTaixuong, txtTrangthaiTaixuong;
    String stringaes = "";
    String stringblowfish = "";
    String stringaesblowfish1 = "";
    String stringaesblowfish2 = "";
    String videoten = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taixuong);
        init();
        Intent intent = getIntent();

        videoten = intent.getStringExtra("videoten");
        int idvideo = intent.getIntExtra("idvideo", 0);

        txtTenvideoTaixuong.setText(videoten);

        getVideoById(url, idvideo);


    }
    private void init() {
        btnTaixuongAES = findViewById(R.id.btn_taixuongaes);
        btnTaixuongBlow = findViewById(R.id.btn_taixuongblow);
        btnTaixuongAESBlow = findViewById(R.id.btn_taixuongaesblow);
        txtTenvideoTaixuong = findViewById(R.id.txt_tenvideotaixuong);
        txtTrangthaiTaixuong = findViewById(R.id.txt_trangthaitaixuong);

        btnTaixuongAES.setVisibility(View.INVISIBLE);
        btnTaixuongBlow.setVisibility(View.INVISIBLE);
        btnTaixuongAESBlow.setVisibility(View.INVISIBLE);
        aes = new AES();
        blowfish = new Blowfish();
    }


    private void getVideoById(String url, final int id) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i <response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                int idt = object.getInt("id");
                                if(idt == id) {
                                    stringaes = object.getString("stringaes");
                                    stringblowfish = object.getString("stringblowfish");
                                    stringaesblowfish1 = object.getString("stringaesblowfish1");
                                    stringaesblowfish2 = object.getString("stringaesblowfish2");

                                }
                                Toast.makeText(TaixuongActivity.this, idt + "", Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        btnTaixuongAES.setVisibility(View.VISIBLE);
                        btnTaixuongBlow.setVisibility(View.VISIBLE);
                        btnTaixuongAESBlow.setVisibility(View.VISIBLE);
                        txtTrangthaiTaixuong.setVisibility(View.INVISIBLE);
                        btnTaixuongAES.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(View v) {
                                String decryptedString = aes.decrypt(stringaes);
                                byte[] decodedBytes = Base64.getDecoder().decode(decryptedString);

                                try {
                                    FileOutputStream out = new FileOutputStream("/storage/emulated/0/Download/doanattt/"+ videoten + " aes"+".mp4");
                                    out.write(decodedBytes);
                                    Toast.makeText(TaixuongActivity.this, "đã tải xong", Toast.LENGTH_SHORT).show();
                                    btnTaixuongAES.setVisibility(View.INVISIBLE);
                                    out.close();
                                } catch (Exception e) {
                                    Log.d("loighifile", e.getMessage());
                                }

                            }
                        });
                        btnTaixuongBlow.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(View v) {
                                String decryptedString = blowfish.decrypt(stringblowfish);
                                byte[] decodedBytes = Base64.getDecoder().decode(decryptedString);

                                try {
                                    FileOutputStream out = new FileOutputStream("/storage/emulated/0/Download/doanattt/"+ videoten + " blowfish"+".mp4");
                                    out.write(decodedBytes);
                                    Toast.makeText(TaixuongActivity.this, "đã tải xong", Toast.LENGTH_SHORT).show();
                                    btnTaixuongBlow.setVisibility(View.INVISIBLE);
                                    out.close();
                                } catch (Exception e) {
                                    Log.d("loighifile", e.getMessage());
                                }

                            }
                        });
                        btnTaixuongAESBlow.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(View v) {
                                String decryptedStringaes = aes.decrypt(stringaesblowfish1);
                                String decryptedStringblow = blowfish.decrypt(stringaesblowfish2);
                                String decryptedString = decryptedStringaes + decryptedStringblow;
                                byte[] decodedBytes = Base64.getDecoder().decode(decryptedString);

                                try {
                                    FileOutputStream out = new FileOutputStream("/storage/emulated/0/Download/doanattt/"+ videoten + " aes + blowfish"+".mp4");
                                    out.write(decodedBytes);
                                    Toast.makeText(TaixuongActivity.this, "đã tải xong", Toast.LENGTH_SHORT).show();
                                    btnTaixuongAESBlow.setVisibility(View.INVISIBLE);
                                    out.close();
                                } catch (Exception e) {
                                    Log.d("loighifile", e.getMessage());
                                }

                            }
                        });

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TaixuongActivity.this, error.toString() + "thu2", Toast.LENGTH_SHORT).show();
            }
        } );
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 20,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }


}
