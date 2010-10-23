package com.desertcodecamp.android.models;

import java.util.ArrayList;

public class Model
{
    protected int id;

    public int getId() {
        return id;
    }
    
    public static ArrayList<Model> all(){
        return new ArrayList<Model>();
    }
}
