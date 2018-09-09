package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.CurrentOpinion;
import db.CurrentOpinionDao;
import db.DaoSession;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 7/27/2016.
 */
public class CurrentPollManager extends BaseManager {

    Context context;
    String responseString="";


    public CurrentPollManager (Context context) {
        this.context=context;

    }

    public String callCurrentPoll()
    {

        try {
            Log.i("responstring", "getcurrentopinion serviceUrl-->" + Constant.BASEURL + "api/userapi/getcurrentopinion");

            HashMap<String,String> entitymap=new HashMap<String,String>();
            String userid=new LoginManager(context).getUserInfo().get(0).getUserid();
            entitymap.put("userid", userid);
            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userapi/getcurrentopinion");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsePollData(responseString);
    }


    public String parsePollData(String jsonResponse)
    {
        String responseCode = "";
        DaoSession daoSession=getDBSessoin(context);
        daoSession.getCurrentOpinionDao().deleteAll();
        CurrentOpinionDao currentOpinionDao=daoSession.getCurrentOpinionDao();
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode"))
                    {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString = responseCode;
                            JSONObject jsonObject1=jsonObject.getJSONObject("responseData");
                            String opinionid=jsonObject1.getString("opinionid");
                            String question=jsonObject1.getString("question");
                            String answertype=jsonObject1.getString("answertype");
                            String enddate=jsonObject1.getString("enddate");
                            String isactive=jsonObject1.getString("isactive");
                            String type=jsonObject1.getString("type");

                            CurrentOpinion currentOpinion = new CurrentOpinion(opinionid,question,answertype,
                                        enddate,isactive,type);
                            currentOpinionDao.insertOrReplace(currentOpinion);

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


    public List<CurrentOpinion> getCurrentPoll()
    {
        DaoSession daoSession=getDBSessoin(context);
        CurrentOpinionDao currentOpinionDao=daoSession.getCurrentOpinionDao();
        try {
            List<CurrentOpinion> currentOpinions = new ArrayList<CurrentOpinion>();
            currentOpinions = currentOpinionDao.loadAll();
            return currentOpinions;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
