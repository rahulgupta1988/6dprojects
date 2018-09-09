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
import model.PollOptionModel;
import model.PollQAModel;

/**
 * Created by Praveen on 01-Sep-17.
 */

public class VotePollManager extends BaseManager {

    Context context;
    String responseString = "";
    public List<PollOptionModel>pollOptionModels=new ArrayList<>();
    public VotePollManager(Context context) {
        this.context = context;

    }

    public String callVotePoll(String opinionanswerid,String pollid) {

        try {
            String userid=new LoginManager(context).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "OpinionPollVoteMark serviceUrl-->" + Constant.BASEURL + "api/OpinionPoll/OpinionPollVoteMark");
            entitymap.put("userid",userid);
            entitymap.put("pollid",pollid);
            entitymap.put("response",opinionanswerid);

            Log.i("userid",userid);
            Log.i("pollid",pollid);
            Log.i("opinionanswerid",opinionanswerid);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/OpinionPoll/OpinionPollVoteMark");

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

                            OpinionPollQuestionAnswerManager.pollQAModel.setOpinionpollid(opinionpollid);
                            OpinionPollQuestionAnswerManager.pollQAModel.setQuestiontitle(questiontitle);
                            OpinionPollQuestionAnswerManager.pollQAModel.setNoofparticipants(noofparticipants);
                            OpinionPollQuestionAnswerManager.pollQAModel.setIsanswer(isanswer);
                            OpinionPollQuestionAnswerManager.pollQAModel.setPolltype(polltype);
                            OpinionPollQuestionAnswerManager.pollQAModel.setEnd_date(end_date);
                            OpinionPollQuestionAnswerManager.pollQAModel.setPollOptionModels(pollOptionModels);
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

