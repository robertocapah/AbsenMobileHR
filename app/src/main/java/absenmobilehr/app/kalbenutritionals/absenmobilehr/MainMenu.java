package absenmobilehr.app.kalbenutritionals.absenmobilehr;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseManager;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyResponseListener;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyUtils;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.clsHardCode;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.clsHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenOnline;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsDisplayPicture;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLastCheckingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsPushData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsTrackingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmConfig;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmVersionApp;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsAbsenDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsAbsenOnlineRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsDisplayPictureRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsLastCheckingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsTrackingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsUserLoginRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsmConfigRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsmVersionAppRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.enumConfigData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment.FragmentAbsen;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment.FragmentInformation;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment.FragmentPushData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment.FragmentReport;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Service.MyServiceNative;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Service.MyTrackingLocationService;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.android.volley.VolleyLog.TAG;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private clsUserLogin dttAbsenUserData;

    private TextView tvUsername, tvEmail;
    private CircleImageView ivProfile;
    private List<clsDisplayPicture> tDisplayPictureData;

    PackageInfo pInfo = null;

    int selectedId;
    private static int menuId = 0;
    Boolean isSubMenu = false;

    clsMainActivity _clsMainActivity = new clsMainActivity();
    List<clsUserLogin> dataLogin= null;
    List<clsmVersionApp> dataInfo = null;
    String[] listMenu;
    String[] linkMenu;

    private GoogleApiClient client;

    String i_view = null;
    Intent intent;
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedId = 0;

        if (!isMyServiceRunning(MyServiceNative.class)) {
            startService(new Intent(MainMenu.this, MyServiceNative.class));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary_color_theme));
        }
        try {
            pInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        FragmentInformation homeFragment = new FragmentInformation();
        FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
        fragmentTransactionHome.replace(R.id.frame, homeFragment,"FragmentInformation");
        fragmentTransactionHome.commit();
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View vwHeader = navigationView.getHeaderView(0);
        ivProfile = (CircleImageView) vwHeader.findViewById(R.id.profile_image);
        tvUsername = (TextView) vwHeader.findViewById(R.id.username);
        tvEmail = (TextView) vwHeader.findViewById(R.id.email);
        clsUserLoginRepo repo = new clsUserLoginRepo(getApplicationContext());
        clsmVersionAppRepo repoVersionInfo = new clsmVersionAppRepo(getApplicationContext());
        try {
            dataLogin = (List<clsUserLogin>) repo.findAll();
            dataInfo = (List<clsmVersionApp>) repoVersionInfo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(dataLogin.size()>0){
            tvUsername.setText(_clsMainActivity.greetings() + dataLogin.get(0).getTxtName());
            tvEmail.setText(dataLogin.get(0).getTxtEmail());
        }

        try {
            tDisplayPictureData = (List<clsDisplayPicture>) new clsDisplayPictureRepo(getApplicationContext()).findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (tDisplayPictureData.size() > 0 && tDisplayPictureData.get(0).getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(tDisplayPictureData.get(0).getImage(), 0, tDisplayPictureData.get(0).getImage().length);
            ivProfile.setImageBitmap(bitmap);
        } else {
            ivProfile.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                    R.drawable.profile));
        }
        ivProfile.setOnClickListener(this);
        Menu header = navigationView.getMenu();
        clsAbsenData dataAbsenAktif = new clsAbsenDataRepo(getApplicationContext()).getDataCheckinActive(getApplicationContext());
//        if (dataAbsenAktif!=null){
//            header.removeItem(R.id.absen);
//            header.removeItem(R.id.logout);
//            header.removeItem(R.id.pushData);
//        }else{
//            header.removeItem(R.id.checkout);
//        }
        clsAbsenOnline dataAbsenOnline = new clsAbsenOnlineRepo(getApplicationContext()).getDataCheckinActive(getApplicationContext());
        if (dataAbsenOnline!=null){
            header.removeItem(R.id.absen);
            header.removeItem(R.id.logout);
            header.removeItem(R.id.pushData);
        }else{
            header.removeItem(R.id.checkout);
        }

        SubMenu subMenuVersion = header.addSubMenu(R.id.groupVersion, 0, 3, "Version");
        try {
            subMenuVersion.add(getPackageManager().getPackageInfo(getPackageName(), 0).versionName + " \u00a9 KN-IT").setIcon(R.drawable.ic_android).setEnabled(false);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.logout:
                        LayoutInflater layoutInflater = LayoutInflater.from(MainMenu.this);
                        final View promptView = layoutInflater.inflate(R.layout.confirm_data, null);

                        final TextView _tvConfirm = (TextView) promptView.findViewById(R.id.tvTitle);
                        final TextView _tvDesc = (TextView) promptView.findViewById(R.id.tvDesc);
                        _tvDesc.setVisibility(View.INVISIBLE);
                        _tvConfirm.setText("Log Out Application ?");
                        _tvConfirm.setTextSize(18);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainMenu.this);
                        alertDialogBuilder.setView(promptView);
                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        stopService(new Intent(getApplicationContext(), MyServiceNative.class));
                                        stopService(new Intent(getApplicationContext(), MyTrackingLocationService.class));
                                        MyTrackingLocationService service = new MyTrackingLocationService();
                                        service.shutdownService();
                                        Context context = getApplicationContext();
                                        List<clsTrackingData> trackingData = new ArrayList<>();
                                        trackingData = (List<clsTrackingData>) new clsTrackingDataRepo(context).getAllDataToPushData(context);
                                        clsAbsenData absenData = null;
                                        absenData = (clsAbsenData) new clsAbsenDataRepo(context).getDataCheckinActive(context);
                                        boolean dvalid = false;
                                        if (trackingData != null && trackingData.size() > 0 && dvalid == false) {
                                            dvalid = true;
                                        }
                                        if (absenData != null && dvalid == false) {
                                            dvalid = true;
                                        }
                                        if (dvalid){
                                            boolean result = pushData();
                                            if (result){
                                                logout();
                                            }else{
                                                new clsMainActivity().showCustomToast(getApplicationContext(),"Push Data to Logout", false);
//                                                logout();
                                            }
//                                            Intent myIntent = new Intent(MainMenu.this, PushData.class);
//                                            myIntent.putExtra("action","logout");
//
//                                            startActivity(myIntent);
//                                            finish();
                                        }else{
                                            logout();
                                        }


//                                        AsyncCallLogOut task = new AsyncCallLogOut();
//                                        task.execute();
                                        //new clsHelperBL().DeleteAllDB();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        final AlertDialog alertD = alertDialogBuilder.create();
                        alertD.show();
                        return true;
                    case R.id.absen:
                        toolbar.setTitle("Absen");
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        FragmentAbsen fragmentAbsen = new FragmentAbsen();
                        FragmentTransaction fragmentTransactionKuesioner = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionKuesioner.replace(R.id.frame, fragmentAbsen);
                        fragmentTransactionKuesioner.commit();
                        return true;
                    case R.id.home:
                        toolbar.setTitle("Home");

                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

                        FragmentInformation homeFragment = new FragmentInformation();
                        FragmentTransaction fragmentTransactionHome = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionHome.replace(R.id.frame, homeFragment,"FragmentInformation");
                        fragmentTransactionHome.commit();
                        selectedId = 99;

                        return true;
                    case R.id.pushData:
                        toolbar.setTitle("Home");

                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

                        FragmentPushData fragmentPush = new FragmentPushData();
                        Bundle arguments = new Bundle();
                        arguments.putString( "key_view" , "main_menu");
                        fragmentPush.setArguments(arguments);
                        FragmentTransaction fragmentTransactionPushData = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionPushData.replace(R.id.frame, fragmentPush);
                        fragmentTransactionPushData.commit();
                        selectedId = 99;

                        return true;
                    case R.id.copydb:
                        toolbar.setTitle("Home");

                       /* try {
                            File sd = Environment.getExternalStorageDirectory();
                            File data = Environment.getDataDirectory();

                            if (sd.canWrite()) {
                                String currentDBPath = "//data//"+getApplicationContext().getPackageName()+"//databases//"+new clsHardCode().dbName;
                                String backupDBPath = "//testo//"+new clsHardCode().dbName;
                                File currentDB = new File(data, currentDBPath);
                                File backupDB = new File(sd, backupDBPath);

                                if (currentDB.exists()) {
                                    FileChannel src = new FileInputStream(currentDB).getChannel();
                                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                                    dst.transferFrom(src, 0, src.size());
                                    src.close();
                                    dst.close();
                                }
                            }
                        } catch (Exception e) {
                            e.getMessage();
                        }*/

                        try {

//                            String msg = new clsHelper().copyDataBase(getApplicationContext());
//                            String msg2 = new clsHelper().writeToSD(getApplicationContext());
                            String msg2 = new clsHelper().copydb(getApplicationContext());

                            new clsMainActivity().showCustomToast(getApplicationContext(),"Database sqlite copied to "+msg2,true);
                        } catch (IOException e) {
                            new clsMainActivity().showCustomToast(getApplicationContext(),"Copy Failed",false);
                            e.printStackTrace();
                        }
                        selectedId = 99;

                        return true;
                    case R.id.report:
                        toolbar.setTitle("Report Data");
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

                        FragmentReport fragmentReport = new FragmentReport();
                        FragmentTransaction fragmentTransactionReport = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionReport.replace(R.id.frame, fragmentReport);
                        fragmentTransactionReport.commit();
//                        try {
//
////                            new clsMainActivity().showCustomToast(getApplicationContext(),"Database sqlite copied to "+msg2,true);
//                        } catch (IOException e) {
//                            new clsMainActivity().showCustomToast(getApplicationContext(),"Copy Failed",false);
//                            e.printStackTrace();
//                        }
                        selectedId = 99;

                        return true;
                    case R.id.checkout:
                        LayoutInflater _layoutInflater = LayoutInflater.from(MainMenu.this);
                        final View _promptView = _layoutInflater.inflate(R.layout.confirm_data, null);

                        final TextView tvConfirm = (TextView) _promptView.findViewById(R.id.tvTitle);
                        final TextView tvDesc = (TextView) _promptView.findViewById(R.id.tvDesc);
                        tvDesc.setVisibility(View.INVISIBLE);
                        tvConfirm.setText("Check Out Data ?");
                        tvConfirm.setTextSize(18);

                        AlertDialog.Builder _alertDialogBuilder = new AlertDialog.Builder(MainMenu.this);
                        _alertDialogBuilder.setView(_promptView);
                        _alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        clsmConfig configData = null;
                                        String linkCheckinData= "";
                                        try {
                                            configData = (clsmConfig) new clsmConfigRepo(getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
                                            linkCheckinData = configData.getTxtValue()+new clsHardCode().linkCheckoutAbsen;
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }

                                        final clsUserLogin dataUserActive = new clsUserLoginRepo(getApplicationContext()).getDataLogin(getApplicationContext());
//                                        clsDeviceInfo dataDeviceInfoUser = new clsDeviceInfoRepo(context).getDataDevice(context);
                                        clsAbsenOnline dataAbsenOnline = new clsAbsenOnlineRepo(getApplicationContext()).getDataCheckinActive(getApplicationContext());
                                        String strLinkAPI = linkCheckinData;
                                        JSONObject resJson = new JSONObject();
                                        try {
                                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                            Calendar cal = Calendar.getInstance();
                                            String now = dateFormat.format(cal.getTime());

                                            resJson.put("txtTime", now);
                                            resJson.put("guiId",dataAbsenOnline.getTxtGuiId());
                                            resJson.put("guiIdLogin",dataAbsenOnline.getTxtGuiIdLogin());
                                            resJson.put(dataUserActive.Property_employeeId,dataUserActive.getEmployeeId());
                                            resJson.put(dataUserActive.Property_txtUserName,dataUserActive.getTxtUserName());
                                            resJson.put("TxtVersion", pInfo.versionName);
                                            resJson.put("TxtNameApp", "Android - Absen HR");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        final String mRequestBody =  resJson.toString();
                                        new VolleyUtils().makeJsonObjectRequest(MainMenu.this, strLinkAPI, mRequestBody, "Checking out please wait !", new VolleyResponseListener() {
                                            @Override
                                            public void onError(String response) {
                                                new clsMainActivity().showCustomToast(getApplicationContext(),"Connection Failed, please check your network !", false);
//                Toast.makeText(getApplicationContext(), "Connection Failed, please check your network !", Toast.LENGTH_SHORT).show();
//                Log.d("connection failed", response);
                                            }

                                            @Override
                                            public void onResponse(String response, Boolean status, String strErrorMsg) {
                                                if (response != null) {
                                                    if (!response.equals("false")){
                                                        clsAbsenOnline data = new clsAbsenOnline();
                                                        try {
                                                            JSONObject obj = new JSONObject(response);

                                                            String txtGUI_ID = obj.getString("txtGUI_ID");
                                                            data.setTxtGuiId(obj.getString("txtGUI_ID"));
                                                            data.setTxtGuiIdLogin(obj.getString("txtGuiIdLogin"));
                                                            data.setSync("1");
                                                            data.setIntSubmit("1");
                                                            data.setDtCheckout(obj.getString("dtCheckout"));

                                                            clsLastCheckingData dataChekin = null;
                                                            try {
                                                                dataChekin = new clsLastCheckingDataRepo(getApplicationContext()).findByGUIId(txtGUI_ID);
                                                                dataChekin.setTxtGuiID(obj.getString("txtGUI_ID"));
                                                                dataChekin.setDtCheckout(obj.getString("dtCheckout"));
                                                                new clsLastCheckingDataRepo(getApplicationContext()).update(dataChekin);
                                                            } catch (SQLException e) {
                                                                e.printStackTrace();
                                                            }

                                                            try {
                                                                new clsAbsenOnlineRepo(getApplicationContext()).update(data);
                                                                Intent myIntent = new Intent(getApplicationContext(), MainMenu.class);
                                                                startActivity(myIntent);
                                                                new clsMainActivity().showCustomToast(getApplicationContext(),"Checkout success !", true);
                                                            } catch (SQLException e) {
                                                                e.printStackTrace();
                                                            }

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }else{
                                                        new clsMainActivity().showCustomToast(getApplicationContext(),"Server Error, please contact your administrator !", false);
                                                    }
                                                }
                                            }
                                        });

//                                        clsAbsenData dataAbsen = new clsAbsenDataRepo(getApplicationContext()).getDataCheckinActive(getApplicationContext());
//
//                                        if (dataAbsen != null) {
//                                            dataAbsen.setDtCheckout(_clsMainActivity.FormatDateDB().toString());
//                                            dataAbsen.setSync("0");
//                                            try {
//                                                new clsAbsenDataRepo(getApplicationContext()).update(dataAbsen);
//                                            } catch (SQLException e) {
//                                                e.printStackTrace();
//                                            }
//                                            finish();
//                                            Intent nextScreen = new Intent(getApplicationContext(), MainMenu.class);
//                                            nextScreen.putExtra("keyMainMenu", "main_menu");
//                                            finish();
//                                            startActivity(nextScreen);
//                                        }
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        final AlertDialog _alertD = _alertDialogBuilder.create();
                        _alertD.show();

                        return true;
                }
                return false;
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    @Override
    @SuppressLint({"NewApi"})
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
//                Intent intent = new Intent(this, CropDisplayPicture.class);
//                String strName = imageUri.toString();
//                intent.putExtra("uriPicture", strName);
//                startActivity(intent);
                finish();
            }
        } else if (requestCode == 100 || requestCode == 130 || requestCode == 99) {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                try{
                    fragment.onActivityResult(requestCode, resultCode, data);
                }catch (Exception ex){

                }
            }
        }
    }
    public boolean pushData(){
        String versionName = "";
        final boolean[] result = {false};
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        clsPushData dtJson = new clsHelper().pushData(versionName, getApplicationContext());
        if (dtJson == null) {
        } else {
            try {
                String strLinkAPI = new clsHardCode().linkPushData;
                new VolleyUtils().makeJsonObjectRequestPushData(getApplicationContext(), strLinkAPI, dtJson, new VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        String error;
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
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                JSONObject method = jsonArray1.getJSONObject(i);
                                String listMethod = method.getString("PstrMethodRequest");
                                if (listMethod.equals(trackingData.Property_ListOftrackingLocation)) {
                                    if (method.getString("pBoolValid").equals("1")) {
                                        new clsTrackingDataRepo(getApplicationContext()).updateAllRowTracking();
                                        result[0] = true;
                                    }
                                }
                                if (listMethod.equals(absenData.Property_ListOftAbsenUser)) {
                                    if (method.getString("pBoolValid").equals("1")) {
                                        new clsAbsenDataRepo(getApplicationContext()).updateAllRowAbsen();
                                        result[0] = true;
                                    }
                                }
                            }
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
        return result[0];
    }

    public void logout(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        clsmConfig configData = null;
        String linkPushData= "";
        try {
            configData = (clsmConfig) new clsmConfigRepo(getApplicationContext()).findById(enumConfigData.API_PRM.getidConfigData());
            linkPushData = configData.getTxtValue()+new clsHardCode().linkLogout;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String strLinkAPI = linkPushData;
//        String nameRole = selectedRole;
        final JSONObject resJson = new JSONObject();

        try {
            resJson.put("TxtGUI_trUserLogin", dataLogin.get(0).getTxtGUI());
            resJson.put("TxtUserID", dataLogin.get(0).getTxtUserID());
            resJson.put("TxtGUI_mVersionApp", dataInfo.get(0).getTxtVersion());
            resJson.put("IntCabangID", dataLogin.get(0).getIntCabangID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = "[" + resJson.toString() + "]";
        new VolleyUtils().makeJsonObjectRequest(MainMenu.this, strLinkAPI, mRequestBody,"Logging Out, Please Wait !", new VolleyResponseListener() {
            @Override
            public void onError(String response) {
                new clsMainActivity().showCustomToast(getApplicationContext(), "Connection lost, please check your network", false);
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
                            stopService(new Intent(getApplicationContext(), MyTrackingLocationService.class));
                            helper.clearDataAfterLogout();
//                            helper.close();
                            finish();
                            Intent nextScreen = new Intent(MainMenu.this, Splash.class);
                            startActivity(nextScreen);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_image:
                pickImage2();
                break;
        }
    }
    public void pickImage2() {
        CropImage.startPickImageActivity(this);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Exit");
        builder.setMessage("Do you want to exit?");

        builder.setPositiveButton("EXIT", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}