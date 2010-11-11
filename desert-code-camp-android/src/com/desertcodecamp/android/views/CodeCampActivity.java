package com.desertcodecamp.android.views;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.desertcodecamp.android.DesertCodeCampApplication;
import com.desertcodecamp.android.models.Session;

public class CodeCampActivity extends ListActivity {

	private static final String SESSIONS = "sessions";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AsyncTask<String, Integer, Bundle> asyncTask = new AsyncTask<String, Integer, Bundle>() {

			@Override
			protected Bundle doInBackground(String... params) {
				Bundle bundle = new Bundle();

				try {
					ArrayList<Session> sessions = Session.getAll();
					bundle.putSerializable(SESSIONS, sessions);
				} catch (UnsupportedEncodingException e) {
					Log.e(DesertCodeCampApplication.TAG, e.getStackTrace().toString());
				} catch (JSONException e) {
					Log.e(DesertCodeCampApplication.TAG, e.getStackTrace().toString());
				}

				return bundle;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void onPostExecute(Bundle result) {
				if (result.containsKey(SESSIONS)) {
					ArrayList<Session> sessions = (ArrayList<Session>) result.getSerializable(SESSIONS);

					for (Session session : sessions)
						Log.d(DesertCodeCampApplication.TAG, session.toString());
				}
			}
		};

		asyncTask.execute();
	}
}
