package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Robert on 16/06/2017.
 */
@DatabaseTable
public class mVersionApp implements Serializable {
    @DatabaseField(id = true,columnName = "idVersion")
    public Integer idVersion;
    @DatabaseField(columnName = "txtGUI")
    public String txtGUI;
    @DatabaseField(columnName = "txtNameApp")
    public String txtNameApp;
    @DatabaseField(columnName = "txtVersion")
    public String txtVersion;
    @DatabaseField(columnName = "txtFile")
    public String txtFile;

    public void mVersionApp(){

    }

    public Integer getIdVersion() {
        return idVersion;
    }

    public void setIdVersion(Integer idVersion) {
        this.idVersion = idVersion;
    }

    public String getTxtGUI() {
        return txtGUI;
    }

    public void setTxtGUI(String txtGUI) {
        this.txtGUI = txtGUI;
    }

    public String getTxtNameApp() {
        return txtNameApp;
    }

    public void setTxtNameApp(String txtNameApp) {
        this.txtNameApp = txtNameApp;
    }

    public String getTxtVersion() {
        return txtVersion;
    }

    public void setTxtVersion(String txtVersion) {
        this.txtVersion = txtVersion;
    }

    public String getTxtFile() {
        return txtFile;
    }

    public void setTxtFile(String txtFile) {
        this.txtFile = txtFile;
    }
}
