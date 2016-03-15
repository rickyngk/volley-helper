package com.R.volleyhelper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by duynk on 1/21/16.
 */
//http://01siddharth.blogspot.com/2014/08/creating-custom-volley-request-to-send.html

public class MultipartRequest  extends StringRequest {
    private Map<String, String> header;
    HttpEntity entity;

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return header;
    }
    public MultipartRequest(String url,
                            Response.Listener<String> rListener,
                            Response.ErrorListener eListener,
                            @Nullable HashMap <String, String> head,
                            @NonNull String stringPart, @NonNull File file,
                            @Nullable Map<String, Object> param) {
        super(Method.POST, url, rListener, eListener);

        header = null;
        if (head == null) {
            header = new HashMap<>();
        } else {
            header = (HashMap)head.clone();
        }

        String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";
        header.put("Content-Type", "multipart/form-data; boundary=" + boundary);

        //build multi-part
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.setBoundary(boundary);
        entityBuilder.addPart(stringPart, new FileBody(file));
        if (param != null) {
            try {
                for (String key : param.keySet()) {
                    Object obj = param.get(key);
                    String input = obj.toString();
                    entityBuilder.addPart(key, new StringBody(input, ContentType.MULTIPART_FORM_DATA));
                }
            } catch (Exception e) {
                VolleyLog.e("Fail to build multipart. " + e.getMessage());
                e.printStackTrace();
            }
        }
        entity = entityBuilder.build();
    }
    @Override
    public String getBodyContentType() {
        return entity.getContentType().getValue();
    }
    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
            String entityContentAsString = new String(bos.toByteArray());
            Log.e("volley", entityContentAsString);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String ret = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(ret, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception E) {
            return Response.error(new ParseError(E));
        }
    }
}