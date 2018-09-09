package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import db.RecentActivity;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 7/19/2016.
 */
public class SearchManager extends BaseManager {

    Context context;
    String responseString = "";


    public SearchManager(Context context) {
        this.context = context;

    }

   /* public String callForSearch(String userID) {

        try {

            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "getpostdetailbyuserid serviceUrl-->" + Constant.BASEURL + "api/userapi/getpostdetailbyuserid");

            entitymap.put("userid",userID);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/getpostdetailbyuserid");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseSearchData(responseString);
    }*/


    public String callForSearch(String key_word,String key_type) {

        try {

            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "advancesearch serviceUrl-->" + Constant.BASEURL + "api/userapi/advancesearch");

            entitymap.put("search_value", key_word);
            entitymap.put("key_type", key_type);
            Log.i("search_value", "" + key_word);
            Log.i("key_type",""+key_type);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/advancesearch");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseSearchData(responseString);
    }


    public String parseSearchData(String jsonResponse) {
        String responseCode = "";

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            Constant.searchResultList.clear();
                            responseString = responseCode;
                            JSONArray jsonarray = jsonObject.getJSONArray("responseData");

                            for (int i = 0; i < jsonarray.length(); i++) {


                                JSONObject jsonObject1 = jsonarray.getJSONObject(i);
                                String nominator_name = jsonObject1.optString("nominator_name");
                                String nominee_name = jsonObject1.optString("nominee_name");
                                String recognise_date = jsonObject1.optString("recognise_date");
                                String recognition_name = jsonObject1.optString("recognition_name");
                                String is_story = jsonObject1.optString("is_story");
                                String count = jsonObject1.optString("count");
                                String recognition_id = jsonObject1.optString("recognition_id");
                                String isxpress = jsonObject1.optString("isxpress");
                                String event_date = jsonObject1.optString("event_date");
                                String type_view = jsonObject1.optString("type_view");
                                String nominee = jsonObject1.optString("nominee");
                                String award = jsonObject1.optString("award");

                                RecentActivity recentActivity = new RecentActivity(nominator_name, nominee_name, recognise_date,
                                        recognition_name, is_story, count, recognition_id,nominee,isxpress,award,type_view);

                                Constant.searchResultList.add(recentActivity);


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
