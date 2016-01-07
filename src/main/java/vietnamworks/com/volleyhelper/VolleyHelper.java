package vietnamworks.com.volleyhelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import R.helper.Callback;
import R.helper.CallbackResult;
import R.helper.CallbackSuccess;

/**
 * Created by duynk on 1/6/16.
 */
public class VolleyHelper {
    public static void request(final Context context, @NonNull String url, @Nullable final HashMap<String, String> header, @Nullable HashMap<String, String> params, final Callback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject data = null;
        if (params != null) {
            data = new JSONObject(params);
        }
        JsonObjectRequest req = new JsonObjectRequest(url, data,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (callback != null) {
                            callback.onCompleted(context, new CallbackSuccess(response));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int statusCode = -1;
                        if (error.networkResponse != null) {
                            statusCode = error.networkResponse.statusCode;
                        }
                        if (callback != null) {
                            callback.onCompleted(context, new CallbackResult(new CallbackResult.CallbackError(statusCode, error.getMessage())));
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (header == null) {
                    return new HashMap<>();
                } else {
                    return header;
                }
            }
        };
        queue.add(req);
    }

    public static void jsonRequestArrayResponse(final Context context, @NonNull String url, @Nullable final HashMap<String, String> header, @Nullable HashMap<String, String> params, final Callback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject data = null;
        if (params != null) {
            data = new JSONObject(params);
        }
        JsonRequestArrayResponse req = new JsonRequestArrayResponse(url, data,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (callback != null) {
                            callback.onCompleted(context, new CallbackSuccess(response));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int statusCode = -1;
                        if (error.networkResponse != null) {
                            statusCode = error.networkResponse.statusCode;
                        }
                        if (callback != null) {
                            callback.onCompleted(context, new CallbackResult(new CallbackResult.CallbackError(statusCode, error.getMessage())));
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (header == null) {
                    return new HashMap<>();
                } else {
                    return header;
                }
            }
        };
        queue.add(req);
    }

    public static void stringRequest(final Context context, @NonNull String url, @Nullable final HashMap<String, String> header, @Nullable final HashMap<String, String> params, final Callback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        int method = params == null?Request.Method.GET:Request.Method.POST;
        StringRequest req = new StringRequest(method, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (callback != null) {
                            callback.onCompleted(context, new CallbackSuccess(response));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int statusCode = -1;
                        if (error.networkResponse != null) {
                            statusCode = error.networkResponse.statusCode;
                        }
                        if (callback != null) {
                            callback.onCompleted(context, new CallbackResult(new CallbackResult.CallbackError(statusCode, error.getMessage())));
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (header == null) {
                    return new HashMap<>();
                } else {
                    return header;
                }
            }
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        queue.add(req);
    }

    public static void get(final Context context, @NonNull String url, @Nullable final HashMap<String, String> header, final Callback callback) {
        request(context, url, header, null, callback);
    }

    public static void get(final Context context, @NonNull String url, final Callback callback) {
        request(context, url, null, null, callback);
    }

    public static void post(final Context context, @NonNull String url, @Nullable final HashMap<String, String> header, @Nullable HashMap<String, String> params, final Callback callback) {
        request(context, url, header, new HashMap<String, String>(), callback);
    }

    public static void post(final Context context, @NonNull String url, @Nullable final HashMap<String, String> header, final Callback callback) {
        request(context, url, header, new HashMap<String, String>(), callback);
    }

    public static void post(final Context context, @NonNull String url, final Callback callback) {
        request(context, url, null, new HashMap<String, String>(), callback);
    }


}
