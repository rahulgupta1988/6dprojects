package expression.sixdexp.com.expressionapp.xpassions.gpmanager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.ServerCall;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import model.GroupObject;
import model.SearchGroupObject;

/**
 * Created by Praveen on 21-Feb-18.
 */

public class UsersGroupsManager extends  BaseManager {

    Context _mContext;
    String responseString = "";
    public List<SearchGroupObject> searchGroupObjects=new ArrayList<>();


    public  UsersGroupsManager(Context _mContext){
        this._mContext = _mContext;
    }


    public String getGroups(String userid) {

        try {
            Log.i("responstring", "search group serviceUrl-->" + Constant.BASEURL+"api/coi/coi_getgrouplist");
            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("userid",userid);
            entitymap.put("mygroup","1");
            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/coi/coi_getgrouplist");

            if (responseString != null)
                Log.i("responseString", "responseString=" + responseString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseData(responseString);
    }

    public String parseData(String jsonResponse)
    {

        String responseCode = "";

        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(_mContext).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");

                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString=responseCode;

                            JSONArray jsonArray=jsonObject.getJSONArray("responseData");
                            for(int k=0;k<jsonArray.length();k++){

                                SearchGroupObject groupObject_tem=new SearchGroupObject();
                                JSONObject gp_obj=jsonArray.getJSONObject(k);



                                String coi_gid=gp_obj.getString("coi_gid");
                                String coi_gtitle=gp_obj.getString("coi_gtitle");
                                String coi_gdescription=gp_obj.getString("coi_gdescription");
                                String coi_gicon=gp_obj.getString("coi_gicon");
                                String coi_gisactive=gp_obj.getString("coi_gisactive");
                                String coi_gadminid=gp_obj.getString("coi_gadminid");
                                String coi_gispublic=gp_obj.getString("coi_gispublic");
                                String coi_gaddeddate=gp_obj.getString("coi_gaddeddate");
                                String noofpendingrequest="";
                                String noofmember=gp_obj.getString("noofmember");
                                if(gp_obj.has("noofpendingrequest")) noofpendingrequest=gp_obj.getString("noofpendingrequest");
                                String noofview=gp_obj.getString("noofview");
                                String themeid=gp_obj.getString("themeid");



                                groupObject_tem.setCoi_gid(coi_gid);
                                groupObject_tem.setCoi_gtitle(coi_gtitle);
                                groupObject_tem.setCoi_gdescription(coi_gdescription);
                                groupObject_tem.setCoi_gicon(coi_gicon);
                                groupObject_tem.setCoi_gisactive(coi_gisactive);
                                groupObject_tem.setCoi_gadminid(coi_gadminid);
                                groupObject_tem.setCoi_gispublic(coi_gispublic);
                                groupObject_tem.setCoi_gaddeddate(coi_gaddeddate);
                                groupObject_tem.setNoofmember(noofmember);
                                groupObject_tem.setNoofpendingrequest(noofpendingrequest);
                                groupObject_tem.setNoofview(noofview);
                                groupObject_tem.setThemeid(themeid);
                                searchGroupObjects.add(groupObject_tem);
                            }


                        }
                        else
                        {
                            responseString = jsonObject.getString("responseData");
                        }
                    }else {
                        responseString=jsonResponse;
                    }
                } else {
                    responseString=jsonResponse;
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
