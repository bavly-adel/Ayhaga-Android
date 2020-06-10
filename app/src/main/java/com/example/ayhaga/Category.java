package com.example.ayhaga;

public class Category {

    private String name;
    private String img_url;
    private Integer id;

    public Category(){

    }

    public Category(String name, String img_url, Integer id) {
        this.name = name;
        this.img_url = img_url;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImg_url() {
        return img_url;
    }

    public Integer getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
