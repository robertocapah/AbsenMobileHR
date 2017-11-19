package absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyResponseListener;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyUtils;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.bl.clsMainBL;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.clsHardCode;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsDeviceInfo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLeaveData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsTypeLeave;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmConfig;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsDeviceInfoRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsLeaveDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsTypeLeaveRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsUserLoginRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsmConfigRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.enumConfigData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.MainMenu;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.R;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.clsMainActivity;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Robert on 13/10/2017.
 */

public class FragmentLeave extends Fragment implements View.OnClickListener,LocationListener {

    private Spinner spnLeave;
    EditText edReason;
    private HashMap<String, Integer> HMLeaveID = new HashMap<>();
    private HashMap<String, String> HMLeave2 = new HashMap<>();
    Context context = null;
    private Location mLastLocation;
    private PackageInfo pInfo = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leave_add, container, false);
        context = getActivity().getApplicationContext();
        spnLeave = (Spinner) view.findViewById(R.id.spnTypeLeave);
        Button btnSaveleave = (Button) view.findViewById(R.id.btnSaveLeave);
        edReason = (EditText) view.findViewById(R.id.editTextReason);
        TextView date = (TextView) view.findViewById(R.id.textViewDateTime);
        try {
            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        List<clsTypeLeave> ltTypeLeave = new ArrayList<>();
        getLocation();
        if (mLastLocation != null) {
            displayLocation(mLastLocation);
        }
        try {
            ltTypeLeave = new clsTypeLeaveRepo(context).findAll();
            String a = "";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<String> arrData1 = new ArrayList<>();
        if (ltTypeLeave != null) {
            for (clsTypeLeave dt : ltTypeLeave) {
                arrData1.add(dt.getTxtLeaveName());
                String idname = String.valueOf(dt.getTxtLeaveName());
                HMLeaveID.put(dt.getTxtLeaveName(), dt.getIntLeaveID());
                HMLeave2.put(dt.getTxtLeaveName(), idname);
            }
            spnLeave.setAdapter(new MyAdapter(getContext(), R.layout.custom_spinner, arrData1));
        }

        clsmConfig configData = null;


        try {
            configData = (clsmConfig) new clsmConfigRepo(getActivity().getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        final String strLinkAPI = configData.getTxtValue() + new clsHardCode().linkLeave;
        clsUserLogin dataLogin = new clsUserLogin();
        dataLogin = new clsUserLoginRepo(context).getDataLogin(context);
        final String username = dataLogin.getTxtUserName();
        final String nik = dataLogin.getEmployeeId();
        final String txtGUIId = dataLogin.getTxtGUI();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final String currentDateandTime = sdf.format(new Date());

        final clsUserLogin dataUserActive = new clsUserLoginRepo(context).getDataLogin(context);
        final clsDeviceInfo dataDeviceInfoUser = new clsDeviceInfoRepo(context).getDataDevice(context);

        btnSaveleave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String typeLeave = spnLeave.getSelectedItem().toString();
                final String txtAlasan = edReason.getText().toString();
                JSONObject resJson = new JSONObject();
                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar cal = Calendar.getInstance();
                    String now = dateFormat.format(cal.getTime());
                    String longitude = String.valueOf(mLastLocation.getLongitude());
                    String latitude = String.valueOf(mLastLocation.getLatitude());
                    final String GuiID = new clsMainBL().GenerateGuid();
                    resJson.put("longitude",longitude);
                    resJson.put("latitude",latitude);
                    resJson.put("txtTime", now);
                    resJson.put("guiId",GuiID);
                    resJson.put("guiIdLogin",dataUserActive.getTxtGUI());
                    resJson.put(dataUserActive.Property_employeeId,dataUserActive.getEmployeeId());
                    resJson.put(dataUserActive.Property_txtUserName,dataUserActive.getTxtUserName());
                    resJson.put(dataUserActive.Property_txtName, dataUserActive.getTxtName());
                    resJson.put("deviceInfo",dataDeviceInfoUser.getTxtModel());
                    resJson.put("deviceId",dataDeviceInfoUser.getIdDevice());
                    resJson.put("TxtVersion", pInfo.versionName);
                    resJson.put("TxtNameApp", "Android - Absen HR");
                    resJson.put("TxtNIK", nik);
                    resJson.put("TxtTime", currentDateandTime);
                    resJson.put("TxtUsername",username );
                    resJson.put("IntTypeLeaveId", HMLeaveID.get(typeLeave));
                    String textReason = edReason.getText().toString();
                    resJson.put("TxtAlasan", textReason);
                    resJson.put("TxtGUILogin", txtGUIId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String mRequestBody = resJson.toString();
                new VolleyUtils().makeJsonObjectRequest(getActivity(), strLinkAPI, mRequestBody, "Going to Leave !", new VolleyResponseListener() {
                    @Override
                    public void onError(String response) {
                        new clsMainActivity().showCustomToast(getActivity().getApplicationContext(), response, false);
                    }

                    @Override
                    public void onResponse(String response, Boolean status, String strErrorMsg) {
                        if (response != null) {
                            if (response.equals("1")){
                                new clsMainActivity().showCustomToast(getActivity().getApplicationContext(), "Your status is leaved now", true);
                                startActivity(new Intent(getActivity(), MainMenu.class));
                                clsLeaveData dataLeave = new clsLeaveData();
                                dataLeave.setBitActive(1);
                                dataLeave.setLeaveId(HMLeaveID.get(typeLeave));
                                dataLeave.setTxtGuiIDLogin(txtGUIId);
                                dataLeave.setTxtTime(currentDateandTime);
                                dataLeave.setTxtKeterangan(txtAlasan);
                                try {
                                    new clsLeaveDataRepo(context).createOrUpdate(dataLeave);
                                    startActivity(new Intent(getActivity(),MainMenu.class));
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
//                if(!status){
//                    new clsMainActivity().showCustomToast(getApplicationContext(), strErrorMsg, false);
//                }
                    }
                });
            }
        });

        return view;
    }
    private void displayLocation(Location mLastLocation) {

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            double accurate = mLastLocation.getAccuracy();

        }

    }
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
            Location location = null;

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                new clsMainActivity().showCustomToast(getContext(), "no network provider is enabled", false);
            } else {
                canGetLocation = true;
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                            mLastLocation = locationManager
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

        return mLastLocation;
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
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

    private class MyAdapter extends ArrayAdapter<String> {
        private List<String> arrayDataAdapyter;
        private Context Ctx;

        List<String> getArrayDataAdapyter() {
            return arrayDataAdapyter;
        }

        void setArrayDataAdapyter(List<String> arrayDataAdapyter) {
            this.arrayDataAdapyter = arrayDataAdapyter;
        }

        public Context getCtx() {
            return Ctx;
        }

        public void setCtx(Context ctx) {
            Ctx = ctx;
        }

        MyAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            setCtx(context);
            setArrayDataAdapyter(objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View row = inflater.inflate(R.layout.custom_spinner, parent, false);
            if (getArrayDataAdapyter().size() > 0) {
                TextView label = (TextView) row.findViewById(R.id.tvTitle);
                //label.setText(arrData.get(position));
                label.setText(getArrayDataAdapyter().get(position));
                label.setTextColor(Color.parseColor("#000000"));
                TextView sub = (TextView) row.findViewById(R.id.tvDesc);
                sub.setVisibility(View.INVISIBLE);
                sub.setVisibility(View.GONE);
                row.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
            //sub.setText(mydata2[position]);
            return row;
        }

    }
}
