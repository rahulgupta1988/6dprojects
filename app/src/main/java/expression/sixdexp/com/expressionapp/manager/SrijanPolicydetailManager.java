package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import model.PolicydetailModel;

/**
 * Created by Praveen on 04-Aug-17.
 */

public class SrijanPolicydetailManager extends BaseManager {

    Context context;
    String responseString = "";
    public static PolicydetailModel policydetailModel=null;

    public SrijanPolicydetailManager(Context context) {
        this.context = context;

    }

    public String callPdetail(String policyid) {

        try {

            String userid=new LoginManager(context).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "getpolicybypolicyid serviceUrl-->" + Constant.BASEURL + "api/srijan/getpolicybypolicyid");

            entitymap.put("policyid",policyid);
            entitymap.put("userid",userid);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/srijan/getpolicybypolicyid");


            Log.i("policyid 0182",""+policyid);
            Log.i("userid 0182",""+userid);

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsedetails(responseString);
    }


    public String parsedetails(String jsonResponse) {
        String responseCode = "";

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) && new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;

                            JSONObject responseData_json = jsonObject.getJSONObject("responseData");
                            Gson gson=new Gson();
                            policydetailModel=gson.fromJson(responseData_json.toString(),PolicydetailModel.class);

                        } else {
                            responseString = jsonObject.getString("responseData");
                        }
                    } else {
                        responseString = "Received data is not compatible.";
                    }
                } else {
                    responseString = responseString;
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