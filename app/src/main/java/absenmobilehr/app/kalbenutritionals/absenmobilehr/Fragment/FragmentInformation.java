package absenmobilehr.app.kalbenutritionals.absenmobilehr.Fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.owater.library.CircleTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyResponseListener;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.VolleyUtils;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.bl.clsActivity;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.clsHardCode;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenOnline;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsBranchAccess;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsDisplayPicture;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLastCheckingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLeaveData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsTrackingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmConfig;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsAbsenDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsAbsenOnlineRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsBranchAccessRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsDisplayPictureRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsLastCheckingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsLeaveDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsTrackingDataRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsUserLoginRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsmConfigRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.enumConfigData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.MainMenu;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.R;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Service.MyTrackingLocationService;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.clsMainActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import gun0912.tedbottompicker.TedBottomPicker;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Robert on 22/06/2017.
 */

public class FragmentInformation extends Fragment {
    View v;
    TextView tvTotalReso, tvLatestMood, tvTotalActivity, tvTotalCustomerBase, tvNIK, tvUsername, tvBranchOutlet, tvEmail, tv_reso1, tv_reso2, tv_act1, tv_act2, tv_cb1, tv_cb2;
    Context context;
    CircleTextView ctvStatus;
    TableLayout tlResume;
    TextView tvMoodId;
    int intMoodId = 0;
    CircleImageView CiMoodImage;
    String gui = "";
    private static byte[] phtProfile;
    List<clsDisplayPicture> dataImageProfile = null;
    ShapeDrawable shapedrawable;
    private Uri selectedImage;
    private static final int CAMERA_REQUEST_PROFILE = 120;
    boolean validate = false;
    final int PIC_CROP = 2;
    final int PIC_CROP2 = 3;
    Bitmap thePic;
    final int PIC_CROP_PROFILE = 5;
    final int SELECT_FILE_PROFILE = 6;
    CircleImageView ivProfile;
    private Uri uriImage;
    private static ByteArrayOutputStream output = new ByteArrayOutputStream();
    ShapeDrawable shapedrawable2;
    Button btnSad, btnTired, btnSatisfied, btnHappy, btnExcited, btnOK, btnOKCheckout;
    boolean validMood;
    private List<clsDisplayPicture> tDisplayPictureData;
    AlertDialog alertD;
    private static final int CAMERA_REQUEST = 1888;
    private static Bitmap  photoProfile;
    private static final int CAMERA_REQUEST2 = 1889;
    private static final String IMAGE_DIRECTORY_NAME = "Image Personal";
    final int SELECT_FILE = 1;
    clsDisplayPictureRepo repoDisplayPicture = null;
    FloatingActionButton fa;
    GradientDrawable gd;
    private static Bitmap photoImage1;
    private static byte[] phtImage1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home_new, container, false);
        context = getActivity().getApplicationContext();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ivProfile = (CircleImageView) v.findViewById(R.id.profile_image);
        tvUsername = (TextView) v.findViewById(R.id.tvUsername);
        fa = (FloatingActionButton) v.findViewById(R.id.DP);
        tvBranchOutlet = (TextView) v.findViewById(R.id.tvBranchOutlet);
        tvEmail = (TextView) v.findViewById(R.id.tvEmail);
        ctvStatus = (CircleTextView) v.findViewById(R.id.status);
        tvNIK = (TextView) v.findViewById(R.id.tvNIK);

        tvLatestMood = (TextView) v.findViewById(R.id.tvLatestMood);
        CiMoodImage = (CircleImageView) v.findViewById(R.id.CiMoodImage);
        tlResume = (TableLayout) v.findViewById(R.id.tlResume);
        final clsUserLogin data = new clsUserLoginRepo(context).getDataLogin(context);
        clsAbsenData dataAbsen = new clsAbsenDataRepo(context).getDataCheckinActive(context);
        clsAbsenOnline dataAbsenOnline = new clsAbsenOnlineRepo(context).getDataCheckinActive(context);

        clsLastCheckingData dataLastCheckOut = null;
        try {
            dataLastCheckOut = new clsLastCheckingDataRepo(context).findLastDataByDate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            tDisplayPictureData = (List<clsDisplayPicture>) new clsDisplayPictureRepo(getActivity().getApplicationContext()).findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (tDisplayPictureData.size() > 0 && tDisplayPictureData.get(0).getImage() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(tDisplayPictureData.get(0).getImage(), 0, tDisplayPictureData.get(0).getImage().length);
            ivProfile.setImageBitmap(bitmap);
        }

        if (data != null && data.getTxtGUI() != null) {
            tvUsername.setText(data.getTxtName().toString());
            if (data.getTxtEmail().toString().equals("null")){
                tvEmail.setText("");
            }else{
                tvEmail.setText(data.getTxtEmail().toString());
            }
            boolean lastMood = false;
            if (dataLastCheckOut != null){
                lastMood = true;
            }
            if(data.getIntMoodLogin() != 0  && lastMood == false){
                if(data.getIntMoodLogin() == 1){
                    CiMoodImage.setImageResource(R.drawable.sad);
                    tvLatestMood.setText("Sad");
                }else if (data.getIntMoodLogin() == 2){
                    CiMoodImage.setImageResource(R.drawable.tired);
                    tvLatestMood.setText("Tired");
                }else if (data.getIntMoodLogin() == 3){
                    CiMoodImage.setImageResource(R.drawable.satisfied);
                    tvLatestMood.setText("Satisfied");
                }else if (data.getIntMoodLogin() == 4){
                    CiMoodImage.setImageResource(R.drawable.excited);
                    tvLatestMood.setText("Excited");
                }else if (data.getIntMoodLogin() == 5){
                    CiMoodImage.setImageResource(R.drawable.happy);
                    tvLatestMood.setText("happy");
                }
            }else if (lastMood == true){
                if(dataLastCheckOut.getIntCheckoutMood() == 1){
                    CiMoodImage.setImageResource(R.drawable.sad);
                    tvLatestMood.setText("Sad");
                }else if (dataLastCheckOut.getIntCheckoutMood() == 2){
                    CiMoodImage.setImageResource(R.drawable.tired);
                    tvLatestMood.setText("Tired");
                }else if (dataLastCheckOut.getIntCheckoutMood() == 3){
                    CiMoodImage.setImageResource(R.drawable.satisfied);
                    tvLatestMood.setText("Satisfied");
                }else if (dataLastCheckOut.getIntCheckoutMood() == 4){
                    CiMoodImage.setImageResource(R.drawable.excited);
                    tvLatestMood.setText("Excited");
                }else if (dataLastCheckOut.getIntCheckoutMood() == 5){
                    CiMoodImage.setImageResource(R.drawable.happy);
                    tvLatestMood.setText("happy");
                }
            }else{
                CiMoodImage.setImageResource(R.drawable.joy);
            }

            String brancheshehe = "";
            try {
                List<clsBranchAccess> branches = new clsBranchAccessRepo(context).findAll();
                int arr = 0;
                if (branches.size()>0){
                    for(clsBranchAccess dt : branches){
                        if (arr == 0){
                            brancheshehe = dt.getTxtBranchName();
                        }else{
                            brancheshehe = brancheshehe +" , "+dt.getTxtBranchName();
                        }
                        arr++;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            phtImage1 = null;
            if (photoImage1 != null){
                ivProfile.setImageBitmap(photoImage1);
                photoImage1.compress(Bitmap.CompressFormat.PNG, 100, output);
                phtImage1 = output.toByteArray();
            }
            fa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                captureImage1();
                    selectImage1();
                }
            });
//            tvBranchOutlet.setText(data.getTxtNamaCabang().toString());
            tvBranchOutlet.setText(brancheshehe);
            tvNIK.setText(data.getEmployeeId());
        }
        try {
            List<clsLeaveData> dataLeave = new clsLeaveDataRepo(context).findAll();
            if (dataLeave.size() > 0) {
                ctvStatus.setText("Leaved");
                new clsMainActivity().showCustomToast(context, "Your status is Leave", false);
            } else {
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
        String absen = "";
        String checkout = "";
        Bundle inteBundle = getActivity().getIntent().getExtras();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String strVal = bundle.getString("checkout", "noob");
//            String strGUI = bundle.getString("GUI", "noob");

        }
        clsLastCheckingData dataChekin = null;
        try {
            dataChekin = new clsLastCheckingDataRepo(context).findCheckoutNotMoodyYet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if  (inteBundle != null){
            checkout = inteBundle.getString("checkout");
        }
//            boolean intentAbsen = inteBundle.getBoolean(new clsHardCode().INTENT);
        gui = data.getTxtGUI();
        int intSurveyMood = data.getBitMood();
        if (intSurveyMood == 0) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            final View promptView = layoutInflater.inflate(R.layout.mood_survey, null);
            btnOK = (Button) promptView.findViewById(R.id.btnOK);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setView(promptView);
            tvMoodId = (TextView) promptView.findViewById(R.id.moodId);
            alertDialogBuilder.setCancelable(false);
            alertD = alertDialogBuilder.create();
            alertD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertD.show();
            btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (intMoodId == 0) {
                        new clsMainActivity().showCustomToast(context, "Please pick a Mood Condition", false);
                    } else {
                        boolean valid = moodSurveyDone(gui, intMoodId);
                    }
                }
            });
            final CircleImageView imgMood = (CircleImageView) promptView.findViewById(R.id.pickMood);

            btnSad = (Button) promptView.findViewById(R.id.btnSad);
            btnTired = (Button) promptView.findViewById(R.id.btnSick);
            btnSatisfied = (Button) promptView.findViewById(R.id.btnLazy);
            btnHappy = (Button) promptView.findViewById(R.id.btnHappy);
            btnExcited = (Button) promptView.findViewById(R.id.btnCool);

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
            btnTired.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgMood.setImageResource(R.drawable.tired);
                    intMoodId = 2;
                    resetButton();
                    btnTired.setBackground(gd);
                }
            });
            btnSatisfied.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgMood.setImageResource(R.drawable.satisfied);
                    intMoodId = 3;
                    resetButton();
                    btnSatisfied.setBackground(gd);
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
            btnExcited.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgMood.setImageResource(R.drawable.excited);
                    intMoodId = 4;
                    resetButton();
                    btnExcited.setBackground(gd);
                }
            });

        } else if (intSurveyMood == 1 && dataChekin != null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            final View promptView = layoutInflater.inflate(R.layout.mood_survey_checkout, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            alertDialogBuilder.setView(promptView);
            btnOKCheckout = (Button) promptView.findViewById(R.id.btnOKCheckout);
            tvMoodId = (TextView) promptView.findViewById(R.id.moodId);
            alertDialogBuilder.setCancelable(false);
            alertD = alertDialogBuilder.create();
            alertD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertD.show();
            btnOKCheckout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (intMoodId == 0) {
                        new clsMainActivity().showCustomToast(context, "Please pick a Mood Condition", false);
                    } else {
                        boolean valid = moodSurveyDoneCheckout(gui, intMoodId);
                    }
                }
            });
            final CircleImageView imgMood = (CircleImageView) promptView.findViewById(R.id.pickMood);

            btnSad = (Button) promptView.findViewById(R.id.btnSad);
            btnTired = (Button) promptView.findViewById(R.id.btnSick);
            btnSatisfied = (Button) promptView.findViewById(R.id.btnLazy);
            btnHappy = (Button) promptView.findViewById(R.id.btnHappy);
            btnExcited = (Button) promptView.findViewById(R.id.btnCool);
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
            btnTired.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgMood.setImageResource(R.drawable.tired);
                    intMoodId = 2;
                    resetButton();
                    btnTired.setBackground(gd);
                }
            });
            btnSatisfied.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgMood.setImageResource(R.drawable.satisfied);
                    intMoodId = 3;
                    resetButton();
                    btnSatisfied.setBackground(gd);
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
            btnExcited.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgMood.setImageResource(R.drawable.excited);
                    intMoodId = 4;
                    resetButton();
                    btnExcited.setBackground(gd);
                }
            });
        }


        List<clsTrackingData> trackingDatas = new ArrayList<>();
        List<clsTrackingData> trackingDatas2 = new ArrayList<>();
        try {
            trackingDatas = (List<clsTrackingData>) new clsTrackingDataRepo(getContext()).findAll();
//            trackingDatas2 = (List<clsTrackingData>) new clsTrackingDataRepo(getContext()).getAllDataToPushData(context);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!isMyServiceRunning(MyTrackingLocationService.class)) {
            Intent i = new Intent(getActivity(), MyTrackingLocationService.class);
            getActivity().startService(i);
        }
        List<clsLastCheckingData> datasChecking = null;
        try {
            datasChecking = new clsLastCheckingDataRepo(context).findAllDataCheckin();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (datasChecking.size() > 0) {
            for (clsLastCheckingData dataChecking : datasChecking) {
                             /* Create a new row to be added. */
                TableRow tr = new TableRow(getActivity());
                tr.setBackgroundColor(Color.parseColor("#ffffff"));
                tr.setPadding(5, 5, 5, 5);
                tr.setWeightSum(3);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                            /* Create a Button to be the row-content. */
                TextView view1 = new TextView(getActivity());
                TextView view2 = new TextView(getActivity());
                TextView view3 = new TextView(getActivity());
//                view1.setText(dataChecking.getTxtOutletName());
                view1.setPadding(5, 5, 5, 5);
                view1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                if(dataChecking.getDtCheckin()!=null){
                    view2.setText(df.format(dataChecking.getDtCheckin()));
                }
                view2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                if (dataChecking.getDtCheckout() != null){
                    view3.setText(df.format(dataChecking.getDtCheckout()));
                }else{
                    view3.setText("-");
                }
                view3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1f));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


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
                new clsMainActivity().showCustomToast(getContext(), "User batal mengambil gambar", false);
            }  else {
                try {
                    photoProfile = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), data.getData());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if (requestCode == SELECT_FILE_PROFILE) {
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
                new clsMainActivity().showCustomToast(getContext(), "User batal mengambil gambar", false);
            }
        }


    }
    private Bitmap decodeUriAsBitmap(Uri uri){
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
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
            cropIntent.putExtra("aspectX", 0);
            cropIntent.putExtra("aspectY", 0);
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
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
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

//            if (dtImageProfile == null){
//                dtImageProfile.setTxtImg(phtProfile);
//            } else {
//                dtImageProfile.setTxtImg(phtProfile);
//            }
            repoDisplayPicture = new clsDisplayPictureRepo(context.getApplicationContext());
            saveImageProfile();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    protected void saveImageProfile() {
        try {
            repoDisplayPicture = new clsDisplayPictureRepo(getActivity().getApplicationContext());
            dataImageProfile = (List<clsDisplayPicture>) repoDisplayPicture.findAll();
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
           int i = new clsDisplayPictureRepo(getActivity().getApplicationContext()).createOrUpdate(data);
            getActivity().finish();
            startActivity(new Intent(getActivity(), MainMenu.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Toast.makeText(getActivity().getApplicationContext(), "Image Profile Saved", Toast.LENGTH_SHORT).show();
    }
    private void performCropProfile(){
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(uriImage, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 0);
            cropIntent.putExtra("aspectY", 0);
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
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    protected void captureImageProfile() {
        uriImage = getOutputMediaFileUri();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_PROFILE);
    }
    protected void tedChooserPicture(){
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(getActivity())
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

        tedBottomPicker.show(getActivity().getSupportFragmentManager());
    }
    private void selectImage1() {
        tedChooserPicture();
//        final CharSequence[] items = { "Take Photo", "Choose from Library",
//                "Cancel" };
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                boolean result=Utility.checkPermission(getActivity());
//                if (items[item].equals("Take Photo")) {
//                    if(result)
//                        captureImage1();
//                } else if (items[item].equals("Choose from Library")) {
//                    if(result)
//                        galleryIntent();
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
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
    private Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //use this if Lollipop_Mr1 (API 22) or above
//            return FileProvider.getUriForFile(getActivity(),getActivity().getPackageName()+".provider", getOutputMediaFile());
//        } else {
//            return Uri.fromFile(getOutputMediaFile());
//        }
    }
    protected void captureImage1() {
        uriImage = getOutputMediaFileUri();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_PROFILE);
    }
    private void galleryIntent() {
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.setType("image/*");
//        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);

        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , SELECT_FILE_PROFILE);//one can be replaced with any action code
    }
    public static class Utility {
        public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public static boolean checkPermission(final Context context)
        {
            int currentAPIVersion = Build.VERSION.SDK_INT;
            if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
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
    public static boolean isMockSettingsON(Context context) {
        // returns true if mock location enabled, false if not enabled.
        if (Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
            return false;
        else
            return true;
    }


    public void resetButton() {
        btnSad.setBackground(getResources().getDrawable(R.drawable.custom_button_round_sad));
//        btnSad = new Button(new ContextThemeWrapper(context, R.style.MyButtonSad));
        btnTired.setBackground(getResources().getDrawable(R.drawable.custom_button_round_sick));
//        btnTired = new Button(new ContextThemeWrapper(context, R.style.MyButtonSick));
        btnSatisfied.setBackground(getResources().getDrawable(R.drawable.custom_button_round_lazy));
//        btnSatisfied = new Button(new ContextThemeWrapper(context, R.style.MyButtonLazy));
        btnHappy.setBackground(getResources().getDrawable(R.drawable.custom_button_round_happy));
//        btnHappy = new Button(new ContextThemeWrapper(context, R.style.MyButtonHappy));
        btnExcited.setBackground(getResources().getDrawable(R.drawable.custom_button_round_joy));
//        btnExcited = new Button(new ContextThemeWrapper(context, R.style.MyButtonCool));
    }

    private boolean moodSurveyDone(final String guiId, final int moodId) {
        clsmConfig configData = null;
        String linkCheckinData = "";
        JSONObject resJson = new JSONObject();
        try {
            final clsUserLogin dataUserActive = new clsUserLoginRepo(context).getDataLogin(context);
            configData = (clsmConfig) new clsmConfigRepo(getActivity().getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
            linkCheckinData = configData.getTxtValue() + new clsHardCode().linkMoodSurveyCheckin;

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            String now = dateFormat.format(cal.getTime());

            try {
                resJson.put("txtTimeDetail", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
                resJson.put("guiId", guiId);
                resJson.put("moodId", moodId);
                resJson.put("txtNIK", dataUserActive.getEmployeeId());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
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
                        moodSurveyDone(guiId, moodId);
                    }
                });

            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                validMood = true;
                alertD.dismiss();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(FragmentInformation.this).attach(FragmentInformation.this).commit();

                clsUserLogin dataLogin2 = new clsUserLoginRepo(context).getDataLogin(context);
                dataLogin2.setBitMood(1);
                dataLogin2.setIntMoodLogin(moodId);
                new clsUserLoginRepo(context).update(dataLogin2);
            }
        });
        return validMood;
    }

    private boolean moodSurveyDoneCheckout(final String guiId, final int moodId) {
        clsmConfig configData = null;
        String linkCheckoutData = "";
        JSONObject resJson = new JSONObject();
        try {
            final clsUserLogin dataUserActive = new clsUserLoginRepo(context).getDataLogin(context);
            configData = (clsmConfig) new clsmConfigRepo(getActivity().getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
            linkCheckoutData = configData.getTxtValue() + new clsHardCode().linkMoodSurveyCheckout;

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            String now = dateFormat.format(cal.getTime());

            try {
                resJson.put("txtTimeDetail", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));
                resJson.put("guiId", guiId);
                resJson.put("moodId", moodId);
                resJson.put("txtNIK", dataUserActive.getEmployeeId());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        final String mRequestBody = resJson.toString();
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
                        moodSurveyDoneCheckout(guiId, moodId);
                    }
                });

            }

            @Override
            public void onResponse(String response, Boolean status, String strErrorMsg) {
                validMood = true;
                alertD.dismiss();
                FragmentTransaction ft = getFragmentManager().beginTransaction();

                ft.detach(FragmentInformation.this).attach(FragmentInformation.this).commit();
                clsUserLogin dataLogin2 = new clsUserLoginRepo(context).getDataLogin(context);

                dataLogin2.setBitMood(1);
                clsLastCheckingData dataChekin = null;
                try {
                    dataChekin = new clsLastCheckingDataRepo(context).findCheckoutNotMoodyYet();
                    dataChekin.setBoolMoodCheckout("1");
                    dataChekin.setIntCheckoutMood(moodId);
                    new clsLastCheckingDataRepo(context).update(dataChekin);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                new clsUserLoginRepo(context).update(dataLogin2);
            }
        });
        return validMood;
    }

