package com.desertcodecamp.android.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Set;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.desertcodecamp.android.DesertCodeCampApplication;
import com.desertcodecamp.android.stubs.StubbedConnection;
import com.desertcodecamp.android.util.Miscellaneous;

public class Connection
{
    public static final String ERROR_FAILURE_TO_CONNECT_TO_SERVER = "{\"error\": \"Failure to connect to server\"}";
    public static String DEFAULTSERVER = DesertCodeCampApplication.SERVER;

    private Handler uiThread;
    private Thread connectionThread;

    private HttpClient httpClient;
    public String server;

    protected Connection(Handler uiThread, String server)
    {
        this.uiThread = uiThread;
        this.httpClient = new DefaultHttpClient();
        this.server = server;
    }

    public static Connection build(Handler uiThread)
    {
        return build(uiThread, DEFAULTSERVER);
    }

    public static Connection build(Handler uiThread, String server)
    {
        if (DesertCodeCampApplication.DEBUG)
            return new StubbedConnection(uiThread, server);
        else
            return new Connection(uiThread, server);
    }

    /**
     * Executes an Http request in a separate thread. Will send a message containing
     * the Server response to the handler provided in the constructor. 
     * 
     * Passes in the remember token if the user is already authenticated.
     * 
     * @param enumerator will return this request code as the key to the server response
     * @param request Request object with proper Method, path and post data 
     */
    @SuppressWarnings("rawtypes")
    public void connect(final Enum enumerator, final Request request)
    {
        connect(enumerator.toString(), request);
    }

    /**
     * Executes an Http request in a separate thread. Will send a message containing
     * the Server response to the handler provided in the constructor. 
     * 
     * Passes in the remember token if the user is already authenticated.
     * 
     * @param requestCode will return this request code as the key to the server response
     * @param request Request object with proper Method, path and post data 
     */
    public void connect(final String requestCode, final Request request)
    {
        this.connectionThread = new Thread(new Runnable() {

            public void run()
            {
                String json;
                try {
                    json = execute(requestCode, request);
                } catch (UnsupportedEncodingException e) {
                    json = "{\"error\": \"Unsupported encoding exception\"}";
                    Log.e(DesertCodeCampApplication.TAG, e.toString());
                }
                sendString(requestCode, json);

            }
        });

        this.connectionThread.start();

    }

    public String execute(String requestCode, Request request) throws UnsupportedEncodingException
    {
        HttpUriRequest httpRequest = getHttpRequest(request);

        if (request.canEncloseData() && request.hasData())
            httpRequest = attachPostData(request, (HttpEntityEnclosingRequestBase)httpRequest);
        else if (request.hasData())
            httpRequest = urlEncodeData(request, httpRequest);

        HttpResponse response = null;
        String contentString = ERROR_FAILURE_TO_CONNECT_TO_SERVER;

        try {
            response = httpClient.execute(httpRequest);
            InputStream content = response.getEntity().getContent();

            if (response.getStatusLine().getStatusCode() < 300)
                contentString = Miscellaneous.convertStreamToString(content);

            content.close();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contentString;
    }

    private HttpUriRequest getHttpRequest(Request request)
    {
        HttpUriRequest httpRequest;

        String absoluteUrl = server + request.getPath();

        switch (request.getMethod()) {
            case POST:
                httpRequest = new HttpPost(absoluteUrl);
                break;
            case PUT:
                httpRequest = new HttpPut(absoluteUrl);
                break;
            case DELETE:
                httpRequest = new HttpDelete(absoluteUrl);
                break;
            case GET:
            default:
                httpRequest = new HttpGet(absoluteUrl);
                break;
        }

        return httpRequest;
    }

    private HttpUriRequest attachPostData(Request request, HttpEntityEnclosingRequestBase httpRequest) throws UnsupportedEncodingException
    {
        Set<String> keyset = request.keySet();
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(keyset.size());

        for (String key : keyset)
            nameValuePairs.add(getNVPFromKEy(key, request));

        httpRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        return httpRequest;
    }

    private HttpUriRequest urlEncodeData(Request request, HttpUriRequest httpRequest)
    {
        String path = request.getPath();
        Set<String> keyset = request.keySet();

        String urlData = "";

        for (String key : keyset)
            urlData += "&" + URLEncoder.encode(key) + "=" + URLEncoder.encode(request.get(key));

        String encodedPath = path + "?" + urlData.substring(1);

        Request newRequest = new Request(encodedPath, request.getMethod());
        return getHttpRequest(newRequest);

    }

    private NameValuePair getNVPFromKEy(String key, Request request)
    {
        String value = request.get(key);
        return new BasicNameValuePair(key, value);
    }

    private void sendString(String requestCode, String value)
    {
        Bundle data = new Bundle();
        data.putString(requestCode, value);

        Message message = Message.obtain();
        message.setData(data);
        uiThread.sendMessage(message);
    }
}
