package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import db.DaoSession;
import db.RecognitionGiven;
import db.RecognitionGivenDao;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;

/**
 * Created by Praveen on 8/8/2016.
 */
public class RecognitionRecivedAndGiven extends BaseManager {


    Context context;
    String responseString = "";
    boolean recognitionGiven;

    public RecognitionRecivedAndGiven(Context context ,boolean recognitionGiven) {
        this.context = context;
        this.recognitionGiven=recognitionGiven;

    }

    public String callRecognitionRecivedAndGiven(String userid,String awardid,String frmdt,String todt) {

        try {

            HashMap<String, String> entitymap = new HashMap<String, String>();
            String nominator_id =  SharedPrefrenceManager.getUserID(context);
            if(userid.equals("") && awardid.equals("") && frmdt.equals("") && todt.equals("")){

                Log.i("userid","0");
                Log.i("awardid", "0");
                Log.i("nominator_id", userid);

                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String todayDate= sdf.format(date);

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE,-30);
                Date result = cal.getTime();
                String beforeOneMonthDate= sdf.format(result);
                Log.i("frmdt11",beforeOneMonthDate);
                Log.i("todt11",todayDate);


                entitymap.put("userid","0");
                entitymap.put("awardid", "0");
                entitymap.put("nominator_id", nominator_id);
                entitymap.put("frmdt",beforeOneMonthDate);
                entitymap.put("todt",todayDate);

            }

            else {

                Log.i("userid",userid);
                Log.i("awardid",awardid);
                Log.i("nominator_id", nominator_id);
                Log.i("frmdt",frmdt);
                Log.i("todt",todt);


                entitymap.put("userid", userid);
                entitymap.put("awardid", awardid);
                entitymap.put("nominator_id", nominator_id);
                entitymap.put("frmdt", frmdt);
                entitymap.put("todt",todt);
            }

            if(recognitionGiven) {
                Log.i("responstring", "getrecognitiongiven serviceUrl-->" + Constant.BASEURL + "api/userapi/getrecognitiongiven");
                responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/getrecognitiongiven");
            }

            else{
                Log.i("responstring", "getRecognitionReceived serviceUrl-->" + Constant.BASEURL + "api/userapi/getRecognitionReceived");
                responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/getRecognitionReceived");
            }

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseRecognitionRecivedAndGivenData(responseString);
    }


    public String parseRecognitionRecivedAndGivenData(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession=getDBSessoin(context);
        daoSession.getRecognitionGivenDao().deleteAll();
        RecognitionGivenDao recognitionGivenDao=daoSession.getRecognitionGivenDao();
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;
                            JSONArray jsonarray = jsonObject.getJSONArray("responseData");

                          for(int i=0;i<jsonarray.length();i++) {


                                JSONObject jsonObject1=jsonarray.getJSONObject(i);
                                String awardid = jsonObject1.optString("awardid");
                                String awardName = jsonObject1.optString("awardName");
                                String count = jsonObject1.optString("count");
                                String total = jsonObject1.optString("total");


                              RecognitionGiven recognitionGiven = new RecognitionGiven(awardid,awardName,count,total);

                              recognitionGivenDao.insertOrReplace(recognitionGiven);

                            }

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


    public List<RecognitionGiven> getRecognitionGivenList() {
        DaoSession daoSession = getDBSessoin(context);
        RecognitionGivenDao recognitionGivenDao=daoSession.getRecognitionGivenDao();
        try {
            List<RecognitionGiven> recognitionGivens = new ArrayList<RecognitionGiven>();
            recognitionGivens = recognitionGivenDao.loadAll();
            return recognitionGivens;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
