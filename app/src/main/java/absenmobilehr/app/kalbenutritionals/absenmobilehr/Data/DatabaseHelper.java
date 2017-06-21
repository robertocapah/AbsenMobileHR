package absenmobilehr.app.kalbenutritionals.absenmobilehr.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsDeviceInfo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsDisplayPicture;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserJabatan;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLob;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserPegawai;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.mVersionApp;


/**
 * Created by Robert on 20/06/2017.
 */
public class DatabaseHelper  extends OrmLiteSqliteOpenHelper {
    private static clsHardCode _path = new clsHardCode();
    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = _path.dbName;
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 2;

    // the DAO object we use to access the SimpleData table
    protected  Dao<clsUserLob, Integer> userLobDao;
    protected  Dao<clsUserJabatan, Integer> userJabatanDao;
    protected  Dao<clsUserPegawai, Integer> userPegawaiDao;

    protected Dao<clsUserLogin, Integer> userLoginDao;
    protected RuntimeExceptionDao<clsUserLogin, Integer> userLoginRuntimeDao = null;

    protected Dao<clsDisplayPicture, Integer> displayPictureDao;
    protected RuntimeExceptionDao<clsDisplayPicture, Integer> displayPictureRuntimeDao = null;

    protected Dao<mVersionApp, Integer> mVersionAppsDao;
    protected RuntimeExceptionDao<mVersionApp, Integer> mVersionAppsRuntimeDao = null;

    protected Dao<clsDeviceInfo, Integer> deviceInfoDao;
    protected RuntimeExceptionDao<clsDeviceInfo, Integer> deviceInfoRuntimeDao = null;

//    private Dao<Comment, Integer> commentsDao = null;
//    private RuntimeExceptionDao<Comment, Integer> commentsRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        // Create tables. This onCreate() method will be invoked only once of the application life time i.e. the first time when the application starts.

        try {
            TableUtils.createTableIfNotExists(connectionSource, clsUserLogin.class);
            TableUtils.createTableIfNotExists(connectionSource, clsDisplayPicture.class);
            TableUtils.createTableIfNotExists(connectionSource, clsDeviceInfo.class);
            TableUtils.createTableIfNotExists(connectionSource, mVersionApp.class);
            TableUtils.createTableIfNotExists(connectionSource, clsUserJabatan.class);
            TableUtils.createTableIfNotExists(connectionSource, clsUserLob.class);
            TableUtils.createTableIfNotExists(connectionSource, clsUserPegawai.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, clsUserLogin.class, true);
            TableUtils.dropTable(connectionSource, clsDeviceInfo.class, true);
            TableUtils.dropTable(connectionSource, clsDisplayPicture.class, true);
            TableUtils.dropTable(connectionSource, mVersionApp.class, true);
            TableUtils.dropTable(connectionSource, clsUserLogin.class, true);
            TableUtils.dropTable(connectionSource, clsUserJabatan.class, true);
            TableUtils.dropTable(connectionSource, clsUserPegawai.class, true);
            TableUtils.dropTable(connectionSource, clsUserLob.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<clsUserPegawai, Integer> getUserPegawaiDao() throws SQLException {
        if (userPegawaiDao == null) {
            userPegawaiDao = getDao(clsUserPegawai.class);
        }
        return userPegawaiDao;
    }
    public Dao<clsUserLob, Integer> getUserLobDao() throws SQLException {
        if (userLobDao == null) {
            userLobDao = getDao(clsUserLob.class);
        }
        return userLobDao;
    }
    public Dao<clsUserJabatan, Integer> getUserJabatanDao() throws SQLException {
        if (userJabatanDao == null) {
            userJabatanDao = getDao(clsUserJabatan.class);
        }
        return userJabatanDao;
    }
    public Dao<clsUserLogin, Integer> getUserLoginDao() throws SQLException {
        if (userLoginDao == null) {
            userLoginDao = getDao(clsUserLogin.class);
        }
        return userLoginDao;
    }
    public Dao<clsDeviceInfo, Integer> getDeviceInfoDao() throws SQLException {
        if (deviceInfoDao == null) {
            deviceInfoDao = getDao(clsDeviceInfo.class);
        }
        return deviceInfoDao;
    }
    public Dao<clsDisplayPicture, Integer> getDisplayPictureDao() throws SQLException {
        if (displayPictureDao == null) {
            displayPictureDao = getDao(clsDisplayPicture.class);
        }
        return displayPictureDao;
    }
    public Dao<mVersionApp, Integer> getmVersionAppsDao() throws SQLException {
        if (mVersionAppsDao == null) {
            mVersionAppsDao = getDao(mVersionApp.class);
        }
        return mVersionAppsDao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<clsUserLogin, Integer> getUserLoginRuntimeDao() {
        if (userLoginRuntimeDao == null) {
            userLoginRuntimeDao = getRuntimeExceptionDao(clsUserLogin.class);
        }
        return userLoginRuntimeDao;
    }
    public RuntimeExceptionDao<clsDisplayPicture, Integer> getDisplayPictureRuntimeDao() {
        if (displayPictureRuntimeDao == null) {
            displayPictureRuntimeDao = getRuntimeExceptionDao(clsDisplayPicture.class);
        }
        return displayPictureRuntimeDao;
    }
    public RuntimeExceptionDao<clsDeviceInfo, Integer> getDeviceInfoRuntimeDao() {
        if (deviceInfoRuntimeDao == null) {
            deviceInfoRuntimeDao = getRuntimeExceptionDao(clsDeviceInfo.class);
        }
        return deviceInfoRuntimeDao;
    }
    public RuntimeExceptionDao<mVersionApp, Integer> getmVersionAppsRuntimeDao() {
        if (mVersionAppsRuntimeDao == null) {
            mVersionAppsRuntimeDao = getRuntimeExceptionDao(mVersionApp.class);
        }
        return mVersionAppsRuntimeDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        userLoginDao = null;
        mVersionAppsDao = null;
        deviceInfoDao = null;
        displayPictureDao = null;
    }

}
