package absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyResponseListener;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyUtils;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.bl.clsMainBL;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.clsHardCode;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenOnline;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsBranchAccess;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsDeviceInfo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsGetOutletDataHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLastCheckingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmConfig;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsAbsenDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsAbsenOnlineRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsBranchAccessRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsDeviceInfoRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsLastCheckingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsUserLoginRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsmConfigRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.enumConfigData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.MainMenu;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.R;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.clsMainActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import jim.h.common.android.lib.zxing.config.ZXingLibConfig;
import jim.h.common.android.lib.zxing.integrator.IntentIntegrator;
import jim.h.common.android.lib.zxing.integrator.IntentResult;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class FragmentAbsen extends Fragment implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private Location mLastLocation;
    boolean mockStatus = false;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private static final String TAG = FragmentAbsen.class.getSimpleName();
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;
    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 3000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters
    private Spinner spnOutlet;
    private Context context = null;
    private List<String> arrData;
    private String[] arrData2;
    private String Long;
    private String Lat;
    private String Acc;
    private static final int CAMERA_CAPTURE_IMAGE1_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_IMAGE2_REQUEST_CODE = 130;
    private ImageView imgPrevNoImg1;
    private ImageView imgPrevNoImg2;
    private ImageView imgScaner;
    private static final String IMAGE_DIRECTORY_NAME = "Image Sales";
    private HashMap<String, String> HMbranch = new HashMap<String, String>();
    private HashMap<String, String> HMbranchCode = new HashMap<String, String>();
    private HashMap<String, String> HMoutletId = new HashMap<String, String>();
    private HashMap<String, String> HMoutletLong = new HashMap<String, String>();
    private HashMap<String, String> HMoutletLat = new HashMap<String, String>();
    private HashMap<String, String> HMoutletMappingHeader = new HashMap<String, String>();
    private HashMap<String, String> HMoutletMappingDetail = new HashMap<String, String>();
    final String finalFile = null;
    private TextView lblLong;
    private TextView lblLang;
    private TextView tvLongOutlet;
    private TextView tvLatOutlet;
    private TextView lblDistance;
    private TextView lblAcc;
    private TextView txtHDId;
    private ArrayAdapter<String> dataAdapterBranch;
    private ArrayAdapter<String> dataAdapterOutlet;
    private ListView lvBranch;
    Options options;
    private Button btnRefreshMaps, btnPopupMap;
    private Button btnCheckIn;
    private PackageInfo pInfo = null;
    private String MenuID;
    private String[] arrdefaultBranch = new String[]{"Branch"};
    private String[] arrdefaultOutlet = new String[]{"Outlet"};
    private clsAbsenData _clsAbsenData = null;

    private String nameBranch;
    private String nameOutlet;
    private String branchCode;
    private String outletCode;
    private String myClass;
    private Class<?> clazz = null;

    private ZXingLibConfig zxingLibConfig;
    private Uri uriImage;
    private int countActivity;
    List<clsUserLogin> dataLogin = null;
    List<clsDeviceInfo> dataDevice = null;

    clsMainActivity _clsMainActivity = new clsMainActivity();

    View v;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public static Bitmap resizeBitMapImage1(String filePath, int targetWidth, int targetHeight) {
        Bitmap bitMapImage = null;
        try {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);
            double sampleSize = 0;
            Boolean scaleByHeight = Math.abs(options.outHeight - targetHeight) >= Math.abs(options.outWidth
                    - targetWidth);
            if (options.outHeight * options.outWidth * 2 >= 1638) {
                sampleSize = scaleByHeight ? options.outHeight / targetHeight : options.outWidth / targetWidth;
                sampleSize = (int) Math.pow(2d, Math.floor(Math.log(sampleSize) / Math.log(2d)));
            }
            options.inJustDecodeBounds = false;
            options.inTempStorage = new byte[128];
            while (true) {
                try {
                    options.inSampleSize = (int) sampleSize;
                    bitMapImage = BitmapFactory.decodeFile(filePath, options);
                    break;
                } catch (Exception ex) {
                    try {
                        sampleSize = sampleSize * 2;
                    } catch (Exception ex1) {

                    }
                }
            }
        } catch (Exception ex) {

        }
        return bitMapImage;
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Absen Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://absenmobilehr.app.kalbenutritionals.absenmobilehr/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public class MyAdapter extends ArrayAdapter<String> {
        private List<String> arrayDataAdapyter;
        private Context Ctx;

        public List<String> getArrayDataAdapyter() {
            return arrayDataAdapyter;
        }

        public void setArrayDataAdapyter(List<String> arrayDataAdapyter) {
            this.arrayDataAdapyter = arrayDataAdapyter;
        }

        public Context getCtx() {
            return Ctx;
        }

        public void setCtx(Context ctx) {
            Ctx = ctx;
        }

        public MyAdapter(Context context, int   textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            setCtx(context);
            setArrayDataAdapyter(objects);
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
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View row = inflater.inflate(R.layout.custom_spinner, parent, false);
            if (arrData.size() > 0) {
                TextView label = (TextView) row.findViewById(R.id.tvTitle);
                //label.setText(arrData.get(position));
                label.setText(getArrayDataAdapyter().get(position));
                TextView sub = (TextView) row.findViewById(R.id.tvDesc);
                sub.setVisibility(View.GONE);
                sub.setVisibility(View.GONE);
                label.setTextColor(new Color().parseColor("#000000"));
                row.setBackgroundColor(new Color().parseColor("#FFFFFF"));
            }
            return row;
        }

    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_absen, container, false);
        mockStatus = false;
        getLocation();
        final String GuiID = new clsMainBL().GenerateGuid();
        txtHDId = (TextView) v.findViewById(R.id.txtHDId);
        btnRefreshMaps = (Button) v.findViewById(R.id.btnRefreshMaps);
        btnCheckIn = (Button) v.findViewById(R.id.buttonCheckIn);
        btnPopupMap = (Button) v.findViewById(R.id.viewMap);
        imgPrevNoImg1 = (ImageView) v.findViewById(R.id.imageViewCamera1);
        imgPrevNoImg2 = (ImageView) v.findViewById(R.id.imageViewCamera2);
        spnOutlet = (Spinner) v.findViewById(R.id.spnOutlet);
        lblLong = (TextView) v.findViewById(R.id.tvLong);
        lblLang = (TextView) v.findViewById(R.id.tvLat);
        lblAcc = (TextView) v.findViewById(R.id.tvAcc);
        lblDistance = (TextView) v.findViewById(R.id.tvDis);
        imgScaner = (ImageView) v.findViewById(R.id.imageScanner);
        tvLongOutlet = (TextView) v.findViewById(R.id.tvLongOutlet);
        tvLatOutlet = (TextView) v.findViewById(R.id.tvLatOutlet);
        options = new Options();
        options.inSampleSize = 2;
        context = getActivity().getApplicationContext();
        clsUserLoginRepo repoLogin = new clsUserLoginRepo(getActivity().getApplicationContext());
        clsDeviceInfoRepo repoDevice = new clsDeviceInfoRepo(getActivity().getApplicationContext());
        lblLong.setText("");
        lblLang.setText("");
        lblAcc.setText("");
        lblDistance.setText("");
        lvBranch = (ListView) v.findViewById(R.id.listBranch);
        lvBranch.setVisibility(View.GONE);
        try {
            List<clsBranchAccess> branches = new clsBranchAccessRepo(context).findAll();
            int arr = 0;
            arrData2 = new String[branches.size()];
            if (branches.size()>0){
                for(clsBranchAccess dt : branches){
                    arrData2[arr]= dt.getTxtBranchName();
                    arr++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final List<String> listBranch = new ArrayList<String>(Arrays.asList(arrData2));
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_listitem, listBranch);
        final ArrayAdapter<String> adapterAA = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, listBranch);
        lvBranch.setAdapter(arrayAdapter);
        setListViewHeightBasedOnChildren(lvBranch);
        try {
            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        imgScaner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spnOutlet.getSelectedItem()!= null){
                    IntentIntegrator.initiateScan(getActivity(), zxingLibConfig);
                }else{
                    getOutlet();
                }

            }
        });
        try {
            dataLogin = (List<clsUserLogin>) repoLogin.findAll();
            dataDevice = (List<clsDeviceInfo>) repoDevice.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//            _clsAbsenData = new clsAbsenDataRepo(getActivity().getApplicationContext()).getDataCheckinActive(getActivity().getApplicationContext());

        MenuID = "mnAbsenKBN";

        spnOutlet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
//                    tvLongOutlet.setText(listDataArea.get(i).get_txtLongitude().equals("") || listDataArea.get(i).get_txtLongitude().equals("null") ? "Not Found" : listDataArea.get(i).get_txtLongitude());
//                    tvLatOutlet.setText(listDataArea.get(i).get_txtLatitude().equals("") || listDataArea.get(i).get_txtLatitude().equals("null") ? "Not Found" : listDataArea.get(i).get_txtLatitude());
                    String outletName = spnOutlet.getSelectedItem().toString();
                    double longitudeOutlet = Double.parseDouble(HMoutletLong.get(outletName));
                    double latitudeOutlet = Double.parseDouble(HMoutletLat.get(outletName));
                    tvLongOutlet.setText(longitudeOutlet+"");
                    tvLatOutlet.setText(latitudeOutlet+"");

                    getLocation();
                    if (longitudeOutlet != 0 && latitudeOutlet != 0) {
                        countDistance(latitudeOutlet, longitudeOutlet);
                        displayLocation(mLastLocation);
                        getLocation();
//                        Location myLocation = getLastKnownLocation();
                        if (mLastLocation != null) {
                            displayLocation(mLastLocation);
                        }
                    }
                } catch (Exception ex) {
                    new clsMainActivity().showCustomToast(getContext(), "Outlet location not found", false);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        final mMenuData dtmenuData = new mMenuBL().getMenuDataByMenuName(MenuID);
        btnRefreshMaps.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                getOutlet();
                displayLocation(mLastLocation);
                getLocation();
                if (mLastLocation != null) {
                    displayLocation(mLastLocation);
                }
                new clsMainActivity().showCustomToast(getContext(), "Location Updated", true);
            }
        });

        getLocation();

        if (mLastLocation != null) {
            displayLocation(mLastLocation);
        }
