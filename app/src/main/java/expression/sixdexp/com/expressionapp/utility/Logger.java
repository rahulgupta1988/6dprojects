package expression.sixdexp.com.expressionapp.utility;

import android.util.Log;

/**
 * Created by Praveen on 8/25/2016.
 */
public class Logger {

    private static final String TAG = "TataTeleService";

    private Logger() {
    }

    public static void i(Object o, String msg) {
        if (Constant.DEV_MODE)
            Log.i(TAG, o.getClass().getSimpleName() + ": " + msg);
        else if (Constant.QA_MODE)
            Log.i(TAG, o.getClass().getSimpleName() + ": " + msg);
    }

    public static void w(Object o, String msg) {
        if (Constant.DEV_MODE)
            Log.w(TAG, o.getClass().getSimpleName() + ": " + msg);
        else if (Constant.QA_MODE)
            Log.w(TAG, o.getClass().getSimpleName() + ": " + msg);
    }

    public static void e(Object o, String msg) {
        if (Constant.DEV_MODE)
            Log.w(TAG, o.getClass().getSimpleName() + ": " + msg);
        else if (Constant.QA_MODE)
            Log.w(TAG, o.getClass().getSimpleName() + ": " + msg);
    }

}
