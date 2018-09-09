package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.CountRG;
import db.CountRGDao;
import db.DaoSession;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;

/**
 * Created by Praveen on 15-Jan-17.
 */

public class GivenReceivedManage extends BaseManager {

    Context context;
    String responseString = "";

    public GivenReceivedManage(Context context) {
        this.context = context;
    }



    public String recognitionRecievedCount() {
        try {

            HashMap<String, String> entitymap = new HashMap<String, String>();
            String nominator_id = SharedPrefrenceManager.getUserID(context);
            Log.i("userid",nominator_id);
            entitymap.put("userid", nominator_id);

            Log.i("responstring", "countRG serviceUrl-->" + Constant.BASEURL + "api/userApi/countRG");

            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/userApi/countRG");
            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsegetRecognitionCountData(responseString);
    }


    public String parsegetRecognitionCountData(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession=getDBSessoin(context);
        daoSession.getCountRGDao().deleteAll();
        CountRGDao countRGDao=daoSession.getCountRGDao();
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;
                            JSONObject jsonarray=jsonObject.getJSONObject("responseData");
                            String grecognition=jsonarray.getString("grecognition");
                            String rrecognition=jsonarray.getString("rrecognition");
                            String tgrecognition=jsonarray.getString("tgrecognition");
                            String trrecognition=jsonarray.getString("trrecognition");
                            CountRG countRG=new CountRG(grecognition,rrecognition,tgrecognition,trrecognition);
                            countRGDao.insertOrReplace(countRG);

                        } else {
                            responseString = jsonObject.getString("responseData");
                        }
                    } else {
                        responseString = "Received data is not compatible.";
                    }
                } else {
                    responseString = "Received data is not compatible.";
                }
            } else {
                responseString = "Please check your internet connection.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseString;
    }


    public List<CountRG> getCount()
    {
        DaoSession daoSession=getDBSessoin(context);
        CountRGDao countRGDao=daoSession.getCountRGDao();
        try {
            List<CountRG> countRGsList = new ArrayList<CountRG>();
            countRGsList = countRGDao.loadAll();
            return countRGsList;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