//        getOutlet();
        btnPopupMap.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean valid = true;
                btnPopupMap.setClickable(false);
                double latitude = 0;
                double longitude = 0;
//                double latitudeOutlet = 0;
//                double longitudeOutlet = 0;

                try {
                    if (mLastLocation != null){
                        latitude = mLastLocation.getLatitude();
                        longitude = mLastLocation.getLongitude();
                    }else{
                        latitude = Double.parseDouble(String.valueOf(lblLang.getText()));
                        longitude = Double.parseDouble(String.valueOf(lblLong.getText()));
                    }

                } catch (Exception ex) {
                    valid = false;
                    new clsMainActivity().showCustomToast(getContext(), "Your location not found", false);
                }

                /*if (valid) {
                    try {
                        latitudeOutlet = Double.parseDouble(HMoutletLat.get(spnOutlet.getSelectedItem().toString()));
                        longitudeOutlet = Double.parseDouble(HMoutletLong.get(spnOutlet.getSelectedItem().toString()));
                    } catch (Exception ex) {
                        valid = false;

                        new clsMainActivity().showCustomToast(getContext(), "Outlet location not found", false);
                    }
                }*/

                if (valid) {

                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());

//                    MapFragment f = (MapFragment) (getActivity()).getFragmentManager().findFragmentById(R.id.map);
//                    if (f != null) {
//                        (getActivity()).getFragmentManager().beginTransaction().remove(f).commit();
//                    }

                    View promptView = layoutInflater.inflate(R.layout.popup_map_absen, null);

                    GoogleMap mMap;
                    mMap = ((MapFragment) (getActivity()).getFragmentManager().findFragmentById(R.id.map)).getMap();

                    if (mMap == null) {
                        mMap = ((MapFragment) (getActivity()).getFragmentManager().findFragmentById(R.id.map)).getMap();
                    }

                    MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Your Location");

//                    String outletName = spnOutlet.getSelectedItem().toString();
//                    MarkerOptions markerOutlet = new MarkerOptions().position(new LatLng(latitudeOutlet, longitudeOutlet)).title("Outlet Location").snippet(outletName);


                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

                    final LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(marker.getPosition());
//                    builder.include(markerOutlet.getPosition());
                    LatLngBounds bounds = builder.build();

                    mMap.clear();
                    mMap.addMarker(marker);
//                    mMap.addMarker(markerOutlet);
//                    PolylineOptions rectOptions = new PolylineOptions().add(new LatLng(latitude, longitude))
//                            .add(new LatLng(latitudeOutlet, longitudeOutlet));
//                    Polyline polyline = mMap.addPolyline(rectOptions);
                    //CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(19).build();

                    final GoogleMap finalMMap = mMap;
                    mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

                        @Override
                        public void onCameraChange(CameraPosition arg0) {
                            // Move camera.
                            finalMMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 60));
                            // Remove listener to prevent position reset on camera move.
                            finalMMap.setOnCameraChangeListener(null);
                        }
                    });

                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(getContext());
                    alertDialogBuilder.setView(promptView);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            MapFragment f = (MapFragment) (getActivity()).getFragmentManager().findFragmentById(R.id.map);
                                            if (f != null) {
                                                (getActivity()).getFragmentManager().beginTransaction().remove(f).commit();
                                            }
                                            btnPopupMap.setClickable(true);
                                            dialog.dismiss();
                                        }
                                    });
                    final android.support.v7.app.AlertDialog alertD = alertDialogBuilder.create();

                    Location locationA = new Location("point A");

                    locationA.setLatitude(latitude);
                    locationA.setLongitude(longitude);

                    Location locationB = new Location("point B");

//                    locationB.setLatitude(latitudeOutlet);
//                    locationB.setLongitude(longitudeOutlet);

//                    float distance = locationA.distanceTo(locationB);
//
//                    alertD.setTitle("Distance : "+String.valueOf((int) Math.ceil(distance)) + " meters");
                    alertD.setTitle("Your Position");
                    alertD.show();
                }


            }
        });
