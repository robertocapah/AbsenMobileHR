package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.bl;

import android.content.Context;

import java.util.UUID;

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


}
