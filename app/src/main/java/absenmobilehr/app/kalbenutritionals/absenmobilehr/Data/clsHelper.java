package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsPushData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsTrackingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmCounterData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.dataJson;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsAbsenDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsTrackingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsUserLoginRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsmCounterDataRepo;

//import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Robert on 20/06/2017.
 */

public class clsHelper {
    private static final String TAG = "MainActivity";
    clsHardCode _path = new clsHardCode();

    public String writeToSD(Context context) throws IOException {
        File sd = Environment.getExternalStorageDirectory();
String status = null;
        if (sd.canWrite()) {
            final String CURRENT_DATABASE_PATH = "/data/data/"+context.getPackageName()+"/databases/";
            String currentDBPath = _path.dbName;
            String backupDBPath = "/sdcard/"+_path.dbName;
            File currentDB = new File(CURRENT_DATABASE_PATH, currentDBPath);
            File backupDB = new File(sd, backupDBPath);

            if (currentDB.exists()) {
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                status = "bisa cuy";
                src.close();
                dst.close();
            }else{
                status = "ga bisa ah";
            }
        }
        return status;
    }
    public String copydb(Context context) throws  IOException{
        String CURRENT_DATABASE_PATH = "data/data/" + context.getPackageName() + "/databases/" + new DatabaseHelper(context).getDatabaseName();

        try {
            File dbFile = new File(CURRENT_DATABASE_PATH);
            FileInputStream fis = new FileInputStream(dbFile);
            String txtPathUserData= Environment.getExternalStorageDirectory()+File.separator+"backupDb";
            File yourFile = new File(txtPathUserData);
            yourFile.createNewFile();
            OutputStream output = new FileOutputStream(yourFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            fis.close();
        } catch (Exception e) {
            String s= "hahaha";
        }

        return "hehe";
    }

    public String copyDataBase(Context context) throws IOException {
        final String CURRENT_DATABASE_PATH = "/data/data/"+context.getPackageName()+"/databases/"+_path.dbName;
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();

        //Open your local db as the input stream
        if(new File(CURRENT_DATABASE_PATH).exists()) {
            File currentDB = new File(data, CURRENT_DATABASE_PATH);
            // database access code comes here
        } else {
            // database does not exists. Create it!
            // create directories if needed
            // and so on
        }
        InputStream myInput = context.getAssets().open(CURRENT_DATABASE_PATH);

        // Path to the just created empty db
        String outFileName = "/sdcard/Android/absenmobilehr.app.kalbenutritionals.absenmobilehr/databases/" + _path.dbName;

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
        return outFileName;
    }
    public clsPushData pushData(String versionName, Context context){
        clsPushData dtclsPushData = new clsPushData();
        dataJson dtPush = new dataJson();
        clsUserLoginRepo repo = new clsUserLoginRepo(context);
        HashMap<String, byte[]> FileUpload = null;
        if (repo.getContactCount(context) > 0) {
            clsUserLogin dataLogin = repo.getDataLogin(context);
            dtPush.set_txtVersionApp(versionName);
            dtPush.set_txtUserId(dataLogin.getTxtUserID());
//            dtPush.set_txtSessionLoginId(dataLogin.);
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                clsmCounterDataRepo _mCounterNumberRepo = new clsmCounterDataRepo(context);
                clsmCounterData _data = new clsmCounterData();
                _data.setIntId(enumCounterData.MonitorSchedule.getidCounterData());
                _data.setTxtDeskripsi("value menunjukan waktu terakhir menjalankan services");
                _data.setTxtName("Monitor Service");
                _data.setTxtValue(dateFormat.format(cal.getTime()));
                _mCounterNumberRepo.createOrUpdate(_data);

                //new clsInit().PushData(db,versionName);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            clsTrackingDataRepo _trackingLocationRepo = new clsTrackingDataRepo(context);
            clsAbsenDataRepo _clsAbsenDataRepo = new clsAbsenDataRepo(context);
            clsUserLoginRepo _clsLoginRepo = new clsUserLoginRepo(context);
            List<clsTrackingData> ListOfTrackingLocation = _trackingLocationRepo.getAllDataToPushData(context);
            List<clsAbsenData> ListOftAbsenUserData = _clsAbsenDataRepo.getAllDataToPushData(context);
            List<clsUserLogin> ListOftLoginData  = _clsLoginRepo.getAllDataToPushData(context);


            FileUpload = new HashMap<String, byte[]>();
            if (ListOftAbsenUserData != null) {
                dtPush.setListOftAbsenUserData(ListOftAbsenUserData);
                for (clsAbsenData dttAbsenUserData : ListOftAbsenUserData) {
                    if (dttAbsenUserData.getTxtImg1() != null) {
                        FileUpload.put("FUAbsen-1", dttAbsenUserData.getTxtImg1());
                    }
                    if (dttAbsenUserData.getTxtImg2() != null) {
                        FileUpload.put("FUAbsen-2", dttAbsenUserData.getTxtImg2());
                    }
                }
            }
            if (ListOfTrackingLocation != null){
                dtPush.setListOfTrackingLocationData(ListOfTrackingLocation);
            }
            if (ListOftLoginData != null){
                dtPush.setListDatatUserLogin(ListOftLoginData);
            }

        }else {
            dtPush = null;
        }
        dtclsPushData.setDtdataJson(dtPush);
        dtclsPushData.setFileUpload(FileUpload);
        return dtclsPushData;
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public JSONArray callPushDataReturnJson(String versionName, String strJson, HashMap<String, byte[]> ListOfDataFile)throws Exception {
        String txtMethod = "PushDataHRMobile";
//        RequestQueue queue = Volley.newRequestQueue(context);
        String strLinkAPI = "http://10.171.11.87:8010/VisitPlan/API/VisitPlanAPI/CheckVersionApp_J";
        /*JSONObject resJson = new JSONObject();
        try {
            resJson.put("TxtVersion", pInfo.versionName);
            resJson.put("TxtNameApp", "Android - Call Plan BR Mobile");
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        clsHelper _help = new clsHelper();
        final String mRequestBody = strJson;
        clsHelper _clsHelper = new clsHelper();
        final JSONArray[] jsonArray = {new JSONArray()};
//        String TimeOut = dataAPI.get_txtValue();
        String JsonData = _help.PushDataWithFile(strLinkAPI, strJson, 5 , ListOfDataFile);
        /*new VolleyUtils().makeJsonObjectRequestPushData(activity, strLinkAPI, mRequestBody, new VolleyResponseListener() {
            @Override
            public void onError(String response) {
//                new clsMainActivity().showCustomToast(get, response, false);
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                if (response != null) {
                    try {
                        jsonArray[0] = new JSONArray(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/
        return jsonArray[0];
    }
    public String PushDataWithFile(String urlToRead,String DataJson,Integer intTimeOut,HashMap<String,byte[]> ListOfDataFile){
        String charset = "UTF-8";

        String requestURL = urlToRead;
        String Result="";
        clsHelper _clsClsHelper = new clsHelper();

        File folder = new File(Environment.getExternalStorageDirectory().toString() + "/data/data/Kalbespgmobile2/tempdata");
        folder.mkdir();

        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset,intTimeOut);

            //multipart.addHeaderField("User-Agent", "CodeJava");
            //multipart.addHeaderField("DataHeader", DataJson);

            multipart.addFormField("dataField",DataJson);
            //multipart.addFormField("keywords", "Java,upload,Spring");

            for(Map.Entry<String, byte[]> entry : ListOfDataFile.entrySet()) {
                String key = entry.getKey();
//                String value = entry.getValue();

                byte [] array = entry.getValue();
                File file = File.createTempFile("image-", ".jpg", new File(Environment.getExternalStorageDirectory().toString() + "/data/data/Kalbespgmobile2/tempdata"));
                FileOutputStream out = new FileOutputStream( file );
                out.write( array );
                out.close();

                multipart.addFilePart(key, new File(file.getAbsolutePath()));
            }
            List<String> response = multipart.finish();
            //System.out.println("SERVER REPLIED:");

            for (String line : response) {
                Result+=line;
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }

        if (folder.isDirectory())
        {
            String[] children = folder.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(folder, children[i]).delete();
            }
            folder.delete();
        }

        return _clsClsHelper.ResultJsonData(Result);
    }
    public String ResultJsonData(String dt){
        return dt.substring(16,dt.length()-2);
    }
    /*public String volleyImplement(final Context context, final String mRequestBody, String strLinkAPI, Activity activity){
        RequestQueue queue = Volley.newRequestQueue(context);
        final String[] ret = {null};
        final ProgressDialog Dialog = new ProgressDialog(activity);
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
                        *//*if (result.equals("1")){
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
                        }*//*


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
    }*/




}

