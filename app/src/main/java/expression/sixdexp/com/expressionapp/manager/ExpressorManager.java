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
import db.Expossor;
import db.ExpossorDao;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 7/14/2016.
 */
public class ExpressorManager extends BaseManager {

    Context context;
    String responseString="";


    public ExpressorManager (Context context) {
        this.context=context;

    }

    public String callExpressor()
    {

        try {

            HashMap<String,String> entitymap=new HashMap<String,String>();
            Log.i("responstring", "getxpressor serviceUrl-->" + Constant.BASEURL + "api/userApi/getxpressor");

            entitymap.put("awardid","0");
            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userApi/getxpressor");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseexpressorData(responseString);
    }


    public String parseexpressorData(String jsonResponse)
    {
        String responseCode = "";
        DaoSession daoSession=getDBSessoin(context);
        daoSession.getExpossorDao().deleteAll();
        ExpossorDao expossorDao=daoSession.getExpossorDao();
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
                                String sno = jsonObject1.optString("sno");
                                String xpressor_id = jsonObject1.optString("xpressor_id");
                                String xpressor_text = jsonObject1.optString("xpressor_text");
                                String xpressor_rmtxt = jsonObject1.optString("xpressor_rmtxt");
                                String awardid = jsonObject1.optString("awardid");

                                Expossor expossor = new Expossor(sno,xpressor_id,xpressor_text,xpressor_rmtxt,awardid);
                                expossorDao.insertOrReplace(expossor);

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


    public List<Expossor> getExpossorList()
    {
        DaoSession daoSession=getDBSessoin(context);
        ExpossorDao expossorDao=daoSession.getExpossorDao();
        try {
            List<Expossor> expossors = new ArrayList<Expossor>();
            expossors = expossorDao.loadAll();
            return expossors;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
