package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.Award;
import db.AwardDao;
import db.DaoSession;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 7/11/2016.
 */
public class AwardManager extends BaseManager {

    Context context;
    String responseString="";


    public AwardManager (Context context) {
        this.context=context;

    }

    public String callAward()
    {

        try {

            HashMap<String,String> entitymap=new HashMap<String,String>();
            Log.i("responstring", "getawards serviceUrl-->" + Constant.BASEURL + "api/userapi/getawards");
            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userapi/getawards");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseawardData(responseString);
    }


    public String parseawardData(String jsonResponse)
    {
        String responseCode = "";
        DaoSession daoSession=getDBSessoin(context);
        daoSession.getAwardDao().deleteAll();
        AwardDao awardDao=daoSession.getAwardDao();
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
                            JSONArray jsonarray=jsonObject.getJSONArray("responseData");

                            for(int i=0;i<jsonarray.length();i++) {


                                JSONObject jsonObject1=jsonarray.getJSONObject(i);
                                String awardid = jsonObject1.optString("awardid");
                                String awardname = jsonObject1.optString("awardname");
                                String awardicon = jsonObject1.optString("awardicon");
                                String isactive = jsonObject1.optString("isactive");



                                Award award = new Award(awardid,awardname,awardicon,isactive);
                                awardDao.insertOrReplace(award);

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
                    responseString=responseString;
                }
            } else {
                responseString = "Please check your internet connection.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseString;
    }


    public List<Award> getAwardList()
    {
        DaoSession daoSession=getDBSessoin(context);
        AwardDao awardDao=daoSession.getAwardDao();
        try {
            List<Award> awards = new ArrayList<Award>();
            awards = awardDao.loadAll();
            return awards;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
