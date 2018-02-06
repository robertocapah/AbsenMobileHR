package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpStatus;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsPushData;
import addons.volley.VolleyMultipartRequest;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by arick.anjasmara on 22/06/2017.
 */

public class VolleyUtils {

    public void makeJsonObjectRequestToken(final Activity activity, String strLinkAPI, final String mRequestBody, String progressBarType, final VolleyResponseListener listener) {
        final ProgressDialog Dialog = new ProgressDialog(activity);
        Dialog.setMessage(progressBarType);
        Dialog.setCancelable(false);
        Dialog.show();

        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;

        StringRequest req = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
                listener.onResponse(response, status, errorMessage);
                finalDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_UNAUTHORIZED) {
                    // HTTP Status Code: 401 Unauthorized
                    Toast.makeText(activity.getApplicationContext(), "Error 401", Toast.LENGTH_SHORT).show();
                    finalDialog1.dismiss();
                    if (error.getMessage() != null) {
                        listener.onError(error.getMessage());
                    }
                } else if (networkResponse.statusCode == HttpStatus.SC_BAD_REQUEST) {
                    Toast.makeText(activity.getApplicationContext(), "Error 400 Bad Request", Toast.LENGTH_SHORT).show();
                    finalDialog1.dismiss();
                } else {
                    popup();
                    finalDialog1.dismiss();
                }
            }

            public void popup() {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);

                builder.setTitle("Request Time Out");
                builder.setMessage("You Have to request again");

                builder.setPositiveButton("REFRESH", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                android.app.AlertDialog alert = builder.create();
                alert.show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("grant_type", mRequestBody);
                return params;
            }
        };
        req.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(req);
    }

    public void makeJsonObjectRequestWithToken(final Activity activity, String strLinkAPI,final String token, final String mRequestBody, String progressBarType, final VolleyResponseListener listener) {

        ProgressDialog Dialog = new ProgressDialog(activity);
//        Dialog.setCancelable(false);
//        Dialog.show();
        final SweetAlertDialog pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(progressBarType);
        pDialog.setCancelable(false);
        pDialog.show();
        /*Dialog = ProgressDialog.show(activity, "",
                progressBarType, true); */
        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;
        StringRequest req = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
//                finalDialog.dismiss();
                listener.onResponse(response, status, errorMessage);
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                finalDialog1.dismiss();
                listener.onError(error.getMessage());
                pDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("txtParam", mRequestBody);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);

                return headers;
            }
        };
        req.setRetryPolicy(new
                DefaultRetryPolicy(700000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(req);
    }

    public void makeJsonObjectRequest(final Activity activity, String strLinkAPI, final String mRequestBody, String progressBarType, final VolleyResponseListener listener) {

        ProgressDialog Dialog = new ProgressDialog(activity);
//        Dialog.setCancelable(false);
//        Dialog.show();
        final SweetAlertDialog pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(progressBarType);
        pDialog.setCancelable(false);
        pDialog.show();
        /*Dialog = ProgressDialog.show(activity, "",
                progressBarType, true); */
        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;
        StringRequest req = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
//                finalDialog.dismiss();
                listener.onResponse(response, status, errorMessage);
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                finalDialog1.dismiss();
                listener.onError(error.getMessage());
                pDialog.dismiss();
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
                DefaultRetryPolicy(200000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(req);
    }

    public StringRequest makeJsonObjectRequest222(final Activity activity, String strLinkAPI, final String mRequestBody, String progressBarType, final VolleyResponseListener listener) {

        ProgressDialog Dialog = new ProgressDialog(activity);
//        Dialog.setCancelable(false);
//        Dialog.show();
        final SweetAlertDialog pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(progressBarType);
        pDialog.setCancelable(false);
        pDialog.show();
        /*Dialog = ProgressDialog.show(activity, "",
                progressBarType, true); */
        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;
        StringRequest req = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
//                finalDialog.dismiss();
                listener.onResponse(response, status, errorMessage);
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                finalDialog1.dismiss();
                listener.onError(error.getMessage());
                pDialog.dismiss();
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
                DefaultRetryPolicy(700000,0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        //queue.add(req);
        return req;
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
                listener.onError(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                try {
                    params.put("txtParam", mRequestBody.getDtdataJson().txtJSON().toString());
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
                if (mRequestBody.getFileUpload().get("FUAbsen-1") != null) {
                    params.put("image1", new DataPart("file_image1.jpg", mRequestBody.getFileUpload().get("FUAbsen-1"), "image/jpeg"));
                }
                if (mRequestBody.getFileUpload().get("FUAbsen-2") != null) {
                    params.put("image2", new DataPart("file_image2.jpg", mRequestBody.getFileUpload().get("FUAbsen-2"), "image/jpeg"));
                }

                return params;
            }
        };
        multipartRequest.setRetryPolicy(new
                DefaultRetryPolicy(50000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(ctx.getApplicationContext());
        queue.add(multipartRequest);
    }

    public VolleyMultipartRequest makeJsonObjectRequestPushData222(final Context ctx, String strLinkAPI, final clsPushData mRequestBody, final VolleyResponseListener listener) {

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
                listener.onError(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                try {
                    params.put("txtParam", mRequestBody.getDtdataJson().txtJSON().toString());
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
                if (mRequestBody.getFileUpload().get("FUAbsen-1") != null) {
                    params.put("image1", new DataPart("file_image1.jpg", mRequestBody.getFileUpload().get("FUAbsen-1"), "image/jpeg"));
                }
                if (mRequestBody.getFileUpload().get("FUAbsen-2") != null) {
                    params.put("image2", new DataPart("file_image2.jpg", mRequestBody.getFileUpload().get("FUAbsen-2"), "image/jpeg"));
                }

                return params;
            }
        };
        multipartRequest.setRetryPolicy(new
                DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(ctx.getApplicationContext());

        return multipartRequest;
    }

    public void makeJsonObjectRequestOutlet(final Activity activity, String strLinkAPI,final String token, final String mRequestBody, String progressBarType, final VolleyResponseListener listener) {

        ProgressDialog Dialog = new ProgressDialog(activity);
//        Dialog.setCancelable(false);
//        Dialog.show();
        final SweetAlertDialog pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(progressBarType);
        pDialog.setCancelable(false);
        pDialog.show();
        /*Dialog = ProgressDialog.show(activity, "",
                progressBarType, true); */
        final ProgressDialog finalDialog = Dialog;
        final ProgressDialog finalDialog1 = Dialog;
        StringRequest req = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Boolean status = false;
                String errorMessage = null;
//                finalDialog.dismiss();
                listener.onResponse(response, status, errorMessage);
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                finalDialog1.dismiss();
                listener.onError(error.getMessage());
                pDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("txtParam", mRequestBody);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);

                return headers;
            }
        };

        req.setRetryPolicy(new
                DefaultRetryPolicy(20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue queue = Volley.newRequestQueue(activity.getApplicationContext());
        queue.add(req);

    }
}
