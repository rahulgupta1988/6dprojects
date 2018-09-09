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
import model.GroupObject;

/**
 * Created by Praveen on 04-Jan-18.
 */

public class GroupsManager  extends  BaseManager {

    Context _mContext;
    String responseString = "";
    public  List<GroupObject> groups=new ArrayList<>();


    public  GroupsManager(Context _mContext){
        this._mContext = _mContext;
    }


    public String getGroups(String glastcount) {

        try {
            Log.i("glastcount132",glastcount);
            Log.i("responstring", "get groups serviceUrl-->" + Constant.BASEURL+"api/coi/coi_getgrouplist");

            String userid = new LoginManager(_mContext).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("userid",userid);
            entitymap.put("mygroup","1");
            entitymap.put("glastcount",glastcount);
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

                            JSONArray gp_array=jsonObject.getJSONArray("responseData");
                            for(int l=0;l<gp_array.length();l++){

                                JSONObject gp_obj=gp_array.getJSONObject(l);

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

                                String noofpost=gp_obj.getString("noofpost");
                                String iscreatedby=gp_obj.getString("iscreatedby");




                                GroupObject groupObject=new GroupObject();

                                groupObject.setCoi_gid(coi_gid);
                                groupObject.setCoi_gtitle(coi_gtitle);
                                groupObject.setCoi_gdescription(coi_gdescription);
                                groupObject.setCoi_gicon(coi_gicon);
                                groupObject.setCoi_gisactive(coi_gisactive);
                                groupObject.setCoi_gadminid(coi_gadminid);
                                groupObject.setCoi_gispublic(coi_gispublic);
                                groupObject.setCoi_gaddeddate(coi_gaddeddate);
                                groupObject.setNoofmember(noofmember);
                                groupObject.setNoofpendingrequest(noofpendingrequest);
                                groupObject.setNoofview(noofview);
                                groupObject.setThemeid(themeid);
                                groupObject.setNoofpost(noofpost);
                                groupObject.setIscreatedby(iscreatedby);


                                groups.add(groupObject);

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