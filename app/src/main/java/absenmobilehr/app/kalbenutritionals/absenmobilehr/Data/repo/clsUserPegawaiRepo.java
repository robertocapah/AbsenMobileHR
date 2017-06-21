package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseManager;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserPegawai;

/**
 * Created by Robert on 21/06/2017.
 */

public class clsUserPegawaiRepo implements crud {
    DatabaseHelper helper;
    public clsUserPegawaiRepo(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }
    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        clsUserPegawai object = (clsUserPegawai) item;
        try {
            index = helper.getUserPegawaiDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        clsUserPegawai object = (clsUserPegawai) item;
        try {
            Dao.CreateOrUpdateStatus status = helper.getUserPegawaiDao().createOrUpdate(object);
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
        clsUserPegawai object = (clsUserPegawai) item;
        try {
            index = helper.getUserPegawaiDao().update(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int delete(Object item) throws SQLException {
        int index = -1;
        clsUserPegawai object = (clsUserPegawai) item;
        try {
            index = helper.getUserPegawaiDao().delete(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        clsUserPegawai item = null;
        try{
            item = helper.getUserPegawaiDao().queryForId(id);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<clsUserPegawai> items = null;
        try{
            items = helper.getUserPegawaiDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }
}
