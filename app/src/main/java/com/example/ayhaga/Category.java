package com.example.ayhaga;

public class Category {

    private String name;
    private String img_url;

    public Category(){

    }

    public Category(String name, String img_url) {
        this.name = name;
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
