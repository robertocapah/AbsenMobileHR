package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data;

import android.os.Environment;

import java.io.File;

/**
 * Created by Robert on 20/06/2017.
 */

public class clsHardCode {
    public String dbName = "KalbeAbsenHR.db";
    public String txtPathApp= Environment.getExternalStorageDirectory()+ File.separator+"Android"+File.separator+"data"+File.separator+"KalbeAbsenHR"+File.separator+"app_database"+File.separator;
    public String txtPathUserData= Environment.getExternalStorageDirectory()+File.separator+"Android"+File.separator+"data"+File.separator+"KalbeAbsenHR"+File.separator+"user_data"+File.separator;
    public String txtFolderAbsen = Environment.getExternalStorageDirectory()+File.separator+"Android"+File.separator+"data"+File.separator+"KalbeAbsenHR"+File.separator+"image_Absen"+File.separator;
    public String txtMessCancelRequest = "Canceled Request Data";
    public String intSuccess = "1";
}
