package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Robert on 14/07/2017.
 */

public class dataJson {
    private List<clsUserLogin> ListDatatUserLogin;
    private String _txtSessionLoginId;
    private List<clsAbsenData> ListOftAbsenUserData;
    public List<clsUserLogin> getListDatatUserLogin() {
        return ListDatatUserLogin;
    }
    private List<clsTrackingData> ListOfTrackingLocationData;
    public String _txtVersionApp;
    private String _txtUserId;
    private String dtLogon;
    private String intResult;
    private String txtMessage;
    private String txtMethod;
    private String txtValue;
    private String txtDescription;

    private String Property_txtUserId = "txtUserId";
    private String Property_txtSessionLoginId = "txtSessionLoginId";
    public String Property_intResult = "intResult";
    public String Property_txtMessage = "txtMessage";
    public String Property_txtDescription = "txtDescription";
    public String Property_txtMethod = "txtMethod";
    public String Property_txtValue = "txtValue";
    public String Property_dtLogon = "dtLogin";
    public String Property_txtVersionApp = "txtVesionApp";

    public void setListDatatUserLogin(List<clsUserLogin> listDatatUserLogin) {
        ListDatatUserLogin = listDatatUserLogin;
    }
    public String get_txtSessionLoginId() {
        return _txtSessionLoginId;
    }

    public void set_txtSessionLoginId(String _txtSessionLoginId) {
        this._txtSessionLoginId = _txtSessionLoginId;
    }
    public synchronized List<clsAbsenData> getListOftAbsenUserData() {
        return ListOftAbsenUserData;
    }

    public synchronized void setListOftAbsenUserData(
            List<clsAbsenData> listOftAbsenUserData) {
        ListOftAbsenUserData = listOftAbsenUserData;
    }
    public synchronized List<clsTrackingData> getListOfTrackingLocationData() {
        return ListOfTrackingLocationData;
    }

    public synchronized void setListOfTrackingLocationData(List<clsTrackingData> listOfTrackingLocationData) {
        ListOfTrackingLocationData = listOfTrackingLocationData;
    }
    public String get_txtVersionApp() {
        return _txtVersionApp;
    }

    public void set_txtVersionApp(String _txtVersionApp) {
        this._txtVersionApp = _txtVersionApp;
    }


    public String get_txtUserId() {
        return _txtUserId;
    }

    public void set_txtUserId(String _txtUserId) {
        this._txtUserId = _txtUserId;
    }
    public String get_dtLogin(){
        return dtLogon;
    }
    public void setDtLogon(String dtLogon){
        this.dtLogon = dtLogon;
    }


