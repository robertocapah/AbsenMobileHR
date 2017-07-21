package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by arick.anjasmara on 22/06/2017.
 */

public class VolleyUtils {
    public void makeJsonObjectRequest(final Activity activity, String strLinkAPI, final String mRequestBody, String progressBarType, final VolleyResponseListener listener) {

        ProgressDialog Dialog = new ProgressDialog(activity);
//        Dialog.setCancelable(false);
//        Dialog.show();
        Dialog = ProgressDialog.show(activity, "",
                progressBarType, true);
        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;
        StringRequest req = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                finalDialog.dismiss();
                listener.onResponse(response, status, errorMessage);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                finalDialog1.dismiss();
                listener.onError(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("txtParam", mRequestBody);
                return params;
            }
        };
        req.setRetryPolicy(new
                DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(req);
    }
    public void makeJsonObjectRequestPushData(Service service, String strLinkAPI, final String mRequestBody, final VolleyResponseListener listener) {
        StringRequest req = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response, status, errorMessage);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error.getMessage());
            }
        }) {
            /*@Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("txtParam", mRequestBody);
                return params;
            }*/

            @Override
            public byte[] getBody() throws AuthFailureError {
                String str = mRequestBody;
                return super.getBody();
            }

            @Override
            public String getPostBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        req.setRetryPolicy(new
                DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(service.getApplicationContext());
        queue.add(req);
    }


}
