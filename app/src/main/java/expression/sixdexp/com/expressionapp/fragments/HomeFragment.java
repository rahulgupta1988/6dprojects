package expression.sixdexp.com.expressionapp.fragments;

import android.app.Activity;
/*import android.app.Fragment;*/
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.RecogniseActivity;
import expression.sixdexp.com.expressionapp.ShareActivity;
import expression.sixdexp.com.expressionapp.TellaTaleActivity;
import expression.sixdexp.com.expressionapp.adapter.GetMorePostAdapter;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.ServiceTocheckUserAuth;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;

;

/**
 * Created by Praveen on 6/30/2016.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    static int commentAt = 0;

    View view;
    //ListView postlist;

    RecyclerView postlist;
    Context mContext;
    LinearLayout sharetab, recognisetab, telltab;
    GetMorePostAdapter getMorePostAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.homefragmentview, container, false);

        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/OpenSans-Regular.ttf");
        TextView shareupdatetxt = (TextView) view.findViewById(R.id.shareupdatetxt);
        TextView recotxt = (TextView) view.findViewById(R.id.recotxt);
        TextView telltxt = (TextView) view.findViewById(R.id.telltxt);
        shareupdatetxt.setTypeface(typeface);
        recotxt.setTypeface(typeface);
        telltxt.setTypeface(typeface);

        sharetab = (LinearLayout) view.findViewById(R.id.sharetab);
        recognisetab = (LinearLayout) view.findViewById(R.id.recognisetab);
        telltab = (LinearLayout) view.findViewById(R.id.telltab);
        sharetab.setOnClickListener(this);
        recognisetab.setOnClickListener(this);
        telltab.setOnClickListener(this);
        postlist = (RecyclerView) view.findViewById(R.id.postlist);
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i("onStart", "onStart");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
/*
        // vistor service
        Intent visitor_counter_intent = new Intent(mContext, VisitorCounterService.class);
        mContext.startService(visitor_counter_intent);*/


        //Login Service

        Intent intent = new Intent(mContext, ServiceTocheckUserAuth.class);
        String userID = SharedPrefrenceManager.getLoginID(mContext);
        String password = SharedPrefrenceManager.getPassword(mContext);
        intent.putExtra("userid", userID);
        intent.putExtra("passw", password);
        mContext.startService(intent);


        Log.i("onActivityCreated", "onActivityCreated");
        new GetMorePostTask().execute();

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i("onresume", "onresume");
        setPostList();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i("onAttach", "onAttach");
        mContext = (Context) activity;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sharetab:
                Intent shareactvityintent = new Intent(mContext, ShareActivity.class);
                startActivity(shareactvityintent);
                break;
            case R.id.recognisetab:
                Intent recognizeActivityIntent = new Intent(mContext, RecogniseActivity.class);
                startActivity(recognizeActivityIntent);
                break;

            case R.id.telltab:
                Intent tellaTaleActivityIntent = new Intent(mContext, TellaTaleActivity.class);
                startActivity(tellaTaleActivityIntent);
                break;
        }

    }

    public class GetMorePostTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);//((HostActivty) mContext).initProgressDialog();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new GetMorePostManager(mContext).callGetMorePost();
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

                setPostList();


            } else {

                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                //progressDialog.dismiss();


            }

        }

    }

    public void setPostList() {

        postlist.invalidate();
        if (getMorePostAdapter != null)
            getMorePostAdapter = null;

        getMorePostAdapter = new GetMorePostAdapter(mContext, new GetMorePostAdapter.MorePostInterface()
        {
            @Override
            public void getMorePost()
            {
                //Toast.makeText(mContext,"data changed",Toast.LENGTH_LONG).show();
                //postlist.invalidate();
                //postlist.getAdapter().notifyDataSetChanged();
                /*Intent home_intent = new Intent(mContext, HostActivty.class);
                mContext.startActivity(home_intent);
                ((Activity)mContext).finish();*/
                setPostList();
            }

            @Override
            public void commentAt(int commentat) {

                commentAt = commentat;

            }


        }, "");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        postlist.setLayoutManager(layoutManager);
        postlist.setItemAnimator(new DefaultItemAnimator());
        postlist.setAdapter(getMorePostAdapter);

        if (Constant.lastpostofPOST != 0) {
            if (((Constant.lastpostofPOST) + 1) == new GetMorePostManager(mContext).getMorePosts().size()) {
                postlist.scrollToPosition(Constant.lastpostofPOST);
            } else {
                postlist.scrollToPosition((Constant.lastpostofPOST) + 1);
            }
        }

        Constant.lastpostofPOST = 0;

        if (commentAt != 0) {
            postlist.scrollToPosition(commentAt);
            commentAt = 0;
        }

    }


}
