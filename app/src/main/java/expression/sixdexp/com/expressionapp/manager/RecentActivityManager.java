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
import db.RecentActivity;
import db.RecentActivityDao;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 7/12/2016.
 */
public class RecentActivityManager extends BaseManager {

    Context context;
    String responseString="";


    public RecentActivityManager (Context context) {
        this.context=context;

    }

    public String callRecentActivity()
    {

        try {

            HashMap<String,String> entitymap=new HashMap<String,String>();
            Log.i("responstring", "recentactivity serviceUrl-->" + Constant.BASEURL + "api/userapi/recentactivity");

            String userid=new LoginManager(context).getUserInfo().get(0).getUserid();
            entitymap.put("userid", userid);

            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userapi/recentactivity");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parserecentActivityData(responseString);
    }


    public String parserecentActivityData(String jsonResponse)
    {
        String responseCode = "";
       DaoSession daoSession=getDBSessoin(context);
        daoSession.getRecentActivityDao().deleteAll();
        RecentActivityDao recentActivityDao=daoSession.getRecentActivityDao();
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
                                String nominator_name = jsonObject1.optString("nominator_name");
                                String nominee_name = jsonObject1.optString("nominee_name");
                                String recognise_date = jsonObject1.optString("recognise_date");
                                String recognition_name = jsonObject1.optString("recognition_name");
                                String is_story = jsonObject1.optString("is_story");
                                String count = jsonObject1.optString("count");
                                String recognition_id = jsonObject1.optString("recognition_id");
                                String isxpress = jsonObject1.optString("isxpress");

                              String event_date ="";
                              String type_view ="";
                              String nominee="";
                              String award="";


                              if(jsonObject1.has("event_date"))
                               jsonObject1.optString("event_date");

                              if(jsonObject1.has("type_view"))
                               type_view = jsonObject1.optString("type_view");

                              if(jsonObject1.has("nominee"))
                                  nominee = jsonObject1.optString("nominee");

                              if(jsonObject1.has("award"))
                                   award = jsonObject1.optString("award");




                              RecentActivity recentActivity = new RecentActivity(nominator_name, nominee_name, recognise_date,
                                      recognition_name, is_story, count, recognition_id,nominee,isxpress,award,type_view);

                              recentActivityDao.insertOrReplace(recentActivity);

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


    public List<RecentActivity> getRecentActivityList()
    {
        DaoSession daoSession=getDBSessoin(context);
        RecentActivityDao recentActivityDao =daoSession.getRecentActivityDao();
        try {
            List<RecentActivity> recentActivities = new ArrayList<RecentActivity>();
            recentActivities = recentActivityDao.loadAll();
            return recentActivities;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
