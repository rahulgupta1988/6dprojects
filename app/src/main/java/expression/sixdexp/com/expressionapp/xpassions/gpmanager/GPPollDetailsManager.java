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
import model.PollOptionModel;
import model.PollQAModel;

/**
 * Created by Praveen on 01-Mar-18.
 */

public class GPPollDetailsManager extends BaseManager {

    Context context;
    String responseString = "";
    public List<PollOptionModel> pollOptionModels=new ArrayList<>();
    public static PollQAModel pollQAModel=new PollQAModel();

    public GPPollDetailsManager(Context context) {
        this.context = context;

    }

    public String callOpinionDetails(String pollid) {

        try {

            String gid="0";
            if(GroupListAdapter.groupObject!=null){
                gid=GroupListAdapter.groupObject.getCoi_gid();
            }

            String userid=new LoginManager(context).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "coi_GetOpinionPollByPollId serviceUrl-->" + Constant.BASEURL + "api/OpinionPoll/coi_GetOpinionPollByPollId");
            entitymap.put("userid",userid);
            entitymap.put("pollid",pollid);
            entitymap.put("gid",gid);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/OpinionPoll/coi_GetOpinionPollByPollId");

            if (responseString != null)
                Log.i("GetOpinionPollByPollId", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseComentData(responseString);
    }


    public String parseComentData(String jsonResponse) {
        String responseCode = "";
        if(pollQAModel.getPollOptionModels()!=null)pollQAModel.getPollOptionModels().clear();
        pollOptionModels.clear();
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) && new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;
                            JSONObject pollJson=jsonObject.getJSONObject("responseData");
                            String opinionpollid=pollJson.getString("opinionpollid");
                            String questiontitle=pollJson.getString("questiontitle");
                            String noofparticipants=pollJson.getString("noofparticipants");
                            String isanswer=pollJson.getString("isanswer");
                            String polltype=pollJson.getString("polltype");
                            String end_date=pollJson.getString("end_date");

                            JSONArray optionArr=pollJson.getJSONArray("optiontovote");

                            for(int i=0;i<optionArr.length();i++){
                                PollOptionModel pollOptionModel=new PollOptionModel();
                                JSONObject optionjson=optionArr.getJSONObject(i);
                                pollOptionModel.setOpinionanswerid(optionjson.getString("opinionanswerid"));
                                pollOptionModel.setAnswertitle(optionjson.getString("answertitle"));
                                pollOptionModel.setImageurl(optionjson.getString("imageurl"));
                                pollOptionModel.setPercentage(optionjson.getString("percentage"));
                                pollOptionModel.setPerc(optionjson.getString("perc"));
                                pollOptionModels.add(pollOptionModel);
                            }

                            pollQAModel.setOpinionpollid(opinionpollid);
                            pollQAModel.setQuestiontitle(questiontitle);
                            pollQAModel.setNoofparticipants(noofparticipants);
                            pollQAModel.setIsanswer(isanswer);
                            pollQAModel.setPolltype(polltype);
                            pollQAModel.setEnd_date(end_date);
                            pollQAModel.setPollOptionModels(pollOptionModels);
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

