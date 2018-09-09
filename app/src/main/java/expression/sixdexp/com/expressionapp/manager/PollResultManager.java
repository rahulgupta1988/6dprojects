package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.DaoSession;
import db.PollResult;
import db.PollResultDao;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 7/27/2016.
 */
public class PollResultManager extends BaseManager {

    Context _mContext;
    String responseString="";
    String recognizationId="";
    public  PollResultManager(Context context){
        _mContext=context;

    }

    public String pollResultCall()
    {

        try {

            Log.i("responstring", "getComments serviceUrl-->" + Constant.BASEURL
                    + "&api/userapi/getOtherPoll");

            String userid=new LoginManager(_mContext).getUserInfo().get(0).getUserid();
            HashMap<String,String> entitymap=new HashMap<String,String>();
            entitymap.put("userid",userid);

            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userapi/getOtherPoll");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsePollResultData(responseString);
    }

    public String parsePollResultData(String jsonResponse)
    {

        String responseCode = "";
        DaoSession daoSession=getDBSessoin(_mContext);

        PollResultDao pollResultDao=daoSession.getPollResultDao();
        pollResultDao.deleteAll();

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(_mContext).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString = responseCode;

                            JSONArray responseData_json=jsonObject.getJSONArray("responseData");
                            for (int i=0;i<responseData_json.length();i++){

                                JSONObject jObj=responseData_json.getJSONObject(i);
                                String opinionid=jObj.getString("opinionid");
                                String question=jObj.getString("question");
                                String answertype=jObj.getString("answertype");
                                String enddate=jObj.getString("enddate");
                                String isactive=jObj.getString("isactive");
                                String agree_perc=jObj.getString("agree_perc");
                                String disagree_perc=jObj.getString("disagree_perc");
                                String cantsay_perc=jObj.getString("cantsay_perc");
                                String status=jObj.getString("status");

                                PollResult pollResult=new PollResult(opinionid,question,answertype,
                                        enddate,isactive,agree_perc,disagree_perc,cantsay_perc,status);
                                pollResultDao.insertOrReplace(pollResult);
                            }

                        }
                        else
                        {
                            responseString = jsonObject.getString("responseData");
                        }
                    }else {
                        responseString="Received data is not compatible.";
                    }
                } else {
                    responseString="Received data is not compatible.";
                }
            } else {
                responseString = "Please check your internet connection.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;
    }


    public List<PollResult> getPollResultList()
    {
        DaoSession daoSession=getDBSessoin(_mContext);
        PollResultDao pollResultDao=daoSession.getPollResultDao();
        try {
            List<PollResult> pollResults = new ArrayList<PollResult>();
            pollResults = pollResultDao.loadAll();
            return pollResults;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



}
