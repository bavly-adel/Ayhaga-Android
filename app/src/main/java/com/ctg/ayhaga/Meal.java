package com.ctg.ayhaga;

import java.io.Serializable;
import java.util.List;

public class Meal implements Serializable {

    private Integer id;
    private String name;
    private String desc;
    private String ingrediants;
    private String preparation;
    private String imgurl;
    private List<String> photos;
    private Integer likes;
    private String category_id;

    public Meal(){

    }

    public Meal(Integer id, String name, String desc, String ingrediants, String preparation, String imgurl, List<String> photos, Integer likes, String category_id) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.ingrediants = ingrediants;
        this.preparation = preparation;
        this.imgurl = imgurl;
        this.photos = photos;
        this.likes = likes;
        this.category_id = category_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getIngrediants() {
        return ingrediants;
    }

    public String getPreparation() {
        return preparation;
    }

    public String getImgurl() {
        return imgurl;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setIngrediants(String ingrediants) {
        this.ingrediants = ingrediants;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
