package absenmobilehr.app.kalbenutritionals.absenmobilehr;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseManager;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.GpsManager.LocationHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyResponseListener;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyUtils;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.bl.clsActivity;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.clsHardCode;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.clsHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenOnline;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsDisplayPicture;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLastCheckingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLeaveData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsPushData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsTrackingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmConfig;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmVersionApp;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsAbsenDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsAbsenOnlineRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsDisplayPictureRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsLastCheckingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsLeaveDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsTrackingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsUserLoginRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsmConfigRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsmVersionAppRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.enumConfigData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment.FragmentAbsen;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment.FragmentInformation;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment.FragmentLeave;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment.FragmentReport;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Service.MyServiceNative;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Service.MyTrackingLocationService;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import gun0912.tedbottompicker.TedBottomPicker;

import static com.android.volley.VolleyLog.TAG;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private clsUserLogin dttAbsenUserData;
    SweetAlertDialog pDialog;
    clsDisplayPictureRepo displayPictureRepo;
    List<clsDisplayPicture> dataImageProfile = null;
    private TextView tvUsername, tvEmail;
    private CircleImageView ivProfile;
    private static byte[] phtProfile;
    private static Bitmap photoProfile, mybitmapImageProfile;
    PackageInfo pInfo = null;
    private Uri uriImage, selectedImage;
    final int PIC_CROP_PROFILE = 5;
    int selectedId;
    private static ByteArrayOutputStream output = new ByteArrayOutputStream();
    private static int menuId = 0;
    Boolean isSubMenu = false;
    private static final int CAMERA_REQUEST_PROFILE = 120;
    private static final String IMAGE_DIRECTORY_NAME = "Image Personal";
    final int SELECT_FILE_PROFILE = 6;
    clsMainActivity _clsMainActivity = new clsMainActivity();
    List<clsUserLogin> dataLogin= null;
    List<clsmVersionApp> dataInfo = null;
    String[] listMenu;
    String[] linkMenu;
    private LocationHelper locationHelper;
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
    public void showAlertDialogAndExitApp(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(MainMenu.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialog.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationHelper = new LocationHelper(this);
        selectedId = 0;
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        if(new DeviceUtils().isDeviceRooted(getApplicationContext())){
            showAlertDialogAndExitApp("This device is rooted. You can't use this app.");
        }
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
        String extra = getIntent().getStringExtra("keyMainMenu");
        Bundle bundle = new Bundle();
        bundle.putString("keyMainMenu", extra);

        FragmentInformation homeFragment = new FragmentInformation();

        homeFragment.setArguments(bundle);
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

        phtProfile = null;

        if (photoProfile != null) {
            ivProfile.setImageBitmap(photoProfile);
            photoProfile.compress(Bitmap.CompressFormat.PNG, 100, output);
            phtProfile = output.toByteArray();
        }

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
            dataImageProfile = (List<clsDisplayPicture>) new clsDisplayPictureRepo(getApplicationContext()).findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            displayPictureRepo = new clsDisplayPictureRepo(getApplicationContext());
            dataImageProfile = (List<clsDisplayPicture>) displayPictureRepo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (dataImageProfile.size() > 0) {
            viewImageProfile();
        }
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //selectImageProfile();
                tedChooserPicture();
            }
        });

//        if (tDisplayPictureData.size() > 0 && tDisplayPictureData.get(0).getImage() != null) {
//            Bitmap bitmap = BitmapFactory.decodeByteArray(tDisplayPictureData.get(0).getImage(), 0, tDisplayPictureData.get(0).getImage().length);
//            ivProfile.setImageBitmap(bitmap);
//        } else {
//            ivProfile.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(),
//                    R.drawable.profile));
//        }
//        ivProfile.setOnClickListener(this);
        Menu header = navigationView.getMenu();
        clsAbsenData dataAbsenAktif = new clsAbsenDataRepo(getApplicationContext()).getDataCheckinActive(getApplicationContext());
