package expression.sixdexp.com.expressionapp.xpassions.gpmanager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.ServerCall;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 09-Feb-18.
 */

public class GPTheamManager extends BaseManager {

    Context _mContext;
    String responseString = "";

    public  GPTheamManager(Context _mContext){
        this._mContext = _mContext;
    }


    public String updateTheam(String gid,String themeid) {

        try {
            Log.i("responstring", "coi_updategrouptheme serviceUrl-->" + Constant.BASEURL+"api/coi/coi_updategrouptheme");
            String userid = new LoginManager(_mContext).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("userid",userid);
            entitymap.put("gid",gid);
            entitymap.put("themeid",themeid);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/coi/coi_updategrouptheme");

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
