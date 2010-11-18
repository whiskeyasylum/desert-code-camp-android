package com.desertcodecamp.android.stubs;

import com.integrumtech.android.busybot.connection.connection.Connection;
import com.integrumtech.android.busybot.connection.connection.Request;

public class StubbedConnection extends Connection {

	public StubbedConnection(String server, Request request) {
		super(server, request);
	}

	
}