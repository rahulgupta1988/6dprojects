package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import expression.sixdexp.com.expressionapp.adapter.ClusterPolicyAdapter;
import expression.sixdexp.com.expressionapp.adapter.SrijanPolicyAdapter;
import expression.sixdexp.com.expressionapp.manager.ClusesterPolicyManager;
import expression.sixdexp.com.expressionapp.manager.SrijanPolicyListManager;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;

/**
 * Created by hp on 8/15/2017.
 */

public class ClusterPolicyListActivity extends Activity {

    Context mContext;
    RecyclerView poliecies_list;
    ProgressDialog progressDialog;
    String clusterID="0";
    String clusterName="";
    TextView cluster_name;
    ImageView back_ic;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
         setContentView(R.layout.clusterpolicylistview);
        Intent intent=getIntent();
        if(intent!=null){
            clusterID=intent.getStringExtra("clusterID");
            clusterName=intent.getStringExtra("clusterName");
            new GetPolicies().execute();
        }
        initViews();
    }

    public void initViews(){
        cluster_name=(TextView)findViewById(R.id.cluster_name);
        back_ic=(ImageView)findViewById(R.id.back_ic);
        back_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        cluster_name.setText(""+clusterName+" - Policies");
    }

    public void setPolicyList(){
        poliecies_list=(RecyclerView)findViewById(R.id.poliecies_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        poliecies_list.setLayoutManager(layoutManager);
        poliecies_list.setItemAnimator(new DefaultItemAnimator());
        poliecies_list.setAdapter(new ClusterPolicyAdapter(mContext));
    }

    public class GetPolicies extends AsyncTask<String, Void, Void> {

        String responseString = "";
        ClusesterPolicyManager callSrijanPolicy = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            if (progressDialog == null) {
                progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            }
            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }

        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                callSrijanPolicy = new ClusesterPolicyManager(mContext);
                responseString = callSrijanPolicy.callClusterPolicy(clusterID);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog.dismiss();

            if(responseString.equals("100")){
                setPolicyList();
            }

            else{
                Toast.makeText(mContext,""+responseString,Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
