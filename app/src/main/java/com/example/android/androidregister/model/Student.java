package com.example.android.androidregister.model;

import java.util.ArrayList;

/**
 * Created by HP on 07-10-2017.
 */

public class Student  {

    Integer uniqueId;
    String name;
    String batch;
    String image_url;

    public Student(Integer uniqueId, String name, String batch, String image_url) {
        this.uniqueId = uniqueId;
        this.name = name;
        this.batch = batch;
        this.image_url = image_url;
    }

    public Integer getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(Integer uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public static ArrayList<Student> getDummyStudents(){
        ArrayList<Student> students = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i++ ) {
            students.add(new Student(i, String.valueOf(i), i+"user", "www"));
        }
        return students;
    }
}
