package expression.sixdexp.com.expressionapp.xpassions.gpfragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.adapter.WhatsTrendingAdapter;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.xpassions.gpadapter.GPWhatsTrendingAdapter;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GPWhatsTrendingManager;

/**
 * Created by Praveen on 05-Jan-18.
 */

public class GPWTFragment extends Fragment {
    Context mContext;
    View view;
    RecyclerView trendlist;
    CoordinatorLayout trendingcoorinate;

    public GPWTFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        view= inflater.inflate(R.layout.gpwtfragment, container, false);
        trendingcoorinate = (CoordinatorLayout) view.findViewById(R.id.trendingcoorinate);
        initView();
        new TrendingBackTask().execute();


        return view;
    }

    public void initView(){}

    void initTrendList() {
        trendlist = (RecyclerView) view.findViewById(R.id.trendlist);
        GPWhatsTrendingAdapter whatsTrendingAdapter = new GPWhatsTrendingAdapter(mContext);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        trendlist.setLayoutManager(layoutManager);
        trendlist.setItemAnimator(new DefaultItemAnimator());
        trendlist.setAdapter(whatsTrendingAdapter);
    }



    protected class TrendingBackTask extends AsyncTask<Void, Void, Void> {

        String responseString = "";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            responseString = new GPWhatsTrendingManager(mContext).callForWhatsTrending("powersector");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();


            if (responseString.equals("100")) {
                initTrendList();
                if (GPWhatsTrendingManager.whatsTrendingBeans.size() == 0)
                    showsnack("No Data Available !");
            } else {

                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
            }
        }
    }


    public void showsnack(String msg) {

        Snackbar snackbar = Snackbar
                .make(trendingcoorinate, msg, Snackbar.LENGTH_LONG);

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
        //textView.setTextColor(Color.parseColor("#0297F1"));
        textView.setTextColor(Color.YELLOW);
        snackbar.show();

    }
}
