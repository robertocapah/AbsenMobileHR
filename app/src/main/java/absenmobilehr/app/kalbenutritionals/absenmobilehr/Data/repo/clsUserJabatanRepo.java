package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseManager;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserJabatan;

/**
 * Created by Robert on 21/06/2017.
 */

public class clsUserJabatanRepo implements crud {
    DatabaseHelper helper;

    public clsUserJabatanRepo(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }
    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        clsUserJabatan object = (clsUserJabatan) item;
        try {
            index = helper.getUserJabatanDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        clsUserJabatan object = (clsUserJabatan) item;
        try {
            Dao.CreateOrUpdateStatus status = helper.getUserJabatanDao().createOrUpdate(object);
            index = status.getNumLinesChanged();
//            index = 1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int update(Object item) throws SQLException {
        int index = -1;
        clsUserJabatan object = (clsUserJabatan) item;
        try {
            index = helper.getUserJabatanDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int delete(Object item) throws SQLException {
        int index = -1;
        clsUserJabatan object = (clsUserJabatan) item;
        try {
            index = helper.getUserJabatanDao().update(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        clsUserJabatan item = null;
        try{
            item = helper.getUserJabatanDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<clsUserJabatan> items = null;
        try{
            items = helper.getUserJabatanDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }
}
