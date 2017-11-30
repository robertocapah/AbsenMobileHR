package absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyResponseListener;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyUtils;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.clsHardCode;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsBranchAccess;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsDeviceInfo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsGetOutletDataHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLastCheckingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmConfig;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsBranchAccessRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsDeviceInfoRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsLastCheckingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsUserLoginRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsmConfigRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.enumConfigData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.R;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.clsMainActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Robert on 29/09/2017.
 */

public class FragmentReport extends Fragment {
    View v;
    TableLayout tlReport;
    Button btnSearch, btnMaps;
    Spinner spnOutlet;
    EditText edDtStart, edDtEnd;
    Context context;
    Button btnReport;
    private Calendar calendar;
    private DatePicker datePicker;
    private int year, month, day;
    int intCompareDate = 0;
    private List<String> arrData;

    private HashMap<String, String> HMoutletId = new HashMap<String, String>();
    private GoogleMap mMap;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frament_report, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        context = getActivity().getApplicationContext();
        spnOutlet =(Spinner) v.findViewById(R.id.spnOutletReport);
        btnSearch = (Button) v.findViewById(R.id.btnsearch);
        btnMaps = (Button) v.findViewById(R.id.btnMap);
        tlReport = (TableLayout) v.findViewById(R.id.tlResume);
        edDtStart = (EditText) v.findViewById(R.id.dtStart);
        edDtEnd = (EditText) v.findViewById(R.id.dtEnd);
        btnReport = (Button) v.findViewById(R.id.buttonReport);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        MapsInitializer.initialize(getActivity().getApplicationContext());

