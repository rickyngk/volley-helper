package vietnamworks.com.volleyhelper;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by duynk on 1/21/16.
 */
//http://01siddharth.blogspot.com/2014/08/creating-custom-volley-request-to-send.html

public class MultipartRequest  extends Request<String> {

    private Response.Listener < String > mListener = null;
    private Response.ErrorListener mEListener;
    //
    private final File mFilePart;
    private final String mStringPart;
    private Map< String, String > parameters;
    private Map < String, String > header;
    MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();

    @Override
    public Map < String, String > getHeaders() throws AuthFailureError {
        return header;
    }
    public MultipartRequest(String url, Response.ErrorListener eListener,
                        Response.Listener<String> rListener, File file, String stringPart,
                        Map<String, String> param, Map <String, String> head) {
        super(Method.POST, url, eListener);
        mListener = rListener;
        mEListener = eListener;
        mFilePart = file;
        mStringPart = stringPart;
        parameters = param;
        header = head;
        buildMultipartEntity();
    }
    @Override
    public String getBodyContentType() {
        return entityBuilder.build().getContentType().getValue();
    }
    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entityBuilder.build().writeTo(bos);
            String entityContentAsString = new String(bos.toByteArray());
            Log.e("volley", entityContentAsString);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }
    @Override
    protected void deliverResponse(String arg0) {
        // TODO Auto-generated method stub
    }
    @Override
    protected Response < String > parseNetworkResponse(NetworkResponse response) {
        // TODO Auto-generated method stub
        return null;
    }
    private void buildMultipartEntity() {
        entityBuilder.addPart(mStringPart, new FileBody(mFilePart));
        try {
            for (String key: parameters.keySet()) {
                Object obj = parameters.get(key);
                if (obj instanceof String) {
                    entityBuilder.addPart(key, new StringBody((String)obj));
                } else {
                    entityBuilder.addPart(key, new StringBody(obj.toString()));
                }
            }
        } catch (UnsupportedEncodingException e) {
            VolleyLog.e("UnsupportedEncodingException");
        }
    }
}