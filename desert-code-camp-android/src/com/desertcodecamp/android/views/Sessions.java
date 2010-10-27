package com.desertcodecamp.android.views;

import android.app.AlertDialog;
import android.os.Bundle;

import com.desertcodecamp.android.adapters.ModelListAdapter;
import com.desertcodecamp.android.models.Session;
import com.desertcodecamp.android.util.DialogHelper;

public class Sessions extends CodeCampActivity  {

	private Session session;
	private ModelListAdapter adapter;
	private AlertDialog loadingDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Show loading dialog
		loadingDialog = DialogHelper.showLoadingDialog(this, "Loading sessions");
		
		// Start loading sessions
		connector.requestSessions();
	}
	
	@Override
	public void handleConnectorCallback(int type, Object data) {
		super.handleConnectorCallback(type, data);
		
		// Build and bind LV adapter from Session object
		session = (Session) data;
		adapter = new ModelListAdapter(session);
		setListAdapter(adapter);
		
		// Dismiss dialog
		DialogHelper.dismissDialog(loadingDialog);
	}

}
