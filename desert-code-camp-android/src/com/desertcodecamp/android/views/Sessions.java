package com.desertcodecamp.android.views;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.json.JSONException;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import com.desertcodecamp.android.DesertCodeCampApplication;
import com.desertcodecamp.android.models.Session;
import com.desertcodecamp.android.util.DialogHelper;

public class Sessions extends CodeCampActivity
{

    private static final String SESSIONS = "sessions";

    private AlertDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        AsyncTask<String, Integer, Bundle> asyncTask = new AsyncTask<String, Integer, Bundle>() {

            @Override
            protected Bundle doInBackground(String... params)
            {
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
            protected void onPostExecute(Bundle result)
            {
                if (result.containsKey(SESSIONS)) {
                    ArrayList<Session> sessions = (ArrayList<Session>)result.getSerializable(SESSIONS);

                    ArrayAdapter<Session> adapter = new ArrayAdapter<Session>(Sessions.this, android.R.layout.simple_list_item_1, android.R.id.text1, sessions);

                    setListAdapter(adapter);

                    if (loadingDialog.isShowing())
                        loadingDialog.dismiss();
                }
            }
        };

        loadingDialog = DialogHelper.showLoadingDialog(this, "Loading sessions");
        asyncTask.execute();

    }
}
