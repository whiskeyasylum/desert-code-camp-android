package com.desertcodecamp.android.models;

import java.io.Serializable;
import java.util.ArrayList;
import com.desertcodecamp.android.connection.Request;

public class Model implements Serializable
{
    private static final long serialVersionUID = 2664088878505586275L;
    
    protected String GET_INSTANCE = null;
    protected String GET_ALL = null;
    protected String GET_ALL_BY_PARENT_ID = null;
    
    protected int id;

    public int getId() {
        return id;
    }
    
    public static ArrayList<Model> getAll(){
        Request requet = new Request(GET_ALL);
        
        return new ArrayList<Model>();
    }
}
