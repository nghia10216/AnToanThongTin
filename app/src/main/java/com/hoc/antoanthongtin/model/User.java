package com.hoc.antoanthongtin.model;

public class User {
    private int id;
    private String username;
    private String password;
    private int trangthai;
    private int idVideo;

    public User(int id, String username, String password, int trangthai, int idVideo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.trangthai = trangthai;
        this.idVideo = idVideo;
    }

    public User(int id, String username,int trangthai, int idVideo) {
        this.id = id;
        this.username = username;
        this.trangthai = trangthai;
        this.idVideo = idVideo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(int idVideo) {
        this.idVideo = idVideo;
    }
}
