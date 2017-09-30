package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
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
    public int getMaxSequence() throws SQLException{
        QueryBuilder<clsTrackingData, Integer> builder = helper.getTrackingDataDao().queryBuilder();
        builder.orderBy("intSequence", false);  // true or false for ascending so change to true to get min id
//        builder.selectRaw("MAX(intSequence)");
        builder.limit(1L);
        clsTrackingData data = helper.getTrackingDataDao().queryForFirst(builder.prepare());

        QueryBuilder<clsTrackingData, Integer> qb = helper.getTrackingDataDao().queryBuilder();
        qb.selectRaw("MAX(intSequence)");

// the results will contain 2 string values for the min and max

        GenericRawResults<String[]> results = helper.getTrackingDataDao().queryRaw(qb.prepareStatementString());

        String[] values = results.getFirstResult();


        int id = data.getIntSequence();
        return id;
    }
    public clsTrackingData getDataByMaxSequence() throws SQLException{
        QueryBuilder<clsTrackingData, Integer> builder = helper.getTrackingDataDao().queryBuilder();
        builder.orderBy("intSequence", false);  // true or false for ascending so change to true to get min id
        clsTrackingData data = helper.getTrackingDataDao().queryForFirst(builder.prepare());
//        String id = data.getIntSequence();
        return data;
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
        int index = -1;
        clsTrackingData object = (clsTrackingData) item;
        try {
            Dao.CreateOrUpdateStatus status = helper.getTrackingDataDao().createOrUpdate(object);
            index = status.getNumLinesChanged();
//            index = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return index;
    }
    public List<clsTrackingData> getAllDataToPushData(Context context){
        QueryBuilder<clsTrackingData, Integer> queryBuilder = null;
        List<clsTrackingData> data = null;
        try {
            data = (List<clsTrackingData>) helper.getTrackingDataDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clsTrackingData dt = new clsTrackingData();
        List<clsTrackingData> listData = new ArrayList<>();
        if (data.size()>0){
            try {
                queryBuilder = helper.getTrackingDataDao().queryBuilder();
                queryBuilder.where().eq(dt.Property_intSubmit, "1").and().eq(dt.Property_intSync, "0");
                listData = queryBuilder.query();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listData;
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
        List<clsTrackingData> items = null;
        try {
            items = helper.getTrackingDataDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    public int updateAllRowTracking(){
        int index = -1;
        clsTrackingData object = new clsTrackingData();
        /*object.setIntSync("1");
        object.setIntSubmit("1");
        try {
            index = helper.getTrackingDataDao().update(object);
        }catch (SQLException e){
            e.printStackTrace();
        }*/
        UpdateBuilder<clsTrackingData, Integer> updateBuilder = null;
        try {
            updateBuilder = helper.getTrackingDataDao().updateBuilder();
            updateBuilder.updateColumnValue(object.Property_intSync,"1");
            updateBuilder.updateColumnValue(object.Property_intSubmit, "1");
            index = updateBuilder.update();
        } catch (SQLException e) {
            e.printStackTrace();
        }
// update the goal_title and goal_why fields

        return index;
    }
}
