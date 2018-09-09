package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by hp on 8/13/2017.
 */

public class EmoticonLikeUnlikeManager extends BaseManager {

    Context context;
    String responseString = "";
    public static List<EmoticonModel> emoticonModelList=new ArrayList<>();

    public EmoticonLikeUnlikeManager(Context context) {
        this.context = context;

    }

    public String callLikeEmoticon(String policyid,String emojiid) {

        try {
            String userid = new LoginManager(context).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "addemojilikeunlike serviceUrl-->" + Constant.BASEURL + "api/srijan/addemojilikeunlike");

            entitymap.put("policyid", policyid);
            entitymap.put("emojiid", emojiid);
            entitymap.put("userid", userid);
            entitymap.put("devicetype", "android");
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/srijan/addemojilikeunlike");

            if (responseString != null)
                Log.i("addemojilikeunlike", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseLikeGivenData(responseString);
    }


    public String parseLikeGivenData(String jsonResponse) {
        String responseCode = "";

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) && new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;
                            JSONArray jsonArray = jsonObject.getJSONArray("responseData");

                            for(int i=0;i<jsonArray.length();i++){
                                EmoticonModel emoticonModel=new EmoticonModel();
                                JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                emoticonModel.setEmocount(jsonObject1.getString("emocount"));
                                emoticonModel.setEmoid(jsonObject1.getString("emoid"));
                                emoticonModel.setEmopercentage(jsonObject1.getString("emopercentage"));
                                emoticonModel.setPolicyid(jsonObject1.getString("policyid"));
                                emoticonModelList.add(emoticonModel);
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
    public class EmoticonModel{

        String emoid;
        String emopercentage;
        String emocount;
        String policyid;

        public String getEmoid() {
            return emoid;
        }

        public void setEmoid(String emoid) {
            this.emoid = emoid;
        }

        public String getEmopercentage() {
            return emopercentage;
        }

        public void setEmopercentage(String emopercentage) {
            this.emopercentage = emopercentage;
        }

        public String getEmocount() {
            return emocount;
        }

        public void setEmocount(String emocount) {
            this.emocount = emocount;
        }

        public String getPolicyid() {
            return policyid;
        }

        public void setPolicyid(String policyid) {
            this.policyid = policyid;
        }
    }


}