//        if (dataAbsenAktif!=null){
//            header.removeItem(R.id.absen);
//            header.removeItem(R.id.logout);
//            header.removeItem(R.id.pushData);
//        }else{
//            header.removeItem(R.id.checkout);
//        }
        List<clsLastCheckingData> datasChecking = null;
        try {
            datasChecking = new clsLastCheckingDataRepo(getApplicationContext()).findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clsAbsenOnline dataAbsenOnline = new clsAbsenOnlineRepo(getApplicationContext()).getDataCheckinActive(getApplicationContext());
        if (dataAbsenOnline!=null){
            header.removeItem(R.id.absen);
            header.removeItem(R.id.logout);
//            header.removeItem(R.id.pushData);
            header.removeItem(R.id.leave);
        }else{
            header.removeItem(R.id.checkout);
        }
        if(datasChecking != null && datasChecking.size()>0){
            header.removeItem(R.id.leave);
        }
        try {
            List<clsAbsenOnline> dataAbsenOnline2 = new clsAbsenOnlineRepo(getApplicationContext()).findAll();
            if ((dataAbsenOnline2.size()>0)){
                header.removeItem(R.id.leave);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            List<clsLeaveData> dataLeave = new clsLeaveDataRepo(getApplicationContext()).findAll();
            if(dataLeave.size()>0){
                header.removeItem(R.id.absen);
                header.removeItem(R.id.leave);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                                            pushData2();
//                                            if (result){
//                                                logout();
//                                            }else{
//                                                new clsMainActivity().showCustomToast(getApplicationContext(),"Push Data to Logout", false);
////                                                logout();
//                                            }
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
                        FragmentTransaction fragmentTransactionAbsen = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionAbsen.replace(R.id.frame, fragmentAbsen);
                        fragmentTransactionAbsen.commit();
                        return true;
                    case R.id.leave:
                        toolbar.setTitle("Leave");
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        FragmentLeave fragmentLeave = new FragmentLeave();
                        FragmentTransaction fragmentTransactionLeave = getSupportFragmentManager().beginTransaction();
                        fragmentTransactionLeave.replace(R.id.frame, fragmentLeave);
                        fragmentTransactionLeave.commit();
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
//                    case R.id.pushData:
//                        toolbar.setTitle("Home");
//
//                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
//
//                        FragmentPushData fragmentPush = new FragmentPushData();
//                        Bundle arguments = new Bundle();
//                        arguments.putString( "key_view" , "main_menu");
//                        fragmentPush.setArguments(arguments);
//                        FragmentTransaction fragmentTransactionPushData = getSupportFragmentManager().beginTransaction();
//                        fragmentTransactionPushData.replace(R.id.frame, fragmentPush);
//                        fragmentTransactionPushData.commit();
//                        selectedId = 99;
//
//                        return true;
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
                                        String versionName = "";
                                        try {
                                            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                                        } catch (PackageManager.NameNotFoundException e2) {
                                            // TODO Auto-generated catch block
                                            e2.printStackTrace();
                                        }
                                        JSONObject resJson = new JSONObject();
                                        try {
                                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                            Calendar cal = Calendar.getInstance();
                                            String now = dateFormat.format(cal.getTime());

                                            resJson.put("txtTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
                                            resJson.put("guiId",dataAbsenOnline.getTxtGuiId());
                                            resJson.put("versionName",versionName);
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
                                                            String dtInserted = obj.getString("dtInserted");
                                                            data.setTxtGuiId(obj.getString("txtGUI_ID"));
                                                            data.setTxtGuiIdLogin(obj.getString("txtGuiIdLogin"));
                                                            data.setSync("1");
                                                            data.setIntSubmit("1");
                                                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
                                                            String dtCheckout = obj.getString("dtCheckout");
                                                            Calendar cal = Calendar.getInstance();
                                                            data.setDtCheckout(dateFormat.format(cal.getTime()));
                                                            clsLastCheckingData dataChekin = null;
                                                            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                                            Date date = null;
                                                            try {
                                                                date= sdf2.parse(dtCheckout);
                                                            } catch (ParseException e) {
                                                                e.printStackTrace();
                                                            }
                                                            try {
                                                                dataChekin = new clsLastCheckingDataRepo(getApplicationContext()).findByGUIId(txtGUI_ID);
                                                                dataChekin.setTxtGuiID(obj.getString("txtGUI_ID"));
                                                                dataChekin.setDtCheckout(date);
                                                                new clsLastCheckingDataRepo(getApplicationContext()).update(dataChekin);
//                                                                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                                                                // Vibrate for 500 milliseconds
//                                                                v.vibrate(500);
                                                            } catch (SQLException e) {
                                                                e.printStackTrace();
                                                            }

                                                            try {
                                                                new clsAbsenOnlineRepo(getApplicationContext()).update(data);
                                                                Intent myIntent = new Intent(getApplicationContext(), MainMenu.class);
                                                                myIntent.putExtra("checkout","true");
                                                                myIntent.putExtra("GUI",txtGUI_ID);
                                                                startActivity(myIntent);
                                                                finish();
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

//    @Override
//    protected void onResume() {
//        locationHelper.register();
//    }
//
//    @Override
//    protected void onPause() {
//        locationHelper.unregister();
//    }

    private void selectImageProfile() {

        final CharSequence[] items = { "Ambil Foto", "Pilih dari Galeri",
                "Batal" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(MainMenu.this);
                if (items[item].equals("Ambil Foto")) {
                    if(result)
                        captureImageProfile();
                } else if (items[item].equals("Pilih dari Galeri")) {
                    if(result)
                        galleryIntentProfile();
                } else if (items[item].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void performCropProfile(){
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(uriImage, "image/*");


//            List<ResolveInfo> list = getPackageManager().queryIntentActivities(intent, 0);
//            int size = list.size();
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP_PROFILE);
        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    private Bitmap decodeUriAsBitmap(Uri uri){
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
    protected void viewImageProfile() {
        try {
            displayPictureRepo = new clsDisplayPictureRepo(getApplicationContext());
            dataImageProfile = (List<clsDisplayPicture>) displayPictureRepo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        File folder = new File(Environment.getExternalStorageDirectory().toString() + "/data/data/KalbeAbsenHR/tempdata/Foto_Profil");
        folder.mkdir();

        for (clsDisplayPicture imgDt : dataImageProfile){
            final byte[] imgFile = imgDt.getImage();
            if (imgFile != null) {
                mybitmapImageProfile = BitmapFactory.decodeByteArray(imgFile, 0, imgFile.length);
                Bitmap bitmap = Bitmap.createScaledBitmap(mybitmapImageProfile, 150, 150, true);
                ivProfile.setImageBitmap(bitmap);
            }
        }
    }
    private void previewCaptureImageProfile(Bitmap photo){
        try {
            Bitmap bitmap = new clsActivity().resizeImageForBlob(photo);
            ivProfile.setVisibility(View.VISIBLE);
            output = null;
            try {
                output = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, output);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (output != null){
                        output.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Bitmap photo_view = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
            phtProfile = output.toByteArray();
            ivProfile.setImageBitmap(photo_view);

            saveImageProfile();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    protected void saveImageProfile() {
        try {
            displayPictureRepo = new clsDisplayPictureRepo(getApplicationContext());
            dataImageProfile = (List<clsDisplayPicture>) displayPictureRepo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        clsDisplayPicture data = new clsDisplayPicture();
        data.setId(1);
        data.setImage(phtProfile);
//        new VolleyUtils().makeJsonObjectRequestPushData(getApplicationContext(), linkPushData, dtJson, new VolleyResponseListener() {
//            @Override
//            public void onError(String message) {
//                String error;
//            }
//
//            @Override
//            public void onResponse(String response, Boolean status, String strErrorMsg) {
//                String res = response;
//
//                Log.i(TAG, "Ski data from server - " + response);
//                clsAbsenData absenData = new clsAbsenData();
//                clsTrackingData trackingData = new clsTrackingData();
////                clsUserLogin userLogin = new clsUserLogin();
//
//                try {
//                    JSONArray jsonArray1 = new JSONArray(response);
//                    for (int i = 0; i < jsonArray1.length(); i++) {
//                        JSONObject method = jsonArray1.getJSONObject(i);
//                        String listMethod = method.getString("PstrMethodRequest");
//                        if (listMethod.equals(trackingData.Property_ListOftrackingLocation)) {
//                            if (method.getString("pBoolValid").equals("1")) {
//                                new clsTrackingDataRepo(getApplicationContext()).updateAllRowTracking();
//                                result[0] = true;
//                            }
//                        }
////                                if (listMethod.equals(absenData.Property_ListOftAbsenUser)) {
////                                    if (method.getString("pBoolValid").equals("1")) {
////                                        new clsAbsenDataRepo(getApplicationContext()).updateAllRowAbsen();
////                                        result[0] = true;
////                                    }
////                                }
//                        if (result[0]){
//                            logout();
//                        }
//                    }
//                    /*for(Object data : jsonObject1){
//
//                    }*/
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        try {
            int i = new clsDisplayPictureRepo(getApplicationContext()).createOrUpdate(data);
            finish();
            startActivity(new Intent(this, MainMenu.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Image Profile Saved", Toast.LENGTH_SHORT).show();
    }
    private void galleryIntentProfile() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , SELECT_FILE_PROFILE);//one can be replaced with any action code
    }
    protected void captureImageProfile() {
        uriImage = getOutputMediaFileUri();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_PROFILE);
    }
    protected void tedChooserPicture(){
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(MainMenu.this)
                .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        // here is selected uri
                        uriImage = uri;
                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                        performCropProfile();
//                        Bitmap bitmap;
//                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//                        bitmap = BitmapFactory.decodeFile(uri2, bitmapOptions);
//
//                        performCropProfile();
                    }
                })
                .create();

        tedBottomPicker.show(getSupportFragmentManager());
    }
    @Override
    @SuppressLint({"NewApi"})
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_PROFILE) {
            if (resultCode == -1) {
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    String uri = uriImage.getPath().toString();

                    bitmap = BitmapFactory.decodeFile(uri, bitmapOptions);

                    performCropProfile();

//                    previewCaptureImage2(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (resultCode == 0) {
                Toast.makeText(getApplicationContext(), "User cancel take image", Toast.LENGTH_SHORT).show();
            }  else {
                try {
                    photoProfile = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if (requestCode == PIC_CROP_PROFILE) {
            if (resultCode == -1) {
                //get the returned data
                Bundle extras = data.getExtras();
                //get the cropped bitmap
                Bitmap thePic = null;
//                if  (extras != null){
//                    thePic = extras.getParcelable("data");
//                }else{
//                    Uri uri = data.getData();
//                    thePic = decodeUriAsBitmap(uri);
//                }
                Uri uri = data.getData();
                if (uri != null){
                    thePic = decodeUriAsBitmap(uri);
                }
                if  (extras != null){
                    Bitmap tempBitm = extras.getParcelable("data");
                    if (tempBitm != null){
                        thePic = tempBitm;
                    }
                }
                previewCaptureImageProfile(thePic);
            } else if (resultCode == 0) {
                Toast.makeText(getApplicationContext(), "User cancel take image", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == SELECT_FILE_PROFILE) {
            if(resultCode == RESULT_OK){
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    selectedImage = data.getData();
                    String uri = selectedImage.getPath().toString();
                    bitmap = BitmapFactory.decodeFile(uri, bitmapOptions);

                    performCropGalleryProfile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

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
            clsmConfig configData = null;
            try {
                String linkPushData= "";
                try {
                    configData = (clsmConfig) new clsmConfigRepo(getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
                    linkPushData = configData.getTxtValue()+new clsHardCode().linkPushData;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                new VolleyUtils().makeJsonObjectRequestPushData(getApplicationContext(), linkPushData, dtJson, new VolleyResponseListener() {
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
//                                if (listMethod.equals(absenData.Property_ListOftAbsenUser)) {
//                                    if (method.getString("pBoolValid").equals("1")) {
//                                        new clsAbsenDataRepo(getApplicationContext()).updateAllRowAbsen();
//                                        result[0] = true;
//                                    }
//                                }
                                if (result[0]){
                                    logout();
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
    private void performCropGalleryProfile(){
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(selectedImage, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP_PROFILE);
        }
        catch(ActivityNotFoundException anfe){
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    private Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //use this if Lollipop_Mr1 (API 22) or above
//            return FileProvider.getUriForFile(this,getPackageName()+".provider", getOutputMediaFile());
//        } else {
//            return Uri.fromFile(getOutputMediaFile());
//        }
    }
    private File getOutputMediaFile() {
        // External sdcard location

        File mediaStorageDir = new File(new clsHardCode().txtFolderData + File.separator);
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "tmp_act"  + ".png");
        return mediaFile;
    }
    public static class Utility {
        public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public static boolean checkPermission(final Context context)
        {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if(currentAPIVersion>= Build.VERSION_CODES.M)
            {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle("Permission necessary");
                        alertBuilder.setMessage("External storage permission is necessary");
                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }
    }

    private void pushData2() {
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
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        clsPushData dtJson = new clsHelper().pushData(versionName, getApplicationContext());
        if (dtJson == null) {
        } else {
            try {
//                pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                clsmConfig configData = null;
                String linkPushData= "";
                try {
                    configData = (clsmConfig) new clsmConfigRepo(getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
                    linkPushData = configData.getTxtValue()+new clsHardCode().linkPushData;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String strLinkAPI = linkPushData;
                new VolleyUtils().makeJsonObjectRequestPushData(getApplicationContext(), strLinkAPI, dtJson, new VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        String error;
                        new clsMainActivity().showCustomToast(getApplicationContext(),"Push Data Failed, Check Your Network",false);
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
                                            logout();
                                        }else{
                                            valid = false;
                                        }
                                    }
                                }
                            }else{
                                new clsMainActivity().showCustomToast(getApplicationContext(),"Push Data Completed",true);
                                logout();
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
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        clsmConfig configData = null;
        String linkPushData= "";
        try {
            configData = (clsmConfig) new clsmConfigRepo(getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
            linkPushData = configData.getTxtValue()+new clsHardCode().linkLogout;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String versionName = "";
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        String strLinkAPI = linkPushData;
//        String nameRole = selectedRole;
        final JSONObject resJson = new JSONObject();
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = dateFormat.format(cal.getTime());
        try {
            resJson.put("TxtGUI_trUserLogin", dataLogin.get(0).getTxtGUI());
            resJson.put("TxtUserID", dataLogin.get(0).getTxtUserID());
            resJson.put("versionName",versionName);
            resJson.put("TxtGUI_mVersionApp", dataInfo.get(0).getTxtVersion());
            resJson.put("IntCabangID", dataLogin.get(0).getIntCabangID());
            resJson.put("TxtTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = "[" + resJson.toString() + "]";
        StringRequest req1 = new VolleyUtils().makeJsonObjectRequest222(MainMenu.this, strLinkAPI, mRequestBody,"Logging Out, Please Wait !", new VolleyResponseListener() {
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
                        String warn = jsonObject1.getString("TxtWarn");
                        String result = jsonObject1.getString("TxtResult");
                        if (result.equals("1")){
//                            new DatabaseHelper(getApplicationContext()).clearDataAfterLogout();
                            DatabaseHelper helper = DatabaseManager.getInstance().getHelper();
                            stopService(new Intent(getApplicationContext(), MyTrackingLocationService.class));
                            helper.clearDataAfterLogout();
//                            helper.close();
                            Intent nextScreen = new Intent(MainMenu.this, Splash.class);
                            startActivity(nextScreen);
                            finish();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        //VolleyMultipartRequest req2 = pushData2();
        //queue.add(req2);
        queue.add(req1);
    }

//    private VolleyMultipartRequest pushData2() {
//        String versionName = "";
//        VolleyMultipartRequest req2= null;
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//        pDialog.setTitleText("Pushing Data");
//        pDialog.setCancelable(false);
//        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//
//            }
//        });
//        pDialog.show();
//        try {
//            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
//        } catch (PackageManager.NameNotFoundException e2) {
//            // TODO Auto-generated catch block
//            e2.printStackTrace();
//        }
//        clsPushData dtJson = new clsHelper().pushData(versionName, getApplicationContext());
//        if (dtJson == null) {
//        } else {
//            try {
////                pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
//                clsmConfig configData = null;
//                String linkPushData= "";
//                try {
//                    configData = (clsmConfig) new clsmConfigRepo(getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
//                    linkPushData = configData.getTxtValue()+new clsHardCode().linkPushData;
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                String strLinkAPI = linkPushData;
//                req2 = new VolleyUtils().makeJsonObjectRequestPushData222(getApplicationContext(), strLinkAPI, dtJson, new VolleyResponseListener() {
//                    @Override
//                    public void onError(String message) {
//                        String error;
//                        new clsMainActivity().showCustomToast(getApplicationContext(),"Push Data Failed, Check Your Network",false);
//                        pDialog.dismiss();
//                    }
//
//                    @Override
//                    public void onResponse(String response, Boolean status, String strErrorMsg) {
//                        String res = response;
//
//                        Log.i(TAG, "Ski data from server - " + response);
//                        clsAbsenData absenData = new clsAbsenData();
//                        clsTrackingData trackingData = new clsTrackingData();
////                clsUserLogin userLogin = new clsUserLogin();
//                        try {
//                            JSONArray jsonArray1 = new JSONArray(response);
//                            boolean valid = false;
//                            if (jsonArray1.length()>1){
//                                for (int i = 0; i < jsonArray1.length(); i++) {
//                                    JSONObject method = jsonArray1.getJSONObject(i);
//                                    String listMethod = method.getString("PstrMethodRequest");
//                                    if (listMethod.equals(trackingData.Property_ListOftrackingLocation)) {
//                                        if (method.getString("pBoolValid").equals("1")) {
//                                            //new DatabaseHelper(getActivity().getApplicationContext()).clearDataTracking();
//                                            new clsTrackingDataRepo(getApplicationContext()).updateAllRowTracking();
//                                            valid = true;
//                                        }else{
//                                            valid = false;
//                                        }
//                                    }
//                                    if (listMethod.equals(absenData.Property_ListOftAbsenUser)) {
//                                        if (method.getString("pBoolValid").equals("1")) {
//                                            new DatabaseHelper(getApplicationContext()).clearDataAbsen();
//                                            valid = true;
//                                        }else{
//                                            valid = false;
//                                        }
//                                    }
//
//                                }
//                            }else{
//
//                            }
//                            pDialog.dismiss();
//
//                    /*for(Object data : jsonObject1){
//
//                    }*/
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//        return req2;
//    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.profile_image:
//                pickImage2();
//                break;
//        }
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
                System.exit(0);
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