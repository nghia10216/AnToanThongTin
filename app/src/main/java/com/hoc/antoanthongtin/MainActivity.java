package com.hoc.antoanthongtin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnTaiLen, btnThongTin, btnDuocChiaSe, btnDanhSachVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");


        btnTaiLen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TailenActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        btnThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ThongtinActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        btnDanhSachVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DanhsachActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        btnDuocChiaSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DuocchiaseActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

    }
    private void init() {
        btnTaiLen = findViewById(R.id.btn_tailen2);
        btnThongTin = findViewById(R.id.btn_thongtin);
        btnDuocChiaSe = findViewById(R.id.btn_duocchiase);
        btnDanhSachVideo = findViewById(R.id.btn_danhsachvideo);

    }
}
