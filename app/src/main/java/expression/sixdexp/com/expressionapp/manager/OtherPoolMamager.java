package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import model.OtherPollModel;

/**
 * Created by Praveen on 02-Sep-17.
 */

public class OtherPoolMamager extends BaseManager {

    Context context;
    String responseString = "";
    public static List<OtherPollModel> otherPollModels=new ArrayList<>();


    public OtherPoolMamager(Context context) {
        this.context = context;

    }

    public String callOtherPoll() {

        try {
            String userid=new LoginManager(context).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "GetOtherPollDetails serviceUrl-->" + Constant.BASEURL + "api/OpinionPoll/GetOtherPollDetails");
            entitymap.put("userid",userid);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/OpinionPoll/GetOtherPollDetails");

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
