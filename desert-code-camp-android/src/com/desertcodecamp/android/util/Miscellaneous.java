package com.desertcodecamp.android.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;
import com.desertcodecamp.android.DesertCodeCampApplication;
import com.integrumtech.android.busybot.connection.connection.Request;

public class Miscellaneous {
    public static boolean showClipToast(Context context, Bundle data, String key) {
        Toast toast = null;

        if (data.getString(key).equals("422")) {
            toast = Toast.makeText(context, "Coupon not Clipped", 2000);
        } else {
            toast = Toast.makeText(context, "Coupon Clipped", 2000);

        }

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        return false;
    }

    public static class OnClickStartActivity implements View.OnClickListener
    {
        private Intent intent;
        private int requestCode;
        private Activity activity;

        public OnClickStartActivity(Activity activity, Class<?> activityToStart)
        {
            this(activity, activityToStart, DesertCodeCampApplication.NO_RESULT);
        }

        public OnClickStartActivity(Activity activity, Class<?> activityToStart, int requestCode)
        {
            this.activity = activity;
            this.requestCode = requestCode;
            intent = new Intent(activity, activityToStart);
        }

        public void onClick(View view)
        {
            if (requestCode == DesertCodeCampApplication.NO_RESULT)
                activity.startActivity(intent);
            else
                activity.startActivityForResult(intent, requestCode);
        }
        
    }
    
    public static String convertStreamToString(InputStream is) throws IOException
    {
        /*
         * To convert the InputStream to String we use the
         * BufferedReader.readLine() method. We iterate until the BufferedReader
         * return null which means there's no more data to read. Each line will
         * appended to a StringBuilder and returned as String.
         */
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                is.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }
    
    public static Request destroyClippedCouponRequest(int couponId) {
        String path = "clipped_coupons/" + couponId + ".json";
        return new Request(path, Request.Method.DELETE);
    }
    
}
