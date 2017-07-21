package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Robert on 05/07/2017.
 */
@DatabaseTable
public class clsAbsenData implements Serializable {
    @DatabaseField(id = true, columnName = "intId")
    private String txtGuiId;
    @DatabaseField(columnName = "txtGuiIdLogin")
    private String txtGuiIdLogin;
    @DatabaseField(columnName = "dtCheckin")
    private String dtCheckin;
    @DatabaseField(columnName = "dtCheckout")
    private String dtCheckout;
    @DatabaseField(columnName = "intSubmit")
    private String intSubmit;
    @DatabaseField(columnName = "Sync")
    private String Sync;
    @DatabaseField(columnName = "txtAbsen")
    private String txtAbsen;
    @DatabaseField(columnName = "txtLatitude")
    private String txtLatitude;
    @DatabaseField(columnName = "txtLongitude")
    private String txtLongitude;
    @DatabaseField(columnName = "txtDeviceId")
    private String txtDeviceId;
    @DatabaseField(columnName = "txtImg1", dataType = DataType.BYTE_ARRAY)
    private byte[] txtImg1;
    @DatabaseField(columnName = "txtImg2", dataType = DataType.BYTE_ARRAY)
    private byte[] txtImg2;
    @DatabaseField(columnName = "txtUserId")
    private String txtUserId;

    public String Property_txtGuiId = "txtGuiId";
    public String Property_GuiIdLogin = "GuiIdLogin";
    public String Property_dtCheckin = "dtCheckin";
    public String Property_dtCheckout = "dtCheckout";
    public String Property_intSubmit = "intSubmit";
    public String Property_Sync = "Sync";
    public String Property_txtAbsen = "txtAbsen";
    public String Property_txtLatitude = "txtLatitude";
    public String Property_txtLongitude = "txtLongitude";
    public String Property_txtDeviceId = "txtDeviceId";
    public String Property_txtImg1 = "txtImg1";
    public String Property_txtImg2 = "txtImg2";
    public String Property_txtUserId = "txtUserId";
    public String Property_ListOftAbsenUser = "ListOftAbsenUser";
    public String Property_All=Property_txtGuiId +","+
            Property_dtCheckin +","+
            Property_dtCheckout +","+
            Property_intSubmit +","+
            Property_Sync +","+
            Property_txtAbsen +","+
            Property_txtLatitude +","+
            Property_txtLongitude +","+
            Property_txtDeviceId +","+
            Property_txtImg1 +","+
            Property_txtImg2 +","+
            Property_txtUserId;

    public clsAbsenData(){}

    public String getGuiId() {
        return txtGuiId;
    }

    public void setGuiId(String intId) {
        this.txtGuiId = intId;
    }

    public String getTxtGuiIdLogin() {
        return txtGuiIdLogin;
    }

    public void setTxtGuiIdLogin(String txtGuiIdLogin) {
        this.txtGuiIdLogin = txtGuiIdLogin;
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

    public String getIntSubmit() {
        return intSubmit;
    }

    public void setIntSubmit(String intSubmit) {
        this.intSubmit = intSubmit;
    }

    public String getSync() {
        return Sync;
    }

    public void setSync(String sync) {
        Sync = sync;
    }

    public String getTxtAbsen() {
        return txtAbsen;
    }

    public void setTxtAbsen(String txtAbsen) {
        this.txtAbsen = txtAbsen;
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

    public byte[] getTxtImg1() {
        return txtImg1;
    }

    public void setTxtImg1(byte[] txtImg1) {
        this.txtImg1 = txtImg1;
    }

    public byte[] getTxtImg2() {
        return txtImg2;
    }

    public void setTxtImg2(byte[] txtImg2) {
        this.txtImg2 = txtImg2;
    }

    public String getTxtUserId() {
        return txtUserId;
    }

    public void setTxtUserId(String txtUserId) {
        this.txtUserId = txtUserId;
    }
}
