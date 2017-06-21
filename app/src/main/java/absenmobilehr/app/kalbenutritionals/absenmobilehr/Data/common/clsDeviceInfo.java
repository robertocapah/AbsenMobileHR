package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Robert on 16/06/2017.
 */
@DatabaseTable
public class clsDeviceInfo implements Serializable {

    @DatabaseField(columnName = "idDevice")
    public String idDevice;
    @DatabaseField(id = true, columnName = "txtGUI")
    public String txtGUI;
    @DatabaseField(columnName = "txtNameApp")
    public String txtNameApp;
    @DatabaseField(columnName = "bitActive")
    public String bitActive;
    @DatabaseField(columnName = "txtVersion")
    public String txtVersion;
    @DatabaseField(columnName = "txtFile")
    public String txtFile;
    @DatabaseField(columnName = "txtDevice")
    public String txtDevice;
    @DatabaseField(columnName = "txtModel")
    public String txtModel;
    @DatabaseField(columnName = "txtUserId")
    public String txtUserId;
    @DatabaseField(columnName = "txtInsertedBy")
    public String txtInsertedBy;

    public clsDeviceInfo() {

    }

    public String getTxtFile() {
        return txtFile;
    }

    public void setTxtFile(String txtFile) {
        this.txtFile = txtFile;
    }

    public String getTxtInsertedBy() {
        return txtInsertedBy;
    }

    public void setTxtInsertedBy(String txtInsertedBy) {
        this.txtInsertedBy = txtInsertedBy;
    }

    public String getTxtNameApp() {
        return txtNameApp;
    }

    public void setTxtNameApp(String txtNameApp) {
        this.txtNameApp = txtNameApp;
    }

    public String getBitActive() {
        return bitActive;
    }

    public void setBitActive(String bitActive) {
        this.bitActive = bitActive;
    }

    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }

    public String getTxtGUI() {
        return txtGUI;
    }

    public void setTxtGUI(String txtGUI) {
        this.txtGUI = txtGUI;
    }

    public String getTxtVersion() {
        return txtVersion;
    }

    public void setTxtVersion(String txtVersion) {
        this.txtVersion = txtVersion;
    }

    public String getTxtDevice() {
        return txtDevice;
    }

    public void setTxtDevice(String txtDevice) {
        this.txtDevice = txtDevice;
    }

    public String getTxtModel() {
        return txtModel;
    }

    public void setTxtModel(String txtModel) {
        this.txtModel = txtModel;
    }

    public String getTxtUserId() {
        return txtUserId;
    }

    public void setTxtUserId(String txtUserId) {
        this.txtUserId = txtUserId;
    }
}
