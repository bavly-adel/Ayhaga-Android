package com.ctg.ayhaga;

public class Book {

    private Integer id;
    private String name;
    private String file_path;

    public Book(){


    }

    public Book(Integer id,String name,String file_path){
        this.id = id;
        this.name = name;
        this.file_path = file_path;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
