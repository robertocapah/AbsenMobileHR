package absenmobilehr.app.kalbenutritionals.absenmobilehr;

import android.content.Context;

import java.io.File;

/**
 * Created by Robert on 27/11/2017.
 */

public class DeviceUtils {

    public Boolean isDeviceRooted(Context context){
//        boolean isRooted = isrooted1() || isrooted2();
//        return isRooted;
        return  false;
    }

    private boolean isrooted1() {

        File file = new File("/system/app/Superuser.apk");
        if (file.exists()) {
            return true;
        }
        return false;
    }

//    // try executing commands
//    private boolean isrooted2() {
//        return canExecuteCommand("/system/xbin/which su")
//                || canExecuteCommand("/system/bin/which su")
//                || canExecuteCommand("which su");
//    }
}
