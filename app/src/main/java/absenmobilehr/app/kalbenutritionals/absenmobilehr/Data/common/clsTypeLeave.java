package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Robert on 13/10/2017.
 */
@DatabaseTable
public class clsTypeLeave implements Serializable {
    @DatabaseField(id = true)
    private int intLeaveID;
    @DatabaseField
    private String txtLeaveCode;
    @DatabaseField
    private String txtLeaveName;
    @DatabaseField
    private String txtKeterangan;
    @DatabaseField
    private int bitActive;

    public String Property_intLeaveID = "intLeaveID";

    public int getIntLeaveID() {
        return intLeaveID;
    }

    public void setIntLeaveID(int intLeaveID) {
        this.intLeaveID = intLeaveID;
    }

    public String getTxtLeaveCode() {
        return txtLeaveCode;
    }

    public void setTxtLeaveCode(String txtLeaveCode) {
        this.txtLeaveCode = txtLeaveCode;
    }

    public String getTxtLeaveName() {
        return txtLeaveName;
    }

    public void setTxtLeaveName(String txtLeaveName) {
        this.txtLeaveName = txtLeaveName;
    }

    public String getTxtKeterangan() {
        return txtKeterangan;
    }

    public void setTxtKeterangan(String txtKeterangan) {
        this.txtKeterangan = txtKeterangan;
    }

    public int getBitActive() {
        return bitActive;
    }

    public void setBitActive(int bitActive) {
        this.bitActive = bitActive;
    }
}
