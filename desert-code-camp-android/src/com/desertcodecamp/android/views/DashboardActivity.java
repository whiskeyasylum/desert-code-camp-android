package com.desertcodecamp.android.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.desertcodecamp.android.R;

public class DashboardActivity extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        startActivity(new Intent(this, Sessions.class));
    }
	
}