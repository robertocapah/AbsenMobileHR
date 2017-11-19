package absenmobilehr.app.kalbenutritionals.absenmobilehr;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.GpsManager.GPSManager;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyResponseListener;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyUtils;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.clsHardCode;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.clsHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsBranchAccess;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsDeviceInfo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLastCheckingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLeaveData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsReportData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsTrackingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsTypeLeave;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmConfig;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmVersionApp;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsBranchAccessRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsDeviceInfoRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsLastCheckingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsLeaveDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsReportDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsTrackingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsTypeLeaveRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsUserLoginRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsmConfigRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsmVersionAppRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.enumConfigData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Service.MyServiceNative;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Service.MyTrackingLocationService;

//import bl.clsHelperBL;
//import bl.clsMainBL;
//import bl.tDeviceInfoUserBL;
//import library.salesforce.common.linkAPI;
//import library.salesforce.common.tDeviceInfoUserData;
//import library.salesforce.dal.clsHardCode;
//import library.salesforce.dal.enumConfigData;
//import library.salesforce.dal.mconfigDA;
//import library.salesforce.dal.tDeviceInfoUserDA;
//import service.MyServiceNative;

public class Login extends clsMainActivity {
    private String role = "Role";
    private String[] roles = new String[1];
    ProgressDialog progress;
    long Delay = 3000;
    private EditText txtLoginEmail;
    private EditText txtLoginPassword;
    private Button btnLogin, btnPing;
    private TextView txtVersionLogin;
    private PackageInfo pInfo = null;
    private List<String> arrrole, arroutlet;
    private HashMap<String, String> HMRole = new HashMap<String, String>();
    private HashMap<String, String> HMOutletCode = new HashMap<String, String>();
    private HashMap<String, String> HMOutletName = new HashMap<String, String>();
    private HashMap<String, String> HMBranchCode = new HashMap<String, String>();
    //    private Spinner spnRole, spnOutlet;
    private int intSet = 1;
    private String selectedRole;
    private String selectedOutlet;
    private String txtEmail;
    private String txtEmail1;
    private String txtPassword1;
    private String txtPassword;
    private String[] arrdefaultBranch = new String[]{"-"};
    private String[] arrdefaultOutlet = new String[]{"-"};
    private static final String TAG = "MainActivity";
    clsDeviceInfoRepo repoDeviceInfo;
    clsUserLoginRepo repoLogin;
    clsmVersionAppRepo repoVersionApp;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Exit");
        builder.setMessage("Do you want to exit?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

//    private clsHardCode clsHardcode = new clsHardCode();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        repoDeviceInfo = new clsDeviceInfoRepo(getApplicationContext());
        repoVersionApp = new clsmVersionAppRepo(getApplicationContext());
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        if (isMyServiceRunning(MyTrackingLocationService.class)) {
            stopService(new Intent(Login.this, MyTrackingLocationService.class));
        }
//        Timer RunSplash = new Timer();
//
//        // Note: declare ProgressDialog progress as a field in your class.
//
//        progress = ProgressDialog.show(this, "",
//                "Checking Version!!!", true);
//
//        TimerTask ShowSplash = new TimerTask() {
//            @Override
//            public void run() {
//                //Intent myIntent = new Intent(Login.this, Login.class);
//                // do the thing that takes a long time
//                progress.dismiss();
//                //finish();
//                //startActivity(myIntent);
//            }
//        };
//
//        RunSplash.schedule(ShowSplash, Delay);
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String imeiNumber = tm.getDeviceId();
//        new tDeviceInfoUserBL().SaveInfoDevice("", "", imeiNumber);
        ImageView imgBanner = (ImageView) findViewById(R.id.ivBannerLogin);
        imgBanner.setAdjustViewBounds(true);
        imgBanner.setScaleType(ImageView.ScaleType.CENTER_CROP);
        txtLoginEmail = (EditText) findViewById(R.id.txtLoginEmail);
        txtLoginPassword = (EditText) findViewById(R.id.editTextPass);
        txtLoginEmail.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    intProcesscancel = 0;
                    txtLoginPassword.requestFocus();
//                    txtEmail1 = txtLoginEmail.getText().toString();
//                    txtPassword1 = txtLoginPassword.getText().toString();
                    return true;
                }
                return false;
            }
        });


