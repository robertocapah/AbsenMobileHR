package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo;

import android.content.Context;

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

    public Object findById(int id) throws SQLException {
        clsmConfig item = null;
        try {
            item = helper.getmConfigDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

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
        data2.setTxtName("API_LOGIN");
        data2.setTxtValue("http://prm.kalbenutritionals.web.id/VisitPlan/API/VisitPlanAPI/LogIn_J");
        data2.setTxtDefaultValue("http://prm.kalbenutritionals.web.id/VisitPlan/API/VisitPlanAPI/LogIn_J");
        data2.setIntEditAdmin(1);
        helper.getmConfigDao().createOrUpdate(data2);
        clsmConfig data3 = new clsmConfig();
        data3.setIntId(3);
        data3.setTxtName("API_VERSION");
        data3.setTxtValue("http://10.171.11.87:8010/VisitPlan/API/VisitPlanAPI/CheckVersionApp_J");
        data3.setTxtDefaultValue("http://prm.kalbenutritionals.web.id/VisitPlan/API/VisitPlanAPI/CheckVersionApp_J");
        data3.setIntEditAdmin(1);
        helper.getmConfigDao().createOrUpdate(data3);
        clsmConfig data4 = new clsmConfig();
        data4.setIntId(4);
        data4.setTxtName("Domain Kalbe");
        data4.setTxtValue("ONEKALBE.LOCAL");
        data4.setTxtDefaultValue("ONEKALBE.LOCAL");
        data4.setIntEditAdmin(1);
        helper.getmConfigDao().createOrUpdate(data4);
        clsmConfig data5 = new clsmConfig();
        data5.setIntId(5);
        data5.setTxtName("Application Name");
        data5.setTxtValue("Android - Call Plan BR Mobile");
        data5.setTxtDefaultValue("Android - Call Plan BR Mobile");
        data5.setIntEditAdmin(1);
        helper.getmConfigDao().createOrUpdate(data5);
        clsmConfig data6 = new clsmConfig();
        data6.setIntId(6);
        data6.setTxtName("Text Footer");
        data6.setTxtValue("Copyright &copy; KN IT 2017");
        data6.setTxtDefaultValue("Copyright &copy; KN IT 2017");
        data6.setIntEditAdmin(1);
        helper.getmConfigDao().createOrUpdate(data6);

    }

    public List<clsmConfig> findByName(String name) throws SQLException {
        List<clsmConfig> items = null;
        try {
            items = helper.getmConfigDao().queryForEq("txtName", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
