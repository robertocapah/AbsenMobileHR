package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseManager;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLastCheckingData;

/**
 * Created by Robert on 29/09/2017.
 */

public class clsLastCheckingDataRepo implements crud{
    DatabaseHelper helper;
    public clsLastCheckingDataRepo(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }
    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        clsLastCheckingData object = (clsLastCheckingData) item;
        try {
            index = helper.getLastCheckingDataDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        clsLastCheckingData object = (clsLastCheckingData) item;
        try {
            Dao.CreateOrUpdateStatus status = helper.getLastCheckingDataDao().createOrUpdate(object);
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
        clsLastCheckingData object = (clsLastCheckingData) item;
        try {
            index = helper.getLastCheckingDataDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int delete(Object item) throws SQLException {
        int index = -1;
        clsLastCheckingData object = (clsLastCheckingData) item;
        try {
            index = helper.getLastCheckingDataDao().delete(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        clsLastCheckingData item = null;
        try {
            item = helper.getLastCheckingDataDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
    public clsLastCheckingData findByGUIId(String guiId) throws SQLException {
        List<clsLastCheckingData> item = null;
        clsLastCheckingData data = new clsLastCheckingData();
        QueryBuilder<clsLastCheckingData, Integer> queryBuilder = null;
        try {
//            item = helper.getLastCheckingDataDao().queryForId(id);
            queryBuilder = helper.getLastCheckingDataDao().queryBuilder();
            queryBuilder.where().eq(data.Property_txtGuiID, guiId);
            item = queryBuilder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item.get(0);
    }

    @Override
    public List<clsLastCheckingData> findAll() throws SQLException {
        List<clsLastCheckingData> items = null;
        try {
            items = helper.getLastCheckingDataDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
