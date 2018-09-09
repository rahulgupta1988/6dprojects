package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 19-Apr-17.
 */

public class LogOutManager extends BaseManager {

    Context _mContext;
    String responseString = "";

    public LogOutManager(Context context) {
        _mContext = context;

    }

    public String logoutUser(String userid) {

        try {

            Log.i("responstring", "Logout serviceUrl-->" + Constant.BASEURL
                    + "&param=isnightmode");
            Log.i("devicetoken", " ");
            Log.i("devicetype", "android");
            Log.i("userid",userid);


            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("devicetoken", " ");
            entitymap.put("devicetype", "android");
            entitymap.put("userid",userid);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userApi/isnightmode");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseLogoutData(responseString);
    }

    public String parseLogoutData(String jsonResponse) {

        String responseCode = "";

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) && new InternetConnectionDetector(_mContext).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;
                        } else {
                            responseString = jsonObject.getString("responseData");
                        }
                    } else {
                        responseString = jsonResponse;
                    }
                } else {
                    responseString = jsonResponse;
                }
            } else {
                responseString = "Please check your internet connection.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;
    }


}