//        btnPopupMap.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
//                final View promptView = layoutInflater.inflate(R.layout.popup_map_absen, null);
//                btnPopupMap.setEnabled(false);
//                GoogleMap mMap = null;
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                    mMap = ((MapFragment) (getActivity()).getFragmentManager().findFragmentById(R.id.map)).getMap();
//
//                    if (mMap == null) {
//                        mMap = ((MapFragment) (getActivity()).getFragmentManager().findFragmentById(R.id.map)).getMap();
//                    }
//
//                    double latitude = Double.parseDouble(String.valueOf(lblLang.getText()));
//                    double longitude = Double.parseDouble(String.valueOf(lblLong.getText()));
//
//                    MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Your Location");
//
//                    marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//
//                    final LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                    builder.include(marker.getPosition());
//                    LatLngBounds bounds = builder.build();
//
//                    mMap.clear();
//                    mMap.addMarker(marker);
//                    //CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(19).build();
//
//                    final GoogleMap finalMMap = mMap;
//                    mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
//
//                        @Override
//                        public void onCameraChange(CameraPosition arg0) {
//                            // Move camera.
//                            finalMMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 60));
//                            // Remove listener to prevent position reset on camera move.
//                            finalMMap.setOnCameraChangeListener(null);
//                        }
//                    });
//
//
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
//                    alertDialogBuilder.setView(promptView);
//                    alertDialogBuilder
//                            .setCancelable(false)
//                            .setPositiveButton("OK",
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            btnPopupMap.setEnabled(true);
//                                            MapFragment f = null;
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                                                f = (MapFragment) (getActivity()).getFragmentManager().findFragmentById(R.id.map);
//                                            }
//                                            if (f != null) {
//                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                                                    (getActivity()).getFragmentManager().beginTransaction().remove(f).commit();
//                                                }
//                                            }
//
//                                            dialog.dismiss();
//                                        }
//                                    });
//                    final AlertDialog alertD = alertDialogBuilder.create();
//
//                    Location locationA = new Location("point A");
//
//                    locationA.setLatitude(latitude);
//                    locationA.setLongitude(longitude);
//
//
////                    alertD.setTitle(String.valueOf((int) Math.ceil(distance)) + " meters");
//                    alertD.show();
//                }
//
//            }
//        });

