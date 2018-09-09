package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;

import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 9/13/2016.
 */
public class WhatsTrendingManager {

    String responseString = "";
    public static ArrayList<WhatsTrendingBean> whatsTrendingBeans = new ArrayList<WhatsTrendingBean>();
    Context context;

    public WhatsTrendingManager(Context context) {
        this.context= context;
    }

    public String callForWhatsTrending(String type) {

        try {

            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "Whats_trending serviceUrl-->" + Constant.BASEURL + "api/userapi/Whats_trending");

            entitymap.put("type", type);
            Log.i("type", "" + type);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/userapi/Whats_trending");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseTrendingData(responseString);
    }


    public String parseTrendingData(String jsonResponse) {
        String responseCode = "";

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            Gson gson=new Gson();
                            whatsTrendingBeans.clear();
                            responseString = responseCode;
                            JSONArray jsonarray = jsonObject.getJSONArray("responseData");

                            for (int i = 0; i < jsonarray.length(); i++) {

                                JSONObject jsonObject1 = jsonarray.getJSONObject(i);

                                WhatsTrendingBean whatsTrendingBean =gson.fromJson(jsonObject1.toString(),WhatsTrendingBean.class);

                                String title="";
                                String desc="";
                                String link="";
                                String date="";

                             /*   if(jsonObject1.has("title"))
                                    title = jsonObject1.optString("title");
                                if(jsonObject1.has("desc"))
                                    desc=jsonObject1.optString("desc");
                                if(jsonObject1.has("link"))
                                    link = jsonObject1.optString("link");
                                if(jsonObject1.has("date"))
                                    date = jsonObject1.optString("date");

                                WhatsTrendingBean whatsTrendingBean = new WhatsTrendingBean();
                                whatsTrendingBean.setTitle(title);
                                whatsTrendingBean.setDesc(desc);
                                whatsTrendingBean.setLink(link);
                                whatsTrendingBean.setDate(date);*/

                                whatsTrendingBeans.add(whatsTrendingBean);




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