//        AsyncCallAppVesion task1 = new AsyncCallAppVesion();
//        task1.execute();
        checkVersion();
        txtLoginPassword.setOnTouchListener(new DrawableClickListener.RightDrawableClickListener(txtLoginPassword) {
            public boolean onDrawableClick() {
                if (intSet == 1) {
                    txtLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    intSet = 0;
                } else {
                    txtLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    intSet = 1;
                }

                return true;
            }
        });

        txtLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER ||
                            keyCode == KeyEvent.KEYCODE_ENTER) {
                        btnLogin.performClick();
                        return true;
                    }
                }

                return false;
            }
        });

        txtLoginPassword.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    btnLogin.performClick();
                    return true;
                }
                return false;
            }
        });


        txtVersionLogin = (TextView) findViewById(R.id.txtVersionLogin);
        txtVersionLogin.setText(pInfo.versionName);
        /*spnRole = (Spinner) findViewById(R.id.spnType);

        spnRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedRole = spnRole.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });*/


       /* spnOutlet = (Spinner) findViewById(R.id.spnOutlet);

        spnOutlet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedOutlet = spnOutlet.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });*/

        btnLogin = (Button) findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                intProcesscancel = 0;
                if (txtLoginEmail.getText().length() == 0) {
                    showCustomToast(Login.this, "Please input username", false);

                } else {
                    txtEmail1 = txtLoginEmail.getText().toString();
                    txtPassword1 = txtLoginPassword.getText().toString();
//                        AsyncCallLogin task = new AsyncCallLogin();
//                        task.execute();
                    List<clsmVersionApp> _clsmVersionApp = new ArrayList<clsmVersionApp>();
                    userLogin();
                }
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Button btnExit = (Button) findViewById(R.id.buttonExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);

                builder.setTitle("Exit");
                builder.setMessage("Do you want to exit?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        Button btnPing = (Button) findViewById(R.id.buttonPing);
        btnPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //buat check isi table deviceInfo
                List<clsDeviceInfo> items = null;
                try {
                    items = (List<clsDeviceInfo>) repoDeviceInfo.findAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
//                clsDeviceInfo info = items.get(0);
//                Log.d("info",info.toString());

                //buat clear isi table
                /*try {
                    repoDeviceInfo.clearTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }*/

                //ini buat copy db ke luar
                try {
                    new clsHelper().copydb(getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        /*btnPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUrl = new mconfigDA(new clsMainBL().getDb()).getData(new clsMainBL().getDb(), enumConfigData.ApiKalbe.getidConfigData()).get_txtValue();

                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    URL url = new URL(strUrl);
                    HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.connect();

                    assertEquals(HttpURLConnection.HTTP_OK, urlConn.getResponseCode());
                    showCustomToast(Login.this, "Connected", true);

                } catch (IOException e) {
                    showCustomToast(Login.this, "Not connected", false);
                }
            }
        });*/
        ArrayAdapter<String> adapterspnBranch = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrdefaultBranch);
        /*spnRole.setAdapter(adapterspnBranch);
        spnRole.setEnabled(false);
        ArrayAdapter<String> adapterspnOutlet = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrdefaultOutlet);
        spnOutlet.setAdapter(adapterspnOutlet);
        spnOutlet.setEnabled(false);
*/

//        AsyncCallAppVesion task = new AsyncCallAppVesion();
//        task.execute();
//        checkVersion();
    }



    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void userLogin() {
        clsmConfig configData = null;
        String linkPushData = "";
        try {
            configData = (clsmConfig) new clsmConfigRepo(getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
            linkPushData = configData.getTxtValue() + new clsHardCode().linkLogin;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<clsmVersionApp> _clsmVersionApp = new ArrayList<>();
        try {
            _clsmVersionApp = (List<clsmVersionApp>) new clsmVersionAppRepo(getApplicationContext()).findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (_clsmVersionApp.size() > 0) {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            txtEmail1 = txtLoginEmail.getText().toString();
            txtPassword1 = txtLoginPassword.getText().toString();
            String strLinkAPI = configData.getTxtValue() + new clsHardCode().linkLogin;
//        String nameRole = selectedRole;
            JSONObject resJson = new JSONObject();
            List<clsmVersionApp> dataInfo = new ArrayList<>();
            List<clsDeviceInfo> dataDevice = new ArrayList<>();
            try {
                dataInfo = (List<clsmVersionApp>) repoVersionApp.findAll();
                dataDevice = (List<clsDeviceInfo>) repoDeviceInfo.findAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                resJson.put("TxtVersion", dataInfo.get(0).getTxtVersion());
                resJson.put("TxtGUI_mVersionApp", dataInfo.get(0).getTxtGUI());
                resJson.put("TxtPegawaiID", txtEmail1);
                resJson.put("TxtPassword", txtPassword1);
                resJson.put("TxtModel", dataDevice.get(0).getTxtModel());
                resJson.put("TxtDevice", dataDevice.get(0).getTxtDevice());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String mRequestBody = "[" + resJson.toString() + "]";
            new VolleyUtils().makeJsonObjectRequest(Login.this, strLinkAPI, mRequestBody, "Logging in, please wait !", new VolleyResponseListener() {
                @Override
                public void onError(String response) {
                    new clsMainActivity().showCustomToast(getApplicationContext(), response, false);
                }

                @Override
                public void onResponse(String response, Boolean status, String strErrorMsg) {
                    if (response != null) {
                        try {
                            JSONObject jsonObject1 = new JSONObject(response);
//                            JSONObject jsn = jsonObject1.getJSONObject("validJson");
                            String warn = jsonObject1.getString("TxtWarn");
                            String result = jsonObject1.getString("TxtResult");
                            JSONObject jsonObject2 = null;
                            if (result.equals("1")) {
                                jsonObject2 = jsonObject1.getJSONObject("TxtData");
                                String strLatest = jsonObject1.getString("TxtLatestData");
                                String bitMood = jsonObject1.getString("bitMood");

                                JSONObject jsonDataUserLogin = jsonObject2.getJSONObject("UserLogin");
                                JSONArray jsonArrayBranchAccess = jsonObject2.getJSONArray("UserBranch");
                                String strLeave = jsonObject1.getString("LeaveData");
                                String nameApp = null;
                                if (strLeave != null && !strLeave.equals("null")){
                                    JSONObject jsonLeave = jsonObject1.getJSONObject("LeaveData");
                                    clsLeaveData dataLeave = new clsLeaveData();
                                    dataLeave.setBitActive(1);
                                    dataLeave.setLeaveId(Integer.parseInt(jsonLeave.getString("intLeaveId")));
                                    dataLeave.setTxtNIK(jsonLeave.getString("txtNIK"));
                                    dataLeave.setTxtTime(jsonLeave.getString("dtAbsenceDate"));
                                    dataLeave.setTxtKeterangan(jsonLeave.getString("Description"));
                                    try {
                                        new clsLeaveDataRepo(getApplicationContext()).createOrUpdate(dataLeave);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if (jsonArrayBranchAccess.length()>0){
                                    for (int j=0 ; j<jsonArrayBranchAccess.length();j++){
                                        JSONObject json_data = jsonArrayBranchAccess.getJSONObject(j);
                                            String intBranchID = json_data.getString("IntBranchID");
                                        String txtBranchCode = json_data.getString("TxtBranchCode");
                                        String txtBranchName = json_data.getString("TxtBranchName");
                                        String txtBranchSiteId = json_data.getString("TxtBranchSiteId");
                                        String txtBranchCOA = json_data.getString("TxtBranchCOA");
                                        String dtNonActive = json_data.getString("DtNonActive");

                                        clsBranchAccess dataBranch = new clsBranchAccess();
                                        dataBranch.setTxtBranchCode(txtBranchCode);
                                        dataBranch.setIntBranchID(Integer.parseInt(intBranchID));
                                        dataBranch.setDtNonActive(dtNonActive);
                                        if(txtBranchCode.equals("HO")){
                                            dataBranch.setTxtBranchName("Head Office");
                                        }else{
                                            dataBranch.setTxtBranchName(txtBranchName);
                                        }

                                        dataBranch.setTxtBranchCOA(txtBranchCOA);

                                        try {
                                            new clsBranchAccessRepo(getApplicationContext()).createOrUpdate(dataBranch);
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                try {
                                    List<clsmVersionApp> appInfo = (List<clsmVersionApp>) repoVersionApp.findAll();
                                    nameApp = appInfo.get(0).getTxtNameApp();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                String TxtGUI = jsonDataUserLogin.getString("TxtGUI");
                                String TxtUserID = jsonDataUserLogin.getString("TxtUserID");
                                String TxtJabatanID = jsonDataUserLogin.getString("TxtJabatanID");
                                String TxtJabatanName = jsonDataUserLogin.getString("TxtJabatanName");
                                String TxtUserName = jsonDataUserLogin.getString("TxtUserName");
                                String TxtName = jsonDataUserLogin.getString("TxtName");
                                String TxtEmail = jsonDataUserLogin.getString("TxtEmail");
                                String TxtEmpID = jsonDataUserLogin.getString("TxtEmpID");
                                String IntCabangID = jsonDataUserLogin.getString("IntCabangID");
                                String TxtKodeCabang = jsonDataUserLogin.getString("TxtKodeCabang");
                                String TxtNamaCabang = jsonDataUserLogin.getString("TxtNamaCabang");
                                String DtLastLogin = jsonDataUserLogin.getString("DtLastLogin");
                                String TxtDeviceId = jsonDataUserLogin.getString("TxtDeviceId");
                                String TxtInsertedBy = jsonDataUserLogin.getString("TxtInsertedBy");
                                clsUserLogin data = new clsUserLogin();

                                if (!bitMood.equals("null") && bitMood != null) {
                                        int mood = Integer.parseInt(bitMood);
                                        data.setBitMood(mood);
                                }

                                data.setTxtNameApp(nameApp);
                                data.setTxtGUI(TxtGUI);
                                data.setTxtUserID(TxtUserID);
                                data.setJabatanId(TxtJabatanID);
                                data.setJabatanName(TxtJabatanName);
                                data.setIdUserLogin(1);
                                data.setTxtUserName(TxtUserName);
                                data.setTxtName(TxtName);
                                data.setTxtEmail(TxtEmail);
                                data.setEmployeeId(TxtEmpID);
                                data.setIntCabangID(IntCabangID);
                                data.setTxtKodeCabang(TxtKodeCabang);
                                data.setTxtNamaCabang(TxtNamaCabang);
                                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                                Calendar cal = Calendar.getInstance();
                                data.setDtLastLogin(DtLastLogin);
                                data.setTxtDeviceId(android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL);
                                data.setTxtInsertedBy(TxtInsertedBy);
                                data.setDtInserted(dateFormat.format(cal.getTime()));

                                repoLogin = new clsUserLoginRepo(getApplicationContext());
                                int i = 0;
                                i = repoLogin.createOrUpdate(data);
                                if (i > -1) {
                                    Log.d("Data info", "Data info berhasil di simpan");
                                    Intent myIntent = new Intent(Login.this, MainMenu.class);
                                    myIntent.putExtra("keyMainMenu", "login");
                                    startActivity(myIntent);
                                    finish();
                                    if (!isMyServiceRunning(MyServiceNative.class)) {
                                        startService(new Intent(Login.this, MyServiceNative.class));
                                    }
                                    boolean tes, tes2;
                                    tes = isMyServiceRunning(MyServiceNative.class);
                                }
                                try {
                                    if (!strLatest.equals("null") && strLatest != null) {
                                        clsTrackingData trackingData = new clsTrackingData();
                                        JSONObject jsonLatest = new JSONObject(strLatest);
                                        trackingData.setGuiId(new clsMainActivity().GenerateGuid());
                                        trackingData.setIntSubmit("1");
                                        trackingData.setIntSync("0");
                                        trackingData.setGuiIdLogin(jsonLatest.getString("txtGuiIdLogin"));
                                        trackingData.setTxtBranchCode(jsonLatest.getString("txtBranchCode"));
                                        int intSeq = Integer.parseInt(jsonLatest.getString("intSequence")) + 1;
                                        trackingData.setIntSequence(intSeq);
                                        trackingData.setTxtDeviceId(jsonLatest.getString("txtDeviceId"));
                                        trackingData.setTxtTime(jsonLatest.getString("dtDate"));
                                        trackingData.setTxtLatitude(jsonLatest.getString("txtLatitude"));
                                        trackingData.setTxtLongitude(jsonLatest.getString("txtLongitude"));
                                        trackingData.setTxtNIK(jsonLatest.getString("txtNIK"));
                                        trackingData.setIntSync("1");
                                        trackingData.setTxtUserId(jsonLatest.getString("txtUserId"));
                                        trackingData.setTxtUsername(jsonLatest.getString("txtUsername"));

                                        if (!jsonLatest.getString("ltAbsen").equals("null")) {
                                            JSONArray jArray = jsonLatest.getJSONArray("ltAbsen");
                                            int jACount = jArray.length();
                                            if (jACount > 0) {
                                                for (int a = 0; a < jArray.length(); a++) {
                                                    JSONObject json_data = jArray.getJSONObject(a);
                                                    String guiID = json_data.getString("txtGUI_ID");
                                                    String outlet = json_data.getString("txtOutletName");
                                                    String outletId = json_data.getString("txtOutletId");
                                                    String dtCheckin = json_data.getString("dtCheckin");
                                                    String dtCheckout = json_data.getString("dtCheckout");

                                                    clsLastCheckingData dataCheckin = new clsLastCheckingData();
                                                    dataCheckin.setTxtGuiID(guiID);
                                                    dataCheckin.setTxtOutletId(outletId);
                                                    dataCheckin.setTxtOutletName(outlet);
                                                    dataCheckin.setDtCheckin(dtCheckin);
                                                    dataCheckin.setDtCheckout(dtCheckout);
                                                    new clsLastCheckingDataRepo(getApplicationContext()).createOrUpdate(dataCheckin);
                                                }
                                            }

                                        }
                                        if (!jsonLatest.getString("ltAbsenAll").equals("null")) {
                                            JSONArray jArrayAll = jsonLatest.getJSONArray("ltAbsenAll");
                                            if (jArrayAll.length() > 0) {
                                                for (int a = 0; a < jArrayAll.length(); a++) {
                                                    JSONObject json_data = jArrayAll.getJSONObject(a);
                                                    String guiID = json_data.getString("txtGUI_ID");
                                                    String outlet = json_data.getString("txtOutletName");
                                                    String outletId = json_data.getString("txtOutletId");
                                                    String dtCheckin = json_data.getString("dtCheckin");
                                                    String dtCheckout = json_data.getString("dtCheckout");
                                                    String txtLongitude = json_data.getString("txtLongitude");
                                                    String txtLatitude = json_data.getString("txtLatitude");
                                                    String txtDeviceId = json_data.getString("txtDeviceId");
                                                    String txtDeviceName = json_data.getString("txtDeviceName");
                                                    String txtUserId = json_data.getString("txtUserId");

//                                    String dateString = "22/05/2014 11:49:00 AM";
                                                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                                    Date convertedDate = new Date();
//                                                    try {
//                                                        convertedDate = dateFormat.parse(dateString);
//                                                    } catch (ParseException e) {
//                                                        // TODO Auto-generated catch block
//                                                        e.printStackTrace();
//                                                    }
                                                    Date dtCheckinD = null;
                                                    if (!dtCheckin.equals("null")) {
                                                        dtCheckinD = dateFormat2.parse(dtCheckin);
                                                    }
                                                    Date dtCheckoutD = null;
                                                    if (!dtCheckout.equals("null")) {
                                                        dtCheckoutD = dateFormat2.parse(dtCheckout);
                                                    }
                                                    clsReportData dataCheckin = new clsReportData();
                                                    dataCheckin.setTxtGuiID(guiID);
                                                    dataCheckin.setTxtOutletId(outletId);
                                                    dataCheckin.setTxtOutletName(outlet);
                                                    dataCheckin.setDtCheckin(dtCheckinD);
                                                    dataCheckin.setDtCheckout(dtCheckoutD);
                                                    dataCheckin.setTxtLongitude(txtLongitude);
                                                    dataCheckin.setTxtLatitude(txtLatitude);
                                                    dataCheckin.setTxtDeviceId(txtDeviceId);
                                                    dataCheckin.setTxtDeviceName(txtDeviceName);
                                                    dataCheckin.setTxtUserId(txtUserId);
                                                    new clsReportDataRepo(getApplicationContext()).createOrUpdate(dataCheckin);


                                                }
                                            }
                                        }
                                         if (!jsonLatest.getString("ltTypeLeave").equals("null")) {
                                            JSONArray jArrayAll = jsonLatest.getJSONArray("ltTypeLeave");
                                            if (jArrayAll.length() > 0) {
                                                for (int a = 0; a < jArrayAll.length(); a++) {
                                                    JSONObject json_data = jArrayAll.getJSONObject(a);
                                                    String typeLeaveId = json_data.getString("typeLeaveId");
                                                    String typeLeaveCode = json_data.getString("txtleaveCode");
                                                    String typeLeaveName = json_data.getString("txtleaveName");
                                                    String typeLevaeKeterangan = json_data.getString("txtKeterangan");
                                                    String bitactive = json_data.getString("bitActive");

                                                    clsTypeLeave dataLeave  = new clsTypeLeave();
                                                    dataLeave.setIntLeaveID(Integer.parseInt(typeLeaveId));
                                                    dataLeave.setBitActive(Integer.parseInt(bitactive));
                                                    dataLeave.setTxtLeaveCode(typeLeaveCode);
                                                    dataLeave.setTxtKeterangan(typeLevaeKeterangan);
                                                    dataLeave.setTxtLeaveName(typeLeaveName);

                                                    new clsTypeLeaveRepo(getApplicationContext()).createOrUpdate(dataLeave);
                                                }
                                            }
                                        }


                                        int done = new clsTrackingDataRepo(getApplicationContext()).create(trackingData);
                                        if (done > -1) {
                                            new clsMainActivity().showCustomToast(getApplicationContext(), "Done", true);
                                            if (!isMyServiceRunning(MyTrackingLocationService.class)) {
                                                Intent a = new Intent(Login.this, MyTrackingLocationService.class);
                                                startService(a);
                                            }
                                            if (!isMyServiceRunning(MyServiceNative.class)) {
                                                Intent a = new Intent(Login.this, MyServiceNative.class);

                                                startService(a);
                                            }
                                            boolean tes, tes2;
                                            tes2 = isMyServiceRunning(MyTrackingLocationService.class);
                                            // Reload current fragment
                                            Fragment frg = null;
//                                            frg = getFragmentManager().findFragmentByTag("FragmentInformation");
//                                            final FragmentTransaction ft = getFragmentManager().beginTransaction();
//
// ft.detach(frg);
//                                            ft.attach(frg);
//                                            ft.commit();
                                        }

                                    } else {
                                        if (!isMyServiceRunning(MyTrackingLocationService.class)) {
                                            Intent a = new Intent(Login.this, MyTrackingLocationService.class);
                                            startService(a);
                                        }
                                        if (!isMyServiceRunning(MyServiceNative.class)) {
                                            Intent a = new Intent(Login.this, MyServiceNative.class);
                                            startService(a);
                                        }
                                    }
                                } catch (Exception ex) {
                                    String a = "hihi";
                                }
                            } else {
                                new clsMainActivity().showCustomToast(getApplicationContext(), warn, false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
//                if(!status){
//                    new clsMainActivity().showCustomToast(getApplicationContext(), strErrorMsg, false);
//                }
                }
            });
        } else {
            checkVersion();
        }

    }

    public void checkVersion() {
        final ProgressDialog Dialog = new ProgressDialog(Login.this);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        clsmConfig configData = null;
        String linkPushData = "";
        try {
            configData = (clsmConfig) new clsmConfigRepo(getApplicationContext()).findById(enumConfigData.API_PRM.getidConfigData());
            linkPushData = configData.getTxtValue() + new clsHardCode().linkCheckVersion;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String strLinkAPI = linkPushData;
        JSONObject resJson = new JSONObject();
        String nameApp = new clsHardCode().name_app;
        try {
            resJson.put("TxtVersion", pInfo.versionName);
            resJson.put("TxtNameApp", nameApp);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = "[" + resJson.toString() + "]";
//        String result = new clsHelper().volleyImplement(getApplicationContext(),mRequestBody,strLinkAPI,Login.this);
//

        new VolleyUtils().makeJsonObjectRequest(Login.this, strLinkAPI, mRequestBody, "Checking Version !", new VolleyResponseListener() {
            @Override
            public void onError(String response) {
                new clsMainActivity().showCustomToast(getApplicationContext(), "Connection Failed, please check your network !", false);
//                Toast.makeText(getApplicationContext(), "Connection Failed, please check your network !", Toast.LENGTH_SHORT).show();
//                Log.d("connection failed", response);
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                if (response != null) {
                    try {
                        JSONObject jsonObject1 = new JSONObject(response);
                        JSONObject jsonObject2 = jsonObject1.getJSONObject("validJson");

                        String result = jsonObject2.getString("TxtResult");
                        String txtWarn = jsonObject2.getString("TxtWarn");
                        if (result.equals("1")) {
                            JSONObject jsonObject3 = jsonObject2.getJSONObject("TxtData");
                            String txtGUI = jsonObject3.getString("TxtGUI");
                            String txtNameApp = jsonObject3.getString("TxtNameApp");
                            String txtVersion = jsonObject3.getString("TxtVersion");
                            String txtFile = jsonObject3.getString("TxtFile");
                            String bitActive = jsonObject3.getString("BitActive");
                            String txtInsertedBy = jsonObject3.getString("TxtInsertedBy");
                            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                            String imeiNumber = tm.getDeviceId();
                            clsDeviceInfo data = new clsDeviceInfo();
                            clsmVersionApp dataVersion = new clsmVersionApp();
                            dataVersion.setTxtGUI(txtGUI);
                            dataVersion.setTxtNameApp(txtNameApp);
                            dataVersion.setTxtVersion(txtVersion);
                            dataVersion.setTxtFile(txtFile);
                            dataVersion.setBitActive(bitActive);
                            data.setTxtDevice(android.os.Build.DEVICE);
                            data.setTxtInsertedBy(txtInsertedBy);
                            data.setIdDevice(imeiNumber);
                            data.setTxtModel(android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL);
                            int i = 0;
                            int j = 0;
                            try {
                                i = repoDeviceInfo.createOrUpdate(data);
                                j = repoVersionApp.createOrUpdate(dataVersion);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            if (i > -1 && j > -1) {
                                Log.d("Data info", "Data info berhasil di simpan");
                                status = true;
                                GPSManager gps = new GPSManager(
                                        Login.this);
                                gps.start();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), txtWarn, Toast.LENGTH_SHORT).show();
                        }


//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject explrObject = jsonArray.getJSONObject(i);
//                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (!status) {
                    new clsMainActivity().showCustomToast(getApplicationContext(), "Connection Failed, please check your network !", false);
                } else {
                    new clsMainActivity().showCustomToast(getApplicationContext(), "Connected, you ready to login", true);
                }
            }
        });

        /*try {
            JSONObject jsonObject1 = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    int intProcesscancel = 0;
    ProgressDialog mProgressDialog;

   /* @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://spgmobile/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://spgmobile/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }*/

    public class MyAdapter extends ArrayAdapter<String> {
        public MyAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
        }


        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.custom_spinner, parent, false);
            TextView label = (TextView) row.findViewById(R.id.tvTitle);
            label.setText(arrrole.get(position));
            TextView sub = (TextView) row.findViewById(R.id.tvDesc);
            sub.setVisibility(View.INVISIBLE);
            sub.setVisibility(View.GONE);
            //sub.setText(mydata2[position]);
            //label.setTextColor(new Color().parseColor("#FFFFF"));
            row.setBackgroundColor(new Color().TRANSPARENT);
            return row;
        }

    }

    public class MyAdapter2 extends ArrayAdapter<String> {
        public MyAdapter2(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            // TODO Auto-generated constructor stub
        }


        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.custom_spinner, parent, false);
            TextView label = (TextView) row.findViewById(R.id.tvTitle);
            label.setText(arroutlet.get(position));
            TextView sub = (TextView) row.findViewById(R.id.tvDesc);
            sub.setVisibility(View.INVISIBLE);
            sub.setVisibility(View.GONE);
            //sub.setText(mydata2[position]);
            //label.setTextColor(new Color().parseColor("#FFFFF"));
            row.setBackgroundColor(new Color().TRANSPARENT);
            return row;
        }

    }

    /*private class DownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                String txtPath = new clsHardCode().txtPathUserData;
                File mediaStorageDir = new File(txtPath);
                // Create the storage directory if it does not exist
                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                        return null;
                    }
                }
                output = new FileOutputStream(txtPath + "kalbespgmobile.apk");

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        int intProcesscancel = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
                showToast(context, "Download error: " + result);
            else {
                showToast(context, "File downloaded");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String txtPath = new clsHardCode().txtPathUserData + "kalbespgmobile.apk";
                intent.setDataAndType(Uri.fromFile(new File(txtPath)), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }*/
}