//        List<mEmployeeBranchData> listDataBranch = new mEmployeeBranchBL().GetAllData();
//        List<mEmployeeAreaData> listDataArea = new mEmployeeAreaBL().GetAllData();
        if (checkPlayServices()) {
            buildGoogleApiClient();
        }

        // First we need to check availability of play services
        imgPrevNoImg1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String idUserActive = String.valueOf(dataLogin.get(0).getTxtUserID());
                String deviceInfo = String.valueOf(dataDevice.get(0).getTxtDevice());
                List<clsAbsenData> absenUserDatas = new ArrayList<clsAbsenData>();
                if (_clsAbsenData == null) {
                    _clsAbsenData = new clsAbsenData();
                }
                _clsAbsenData.setGuiId(GuiID);
                _clsAbsenData.setIntSubmit("0");
                _clsAbsenData.setSync("0");
                _clsAbsenData.setTxtAbsen("0");
                _clsAbsenData.setDtCheckout("-");
                _clsAbsenData.setTxtLatitude(lblLang.getText().toString());
                _clsAbsenData.setTxtLongitude(lblLong.getText().toString());
                _clsAbsenData.setTxtDeviceId(deviceInfo);
                _clsAbsenData.setTxtUserId(idUserActive);
                try {
                    new clsAbsenDataRepo(getActivity().getApplicationContext()).createOrUpdate(_clsAbsenData);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                captureImage1();
            }
        });

        imgPrevNoImg2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String idUserActive = String.valueOf(dataLogin.get(0).getTxtUserID());
                String deviceInfo = String.valueOf(dataDevice.get(0).getTxtDevice());
                List<clsAbsenData> absenUserDatas = new ArrayList<clsAbsenData>();
                if (_clsAbsenData == null) {
                    _clsAbsenData = new clsAbsenData();
                }
                _clsAbsenData.setGuiId(GuiID);
                _clsAbsenData.setIntSubmit("0");
                _clsAbsenData.setSync("0");
                _clsAbsenData.setTxtAbsen("0");
                _clsAbsenData.setDtCheckout("-");
                _clsAbsenData.setTxtLatitude(lblLang.getText().toString());
                _clsAbsenData.setTxtLongitude(lblLong.getText().toString());
                _clsAbsenData.setTxtDeviceId(deviceInfo);
                _clsAbsenData.setTxtUserId(idUserActive);
                try {
                    new clsAbsenDataRepo(getActivity().getApplicationContext()).createOrUpdate(_clsAbsenData);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                captureImage2();
            }
        });
        /*arrData = new ArrayList<String>();
        if (listDataBranch.size() > 0) {
            for (mEmployeeBranchData dt : listDataBranch) {
                arrData.add(dt.get_txtBranchName());
                HMbranch.put(dt.get_txtBranchName(), dt.get_txtBranchCode());
            }
            dataAdapterBranch = new MyAdapter(getContext(), R.layout.custom_spinner, arrData);
            spnBranch.setAdapter(dataAdapterBranch);
        }
        arrData = new ArrayList<String>();
        if (listDataArea.size() > 0) {
            for (mEmployeeAreaData dt : listDataArea) {
                arrData.add(dt.get_txtOutletName());
                HMoutletId.put(dt.get_txtOutletName(), dt.get_txtOutletCode());
                HMoutletLang.put(dt.get_txtOutletName(), dt.get_txtLongitude());
                HMoutletLat.put(dt.get_txtOutletName(), dt.get_txtLatitude());
            }
            dataAdapterOutlet = new MyAdapter(getContext(), R.layout.custom_spinner, arrData);
            spnOutlet.setAdapter(dataAdapterOutlet);
        }
*/
        if (_clsAbsenData != null) {
            if (_clsAbsenData.getIntSubmit().equals("1")) {
                imgPrevNoImg1.setClickable(false);
                imgPrevNoImg2.setClickable(false);
            }


            txtHDId.setText(_clsAbsenData.getGuiId());
            lblLang.setText(_clsAbsenData.getTxtLatitude());
            lblLong.setText(_clsAbsenData.getTxtLongitude());

            double latitude = Double.valueOf(lblLang.getText().toString());
            double longitude = Double.valueOf(lblLong.getText().toString());
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Updating Location!");
            marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            try {
                initilizeMap();
                // Changing map type
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            } catch (Exception e) {
                e.printStackTrace();
            }
            mMap.clear();
            mMap.addMarker(marker);
        } else {
            int IdAbsen = 0;
            try {
                IdAbsen = new clsAbsenDataRepo(context).getContactsCount(context) + 1;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            txtHDId.setText(String.valueOf(IdAbsen));
//            displayLocation();
        }

        // Checking camera availability
        if (!isDeviceSupportCamera()) {
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(getContext()).addApi(AppIndex.API).build();
        boolean isMockStatus = isMockSettingsON(context);

        if (mockStatus){
            imgScaner.setClickable(false);
            btnCheckIn.setClickable(false);
        }else{
            imgScaner.setClickable(true);
            btnCheckIn.setClickable(true);
        }
        // TODO: 24/01/2018 untuk checkin/absen action 
        btnCheckIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String langlbl = String.valueOf(lblLang.getText());
                String longlbl = String.valueOf(lblLong.getText());
                if  (longlbl.equals("")||langlbl.equals("")){
                    new clsMainActivity().showCustomToast(context,"Please turn on GPS",false);
                }else{
                    myClass = "absenmobilehr.app.kalbenutritionals.absenmobilehr.MainMenu";
                    ;
                    MenuID = "mnCheckinKBN";
                    clazz = null;

                    myClass = "absenmobilehr.app.kalbenutritionals.absenmobilehr.MainMenu";
                    MenuID = "mnCheckinKBN";
                    LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                    final View promptView = layoutInflater.inflate(R.layout.confirm_data, null);

                    final TextView _tvConfirm = (TextView) promptView.findViewById(R.id.tvTitle);
                    final TextView _tvDesc = (TextView) promptView.findViewById(R.id.tvDesc);
                    _tvDesc.setVisibility(View.INVISIBLE);
                    _tvConfirm.setText("Check In Data ?");
                    _tvConfirm.setTextSize(18);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                    alertDialogBuilder.setView(promptView);
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            final String guiId = new clsMainBL().GenerateGuid();
//                                        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//                                        if (scanResult.getContents() == null && scanResult.getFormatName() == null) {
//                                            return;
//                                        }
//                                        final String result = scanResult.getContents();
                                            final String result = "";
                                            Log.d("Data info", "Scanning Success result = "+result);
                                            clsmConfig configData = null;
                                            String linkCheckinData= "";
                                            try {
                                                configData = (clsmConfig) new clsmConfigRepo(getActivity().getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
                                                linkCheckinData = configData.getTxtValue()+new clsHardCode().linkAbsen;
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                            double latitude = Double.parseDouble(String.valueOf(lblLang.getText()));
                                            double longitude = Double.parseDouble(String.valueOf(lblLong.getText()));
//                                        final String txtOutlet = spnOutlet.getSelectedItem().toString();
//                                        String txtOutletId = HMoutletId.get(txtOutlet);
//                                        double latitudeOutlet = Double.parseDouble(HMoutletLat.get(txtOutlet));
//                                        double longitudeOutlet = Double.parseDouble(HMoutletLong.get(txtOutlet));
//                                        int mappingHeaderId = Integer.parseInt(HMoutletMappingHeader.get(txtOutlet));
//                                        int mappingDetailId = Integer.parseInt(HMoutletMappingDetail.get(txtOutlet));

                                            Location locationA = new Location("point A");
                                            Location locationB = new Location(("point B"));
                                            locationA.setLatitude(latitude);
                                            locationA.setLongitude(longitude);
//                                        locationB.setLongitude(longitudeOutlet);
//                                        locationB.setLatitude(latitudeOutlet);
                                            clsmConfig mradius = new clsmConfig();
                                            try {
                                                mradius = new clsmConfigRepo(context).findById(9);
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                            float distance = locationA.distanceTo(locationB);
//                                        if (distance>Integer.parseInt(mradius.getTxtValue())){
//                                            new clsMainActivity().showCustomToast(getActivity().getApplicationContext(),"You're too far from outlet",false);
//                                        }else{
                                            final clsUserLogin dataUserActive = new clsUserLoginRepo(context).getDataLogin(context);
                                            clsDeviceInfo dataDeviceInfoUser = new clsDeviceInfoRepo(context).getDataDevice(context);
                                            String strLinkAPI = linkCheckinData;
                                            String versionName = "";
                                            try {
                                                versionName = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName;
                                            } catch (PackageManager.NameNotFoundException e2) {
                                                // TODO Auto-generated catch block
                                                e2.printStackTrace();
                                            }
                                            JSONObject resJson = new JSONObject();
                                            try {
                                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                                Calendar cal = Calendar.getInstance();
                                                String now = dateFormat.format(cal.getTime());

                                                resJson.put("longitude",longitude);
                                                resJson.put("latitude",latitude);
                                                resJson.put("versionName",versionName);
//                                                resJson.put("outletId",txtOutletId);
//                                                resJson.put("outletName",txtOutlet);
                                                resJson.put("qrCode",result);
                                                resJson.put("txtTime", now);
//                                                resJson.put("mappingHeaderId",mappingHeaderId);
//                                                resJson.put("mappingDetailId",mappingDetailId);
                                                resJson.put("txtTimeDetail",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
                                                resJson.put("guiId",guiId);
                                                resJson.put("guiIdLogin",dataUserActive.getTxtGUI());
                                                resJson.put(dataUserActive.Property_employeeId,dataUserActive.getEmployeeId());
                                                resJson.put(dataUserActive.Property_txtUserName,dataUserActive.getTxtUserName());
                                                resJson.put(dataUserActive.Property_txtName,dataUserActive.getTxtName());
                                                resJson.put("deviceInfo",dataDeviceInfoUser.getTxtModel());
                                                resJson.put("deviceId",dataDeviceInfoUser.getIdDevice());
                                                resJson.put("TxtVersion", pInfo.versionName);
                                                resJson.put("TxtNameApp", dataUserActive.getTxtNameApp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            final String mRequestBody =  resJson.toString();
                                            new VolleyUtils().makeJsonObjectRequest(getActivity(), strLinkAPI, mRequestBody, "Checking in please wait !", new VolleyResponseListener() {
                                                @Override
                                                public void onError(String response) {
                                                    new clsMainActivity().showCustomToast(getActivity().getApplicationContext(),"Connection Failed, please check your network !", false);
//                Toast.makeText(getApplicationContext(), "Connection Failed, please check your network !", Toast.LENGTH_SHORT).show();
//                Log.d("connection failed", response);
                                                }

                                                @Override
                                                public void onResponse(String response, Boolean status, String strErrorMsg) {
                                                    if (response != null || !response.equals("false")) {
                                                        clsAbsenOnline data = new clsAbsenOnline();
                                                        clsLastCheckingData dataCheckin = new clsLastCheckingData();
                                                        if (response.equals("0")){
                                                            new clsMainActivity().showCustomToast(getActivity().getApplicationContext(),"Outlet doesn't match !", false);
                                                        }else if(response.equals("1")){
                                                            new clsMainActivity().showCustomToast(getActivity().getApplicationContext(),"Barcode Error, Check your outlet and try again !", false);
                                                        }else{
                                                            try {
                                                                JSONArray array = new JSONArray(response);

                                                                JSONObject obj = array.getJSONObject(0);
                                                                String txtGUI_ID = obj.getString("txtGUI_ID");
                                                                data.setTxtGuiId(obj.getString("txtGUI_ID"));
                                                                data.setTxtGuiIdLogin(obj.getString("txtGuiIdLogin"));
                                                                data.setTxtLatitude(obj.getString("txtLatitude"));
                                                                data.setTxtLongitude(obj.getString("txtLongitude"));
                                                                data.setTxtDeviceId(obj.getString("txtDeviceId"));
                                                                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
                                                                String dateCheckin = obj.getString("dtCheckin");
                                                                String dtInserted = obj.getString("dtInserted");
                                                                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                                                Date date = null;

                                                                try {
                                                                     date= sdf2.parse(dtInserted);
                                                                } catch (ParseException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                Calendar cal = Calendar.getInstance();
                                                                data.setDtCheckin(dateFormat.format(cal.getTime()));
//                                                                data.setQrCode(obj.getString("qrCode"));
//                                                                data.setQrId(obj.getString("qrCode"));
                                                                data.setSync("0");
                                                                data.setIntSubmit("1");

//                                                                dataCheckin.setTxtOutletName(txtOutlet);
                                                                dataCheckin.setTxtGuiID(obj.getString("txtGUI_ID"));
                                                                dataCheckin.setDtCheckin(date);
                                                                dataCheckin.setDtCheckout(null);
                                                                dataCheckin.setBoolMoodCheckout("0");
                                                                try {
                                                                    new clsAbsenOnlineRepo(context).createOrUpdate(data);
                                                                    new clsLastCheckingDataRepo(context).createOrUpdate(dataCheckin);
                                                                    Intent myIntent = new Intent(getContext(), MainMenu.class);
                                                                    myIntent.putExtra(new clsHardCode().INTENT,true);
                                                                    myIntent.putExtra(new clsHardCode().GUI,obj.getString("txtGUI_ID"));
                                                                    Bundle bundleAbsen = new Bundle();
                                                                    bundleAbsen.putString(new clsHardCode().INTENT,"1");
                                                                    bundleAbsen.putString(new clsHardCode().GUI,obj.getString("txtGUI_ID"));
                                                                    Fragment fragment = new FragmentInformation();
                                                                    fragment.setArguments(bundleAbsen);
//                                                                    Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
//                                                                    // Vibrate for 500 milliseconds
//                                                                    v.vibrate(500);
                                                                    startActivity(myIntent);
                                                                    getActivity().finish();
                                                                    new clsMainActivity().showCustomToast(getActivity().getApplicationContext(),"Checkin success !", true);
                                                                } catch (SQLException e) {
                                                                    e.printStackTrace();
                                                                }

                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }




                                                    }else{
                                                        new clsMainActivity().showCustomToast(context,"Checkin failed, Please try again !",false);
                                                    }
                                                }
                                            });
//                                        }
//                                        Boolean pRes = true;
//                                        if (_clsAbsenData == null) {
//
//                                            pRes = false;
//
//                                        } else {
//                                            if ((_clsAbsenData.getTxtImg1() == null)
//                                                    && (_clsAbsenData.getTxtImg2() == null)) {
//
//                                                pRes = false;
//                                            }
//                                        }
//                                        if (pRes) {
//                                            double latitude = Double.parseDouble(String.valueOf(lblLang.getText()));
//                                            double longitude = Double.parseDouble(String.valueOf(lblLong.getText()));
//                                            Location locationA = new Location("point A");
//
//                                            locationA.setLatitude(latitude);
//                                            locationA.setLongitude(longitude);
//
//
//                                            clsUserLogin checkLocation = new clsUserLoginRepo(context).getDataLogin(context);
//                                            if (_clsAbsenData == null) {
//                                                _clsAbsenData = new clsAbsenData();
//                                            }
//                                            clsAbsenData datatAbsenUserData = _clsAbsenData;
//                                            clsUserLogin dataUserActive = new clsUserLoginRepo(context).getDataLogin(context);
//                                            String idUserActive = String.valueOf(dataUserActive.getTxtUserID());
//                                            clsDeviceInfo dataDeviceInfoUser = new clsDeviceInfoRepo(context).getDataDevice(context);
//                                            clsAbsenData absenUserDatas = new clsAbsenData();
//                                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                            Calendar cal = Calendar.getInstance();
//                                            datatAbsenUserData.setDtCheckin(dateFormat.format(cal.getTime()));
////                                            datatAbsenUserData.setIntId(txtHDId.getText().toString());
//                                            datatAbsenUserData.setGuiId(GuiID);
//                                            datatAbsenUserData.setIntSubmit("1");
//                                            datatAbsenUserData.setSync("0");
//                                            datatAbsenUserData.setTxtAbsen("0");
//                                            datatAbsenUserData.setTxtLatitude(lblLang.getText().toString());
//                                            datatAbsenUserData.setTxtLongitude(lblLong.getText().toString());
//                                            datatAbsenUserData.setTxtDeviceId(dataDeviceInfoUser.getTxtDevice());
//                                            datatAbsenUserData.setTxtUserId(idUserActive);
//                                            datatAbsenUserData.setTxtGuiIdLogin(dataUserActive.getTxtGUI());
//                                            datatAbsenUserData.setDtCheckout(null);
//                                            try {
//                                                new clsAbsenDataRepo(context).createOrUpdate(datatAbsenUserData);
//                                            } catch (SQLException e) {
//                                                e.printStackTrace();
//                                            }
//                                            imgPrevNoImg1.setClickable(false);
//                                            imgPrevNoImg2.setClickable(false);
//                                            btnRefreshMaps.setClickable(false);
//                                            btnRefreshMaps.setVisibility(View.GONE);
//
//
//                                            _clsMainActivity.showCustomToast(getContext(), "Saved", true);
//                                            try {
//                                                clazz = Class.forName(myClass);
//                                                Intent myIntent = new Intent(getContext(), MainMenu.class);
////                                                myIntent.putExtra(clsParameterPutExtra.MenuID, MenuID);
////                                                myIntent.putExtra(clsParameterPutExtra.BranchCode, branchCode);
////                                                myIntent.putExtra(clsParameterPutExtra.OutletCode, outletCode);
//                                                getActivity().finish();
//                                                startActivity(myIntent);
//                                            } catch (ClassNotFoundException e) {
//                                                // TODO Auto-generated catch block
//                                                e.printStackTrace();
//                                            }
//
//                                        } else {
//                                            _clsMainActivity.showCustomToast(getContext(), "Please Photo at least 1 photo..", false);
//                                        }
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });
                    final AlertDialog alertD = alertDialogBuilder.create();
                    alertD.show();
                }

            }
//					else{
//						clazz = Class.forName(myClass);
//						Intent myIntent = new Intent(getApplicationContext(), clazz);
//						myIntent.putExtra(clsParameterPutExtra.MenuID, MenuID);
//						myIntent.putExtra(clsParameterPutExtra.BranchCode, branchCode);
//						myIntent.putExtra(clsParameterPutExtra.OutletCode, outletCode);
//						finish();
//						startActivity(myIntent);
//					}

        });

//        displayLocation();

        return v;
    }
    public static boolean isMockSettingsON(Context context) {
        // returns true if mock location enabled, false if not enabled.
        if (Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
            return false;
        else
            return true;
    }
    private void getOutlet() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        clsmConfig configData = null;
        String linkPushData = "";
        try {
            configData = (clsmConfig) new clsmConfigRepo(getActivity().getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String strLinkAPI = configData.getTxtValue() + new clsHardCode().linkGetOutlet;
//        String nameRole = selectedRole;
        final JSONObject resJson = new JSONObject();
        clsGetOutletDataHelper dataPush = new clsGetOutletDataHelper();
        clsUserLogin dataLogin = new clsUserLogin();
        clsDeviceInfo deviceInfo = new clsDeviceInfo();
        deviceInfo = new clsDeviceInfoRepo(getActivity().getApplicationContext()).getDataDevice(getActivity().getApplicationContext());
        dataLogin = new clsUserLoginRepo(getActivity().getApplicationContext()).getDataLogin(getActivity().getApplicationContext());

        /*try {
            resJson.put("TxtNIK", dataLogin.getEmployeeId());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        List<clsBranchAccess> ltBranch = new ArrayList<>();
        try {
            ltBranch = new clsBranchAccessRepo(context).findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JSONArray branchArray = new JSONArray(ltBranch);
        Gson jsn = new Gson();
        String branchAreaJson = jsn.toJson(ltBranch);
        JSONObject json = new JSONObject();
        try {
            json.put(dataPush.Property_txtIdUserLogin,String.valueOf(dataLogin.getEmployeeId()));
            json.put(dataPush.Property_txtDeviceId,String.valueOf(deviceInfo.getIdDevice()));
            json.put(dataPush.Property_txtBranchCode,String.valueOf(dataLogin.getTxtKodeCabang()));
            json.put(dataPush.Property_txtLatitude,String.valueOf(lblLang.getText()));
            json.put(dataPush.Property_txtLongitude,String.valueOf(lblLong.getText()));
            json.put(dataPush.Property_AreaAccess,branchAreaJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String mRequestBody = json.toString();
        SharedPreferences prefs = getActivity().getSharedPreferences(new clsHardCode().MY_PREFS_NAME, MODE_PRIVATE);
        String token= prefs.getString("token", "No token defined");
        new VolleyUtils().makeJsonObjectRequestOutlet(getActivity(), strLinkAPI, token, mRequestBody, "Getting Outlet Data", new VolleyResponseListener() {
            @Override
            public void onError(String response) {
//                new clsMainActivity().showCustomToast(getActivity().getApplicationContext(), "Connection Lost, get latest data failed", false);
                final SweetAlertDialog dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...Connection lost")
                        .setContentText("Please check your internet connection,and try again");
                dialog.show();
                dialog.setCancelable(false);
                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dialog.dismiss();
                        getOutlet();
                    }
                });

            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                String a = response;
                a = a.trim();
                a = a.substring(1, a.length()-1);
                a = a.replace("\\", "");
                if(!a.equals("")){
                    try {
                        JSONObject object = new JSONObject(a);
                        JSONArray result = object.getJSONArray("listOutlet");
                        arrData = new ArrayList<>();
                        if (result.length() == 0){
                            new clsMainActivity().showCustomToast(context,"Outlet not found",false);
                        }
                        for(int i = 0; i<result.length();i++ ){
                            JSONObject obj = result.getJSONObject(i);
                            String intOutletId = obj.getString("mOutletId");
                            String txtBranchCode = obj.getString("txtBranchCode");
                            String txtOutletName = obj.getString("txtOutletName").toUpperCase()+" - "+txtBranchCode;
                            String txtLongitude = obj.getString("txtLongitude");
                            String txtLatitude = obj.getString("txtLatitude");
                            String mmapingHeaderId = obj.getString("intHeaderMappingOutlet");
                            String mmappingDetailId = obj.getString("intDetailIdMappingOutlet");

                            arrData.add(txtOutletName);
                            HMoutletId.put(txtOutletName, intOutletId);
                            HMoutletLong.put(txtOutletName, txtLongitude);
                            HMoutletLat.put(txtOutletName, txtLatitude);
                            HMoutletMappingHeader.put(txtOutletName,mmapingHeaderId);
                            HMoutletMappingDetail.put(txtOutletName,mmappingDetailId);

                        }
                        JSONObject mconfig = object.getJSONObject("radius");
                        clsmConfig config = new clsmConfig();
                        config.setIntId(9);
                        config.setTxtName(mconfig.getString("txtKeyID"));
                        config.setTxtValue(mconfig.getString("txtValue"));
                        try {
                            new clsmConfigRepo(context).createOrUpdate(config);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        ArrayAdapter<String> dataAdapterOutlet = new MyAdapter(getContext(), R.layout.custom_spinner, arrData);
                        spnOutlet.setAdapter(dataAdapterOutlet);
                        if (mockStatus){
                            imgScaner.setClickable(false);
                        }else{
                            imgScaner.setClickable(true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                if (listDataArea.size() > 0) {
//                    for (mEmployeeAreaData dt : listDataArea) {
//                        arrData.add(dt.get_txtOutletName());
//                        HMoutletId.put(dt.get_txtOutletName(), dt.get_txtOutletCode());
//                        HMoutletLang.put(dt.get_txtOutletName(), dt.get_txtLongitude() == null ? "" : dt.get_txtLongitude());
//                        HMoutletLat.put(dt.get_txtOutletName(), dt.get_txtLatitude() == null ? "" : dt.get_txtLatitude());
//                    }
                    ArrayAdapter<String> dataAdapterOutlet = new MyAdapter(getContext(), R.layout.custom_spinner, arrData);
                    spnOutlet.setAdapter(dataAdapterOutlet);
//                if(!status){
//                    new clsMainActivity().showCustomToast(getApplicationContext(), strErrorMsg, false);
//                }
                }else{
                    new clsMainActivity().showCustomToast(getActivity().getApplicationContext(), "Outlet Not Found", false);
                }

            }
        });
    }
    private void gettingLocation() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            onLocationChanged(location);
        }

        locationManager.requestLocationUpdates(provider, 1000, 0, this);
    }

    LocationManager mLocationManager2;


    public Location getLocation() {

        try {
            LocationManager locationManager = (LocationManager) getActivity()
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            boolean canGetLocation = false;


            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                new clsMainActivity().showCustomToast(getContext(), "no network provider is enabled", false);
            } else {
                canGetLocation = true;
                Location location = null;
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            1000,
                            0, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            if (mLastLocation != null){
                                mLastLocation.reset();
                            }
                            mLastLocation = location;
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    Location location2 = null;
                    if (mLastLocation == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                1000,
                                0, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location2 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                if (mLastLocation != null){
                                    mLastLocation.reset();
                                }
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                            }else{
                                if(mLastLocation!=null){
                                    int intOs = Integer.valueOf(android.os.Build.VERSION.SDK);
                                    if(intOs >=18){
                                        mockStatus = mLastLocation.isFromMockProvider();
                                    }
                                }else{
                                    new clsMainActivity().showCustomToast(context,"Your GPS off", false);
                                }
                            }
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(mLastLocation!=null){
            int intOs = Integer.valueOf(android.os.Build.VERSION.SDK);
            if(intOs >=18){
                mockStatus = mLastLocation.isFromMockProvider();
            }
        }



        return mLastLocation;
    }


    @SuppressWarnings("deprecation")
    private void displayLocation(Location mLastLocation) {
        if(mLastLocation!=null){
            int intOs = Integer.valueOf(android.os.Build.VERSION.SDK);
            if(intOs >=18){
                mockStatus = mLastLocation.isFromMockProvider();
            }
        }
        if (mockStatus){
            btnCheckIn.setClickable(false);
            new clsMainActivity().showCustomToast(getActivity().getApplicationContext(), "Fake GPS detected, !", false);
            new clsMainActivity().showCustomToast(getActivity().getApplicationContext(), "Please Turn Off Fake Location, And Restart Your Phone", false);
//            Toast.makeText(context,"Please Turn Off Fake Location, And Restart Your Phone",Toast.LENGTH_LONG);
            Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(5000);
        }
        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            double accurate = mLastLocation.getAccuracy();
            lblLong.setText(longitude + "");
            lblLang.setText(latitude + "");
            lblAcc.setText(accurate+"");

            Long = String.valueOf(longitude);
            Lat = String.valueOf(latitude);
            Acc = String.valueOf(accurate);
            try {
                float distance = countDistance(latitude, longitude);
                lblDistance.setText(String.valueOf((int) Math.ceil(distance)) + " meters");
            } catch (Exception ex) {

            }
        }

    }
    private float countDistance(double latitude, double longitude) {
        float distance = 0;


        double latitudeOutlet = Double.parseDouble(HMoutletLat.get(spnOutlet.getSelectedItem().toString()));
        double longitudeOutlet = Double.parseDouble(HMoutletLong.get(spnOutlet.getSelectedItem().toString()));

        Location locationA = new Location("point A");

        locationA.setLatitude(latitude);
        locationA.setLongitude(longitude);

        Location locationB = new Location("point B");

        locationB.setLatitude(latitudeOutlet);
        locationB.setLongitude(longitudeOutlet);

        distance = locationA.distanceTo(locationB);

        lblDistance.setText(String.valueOf((int) Math.ceil(distance)) + " meters");

        return distance;
    }
    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

    }

    @SuppressWarnings("deprecation")
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
            }
            return false;
        }
        return true;
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    private void initilizeMap() {
        if (mMap == null) {

            // check if map is created successfully or not
            if (mMap == null) {

            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Absen Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://absenmobilehr.app.kalbenutritionals.absenmobilehr/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onResume() {
        super.onResume();
        initilizeMap();
        checkPlayServices();
    }

    private Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    private File getOutputMediaFile() {
        // External sdcard location

        File mediaStorageDir = new File(new clsHardCode().txtFolderAbsen + File.separator);
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
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "tmp_absen" + ".jpg");
        return mediaFile;
    }

    protected void captureImage1() {
        uriImage = getOutputMediaFileUri();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intentCamera1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera1.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);
        getActivity().startActivityForResult(intentCamera1, CAMERA_CAPTURE_IMAGE1_REQUEST_CODE);
    }

    protected void captureImage2() {
        uriImage = getOutputMediaFileUri();
        Intent intentCamera2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera2.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);
        getActivity().startActivityForResult(intentCamera2, CAMERA_CAPTURE_IMAGE2_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        // if the result is capturing Image
        if (requestCode == 99){
            final String guiId = new clsMainBL().GenerateGuid();
            IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (scanResult.getContents() == null && scanResult.getFormatName() == null) {
                return;
            }
            final String result = scanResult.getContents();
            Log.d("Data info", "Scanning Success result = "+result);
            clsmConfig configData = null;
            String linkCheckinData= "";
            try {
                configData = (clsmConfig) new clsmConfigRepo(getActivity().getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
                linkCheckinData = configData.getTxtValue()+new clsHardCode().linkAbsen;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            double latitude = Double.parseDouble(String.valueOf(lblLang.getText()));
            double longitude = Double.parseDouble(String.valueOf(lblLong.getText()));
            final String txtOutlet = spnOutlet.getSelectedItem().toString();
            String txtOutletId = HMoutletId.get(txtOutlet);
            double latitudeOutlet = Double.parseDouble(HMoutletLat.get(txtOutlet));
            double longitudeOutlet = Double.parseDouble(HMoutletLong.get(txtOutlet));
            int mappingHeaderId = Integer.parseInt(HMoutletMappingHeader.get(txtOutlet));
            int mappingDetailId = Integer.parseInt(HMoutletMappingDetail.get(txtOutlet));

            Location locationA = new Location("point A");
            Location locationB = new Location(("point B"));
            locationA.setLatitude(latitude);
            locationA.setLongitude(longitude);
            locationB.setLongitude(longitudeOutlet);
            locationB.setLatitude(latitudeOutlet);
            clsmConfig mradius = new clsmConfig();
            try {
                mradius = new clsmConfigRepo(context).findById(9);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            float distance = locationA.distanceTo(locationB);
            if (distance>Integer.parseInt(mradius.getTxtValue())){
               new clsMainActivity().showCustomToast(getActivity().getApplicationContext(),"You're too far from outlet",false);
            }else{
                final clsUserLogin dataUserActive = new clsUserLoginRepo(context).getDataLogin(context);
                clsDeviceInfo dataDeviceInfoUser = new clsDeviceInfoRepo(context).getDataDevice(context);
                String strLinkAPI = linkCheckinData;
                JSONObject resJson = new JSONObject();
                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar cal = Calendar.getInstance();
                    String now = dateFormat.format(cal.getTime());

                    resJson.put("longitude",longitude);
                    resJson.put("latitude",latitude);
                    resJson.put("outletId",txtOutletId);
                    resJson.put("outletName",txtOutlet);
                    resJson.put("qrCode",result);
                    resJson.put("txtTime", now);
                    resJson.put("mappingHeaderId",mappingHeaderId);
                    resJson.put("mappingDetailId",mappingDetailId);
                    resJson.put("txtTimeDetail",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
                    resJson.put("guiId",guiId);
                    resJson.put("guiIdLogin",dataUserActive.getTxtGUI());
                    resJson.put(dataUserActive.Property_employeeId,dataUserActive.getEmployeeId());
                    resJson.put(dataUserActive.Property_txtUserName,dataUserActive.getTxtUserName());
                    resJson.put(dataUserActive.Property_txtName,dataUserActive.getTxtName());
                    resJson.put("deviceInfo",dataDeviceInfoUser.getTxtModel());
                    resJson.put("deviceId",dataDeviceInfoUser.getIdDevice());
                    resJson.put("TxtVersion", pInfo.versionName);
                    resJson.put("TxtNameApp", "Android - Absen HR");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String mRequestBody =  resJson.toString();
                new VolleyUtils().makeJsonObjectRequest(getActivity(), strLinkAPI, mRequestBody, "Checking in please wait !", new VolleyResponseListener() {
                    @Override
                    public void onError(String response) {
                        new clsMainActivity().showCustomToast(getActivity().getApplicationContext(),"Connection Failed, please check your network !", false);
//                Toast.makeText(getApplicationContext(), "Connection Failed, please check your network !", Toast.LENGTH_SHORT).show();
//                Log.d("connection failed", response);
                    }

                    @Override
                    public void onResponse(String response, Boolean status, String strErrorMsg) {
                        if (response != null) {
                            clsAbsenOnline data = new clsAbsenOnline();
                            clsLastCheckingData dataCheckin = new clsLastCheckingData();
                            if (response.equals("0")){
                                new clsMainActivity().showCustomToast(getActivity().getApplicationContext(),"Outlet doesn't match !", false);
                            }else if(response.equals("1")){
                                new clsMainActivity().showCustomToast(getActivity().getApplicationContext(),"Barcode Error, Check your outlet and try again !", false);
                            }else{
                                try {
                                    JSONArray array = new JSONArray(response);

                                    JSONObject obj = array.getJSONObject(0);
                                    String txtGUI_ID = obj.getString("txtGUI_ID");
                                    data.setTxtGuiId(obj.getString("txtGUI_ID"));
                                    data.setTxtGuiIdLogin(obj.getString("txtGuiIdLogin"));
                                    data.setTxtLatitude(obj.getString("txtLatitude"));
                                    data.setTxtLongitude(obj.getString("txtLongitude"));
                                    data.setTxtDeviceId(obj.getString("txtDeviceId"));
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
                                    String dateCheckin = obj.getString("dtCheckin");

                                    Calendar cal = Calendar.getInstance();
                                    data.setDtCheckin(dateFormat.format(cal.getTime()));
                                    data.setQrCode(obj.getString("qrCode"));
                                    data.setQrId(obj.getString("qrCode"));
                                    data.setSync("0");
                                    data.setIntSubmit("1");

                                    dataCheckin.setTxtOutletName(txtOutlet);
                                    dataCheckin.setTxtGuiID(obj.getString("txtGUI_ID"));
                                    dataCheckin.setDtCheckin(cal.getTime());
                                    dataCheckin.setDtCheckout(null);
                                    dataCheckin.setBoolMoodCheckout("0");
                                    try {
                                        new clsAbsenOnlineRepo(context).createOrUpdate(data);
                                        new clsLastCheckingDataRepo(context).createOrUpdate(dataCheckin);
                                        Intent myIntent = new Intent(getContext(), MainMenu.class);
                                        myIntent.putExtra(new clsHardCode().INTENT,true);
                                        myIntent.putExtra(new clsHardCode().GUI,obj.getString("txtGUI_ID"));
                                        Bundle bundleAbsen = new Bundle();
                                        bundleAbsen.putString(new clsHardCode().INTENT,"1");
                                        bundleAbsen.putString(new clsHardCode().GUI,obj.getString("txtGUI_ID"));
                                        Fragment fragment = new FragmentInformation();
                                        fragment.setArguments(bundleAbsen);
//                                        Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
//                                        // Vibrate for 500 milliseconds
//                                        v.vibrate(500);
                                        startActivity(myIntent);
                                        getActivity().finish();
                                        new clsMainActivity().showCustomToast(getActivity().getApplicationContext(),"Checkin success !", true);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }




                        }
                    }
                });
            }


//            if (result != null) {
//                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
//            }
//            clsQRCodeData qrCodeData = new clsQRCodeData();
//            qrCodeData.setIntQRCodeID(result);
//            qrCodeData.setTxtKontakID(dataMember.get(0).txtKontakId.toString());
//
//            qrCodeRepo = new clsQRCodeRepo(getApplicationContext());
//
//            int h = 0;
//            h = qrCodeRepo.createOrUpdate(qrCodeData);
//            if(h > -1) {
//                Log.d("Data info", "Scanning Success");
////                                    status = true;
//            }
//
//            sendData();
//            statusQRCode.setText("Scan Success");
        }
        if (requestCode == CAMERA_CAPTURE_IMAGE1_REQUEST_CODE) {
            if (resultCode == -1) {
                try {

                    Bitmap bitmap;
                    Options bitmapOptions = new Options();
                    String uri = uriImage.getPath().toString();

                    bitmap = BitmapFactory.decodeFile(uri, bitmapOptions);

                    previewCapturedImage1(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (resultCode == 0) {
                _clsMainActivity.showCustomToast(getContext(), "User canceled photo", false);
            } else {
                _clsMainActivity.showCustomToast(getContext(), "Something error", false);
            }
        } else if (requestCode == CAMERA_CAPTURE_IMAGE2_REQUEST_CODE) {
            if (resultCode == -1) {
                try {

                    Bitmap bitmap;
                    Options bitmapOptions = new Options();
                    String uri = uriImage.getPath().toString();

                    bitmap = BitmapFactory.decodeFile(uri, bitmapOptions);

                    previewCapturedImage2(bitmap);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == 0) {
                _clsMainActivity.showCustomToast(getContext(), "User canceled photo", false);
            } else {
                _clsMainActivity.showCustomToast(getContext(), "Something error", false);
            }
        }

    }

    private void previewCapturedImage1(Bitmap photo) {
        try {
//            dttAbsenUserData = _tAbsenUserBL.getDataCheckInActive();
            Bitmap bitmap = new clsMainActivity().resizeImageForBlob(photo);
            imgPrevNoImg1.setVisibility(View.VISIBLE);
            ByteArrayOutputStream out = null;
            try {
                out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, out); // bmp is your Bitmap instance
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Bitmap photo_view = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
//            photo.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, blob);
//            Bitmap bitmap1 = Bitmap.createScaledBitmap(photo, 150, 150, false);
//            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, blob);
            byte[] pht = out.toByteArray();
            imgPrevNoImg1.setImageBitmap(photo_view);
            if (_clsAbsenData != null) {
                _clsAbsenData.setTxtImg1(pht);
            } else {
                _clsAbsenData.setTxtImg1(pht);
//                dttAbsenUserData.set_txtImg2(null);
                _clsAbsenData.setGuiId(txtHDId.getText().toString());
            }
            _clsAbsenData.setIntSubmit("0");
            _clsAbsenData.setSync("0");
            _clsAbsenData.setTxtAbsen("0");
            /*try {
                new clsAbsenDataRepo(context).createOrUpdate(_clsAbsenData);
            } catch (SQLException e) {
                e.printStackTrace();
            }*/

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void previewCapturedImage2(Bitmap photo) {
        try {
//            dttAbsenUserData = _tAbsenUserBL.getDataCheckInActive();
            Bitmap bitmap = new clsMainActivity().resizeImageForBlob(photo);
            imgPrevNoImg2.setVisibility(View.VISIBLE);
            ByteArrayOutputStream out = null;
            try {
                out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, out); // bmp is your Bitmap instance
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Bitmap photo_view = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
//            photo.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, blob);
//            Bitmap bitmap1 = Bitmap.createScaledBitmap(photo, 150, 150, false);
//            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, blob);
            byte[] pht = out.toByteArray();
            imgPrevNoImg2.setImageBitmap(photo_view);
            if (_clsAbsenData != null) {
                _clsAbsenData.setTxtImg2(pht);
            } else {
                _clsAbsenData.setTxtImg1(null);
                _clsAbsenData.setTxtImg2(pht);
                _clsAbsenData.setGuiId(txtHDId.getText().toString());
            }
            _clsAbsenData.setIntSubmit("1");
            _clsAbsenData.setSync("0");
            _clsAbsenData.setTxtAbsen("0");//
            /*try {
                new clsAbsenDataRepo(context).createOrUpdate(_clsAbsenData);
            } catch (SQLException e) {
                e.printStackTrace();
            }*/
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());

    }

    @Override
    public void onConnected(@Nullable Bundle arg0) {

    }

    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
        mGoogleApiClient.connect();

    }

    private boolean isDeviceSupportCamera() {
        if (getContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        mLastLocation = location;
        displayLocation(mLastLocation);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}







