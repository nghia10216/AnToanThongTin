package com.hoc.antoanthongtin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hoc.antoanthongtin.thuattoan.AES;
import com.hoc.antoanthongtin.thuattoan.Blowfish;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class TailenActivity extends AppCompatActivity {

    private final String urltailen = "http://192.168.0.103/DoanATTT/insertvideo.php";
    private static final int REQUEST_TAKE_GALLERY_VIDEO = 11;

    private AES aes;
    private Blowfish blowfish;


    String encryptedStringAES = "";
    String encryptedStringBlowfish = "";
    String encryptedStringAESBlowfish1AES = "";
    String encryptedStringAESBlowfish2Blowfish = "";

    String stringAB1 = "";
    String stringAB2 = "";
    private String username = "";
    private EditText edtTenVideo;
    private ImageView imgFolder;
    private TextView txtDuongdan;
    private Button btnTailen;
    private ProgressBar prgAES, prgBlowfish, prgAESBlow;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailen);

        init();
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        Log.d("ahii", username);

        imgFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(TailenActivity.this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, REQUEST_TAKE_GALLERY_VIDEO);
            }
        });
        btnTailen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtTenVideo.getText().toString().isEmpty()) {
                    Toast.makeText(TailenActivity.this, "Chưa nhập tên video", Toast.LENGTH_SHORT).show();
                } else {
                    dialogTailen();
                }

            }
        });


        Log.d("xin", encryptedStringAES);
    }
    private void init() {
        edtTenVideo = findViewById(R.id.edit_tenvideo);
        imgFolder = findViewById(R.id.img_folder);
        txtDuongdan = findViewById(R.id.txt_duongdan);
        btnTailen = findViewById(R.id.btn_tailen2);

        aes = new AES();
        blowfish = new Blowfish();

    }

    FileInputStream fis;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_GALLERY_VIDEO) {
                Uri selectedUri = data.getData();
                String duongdan = GetRealPathFromURI(selectedUri);

                txtDuongdan.setText(duongdan);
                File file = new File(duongdan);
                byte[] b = new byte[(int) file.length()];
                Log.d("dai",file.length() + "");
                try {
                    fis = new FileInputStream(file);
                    fis.read(b);
                    //mã hóa
                    String encodedString = Base64.getEncoder().encodeToString(b);
                    encryptedStringAES = aes.encrypt(encodedString);
                    Log.d("conaa", encryptedStringAES);
                    encryptedStringBlowfish = blowfish.encrypt(encodedString);
                    Log.d("habi", encryptedStringBlowfish);
                    String[] stringAB= split(encodedString);

                    stringAB1 = stringAB[0];
                    encryptedStringAESBlowfish1AES = aes.encrypt(stringAB1);
                    Log.d("shibp ", encryptedStringAESBlowfish1AES);
                    stringAB2 = stringAB[1];
                    encryptedStringAESBlowfish2Blowfish = blowfish.encrypt(stringAB2);
                    Log.d("dongke", encryptedStringAESBlowfish2Blowfish);

                } catch (Exception e) {
                    Log.d("nghia", e.getMessage());
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String GetRealPathFromURI(final Uri ac_Uri) {
        String result = "";
        boolean isok = false;

        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Video.Media.DATA};
            cursor = getContentResolver().query(ac_Uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
            isok = true;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return isok ? result : "";
    }

    private void dialogTailen() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_tailen_dialog);

        prgAES = dialog.findViewById(R.id.prg_aes);
        prgBlowfish = dialog.findViewById(R.id.prg_blow);
        prgAESBlow = dialog.findViewById(R.id.prg_aesblow);

        prgAES.setVisibility(View.INVISIBLE);
        prgBlowfish.setVisibility(View.INVISIBLE);
        prgAESBlow.setVisibility(View.INVISIBLE);


        Button btnTailenDialog = dialog.findViewById(R.id.btn_tailendialog);
        Button btnHuyDialog = dialog.findViewById(R.id.btn_huydialog);

        btnHuyDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();

            }
        });
        btnTailenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prgAES.setVisibility(View.VISIBLE);
                prgBlowfish.setVisibility(View.VISIBLE);
                prgAESBlow.setVisibility(View.VISIBLE);
                taiLen(urltailen);

            }
        });

        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_TAKE_GALLERY_VIDEO && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)  {
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            );
            startActivityForResult(i, REQUEST_TAKE_GALLERY_VIDEO);
        } else {
            Toast.makeText(this, "Bạn không cho phép", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public  String[] split(String in) {
        int lengh = 0;
        int t = 0;
        for (char c : in.toCharArray()) {
            lengh++;
        }
        System.out.println(lengh);
        t = lengh/2;
        return new String[]{in.substring(0,t), in.substring(t)};
    }

    public void writeFileOnInternalStorage(Context mcoContext, String sFileName, String sBody){
        File file = new File(mcoContext.getFilesDir(),"mydir");
        if(!file.exists()){
            file.mkdir();
        }

        try{
            File gpxfile = new File(file, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    private void taiLen(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success")) {
                    Toast.makeText(TailenActivity.this, "Tải lên thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    edtTenVideo.setText("");
                    txtDuongdan.setText("Chưa chọn video");
                } else if(response.trim().equals("error")) {
                    Toast.makeText(TailenActivity.this, "Có lỗi khi tải lên", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TailenActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();

                String tenVideo = edtTenVideo.getText().toString().trim();

                Log.d("string aes", encryptedStringAES);

                param.put("tenvideo", tenVideo);
                param.put("stringaes", encryptedStringAES);
                param.put("stringblowfish", encryptedStringBlowfish);
                param.put("stringaesblowfish1", encryptedStringAESBlowfish1AES);
                param.put("stringaesblowfish2", encryptedStringAESBlowfish2Blowfish);
                param.put("username", username);
                return param;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 20,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);

    }
}
