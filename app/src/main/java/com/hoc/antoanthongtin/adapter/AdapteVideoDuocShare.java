package com.hoc.antoanthongtin.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hoc.antoanthongtin.DuocchiaseActivity;
import com.hoc.antoanthongtin.R;
import com.hoc.antoanthongtin.TaixuongActivity;
import com.hoc.antoanthongtin.model.VideoInfo;
import com.hoc.antoanthongtin.model.VideoTen;

import java.util.ArrayList;

public class AdapteVideoDuocShare extends RecyclerView.Adapter<AdapteVideoDuocShare.ViewHolder>{

    private DuocchiaseActivity context;
    private ArrayList<VideoInfo> videoInfoArrayListArrayList;

    public AdapteVideoDuocShare(DuocchiaseActivity context, ArrayList<VideoInfo> videoInfoArrayListArrayList) {
        this.context = context;
        this.videoInfoArrayListArrayList = videoInfoArrayListArrayList;
    }

    public AdapteVideoDuocShare(ArrayList<VideoInfo> videoInfoArrayListArrayList) {
        this.videoInfoArrayListArrayList = videoInfoArrayListArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_video_duocchiase, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final VideoInfo videoTen = videoInfoArrayListArrayList.get(position);
        Log.d("videotenne", videoTen.getName());
        TextView txtTenVideo = holder.txtTenVideo;


        txtTenVideo.setText( videoTen.getName());

        txtTenVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TaixuongActivity.class);
                intent.putExtra("videoten", videoTen.getName());
                intent.putExtra("idvideo", videoTen.getId());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return videoInfoArrayListArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenVideo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenVideo = itemView.findViewById(R.id.txt_videonameduocchiase);

        }
    }
}

