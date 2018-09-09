package expression.sixdexp.com.expressionapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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
import expression.sixdexp.com.expressionapp.manager.WhatsTrendingManager;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;

/**
 * Created by Praveen on 9/13/2016.
 */
public class PowerTrendFragment extends android.support.v4.app.Fragment{

    Context mContext;
    View view;
    RecyclerView trendlist;
    CoordinatorLayout trendingcoorinate;
    //TextView trendingtypetxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tatatrenfagview, container, false);
        trendingcoorinate = (CoordinatorLayout) view.findViewById(R.id.trendingcoorinate);
//        trendingtypetxt=(TextView)view.findViewById(R.id.trendingtypetxt);
//        trendingtypetxt.setText("Power Sector");
        new TrendingBackTask().execute();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = (Context) activity;
    }

    void initTrendList() {
        trendlist = (RecyclerView) view.findViewById(R.id.trendlist);
        WhatsTrendingAdapter whatsTrendingAdapter = new WhatsTrendingAdapter(mContext);
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

            responseString = new WhatsTrendingManager(mContext).callForWhatsTrending("powersector");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();


            if (responseString.equals("100")) {
                initTrendList();
                if (WhatsTrendingManager.whatsTrendingBeans.size() == 0)
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
