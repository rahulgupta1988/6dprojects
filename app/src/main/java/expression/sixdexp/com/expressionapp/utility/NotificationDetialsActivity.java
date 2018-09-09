package expression.sixdexp.com.expressionapp.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.expression.ui.FlipComplexLayoutActivity;

import expression.sixdexp.com.expressionapp.HostActivty;
import expression.sixdexp.com.expressionapp.LoginActivity;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.adapter.GetMorePostAdapter;
import expression.sixdexp.com.expressionapp.manager.SinglePostManager;

/**
 * Created by Praveen on 8/29/2016.
 */
public class NotificationDetialsActivity  extends Activity implements View.OnClickListener {


    RecyclerView postlist;
    Context mContext;
    GetMorePostAdapter getMorePostAdapter;
    ImageView cancelshare;
    String postId;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.postdetailsview);
            Constant.singlePostList.clear();
            postId = getIntent().getStringExtra("postId");
            Log.i("postId", "" + postId);
            initView();

    }

    public void initView() {
        postlist = (RecyclerView) findViewById(R.id.postlist);
        cancelshare = (ImageView) findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(this);
        if (postId != null && !postId.equalsIgnoreCase("")) {

            new GetPost().execute();
        } else {
            Toast.makeText(mContext, "PostID Should not be null", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        setPost();
    }

    public void setPost() {


        getMorePostAdapter = new GetMorePostAdapter(mContext, new GetMorePostAdapter.MorePostInterface() {
            @Override
            public void getMorePost() {


            }

            @Override
            public void commentAt(int commentat) {

            }
        }, postId);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
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
            SpannableString ss2 = new SpannableString(s);
            ss2.setSpan(new RelativeSizeSpan(1.3f), 0, ss2.length(), 0);
            ss2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, ss2.length(), 0);
            progressDialog = new ProgressDialog(mContext,
                    android.R.style.Theme_DeviceDefault_Light_Dialog);
            Window window = progressDialog.getWindow();
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setMessage(ss2);
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            progressDialog.show();
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
                // setPost();
            }

        }


    }



    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
        //Toast.makeText(mContext,"backpressed",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(mContext,FlipComplexLayoutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
