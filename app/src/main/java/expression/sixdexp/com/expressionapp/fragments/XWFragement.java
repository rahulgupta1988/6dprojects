package expression.sixdexp.com.expressionapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import expression.sixdexp.com.expressionapp.HostActivty;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.adapter.ExpressionWayList;
import expression.sixdexp.com.expressionapp.manager.XpresswayManager;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;

/**
 * Created by Praveen on 6/30/2016.
 */
public class XWFragement extends Fragment {

    Context mContext;
    View view;
    ListView expressionwaylist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.xwfragementview, container, false);
        expressionwaylist=(ListView)view.findViewById(R.id.expressionwaylist);
        new XpresswaytTask().execute();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=(Context)activity;
    }


    public class XpresswaytTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //progressDialog= ProgressLoaderUtilities.getProgressDialog(LoginActivity.this);
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);//((HostActivty)getActivity()).initProgressDialog();
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new XpresswayManager(mContext).callXpressway();
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
            if (responseString.equals("100")) {

                ExpressionWayList expressionWayList=new ExpressionWayList(mContext);
                expressionwaylist.setAdapter(expressionWayList);

            } else {

                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

            }

        }


    }

}
