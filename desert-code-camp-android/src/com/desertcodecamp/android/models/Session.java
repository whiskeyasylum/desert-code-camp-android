package com.desertcodecamp.android.models;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONException;

public class Session extends Model
{
	private static final long serialVersionUID = -6995292590154134276L;

    protected static String GET_ALL = "GetSessionsByCampId?campId=2";
    
    String name;
    
    public Session(String json) throws JSONException {
    	super(json);
    	this.name = jsonObject.getString("Name");
    }
    
    @Override
    public String toString()
    {
        return name;
    }
    
    public static <T extends Model> ArrayList<T> getAll() throws UnsupportedEncodingException, JSONException{
		return getAll(GET_ALL, Session.class);
	}
}
