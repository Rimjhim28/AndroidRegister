package com.example.android.androidregister.model;

import java.util.ArrayList;

/**
 * Created by HP on 07-10-2017.
 */

public class Batch {
    String name;
    static ArrayList<String> batches = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Batch(String name) {
        this.name = name;
    }
}
