package absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyResponseListener;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyUtils;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsTrackingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsUserLoginRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.R;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.clsMainActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;


public class FragmentHome extends Fragment {

    View v;
    private Toolbar toolbar;
//    private SortableReportTableView ReportTableView;
    TextView username, branch, outlet, statusAbsen, totalBrand, totalProduct, totalReso, totalActivity, totalCustomerBase, tv_reso1, tv_reso2, tv_act1, tv_act2, tv_cb1, tv_cb2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_home,container,false);

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) v.findViewById(R.id.coordinatorLayout);

        username = (TextView) v.findViewById(R.id.username);
        branch = (TextView) v.findViewById(R.id.branch);
        outlet = (TextView) v.findViewById(R.id.outlet);
        statusAbsen = (TextView) v.findViewById(R.id.statusAbsen);
        totalBrand = (TextView) v.findViewById(R.id.totalBrand);
        totalProduct = (TextView) v.findViewById(R.id.totalProduct);
        totalReso = (TextView) v.findViewById(R.id.totalReso);
        totalActivity = (TextView) v.findViewById(R.id.totalActivity);
        totalCustomerBase = (TextView) v.findViewById(R.id.totalCustomerBase);
        tv_reso1 = (TextView) v.findViewById(R.id.tv_reso1);
        tv_reso2 = (TextView) v.findViewById(R.id.tv_reso2);
        tv_act1 = (TextView) v.findViewById(R.id.tv_act1);
        tv_act2 = (TextView) v.findViewById(R.id.tv_act2);
        tv_cb1 = (TextView) v.findViewById(R.id.tv_cb1);
        tv_cb2 = (TextView) v.findViewById(R.id.tv_cb2);
        getLastTrackingData();

        return v;
    }
    private void getLastTrackingData(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String strLinkAPI = "http://10.171.11.87/APIEF2/api/TrackingDataAPI/getDataLastLocation/{id}";
//        String nameRole = selectedRole;
        JSONObject resJson = new JSONObject();
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
                new clsMainActivity().showCustomToast(getActivity().getApplicationContext(), "Connection Lost, get latest data failed", false);
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...Connection lost")
                        .setContentText("Please check your internet connection, try again")
                        .setOnKeyListener(new DialogInterface.OnKeyListener() {
                            @Override
                            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                getLastTrackingData();
                                return false;
                            }
                        });
            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                if (response != null) {
                    try{
                        clsTrackingData trackingData = new clsTrackingData();
                        JSONObject jsonObject1 = new JSONObject(response);

                    }catch (Exception ex){

                    }
                }
//                if(!status){
//                    new clsMainActivity().showCustomToast(getApplicationContext(), strErrorMsg, false);
//                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void onHiddenFirstShowcase() {
        Toast.makeText(getContext(), "Jump", Toast.LENGTH_LONG).show();
    }

}
