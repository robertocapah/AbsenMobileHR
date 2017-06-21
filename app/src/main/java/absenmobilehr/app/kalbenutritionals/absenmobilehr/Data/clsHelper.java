package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Robert on 20/06/2017.
 */

public class clsHelper {
    private static final String TAG = "MainActivity";
    clsHardCode _path = new clsHardCode();
    public void copyDataBase(Context context) throws IOException {
        final String CURRENT_DATABASE_PATH = "data/data/" + context.getPackageName() + "/databases/" + _path.dbName;

        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(CURRENT_DATABASE_PATH);

        // Path to the just created empty db
        String outFileName = "/data/data/absenmobilehr.app.kalbenutritionals.absenmobilehr/databases/" + _path.dbName;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }
    public String volleyImplement(final Context context, final String mRequestBody, String strLinkAPI, Activity activity){
        RequestQueue queue = Volley.newRequestQueue(context);
        final String[] ret = {null};
        final SweetAlertDialog Dialog = new SweetAlertDialog(activity);
        Dialog.setTitleText("Loading");
        Dialog.show();
//        JSONObject obj = null;
        StringRequest req = new StringRequest(Request.Method.POST, strLinkAPI, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                if (response != null){
                    try {
                        JSONObject jsonObject1 = new JSONObject(response);
                        JSONObject jsonObject2 = jsonObject1.getJSONObject("validJson");

                        String result = jsonObject2.getString("TxtResult");
                        String txtWarn = jsonObject2.getString("TxtWarn");
                        /*if (result.equals("1")){
                            JSONObject jsonObject3 = jsonObject2.getJSONObject("TxtData");
                            String txtGUI = jsonObject3.getString("TxtGUI");
                            String txtNameApp = jsonObject3.getString("TxtNameApp");
                            String txtVersion = jsonObject3.getString("TxtVersion");
                            String txtFile = jsonObject3.getString("TxtFile");
                            String bitActive = jsonObject3.getString("BitActive");
                            String txtInsertedBy = jsonObject3.getString("TxtInsertedBy");
//                            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                            String imeiNumber = tm.getDeviceId();
                            clsDeviceInfo data = new clsDeviceInfo();
                            data.setTxtGUI(txtGUI);
                            data.setTxtNameApp(txtNameApp);
                            data.setTxtDevice(android.os.Build.DEVICE);
                            data.setTxtFile(txtFile);
                            data.setTxtVersion(txtVersion);
                            data.setBitActive(bitActive);
                            data.setTxtInsertedBy(txtInsertedBy);
                            data.setIdDevice(imeiNumber);
                            data.setTxtModel(android.os.Build.MANUFACTURER+" "+android.os.Build.MODEL);
                            repo =new clsDeviceInfoRepo(context);
                            int i = 0;
                            try {
                                i = repo.createOrUpdate(data);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            if(i > -1)
                            {
                                Log.d("Data info", "Data info berhasil di simpan");
                            }
                        }else{
                            Toast.makeText(context, txtWarn, Toast.LENGTH_SHORT).show();
                        }*/


//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject explrObject = jsonArray.getJSONObject(i);
//                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("txtParam",mRequestBody);
                return params;
            }
        };
        req.setRetryPolicy(new
                DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
        return ret[0];
    }


}
