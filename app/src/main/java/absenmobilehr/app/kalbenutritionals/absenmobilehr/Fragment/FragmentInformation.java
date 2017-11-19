package absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.owater.library.CircleTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyResponseListener;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyUtils;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.clsHardCode;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenOnline;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLastCheckingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLeaveData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsReportData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsTrackingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmConfig;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsAbsenDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsAbsenOnlineRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsLastCheckingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsLeaveDataRepo;
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
    TextView tvMoodId;
    int intMoodId = 0;
    String gui = "";
    ShapeDrawable shapedrawable;
    ShapeDrawable shapedrawable2;
    Button btnSad,btnSick,btnLazy,btnHappy,btnJoy;
    boolean validMood;
    AlertDialog alertD;
    GradientDrawable gd;
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
        final clsUserLogin data = new clsUserLoginRepo(context).getDataLogin(context);
        clsAbsenData dataAbsen = new clsAbsenDataRepo(context).getDataCheckinActive(context);
        clsAbsenOnline dataAbsenOnline = new clsAbsenOnlineRepo(context).getDataCheckinActive(context);
        if (data != null && data.getTxtGUI() != null) {
            tvUsername.setText(data.getTxtName().toString());
            tvEmail.setText(data.getTxtEmail().toString());
            tvBranchOutlet.setText(data.getTxtNamaCabang().toString());
            tvNIK.setText(data.getEmployeeId());
        }
        try {
            List<clsLeaveData> dataLeave = new clsLeaveDataRepo(context).findAll();
            if (dataLeave.size()>0){
                ctvStatus.setText("Leaved");
                new clsMainActivity().showCustomToast(context,"Your status is Leave", false);
            }else{
                if (dataAbsenOnline != null) {
                    ctvStatus.setText("Checkin");
                } else {
                    ctvStatus.setText("Checkout");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        gd = new GradientDrawable();
        gd.setColor(0xFF00FF00); // Changes this drawbale to use a single color instead of a gradient
        gd.setCornerRadius(5);
        gd.setStroke(1, 0xFF000000);
        shapedrawable = new ShapeDrawable();
        shapedrawable.setShape(new RectShape());
        shapedrawable.getPaint().setColor(Color.RED);
        shapedrawable.getPaint().setStrokeWidth(10f);
        shapedrawable.getPaint().setStyle(Paint.Style.STROKE);
        shapedrawable2 = new ShapeDrawable();
        shapedrawable2.setShape(new RectShape());
        shapedrawable2.getPaint().setColor(Color.WHITE);
        shapedrawable2.getPaint().setStrokeWidth(10f);
        shapedrawable2.getPaint().setStyle(Paint.Style.STROKE);
        String absen ="";
        String checkout = "";
        Bundle inteBundle = getActivity().getIntent().getExtras();
        Bundle bundle = this.getArguments();
        if(bundle != null ){
            String strVal = bundle.getString("checkout", "noob");
            checkout = inteBundle.getString("checkout");
//            boolean intentAbsen = inteBundle.getBoolean(new clsHardCode().INTENT);
            gui = data.getTxtGUI();
            int intSurveyMood = data.getBitMood();
            if ( intSurveyMood == 0){
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                final View promptView = layoutInflater.inflate(R.layout.mood_survey, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setView(promptView);
                tvMoodId = (TextView) promptView.findViewById(R.id.moodId);
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                alertD = alertDialogBuilder.create();
                alertD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertD.show();
                alertD.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean valid = moodSurveyDone(gui,intMoodId);
                    }
                });
                final CircleImageView imgMood = (CircleImageView) promptView.findViewById(R.id.pickMood);

                btnSad = (Button) promptView.findViewById(R.id.btnSad);
                btnSick = (Button) promptView.findViewById(R.id.btnSick);
                btnLazy = (Button) promptView.findViewById(R.id.btnLazy);
                btnHappy = (Button) promptView.findViewById(R.id.btnHappy);
                btnJoy = (Button) promptView.findViewById(R.id.btnJoy);
//                LinearLayout ll = (LinearLayout) promptView.findViewById(R.id.lnMoodSurvey);
//                ll.setAlpha(0.4);
                btnSad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgMood.setImageResource(R.drawable.sad);
                        intMoodId = 1;
                        resetButton();
                        btnSad.setBackground(gd);
                    }
                });
                btnSick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgMood.setImageResource(R.drawable.sick);
                        intMoodId = 3;
                        resetButton();
                        btnSick.setBackground(gd);
                    }
                });
                btnLazy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgMood.setImageResource(R.drawable.sleeping);
                        intMoodId = 2;
                        resetButton();
                        btnLazy.setBackground(gd);
                    }
                });
                btnHappy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgMood.setImageResource(R.drawable.happy);
                        intMoodId = 5;
                        resetButton();
                        btnHappy.setBackground(gd);
                    }
                });
                btnJoy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgMood.setImageResource(R.drawable.joy);
                        intMoodId = 4;
                        resetButton();
                        btnJoy.setBackground(gd);
                    }
                });

            }else if (intSurveyMood == 1 && checkout !=null && checkout.equals("true")){
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                final View promptView = layoutInflater.inflate(R.layout.mood_survey_checkout, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setView(promptView);
                tvMoodId = (TextView) promptView.findViewById(R.id.moodId);
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                alertD = alertDialogBuilder.create();
                alertD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertD.show();
                alertD.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (intMoodId == 0){
                            new clsMainActivity().showCustomToast(context,"Please pick a Mood Condition",false);
                        }else{
                            boolean valid = moodSurveyDoneCheckout(gui,intMoodId);
                        }

                    }
                });
                final CircleImageView imgMood = (CircleImageView) promptView.findViewById(R.id.pickMood);

                btnSad = (Button) promptView.findViewById(R.id.btnSad);
                btnSick = (Button) promptView.findViewById(R.id.btnSick);
                btnLazy = (Button) promptView.findViewById(R.id.btnLazy);
                btnHappy = (Button) promptView.findViewById(R.id.btnHappy);
                btnJoy = (Button) promptView.findViewById(R.id.btnJoy);
