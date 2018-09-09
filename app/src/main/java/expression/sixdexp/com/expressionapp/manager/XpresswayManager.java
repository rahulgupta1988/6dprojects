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
import db.Xpressway;
import db.XpresswayDao;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 7/5/2016.
 */
public class XpresswayManager extends BaseManager{

    Context context;
    String responseString="";


    public XpresswayManager (Context context) {
        this.context=context;

    }

    public String callXpressway()
    {

        try {

            HashMap<String,String> entitymap=new HashMap<String,String>();
            Log.i("responstring", "getmorepost serviceUrl-->" + Constant.BASEURL + "api/userapi/xpressway");
            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userapi/xpressway");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseXpresswayData(responseString);
    }


    public String parseXpresswayData(String jsonResponse)
    {
        String responseCode = "";
        DaoSession daoSession=getDBSessoin(context);
        daoSession.getXpresswayDao().deleteAll();
        XpresswayDao xpresswayDao=daoSession.getXpresswayDao();
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
                                String recognition_id = jsonObject1.optString("recognition_id");
                                String nominator_name = jsonObject1.optString("nominator_name");
                                String subject = jsonObject1.optString("subject");
                                String isstory = jsonObject1.optString("isstory");
                                String nominee = jsonObject1.optString("nominee");
                                String other = jsonObject1.optString("other");
                                String award = jsonObject1.optString("award");
                                String recognise_date = jsonObject1.optString("recognise_date");
                                String image_url=jsonObject1.optString("image_url");
                                Xpressway xpressway = new Xpressway(recognition_id,nominator_name,subject,isstory,
                                        nominee,other,award,recognise_date,image_url);
                                xpresswayDao.insertOrReplace(xpressway);

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


    public List<Xpressway> getXpresswayDataList()
    {
        DaoSession daoSession=getDBSessoin(context);
        XpresswayDao xpresswayDao=daoSession.getXpresswayDao();
        try {
            List<Xpressway> xpressways = new ArrayList<Xpressway>();
            xpressways = xpresswayDao.loadAll();
            return xpressways;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
