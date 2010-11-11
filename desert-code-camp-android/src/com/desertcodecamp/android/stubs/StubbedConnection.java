package com.desertcodecamp.android.stubs;

import com.desertcodecamp.android.connection.Connection;
import com.desertcodecamp.android.connection.Request;

public class StubbedConnection extends Connection {

	public StubbedConnection(String server, Request request) {
		super(server, request);
	}

	
}