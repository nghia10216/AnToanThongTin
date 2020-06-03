package com.hoc.antoanthongtin.model;

public class VideoTen{
    private int id;
    private String nameVideo;
    private String username;


    public VideoTen(int id, String nameVideo, String username) {
        this.id = id;
        this.nameVideo = nameVideo;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameVideo() {
        return nameVideo;
    }

    public void setNameVideo(String nameVideo) {
        this.nameVideo = nameVideo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}