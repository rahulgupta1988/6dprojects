package expression.sixdexp.com.expressionapp.xpassions.gpmanager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;

import expression.sixdexp.com.expressionapp.adapter.GroupListAdapter;
import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.ServerCall;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import model.GPActionModel;

/**
 * Created by Praveen on 02-Apr-18.
 */

public class GropuActionManager extends BaseManager {

    Context _mContext;
    String responseString = "";

    public static GPActionModel gpActionModel=new GPActionModel();


    public  GropuActionManager(Context _mContext){
        this._mContext = _mContext;

    }


    public String getGPAction() {

        try {

            String gid="0";
            if(GroupListAdapter.groupObject!=null){
                gid=GroupListAdapter.groupObject.getCoi_gid();
            }

            Log.i("responstring", "coi_getgroupActionbygid serviceUrl-->" + Constant.BASEURL+"api/coi/coi_getgroupActionbygid");
            String userid = new LoginManager(_mContext).getUserInfo().get(0).getUserid();
            HashMap<String, String> entitymap = new HashMap<String, String>();
            entitymap.put("userid",userid);
            entitymap.put("gid",gid);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/coi/coi_getgroupActionbygid");

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
                            JSONArray gpingoarray=jsonObject.getJSONArray("responseData");
                            if(gpingoarray.length()>0) {
                                JSONObject goinfobj = gpingoarray.getJSONObject(0);
                                gpActionModel.setStatus(goinfobj.getString("status"));
                                gpActionModel.setDiffdays(goinfobj.getString("diffdays"));
                                gpActionModel.setIsadmin(goinfobj.getString("isadmin"));
                                gpActionModel.setPrivategsatus(goinfobj.getString("privategsatus"));
                                gpActionModel.setIsnoatmember(goinfobj.getString("isnoatmember"));
                                gpActionModel.setCoi_gid(goinfobj.getString("coi_gid"));
                                gpActionModel.setCoi_gdescription(goinfobj.getString("coi_gdescription"));
                                gpActionModel.setCoi_gicon(goinfobj.getString("coi_gicon"));
                                gpActionModel.setCoi_gtitle(goinfobj.getString("coi_gtitle"));
                                gpActionModel.setCoi_gispublic(goinfobj.getString("coi_gispublic"));
                                gpActionModel.setThemeid(goinfobj.getString("themeid"));
                                gpActionModel.setNoofview(goinfobj.getString("noofview"));
                                gpActionModel.setNoofmember(goinfobj.getString("noofmember"));
                                gpActionModel.setMxway(goinfobj.getString("mxway"));

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
