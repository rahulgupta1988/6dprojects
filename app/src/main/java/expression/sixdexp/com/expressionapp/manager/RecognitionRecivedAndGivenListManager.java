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
import db.RecognitionGivenList;
import db.RecognitionGivenListDao;
import db.RecognitionRecieved;
import db.RecognitionRecievedDao;
import db.RecognitionRecievedList;
import db.RecognitionRecievedListDao;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;

/**
 * Created by Praveen on 8/8/2016.
 */
public class RecognitionRecivedAndGivenListManager extends BaseManager {

    Context context;
    String responseString = "";
    boolean recognitionGiven;

    public RecognitionRecivedAndGivenListManager(Context context,boolean recognitionGiven) {
        this.context = context;
        this.recognitionGiven=recognitionGiven;

    }

    public RecognitionRecivedAndGivenListManager(Context context) {
        this.context = context;
    }



    public String RecognitionRecievedListTaskData(String userid,String awardid,String frmdt,String todt) {
        try {

            HashMap<String, String> entitymap = new HashMap<String, String>();
            String nominator_id = SharedPrefrenceManager.getUserID(context);
            if(userid.equals("") && awardid.equals("") && frmdt.equals("") && todt.equals("")){

                Log.i("userid","0");
                Log.i("awardid", "0");
                Log.i("nominator_id", userid);

                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String todayDate= sdf.format(date);

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -30);
                Date result = cal.getTime();
                String beforeOneMonthDate= sdf.format(result);

                Log.i("frmdt",beforeOneMonthDate);
                Log.i("todt",todayDate);

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
                entitymap.put("awardid",awardid);
                entitymap.put("nominator_id", nominator_id);
                entitymap.put("frmdt", frmdt);
                entitymap.put("todt",todt);
            }


            if(recognitionGiven) {
                Log.i("responstring1", "getrecognitiongivenlist serviceUrl-->" + Constant.BASEURL + "api/userapi/getRecognitionReceived");
//                responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/getrecognitiongivenlist");
                responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/getRecognitionReceived");

            }

            else{
                Log.i("responstring2", "getrecognitionRecievelist serviceUrl-->" + Constant.BASEURL + "api/userapi/getRecognitionReceived");
                responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/getRecognitionReceived");
            }

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsegetRecognitionReceivedData(responseString);
    }


