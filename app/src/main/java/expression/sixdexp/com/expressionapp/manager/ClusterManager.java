package expression.sixdexp.com.expressionapp.manager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import db.Cluster;
import db.ClusterDao;
import db.DaoSession;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;

/**
 * Created by Praveen on 01-Aug-17.
 */

public class ClusterManager extends BaseManager {

    Context context;
    String responseString="";


    public ClusterManager (Context context) {
        this.context=context;

    }

    public String callCluster()
    {

        try {

            HashMap<String,String> entitymap=new HashMap<String,String>();
            Log.i("responstring", "getcluster serviceUrl-->" + Constant.BASEURL + "api/srijan/getcluster");
            responseString=ServerCall.makeConnection(Constant.BASEURL,entitymap,"api/srijan/getcluster");

            if (responseString!=null)
                Log.i("responseString", "responseString=" + responseString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parseComentData(responseString);
    }


    public String parseComentData(String jsonResponse)
    {
        String responseCode = "";
        DaoSession daoSession=getDBSessoin(context);
        ClusterDao clusterDao=daoSession.getClusterDao();
        clusterDao.deleteAll();
        try {
            if (jsonResponse != null && !jsonResponse.equals(null) &&  new InternetConnectionDetector(context).isConnectingToInternet()) {
                Object object = new JSONTokener(jsonResponse).nextValue();
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    if (jsonObject.has("responseCode"))
                    {
                        responseCode = jsonObject.getString("responseCode");
                        if (responseCode.equalsIgnoreCase("100"))
                        {
                            responseString = responseCode;

                            JSONArray responseData_json=jsonObject.getJSONArray("responseData");

                            for (int i=0;i<responseData_json.length();i++){
                                JSONObject jObj=responseData_json.getJSONObject(i);
                                String clusterid=jObj.getString("clusterid");
                                String clustername=jObj.getString("clustername");
                                Cluster cluster=new Cluster(clusterid,clustername);
                                clusterDao.insertOrReplace(cluster);
                            }

                        }
                        else
                        {
                            responseString = jsonObject.getString("responseData");
                        }
                    }else {
                        responseString="Received data is not compatible.";
                    }
                } else {
                    responseString=responseString;
                }
            } else {
                responseString = "Please check your internet connection.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseString;
    }


    public List<Cluster> getClusters(){
        DaoSession daoSession=getDBSessoin(context);
        ClusterDao clusterDao=daoSession.getClusterDao();
        List<Cluster> clusters=clusterDao.loadAll();
        Collections.sort(clusters,clusterComparator);
        return clusters;
    }


    public Comparator<Cluster> clusterComparator = new Comparator<Cluster>() {

        public int compare(Cluster s1, Cluster s2) {
            String cluster1 = s1.getClustername().toUpperCase();
            String cluster2 = s2.getClustername().toUpperCase();

            //ascending order
            return cluster1.compareTo(cluster2);

            //descending order
            // return cluster2.compareTo(cluster1);
        }};


}

