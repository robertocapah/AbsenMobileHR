package absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.owater.library.CircleTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseManager;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyResponseListener;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyUtils;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.clsHardCode;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.clsHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsPushData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsTrackingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmConfig;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmVersionApp;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.dataJson;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsAbsenDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsTrackingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsUserLoginRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsmConfigRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsmVersionAppRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.enumConfigData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.MainMenu;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.R;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Service.MyServiceNative;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Splash;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.clsMainActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.android.volley.VolleyLog.TAG;

public class FragmentPushData extends Fragment {


    private TableLayout tlAbsen;
    private Button btnPush;
    private String myValue;

    View v;

    clsMainActivity _clsMainActivity = new clsMainActivity();
    Context context = null;
    PackageInfo pInfo = null;
    CircleTextView ctvStatusAbsen, ctvStatusTracking;
    SweetAlertDialog pDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        try {
            pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        if (this.getArguments() != null) {
            myValue = this.getArguments().getString("message");
        }

        v = inflater.inflate(R.layout.activity_push_data, container, false);
        ctvStatusAbsen = (CircleTextView) v.findViewById(R.id.tvTotalAbsen);
        ctvStatusTracking = (CircleTextView) v.findViewById(R.id.tvTotalTracking);

        List<clsAbsenData> absenDatas = (List<clsAbsenData>) new clsAbsenDataRepo(context).getAllDataToPushData(context);
        List<clsTrackingData> trackingDatas = (List<clsTrackingData>) new clsTrackingDataRepo(context).getAllDataToPushData(context);
        if (absenDatas != null && absenDatas.size() > 0 ){
            int count = absenDatas.size();
            ctvStatusAbsen.setText(String.valueOf(count)+" Attendance Datas");
        }else{
            ctvStatusAbsen.setText("Data Remaining to Push");
        }
        if (trackingDatas != null && trackingDatas.size()>0){
            int count = trackingDatas.size();
            ctvStatusTracking.setText(String.valueOf(count)+" Tracking Datas");
        }else{
            ctvStatusTracking.setText("No tracking data to push");
        }
        checkAbsenData();
        Intent serviceIntentMyServiceNative = new Intent(getContext(), MyServiceNative.class);
        getContext().stopService(serviceIntentMyServiceNative);

//        tlAbsen = (TableLayout) v.findViewById(R.id.tl_absen);
        btnPush = (Button) v.findViewById(R.id.btnPush);

        btnPush.setTextColor(Color.parseColor("#FFFFFF"));

        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AsyncCallRole task=new AsyncCallRole();
//                task.execute();
               pushData();
            }
        });

