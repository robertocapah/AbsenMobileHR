package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Robert on 21/06/2017.
 */
@DatabaseTable
public class clsUserJabatan {
    @DatabaseField(id=true,columnName = "intJabatanId")
    public Integer intJabatanId;
    @DatabaseField(columnName = "txtJabatanName")
    public String txtJabatanName;
    @DatabaseField(columnName = "txtJabatanDesc")
    public String txtJabatanDesc;
    @DatabaseField(columnName = "bitPrimary")
    public String bitPrimary;
    @DatabaseField(columnName = "limit")
    public String limit;

    public clsUserJabatan(){
    }

    public Integer getIntJabatanId() {
        return intJabatanId;
    }

    public void setIntJabatanId(Integer intJabatanId) {
        this.intJabatanId = intJabatanId;
    }

    public String getTxtJabatanName() {
        return txtJabatanName;
    }

    public void setTxtJabatanName(String txtJabatanName) {
        this.txtJabatanName = txtJabatanName;
    }

    public String getTxtJabatanDesc() {
        return txtJabatanDesc;
    }

    public void setTxtJabatanDesc(String txtJabatanDesc) {
        this.txtJabatanDesc = txtJabatanDesc;
    }

    public String getBitPrimary() {
        return bitPrimary;
    }

    public void setBitPrimary(String bitPrimary) {
        this.bitPrimary = bitPrimary;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
