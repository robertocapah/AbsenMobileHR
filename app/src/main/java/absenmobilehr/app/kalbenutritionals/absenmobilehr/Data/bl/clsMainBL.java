package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.bl;

import android.content.Context;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsStatusMenuStart;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.clsUserLoginRepo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo.enumStatusMenuStart;

/**
 * Created by Robert on 03/07/2017.
 */

public class clsMainBL {
    public String GenerateGuid(){
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
    }
    public String GenerateGuid(Context context){
        DeviceUuidFactory _DeviceUuidFactory=new DeviceUuidFactory(context);
        return _DeviceUuidFactory.getDeviceUuid().toString();
    }
    public clsStatusMenuStart checkUserActive(Context context) throws ParseException {
        clsUserLoginRepo repo = new clsUserLoginRepo(context);
        clsStatusMenuStart _clsStatusMenuStart =new clsStatusMenuStart();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String now = dateFormat.format(cal.getTime()).toString();
//        if(repo.CheckLoginNow()){
        List<clsUserLogin> listData= null;
        try {
            listData = (List<clsUserLogin>) repo.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (clsUserLogin data : listData){
                if (data.getDtLastLogin().equals(now)){
                    _clsStatusMenuStart.set_intStatus(enumStatusMenuStart.UserActiveLogin);
                }
            }

//        }
        return _clsStatusMenuStart;
    }

}
