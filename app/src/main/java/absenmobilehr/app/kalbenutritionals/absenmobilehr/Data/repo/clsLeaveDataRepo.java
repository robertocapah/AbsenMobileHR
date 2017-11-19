package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseManager;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLeaveData;

/**
 * Created by Robert on 13/10/2017.
 */

public class clsLeaveDataRepo implements crud {
    private DatabaseHelper helper;

    public clsLeaveDataRepo(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        clsLeaveData object = (clsLeaveData) item;
        try {
            index = helper.getLeaveDataDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        clsLeaveData object = (clsLeaveData) item;
        try {
            Dao.CreateOrUpdateStatus status = helper.getLeaveDataDao().createOrUpdate(object);
            index = status.getNumLinesChanged();
//            index = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int update(Object item) throws SQLException {
        int index = -1;
        clsLeaveData object = (clsLeaveData) item;
        try {
            index = helper.getLeaveDataDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }
    public Object findByNIK(String txtNIK) throws SQLException {
        clsLeaveData item = null;
        try{
            QueryBuilder<clsLeaveData,Integer> queryBuilder = null;
            queryBuilder = helper.getLeaveDataDao().queryBuilder();
            queryBuilder.where().eq(item.Property_txtNIK,txtNIK);
            item = queryBuilder.queryForFirst();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public int delete(Object item) throws SQLException {
        return 0;
    }

    @Override
    public Object findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<clsLeaveData> findAll() throws SQLException {
        List<clsLeaveData> items = null;
        try{
            items = helper.getLeaveDataDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }
}
