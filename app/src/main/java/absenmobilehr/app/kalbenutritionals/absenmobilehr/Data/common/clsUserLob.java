package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Robert on 21/06/2017.
 */
@DatabaseTable
public class clsUserLob {
    @DatabaseField(id = true)
    public Integer intLOBID;
    @DatabaseField
    public String txtLobName;
    @DatabaseField
    public String txtLobDesc;
    @DatabaseField
    public String bitActive;
    @DatabaseField
    public String dtNonActive;
    @DatabaseField
    public String insertedBy;
    @DatabaseField
    public String dtInserted;
    @DatabaseField
    public String updatedby;
    @DatabaseField
    public String dtUpdated;

    public clsUserLob(){}

    public Integer getIntLOBID() {
        return intLOBID;
    }

    public void setIntLOBID(Integer intLOBID) {
        this.intLOBID = intLOBID;
    }

    public String getTxtLobName() {
        return txtLobName;
    }

    public void setTxtLobName(String txtLobName) {
        this.txtLobName = txtLobName;
    }

    public String getTxtLobDesc() {
        return txtLobDesc;
    }

    public void setTxtLobDesc(String txtLobDesc) {
        this.txtLobDesc = txtLobDesc;
    }

    public String getBitActive() {
        return bitActive;
    }

    public void setBitActive(String bitActive) {
        this.bitActive = bitActive;
    }

    public String getDtNonActive() {
        return dtNonActive;
    }

    public void setDtNonActive(String dtNonActive) {
        this.dtNonActive = dtNonActive;
    }

    public String getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }

    public String getDtInserted() {
        return dtInserted;
    }

    public void setDtInserted(String dtInserted) {
        this.dtInserted = dtInserted;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public String getDtUpdated() {
        return dtUpdated;
    }

    public void setDtUpdated(String dtUpdated) {
        this.dtUpdated = dtUpdated;
    }
}
