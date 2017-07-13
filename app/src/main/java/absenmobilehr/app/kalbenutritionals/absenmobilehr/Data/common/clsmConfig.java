package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Robert on 22/06/2017.
 */
@DatabaseTable
public class clsmConfig {
    @DatabaseField(id = true, columnName = "intId")
    private Integer intId;
    @DatabaseField(columnName = "txtName")
    private String txtName;
    @DatabaseField(columnName = "txtValue")
    private String txtValue;
    @DatabaseField(columnName = "txtDefaultValue")
    private String txtDefaultValue;
    @DatabaseField(columnName = "intEditAdmin")
    private Integer intEditAdmin;

    public clsmConfig(){

    }

    public Integer getIntId() {
        return intId;
    }

    public void setIntId(Integer intId) {
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

    public String getTxtDefaultValue() {
        return txtDefaultValue;
    }

    public void setTxtDefaultValue(String txtDefaultValue) {
        this.txtDefaultValue = txtDefaultValue;
    }

    public Integer getIntEditAdmin() {
        return intEditAdmin;
    }

    public void setIntEditAdmin(Integer intEditAdmin) {
        this.intEditAdmin = intEditAdmin;
    }
}
