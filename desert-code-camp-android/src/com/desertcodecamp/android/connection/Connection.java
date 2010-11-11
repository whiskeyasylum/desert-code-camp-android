package com.desertcodecamp.android.connection;

import java.io.IOException;
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

import com.desertcodecamp.android.DesertCodeCampApplication;
import com.desertcodecamp.android.stubs.StubbedConnection;

public class Connection
{
    public static final String ERROR_FAILURE_TO_CONNECT_TO_SERVER = "{\"error\": \"Failure to connect to server\"}";
    public static String DEFAULTSERVER = DesertCodeCampApplication.SERVER;

    private HttpClient httpClient;
    private Request request;
    public String server;

    protected Connection(String server, Request request)
    {
    	this.request = request;
    	this.server = server;
        this.httpClient = new DefaultHttpClient();
    }
    
    public static Connection build(Request request)
    {
    	return build(DEFAULTSERVER, request);
    }

    public static Connection build(String server, Request request)
    {
        if (DesertCodeCampApplication.DEBUG)
            return new StubbedConnection(server, request);
        else
            return new Connection(server, request);
    }

    /**
     * Executes an http request to the Server response to the handler provided in the 
     * constructor. Passes in the remember token if the user is already authenticated.
     * 
     * @param request Request object with proper Method, path and post data 
     */
    public Response execute() throws UnsupportedEncodingException
    {
        HttpUriRequest httpRequest = getHttpRequest(request);

        if (request.canEncloseData() && request.hasData())
            httpRequest = attachPostData(request, (HttpEntityEnclosingRequestBase)httpRequest);
        else if (request.hasData())
            httpRequest = urlEncodeData(request, httpRequest);

        HttpResponse response = null;

        try {
            response = httpClient.execute(httpRequest);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Response(response);
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
}
