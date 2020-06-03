package com.hoc.antoanthongtin.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hoc.antoanthongtin.R;
import com.hoc.antoanthongtin.UyquyenActivity;
import com.hoc.antoanthongtin.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapteUser extends RecyclerView.Adapter<AdapteUser.ViewHolder>{
    String urlUpdateTrangThaiIdSV = "http://192.168.0.103/DoanATTT/updatetrangthaiidvideo.php";
    private UyquyenActivity context;
    private ArrayList<User> userArrayList;


    public AdapteUser(UyquyenActivity context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    public AdapteUser(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_user_uyquyen, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = userArrayList.get(position);
        TextView txtTenUser = holder.txtUsernameUyquyen;
        CheckBox checkBox = holder.ckbUyQuyen;
        txtTenUser.setText(user.getUsername());

        boolean t = false;
        if(user.getTrangthai() != 0 && (user.getIdVideo() == context.idVideo) && (user.getUsername().equals(context.username)) ) {
            t = true;
        }

        checkBox.setChecked(t);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    capnhatUyQuyen(urlUpdateTrangThaiIdSV, user.getId(), 1, context.idVideo);
                } else {
                    capnhatUyQuyen(urlUpdateTrangThaiIdSV, user.getId(), 0, 0);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtUsernameUyquyen;
        CheckBox ckbUyQuyen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUsernameUyquyen = itemView.findViewById(R.id.txt_usernameuyquyen);
            ckbUyQuyen = itemView.findViewById(R.id.ckb_uyqyen);
        }
    }
    private void capnhatUyQuyen(String url, final int id, final int trangthai, final int idVideo ) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success")) {
                    Toast.makeText(context, "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Lỗi cập nhật trạng thái", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Loi ket noi Update", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();

                param.put("id", String.valueOf(id));
                param.put("trangthai", String.valueOf(trangthai));
                param.put("idvideo", String.valueOf(idVideo));

                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
