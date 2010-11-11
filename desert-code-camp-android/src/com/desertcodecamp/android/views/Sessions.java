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
		
	}
}
