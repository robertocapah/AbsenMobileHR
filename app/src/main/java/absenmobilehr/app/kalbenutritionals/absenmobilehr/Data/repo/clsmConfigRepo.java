package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseManager;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmConfig;

/**
 * Created by Robert on 22/06/2017.
 */

public class clsmConfigRepo {
    DatabaseHelper helper;

    public clsmConfigRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        clsmConfig object = (clsmConfig) item;
        try {
            Dao.CreateOrUpdateStatus status = helper.getmConfigDao().createOrUpdate(object);
            index = status.getNumLinesChanged();
//            index = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }


    public clsmConfig findById(int id) throws SQLException {
        clsmConfig item = null;
        try {
            item = helper.getmConfigDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
//String a;
    public void InsertDefaultMconfig() throws SQLException {

        clsmConfig data1 = new clsmConfig();
        data1.setIntId(1);
        data1.setTxtName("android:versionCode");
        data1.setTxtValue("5");
        data1.setTxtDefaultValue("5");
        data1.setIntEditAdmin(1);
        helper.getmConfigDao().createOrUpdate(data1);
        clsmConfig data2 = new clsmConfig();
        data2.setIntId(2);
        data2.setTxtName("API_PRM");
//        data2.setTxtValue("http://prm.kalbenutritionals.web.id/VisitPlan/API/VisitPlanAPI/");
//        data2.setTxtDefaultValue("http://prm.kalbenutritionals.web.id/VisitPlan/API/VisitPlanAPI/");
        data2.setTxtValue("https://prm.kalbenutritionals.com/VisitPlan/API/VisitPlanAPI/");
        data2.setTxtDefaultValue("https://prm.kalbenutritionals.com/VisitPlan/API/VisitPlanAPI/");
//        data2.setTxtValue("http://10.171.10.27/KN2015_PRM_V2.WEB/VisitPlan/API/VisitPlanAPI/");
//        data2.setTxtDefaultValue("http://10.171.10.27/KN2015_PRM_V2.WEB/VisitPlan/API/VisitPlanAPI/");
        data2.setIntEditAdmin(1);
        helper.getmConfigDao().createOrUpdate(data2);
        clsmConfig data3 = new clsmConfig();
        data3.setIntId(3);
        data3.setTxtName("Domain Kalbe");
        data3.setTxtValue("ONEKALBE.LOCAL");
        data3.setTxtDefaultValue("ONEKALBE.LOCAL");
        data3.setIntEditAdmin(1);
        helper.getmConfigDao().createOrUpdate(data3);
        clsmConfig data4 = new clsmConfig();
        data4.setIntId(4);
        data4.setTxtName("Application Name");
        data4.setTxtValue("Android - Call Plan BR Mobile");
        data4.setTxtDefaultValue("Android - Call Plan BR Mobile");
        data4.setIntEditAdmin(1);
        helper.getmConfigDao().createOrUpdate(data4);
        clsmConfig data5 = new clsmConfig();
        data5.setIntId(5);
        data5.setTxtName("Text Footer");
        data5.setTxtValue("Copyright &copy; KN IT 2017");
        data5.setTxtDefaultValue("Copyright &copy; KN IT 2017");
        data5.setIntEditAdmin(1);
        helper.getmConfigDao().createOrUpdate(data5);
        clsmConfig data6 = new clsmConfig();
        data6.setIntId(6);
        data6.setTxtName("Background Service Online");
        data6.setTxtValue("1000");
        data6.setTxtDefaultValue("1000");
        data6.setIntEditAdmin(1);
        helper.getmConfigDao().createOrUpdate(data6);
        clsmConfig data7 = new clsmConfig();
        data7.setIntId(7);
        data7.setTxtName("API_EF");
//        data7.setTxtValue("http://10.171.11.87/APIEF2/api/");
//        data7.setTxtDefaultValue("http://10.171.11.87/APIEF2/api/");
        data7.setTxtValue("https://apiabsensi.kalbenutritionals.com/api/");
        data7.setTxtDefaultValue("https://apiabsensi.kalbenutritionals.com/api/");
        data7.setIntEditAdmin(1);
        helper.getmConfigDao().createOrUpdate(data7);
        clsmConfig data9 = new clsmConfig();
        data9.setIntId(9);
        data9.setTxtName("API_WEBAbsen");
        data9.setTxtValue("https://absensi.kalbenutritionals.com/user_data/files/apk/");
        data9.setTxtDefaultValue("https://absensi.kalbenutritionals.com/user_data/files/apk/");
        data9.setIntEditAdmin(1);
        helper.getmConfigDao().createOrUpdate(data9);
        clsmConfig data8 = new clsmConfig();
        data8.setIntId(8);
        data8.setTxtName("WidthScreen");
        data8.setTxtValue("");
        data8.setTxtDefaultValue("");
        data8.setIntEditAdmin(1);
        helper.getmConfigDao().createOrUpdate(data8);

    }
    /*public String getLIVE(Context context){
        String txtLinkAPI="";
        clsmConfigRepo

        return txtLinkAPI;
    }*/

    public String getLinkById(String id) throws SQLException {
        List<clsmConfig> items = null;
        String link = "";
        try {
            items = helper.getmConfigDao().queryForEq("intId", id);
            link = items.get(0).getTxtValue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return link;
    }
}
