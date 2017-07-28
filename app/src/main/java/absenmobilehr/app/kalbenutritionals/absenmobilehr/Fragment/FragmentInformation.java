package absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.owater.library.CircleTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyResponseListener;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyUtils;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsTrackingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsAbsenDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsTrackingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsUserLoginRepo;
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
    TextView tvTotalReso, tvTotalActivity, tvTotalCustomerBase, tvUsername, tvBranchOutlet, tvEmail, tv_reso1, tv_reso2, tv_act1, tv_act2, tv_cb1, tv_cb2;
    Context context;
    CircleTextView ctvStatus;

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
        clsUserLogin data = new clsUserLoginRepo(context).getDataLogin(context);
        clsAbsenData dataAbsen = new clsAbsenDataRepo(context).getDataCheckinActive(context);

        if (data != null) {
            tvUsername.setText(data.getTxtName().toString());
            tvEmail.setText(data.getTxtEmail().toString());
        }
        if (dataAbsen != null) {
            ctvStatus.setText("Checkin");
        } else {
            ctvStatus.setText("Checkout");
        }
        List<clsTrackingData> trackingDatas = new ArrayList<>();
        try {
            trackingDatas = (List<clsTrackingData>) new clsTrackingDataRepo(getContext()).findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (trackingDatas != null && trackingDatas.size() == 0) {
            getLastTrackingData();
        }
        return v;
    }

    private void getLastTrackingData() {
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String strLinkAPI = "http://10.171.11.87/APIEF2/api/TrackingDataAPI/getDataLastLocation/{id}";
//        String nameRole = selectedRole;
        final JSONObject resJson = new JSONObject();
        clsUserLogin dataLogin = new clsUserLogin();
        dataLogin = new clsUserLoginRepo(getActivity().getApplicationContext()).getDataLogin(getActivity().getApplicationContext());

        try {
            resJson.put("TxtGUId", dataLogin.getTxtGUI());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String mRequestBody = dataLogin.getTxtGUI();
        new VolleyUtils().makeJsonObjectRequest(getActivity(), strLinkAPI, mRequestBody, "Getting in latest data", new VolleyResponseListener() {
            @Override
            public void onError(String response) {
//                new clsMainActivity().showCustomToast(getActivity().getApplicationContext(), "Connection Lost, get latest data failed", false);
                final SweetAlertDialog dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...Connection lost")
                        .setContentText("Please check your internet connection,and try again");
                dialog.show();
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
                if (response != null) {
                    try {
                        if (!response.equals("null") && response != null) {
                            clsTrackingData trackingData = new clsTrackingData();
                            JSONObject jsonObject1 = new JSONObject(response);
                            if (!isMyServiceRunning(MyServiceNative.class)) {
                                Intent i = new Intent(getActivity(), MyTrackingLocationService.class);
                                i.putExtra("intSequence", trackingData.getIntSequence());
                                getActivity().startService(i);
                            }
                            if (!isMyServiceRunning(MyTrackingLocationService.class)) {
                                getActivity().startService(new Intent(getActivity(), MyServiceNative.class));
                            }
                            boolean tes, tes2;
                            tes = isMyServiceRunning(MyServiceNative.class);
                            tes2 = isMyServiceRunning(MyTrackingLocationService.class);
                            new clsMainActivity().showCustomToast(getContext(), "Success", true);
                        } else {

                        }
                    } catch (Exception ex) {

                    }
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

