package com.stanleycen.facepunch.request.base;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import hugo.weaving.DebugLog;

/**
 * Created by scen on 2/13/14.
 * Extend this by creating a constructor that calls this ctor with the desired args
 * MAKE SURE TO CALL setParams!
 */
public class ParamStringRequest extends Request<String> {
    private final Response.Listener<String> listener;
    private final Map<String, String> params;

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    public ParamStringRequest(int method, String url, Map<String, String> params, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
        this.params = params;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String s) {
        listener.onResponse(s);
    }
}
