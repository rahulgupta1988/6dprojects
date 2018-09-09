package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.BulletinList;
import db.BulletinListDao;
import db.DaoSession;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 19-Jan-17.
 */

public class XPBulletinManager extends BaseManager {

    Context context;
    String responseString="";
    int loadinMore;

    public XPBulletinManager (Context context) {
        this.context=context;

    }

    public String callBulletinList(String catid,int loadinMore,String startindex,String pagesize)
    {

        this.loadinMore=loadinMore;
        try {
            String userid=new LoginManager(context).getUserInfo().get(0).getUserid();
            HashMap<String,String> entitymap=new HashMap<String,String>();
            entitymap.put("userid",userid);
            entitymap.put("catid",catid);
            entitymap.put("startindex",startindex);
            entitymap.put("pagesize",pagesize);
            Log.i("responstring", "getAllButtinbycatid serviceUrl-->" + Constant.BASEURL + "api/userApi/getAllButtinbycatid");
                Log.i("userid",""+userid);
                Log.i("catid",""+catid);
                Log.i("startindex",startindex);
                Log.i("pagesize",pagesize);

            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userApi/getAllButtinbycatid");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseBulletin(responseString);
    }

    public String callBulletinListBySearchKey(String keyword,int loadinMore)
    {
        this.loadinMore=loadinMore;
        try {

            HashMap<String,String> entitymap=new HashMap<String,String>();
            entitymap.put("keyword",keyword);
            Log.i("responstring", "getAllButtinbykeyworksearch serviceUrl-->" + Constant.BASEURL + "api/userApi/getAllButtinbykeyworksearch");
            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userApi/getAllButtinbykeyworksearch");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseBulletin(responseString);
    }


    public String parseBulletin(String jsonResponse)
    {
        String responseCode = "";
        DaoSession daoSession=getDBSessoin(context);
        if(loadinMore==0 || loadinMore ==2){
            daoSession.getBulletinListDao().deleteAll();
        }


        BulletinListDao bulletinListDao=daoSession.getBulletinListDao();
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

                                String postedbyuser = jsonObject1.optString("postedbyuser");
                                String postbyemail = jsonObject1.optString("postbyemail");
                                String details = jsonObject1.optString("details");
                                String create_date = jsonObject1.optString("create_date");
                                String groupemail = jsonObject1.optString("groupemail");
                                String type = jsonObject1.optString("type");
                                String fileurl = jsonObject1.optString("fileurl");
                                String url = jsonObject1.optString("url");


                                BulletinList bulletinList = new BulletinList(postedbyuser,postbyemail,details,create_date,groupemail,
                                        type,fileurl,url);
                                bulletinListDao.insertOrReplace(bulletinList);

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


    public List<BulletinList> getBulletinList()
    {
        DaoSession daoSession=getDBSessoin(context);
        BulletinListDao bulletinListDao=daoSession.getBulletinListDao();
        try {
            List<BulletinList> bulletinLists = new ArrayList<BulletinList>();
            bulletinLists = bulletinListDao.loadAll();
            return bulletinLists;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
