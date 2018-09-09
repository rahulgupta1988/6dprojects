package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import db.DaoMaster;
import db.DaoSession;

/**
 * Created by Praveen on 7/4/2016.
 */
public class BaseManager {


    public static final String LOG_TAG = "BaseManager";
    public static DaoMaster.DevOpenHelper helper=null;

    public static DaoSession getDBSessoin(Context context) {
        try {
            if(context!=null){
                DaoMaster.DevOpenHelper helper=getHelper(context);
                SQLiteDatabase sqLiteDatabase=helper.getWritableDatabase();
                DaoMaster daoMaster=new DaoMaster(sqLiteDatabase);
                DaoSession daoSession=daoMaster.newSession();
                if (daoSession != null) {
                    return daoSession;
                } else {
                    Log.i(LOG_TAG, "getDBSessoin:  - daoSession is null");
                    return null;
                }
            }
            else {
                Log.i(LOG_TAG, "getDBSessoin:  - Application context is null");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void closeDatabase(Context context) {
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(context,"xpressiontataapp.sqlite",null);
        helper.close();
    }

    public static DaoMaster.DevOpenHelper getHelper(Context context){

        if(helper==null){
            Log.i("Created Helper","created");
            helper=new DaoMaster.DevOpenHelper(context,"xpressiontataapp.sqlite",null);
            return helper;
        }

        else{
            Log.i("not Created Helper","return old");
            return helper;
        }
    }




}
