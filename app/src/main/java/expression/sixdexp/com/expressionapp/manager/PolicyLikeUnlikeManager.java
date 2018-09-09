package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by hp on 8/13/2017.
 */

public class PolicyLikeUnlikeManager extends BaseManager {

    Context context;
    String responseString = "";
    public  String likeCount="0";
    public String isLike="0";

    public PolicyLikeUnlikeManager(Context context) {
        this.context = context;

    }

    public String callLikePolicy(String policyid) {

        try {
            String userid = new LoginManager(context).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "policy like serviceUrl-->" + Constant.BASEURL + "api/srijan/policylikeunlike");

            entitymap.put("policyid", policyid);
            entitymap.put("userid", userid);
            entitymap.put("devicetype", "android");
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/srijan/policylikeunlike");

            if (responseString != null)
                Log.i("policy like", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseLikeGivenData(responseString);
    }


    public String parseLikeGivenData(String jsonResponse) {
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
                            JSONObject jsonObject1 = jsonObject.getJSONObject("responseData");

                            likeCount=jsonObject1.getString("likes");
                            isLike=jsonObject1.getString("isloginuserliked");


                        } else {
                            responseString = jsonObject.getString("responseData");
                        }
                    } else {
                        responseString = "Received data is not compatible.";
                    }
                } else {
                    responseString = "Received data is not compatible.";
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
