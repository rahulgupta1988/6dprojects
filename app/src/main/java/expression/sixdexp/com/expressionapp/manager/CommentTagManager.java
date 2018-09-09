package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import model.TagPostModel;

/**
 * Created by Praveen on 19-Sep-17.
 */

public class CommentTagManager extends BaseManager {

    Context context;
    String responseString = "";


    public CommentTagManager(Context context) {
        this.context = context;

    }

    public String callTagedUser(String recognitionid,String commentid) {

        try {
            String userid=new LoginManager(context).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "getcommentbytaguser serviceUrl-->" + Constant.BASEURL + "api/tagging/getcommentbytaguser");

            entitymap.put("userid", userid);
            entitymap.put("postid", recognitionid);
            entitymap.put("commentid", commentid);
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/tagging/getcommentbytaguser");

            if (responseString != null)
                Log.i("getcommentbytaguser", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseTagedData(responseString);
    }


    public String parseTagedData(String jsonResponse) {
        String responseCode = "";
        TagPostManager.tagPostModels.clear();
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

                            for (int i = 0; i < jsonarray.length(); i++) {
                                TagPostModel tagPostModel=new TagPostModel();
                                JSONObject jsonObject1 = jsonarray.getJSONObject(i);

                                String name = jsonObject1.optString("name");
                                String userid = jsonObject1.optString("userid");
                                String imageurl = jsonObject1.optString("imageurl");
                                String designation = jsonObject1.optString("designation");
                                tagPostModel.setName(name);
                                tagPostModel.setUserid(userid);
                                tagPostModel.setImageurl(imageurl);
                                tagPostModel.setDesignation(designation);

                                TagPostManager.tagPostModels.add(tagPostModel);
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


}