//        loadData();

        return v;
    }

    private void pushData() {
        Intent i = getActivity().getIntent();
        final String StrIntentPushDataFromLogin = i.getStringExtra("key_view");
        Bundle arguments = getArguments();
        final String StrFragmentFromMainMenu = arguments.getString("key_view");
        String versionName = "";
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Pushing Data");
        pDialog.setCancelable(false);
        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        pDialog.show();
        try {
            versionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (NameNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        clsPushData dtJson = new clsHelper().pushData(versionName, getActivity().getApplicationContext());
        if (dtJson == null) {
        } else {
            try {
//                pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                clsmConfig configData = null;
                String linkPushData= "";
                try {
                    configData = (clsmConfig) new clsmConfigRepo(getActivity().getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
                    linkPushData = configData.getTxtValue()+new clsHardCode().linkPushData;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String strLinkAPI = linkPushData;
                new VolleyUtils().makeJsonObjectRequestPushData(getActivity().getApplicationContext(), strLinkAPI, dtJson, new VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        String error;
                        new clsMainActivity().showCustomToast(getActivity().getApplicationContext(),"Push Data Failed, Check Your Network",false);
                        pDialog.dismiss();
                    }

                    @Override
                    public void onResponse(String response, Boolean status, String strErrorMsg) {
                        String res = response;

                        Log.i(TAG, "Ski data from server - " + response);
                        clsAbsenData absenData = new clsAbsenData();
                        clsTrackingData trackingData = new clsTrackingData();
//                clsUserLogin userLogin = new clsUserLogin();
                        try {
                            JSONArray jsonArray1 = new JSONArray(response);
                            boolean valid = false;
                            if (jsonArray1.length()>1){
                                for (int i = 0; i < jsonArray1.length(); i++) {
                                    JSONObject method = jsonArray1.getJSONObject(i);
                                    String listMethod = method.getString("PstrMethodRequest");
                                    if (listMethod.equals(trackingData.Property_ListOftrackingLocation)) {
                                        if (method.getString("pBoolValid").equals("1")) {
                                            //new DatabaseHelper(getActivity().getApplicationContext()).clearDataTracking();
                                            new clsTrackingDataRepo(context).updateAllRowTracking();
                                            valid = true;
                                        }else{
                                            valid = false;
                                        }
                                    }
                                    if (listMethod.equals(absenData.Property_ListOftAbsenUser)) {
                                        if (method.getString("pBoolValid").equals("1")) {
                                            new DatabaseHelper(getActivity().getApplicationContext()).clearDataAbsen();
                                            valid = true;
                                        }else{
                                            valid = false;
                                        }
                                    }
                                    if (StrIntentPushDataFromLogin != null && StrIntentPushDataFromLogin.equals("push_data")){
//                                        new clsMainActivity().showCustomToast(getActivity().getApplicationContext(),"Push Data Completed",true);
//                                        new clsAbsenDataRepo(getActivity().getApplicationContext()).updateAllRowAbsen();
                                        new DatabaseHelper(context).clearAllDataInDatabase();
                                        startActivity(new Intent(getActivity(),Splash.class));
                                        getActivity().finish();
//                                        logout();
                                    }else if(StrFragmentFromMainMenu != null && StrFragmentFromMainMenu.equals("main_menu")){
                                        new clsMainActivity().showCustomToast(getActivity().getApplicationContext(),"Push Data Completed",true);
                                        new clsAbsenDataRepo(getActivity().getApplicationContext()).updateAllRowAbsen();
                                        startActivity(new Intent(getActivity(),MainMenu.class));
                                        getActivity().finish();
                                    }
                                }
                            }else{
                                new clsMainActivity().showCustomToast(getActivity().getApplicationContext(),"Push Data Completed",true);

                                if (StrIntentPushDataFromLogin != null && StrIntentPushDataFromLogin.equals("push_data")){
                                    new clsAbsenDataRepo(getActivity().getApplicationContext()).updateAllRowAbsen();
                                    startActivity(new Intent(getActivity(), MainMenu.class));
                                    getActivity().finish();
                                }else if(StrFragmentFromMainMenu != null && StrFragmentFromMainMenu.equals("main_menu")){
                                    new clsTrackingDataRepo(getActivity().getApplicationContext()).updateAllRowTracking();
                                    new clsAbsenDataRepo(getActivity().getApplicationContext()).updateAllRowAbsen();
                                    startActivity(new Intent(getActivity(), MainMenu.class));
                                    getActivity().finish();
                                }

                            }
                            pDialog.dismiss();

                    /*for(Object data : jsonObject1){

                    }*/
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        /*FragmentPushData fragment = new FragmentPushData();
        fragment.getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    pDialog.dismiss();
                    return true;
                }
                return false;
            }
        });*/
    }

    public void logout(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        List<clsmVersionApp> dataInfo = null;
        List<clsUserLogin> dataLogin= null;
        clsUserLoginRepo repo = new clsUserLoginRepo(getActivity().getApplicationContext());
        clsmVersionAppRepo repoVersionInfo = new clsmVersionAppRepo(getActivity().getApplicationContext());
        try {
            dataLogin = (List<clsUserLogin>) repo.findAll();
            dataInfo = (List<clsmVersionApp>) repoVersionInfo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String linkPushData= "";
        clsmConfig configData = null;
        try {
            configData = (clsmConfig) new clsmConfigRepo(getActivity().getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
            linkPushData = configData.getTxtValue()+new clsHardCode().linkPushData;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String strLinkAPI = linkPushData;
//        String nameRole = selectedRole;
        final JSONObject resJson = new JSONObject();
        List<clsTrackingData> trackingDatas = new ArrayList<>();
        trackingDatas = new clsTrackingDataRepo(getActivity().getApplicationContext()).getAllDataToPushData(getActivity().getApplicationContext());
        try {
            resJson.put("TxtGUI_trUserLogin", dataLogin.get(0).getTxtGUI());
            resJson.put("TxtUserID", dataLogin.get(0).getTxtUserID());
            resJson.put("TxtGUI_mVersionApp", dataInfo.get(0).getTxtVersion());
            resJson.put("IntCabangID", dataLogin.get(0).getIntCabangID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = "[" + resJson.toString() + "]";
        new VolleyUtils().makeJsonObjectRequest(getActivity(), strLinkAPI, mRequestBody,"Logging Out, Please Wait !", new VolleyResponseListener() {
            @Override
            public void onError(String response) {
                new clsMainActivity().showCustomToast(getActivity().getApplicationContext(), response, false);
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                if (response != null) {
                    JSONObject jsonObject1 = null;
                    try {
                        jsonObject1 = new JSONObject(response);
                        JSONObject jsn = jsonObject1.getJSONObject("validJson");
                        String warn = jsn.getString("TxtWarn");
                        String result = jsn.getString("TxtResult");
                        if (result.equals("1")){
//                            new DatabaseHelper(getApplicationContext()).clearDataAfterLogout();
                            DatabaseHelper helper = DatabaseManager.getInstance().getHelper();
//                            getActivity().stopService(new Intent(getActivity().getApplicationContext(), MyTrackingLocationService.class));
                            helper.clearDataAfterLogout();
//                            helper.close();
                            getActivity().finish();
                            Intent nextScreen = new Intent(getActivity(), Splash.class);
                            startActivity(nextScreen);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private void checkAbsenData() {
        clsAbsenData absenDatas = new clsAbsenDataRepo(getActivity().getApplicationContext()).getDataCheckinActive(getActivity().getApplicationContext());
        if (absenDatas != null) {
            clsAbsenData clsAbsenData = absenDatas;
            clsAbsenData.setDtCheckout(_clsMainActivity.FormatDateComplete().toString());
            try {
                new clsAbsenDataRepo(getActivity().getApplicationContext()).update(clsAbsenData);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadData() {
        String versionName = "";
        try {
            versionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (NameNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        clsPushData dtclsPushData = new clsHelper().pushData(versionName, getContext());

        if (dtclsPushData != null) {
            dataJson dtJson = dtclsPushData.getDtdataJson();


            /*if (dtJson.getListOftAbsenUserData() != null) {
                inittAbsen(getContext(), dtJson.getListOftAbsenUserData());
            } else {
                inittAbsen(getContext(), null);
            }*/
        }
    }


    /*private class AsyncCallRole extends AsyncTask<List<dataJson>, Void, List<dataJson>> {
        @Override
        protected List<dataJson> doInBackground(List<dataJson>... params) {
            String versionName="";
            try {
                versionName = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionName;
//                    versionName = new clsMainActivity().getApplicationContext().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
            } catch (NameNotFoundException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
//            android.os.Debug.waitForDebugger();
            List<dataJson> roledata=new ArrayList<dataJson>();
            clsPushData dtJson= new clsHelperBL().pushData(versionName);
            dataJson dtdataJson=new dataJson();
            if(dtJson!=null){

                try {
                    JSONArray Jresult= new clsHelper().callPushDataReturnJson(versionName,dtJson.getDtdataJson().txtJSON().toString(),dtJson.getFileUpload());
                    Iterator iterator = Jresult.iterator();
                    Boolean flag = true;
                    String errorMess = "";
                    APIData dtAPIDATA = new APIData();
                    while (iterator.hasNext()){
                        JSONObject innerObj = (JSONObject) iterator.next();
                        int boolValid = Integer.valueOf(String.valueOf(innerObj.get(dtAPIDATA.boolValid)));
                        if (boolValid == Integer.valueOf(new clsHardCode().intSuccess)){
                        } else {
                            flag = false;
                            errorMess = (String) innerObj.get(dtAPIDATA.strMessage);
                            break;
                        }
                    }
                }catch (Exception e3){
                    e3.printStackTrace();
                }
                try {
                    JSONArray Jresult= new clsHelperBL().callPushDataReturnJson(versionName,dtJson.getDtdataJson().txtJSON().toString(),dtJson.getFileUpload());
                    new clsHelperBL().saveDataPush(dtJson.getDtdataJson(),Jresult);
                    dtdataJson.setIntResult("1");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    dtdataJson.setIntResult("0");
                }
            }
            else
            {
                dtdataJson.setIntResult("0");
                dtdataJson.setTxtMessage("No Data");
            }
            roledata.add(dtdataJson);
            return roledata ;
        }

        private ProgressDialog Dialog = new ProgressDialog(getContext());
        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(getContext(),
                    new clsHardCode().txtMessCancelRequest, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }
        @Override
        protected void onPostExecute(List<dataJson> roledata) {
            boolean result = false;
            if(roledata.get(0).getIntResult().equals("1")){
                new clsMainActivity().showCustomToast(getContext(), "Success Push Data", true);
                Intent myIntent = new Intent(getContext(), MainMenu.class);
                if(myValue!=null&&myValue.equals("notMainMenu")){
                    AsyncCallLogOut task = new AsyncCallLogOut();
                    task.execute();
                } else {
                    startActivity(myIntent);
                }
            }else{
                new clsMainActivity().showToast(getContext(), roledata.get(0).getTxtMessage());
            }
            Dialog.dismiss();
        }
        int intProcesscancel=0;
        @Override
        protected void onPreExecute() {
            //Make ProgressBar invisible
            //pg.setVisibility(View.VISIBLE);
            Dialog.setMessage("Syncronize Data!!");
            Dialog.setCancelable(false);
            Dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    intProcesscancel=1;
                }
            });
            Dialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            AsyncCallLogOut task = new AsyncCallLogOut();
            task.execute();
            Dialog.dismiss();
        }

    }*/

    int intProcesscancel = 0;
    /*private class AsyncCallLogOut extends AsyncTask<JSONArray, Void, JSONArray> {
        @Override
        protected JSONArray doInBackground(JSONArray... params) {
//            android.os.Debug.waitForDebugger();
            String versionName = "";
            try {
                versionName = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionName;
            } catch (NameNotFoundException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }
            JSONArray Json = null;

            try {
                List<tAbsenUserData> listAbsenData = new ArrayList<tAbsenUserData>();
                tAbsenUserData dtTabsenData = new tAbsenUserBL().getDataCheckInActive();
                if (dtTabsenData != null) {
                    dtTabsenData.set_dtDateCheckOut(_clsMainActivity.FormatDateComplete().toString());
                    dtTabsenData.set_intSubmit("1");
                    dtTabsenData.set_intSync("0");
                    listAbsenData.add(dtTabsenData);
                    new tAbsenUserBL().saveData(listAbsenData);
                }
                clsPushData dtJson = new clsHelperBL().pushData(versionName);
                if (dtJson != null) {
                    try {
                        JSONArray Jresult = null;
                        if (dtJson.getDtdataJson().getListOftAbsenUserData() != null) {
                            List<tAbsenUserData> listAbsen = dtJson.getDtdataJson().getListOftAbsenUserData();
                            if (listAbsen.get(0).get_txtAbsen().equals("0")) {
                                Jresult = new clsHelperBL().callPushDataReturnJson(versionName, dtJson.getDtdataJson().txtJSON().toString(), dtJson.getFileUpload());
                            } else {
                                Jresult = new clsHelperBL().callPushDataReturnJson(versionName, dtJson.getDtdataJson().txtJSON().toString(), null);
                            }
//                            new clsHelperBL().saveDataPush(dtJson.getDtdataJson(), Jresult);
                        }
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

                try {
                    pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
                } catch (NameNotFoundException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }

                Json = new tUserLoginBL().LogoutFromPushData(pInfo.versionName);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
            return Json;
        }

        private ProgressDialog Dialog = new ProgressDialog(getContext());

        @Override
        protected void onCancelled() {
            Dialog.dismiss();
            Toast toast = Toast.makeText(getContext(), "cancel", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();
        }

        @Override
        protected void onPostExecute(JSONArray roledata) {
            if (roledata != null) {
                Iterator i = roledata.iterator();
                while (i.hasNext()) {
                    JSONObject innerObj = (JSONObject) i.next();
                    Long IntResult = (Long) innerObj.get("_pboolValid");
                    String PstrMessage = (String) innerObj.get("_pstrMessage");

                    if (IntResult == 1) {
                        tNotificationBL _tNotificationBL = new tNotificationBL();
                        List<tNotificationData> ListData = _tNotificationBL.getAllDataWillAlert("1");
                        if (ListData != null) {
                            for (tNotificationData dttNotificationData : ListData) {
                                NotificationManager tnotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                                tnotificationManager.cancelAll();
                                ShortcutBadger.applyCount(getContext(), 0);
                                System.gc();
                            }
                        }
                        new clsHelperBL().DeleteAllDB();
                        Intent nextScreen = new Intent(getContext(), Splash.class);
                        startActivity(nextScreen);
                        getActivity().finish();
                    } else {
                        Toast toast = Toast.makeText(getContext(),
                                PstrMessage, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 25, 400);
                        toast.show();
                    }
                }

            } else {
                if (intProcesscancel == 1) {
                    onCancelled();
                } else {
                    _clsMainActivity.showCustomToast(getContext(), "Offline", false);
                }

            }
            Dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            Dialog.setMessage(new clsHardCode().txtMessLogOut);
            Dialog.setCancelable(false);
            Dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    intProcesscancel = 1;
                }
            });
            Dialog.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Dialog.dismiss();
        }

    }*/


    public void inittAbsen(Context _ctx, List<clsAbsenData> ListData) {
//        tlAbsen = (TableLayout) v.findViewById(R.id.tl_absen);
        tlAbsen.removeAllViews();

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1f);
        params.setMargins(1, 1, 1, 1);

        TableRow tr = new TableRow(getContext());

        String[] colTextHeader = {"No.", "Outlet Code", "Outlet Name", "Date"};

        for (String text : colTextHeader) {
            TextView tv = new TextView(getContext());

            tv.setTextSize(14);
            tv.setPadding(10, 10, 10, 10);
            tv.setText(text);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(Color.parseColor("#4CAF50"));
            tv.setTextColor(Color.WHITE);
            tv.setLayoutParams(params);

            tr.addView(tv);
        }
        tlAbsen.addView(tr);

        if (ListData != null) {
            int index = 1;
            for (clsAbsenData dat : ListData) {
                tr = new TableRow(getContext());

                TextView tv_index = new TextView(getContext());
                tv_index.setTextSize(12);
                tv_index.setPadding(10, 10, 10, 10);
                tv_index.setBackgroundColor(Color.parseColor("#f0f0f0"));
                tv_index.setTextColor(Color.BLACK);
                tv_index.setGravity(Gravity.CENTER);
                tv_index.setText(String.valueOf(index + "."));
                tv_index.setLayoutParams(params);

                tr.addView(tv_index);

                TextView date = new TextView(getContext());
                date.setTextSize(12);
                date.setPadding(10, 10, 10, 10);
                date.setBackgroundColor(Color.parseColor("#f0f0f0"));
                date.setTextColor(Color.BLACK);
                date.setGravity(Gravity.CENTER);
                date.setText(new clsMainActivity().giveFormatDate2(dat.getDtCheckin()));
                date.setLayoutParams(params);

                tr.addView(date);

                tlAbsen.addView(tr, index++);
            }
        }
    }
}
