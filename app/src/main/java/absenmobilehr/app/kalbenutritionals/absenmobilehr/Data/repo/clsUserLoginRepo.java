package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseManager;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;

/**
 * Created by Robert on 19/06/2017.
 */

public class clsUserLoginRepo implements crud {

    private DatabaseHelper helper;
    public clsUserLoginRepo(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }
    @Override
    public int create(Object item) {
        int index = -1;
        clsUserLogin object = (clsUserLogin) item;
        try {
            index = helper.getUserLoginDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) {
        return 0;
    }

    @Override
    public int update(Object item) {
        int index = -1;
        clsUserLogin object = (clsUserLogin) item;
        try {
            index = helper.getUserLoginDao().update(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int delete(Object item) {
        int index = -1;
        clsUserLogin object = (clsUserLogin) item;
        try {
            index = helper.getUserLoginDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) {
        clsUserLogin item = null;
        try{
            item = helper.getUserLoginDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() {

        List<clsUserLogin> items = null;
        try{
            items = helper.getUserLoginDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }
}
