package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Robert on 21/06/2017.
 */
@DatabaseTable
public class clsUserPegawai {
    @DatabaseField(id = true,columnName = "intPegawaiId")
    public Integer intPegawaiId;
    @DatabaseField(columnName = "intDomainId")
    public Integer intDomainId;
    @DatabaseField(columnName = "txtPegawaiId")
    public String txtPegawaiId;
    @DatabaseField(columnName = "txtNik")
    public String txtNik;
    @DatabaseField(columnName = "txtPegawaiName")
    public String txtPegawaiName;
    @DatabaseField(columnName = "txtEmail")
    public String txtEmail;
    @DatabaseField(columnName = "txtUserCRM")
    public String txtUserCRM;
    @DatabaseField(columnName = "bitDomainUser")
    public String bitDomainUser;
    @DatabaseField(columnName = "bitActive")
    public String bitActive;
    @DatabaseField(columnName = "dtNonActive")
    public String dtNonActive;
    @DatabaseField(columnName = "txtInsertedBy")
    public String txtInsertedBy;
    @DatabaseField(columnName = "dtInserted")
    public String dtInserted;
    @DatabaseField(columnName = "dtUpdated")
    public String dtUpdated;

    public clsUserPegawai(){}

    public Integer getIntPegawaiId() {
        return intPegawaiId;
    }

    public void setIntPegawaiId(Integer intPegawaiId) {
        this.intPegawaiId = intPegawaiId;
    }

    public Integer getIntDomainId() {
        return intDomainId;
    }

    public void setIntDomainId(Integer intDomainId) {
        this.intDomainId = intDomainId;
    }

    public String getTxtPegawaiId() {
        return txtPegawaiId;
    }

    public void setTxtPegawaiId(String txtPegawaiId) {
        this.txtPegawaiId = txtPegawaiId;
    }

    public String getTxtNik() {
        return txtNik;
    }

    public void setTxtNik(String txtNik) {
        this.txtNik = txtNik;
    }

    public String getTxtPegawaiName() {
        return txtPegawaiName;
    }

    public void setTxtPegawaiName(String txtPegawaiName) {
        this.txtPegawaiName = txtPegawaiName;
    }

    public String getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(String txtEmail) {
        this.txtEmail = txtEmail;
    }

    public String getTxtUserCRM() {
        return txtUserCRM;
    }

    public void setTxtUserCRM(String txtUserCRM) {
        this.txtUserCRM = txtUserCRM;
    }

    public String getBitDomainUser() {
        return bitDomainUser;
    }

    public void setBitDomainUser(String bitDomainUser) {
        this.bitDomainUser = bitDomainUser;
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

    public String getTxtInsertedBy() {
        return txtInsertedBy;
    }

    public void setTxtInsertedBy(String txtInsertedBy) {
        this.txtInsertedBy = txtInsertedBy;
    }

    public String getDtInserted() {
        return dtInserted;
    }

    public void setDtInserted(String dtInserted) {
        this.dtInserted = dtInserted;
    }

    public String getDtUpdated() {
        return dtUpdated;
    }

    public void setDtUpdated(String dtUpdated) {
        this.dtUpdated = dtUpdated;
    }
}
