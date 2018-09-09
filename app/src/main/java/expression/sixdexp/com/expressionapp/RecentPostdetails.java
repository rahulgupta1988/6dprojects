package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import expression.sixdexp.com.expressionapp.adapter.GetMorePostAdapterTest;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.manager.SinglePostManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;

/**
 * Created by Praveen on 25-Jan-17.
 */

public class RecentPostdetails extends Activity implements View.OnClickListener{

    RecyclerView postlist;
    Context mContext;
    GetMorePostAdapterTest getMorePostAdapter;
    String postId;
    ProgressDialog progressDialog;
    LinearLayout cancelshare;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postdetailsview);
        mContext = this;
        Constant.singlePostList.clear();
        postId =getIntent().getStringExtra("postId");
        Log.i("postId",""+postId);
        initView();
    }

    public void initView() {

        postlist = (RecyclerView) findViewById(R.id.postlist);
        cancelshare = (LinearLayout) findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(this);
        if (postId != null && !postId.equalsIgnoreCase("")) {
            Constant.singlePostList=new GetMorePostManager(mContext).getPostsByPostID(postId);
            if(Constant.singlePostList!=null){
                if(Constant.singlePostList.size()>0){
                    setPost();
                }

                else{
                    new GetPost().execute();
                }
            }

            else{
                new GetPost().execute();
            }

        } else {
            Toast.makeText(mContext, "PostID Should not be null", Toast.LENGTH_LONG).show();
        }
    }

    LinearLayoutManager layoutManager;
    /*public void setScroll(final int lay_height){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (postlist != null) {
                    postlist.scrollBy(0,postlist.getHeight());
                }
            }
        });
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        //setPost();
    }

    public void setPost() {


        getMorePostAdapter = new GetMorePostAdapterTest(mContext, new GetMorePostAdapterTest.MorePostInterface() {
            @Override
            public void getMorePost() {


            }

            @Override
            public void commentAt(int commentat) {

            }

            @Override
            public void scrollList() {

            }
        }, postId);
        layoutManager = new LinearLayoutManager(mContext);
        postlist.setLayoutManager(layoutManager);
        postlist.setItemAnimator(new DefaultItemAnimator());
        postlist.setAdapter(getMorePostAdapter);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.cancelshare:
                onBackPressed();
                break;
        }

    }

    public class GetPost extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);

        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new SinglePostManager(mContext).callPost(postId);

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

                setPost();


            } else {

                Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

            }

        }


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Toast.makeText(mContext,"backpressed",Toast.LENGTH_LONG).show();
       /* Intent intent=new Intent(mContext,FlipComplexLayoutActivity.class);
        startActivity(intent);
        finish();*/

    }



}
