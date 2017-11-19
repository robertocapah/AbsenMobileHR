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

import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsAbsenOnline;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsBranchAccess;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsDeviceInfo;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsDisplayPicture;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLastCheckingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsLeaveData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsReportData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsTrackingData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsTypeLeave;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserJabatan;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLob;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserLogin;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsUserPegawai;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmConfig;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmCounterData;
import absenmobilehr.app.kalbenutritionals.absenmobilehr.Data.common.clsmVersionApp;


/**
 * Created by Robert on 20/06/2017.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static clsHardCode _path = new clsHardCode();
    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = _path.dbName;
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 8;

    // the DAO object we use to access the SimpleData table
    protected Dao<clsmConfig, Integer> mConfigDao;
    protected Dao<clsUserLob, Integer> userLobDao;
    protected Dao<clsUserJabatan, Integer> userJabatanDao;
    protected Dao<clsUserPegawai, Integer> userPegawaiDao;
    protected Dao<clsAbsenData, Integer> userAbsenDao;
    protected Dao<clsAbsenOnline, Integer> userAbsenOnlineDao;
    protected Dao<clsTrackingData, Integer> trackingDataDao;
    protected Dao<clsmCounterData, Integer> counterDataDao;
    protected Dao<clsLastCheckingData, Integer> lastCheckingDataDao;
    protected Dao<clsUserLogin, Integer> userLoginDao;
    protected Dao<clsReportData, Integer> reportDataDao;
    protected Dao<clsLeaveData, Integer> leaveDataDao;
    protected Dao<clsTypeLeave, Integer> typeLeaveDao;
    protected Dao<clsBranchAccess,Integer> branchAccessDao;

    protected RuntimeExceptionDao<clsUserLogin, Integer> userLoginRuntimeDao = null;

    protected Dao<clsDisplayPicture, Integer> displayPictureDao;
    protected RuntimeExceptionDao<clsDisplayPicture, Integer> displayPictureRuntimeDao = null;

    protected Dao<clsmVersionApp, Integer> mVersionAppsDao;
    protected RuntimeExceptionDao<clsmVersionApp, Integer> mVersionAppsRuntimeDao = null;

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
            TableUtils.createTableIfNotExists(connectionSource, clsmVersionApp.class);
            TableUtils.createTableIfNotExists(connectionSource, clsUserJabatan.class);
            TableUtils.createTableIfNotExists(connectionSource, clsUserLob.class);
            TableUtils.createTableIfNotExists(connectionSource, clsUserPegawai.class);
            TableUtils.createTableIfNotExists(connectionSource, clsmConfig.class);
            TableUtils.createTableIfNotExists(connectionSource, clsAbsenData.class);
            TableUtils.createTableIfNotExists(connectionSource, clsTrackingData.class);
            TableUtils.createTableIfNotExists(connectionSource, clsmCounterData.class);
            TableUtils.createTableIfNotExists(connectionSource, clsAbsenOnline.class);
            TableUtils.createTableIfNotExists(connectionSource, clsLastCheckingData.class);
            TableUtils.createTableIfNotExists(connectionSource, clsReportData.class);
            TableUtils.createTableIfNotExists(connectionSource, clsLeaveData.class);
            TableUtils.createTableIfNotExists(connectionSource, clsTypeLeave.class);
            TableUtils.createTableIfNotExists(connectionSource, clsBranchAccess.class);
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
            TableUtils.dropTable(connectionSource, clsmVersionApp.class, true);
            TableUtils.dropTable(connectionSource, clsUserLogin.class, true);
            TableUtils.dropTable(connectionSource, clsUserJabatan.class, true);
            TableUtils.dropTable(connectionSource, clsUserPegawai.class, true);
            TableUtils.dropTable(connectionSource, clsUserLob.class, true);
            TableUtils.dropTable(connectionSource, clsmConfig.class, true);
            TableUtils.dropTable(connectionSource, clsAbsenData.class, true);
            TableUtils.dropTable(connectionSource, clsAbsenOnline.class, true);
            TableUtils.dropTable(connectionSource, clsTrackingData.class, true);
            TableUtils.dropTable(connectionSource, clsmCounterData.class, true);
            TableUtils.dropTable(connectionSource, clsLastCheckingData.class, true);
            TableUtils.dropTable(connectionSource, clsReportData.class, true);
            TableUtils.dropTable(connectionSource, clsLeaveData.class, true);
            TableUtils.dropTable(connectionSource,clsTypeLeave.class,true);
            TableUtils.dropTable(connectionSource,clsBranchAccess.class,true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public void clearAllDataInDatabase() {
        try {
            TableUtils.clearTable(connectionSource, clsUserLogin.class);
            TableUtils.clearTable(connectionSource, clsDeviceInfo.class);
            TableUtils.clearTable(connectionSource, clsDisplayPicture.class);
            TableUtils.clearTable(connectionSource, clsmVersionApp.class);
            TableUtils.clearTable(connectionSource, clsUserLogin.class);
            TableUtils.clearTable(connectionSource, clsUserJabatan.class);
            TableUtils.clearTable(connectionSource, clsUserPegawai.class);
            TableUtils.clearTable(connectionSource, clsUserLob.class);
            TableUtils.clearTable(connectionSource, clsmConfig.class);
            TableUtils.clearTable(connectionSource, clsAbsenData.class);
            TableUtils.clearTable(connectionSource, clsAbsenOnline.class);
            TableUtils.clearTable(connectionSource, clsTrackingData.class);
            TableUtils.clearTable(connectionSource, clsmCounterData.class);
            TableUtils.clearTable(connectionSource, clsLastCheckingData.class);
            TableUtils.clearTable(connectionSource, clsReportData.class);
            TableUtils.clearTable(connectionSource, clsLeaveData.class);
            TableUtils.clearTable(connectionSource, clsTypeLeave.class);
            TableUtils.clearTable(connectionSource, clsBranchAccess.class);
            // after we drop the old databases, we create the new ones
//            onCreate(db, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void clearDataAfterLogout() {
        try {
            TableUtils.clearTable(connectionSource, clsUserLogin.class);
            TableUtils.clearTable(connectionSource, clsDisplayPicture.class);
            TableUtils.clearTable(connectionSource, clsmVersionApp.class);
            TableUtils.clearTable(connectionSource, clsUserJabatan.class);
            TableUtils.clearTable(connectionSource, clsUserPegawai.class);
            TableUtils.clearTable(connectionSource, clsUserLob.class);
            TableUtils.clearTable(connectionSource, clsAbsenData.class);
            TableUtils.clearTable(connectionSource, clsTrackingData.class);
            TableUtils.clearTable(connectionSource, clsmCounterData.class);
            TableUtils.clearTable(connectionSource, clsLastCheckingData.class);
            TableUtils.clearTable(connectionSource, clsReportData.class);
            TableUtils.clearTable(connectionSource, clsAbsenOnline.class);
            TableUtils.clearTable(connectionSource, clsLeaveData.class);
            TableUtils.clearTable(connectionSource,clsTypeLeave.class);
            TableUtils.clearTable(connectionSource,clsBranchAccess.class);
            // after we drop the old databases, we create the new ones
//            onCreate(db, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void clearDataTracking() {
        try {
            TableUtils.clearTable(connectionSource, clsTrackingData.class);
            // after we drop the old databases, we create the new ones
//            onCreate(db, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void clearDataAbsen() {
        try {
            TableUtils.clearTable(connectionSource, clsAbsenData.class);
            // after we drop the old databases, we create the new ones
//            onCreate(db, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<clsBranchAccess, Integer> getBranchAccessDao() throws SQLException {
        if (branchAccessDao == null){
            branchAccessDao = getDao(clsBranchAccess.class);
        }
        return branchAccessDao;
    }
    public Dao<clsTypeLeave, Integer> getTypeLeaveDao() throws  SQLException{
        if(typeLeaveDao == null){
            typeLeaveDao = getDao(clsTypeLeave.class);
        }
        return typeLeaveDao;
    }
    public Dao<clsLeaveData, Integer> getLeaveDataDao() throws SQLException {
        if (leaveDataDao == null) {
            leaveDataDao = getDao(clsLeaveData.class);
        }
        return leaveDataDao;
    }

    public Dao<clsLastCheckingData, Integer> getLastCheckingDataDao() throws SQLException {
        if (lastCheckingDataDao == null) {
            lastCheckingDataDao = getDao(clsLastCheckingData.class);
        }
        return lastCheckingDataDao;
    }

    public Dao<clsReportData, Integer> getReportDataDao() throws SQLException {
        if (reportDataDao == null) {
            reportDataDao = getDao(clsReportData.class);
        }
        return reportDataDao;
    }

    public Dao<clsmCounterData, Integer> getCounterDataDao() throws SQLException {
        if (counterDataDao == null) {
            counterDataDao = getDao(clsmCounterData.class);
        }
        return counterDataDao;
    }

    public Dao<clsTrackingData, Integer> getTrackingDataDao() throws SQLException {
        if (trackingDataDao == null) {
            trackingDataDao = getDao(clsTrackingData.class);
        }
        return trackingDataDao;
    }

    public Dao<clsAbsenData, Integer> getUserAbsenDao() throws SQLException {
        if (userAbsenDao == null) {
            userAbsenDao = getDao(clsAbsenData.class);
        }
        return userAbsenDao;
    }

    public Dao<clsAbsenOnline, Integer> getUserAbsenOnlineDao() throws SQLException {
        if (userAbsenOnlineDao == null) {
            userAbsenOnlineDao = getDao(clsAbsenOnline.class);
        }
        return userAbsenOnlineDao;
    }

    public Dao<clsmConfig, Integer> getmConfigDao() throws SQLException {
        if (mConfigDao == null) {
            mConfigDao = getDao(clsmConfig.class);
        }
        return mConfigDao;
    }

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

    public Dao<clsmVersionApp, Integer> getmVersionAppsDao() throws SQLException {
        if (mVersionAppsDao == null) {
            mVersionAppsDao = getDao(clsmVersionApp.class);
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

    public RuntimeExceptionDao<clsmVersionApp, Integer> getmVersionAppsRuntimeDao() {
        if (mVersionAppsRuntimeDao == null) {
            mVersionAppsRuntimeDao = getRuntimeExceptionDao(clsmVersionApp.class);
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
        mConfigDao = null;
        userLobDao = null;
        userJabatanDao = null;
        userPegawaiDao = null;
        trackingDataDao = null;
        userAbsenOnlineDao = null;
        lastCheckingDataDao = null;
        reportDataDao = null;
        leaveDataDao = null;
        typeLeaveDao =null;
    }

}
