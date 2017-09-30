package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseManager;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenOnline;

/**
 * Created by Robert on 22/09/2017.
 */

public class clsAbsenOnlineRepo implements crud {
    DatabaseHelper helper;

    public clsAbsenOnlineRepo(Context context) {
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }

    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        clsAbsenOnline object = (clsAbsenOnline) item;
        try {
            index = helper.getUserAbsenOnlineDao().create(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        int index = -1;
        clsAbsenOnline object = (clsAbsenOnline) item;
        try {
            Dao.CreateOrUpdateStatus status = helper.getUserAbsenOnlineDao().createOrUpdate(object);
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
        clsAbsenOnline object = (clsAbsenOnline) item;
        try {
            index = helper.getUserAbsenOnlineDao().update(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int delete(Object item) throws SQLException {
        int index = -1;
        clsAbsenOnline object = (clsAbsenOnline) item;
        try {
            index = helper.getUserAbsenOnlineDao().delete(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) throws SQLException {
        clsAbsenOnline item = null;
        try {
            item = helper.getUserAbsenOnlineDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public List<?> findAll() throws SQLException {
        List<clsAbsenOnline> items = null;
        try {
            items = helper.getUserAbsenOnlineDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public clsAbsenOnline getDataCheckinActive(Context context) {
        clsAbsenOnline dataAbsen = null;
        List<clsAbsenOnline> listData = null;

//            QueryBuilder<clsAbsenData, Integer> queryBuilder = helper.getUserAbsenDao().queryBuilder();
//            queryBuilder.where().eq(dataAbsen.Property_dtCheckout, null).or().eq(dataAbsen.Property_dtCheckout, "");
//            listData = queryBuilder.query();
        try {
            listData = helper.getUserAbsenOnlineDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (listData != null && listData.size() > 0) {
            for (clsAbsenOnline data : listData) {
                if (data.getIntSubmit().equals("1")&& (data.getDtCheckout() == null || data.getDtCheckout() == "")) {
                    dataAbsen = data;
                }
            }
        }


//        }
        return dataAbsen;
    }

    public int getContactsCount(Context context) throws SQLException {
        List<clsAbsenOnline> items = null;
        int num = 0;
        try {
            items = helper.getUserAbsenOnlineDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        num = items.size();
        // return count
        return num;
    }
    public List<clsAbsenOnline> getAllDataToPushData(Context context){
        QueryBuilder<clsAbsenOnline, Integer> queryBuilder = null;
        List<clsAbsenOnline> data = null;
        try {
            data = (List<clsAbsenOnline>) helper.getUserAbsenOnlineDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clsAbsenOnline dt = new clsAbsenOnline();
        List<clsAbsenOnline> listData = new ArrayList<>();

        if (data.size()>0){
            try {
                queryBuilder = helper.getUserAbsenOnlineDao().queryBuilder();
                queryBuilder.where().eq(dt.Property_intSubmit, "1").and().eq(dt.Property_Sync, "0").and().isNotNull(dt.Property_dtCheckin);
                listData = queryBuilder.query();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return listData;
    }
    public int updateAllRowAbsen(){
        int index = -1;
        clsAbsenOnline object = new clsAbsenOnline();
        /*object.setIntSync("1");
        object.setIntSubmit("1");
        try {
            index = helper.getTrackingDataDao().update(object);
        }catch (SQLException e){
            e.printStackTrace();
        }*/
        UpdateBuilder<clsAbsenOnline, Integer> updateBuilder = null;
        try {
            updateBuilder = helper.getUserAbsenOnlineDao().updateBuilder();
            updateBuilder.updateColumnValue(object.Property_Sync,"1");
            updateBuilder.updateColumnValue(object.Property_intSubmit, "1");
            index = updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
// update the goal_title and goal_why fields

        return index;
    }
}
