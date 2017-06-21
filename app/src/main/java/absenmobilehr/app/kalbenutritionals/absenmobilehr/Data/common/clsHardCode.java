package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common;

import android.os.Environment;

import java.io.File;

/**
 * Created by Robert on 16/06/2017.
 */

public class clsHardCode {
    public String txtPathApp= Environment.getExternalStorageDirectory()+ File.separator+"Android"+File.separator+"data"+File.separator+"KalbeAbsenHR"+File.separator+"app_database"+File.separator;
    public String db="KalbeAbsenHR";
    public String txtPathUserData= Environment.getExternalStorageDirectory()+File.separator+"Android"+File.separator+"data"+File.separator+"KalbeAbsenHR"+File.separator+"user_data"+File.separator;

}
