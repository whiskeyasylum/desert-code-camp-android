package com.desertcodecamp.android.util;

import com.desertcodecamp.android.DesertCodeCampApplication;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.WindowManager.BadTokenException;

public class DialogHelper
{
    
    public static AlertDialog showLoadingDialog(Context context, String message)
    {
    	try {
        final ProgressDialog progressDialog = ProgressDialog.show(context, "Loading...", message, true, false);
        progressDialog.setOnKeyListener(new OnKeyListener() {

            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_BACK && progressDialog.isShowing())
                    dialog.dismiss();

                return false;
            }
        });
        progressDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        return progressDialog;
    	} catch (BadTokenException e) {
    		return new AlertDialog.Builder(context).create();
    	}
    }
    
    public static void showNotificationDialog(Context context, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        
        builder.setTitle("Digital Qpons");
        builder.setMessage(message);

        builder.setPositiveButton("Close", new OnClickListener() {

            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        tryToShowDialog(builder);
    }
    
    public static void showErrorDialog(final Activity activity, String message, final boolean fatal)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(fatal ? "Fatal Error" : "Error");
        builder.setMessage(message);
        builder.setPositiveButton("Close", new OnClickListener() {

            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                if(fatal)
                {
                    activity.setResult(DesertCodeCampApplication.FATAL_ERROR);
                    activity.finish();
                }
            }
        });

        tryToShowDialog(builder);
    }
    
    public static AlertDialog tryToShowDialog(Builder builder) {
		try {
			return builder.show();
		} catch (BadTokenException e) {
			Log.e(DesertCodeCampApplication.TAG, e.toString());
			return builder.create();
		}
	}
    
    public static void dismissDialog(Dialog dialog)
    {
        if (dialog.isShowing())
            dialog.dismiss();
    }
    
}