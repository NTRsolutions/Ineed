package com.androidtutorialpoint.ineed.proj.webservices;


import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class CustomRequest extends Request<JSONObject>{
    private Response.Listener<JSONObject> listener;
    private Map<String,String> params;

    public CustomRequest(int method, String url,Map<String,String> params,  Response.Listener<JSONObject> reponseListener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.listener=reponseListener;
        this.params=params;

    }

    @Override
    public Map<String, String> getParams() throws com.android.volley.AuthFailureError{
        return params;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString=new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }

    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }
}
