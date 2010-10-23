package com.desertcodecamp.android;

import android.app.Application;

public class DesertCodeCampApplication extends Application
{

    public enum MessageType {
        
    }

    public static final String TAG = "dcc";
    public static final int FATAL_ERROR = -1;
    public static final boolean DEBUG = true;
    public static final int NO_RESULT = 0;
    
    
    public static final String SERVER = "http://nov2010.desertcodecamp.com/Services/Session.svc/";
}
