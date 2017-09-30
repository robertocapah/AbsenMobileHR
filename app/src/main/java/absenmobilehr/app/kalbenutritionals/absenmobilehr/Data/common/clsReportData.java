package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Robert on 29/09/2017.
 */
@DatabaseTable
public class clsReportData {
    @DatabaseField(id = true)
    private String txtGuiID;
    @DatabaseField
    private String txtOutletId;
    @DatabaseField
    private String txtOutletName;
    @DatabaseField
    private String dtCheckin;
    @DatabaseField
    private String dtCheckout;
    @DatabaseField
    private String txtLatitude;
    @DatabaseField
    private String txtLongitude;
    @DatabaseField
    private String txtDeviceId;
    @DatabaseField
    private String txtDeviceName;
    @DatabaseField
    private String txtUserId;

    public String Property_txtGuiID="txtGuiID";

    public String getTxtGuiID() {
        return txtGuiID;
    }

    public void setTxtGuiID(String txtGuiID) {
        this.txtGuiID = txtGuiID;
    }

    public String getTxtOutletId() {
        return txtOutletId;
    }

    public void setTxtOutletId(String txtOutletId) {
        this.txtOutletId = txtOutletId;
    }

    public String getTxtOutletName() {
        return txtOutletName;
    }

    public void setTxtOutletName(String txtOutletName) {
        this.txtOutletName = txtOutletName;
    }

    public String getDtCheckin() {
        return dtCheckin;
    }

    public void setDtCheckin(String dtCheckin) {
        this.dtCheckin = dtCheckin;
    }

    public String getDtCheckout() {
        return dtCheckout;
    }

    public void setDtCheckout(String dtCheckout) {
        this.dtCheckout = dtCheckout;
    }

    public String getTxtLatitude() {
        return txtLatitude;
    }

    public void setTxtLatitude(String txtLatitude) {
        this.txtLatitude = txtLatitude;
    }

    public String getTxtLongitude() {
        return txtLongitude;
    }

    public void setTxtLongitude(String txtLongitude) {
        this.txtLongitude = txtLongitude;
    }

    public String getTxtDeviceId() {
        return txtDeviceId;
    }

    public void setTxtDeviceId(String txtDeviceId) {
        this.txtDeviceId = txtDeviceId;
    }

    public String getTxtDeviceName() {
        return txtDeviceName;
    }

    public void setTxtDeviceName(String txtDeviceName) {
        this.txtDeviceName = txtDeviceName;
    }

    public String getTxtUserId() {
        return txtUserId;
    }

    public void setTxtUserId(String txtUserId) {
        this.txtUserId = txtUserId;
    }
}
