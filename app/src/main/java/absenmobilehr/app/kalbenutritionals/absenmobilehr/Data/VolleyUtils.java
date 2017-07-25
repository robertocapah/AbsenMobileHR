package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsPushData;
import addons.volley.VolleyMultipartRequest;

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
    public void makeJsonObjectRequestPushData(final Context ctx, String strLinkAPI, final clsPushData mRequestBody, final VolleyResponseListener listener) {

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response.toString(), status, errorMessage);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                try {
                    params.put("txtParams", mRequestBody.getDtdataJson().txtJSON().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                if (mRequestBody.getFileUpload().get("FUAbsen-1") != null){
                    params.put("image1", new DataPart("file_image1.jpg", mRequestBody.getFileUpload().get("FUAbsen-1"), "image/jpeg"));
                }
                if (mRequestBody.getFileUpload().get("FUAbsen-2") != null){
                    params.put("image2", new DataPart("file_image2.jpg", mRequestBody.getFileUpload().get("FUAbsen-2"), "image/jpeg"));
                }

                return params;
            }
        };
        multipartRequest.setRetryPolicy(new
                DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(ctx.getApplicationContext());
        queue.add(multipartRequest);
    }


}
