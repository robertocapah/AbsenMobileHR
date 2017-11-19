package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Robert on 29/09/2017.
 */
@DatabaseTable
public class clsLastCheckingData implements Serializable {
    @DatabaseField(id = true)
    private String txtGuiID;
    @DatabaseField
    private String txtOutletId;
    @DatabaseField
    private String txtOutletName;
    @DatabaseField
    private String dtCheckin;
    @DatabaseField
    private String dtCheckout;

    public String Property_txtGuiID = "txtGuiID";

    public String getTxtGuiID() {
        return txtGuiID;
    }

    public void setTxtGuiID(String txtGuiID) {
        this.txtGuiID = txtGuiID;
    }

    public String getTxtOutletId() {
        return txtOutletId;
    }

    public void setTxtOutletId(String txtOutletId) {
        this.txtOutletId = txtOutletId;
    }

    public String getTxtOutletName() {
        return txtOutletName;
    }

    public void setTxtOutletName(String txtOutletName) {
        this.txtOutletName = txtOutletName;
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
}