//    private void getLastTrackingData() {
//        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
//        clsmConfig configData = null;
//        String linkPushData = "";
//        try {
//            configData = (clsmConfig) new clsmConfigRepo(getActivity().getApplicationContext()).findById(enumConfigData.API_EF.getidConfigData());
//            linkPushData = configData.getTxtValue() + new clsHardCode().linkGetLastLocation;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        String strLinkAPI = linkPushData;
////        String nameRole = selectedRole;
//        final JSONObject resJson = new JSONObject();
//        clsUserLogin dataLogin = new clsUserLogin();
//        dataLogin = new clsUserLoginRepo(getActivity().getApplicationContext()).getDataLogin(getActivity().getApplicationContext());
//
//        /*try {
//            resJson.put("TxtNIK", dataLogin.getEmployeeId());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }*/
//
//        final String mRequestBody = dataLogin.getEmployeeId();
//        new VolleyUtils().makeJsonObjectRequest(getActivity(), strLinkAPI, mRequestBody, "Getting in latest data", new VolleyResponseListener() {
//            @Override
//            public void onError(String response) {
////                new clsMainActivity().showCustomToast(getActivity().getApplicationContext(), "Connection Lost, get latest data failed", false);
//                final SweetAlertDialog dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
//                        .setTitleText("Oops...Connection lost")
//                        .setContentText("Please check your internet connection,and try again");
//                dialog.show();
//                dialog.setCancelable(false);
//                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        dialog.dismiss();
//                        getLastTrackingData();
//                    }
//                });
//
//            }
//
//            @Override
//            public void onResponse(String response, Boolean status, String strErrorMsg) {
//                try {
//                    if (!response.equals("null") && response != null) {
//                        clsTrackingData trackingData = new clsTrackingData();
//                        JSONObject jsonObject1 = new JSONObject(response);
//                        trackingData.setGuiId(new clsMainActivity().GenerateGuid());
//                        trackingData.setIntSubmit("1");
//                        trackingData.setIntSync("0");
//                        trackingData.setGuiIdLogin(jsonObject1.getString("txtGuiIdLogin"));
//                        trackingData.setTxtBranchCode(jsonObject1.getString("txtBranchCode"));
//                        int intSeq = Integer.parseInt(jsonObject1.getString("intSequence")) + 1;
//                        trackingData.setIntSequence(intSeq);
//                        trackingData.setTxtDeviceId(jsonObject1.getString("txtDeviceId"));
//                        trackingData.setTxtTime(jsonObject1.getString("dtDate"));
//                        trackingData.setTxtLatitude(jsonObject1.getString("txtLatitude"));
//                        trackingData.setTxtLongitude(jsonObject1.getString("txtLongitude"));
//                        trackingData.setTxtNIK(jsonObject1.getString("txtNIK"));
//                        trackingData.setTxtUserId(jsonObject1.getString("txtUserId"));
//                        trackingData.setTxtUsername(jsonObject1.getString("txtUsername"));
//
//                        if (!jsonObject1.getString("ltAbsen").equals("null")) {
//                            JSONArray jArray = jsonObject1.getJSONArray("ltAbsen");
//                            int jACount = jArray.length();
//                            if (jACount > 0) {
//                                for (int i = 0; i < jArray.length(); i++) {
//                                    JSONObject json_data = jArray.getJSONObject(i);
//                                    String guiID = json_data.getString("txtGUI_ID");
//                                    String outlet = json_data.getString("txtOutletName");
//                                    String outletId = json_data.getString("txtOutletId");
//                                    String dtCheckin = json_data.getString("dtCheckin");
//                                    String dtCheckout = json_data.getString("dtCheckout");
//
//                                    clsLastCheckingData dataCheckin = new clsLastCheckingData();
//                                    dataCheckin.setTxtGuiID(guiID);
//                                    dataCheckin.setTxtOutletId(outletId);
//                                    dataCheckin.setTxtOutletName(outlet);
//
//                                    dataCheckin.setDtCheckin(dtCheckin);
//                                    dataCheckin.setDtCheckout(dtCheckout);
//                                    new clsLastCheckingDataRepo(context).create(dataCheckin);
//                                }
//                            }
//
//                        }
//                        if (!jsonObject1.getString("ltAbsenAll").equals("null")) {
//                            JSONArray jArrayAll = jsonObject1.getJSONArray("ltAbsenAll");
//                            if (jArrayAll.length() > 0) {
//                                for (int i = 0; i < jArrayAll.length(); i++) {
//                                    JSONObject json_data = jArrayAll.getJSONObject(i);
//                                    String guiID = json_data.getString("txtGUI_ID");
//                                    String outlet = json_data.getString("txtOutletName");
//                                    String outletId = json_data.getString("txtOutletId");
//                                    String dtCheckin = json_data.getString("dtCheckin");
//                                    String dtCheckout = json_data.getString("dtCheckout");
//                                    String txtLongitude = json_data.getString("txtLongitude");
//                                    String txtLatitude = json_data.getString("txtLatitude");
//                                    String txtDeviceId = json_data.getString("txtDeviceId");
//                                    String txtDeviceName = json_data.getString("txtDeviceName");
//                                    String txtUserId = json_data.getString("txtUserId");
//
//                                    String dateString = "22/05/2014 11:49:00 AM";
//                                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
//                                    Date convertedDate = new Date();
//                                    try {
//                                        convertedDate = dateFormat.parse(dateString);
//                                    } catch (ParseException e) {
//                                        // TODO Auto-generated catch block
//                                        e.printStackTrace();
//                                    }
//                                    Date dtCheckinD = null;
//                                    if (!dtCheckin.equals("null")) {
//                                        dtCheckinD = dateFormat.parse(dtCheckin);
//                                    }
//                                    Date dtCheckoutD = null;
//                                    if (!dtCheckout.equals("null")) {
//                                        dtCheckoutD = dateFormat.parse(dtCheckout);
//                                    }
//                                    clsReportData dataCheckin = new clsReportData();
//                                    dataCheckin.setTxtGuiID(guiID);
//                                    dataCheckin.setTxtOutletId(outletId);
//                                    dataCheckin.setTxtOutletName(outlet);
//                                    dataCheckin.setDtCheckin(dtCheckinD);
//                                    dataCheckin.setDtCheckout(dtCheckoutD);
//                                    dataCheckin.setTxtLongitude(txtLongitude);
//                                    dataCheckin.setTxtLatitude(txtLatitude);
//                                    dataCheckin.setTxtDeviceId(txtDeviceId);
//                                    dataCheckin.setTxtDeviceName(txtDeviceName);
//                                    dataCheckin.setTxtUserId(txtUserId);
//                                    new clsReportDataRepo(context).createOrUpdate(dataCheckin);
//
//
//                                }
//                            }
//                        }
//
//                        int done = new clsTrackingDataRepo(getActivity().getApplicationContext()).create(trackingData);
//                        if (done > -1) {
//                            new clsMainActivity().showCustomToast(getActivity().getApplicationContext(), "Done", true);
//                            if (!isMyServiceRunning(MyTrackingLocationService.class)) {
//                                Intent i = new Intent(getActivity(), MyTrackingLocationService.class);
//                                getActivity().startService(i);
//                            }
//                            if (!isMyServiceRunning(MyServiceNative.class)) {
//                                Intent i = new Intent(getActivity(), MyServiceNative.class);
//
//                                getActivity().startService(i);
//                            }
//                            boolean tes, tes2;
//                            tes2 = isMyServiceRunning(MyTrackingLocationService.class);
//                            // Reload current fragment
//                            Fragment frg = null;
//                            frg = getFragmentManager().findFragmentByTag("FragmentInformation");
//                            final FragmentTransaction ft = getFragmentManager().beginTransaction();
//                            ft.detach(frg);
//                            ft.attach(frg);
//                            ft.commit();
//                        }
//
//                    } else {
//                        if (!isMyServiceRunning(MyTrackingLocationService.class)) {
//                            Intent i = new Intent(getActivity(), MyTrackingLocationService.class);
//                            getActivity().startService(i);
//                        }
//                        if (!isMyServiceRunning(MyServiceNative.class)) {
//                            Intent i = new Intent(getActivity(), MyServiceNative.class);
//
//                            getActivity().startService(i);
//                        }
//                    }
//                } catch (Exception ex) {
//                    String a = "hihi";
//                }
////                if(!status){
////                    new clsMainActivity().showCustomToast(getApplicationContext(), strErrorMsg, false);
////                }
//            }
//        });
//    }

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

