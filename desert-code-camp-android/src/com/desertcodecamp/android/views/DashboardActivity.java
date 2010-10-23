package com.desertcodecamp.android.views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.desertcodecamp.android.R;

public class DashboardActivity extends Activity {

	public static final String HOME_PAGE = "www.google.com";
//	public static final String HOME_PAGE = "172.16.2.195:3000";

	private WebView webView;
	private ProgressDialog progressDialog;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
		webView = (WebView) findViewById(R.id.webview);
		webView.setVerticalScrollBarEnabled(false);

		setupWebView();
    }
	
	private void showLoadingSpinner(String message) {
		progressDialog = ProgressDialog.show(this, "Loading...", message, true, false);
		progressDialog.setIcon(R.drawable.icon);
		progressDialog.setOnKeyListener(new OnKeyListener() {

			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && progressDialog.isShowing())
					dialog.dismiss();

				return false;
			}
		});
	}
	
	private void hideLoadingSpinner() {
		progressDialog.dismiss();
	}
	
	private void setupWebView() {
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
		webView.getSettings().setSupportMultipleWindows(false);
		webView.getSettings().setSupportZoom(false);

		webView.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				Toast.makeText(DashboardActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onLoadResource(WebView view, String url) {
				super.onLoadResource(view, url);
//				showLoadingSpinner(url);
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
//				hideLoadingSpinner();
			}
		});

		webView.loadUrl(HOME_PAGE);
	}
	
}