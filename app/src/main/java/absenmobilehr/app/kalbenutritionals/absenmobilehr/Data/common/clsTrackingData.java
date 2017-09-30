package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Robert on 06/07/2017.
 */

public class clsTrackingData implements Serializable {
    @DatabaseField(columnName = "GuiId", id = true)
    private String GuiId;
    @DatabaseField(columnName = "GuiIdLogin")
    private String GuiIdLogin;
    @DatabaseField(columnName = "txtLongitude")
    private String txtLongitude;
    @DatabaseField(columnName = "txtLatitude")
    private String txtLatitude;
    @DatabaseField(columnName = "txtTime")
    private String txtTime;
    @DatabaseField(columnName = "txtUserId")
    private String txtUserId;
    @DatabaseField(columnName = "txtUsername")
    private String txtUsername;
    @DatabaseField(columnName = "txtDeviceId")
    private String txtDeviceId;
    @DatabaseField(columnName = "txtBranchCode")
    private String txtBranchCode;
    @DatabaseField(columnName = "txtNIK")
    private String txtNIK;
    @DatabaseField(columnName = "intSequence")
    private Integer intSequence;
    @DatabaseField(columnName = "intSubmit")
    private String intSubmit;
    @DatabaseField(columnName = "intSync")
    private String intSync;

    public clsTrackingData(){}

    public String Property_GuiID = "GuiID";
    public String Property_GuiIdLogin = "GuiIdLogin";
    public String Property_txtLongitude = "txtLongitude";
    public String Property_txtLatitude = "txtLatitude";
    public String Property_txtTime = "txtTime";
    public String Property_txtUserId = "txtUserId";
    public String Property_txtUsername = "txtUsername";
    public String Property_txtDeviceId = "txtDeviceId";
    public String Property_txtBranchCode = "txtBranchCode";
    public String Property_txtNIK = "txtNIK";
    public String Property_intSubmit = "intSubmit";
    public String Property_intSync = "intSync";
    public String Property_intSequence = "intSequence";
    public String Property_ListOftrackingLocation = "ListOfTrackingLocationData";
    public String Property_All = Property_GuiID + "," +
            Property_txtLongitude + "," +
            Property_txtLatitude + "," +
            Property_txtTime + "," +
            Property_txtUserId + "," +
            Property_txtUsername + "," +
            Property_txtDeviceId + "," +
            Property_txtBranchCode + "," +
            Property_txtNIK + "," +
            Property_intSequence + "," +
            Property_intSubmit + "," +
            Property_intSync;

    public String getGuiIdLogin() {
        return GuiIdLogin;
    }

    public void setGuiIdLogin(String guiIdLogin) {
        GuiIdLogin = guiIdLogin;
    }

    public String getGuiId() {
        return GuiId;
    }

    public void setGuiId(String intId) {
        this.GuiId = intId;
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

    public String getTxtTime() {
        return txtTime;
    }

    public void setTxtTime(String txtTime) {
        this.txtTime = txtTime;
    }

    public String getTxtUserId() {
        return txtUserId;
    }

    public void setTxtUserId(String txtUserId) {
        this.txtUserId = txtUserId;
    }

    public String getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(String txtUsername) {
        this.txtUsername = txtUsername;
    }

    public String getTxtDeviceId() {
        return txtDeviceId;
    }

    public void setTxtDeviceId(String txtDeviceId) {
        this.txtDeviceId = txtDeviceId;
    }

    public String getTxtBranchCode() {
        return txtBranchCode;
    }

    public void setTxtBranchCode(String txtBranchCode) {
        this.txtBranchCode = txtBranchCode;
    }

    public String getTxtNIK() {
        return txtNIK;
    }

    public void setTxtNIK(String txtNIK) {
        this.txtNIK = txtNIK;
    }

    public String getIntSubmit() {
        return intSubmit;
    }

    public void setIntSubmit(String intSubmit) {
        this.intSubmit = intSubmit;
    }

    public String getIntSync() {
        return intSync;
    }

    public void setIntSync(String intSync) {
        this.intSync = intSync;
    }

    public Integer getIntSequence() {
        return intSequence;
    }

    public void setIntSequence(Integer intSequence) {
        this.intSequence = intSequence;
    }
}
