package com.desertcodecamp.android.views;

import android.app.ListActivity;
import android.os.Bundle;

import com.desertcodecamp.android.connection.CodeCampConnector;
import com.desertcodecamp.android.connection.ConnectorCallback;

public class CodeCampActivity extends ListActivity implements ConnectorCallback {

	protected CodeCampConnector connector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		connector = new CodeCampConnector(this);
	}
	
	@Override
	public void handleConnectorCallback(int type, Object data) {
	}

}
