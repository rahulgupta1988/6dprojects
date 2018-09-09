package expression.sixdexp.com.expressionapp.xpassions.gpmanager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.ServerCall;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import model.GroupObject;

/**
 * Created by Praveen on 08-Jan-18.
 */

public class GroupInfoManager extends BaseManager {

    Context _mContext;
    String responseString = "";



    public  GroupInfoManager(Context _mContext){
        this._mContext = _mContext;
    }


    public String getInfo() {

        try {
            Log.i("responstring", "get groups serviceUrl-->" + Constant.BASEURL+"api/coi/coi_getgrouplist");
            String userid = new LoginManager(_mContext).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("userid",userid);
            entitymap.put("mygroup","1");
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/coi/coi_getgrouplist");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseData(responseString);
    }

    public String parseData(String jsonResponse)
    {

        String responseCode = "";

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(_mContext).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");

                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString=responseCode;

                        }
                        else
                        {
                            responseString = jsonObject.getString("responseData");
                        }
                    }else {
                        responseString=jsonResponse;
                    }
                } else {
                    responseString=jsonResponse;
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