//                LinearLayout ll = (LinearLayout) promptView.findViewById(R.id.lnMoodSurvey);
//                ll.setAlpha(0.4);
                btnSad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgMood.setImageResource(R.drawable.sad);
                        intMoodId = 1;
                        resetButton();
                        btnSad.setBackground(gd);
                    }
                });
                btnSick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgMood.setImageResource(R.drawable.sick);
                        intMoodId = 3;
                        resetButton();
                        btnSick.setBackground(gd);
                    }
                });
                btnLazy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgMood.setImageResource(R.drawable.sleeping);
                        intMoodId = 2;
                        resetButton();
                        btnLazy.setBackground(gd);
                    }
                });
                btnHappy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgMood.setImageResource(R.drawable.happy);
                        intMoodId = 5;
                        resetButton();
                        btnHappy.setBackground(gd);
                    }
                });
                btnJoy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgMood.setImageResource(R.drawable.joy);
                        intMoodId = 4;
                        resetButton();
                        btnJoy.setBackground(gd);
                    }
                });
            }
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
//            getLastTrackingData();
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
    public void resetButton(){
        btnSad.setBackground(getResources().getDrawable(R.drawable.custom_button_round_sad));
        btnSick.setBackground(getResources().getDrawable(R.drawable.custom_button_round_sick));
        btnLazy.setBackground(getResources().getDrawable(R.drawable.custom_button_round_lazy));
        btnHappy.setBackground(getResources().getDrawable(R.drawable.custom_button_round_happy));
        btnJoy.setBackground(getResources().getDrawable(R.drawable.custom_button_round_joy));
    }

    private boolean moodSurveyDone(final String guiId, final int moodId){
        clsmConfig configData = null;
        String linkCheckinData= "";
        JSONObject resJson = new JSONObject();
        try {
            final clsUserLogin dataUserActive = new clsUserLoginRepo(context).getDataLogin(context);
            configData = (clsmConfig) new clsmConfigRepo(getActivity().getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
            linkCheckinData = configData.getTxtValue()+new clsHardCode().linkMoodSurveyCheckin;

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            String now = dateFormat.format(cal.getTime());

            try {
                resJson.put("txtTimeDetail",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
                resJson.put("guiId",guiId);
                resJson.put("moodId",moodId);
                resJson.put("txtNIK",dataUserActive.getEmployeeId());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody =  resJson.toString();
        new VolleyUtils().makeJsonObjectRequest(getActivity(), linkCheckinData, mRequestBody, "Submitting", new VolleyResponseListener() {
            @Override
            public void onError(String response) {
                validMood = false;
                //                new clsMainActivity().showCustomToast(getActivity().getApplicationContext(), "Connection Lost, get latest data failed", false);
                final SweetAlertDialog dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...Connection lost")
                        .setContentText("Please check your internet connection,and try again");
                dialog.show();
                dialog.setCancelable(false);
                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        moodSurveyDone(guiId,moodId);
                    }
                });

            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                validMood = true;
                alertD.dismiss();
                clsUserLogin dataLogin2 = new clsUserLoginRepo(context).getDataLogin(context);
                dataLogin2.setBitMood(1);
                new clsUserLoginRepo(context).update(dataLogin2);
            }
        });
        return validMood;
    }

    private boolean moodSurveyDoneCheckout(final String guiId, final int moodId){
        clsmConfig configData = null;
        String linkCheckoutData= "";
        JSONObject resJson = new JSONObject();
        try {
            final clsUserLogin dataUserActive = new clsUserLoginRepo(context).getDataLogin(context);
            configData = (clsmConfig) new clsmConfigRepo(getActivity().getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
            linkCheckoutData = configData.getTxtValue()+new clsHardCode().linkMoodSurveyCheckout;

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            String now = dateFormat.format(cal.getTime());

            try {
                resJson.put("txtTimeDetail",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
                resJson.put("guiId",guiId);
                resJson.put("moodId",moodId);
                resJson.put("txtNIK",dataUserActive.getEmployeeId());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody =  resJson.toString();
        new VolleyUtils().makeJsonObjectRequest(getActivity(), linkCheckoutData, mRequestBody, "Submitting", new VolleyResponseListener() {
            @Override
            public void onError(String response) {
                validMood = false;
                //                new clsMainActivity().showCustomToast(getActivity().getApplicationContext(), "Connection Lost, get latest data failed", false);
                final SweetAlertDialog dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...Connection lost")
                        .setContentText("Please check your internet connection,and try again");
                dialog.show();
                dialog.setCancelable(false);
                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        moodSurveyDoneCheckout(guiId,moodId);
                    }
                });

            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                validMood = true;
                alertD.dismiss();
                clsUserLogin dataLogin2 = new clsUserLoginRepo(context).getDataLogin(context);
                dataLogin2.setBitMood(1);
                new clsUserLoginRepo(context).update(dataLogin2);
            }
        });
        return validMood;
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

                                    String dateString = "22/05/2014 11:49:00 AM";
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
                                    Date convertedDate = new Date();
                                    try {
                                        convertedDate = dateFormat.parse(dateString);
                                    } catch (ParseException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    Date dtCheckinD = null;
                                    if (!dtCheckin.equals("null")){
                                        dtCheckinD = dateFormat.parse(dtCheckin);
                                    }
                                    Date dtCheckoutD = null;
                                    if (!dtCheckout.equals("null")){
                                        dtCheckoutD = dateFormat.parse(dtCheckout);
                                    }
                                    clsReportData dataCheckin = new clsReportData();
                                    dataCheckin.setTxtGuiID(guiID);
                                    dataCheckin.setTxtOutletId(outletId);
                                    dataCheckin.setTxtOutletName(outlet);
                                    dataCheckin.setDtCheckin(dtCheckinD);
                                    dataCheckin.setDtCheckout(dtCheckoutD);
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

