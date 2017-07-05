package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Robert on 16/06/2017.
 */
@DatabaseTable
public class clsDeviceInfo implements Serializable {

    @DatabaseField(id = true, columnName = "idDevice")
    public String idDevice;
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

    public String getTxtInsertedBy() {
        return txtInsertedBy;
    }

    public void setTxtInsertedBy(String txtInsertedBy) {
        this.txtInsertedBy = txtInsertedBy;
    }

    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
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
