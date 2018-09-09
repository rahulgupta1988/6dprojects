package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import db.DaoSession;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;

/**
 * Created by Praveen on 7/8/2016.
 */
public class ShareAnUpdateManager extends BaseManager {

    Context _mContext;
    String responseString = "";

    public ShareAnUpdateManager(Context context) {
        _mContext = context;

    }


    public String postUpdateShare(String file_name,String base64, String videolink,
                                  String sharedtxt, String ftype,
                                  String file_extension,String content_type,
                                  String xprssway,String xwaysubject_txt,String emailID,String select_category_id,
                                  String taguser) {

        try {

            Log.i("responstring", "shareandupdate serviceUrl-->" + Constant.BASEURL
                    + "&param=api/userapi/shareandupdate"
            /*+"&base64_string="+ base64*/
                    + "&vdourl=" + ""
                    + "&descp=" + sharedtxt
                    + "&ftype=" + ftype
                    + "&file_extension=" + file_extension);



            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("grpalias", "");
            entitymap.put("descp", sharedtxt);
            entitymap.put("ftype", ftype);
            entitymap.put("vdourl", videolink);
            entitymap.put("xprssway",xprssway);
            entitymap.put("xprsswaytxt",xwaysubject_txt);
            entitymap.put("userid", SharedPrefrenceManager.getUserID(_mContext));
            entitymap.put("base64_string", base64);
            entitymap.put("filename",file_name);
            entitymap.put("file_extension", file_extension);
            entitymap.put("content_type",content_type);
            entitymap.put("devicetype", "android");
            entitymap.put("grpemail",emailID);
            entitymap.put("bcatid", select_category_id);
            entitymap.put("taguser",taguser);




            Log.i("hello XB 436435656", "Hi XB");
            Log.i("grpalias", "");
            Log.i("descp", sharedtxt);
            Log.i("ftype", ftype);
            Log.i("vdourl", videolink);
            Log.i("xprssway",xprssway);
            Log.i("xprsswaytxt",xwaysubject_txt);
            Log.i("userid", SharedPrefrenceManager.getUserID(_mContext));
            Log.i("base64_string", base64);
            Log.i("filename",file_name);
            Log.i("file_extension", file_extension);
            Log.i("content_type",content_type);
            Log.i("devicetype", "android");
            Log.i("grpemail",emailID);
            Log.i("bcatid", select_category_id);
            Log.i("taguser",taguser);


            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/shareandupdate");


            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseShareData(responseString);
    }




    public String parseShareData(String jsonResponse) {

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
