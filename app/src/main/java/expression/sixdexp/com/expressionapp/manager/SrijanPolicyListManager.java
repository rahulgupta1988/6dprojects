package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import model.SrijanPolicyListModel;

/**
 * Created by Praveen on 01-Aug-17.
 */

public class SrijanPolicyListManager extends BaseManager {

    Context context;
    String responseString = "";

    public static List<SrijanPolicyListModel> srijanPolicyListModels = null;

    public SrijanPolicyListManager(Context context) {
        this.context = context;

    }

    public String callSrijanPolicy() {

        try {

            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "getallpolicies serviceUrl-->" + Constant.BASEURL + "api/srijan/getallpolicies");
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/srijan/getallpolicies");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseComentData(responseString);
    }


    public String parseComentData(String jsonResponse) {
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

                            JSONArray responseData_json = jsonObject.getJSONArray("responseData");
                            Gson gson = new Gson();
                            //srijanPolicyListModel=gson.fromJson(responseData_json.toString(),SrijanPolicyListModel.class);

                            Type listType = new TypeToken<List<SrijanPolicyListModel>>() {
                            }.getType();
                            srijanPolicyListModels = (List<SrijanPolicyListModel>) gson.fromJson(responseData_json.toString(), listType);
                            Collections.sort(srijanPolicyListModels,SrijanPolicyListModel.policyComparator);
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