    public String parsegetRecognitionReceivedData(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession=getDBSessoin(context);
        daoSession.getRecognitionRecievedDao().deleteAll();
        RecognitionRecievedDao recognitionRecievedListDao=daoSession.getRecognitionRecievedDao();
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

                                RecognitionRecieved recognitionGivenList = new RecognitionRecieved(awardid,awardName,count,total);
                                recognitionRecievedListDao.insertOrReplace(recognitionGivenList);

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

    public String parseRecognitionRecievedListTaskData(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession=getDBSessoin(context);
        daoSession.getRecognitionRecievedListDao().deleteAll();
        RecognitionRecievedListDao recognitionRecievedListDao=daoSession.getRecognitionRecievedListDao();
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
                                String nominee_name = jsonObject1.optString("nominee_name");
                                String nominee = jsonObject1.optString("nominee");
                                String nomineedesignation = jsonObject1.optString("nomineedesignation");
                                String recognition_id = jsonObject1.optString("recognition_id");
                                String details = jsonObject1.optString("details");
                                String count = jsonObject1.optString("count");
                                String imageurl = jsonObject1.optString("imageurl");
                                String recognise_date = jsonObject1.optString("recognise_date");
                                String recognition_name = jsonObject1.optString("recognition_name");
                                String is_story = jsonObject1.optString("is_story");
                                String isxpress = jsonObject1.optString("isxpress");

                                RecognitionRecievedList recognitionGivenList = new RecognitionRecievedList(
                                        nominee_name,nominee,nomineedesignation,recognition_id,details,count,
                                        imageurl,recognise_date,recognition_name,is_story,isxpress);

                                recognitionRecievedListDao.insertOrReplace(recognitionGivenList);

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



    public String callRecognitionRecivedAndGivenListManager(String userid,String awardid,String frmdt,String todt) {
        try {

            HashMap<String, String> entitymap = new HashMap<String, String>();
            String nominator_id = SharedPrefrenceManager.getUserID(context);
            if(userid.equals("") && awardid.equals("") && frmdt.equals("") && todt.equals("")){

                Log.i("userid","0");
                Log.i("awardid", "0");
                Log.i("nominator_id", userid);

                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String todayDate= sdf.format(date);

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, -30);
                Date result = cal.getTime();
                String beforeOneMonthDate= sdf.format(result);

                Log.i("frmdt",beforeOneMonthDate);
                Log.i("todt",todayDate);

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
                entitymap.put("awardid",awardid);
                entitymap.put("nominator_id", nominator_id);
                entitymap.put("frmdt", frmdt);
                entitymap.put("todt",todt);
            }


            if(recognitionGiven) {
                Log.i("responstring1", "getrecognitiongivenlist serviceUrl-->" + Constant.BASEURL + "api/userapi/getrecognitiongivenlist");
               // responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/getrecognitiongivenlist");
                responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/getrecognitiongiven");
               // http://newxpress.wsisrdev.com/xpmob/api/userapi/getrecognitiongiven
            }

            else{
                Log.i("responstring2", "getrecognitionRecievelist serviceUrl-->" + Constant.BASEURL + "api/userapi/getrecognitionRecievelist");
                responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/getrecognitionRecievelist");
            }

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsegetrecognitiongivenData(responseString);
    }



    public String parsegetrecognitiongivenData(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession=getDBSessoin(context);
       // RecognitionGiven
        daoSession.getRecognitionGivenDao().deleteAll();
        RecognitionGivenDao recognitionGivenListDao=daoSession.getRecognitionGivenDao();
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
                            Log.i("responseString1", "responseString1=" + jsonarray.length());

                            for(int i=0;i<jsonarray.length();i++)
                            {

                                JSONObject jsonObject1=jsonarray.getJSONObject(i);
                                String awardid = jsonObject1.optString("awardid");
                                String awardName = jsonObject1.optString("awardName");
                                String count = jsonObject1.optString("count");
                                String total = jsonObject1.optString("total");

                                RecognitionGiven recognitionGivenList = new RecognitionGiven(awardid,awardName,count,total);
                                recognitionGivenListDao.insertOrReplace(recognitionGivenList);

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


    public String parseRecognitionRecivedAndGivenListManagerData(String jsonResponse) {
        String responseCode = "";
        DaoSession daoSession=getDBSessoin(context);
        daoSession.getRecognitionGivenListDao().deleteAll();
        RecognitionGivenListDao recognitionGivenListDao=daoSession.getRecognitionGivenListDao();
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
                            Log.i("responseString1", "responseString1=" + jsonarray.length());

                           for(int i=0;i<jsonarray.length();i++) {


                                JSONObject jsonObject1=jsonarray.getJSONObject(i);
                                String nominee_name = jsonObject1.optString("nominee_name");
                                String nominee = jsonObject1.optString("nominee");
                                String nomineedesignation = jsonObject1.optString("nomineedesignation");
                                String recognition_id = jsonObject1.optString("recognition_id");
                                String details = jsonObject1.optString("details");
                                String count = jsonObject1.optString("count");
                                String imageurl = jsonObject1.optString("imageurl");
                                String recognise_date = jsonObject1.optString("recognise_date");
                                String recognition_name = jsonObject1.optString("recognition_name");
                                String is_story = jsonObject1.optString("is_story");
                                String isxpress = jsonObject1.optString("isxpress");

                                RecognitionGivenList recognitionGivenList = new RecognitionGivenList(
                                        nominee_name,nominee,nomineedesignation,recognition_id,details,count,
                                        imageurl,recognise_date,recognition_name,is_story,isxpress);

                               recognitionGivenListDao.insertOrReplace(recognitionGivenList);

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


    public List<RecognitionGivenList> getRecognitionGivenList() {
        DaoSession daoSession = getDBSessoin(context);
        RecognitionGivenListDao recognitionGivenListDao=daoSession.getRecognitionGivenListDao();
        try {
            List<RecognitionGivenList> recognitionGivenLists = new ArrayList<RecognitionGivenList>();
            recognitionGivenLists = recognitionGivenListDao.loadAll();
            return recognitionGivenLists;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<RecognitionGiven> getRecognitionGivens()
    {
        DaoSession daoSession = getDBSessoin(context);
        RecognitionGivenDao recognitionGivenDao=daoSession.getRecognitionGivenDao();
        try
        {
            List<RecognitionGiven> recognitionGivens= new ArrayList<>();
            recognitionGivens=recognitionGivenDao.loadAll();
            return recognitionGivens;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<RecognitionRecieved> getRecognitionRecieveds()
    {
        DaoSession daoSession = getDBSessoin(context);
        RecognitionRecievedDao recognitionRecievedDao=daoSession.getRecognitionRecievedDao();
        try {
            List<RecognitionRecieved> recognitionRecieveds=new ArrayList<>();
            recognitionRecieveds=recognitionRecievedDao.loadAll();
            return recognitionRecieveds;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<RecognitionRecievedList> getRecognitionRecievedLists()
    {
        DaoSession daoSession = getDBSessoin(context);
        RecognitionRecievedListDao recognitionRecievedListDao=daoSession.getRecognitionRecievedListDao();
        try {
            List<RecognitionRecievedList> recognitionRecievedLists=new ArrayList<>();
            recognitionRecievedLists=recognitionRecievedListDao.loadAll();
            return recognitionRecievedLists;
        }

        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
