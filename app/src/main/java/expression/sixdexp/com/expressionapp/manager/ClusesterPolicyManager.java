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
 * Created by hp on 8/15/2017.
 */

public class ClusesterPolicyManager extends BaseManager {

    Context context;
    String responseString = "";
    public static List<ClusterPolicyModel> clusterPolicyModels = new ArrayList<>();

    public ClusesterPolicyManager(Context context) {
        this.context = context;

    }

    public String callClusterPolicy(String clusterid) {

        try {

            HashMap<String, String> entitymap = new HashMap<String, String>();
            Log.i("responstring", "getpolicybyclusterid serviceUrl-->" + Constant.BASEURL + "api/srijan/getpolicybyclusterid");
            entitymap.put("clusterid", clusterid);

            responseString = ServerCall.makeConnection(Constant.BASEURL, entitymap, "api/srijan/getpolicybyclusterid");

            if (responseString != null)
                Log.i("getpolicybyclusterid", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseComentData(responseString);
    }


    public String parseComentData(String jsonResponse) {
        String responseCode = "";
        clusterPolicyModels.clear();
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) && new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode")) {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100")) {
                            responseString = responseCode;

                            JSONArray responseData_json = jsonObject.getJSONArray("responseData");
                            for (int i = 0; i < responseData_json.length(); i++) {
                                ClusterPolicyModel clusterPolicyModel = new ClusterPolicyModel();
                                JSONObject jsonObject1 = responseData_json.getJSONObject(i);
                                String policyid = jsonObject1.getString("policyid");
                                String policyname = jsonObject1.getString("policyname");

                                clusterPolicyModel.setPolicyid(policyid);
                                clusterPolicyModel.setPolicyname(policyname);

                                clusterPolicyModels.add(clusterPolicyModel);

                            }

                        } else {
                            responseString = jsonObject.getString("responseData");
                        }
                    } else {
                        responseString = "Received data is not compatible.";
                    }
                } else {
                    responseString = responseString;
                }
            } else {
                responseString = "Please check your internet connection.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseString;
    }

    public class ClusterPolicyModel {
        String policyid;
        String policyname;

        public String getPolicyid() {
            return policyid;
        }

        public void setPolicyid(String policyid) {
            this.policyid = policyid;
        }

        public String getPolicyname() {
            return policyname;
        }

        public void setPolicyname(String policyname) {
            this.policyname = policyname;
        }
    }

}

