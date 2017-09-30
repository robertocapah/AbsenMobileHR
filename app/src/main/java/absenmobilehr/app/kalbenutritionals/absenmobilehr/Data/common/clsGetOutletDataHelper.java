package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Robert on 14/09/2017.
 */

public class clsGetOutletDataHelper {
    private String txtIdUserLogin;
    private String txtLongitude;
    private String txtLatitude;
//    private String txtOutletId;
//    private String txtOutletName;
//    private String txtQRCode;
    private String txtDeviceId;
    private String txtUsername;


    public String Property_txtIdUserLogin = "txtIdUserLogin";
    public String Property_txtLongitude ="txtLongitude";
    public String Property_txtLatitude ="txtLatitude";
    public String Property_txtDeviceId ="txtDeviceId";
    public String Property_txtUsername ="txtUsername";

    public String getTxtIdUserLogin() {
        return txtIdUserLogin;
    }

    public void setTxtIdUserLogin(String txtIdUserLogin) {
        this.txtIdUserLogin = txtIdUserLogin;
    }

    public String getTxtLongitude() {
        return txtLongitude;
    }

    public void setTxtLongitude(String txtLongitude) {
        this.txtLongitude = txtLongitude;
    }

    public String getTxtLatitude() {
        return txtLatitude;
    }

    public void setTxtLatitude(String txtLatitude) {
        this.txtLatitude = txtLatitude;
    }

//    public String getTxtOutletId() {
//        return txtOutletId;
//    }
//
//    public void setTxtOutletId(String txtOutletId) {
//        this.txtOutletId = txtOutletId;
//    }
//
//    public String getTxtOutletName() {
//        return txtOutletName;
//    }
//
//    public void setTxtOutletName(String txtOutletName) {
//        this.txtOutletName = txtOutletName;
//    }
//
//    public String getTxtQRCode() {
//        return txtQRCode;
//    }
//
//    public void setTxtQRCode(String txtQRCode) {
//        this.txtQRCode = txtQRCode;
//    }

    public String getTxtDeviceId() {
        return txtDeviceId;
    }

    public void setTxtDeviceId(String txtDeviceId) {
        this.txtDeviceId = txtDeviceId;
    }

    public String getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(String txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JSONObject txtJSON() throws JSONException {
        JSONObject resJson = new JSONObject();

        return resJson;
    }
}
