package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by Robert on 06/07/2017.
 */

public class clsmCounterData implements Serializable {
    @DatabaseField(id = true, columnName = "intId",generatedId = true)
    private int intId;
    @DatabaseField(columnName = "txtName")
    private String txtName;
    @DatabaseField(columnName = "txtValue")
    private String txtValue;
    @DatabaseField(columnName = "txtDeskripsi")
    private String txtDeskripsi;
    public String Property_intId="intId";
    public String Property_txtName="txtName";
    public String Property_txtValue="txtValue";
    public String Property_txtDeskripsi="txtDeskripsi";
    public String Property_ListOfmCounterNumber="ListOfmCounterNumber";
    public String Property_All=Property_intId + "," +
            Property_txtDeskripsi + "," +
            Property_txtName + "," +
            Property_txtValue ;
    public clsmCounterData(){}

    public int getIntId() {
        return intId;
    }

    public void setIntId(int intId) {
        this.intId = intId;
    }

    public String getTxtName() {
        return txtName;
    }

    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

    public String getTxtValue() {
        return txtValue;
    }

    public void setTxtValue(String txtValue) {
        this.txtValue = txtValue;
    }

    public String getTxtDeskripsi() {
        return txtDeskripsi;
    }

    public void setTxtDeskripsi(String txtDeskripsi) {
        this.txtDeskripsi = txtDeskripsi;
    }
}
