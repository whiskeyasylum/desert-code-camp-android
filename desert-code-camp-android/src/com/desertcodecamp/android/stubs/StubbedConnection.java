package com.desertcodecamp.android.stubs;

import java.io.UnsupportedEncodingException;
import android.os.Handler;
import com.desertcodecamp.android.DesertCodeCampApplication;
import com.desertcodecamp.android.connection.Connection;
import com.desertcodecamp.android.connection.Request;

public class StubbedConnection extends Connection
{

    public StubbedConnection(Handler uiThread, String server)
    {
        super(uiThread, server);
    }

    @Override
    public String execute(String requestCode, Request request) throws UnsupportedEncodingException
    {
        switch (DesertCodeCampApplication.MessageType.valueOf(requestCode)) {
            default:
                return super.execute(requestCode, request);
        }
    }

}
