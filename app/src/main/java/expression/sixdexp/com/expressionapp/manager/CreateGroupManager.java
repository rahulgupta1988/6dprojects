package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 04-Jan-18.
 */

public class CreateGroupManager  extends  BaseManager {

    Context _mContext;
    String responseString = "";

    public  CreateGroupManager(Context _mContext){
        this._mContext = _mContext;
    }


    public String createGroup(String gp_name_str,String gp_des_str,String people_ids,String base64,String closed_open,String themeid) {

        try {
            Log.i("responstring", "get create group serviceUrl-->" + Constant.BASEURL+"api/coi/coi_creategroup");
            String userid = new LoginManager(_mContext).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("userid",userid);
            entitymap.put("gtitle",gp_name_str);
            entitymap.put("gdescription",gp_des_str);
            entitymap.put("base64groupicon",base64);
            entitymap.put("isopen",closed_open);
            entitymap.put("membertobeadded",people_ids);
            entitymap.put("devicetype","android");
            entitymap.put("themeid",themeid);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/coi/coi_creategroup");

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