package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Robert on 15/06/2017.
 */
@DatabaseTable
public class clsUserLogin implements Serializable{
    @DatabaseField(id = true,columnName = "clsUserLogin")
    private Integer idUserLogin;
    @DatabaseField(columnName = "intCabangId")
    private String IntCabangID;
    @DatabaseField(columnName = "txtGUI")
    private String txtGUI;
    @DatabaseField(columnName = "txtNameApp")
    private String txtNameApp;
    @DatabaseField(columnName = "employeeId")
    private String employeeId;
    @DatabaseField(columnName = "jabatanId")
    private String jabatanId;
    @DatabaseField(columnName = "jabatanName")
    private String jabatanName;
    @DatabaseField(columnName = "txtKodeCabang")
    private String txtKodeCabang;
    @DatabaseField(columnName = "txtNamaCabang")
    private String txtNamaCabang;
    @DatabaseField(columnName = "txtUserId")
    private String txtUserID;
    @DatabaseField(columnName = "txtUserName")
    private String txtUserName;
    @DatabaseField(columnName = "txtName")
    private String txtName;
    @DatabaseField(columnName = "txtEmail")
    private String txtEmail;
    @DatabaseField(columnName = "dtLastLogin")
    private String dtLastLogin;
    @DatabaseField(columnName = "txtDeviceId")
    private String txtDeviceId;
    @DatabaseField(columnName = "dtLogout")
    private String dtLogOut;
    @DatabaseField(columnName = "bitMood")
    private int bitMood;
    @DatabaseField(columnName = "txtInsertedBy")
    private String txtInsertedBy;
    @DatabaseField(columnName = "dtInserted")
    private String dtInserted;
//    public String Property_dbName = new clsUserLogin().getClass().getSimpleName();
    public String Property_dbName = "clsUserLogin";
    public String Property_clsUserLogin = "clsUserLogin";
    public String Property_intCabangId = "intCabangId";
    public String Property_txtGUI = "txtGUI";
    public String Property_txtNameApp = "txtNameApp";
    public String Property_employeeId = "employeeId";
    public String Property_jabatanId = "jabatanId";
    public String Property_jabatanName = "jabatanName";
    public String Property_cabangId = "cabangId";
    public String Property_txtKodeCabang = "txtKodeCabang";
    public String Property_txtNamaCabang = "txtNamaCabang";
    public String Property_txtUserId = "txtUserId";
    public String Property_txtUserName = "txtUserName";
    public String Property_txtName = "txtName";
    public String Property_txtEmail = "txtEmail";
    public String Property_dtLastLogin = "dtLastLogin";
    public String Property_txtDeviceId = "txtDeviceId";
    public String Property_dtLogout = "dtLogout";
    public String Property_bitMood = "bitMood";
    public String Property_listOfUserLogin = "listOfUserLogin";

    public String Property_All=Property_clsUserLogin +","+
            Property_intCabangId +","+
            Property_txtGUI +","+
            Property_txtNameApp +","+
            Property_employeeId +","+
            Property_jabatanId +","+
            Property_jabatanName +","+
            Property_txtKodeCabang +","+
            Property_txtNamaCabang +","+
            Property_txtUserId +","+
            Property_txtUserName +","+
            Property_txtName +","+
            Property_txtEmail +","+
            Property_dtLastLogin+","+
            Property_txtDeviceId+","+
            Property_dtLogout;

    public clsUserLogin(){

    }

    public int getBitMood() {
        return bitMood;
    }

    public void setBitMood(int bitMood) {
        this.bitMood = bitMood;
    }

    public String getTxtInsertedBy() {
        return txtInsertedBy;
    }

    public void setTxtInsertedBy(String txtInsertedBy) {
        this.txtInsertedBy = txtInsertedBy;
    }

    public String getJabatanId() {
        return jabatanId;
    }

    public void setJabatanId(String jabatanId) {
        this.jabatanId = jabatanId;
    }

    public String getJabatanName() {
        return jabatanName;
    }

    public void setJabatanName(String jabatanName) {
        this.jabatanName = jabatanName;
    }

    public String getTxtNameApp() {
        return txtNameApp;
    }

    public void setTxtNameApp(String txtNameApp) {
        this.txtNameApp = txtNameApp;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getTxtKodeCabang() {
        return txtKodeCabang;
    }

    public void setTxtKodeCabang(String txtKodeCabang) {
        this.txtKodeCabang = txtKodeCabang;
    }

    public String getTxtNamaCabang() {
        return txtNamaCabang;
    }

    public void setTxtNamaCabang(String txtNamaCabang) {
        this.txtNamaCabang = txtNamaCabang;
    }

    public Integer getIdUserLogin() {
        return idUserLogin;
    }

    public void setIdUserLogin(Integer idUserLogin) {
        this.idUserLogin = idUserLogin;
    }

    public String getIntCabangID() {
        return IntCabangID;
    }

    public void setIntCabangID(String intCabangID) {
        IntCabangID = intCabangID;
    }

    public String getTxtGUI() {
        return txtGUI;
    }

    public void setTxtGUI(String txtGUI) {
        this.txtGUI = txtGUI;
    }

    public String getTxtUserID() {
        return txtUserID;
    }

    public void setTxtUserID(String txtUserID) {
        this.txtUserID = txtUserID;
    }

    public String getTxtUserName() {
        return txtUserName;
    }

    public void setTxtUserName(String txtUserName) {
        this.txtUserName = txtUserName;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public String getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(String txtEmail) {
        this.txtEmail = txtEmail;
    }

    public String getDtLastLogin() {
        return dtLastLogin;
    }

    public void setDtLastLogin(String dtLastLogin) {
        this.dtLastLogin = dtLastLogin;
    }

    public String getTxtDeviceId() {
        return txtDeviceId;
    }

    public void setTxtDeviceId(String txtDeviceId) {
        this.txtDeviceId = txtDeviceId;
    }

    public String getDtInserted() {
        return dtInserted;
    }

    public void setDtInserted(String dtInserted) {
        this.dtInserted = dtInserted;
    }

    public String getDtLogOut() {
        return dtLogOut;
    }

    public void setDtLogOut(String dtLogOut) {
        this.dtLogOut = dtLogOut;
    }
}
