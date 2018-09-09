package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.List;

import db.CelebrationMoments;
import db.CelebrationMomentsDao;
import db.DaoSession;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 07-Jan-17.
 */

public class CelebrationMomentsManger extends BaseManager {

    Context context;
    String responseString="";


    public CelebrationMomentsManger (Context context) {
        this.context=context;

    }

    public String callCelebration()
    {
        try {

            HashMap<String,String> entitymap=new HashMap<String,String>();
            Log.i("responstring", "getCelebration serviceUrl-->" + Constant.BASEURL + "api/userApi/getTodayAllCelebrateEvent");

            String userid=new LoginManager(context).getUserInfo().get(0).getUserid();
            entitymap.put("userid",userid);
            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userApi/getTodayAllCelebrateEvent");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseeCelebrationData(responseString);
    }


    public String parseeCelebrationData(String jsonResponse)
    {
        String responseCode = "";
        DaoSession daoSession=getDBSessoin(context);
        daoSession.getCelebrationMomentsDao().deleteAll();
        CelebrationMomentsDao celebrationMomentsDao=daoSession.getCelebrationMomentsDao();
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
                                String userid = jsonObject1.optString("userid");
                                String name = jsonObject1.optString("name");
                                String eventmaster_id = jsonObject1.optString("eventmaster_id");
                                String useevent_idrid = jsonObject1.optString("event_id");
                                String department = jsonObject1.optString("department");
                                String event_desc = jsonObject1.optString("event_desc");
                                String work_year = jsonObject1.optString("work_year");
                                String imageurl = jsonObject1.optString("imageurl");
                                String emailId = jsonObject1.optString("emailId");
                                String title = jsonObject1.optString("title");

                                CelebrationMoments celebrationMoments = new CelebrationMoments(userid,name,eventmaster_id,useevent_idrid,
                                        department,event_desc,work_year,imageurl,emailId,title);
                                celebrationMomentsDao.insertOrReplace(celebrationMoments);

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


    public List<CelebrationMoments> getCelebrationList()
    {
        DaoSession daoSession=getDBSessoin(context);
        CelebrationMomentsDao celebrationMomentsDao=daoSession.getCelebrationMomentsDao();
        try {
            List<CelebrationMoments> celebrationMomentses=null;
            celebrationMomentses = celebrationMomentsDao.loadAll();
            return celebrationMomentses;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
