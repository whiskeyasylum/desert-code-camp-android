package com.desertcodecamp.android.models;

public class Session extends Model
{
    private static final long serialVersionUID = -6995292590154134276L;

    protected String GET_ALL = "GetSessionsByCampId?campId=2";
    
    String name;
    
    @Override
    public String toString()
    {
        return name;
    }
}
