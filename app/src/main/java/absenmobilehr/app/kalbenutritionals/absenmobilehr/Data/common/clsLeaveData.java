package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Robert on 13/10/2017.
 */
@DatabaseTable
public class clsLeaveData implements Serializable {
    @DatabaseField(generatedId = true)
    private int leaveId;
    @DatabaseField
    private String txtGuiIDLogin;
    @DatabaseField
    private String txtNIK;
    @DatabaseField
    private String txtKeterangan;
    @DatabaseField
    private String txtTime;
    @DatabaseField
    private int BitActive;

    public String Property_txtGuiIDLogin = "txtGuiIDLogin";
    public String Property_txtNIK = "txtNIK";

    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    public String getTxtGuiIDLogin() {
        return txtGuiIDLogin;
    }

    public void setTxtGuiIDLogin(String txtGuiIDLogin) {
        this.txtGuiIDLogin = txtGuiIDLogin;
    }

    public String getTxtNIK() {
        return txtNIK;
    }

    public void setTxtNIK(String txtNIK) {
        this.txtNIK = txtNIK;
    }

    public String getTxtKeterangan() {
        return txtKeterangan;
    }

    public void setTxtKeterangan(String txtKeterangan) {
        this.txtKeterangan = txtKeterangan;
    }

    public String getTxtTime() {
        return txtTime;
    }

    public void setTxtTime(String txtTime) {
        this.txtTime = txtTime;
    }

    public int getBitActive() {
        return BitActive;
    }

    public void setBitActive(int bitActive) {
        BitActive = bitActive;
    }
}


