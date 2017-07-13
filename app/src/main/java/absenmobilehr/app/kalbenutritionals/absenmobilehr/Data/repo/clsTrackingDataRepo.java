package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo;

import android.content.Context;

import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseHelper;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.DatabaseManager;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsTrackingData;

/**
 * Created by Robert on 06/07/2017.
 */

public class clsTrackingDataRepo implements crud {
    private DatabaseHelper helper;
    public clsTrackingDataRepo(Context context){
        DatabaseManager.init(context);
        helper = DatabaseManager.getInstance().getHelper();
    }
    @Override
    public int create(Object item) throws SQLException {
        int index = -1;
        clsTrackingData object = (clsTrackingData) item;
        try {
            index = helper.getTrackingDataDao().create(object);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
    }
    public int getContactCount() throws SQLException{
        int index = 0;
        index = helper.getTrackingDataDao().queryForAll().size();
        return index;
    }
    public List<clsTrackingData> getLastDataByTime() throws SQLException{
        clsTrackingData data = new clsTrackingData();
        QueryBuilder<clsTrackingData,Integer> query = helper.getTrackingDataDao().queryBuilder();
//        Where<clsTrackingData,Integer> wh= query.where();
//        wh.eq(data.Property_txtTime,)
        query.orderBy(data.Property_txtTime,false);
        List<clsTrackingData> datas =  query.query();
        return datas;
    }

    @Override
    public int createOrUpdate(Object item) throws SQLException {
        return 0;
    }

    @Override
    public int update(Object item) throws SQLException {
        return 0;
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
    public List<?> findAll() throws SQLException {
        return null;
    }
}
