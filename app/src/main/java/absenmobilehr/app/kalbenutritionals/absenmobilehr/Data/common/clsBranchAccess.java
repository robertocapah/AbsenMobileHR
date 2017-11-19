package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Robert on 18/10/2017.
 */
@DatabaseTable
public class clsBranchAccess implements Serializable {
    @DatabaseField(id = true)
    private Integer IntBranchID;
    @DatabaseField
    private String TxtBranchCode;
    @DatabaseField
    private String TxtBranchName;
    @DatabaseField
    private String TxtBranchSiteId;
    @DatabaseField
    private String TxtBranchCOA;
    @DatabaseField
    private String BitActive;
    @DatabaseField
    private String DtNonActive;

    public String Property_IntBranchID="IntBranchID";

    public Integer getIntBranchID() {
        return IntBranchID;
    }

    public void setIntBranchID(Integer intBranchID) {
        IntBranchID = intBranchID;
    }

    public String getTxtBranchCode() {
        return TxtBranchCode;
    }

    public void setTxtBranchCode(String txtBranchCode) {
        TxtBranchCode = txtBranchCode;
    }

    public String getTxtBranchName() {
        return TxtBranchName;
    }

    public void setTxtBranchName(String txtBranchName) {
        TxtBranchName = txtBranchName;
    }

    public String getTxtBranchSiteId() {
        return TxtBranchSiteId;
    }

    public void setTxtBranchSiteId(String txtBranchSiteId) {
        TxtBranchSiteId = txtBranchSiteId;
    }

    public String getTxtBranchCOA() {
        return TxtBranchCOA;
    }

    public void setTxtBranchCOA(String txtBranchCOA) {
        TxtBranchCOA = txtBranchCOA;
    }

    public String getBitActive() {
        return BitActive;
    }

    public void setBitActive(String bitActive) {
        BitActive = bitActive;
    }

    public String getDtNonActive() {
        return DtNonActive;
    }

    public void setDtNonActive(String dtNonActive) {
        DtNonActive = dtNonActive;
    }
}
