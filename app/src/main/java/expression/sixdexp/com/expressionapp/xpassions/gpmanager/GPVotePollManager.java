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

/**
 * Created by Praveen on 26-Feb-18.
 */

public class GPVotePollManager extends BaseManager {

    Context context;
    String responseString = "";

    String singleGPid="";
    public List<PollOptionModel> pollOptionModels=new ArrayList<>();
    public GPVotePollManager(Context context) {
        this.context = context;

    }

    public GPVotePollManager(Context context, String singleGPid) {
        this.context = context;
        this.singleGPid=singleGPid;

    }


    public String callVotePoll(String opinionanswerid,String pollid) {

        try {
            String gid="0";


            if(!singleGPid.equals(""))
            {
                gid=singleGPid;
            }

            else {
                if (GroupListAdapter.groupObject != null) {
                    gid = GroupListAdapter.groupObject.getCoi_gid();
                }
            }

            String userid=new LoginManager(context).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "coi_OpinionPollVoteMark serviceUrl-->" + Constant.BASEURL + "api/OpinionPoll/coi_OpinionPollVoteMark");
            entitymap.put("userid",userid);
            entitymap.put("pollid",pollid);
            entitymap.put("response",opinionanswerid);
            entitymap.put("gid",gid);

            Log.i("userid",userid);
            Log.i("pollid",pollid);
            Log.i("opinionanswerid",opinionanswerid);
            Log.i("gid",gid);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/OpinionPoll/coi_OpinionPollVoteMark");

            if (responseString != null)
                Log.i("OpinionPollVoteMark", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseComentData(responseString);
    }


    public String parseComentData(String jsonResponse) {
        String responseCode = "";
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

                            GPOpinionPollQuestionAnswerManager.pollQAModel.setOpinionpollid(opinionpollid);
                            GPOpinionPollQuestionAnswerManager.pollQAModel.setQuestiontitle(questiontitle);
                            GPOpinionPollQuestionAnswerManager.pollQAModel.setNoofparticipants(noofparticipants);
                            GPOpinionPollQuestionAnswerManager.pollQAModel.setIsanswer(isanswer);
                            GPOpinionPollQuestionAnswerManager.pollQAModel.setPolltype(polltype);
                            GPOpinionPollQuestionAnswerManager.pollQAModel.setEnd_date(end_date);
                            GPOpinionPollQuestionAnswerManager.pollQAModel.setPollOptionModels(pollOptionModels);
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

