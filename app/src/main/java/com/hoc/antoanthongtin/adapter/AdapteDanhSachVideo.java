package com.hoc.antoanthongtin.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hoc.antoanthongtin.DanhsachActivity;
import com.hoc.antoanthongtin.R;
import com.hoc.antoanthongtin.UyquyenActivity;
import com.hoc.antoanthongtin.model.VideoTen;

import java.util.ArrayList;

public class AdapteDanhSachVideo extends RecyclerView.Adapter<AdapteDanhSachVideo.ViewHolder>{

    private DanhsachActivity context;
    private ArrayList<VideoTen> videoTenArrayList;

    public AdapteDanhSachVideo(DanhsachActivity context, ArrayList<VideoTen> videoTenArrayList) {
        this.context = context;
        this.videoTenArrayList = videoTenArrayList;
    }

    public AdapteDanhSachVideo(ArrayList<VideoTen> videoTenArrayList) {
        this.videoTenArrayList = videoTenArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_video, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final VideoTen videoTen = videoTenArrayList.get(position);
        TextView txtTenVideo = holder.txtTenVideo;

        ImageView imgShare = holder.imgShare;
        txtTenVideo.setText(videoTen.getNameVideo());
        imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UyquyenActivity.class);
                intent.putExtra("idvideo", videoTen.getId());
                intent.putExtra("videoname", videoTen.getNameVideo());
                intent.putExtra("username", context.username);


                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoTenArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenVideo;
        ImageView imgShare;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenVideo = itemView.findViewById(R.id.txt_videoname);
            imgShare = itemView.findViewById(R.id.img_share);
        }
    }
}