    public JSONObject txtJSON() throws JSONException {
        JSONObject resJson = new JSONObject();
        Collection<JSONObject> itemsListJquey = new ArrayList<JSONObject>();
        if (this.getListOfTrackingLocationData() != null){
            clsTrackingData dtTrackingLocationData = new clsTrackingData();
            itemsListJquey = new ArrayList<JSONObject>();
            for (clsTrackingData data : this.getListOfTrackingLocationData()){
                JSONObject item1 = new JSONObject();
                item1.put(dtTrackingLocationData.Property_GuiID, String.valueOf(data.getGuiId()));
                item1.put(dtTrackingLocationData.Property_GuiIdLogin, String.valueOf(data.getGuiIdLogin()));
                item1.put(dtTrackingLocationData.Property_txtLongitude, String.valueOf(data.getTxtLongitude()));
                item1.put(dtTrackingLocationData.Property_txtLatitude, String.valueOf(data.getTxtLatitude()));
                item1.put(dtTrackingLocationData.Property_txtTime, String.valueOf(data.getTxtTime()));
                item1.put(dtTrackingLocationData.Property_txtUserId, String.valueOf(data.getTxtUserId()));
                item1.put(dtTrackingLocationData.Property_txtUsername, String.valueOf(data.getTxtUsername()));
                item1.put(dtTrackingLocationData.Property_txtDeviceId, String.valueOf(data.getTxtDeviceId()));
                item1.put(dtTrackingLocationData.Property_txtBranchCode, String.valueOf(data.getTxtBranchCode()));
                item1.put(dtTrackingLocationData.Property_intSequence, String.valueOf(data.getIntSequence()));
                item1.put(dtTrackingLocationData.Property_txtNIK, String.valueOf(data.getTxtNIK()));
//                item1.put(dtTrackingLocationData.Property_intSubmit, String.valueOf(data.getIntSubmit()));
//                item1.put(dtTrackingLocationData.Property_intSync, String.valueOf(data.getIntSync()));
                itemsListJquey.add(item1);
            }
            resJson.put(dtTrackingLocationData.Property_ListOftrackingLocation, new JSONArray(itemsListJquey));
        }
        if (this.getListOftAbsenUserData() != null) {
            clsAbsenData dttAbsenUserData = new clsAbsenData();
            itemsListJquey = new ArrayList<JSONObject>();
            for (clsAbsenData data : this.getListOftAbsenUserData()) {
                JSONObject item1 = new JSONObject();
                item1.put(dttAbsenUserData.Property_dtCheckin, String.valueOf(data.getDtCheckin()));
                item1.put(dttAbsenUserData.Property_dtCheckout, String.valueOf(data.getDtCheckout()));
                item1.put(dttAbsenUserData.Property_txtGuiId, String.valueOf(data.getGuiId()));
                item1.put(dttAbsenUserData.Property_GuiIdLogin, String.valueOf(data.getTxtGuiIdLogin()));
                item1.put(dttAbsenUserData.Property_intSubmit, String.valueOf(data.getIntSubmit()));
                item1.put(dttAbsenUserData.Property_txtAbsen, String.valueOf(data.getTxtAbsen()));
                item1.put(dttAbsenUserData.Property_txtLatitude, String.valueOf(data.getTxtLatitude()));
                item1.put(dttAbsenUserData.Property_txtLongitude, String.valueOf(data.getTxtLongitude()));
                item1.put(dttAbsenUserData.Property_txtUserId, String.valueOf(data.getTxtUserId()));
                item1.put(dttAbsenUserData.Property_txtDeviceId, String.valueOf(data.getTxtDeviceId()));
                String img1 = "null";
                String img2 = "null";
                /*if (data.getTxtImg1()!= null){
                    Bitmap bmp1 = new clsHelper().getImage(data.getTxtImg1());
                    img1 = new clsHelper().getStringImage(bmp1);

                }
                if (data.getTxtImg2()!= null){
                    Bitmap bmp2 = new clsHelper().getImage(data.getTxtImg2());
                    img2 = new clsHelper().getStringImage(bmp2);

                }*/
                item1.put(dttAbsenUserData.Property_txtImg2, String.valueOf(img2));
                item1.put(dttAbsenUserData.Property_txtImg1, String.valueOf(img1));
                itemsListJquey.add(item1);
            }
            resJson.put(dttAbsenUserData.Property_ListOftAbsenUser, new JSONArray(itemsListJquey));
        }
        if ( this.getListDatatUserLogin() != null){
            clsUserLogin dttUserLogin = new clsUserLogin();
            itemsListJquey = new ArrayList<JSONObject>();
            for (clsUserLogin data : this.getListDatatUserLogin()) {
                JSONObject item1 = new JSONObject();
                item1.put(dttUserLogin.Property_txtGUI, String.valueOf(data.getTxtGUI()));
                item1.put(dttUserLogin.Property_employeeId, String.valueOf(data.getEmployeeId()));
                item1.put(dttUserLogin.Property_jabatanId, String.valueOf(data.getJabatanId()));
                item1.put(dttUserLogin.Property_jabatanName, String.valueOf(data.getJabatanName()));
                item1.put(dttUserLogin.Property_txtName, String.valueOf(data.getTxtName()));
                item1.put(dttUserLogin.Property_txtUserName, String.valueOf(data.getTxtUserName()));
                item1.put(dttUserLogin.Property_cabangId, String.valueOf(data.getIntCabangID()));
                item1.put(dttUserLogin.Property_txtNamaCabang, String.valueOf(data.getTxtNamaCabang()));
                item1.put(dttUserLogin.Property_dtLastLogin, String.valueOf(data.getDtLastLogin()));
                item1.put(dttUserLogin.Property_txtDeviceId, String.valueOf(data.getTxtDeviceId()));
                item1.put(dttUserLogin.Property_txtUserId, String.valueOf(data.getTxtUserID()));
                item1.put(dttUserLogin.Property_txtEmail, String.valueOf(data.getTxtEmail()));
                itemsListJquey.add(item1);
            }
            resJson.put(dttUserLogin.Property_listOfUserLogin, new JSONArray(itemsListJquey));
        }
        resJson.put(Property_intResult, getIntResult());
        resJson.put(Property_txtDescription, getTxtDescription());
        resJson.put(Property_txtMessage, getTxtMessage());
        resJson.put(Property_txtMethod, getTxtMethod());
        resJson.put(Property_txtValue, getTxtValue());
        resJson.put(Property_txtSessionLoginId, get_txtSessionLoginId());
        resJson.put(Property_txtUserId, get_txtUserId());
        resJson.put(Property_dtLogon, get_dtLogin());
        resJson.put(Property_txtVersionApp, get_txtVersionApp());

        return resJson;
    }
    public String getIntResult() {
        return intResult;
    }
    public void setIntResult(String intResult) {
        this.intResult = intResult;
    }
    public void setTxtMessage(String txtMessage) {
        this.txtMessage = txtMessage;
    }
    public String getTxtMessage() {
        return txtMessage;
    }
    public String getTxtMethod() {
        return txtMethod;
    }

    public void setTxtMethod(String txtMethod) {
        this.txtMethod = txtMethod;
    }
    public String getTxtDescription() {
        return txtDescription;
    }
    public synchronized String getTxtValue() {
        return txtValue;
    }

    public synchronized void setTxtValue(String txtValue) {
        this.txtValue = txtValue;
    }

    public void setTxtDescription(String txtDescription) {
        this.txtDescription = txtDescription;
    }

    public dataJson() {
        super();
        // TODO Auto-generated constructor stub
    }
}
