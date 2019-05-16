package com.myself.teacher.base;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.myself.teacher.db.DaoMaster;
import com.myself.teacher.db.DaoSession;
import com.myself.teacher.utils.SharedPreferencesUtils;

import org.greenrobot.greendao.database.Database;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by admin on 2018/5/9.
 */

public class MyApplication extends Application {

    public static MyApplication MyInstance;
    private static Context context;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        MyInstance = this;
        context = getApplicationContext();
        SharedPreferencesUtils.init(context);


        //复制assets目录下的数据库文件到应用数据库中
        try {
            copyDataBase("teacher.db");
        } catch (Exception e) {
            Log.e("Application", e.getMessage());
        }

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "teacher.db", null);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static Context getContextObject() {
        return context;
    }

    public static MyApplication getMyInstance() {
        return MyInstance;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase(String dbname) throws IOException {
        // Open your local db as the input stream
        InputStream myInput = this.getAssets().open(dbname);
        // Path to the just created empty db
        File outFileName = this.getDatabasePath(dbname);

        if (!outFileName.exists()) {
            outFileName.getParentFile().mkdirs();

            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

}
    
