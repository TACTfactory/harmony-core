/**************************************************************************
 * RestClient.java, TACT Library
 * 
 * Copyright 2011 TACTfactory SARL
 * Description : Rest Library of TACTfactory
 * Author(s)   : Mickael Gaillard <mickael.gaillard@tactfactory.com> ,
 * License     : all right reserved
 * Create      : 2011
 * 
 **************************************************************************/
package com.tactfactory.android.lib;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

public class RestClient {
	private final String TAG = "WebService";

	public boolean debugWeb = true;
	public String FILE_TAG = "userfile";

	protected DefaultHttpClient httpClient;
	protected HttpRequest request;
    protected HttpResponse response;
    protected int statusCode;
    
    protected String serviceName;
    protected int port;
    protected String login, password;
    protected String sessionId, CSRFtoken;
 
    //The serviceName should be the name of the Service you are going to be using.
    public RestClient(String serviceName, int port){    	
        HttpParams myParams = new BasicHttpParams();
        
        // Set timeout
        myParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpConnectionParams.setConnectionTimeout(myParams, 30000);
        HttpConnectionParams.setSoTimeout(myParams, 20000);
        HttpProtocolParams.setUseExpectContinue(myParams, true);
        
        this.httpClient = new DefaultHttpClient(myParams);
        this.serviceName = serviceName;
        this.port = port;
    }
    
    public void setAuth(String login, String password) {
    	this.login = login;
    	this.password = password;
    }
    
    public String getSessionId() {
    	return this.sessionId;
    }
    
    public String getCSRFToken() {
		return this.CSRFtoken;
    }
    
	public void setCSRFtoken(String CSRFtoken) {
		this.CSRFtoken = CSRFtoken;
	}
    
    public int getStatusCode() {
    	return this.statusCode;
    }
    
    public String invoke(Verb verb, String path, JSONObject  jsonParams) throws Exception {
    	return this.invoke(verb, path, jsonParams, null);
	}
	
	public String invoke(Verb verb, String path, JSONObject  jsonParams, List<Header> headers) throws Exception {
        //EncodingTypes charset=serviceContext.getCharset();
		long startTime = 0, endTime = 0;
		String result = null;
		this.statusCode = 404;
		
        HttpResponse response = null;
        HttpHost targetHost = new HttpHost(this.serviceName, port);
        //HttpEntity entity = this.buildMultipartEntity(params);
        HttpEntity entity = null;
        if (jsonParams != null && jsonParams.has("file")) {
        	entity = this.buildMultipartEntity(jsonParams);
        }
        else {
        	entity = this.buildJsonEntity(jsonParams);
        }
        HttpRequest request = this.buildHttpRequest(verb, path, entity, headers);
        
        if (this.login != null && this.password != null) {
        	request.addHeader(BasicScheme.authenticate(
        		 new UsernamePasswordCredentials(this.login, this.password),
        		 "UTF-8", false));
        }
        
        if (!TextUtils.isEmpty(this.CSRFtoken))
        	request.addHeader("X-CSRF-Token", this.CSRFtoken);

        try {
        	if (debugWeb)
                startTime = System.currentTimeMillis();
        	
            response = this.httpClient.execute(targetHost, request);
            this.statusCode = response.getStatusLine().getStatusCode();
            
            this.readHeader(response);
            
            if (debugWeb)
            	endTime = System.currentTimeMillis();
            
            // we assume that the response body contains the error message
            HttpEntity resultEntity = response.getEntity();
            if (resultEntity != null)
            	result = EntityUtils.toString(resultEntity, HTTP.UTF_8);
                
            if (debugWeb) {
                final long endTime2 = System.currentTimeMillis();
                
                // System.out.println( "The REST response is:\n " + serviceResponse);
                Log.d(TAG, "Time taken in REST operation : "+ (endTime - startTime) + " ms. => ["+ verb +"]" + path);
                Log.d(TAG, "Time taken in service response construction : "+ (endTime2 - endTime) + " ms.");
            }

        } catch(ConnectTimeoutException e){
            Log.e(TAG, "Connection timed out. The host may be unreachable.");
            e.printStackTrace();
            
            throw new Exception(e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getCause().getMessage());
            
            throw new Exception(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            
            throw new Exception(e.getMessage());
        } finally {
            this.httpClient.getConnectionManager().shutdown();
        }
        
        return result;
	}
	
	public void clearCookies() {
        this.httpClient.getCookieStore().clear();
    }
 
    public void abort() {
        try {
            if (httpClient != null) {
                System.out.println("Abort.");
                //httpPost.abort();
            }
        } catch (Exception e) {
            System.out.println("Your App Name Here" + e);
        }
    }
    
    private void readHeader(HttpResponse responce) {
    	if (responce.containsHeader("csrf-token"))
    		this.CSRFtoken = responce.getFirstHeader("csrf-token").getValue();
    }
	
    private HttpEntity buildJsonEntity(JSONObject json) {
    	StringEntity entity = null;
    	
    	if (json != null) {
	    	try { 
				entity= new StringEntity(json.toString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	return entity;
    }
    
    private HttpEntity buildMultipartEntity(JSONObject jsonParams) {
    	MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
    	
    	try {
			String path = jsonParams.getString("file");

	    	if (debugWeb)
	    		Log.d(TAG, "key : file value :" + path);
	    	
	    	if (!TextUtils.isEmpty(path)) {
	    		// File entry
				File file = new File(path);
				FileBody fileBin = new FileBody(file, "application/octet");
				entity.addPart("file", fileBin);
				try {
					entity.addPart(
							"file", 
							new StringBody(
									path, 
									"text/plain", 
									Charset.forName( HTTP.UTF_8 )) );
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					Log.e(TAG, e.getMessage());
				}
	    	}
	    	
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
 
    	
    	return entity;
    }
    
	private HttpRequest buildHttpRequest(Verb verb, String path, HttpEntity httpEntity, List<Header> headers) {
        HttpRequest result;
        
        if (verb == Verb.GET) {
        	result = new HttpGet(path);
        	
        } else if (verb == Verb.POST) {
        	result = new HttpPost(path);
        	
        	if(httpEntity!=null)
        		((HttpPost)result).setEntity(httpEntity);
        	
        } else if (verb == Verb.DELETE) {
        	result = new HttpDelete(path);
        	
        } else {
        	result = new HttpPut(path);
        	
        	if(httpEntity!=null)
        		((HttpPut)result).setEntity(httpEntity);
        }
        
        if (result !=null) {
        	if (headers!=null){
	            for(Header header:headers)
	            	result.addHeader(header);
        	}
        } 
        
        return result;
	}
	
	public static class RequestConstants {
		public static final String HTTP="http";
		public static final String HTTPS="https";
		public static final String EMPTY_STRING = "";
		public static final String PAIR_SEPARATOR = "%3D";
		public static final String PARAM_SEPARATOR = "%26";
		public static final String CARRIAGE_RETURN = "\r\n";
	}
	
	public static enum Verb {
        GET, //HTTP GET request
        PUT, //HTTP PUT request
        POST, //HTTP POST request
        DELETE; //HTTP DELETE request
	}
}

