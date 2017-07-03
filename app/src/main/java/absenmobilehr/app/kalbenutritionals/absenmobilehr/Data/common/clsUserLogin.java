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
    public Integer idUserLogin;
    @DatabaseField(columnName = "intCabangId")
    public String IntCabangID;
    @DatabaseField(columnName = "txtGUI")
    public String txtGUI;
    @DatabaseField(columnName = "txtNameApp")
    public String txtNameApp;
    @DatabaseField(columnName = "employeeId")
    public String employeeId;
    @DatabaseField(columnName = "jabatanId")
    public String jabatanId;
    @DatabaseField(columnName = "jabatanName")
    public String jabatanName;
    @DatabaseField(columnName = "cabangId")
    public String cabangId;
    @DatabaseField(columnName = "txtKodeCabang")
    public String txtKodeCabang;
    @DatabaseField(columnName = "txtNamaCabang")
    public String txtNamaCabang;
    @DatabaseField(columnName = "txtUserId")
    public String txtUserID;
    @DatabaseField(columnName = "txtUserName")
    public String txtUserName;
    @DatabaseField(columnName = "txtName")
    public String txtName;
    @DatabaseField(columnName = "txtEmail")
    public String txtEmail;
    @DatabaseField(columnName = "txtEmpId")
    public String txtEmpId;
    @DatabaseField(columnName = "dtLastLogin")
    public String dtLastLogin;
    @DatabaseField(columnName = "txtDeviceId")
    public String txtDeviceId;
    @DatabaseField(columnName = "dtLogout")
    public String dtLogOut;
    @DatabaseField(columnName = "txtInsertedBy")
    public String txtInsertedBy;
    @DatabaseField(columnName = "dtInserted")
    public String dtInserted;


    public clsUserLogin(){

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

    public String getCabangId() {
        return cabangId;
    }

    public void setCabangId(String cabangId) {
        this.cabangId = cabangId;
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

    public String getTxtEmpId() {
        return txtEmpId;
    }

    public void setTxtEmpId(String txtEmpId) {
        this.txtEmpId = txtEmpId;
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
