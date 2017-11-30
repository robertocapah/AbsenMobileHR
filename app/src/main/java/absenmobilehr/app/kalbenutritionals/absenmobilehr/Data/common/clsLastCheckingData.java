package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

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
    @DatabaseField(columnName = "dtCheckin", dataType = DataType.DATE_STRING,format = "yyyy-MM-dd HH:mm:ss")
    private Date dtCheckin;
    @DatabaseField(columnName = "dtCheckout", dataType = DataType.DATE_STRING,format = "yyyy-MM-dd HH:mm:ss")
    private Date dtCheckout;
    @DatabaseField
    private Integer intCheckoutMood;
    @DatabaseField
    private String boolMoodCheckout;

    public String Property_txtGuiID = "txtGuiID";
    public String Property_boolMoodCheckout = "boolMoodCheckout";
    public String Property_dtCheckout = "dtCheckout";
    public String Property_dtCheckin = "dtCheckin";
    public String Property_intCheckoutMood = "intCheckoutMood";

    public Integer getIntCheckoutMood() {
        return intCheckoutMood;
    }

    public void setIntCheckoutMood(Integer intCheckoutMood) {
        this.intCheckoutMood = intCheckoutMood;
    }

    public String getBoolMoodCheckout() {
        return boolMoodCheckout;
    }

    public void setBoolMoodCheckout(String boolMoodCheckout) {
        this.boolMoodCheckout = boolMoodCheckout;
    }

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

    public Date getDtCheckin() {
        return dtCheckin;
    }

    public void setDtCheckin(Date dtCheckin) {
        this.dtCheckin = dtCheckin;
    }

    public Date getDtCheckout() {
        return dtCheckout;
    }

    public void setDtCheckout(Date dtCheckout) {
        this.dtCheckout = dtCheckout;
    }
}
