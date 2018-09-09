package expression.sixdexp.com.expressionapp.xpassions.gpmanager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import expression.sixdexp.com.expressionapp.adapter.GroupListAdapter;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.ServerCall;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;

/**
 * Created by Praveen on 02-Apr-18.
 */

public class GPVisitorManager {


    Context context;
    String responseString = "";

    public GPVisitorManager(Context context) {
        this.context = context;

    }

    public String callGPVisitorCount() {
        try {
            String gid = "0";
            if (GroupListAdapter.groupObject != null) {
                gid = GroupListAdapter.groupObject.getCoi_gid();
            }
            String userid = new LoginManager(context).getUserInfo().get(0).getUserid();


            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "api/coi/coi_visitors serviceUrl-->" + Constant.BASEURL + "api/coi/coi_visitors");
            entitymap.put("userid", userid);
            entitymap.put("gid", gid);

            Log.i("userid", userid);
            Log.i("gid", gid);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/coi/coi_visitors");

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
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");

                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString=responseCode;
                           String  vistior_count = jsonObject.getString("responseData");
                           SharedPrefrenceManager.putGPVisitorCount(context,vistior_count);
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