        try {
            initilizeMap();
            // Changing map type
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        } catch (Exception e) {
            e.printStackTrace();
        }
        List<clsLastCheckingData> datasChecking = null;
        try {
            datasChecking = new clsLastCheckingDataRepo(context).findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//            List<clsReportData> datas = new clsReportDataRepo(context).findAll();
        for (clsLastCheckingData data :
                datasChecking) {
//            TableRow tr = new TableRow(getActivity());
//            TextView TxtLocation = new TextView(getActivity());
//            TextView txtDateCheckin = new TextView(getActivity());
//            TextView txtDateCheckout = new TextView(getActivity());
////                TextView txtLongitude = new TextView(getActivity());
////                TextView txtLatitude = new TextView(getActivity());
//
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
//            TxtLocation.setPadding(5, 5, 5, 5);
//            TxtLocation.setText(data.getTxtOutletName());
//            TxtLocation.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
//            txtDateCheckin.setText(data.getDtCheckin());
//            txtDateCheckin.setPadding(5, 5, 5, 5);
//            txtDateCheckin.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
//            txtDateCheckout.setText(data.getDtCheckout());
//            txtDateCheckout.setPadding(5, 5, 5, 5);
//            txtDateCheckout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
////                txtLongitude.setPadding(5,5,5,5);
////                txtLongitude.setText(data.());
////                txtLongitude.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
////                txtLatitude.setText(data.getTxtLatitude());
////                txtLatitude.setPadding(5,5,5,5);
////                txtLatitude.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
//
//            tr.addView(TxtLocation);
//            tr.addView(txtDateCheckin);
//            tr.addView(txtDateCheckout);
////                tr.addView(txtLongitude);
////                tr.addView(txtLatitude);
//            tlReport.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            Date dtCheckin = data.getDtCheckin();
            Date dtChekout = data.getDtCheckout();

            Calendar aCalendar = Calendar.getInstance();
            // add -1 month to current month
            aCalendar.add(Calendar.MONTH, -1);
            // set DATE to 1, so first date of previous month
            aCalendar.set(Calendar.DATE, 1);
            Date firstDateOfPreviousMonth = aCalendar.getTime();

            Date cal = Calendar.getInstance().getTime();
//                String c = dateFormat.format(dtCheckin);
//                try {
//                    Date d = dateFormat.parse(c);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
        }
        edDtStart.setShowSoftInputOnFocus(false);
        edDtEnd.setShowSoftInputOnFocus(false);
        edDtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(edDtStart);
            }
        });
        edDtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(edDtEnd);
            }
        });
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tlReport.removeAllViews();
                clsUserLogin dataLogin = new clsUserLogin();
                JSONObject json = new JSONObject();
                dataLogin = new clsUserLoginRepo(getActivity().getApplicationContext()).getDataLogin(getActivity().getApplicationContext());
                String startDate = edDtStart.getText().toString();
                String endDate = edDtEnd.getText().toString();
                String spnResult = spnOutlet.getSelectedItem().toString();
                String aa = HMoutletId.get(spnResult);
                int outletId=0;
                if (aa != null){
                    outletId = Integer.parseInt(aa);
                }
                int dateStatus = compareDates(startDate, endDate);

                clsmConfig configData = null;
                String linkGetReport = "";
                try {
                    configData = (clsmConfig) new clsmConfigRepo(getActivity().getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
                    linkGetReport = configData.getTxtValue() + new clsHardCode().linkGetReport;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
//                clsmConfig configData = null;
//                String linkPushData = "";
//                try {
//                    configData = (clsmConfig) new clsmConfigRepo(getActivity().getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                String strLinkAPI = configData.getTxtValue() + new clsHardCode().linkGetReport;

                try {
                    json.put("NIK", String.valueOf(dataLogin.getEmployeeId()));
                    json.put("StartDate", startDate);
                    json.put("EndDate", endDate);
                    json.put("outletId", outletId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String mRequestBody = json.toString();

                if (dateStatus == 1) {
                    new clsMainActivity().showCustomToast(context, "Date period empty !", false);
                } else if (startDate.equals("") ) {
                    new clsMainActivity().showCustomToast(context, "Please,complete start date!", false);
                }else if(endDate.equals("")){
                    new clsMainActivity().showCustomToast(context, "Please,complete end date!", false);
                }else {
                    new VolleyUtils().makeJsonObjectRequest(getActivity(), linkGetReport, mRequestBody, "Getting Report Data", new VolleyResponseListener() {
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
                                    btnReport.performClick();
//
                                }
                            });

                        }

                        @Override
                        public void onResponse(String response, Boolean status, String strErrorMsg) {
                            String a = "";
                            if (response != null) {
                                try {
                                    JSONArray array = new JSONArray(response);
                                    if (array.length()==0){
                                        btnMaps.setVisibility(View.GONE);
                                    }else{
                                        btnMaps.setVisibility(View.VISIBLE);
                                    }
                                    for (int j = 0; j < array.length(); j++) {
                                        JSONObject json_data = array.getJSONObject(j);
                                        String outletName = json_data.getString("outletName").toUpperCase();
                                        String timeCheckin = json_data.getString("strTimeCheckin");
                                        String timecheckout = json_data.getString("strTimeCheckout");
                                        String strLong = json_data.getString("longitude");
                                        String strLat = json_data.getString("latitude");
                                        Double longitude = null;
                                        Double latitude = null;


                                        TableRow tr = new TableRow(getActivity());
                                        TextView TxtLocation = new TextView(getActivity());
                                        TextView txtDateCheckin = new TextView(getActivity());
                                        TextView txtDateCheckout = new TextView(getActivity());

                                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                                        TxtLocation.setPadding(5, 5, 5, 5);
                                        TxtLocation.setText(outletName);
                                        TxtLocation.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                                        txtDateCheckin.setText(timeCheckin);
                                        txtDateCheckin.setPadding(5, 5, 5, 5);
                                        txtDateCheckin.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                                        txtDateCheckout.setText(timecheckout);
                                        txtDateCheckout.setPadding(5, 5, 5, 5);
                                        txtDateCheckout.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));

                                        tr.addView(TxtLocation);
                                        tr.addView(txtDateCheckin);
                                        tr.addView(txtDateCheckout);
                                        tlReport.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                                        Boolean valid = true;
                                        try {
                                            if (strLong != null || !strLong.equals("null")) ;
                                            {
                                                longitude = Double.parseDouble(strLong);
                                            }
                                            if (strLat != null || !strLat.equals("null")) {
                                                latitude = Double.parseDouble(strLat);
                                            }
                                        } catch (Exception ex) {
                                            valid = false;
                                            new clsMainActivity().showCustomToast(getContext(), "Your location not found", false);
                                        }
                                        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(outletName);
                                        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

//                                        mMap.addMarker(marker);
//                                        PolylineOptions rectOptions = new PolylineOptions().add(new LatLng(latitude, longitude))
//                                                .add(new LatLng(latitude, longitude));
//                                        Polyline polyline = mMap.addPolyline(rectOptions);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                        }
                    });
                }
                btnMaps.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        clsUserLogin dataLogin = new clsUserLogin();
                        final JSONObject json = new JSONObject();
                        dataLogin = new clsUserLoginRepo(getActivity().getApplicationContext()).getDataLogin(getActivity().getApplicationContext());
                        String startDate = edDtStart.getText().toString();
                        String endDate = edDtEnd.getText().toString();
                        String spnResult = spnOutlet.getSelectedItem().toString();
                        String aa = HMoutletId.get(spnResult);
                        int outletId=0;
                        if (aa != null){
                            outletId = Integer.parseInt(aa);
                        }
                        int dateStatus = compareDates(startDate, endDate);

                        clsmConfig configData = null;
                        String linkGetReport = "";
                        try {
                            configData = (clsmConfig) new clsmConfigRepo(getActivity().getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
                            linkGetReport = configData.getTxtValue() + new clsHardCode().linkGetReport;
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
//                clsmConfig configData = null;
//                String linkPushData = "";
//                try {
//                    configData = (clsmConfig) new clsmConfigRepo(getActivity().getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                String strLinkAPI = configData.getTxtValue() + new clsHardCode().linkGetReport;

                        try {
                            json.put("NIK", String.valueOf(dataLogin.getEmployeeId()));
                            json.put("StartDate", startDate);
                            json.put("EndDate", endDate);
                            json.put("outletId",outletId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        final String mRequestBody = json.toString();

                        if (dateStatus == 1) {
                            new clsMainActivity().showCustomToast(context, "Date Periode Error", false);
                        } else if (startDate.equals("") || endDate.equals("")) {
                            new clsMainActivity().showCustomToast(context, "Date Empty !", false);
                        } else {
                            new VolleyUtils().makeJsonObjectRequest(getActivity(), linkGetReport, mRequestBody, "Getting Report Data", new VolleyResponseListener() {
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
                                            btnReport.performClick();
//
                                        }
                                    });

                                }

                                @Override
                                public void onResponse(String response, Boolean status, String strErrorMsg) {
                                    String a = "";
                                    if (response != null) {
                                        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                                        View promptView = layoutInflater.inflate(R.layout.popup_map_absen, null);
                                        GoogleMap mMap;
                                        mMap = ((MapFragment) (getActivity()).getFragmentManager().findFragmentById(R.id.map)).getMap();
//                                        mMap = ((MapFragment) (getActivity()).getFragmentManager().findFragmentById(R.id.map)).getMap();

                                        if (mMap == null) {
                                            mMap = ((MapFragment) (getActivity()).getFragmentManager().findFragmentById(R.id.map)).getMap();
                                        }
                                        final LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                        PolylineOptions rectOptions = new PolylineOptions();
                                        PolylineOptions rectOptions2 = new PolylineOptions();
                                        try {
                                            JSONArray array = new JSONArray(response);
                                            for (int j = 0; j < array.length(); j++) {
                                                JSONObject json_data = array.getJSONObject(j);
                                                String outletName = json_data.getString("outletName");
                                                String timeCheckin = json_data.getString("strTimeCheckin");
                                                String timecheckout = json_data.getString("strTimeCheckout");
                                                String strLong = json_data.getString("longitude");
                                                String strLat = json_data.getString("latitude");
                                                String strLongHuman = json_data.getString("longitudeHuman");
                                                String strLatHuman = json_data.getString("latitudeHuman");
                                                Double longHuman = null;
                                                Double latHuman = null;
                                                Double longitude = null;
                                                Double latitude = null;

                                                Boolean valid = true;
                                                try {
                                                    if (strLong != null || !strLong.equals("null"))
                                                    {
                                                        longitude = Double.parseDouble(strLong);
                                                    }
                                                    if (strLat != null || !strLat.equals("null")) {
                                                        latitude = Double.parseDouble(strLat);
                                                    }
                                                    if (strLongHuman != null || !strLongHuman.equals("null")) {
                                                        longHuman = Double.parseDouble(strLongHuman);
                                                    }
                                                    if (strLatHuman != null || !strLatHuman.equals("null")) {
                                                        latHuman = Double.parseDouble(strLatHuman);
                                                    }


                                                } catch (Exception ex) {
                                                    valid = false;
                                                    new clsMainActivity().showCustomToast(getContext(), "Your location not found", false);
                                                }
                                                MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(outletName);
                                                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                                builder.include(marker.getPosition());
                                                mMap.addMarker(marker);

                                                MarkerOptions marker2 = new MarkerOptions().position(new LatLng(latHuman, longHuman)).title("Posisi Anda ketika absen di :"+outletName).snippet("Time :"+timeCheckin);
                                                marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                                                builder.include(marker2.getPosition());
                                                mMap.addMarker(marker2);

                                                rectOptions.add(new LatLng(latitude, longitude));
                                                Polyline polyline = mMap.addPolyline(rectOptions);
                                                Polyline polyline2 = mMap.addPolyline(rectOptions2);
                                            }

                                            LatLngBounds bounds = builder.build();
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

                                                                    dialog.dismiss();
                                                                }
                                                            });
                                            final android.support.v7.app.AlertDialog alertD = alertDialogBuilder.create();
                                            alertD.setTitle("History Location Visited");
                                            alertD.show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }


                                }
                            });
                        }
                    }
                });

            }
        });
        getOutlet();
        return v;
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    private void initilizeMap() {
        // TODO Auto-generated method stub
        if (mMap == null) {

            // check if map is created successfully or not
            if (mMap == null) {

            }
        }

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

        String strLinkAPI = configData.getTxtValue() + new clsHardCode().linkGetOutletReporting;
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
        String branches = "";
        int hehe = 1;
        try {
            List<clsBranchAccess> branchAccess = new clsBranchAccessRepo(context).findAll();
            if (branchAccess.size()>0){
                for (clsBranchAccess branch :
                        branchAccess) {
                    if (hehe == 1) {
                        branches = branch.getTxtBranchCode();
                    }else{
                        branches = branches + "," + branch.getTxtBranchCode();
                    }
                    hehe++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            json.put(dataPush.Property_txtIdUserLogin,String.valueOf(dataLogin.getEmployeeId()));
            json.put(dataPush.Property_txtDeviceId,String.valueOf(deviceInfo.getIdDevice()));
            json.put(dataPush.Property_txtBranchCode,branches);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String mRequestBody = json.toString();
        new VolleyUtils().makeJsonObjectRequestOutlet(getActivity(), strLinkAPI, mRequestBody, "Getting Outlet Data", new VolleyResponseListener() {
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
                        arrData.add(0, "ALL");
                        for(int i = 0; i<result.length();i++ ){
                            JSONObject obj = result.getJSONObject(i);
                            String intOutletId = obj.getString("mOutletId");
                            String txtOutletName = obj.getString("txtOutletName").toUpperCase();
                            HMoutletId.put(txtOutletName, intOutletId);
                            arrData.add(txtOutletName);


                        }
//                        ArrayAdapter<String> dataAdapterOutlet = new FragmentReport.MyAdapter(getContext(), R.layout.custom_spinner, arrData);
//                        spnOutlet.setAdapter(dataAdapterOutlet);
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
                    ArrayAdapter<String> dataAdapterOutlet = new FragmentReport.MyAdapter(getContext(), R.layout.custom_spinner, arrData);
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


    public int compareDates(String d1, String d2) {
        try {
            // If you already have date objects then skip 1

            //1
            // Create 2 dates starts
            SimpleDateFormat sdf = new SimpleDateFormat("dd - MM - yyyy");
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);

            System.out.println("Date1" + sdf.format(date1));
            System.out.println("Date2" + sdf.format(date2));
            System.out.println();

            // Create 2 dates ends
            //1

            // Date object is having 3 methods namely after,before and equals for comparing
            // after() will return true if and only if date1 is after date 2
            if (date1.after(date2)) {
                System.out.println("Date1 is after Date2");
                intCompareDate = 1;
            }
            // before() will return true if and only if date1 is before date2
            if (date1.before(date2)) {
                System.out.println("Date1 is before Date2");
                intCompareDate = 2;
            }

            //equals() returns true if both the dates are equal
            if (date1.equals(date2)) {
                System.out.println("Date1 is equal Date2");
                intCompareDate = 3;
            }

            System.out.println();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return intCompareDate;
    }

    public static void compareDates(Date date1, Date date2) {
        // if you already have date objects then skip 1
        //1

        //1

        //date object is having 3 methods namely after,before and equals for comparing
        //after() will return true if and only if date1 is after date 2
        if (date1.after(date2)) {
            System.out.println("Date1 is after Date2");
        }

        //before() will return true if and only if date1 is before date2
        if (date1.before(date2)) {
            System.out.println("Date1 is before Date2");
        }

        //equals() returns true if both the dates are equal
        if (date1.equals(date2)) {
            System.out.println("Date1 is equal Date2");
        }

        System.out.println();
    }

    private void showDatePicker(EditText editText) {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        if (editText == edDtStart) {
            calender.add(Calendar.DATE, -1);
        }
        if (editText == edDtEnd) {
            calender.add(Calendar.DATE, +1);
        }

        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);

        /**
         * Set Call back to capture selected date
         */
        if (editText == edDtStart) {
            date.setCallBack(ondate);
        }
        if (editText == edDtEnd) {
            date.setCallBack(ondateEnd);
        }
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            clsMainActivity clsMainMonth = new clsMainActivity();
//            String month2 = clsMainMonth.months[monthOfYear+1];

            String he = String.format("%02d", dayOfMonth);
            String ha = String.format("%02d", monthOfYear + 1);
            edDtStart.setText(he + " - " + ha + " - " + String.valueOf(year));
            edDtStart.setHint("");
        }
    };
    DatePickerDialog.OnDateSetListener ondateEnd = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            clsMainActivity clsMainMonth = new clsMainActivity();
//            String month2 = clsMainMonth.months[monthOfYear+1];
            String he = String.format("%02d", dayOfMonth);
            String ha = String.format("%02d", monthOfYear + 1);
            edDtEnd.setText(he + " - " + ha + " - " + String.valueOf(year));
            edDtEnd.setHint("");
        }
    };

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

        public MyAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            setCtx(context);
            setArrayDataAdapyter(objects);
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
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View row = inflater.inflate(R.layout.custom_spinner, parent, false);
            if (getArrayDataAdapyter().size() > 0) {
                TextView label = (TextView) row.findViewById(R.id.tvTitle);
                //label.setText(arrData.get(position));
                label.setText(getArrayDataAdapyter().get(position));
                label.setTextColor(new Color().parseColor("#000000"));
                TextView sub = (TextView) row.findViewById(R.id.tvDesc);
                sub.setVisibility(View.INVISIBLE);
                sub.setVisibility(View.GONE);
                row.setBackgroundColor(new Color().parseColor("#FFFFFF"));
            }
            //sub.setText(mydata2[position]);
            return row;
        }

    }
}
