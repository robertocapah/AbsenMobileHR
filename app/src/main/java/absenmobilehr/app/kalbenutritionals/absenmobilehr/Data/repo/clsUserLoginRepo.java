package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.repo;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        int index = -1;
        clsUserLogin object = (clsUserLogin) item;
        try {
            Dao.CreateOrUpdateStatus status = helper.getUserLoginDao().createOrUpdate(object);
            index = status.getNumLinesChanged();
//            index = 1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return index;
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
    public List<?> findAll() throws SQLException{
        List<clsUserLogin> items = null;
        try{
            items = helper.getUserLoginDao().queryForAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return items;
    }
    public boolean CheckLoginNow() throws ParseException {
        // Select All Query
        clsUserLogin dt=new clsUserLogin();
        List<clsUserLogin> items = null;
        boolean result=false;
        try{
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            String date = dateFormat.format(cal.getTime());
            items = helper.getUserLoginDao().queryForEq(dt.Property_dtLastLogin,date);
            if (items.size()>0){
                result = true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return result;
    }
    public clsUserLogin getDataLogin(Context context){
        clsUserLoginRepo repo = new clsUserLoginRepo(context);
        clsUserLogin dataLogin =new clsUserLogin();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        String now = dateFormat.format(cal.getTime()).toString();
//        if(repo.CheckLoginNow()){
        List<clsUserLogin> listData= null;
        try {
            listData = helper.getUserLoginDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (clsUserLogin data : listData){
//            if (data.getDtLastLogin().equals(now)){
//                dataLogin = data;
//            }
            if (data.getTxtGUI()!=null && !data.getTxtGUI().equals("null")){
                dataLogin = data;
            }
        }

//        }
        return dataLogin;
    }
    public int getContactCount(Context context){
        clsUserLoginRepo repo = new clsUserLoginRepo(context);
        int count = 0;
        try {
            List<clsUserLogin> data = (List<clsUserLogin>)repo.findAll();
            count = data.size();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public List<clsUserLogin> getAllDataToPushData(Context context){
        QueryBuilder<clsUserLogin, Integer> queryBuilder = null;
        List<clsUserLogin> data = null;
        try {
            data = (List<clsUserLogin>) helper.getUserLoginDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    public int getContactsCount(Context context) throws SQLException {
        List<clsUserLogin> items = null;
        int num = 0;
        try {
            items = helper.getUserLoginDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        num = items.size();
        // return count
        return num;
    }

}
