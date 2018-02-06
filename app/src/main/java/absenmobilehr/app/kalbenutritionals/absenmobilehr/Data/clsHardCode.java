package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data;

import android.os.Environment;

import java.io.File;

/**
 * Created by Robert on 20/06/2017.
 */

public class clsHardCode {
    public final String INTENT = "INTENT";
    public final String GUI = "GUI";
    public String dbName = "KalbeAbsenHR.db";
    public String txtPathApp= Environment.getExternalStorageDirectory()+ File.separator+"Android"+File.separator+"data"+File.separator+"KalbeAbsenHR"+File.separator+"app_database"+File.separator;
    public String txtPathUserData= Environment.getExternalStorageDirectory()+File.separator+"Android"+File.separator+"data"+File.separator+"KalbeAbsenHR"+File.separator+"user_data"+File.separator;
    public String txtFolderAbsen = Environment.getExternalStorageDirectory()+File.separator+"Android"+File.separator+"data"+File.separator+"KalbeAbsenHR"+File.separator+"image_Absen"+File.separator;
    public String txtMessCancelRequest = "Canceled Request Data";
    public String intSuccess = "1";
//    public String linkGetLastLocation = "http://192.168.66.1/APIEF2/api/TrackingDataAPI/getDataLastLocation/{id}";
    public String linkGetLastLocation = "TrackingDataAPI/getDataLastLocation/{id}";
    public String linkLogin = "LoginAPI/Login";
    public String linkLeave = "LeaveAbsen/setLeaveData";
    public String linkCheckVersion = "CheckVersionApp_J";
    public String linkGetLinkApk = "getLinkDownloadApk";
    public String linkPushData = "PushData/pushData2";
    public String linkGetOutlet = "TrackingDataAPI/getOutlet";
    public String linkGetOutletReporting = "ReportingMobile/getOutlet";
    public String linkGetReport = "ReportingMobile/getReportPeriode";
    public String linkAbsen = "AbsenAPI/AbsenOnline";
    public String linkGetApkLink = "UpdateMobileApp/getLinkAPK";
    public String linkMoodSurveyCheckin ="LoginAPI/moodSurvey";
    public String linkMoodSurveyCheckout ="AbsenAPI/moodSurveyCheckout";
    public String linkCheckoutAbsen = "AbsenAPI/AbsenCheckoutOnline";
//    public String linkPushData = "http://192.168.66.1/APIEF2/api/PushData/pushData2";
//    public String linkLogout = "LogOut_J";
    public String leave_sick = "SICK";
    public String leave_trn = "TRAINING";
    public String name_app = "Android - HR Mobile";
    public String txtFolderData = Environment.getExternalStorageDirectory()+ File.separator+"Android"+File.separator+"data"+File.separator+"KalbeAbsenHR"+File.separator+"image_Person"+File.separator;
    public String linkLogout = "LoginAPI/Logout";
    public String apkName = "absenMobileHR.apk";
//    public String linkDownloadApk = "files/HRISMobileKN.apk";
    public String linkDownloadApk = "HRISMobileKN.apk";
    public String linkGetToken = "Token";
    public String MY_PREFS_NAME = "token_available";
}
