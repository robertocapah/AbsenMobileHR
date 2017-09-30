package absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.owater.library.CircleTextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyResponseListener;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyUtils;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.clsHardCode;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenOnline;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLastCheckingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsReportData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsTrackingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmConfig;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsAbsenDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsAbsenOnlineRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsLastCheckingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsReportDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsTrackingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsUserLoginRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsmConfigRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.enumConfigData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.R;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Service.MyServiceNative;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Service.MyTrackingLocationService;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.clsMainActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Robert on 22/06/2017.
 */

public class FragmentInformation extends Fragment {
    View v;
    TextView tvTotalReso, tvTotalActivity, tvTotalCustomerBase, tvNIK, tvUsername, tvBranchOutlet, tvEmail, tv_reso1, tv_reso2, tv_act1, tv_act2, tv_cb1, tv_cb2;
    Context context;
    CircleTextView ctvStatus;
    TableLayout tlResume;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home_new, container, false);
        context = getActivity().getApplicationContext();
        CircleImageView ivProfile = (CircleImageView) v.findViewById(R.id.profile_image);
        tvUsername = (TextView) v.findViewById(R.id.tvUsername);
        tvBranchOutlet = (TextView) v.findViewById(R.id.tvBranchOutlet);
        tvEmail = (TextView) v.findViewById(R.id.tvEmail);
        ctvStatus = (CircleTextView) v.findViewById(R.id.status);
        tvNIK = (TextView) v.findViewById(R.id.tvNIK);
        tlResume = (TableLayout) v.findViewById(R.id.tlResume);
        clsUserLogin data = new clsUserLoginRepo(context).getDataLogin(context);
        clsAbsenData dataAbsen = new clsAbsenDataRepo(context).getDataCheckinActive(context);
        clsAbsenOnline dataAbsenOnline = new clsAbsenOnlineRepo(context).getDataCheckinActive(context);
        if (data != null && data.getTxtGUI() != null) {
            tvUsername.setText(data.getTxtName().toString());
            tvEmail.setText(data.getTxtEmail().toString());
            tvBranchOutlet.setText(data.getTxtNamaCabang().toString());
            tvNIK.setText(data.getEmployeeId());
        }
        if (dataAbsenOnline != null) {
            ctvStatus.setText("Checkin");
        } else {
            ctvStatus.setText("Checkout");
        }

        List<clsTrackingData> trackingDatas = new ArrayList<>();
        List<clsTrackingData> trackingDatas2 = new ArrayList<>();
        try {
            trackingDatas = (List<clsTrackingData>) new clsTrackingDataRepo(getContext()).findAll();
//            trackingDatas2 = (List<clsTrackingData>) new clsTrackingDataRepo(getContext()).getAllDataToPushData(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (trackingDatas.size() == 0) {
            getLastTrackingData();
        } else {
            if (!isMyServiceRunning(MyTrackingLocationService.class)) {
                Intent i = new Intent(getActivity(), MyTrackingLocationService.class);
                getActivity().startService(i);
            }
        }
        List<clsLastCheckingData> datasChecking = null;
        try {
            datasChecking = new clsLastCheckingDataRepo(context).findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (datasChecking.size()>0){
            for (clsLastCheckingData dataChecking : datasChecking) {
                             /* Create a new row to be added. */
                TableRow tr = new TableRow(getActivity());
                tr.setBackgroundColor(Color.parseColor("#ffffff"));
                tr.setPadding(5,5,5,5);
                tr.setWeightSum(3);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                            /* Create a Button to be the row-content. */
                TextView view1 = new TextView(getActivity());
                TextView view2 = new TextView(getActivity());
                TextView view3 = new TextView(getActivity());
                view1.setText(dataChecking.getTxtOutletName());
                view1.setPadding(5,5,5,5);
                view1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
                view2.setText(dataChecking.getDtCheckin());
                view2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
                view3.setText(dataChecking.getDtCheckout());
                view3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT,1f));
                //        b.setText("Dynamic Button");

                //        b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                            /* Add Button to row. */
                tr.addView(view1);
                tr.addView(view2);
                tr.addView(view3);
/* Add row to TableLayout. */
//tr.setBackgroundResource(R.drawable.sf_gradient_03);
                tlResume.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            }
        }

        return v;
    }

    private void getLastTrackingData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        clsmConfig configData = null;
        String linkPushData = "";
        try {
            configData = (clsmConfig) new clsmConfigRepo(getActivity().getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
            linkPushData = configData.getTxtValue() + new clsHardCode().linkGetLastLocation;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String strLinkAPI = linkPushData;
//        String nameRole = selectedRole;
        final JSONObject resJson = new JSONObject();
        clsUserLogin dataLogin = new clsUserLogin();
        dataLogin = new clsUserLoginRepo(getActivity().getApplicationContext()).getDataLogin(getActivity().getApplicationContext());

        /*try {
            resJson.put("TxtNIK", dataLogin.getEmployeeId());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        final String mRequestBody = dataLogin.getEmployeeId();
        new VolleyUtils().makeJsonObjectRequest(getActivity(), strLinkAPI, mRequestBody, "Getting in latest data", new VolleyResponseListener() {
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
                        getLastTrackingData();
                    }
                });

            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                try {
                    if (!response.equals("null") && response != null) {
                        clsTrackingData trackingData = new clsTrackingData();
                        JSONObject jsonObject1 = new JSONObject(response);
                        trackingData.setGuiId(new clsMainActivity().GenerateGuid());
                        trackingData.setIntSubmit("1");
                        trackingData.setIntSync("0");
                        trackingData.setGuiIdLogin(jsonObject1.getString("txtGuiIdLogin"));
                        trackingData.setTxtBranchCode(jsonObject1.getString("txtBranchCode"));
                        int intSeq = Integer.parseInt(jsonObject1.getString("intSequence")) + 1;
                        trackingData.setIntSequence(intSeq);
                        trackingData.setTxtDeviceId(jsonObject1.getString("txtDeviceId"));
                        trackingData.setTxtTime(jsonObject1.getString("dtDate"));
                        trackingData.setTxtLatitude(jsonObject1.getString("txtLatitude"));
                        trackingData.setTxtLongitude(jsonObject1.getString("txtLongitude"));
                        trackingData.setTxtNIK(jsonObject1.getString("txtNIK"));
                        trackingData.setTxtUserId(jsonObject1.getString("txtUserId"));
                        trackingData.setTxtUsername(jsonObject1.getString("txtUsername"));

                        if (!jsonObject1.getString("ltAbsen").equals("null")){
                            JSONArray jArray = jsonObject1.getJSONArray("ltAbsen");
                            int jACount = jArray.length();
                            if(jACount>0){
                                for(int i=0; i<jArray.length(); i++){
                                    JSONObject json_data = jArray.getJSONObject(i);
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
                                    new clsLastCheckingDataRepo(context).create(dataCheckin);
                                }
                            }

                        }
                        if (!jsonObject1.getString("ltAbsenAll").equals("null")){
                            JSONArray jArrayAll = jsonObject1.getJSONArray("ltAbsenAll");
                            if (jArrayAll.length()>0){
                                for(int i=0; i<jArrayAll.length(); i++){
                                    JSONObject json_data = jArrayAll.getJSONObject(i);
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

                                    clsReportData dataCheckin = new clsReportData();
                                    dataCheckin.setTxtGuiID(guiID);
                                    dataCheckin.setTxtOutletId(outletId);
                                    dataCheckin.setTxtOutletName(outlet);
                                    dataCheckin.setDtCheckin(dtCheckin);
                                    dataCheckin.setDtCheckout(dtCheckout);
                                    dataCheckin.setTxtLongitude(txtLongitude);
                                    dataCheckin.setTxtLatitude(txtLatitude);
                                    dataCheckin.setTxtDeviceId(txtDeviceId);
                                    dataCheckin.setTxtDeviceName(txtDeviceName);
                                    dataCheckin.setTxtUserId(txtUserId);
                                    new clsReportDataRepo(context).createOrUpdate(dataCheckin);
                                }
                            }
                        }

                        int done = new clsTrackingDataRepo(getActivity().getApplicationContext()).create(trackingData);
                        if (done > -1) {
                            new clsMainActivity().showCustomToast(getActivity().getApplicationContext(), "Done", true);
                            if (!isMyServiceRunning(MyTrackingLocationService.class)) {
                                Intent i = new Intent(getActivity(), MyTrackingLocationService.class);
                                getActivity().startService(i);
                            }
                            if (!isMyServiceRunning(MyServiceNative.class)) {
                                Intent i = new Intent(getActivity(), MyServiceNative.class);

                                getActivity().startService(i);
                            }
                            boolean tes, tes2;
                            tes2 = isMyServiceRunning(MyTrackingLocationService.class);
                            // Reload current fragment
                            Fragment frg = null;
                            frg = getFragmentManager().findFragmentByTag("FragmentInformation");
                            final FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.detach(frg);
                            ft.attach(frg);
                            ft.commit();
                        }

                    } else {
                        if (!isMyServiceRunning(MyTrackingLocationService.class)) {
                            Intent i = new Intent(getActivity(), MyTrackingLocationService.class);
                            getActivity().startService(i);
                        }
                        if (!isMyServiceRunning(MyServiceNative.class)) {
                            Intent i = new Intent(getActivity(), MyServiceNative.class);

                            getActivity().startService(i);
                        }
                    }
                } catch (Exception ex) {
                    String a = "hihi";
                }
//                if(!status){
//                    new clsMainActivity().showCustomToast(getApplicationContext(), strErrorMsg, false);
//                }
            }
        });
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

