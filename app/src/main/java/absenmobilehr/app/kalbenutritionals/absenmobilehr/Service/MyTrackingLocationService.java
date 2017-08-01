package absenmobilehr.app.kalbenutritionals.absenmobilehr.Service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.clsHardCode;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsTrackingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmCounterData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.enumCounterData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsTrackingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsUserLoginRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsmCounterDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.clsMainActivity;

/**
 * Created by Robert on 07/06/2018.
 */

public class MyTrackingLocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    List<clsUserLogin> dtLogin;

    public MyTrackingLocationService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        buildGoogleApiClient();
    }

    private GoogleApiClient mGoogleApiClient;

    private void buildGoogleApiClient() {
        // TODO Auto-generated method stub
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

    }

    private Location mLastLocation;
    Handler mHandler = new Handler();
    private final static int INTERVAL = 1000 * 60; //2 minutes
    private static long UPDATE_INTERVAL = 1 * 360 * 1000;
    ;  //default
    private static long UPDATE_INTERVAL_TESTING = /*1000 * 60 * 2*/3000; //2 minutes
    private static Timer timer = new Timer();

    private void startService() throws JSONException {
        try {
            doService();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void doService() throws JSONException {
        SQLiteDatabase db;
        String versionName = "";
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        clsHardCode clsdthc = new clsHardCode();
//        db = SQLiteDatabase.openOrCreateDatabase(clsdthc.dbName, null); // create file database
        clsUserLogin loginData = new clsUserLoginRepo(getApplicationContext()).getDataLogin(getApplicationContext());

        if (loginData != null) {

            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                clsmCounterData counterData = new clsmCounterData();
                counterData.setIntId(enumCounterData.MonitoringLocation.getidCounterData());
                counterData.setTxtDeskripsi("value menunjukan waktu terakhir menjalankan services");
                counterData.setTxtName("Monitor Service Location");
                counterData.setTxtValue(dateFormat.format(cal.getTime()));
                new clsmCounterDataRepo(getApplicationContext()).createOrUpdate(counterData);

                startRepeatingTask();
                //new clsInit().PushData(db,versionName);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        } else {
            shutdownService();
        }
//        db.close();
    }

    public Location trackingLocation() {
        try {
            LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            boolean canGetLocation = false;
            Location location = null;

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                new clsMainActivity().showCustomToast(getApplicationContext(), "no network provider is enabled", false);
            } else {
                canGetLocation = true;
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            1000,
                            0, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        mLastLocation = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (mLastLocation == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                1000,
                                0, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        clsTrackingData dataLocation = new clsTrackingData();
        clsUserLogin dataUserActive = new clsUserLoginRepo(getApplicationContext()).getDataLogin(getApplicationContext());
        final int index;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        try {
//            String intSquence = new Intent().getStringExtra("intSequence");
//            if (intSquence != null &&!intSquence.equals("null")){
//                int intSq = Integer.parseInt(intSquence);
//                index = intSq + 1;
//            }else{
                List<clsTrackingData> _datas = (List<clsTrackingData>) new clsTrackingDataRepo(getApplicationContext()).findAll();
                if (_datas != null && _datas.size()>0){
                    index = new clsTrackingDataRepo(getApplicationContext()).getMaxSequence() + 1;
                }else{
                    index = 1;
                }

//            }


            if (new clsTrackingDataRepo(getApplicationContext()).getContactCount() == 0) {
                if (dataUserActive != null) {
                    dataLocation.setGuiId(new clsMainActivity().GenerateGuid());
                    dataLocation.setTxtLongitude(String.valueOf(mLastLocation.getLongitude()));
                    dataLocation.setTxtLatitude(String.valueOf(mLastLocation.getLatitude()));
                    dataLocation.setTxtUserId(dataUserActive.getTxtUserID());
                    dataLocation.setTxtUsername(dataUserActive.getTxtUserName());
                    dataLocation.setTxtDeviceId(dataUserActive.getTxtDeviceId());
                    dataLocation.setTxtBranchCode(dataUserActive.getTxtKodeCabang());
                    dataLocation.setTxtNIK(dataUserActive.getEmployeeId());
                    dataLocation.setGuiIdLogin(dataUserActive.getTxtGUI());
                    dataLocation.setIntSequence(String.valueOf(index));
                    dataLocation.setTxtTime(dateFormat.format(cal.getTime()));
                    dataLocation.setIntSubmit("1");
                    dataLocation.setIntSync("0");
                    if (dataUserActive.getTxtGUI() != null){
                        try{
                            new clsTrackingDataRepo(getApplicationContext()).create(dataLocation);
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }else{
                        shutdownService();
                    }
                } else {
                    shutdownService();
                }
            } else {
                int data = 0;
                List<clsTrackingData> datas = new clsTrackingDataRepo(getApplicationContext()).getLastDataByTime();
                data = Integer.valueOf(datas.get(0).getIntSequence());

                if (dataUserActive != null) {
                    dataLocation.setGuiId(new clsMainActivity().GenerateGuid());
                    dataLocation.setTxtLongitude(String.valueOf(mLastLocation.getLongitude()));
                    dataLocation.setTxtLatitude(String.valueOf(mLastLocation.getLatitude()));
                    dataLocation.setTxtUserId(dataUserActive.getTxtUserID());
                    dataLocation.setTxtUsername(dataUserActive.getTxtUserName());
                    dataLocation.setTxtDeviceId(dataUserActive.getTxtDeviceId());
                    dataLocation.setTxtBranchCode(dataUserActive.getTxtKodeCabang());
                    dataLocation.setTxtNIK(dataUserActive.getEmployeeId());
                    dataLocation.setGuiIdLogin(dataUserActive.getTxtGUI());
                    dataLocation.setIntSequence(String.valueOf(index));
                    dataLocation.setTxtTime(dateFormat.format(cal.getTime()));
                    dataLocation.setIntSubmit("1");
                    dataLocation.setIntSync("0");
                    if (dataUserActive.getTxtGUI() != null){
                        try{
                            new clsTrackingDataRepo(getApplicationContext()).create(dataLocation);
                        }catch (SQLException e){
                            e.printStackTrace();
                        }
                    }
                } else {
                    shutdownService();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return mLastLocation;
    }

    Runnable mHandlerTask = new Runnable() {
        @Override
        public void run() {
            trackingLocation();
            mHandler.postDelayed(mHandlerTask, INTERVAL);
        }
    };

    void startRepeatingTask() {
        mHandlerTask.run();
    }

    public void shutdownService() {
        if (timer != null) timer.cancel();
        Log.i(getClass().getSimpleName(), "Timer stopped...");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        // For time consuming an long tasks you can launch a new thread here...
        //Toast.makeText(this, "Welcome Kalbe SPG Mobile", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    startService();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, 35000);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        //Toast.makeText(this, " onStartCommand", Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    startService();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, 35000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        shutdownService();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
