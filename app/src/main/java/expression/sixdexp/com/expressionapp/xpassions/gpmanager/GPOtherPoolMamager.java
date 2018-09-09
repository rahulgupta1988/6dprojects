package expression.sixdexp.com.expressionapp.xpassions.gpmanager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import expression.sixdexp.com.expressionapp.adapter.GroupListAdapter;
import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.ServerCall;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import model.OtherPollModel;

/**
 * Created by Praveen on 26-Feb-18.
 */

public class GPOtherPoolMamager extends BaseManager {

    Context context;
    String responseString = "";
    public static List<OtherPollModel> otherPollModels=new ArrayList<>();


    public GPOtherPoolMamager(Context context) {
        this.context = context;

    }

    public String callOtherPoll() {

        try {

            String gid="0";
            if(GroupListAdapter.groupObject!=null){
                gid=GroupListAdapter.groupObject.getCoi_gid();
            }

            String userid=new LoginManager(context).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "coi_GetOtherPollDetails serviceUrl-->" + Constant.BASEURL + "api/OpinionPoll/coi_GetOtherPollDetails");
            entitymap.put("userid",userid);
            entitymap.put("gid",gid);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/OpinionPoll/coi_GetOtherPollDetails");

            if (responseString != null)
                Log.i("GetOtherPollDetails", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseComentData(responseString);
    }


    public String parseComentData(String jsonResponse) {
        String responseCode = "";
        otherPollModels.clear();
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) && new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;
                            JSONArray pollJson=jsonObject.getJSONArray("responseData");

                            for(int i=0;i<pollJson.length();i++){
                                OtherPollModel otherPollModel=new OtherPollModel();
                                JSONObject optionjson=pollJson.getJSONObject(i);
                                otherPollModel.setOpinionpollid(optionjson.getString("opinionpollid"));
                                otherPollModel.setQuestiontitle(optionjson.getString("questiontitle"));
                                otherPollModel.setEnddate(optionjson.getString("enddate"));
                                otherPollModels.add(otherPollModel);
                            }
                        }

                        else {
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

