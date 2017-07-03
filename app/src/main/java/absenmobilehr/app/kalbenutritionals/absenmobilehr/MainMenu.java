package absenmobilehr.app.kalbenutritionals.absenmobilehr;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseManager;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyResponseListener;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyUtils;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsDeviceInfo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsDisplayPicture;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsDeviceInfoRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsUserLoginRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment.FragmentInformation;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private clsUserLogin dttAbsenUserData;

    private TextView tvUsername, tvEmail;
    private CircleImageView ivProfile;
    private clsDisplayPicture tDisplayPictureData;

    PackageInfo pInfo = null;

    int selectedId;
    private static int menuId = 0;
    Boolean isSubMenu = false;

    clsMainActivity _clsMainActivity = new clsMainActivity();

    String[] listMenu;
    String[] linkMenu;

    private GoogleApiClient client;

    String i_view = null;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        fragmentTransactionHome.replace(R.id.frame, homeFragment);
        fragmentTransactionHome.commit();

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
                                        DatabaseHelper helper = DatabaseManager.getInstance().getHelper();
                                        helper.close();
                                        helper.clearAllDataInDatabase();
                                        logout();
//                                        stopService(new Intent(getApplicationContext(), MyServiceNative.class));
//                                        stopService(new Intent(getApplicationContext(), MyTrackingLocationService.class));
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
                }
                return false;
            }
        });
    }
    public void logout(){
        final ProgressDialog Dialog = new ProgressDialog(MainMenu.this);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        clsUserLoginRepo repoUserLogin = null;
        clsDeviceInfoRepo repoDeviceInfo = null;
        String strLinkAPI = "http://10.171.10.30/KN2015_PRM_V2.WEB/VisitPlan/API/VisitPlanAPI/LogOut_J";
//        String nameRole = selectedRole;
        JSONObject resJson = new JSONObject();
        List<clsUserLogin> dataLogin = null;
        dataLogin = (List<clsUserLogin>) repoUserLogin.findAll();
        List<clsDeviceInfo> dataInfo = null;
        try {
            dataInfo = (List<clsDeviceInfo>) repoDeviceInfo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            resJson.put("TxtGUI_trUserLogin", dataLogin.get(0).getTxtGUI());
            resJson.put("TxtUserID", dataLogin.get(0).getTxtUserID());
            resJson.put("TxtGUI_mVersionApp", dataInfo.get(0).getTxtVersion());
            resJson.put("IntCabangID", dataLogin.get(0).getCabangId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = "[" + resJson.toString() + "]";
        new VolleyUtils().makeJsonObjectRequest(MainMenu.this, strLinkAPI, mRequestBody, new VolleyResponseListener() {
            @Override
            public void onError(String response) {
                new clsMainActivity().showCustomToast(getApplicationContext(), response, false);
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                if (response != null) {

                }
                /*
                if (response != null) {
                    try {
                        JSONObject jsonObject1 = new JSONObject(response);
                        String result = jsonObject1.getString("TxtResult");
                        String warn = jsonObject1.getString("TxtWarn");
                        if (result.equals("1")) {
                            JSONObject jsonObject2 = jsonObject1.getJSONObject("TxtData");
                            JSONObject jsonDataUserLogin = jsonObject2.getJSONObject("UserLogin");
                            String TxtNameApp = jsonDataUserLogin.getString("TxtNameApp");
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
                            data.setTxtNameApp(TxtNameApp);
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
                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Calendar cal = Calendar.getInstance();
                            data.setDtLastLogin(DtLastLogin);
                            data.setTxtDeviceId(TxtDeviceId);
                            data.setTxtInsertedBy(TxtInsertedBy);
                            data.setDtInserted(dateFormat.format(cal.getTime()));

                            repoLogin =new clsUserLoginRepo(getApplicationContext());
                            int i = 0;
                            i = repoLogin.create(data);
                            if(i > -1)
                            {
                                Log.d("Data info", "Data info berhasil di simpan");
                                Intent myIntent = new Intent(Login.this, MainMenu.class);
                                myIntent.putExtra("keyMainMenu", "main_menu");
                                startActivity(myIntent);
                            }else{
                                new clsMainActivity().showCustomToast(getApplicationContext(),warn,false);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(!status){
                    new clsMainActivity().showCustomToast(getApplicationContext(), strErrorMsg, false);
                }*/
            }
        });
    }

    @Override
    public void onClick(View v) {

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