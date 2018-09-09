package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import db.DaoSession;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 7/13/2016.
 */
public class RecognizeSomeOneManager extends BaseManager {


    Context _mContext;
    String responseString = "";

    public RecognizeSomeOneManager(Context context) {
        _mContext = context;

    }


    public String postRecognizeSomeOne(String cv,String extrnlusr,String cc,String uid,String rid,
                                  String descp,String ftype,String vdourl,String xprssway,String xprsswaytxt,
                                  String userid,String base64_strig,String filename,String file_extension,
                                  String content_type,String taguser) {

        try {


            Log.i("cv11", cv);
            Log.i("extrnlusr", extrnlusr);
            Log.i("cc", cc);
            Log.i("uid", uid);
            Log.i("awardid", rid);
            Log.i("descp", descp);
            Log.i("ftype", ftype);
            Log.i("vdourl", vdourl);
            Log.i("xprssway", xprssway);
            Log.i("xprsswaytxt", xprsswaytxt);
            Log.i("userid", userid);
            //Log.i("base64_string", base64_strig);
           // Log.i("filename", filename);
            Log.i("file_extension", file_extension);
            Log.i("content_type", content_type);
            Log.i("taguser",taguser);

            Log.i("responstring", "recognizesomeone serviceUrl-->" + Constant.BASEURL
                    + "&param=api/userapi/recognizesomeone");


            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("cv", cv);
            entitymap.put("extrnlusr", extrnlusr);
            entitymap.put("cc", cc);
            entitymap.put("uid", uid);
            entitymap.put("awardid", rid);
            entitymap.put("descp", descp);
            entitymap.put("ftype", ftype);
            entitymap.put("vdourl", vdourl);
            entitymap.put("xprssway", xprssway);
            entitymap.put("xprsswaytxt", xprsswaytxt);
            entitymap.put("userid", userid);
            entitymap.put("base64_string", base64_strig);
            entitymap.put("filename", filename);
            entitymap.put("file_extension", file_extension);
            entitymap.put("content_type", content_type);
            entitymap.put("devicetype", "android");
            entitymap.put("taguser",taguser);



            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/recognizesomeone");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseRecognizeData(responseString);
    }


    public String parseRecognizeData(String jsonResponse) {

        String responseCode = "";
        DaoSession daoSession = getDBSessoin(_mContext);

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(_mContext).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;

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

}
