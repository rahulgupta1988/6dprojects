package expression.sixdexp.com.expressionapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.expression.ui.FlipComplexLayoutActivity;

import expression.sixdexp.com.expressionapp.adapter.TagPostAdapter;
import expression.sixdexp.com.expressionapp.manager.PolicyEmoticonDetailsManager;
import expression.sixdexp.com.expressionapp.manager.PolicyLikeUnlikeManager;
import expression.sixdexp.com.expressionapp.manager.SrijanPolicyLikeManager;
import expression.sixdexp.com.expressionapp.manager.SrijanPolicydetailManager;
import expression.sixdexp.com.expressionapp.utility.EmoticonInterface;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import model.PolicydetailModel;

/**
 * Created by Praveen on 29-Jul-17.
 */

public class SrijanPolicydetailActivity extends AppCompatActivity {

    Context mContext;
    ImageView back_ic;
    WebView policy_pdf_web;
    RelativeLayout download_task;
    ProgressBar progressDialog;
    String policyid="";
    TextView date_txt,like_count,cmt_count,view_count;
    ImageView emoticon_ic;
    LinearLayout top_lay;
    RelativeLayout comment_lay,like_lay;
    ImageView like_icon;
    TextView policy_name,policy_description;
    String fromnotification;
    RelativeLayout like_count_task;
    TextView like_users_count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.srijan_policy_detail_view);
        mContext=this;
        Intent intent=getIntent();
        if(intent!=null) {
            policyid = intent.getStringExtra("policyid");
            fromnotification=getIntent().getStringExtra("fromnotification");
        }
        initViews();
    }

    public void initViews(){
        like_users_count=(TextView)findViewById(R.id.like_users_count);
        like_count_task=(RelativeLayout)findViewById(R.id.like_count_task);
        policy_name=(TextView)findViewById(R.id.policy_name);
        policy_description=(TextView)findViewById(R.id.policy_description);
        policy_description.setMovementMethod(new ScrollingMovementMethod());
        like_icon=(ImageView)findViewById(R.id.like_icon);
        comment_lay=(RelativeLayout)findViewById(R.id.comment_lay);
        like_lay=(RelativeLayout)findViewById(R.id.like_lay);

        top_lay=(LinearLayout)findViewById(R.id.top_lay);
        emoticon_ic=(ImageView)findViewById(R.id.emoticon_ic);
        date_txt=(TextView)findViewById(R.id.date_txt);
        like_count=(TextView)findViewById(R.id.like_count);
        cmt_count=(TextView)findViewById(R.id.cmt_count);
        view_count=(TextView)findViewById(R.id.view_count);

        progressDialog=(ProgressBar)findViewById(R.id.progress_loader);
        // progressDialog= ProgressLoaderUtilities.getProgressDialog(SrijanPolicydetailActivity.this);
        download_task=(RelativeLayout)findViewById(R.id.download_task);
        back_ic=(ImageView)findViewById(R.id.back_ic);
        back_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if(policyid!=null) {
            if (policyid.length() > 0)
                new GetPolicydetail().execute();
        }

            else{
                  Toast.makeText(mContext, "Policy id should not be null", Toast.LENGTH_SHORT).show();
        }

        emoticon_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PolicyEmoticonTask().execute();
            }
        });

        comment_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PolicydetailModel policydetailModel=SrijanPolicydetailManager.policydetailModel;
                Intent intent=new Intent(mContext,PolicyCommentActivity.class);
                intent.putExtra("policyid",policyid);
              startActivity(intent);
            }
        });


        like_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PolicyLikeUnlikeTask().execute();
            }
        });

        like_count_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LikesWondow(policyid);
            }
        });


    }

    public void setPPDF(String pdfPathUrl){
        progressDialog.setVisibility(View.VISIBLE);
        policy_pdf_web=(WebView)findViewById(R.id.policy_pdf_web);
        Log.i("234 pdfPathUrl",""+pdfPathUrl);
        final String url = "https://docs.google.com/gview?embedded=true&url="+pdfPathUrl;
        Log.i("234 sdf",""+url);
        policy_pdf_web.getSettings().setJavaScriptEnabled(true);
        policy_pdf_web.setWebViewClient(new Callback());
        policy_pdf_web.loadUrl(url);

    }



    private class Callback extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(
                WebView view, String url) {
            policy_pdf_web.loadData(url, "text/html", "utf-8");
       /*     if (url != null && url.startsWith("http://")) {
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            } else {*/
                return false;
           /* }*/
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressDialog.setVisibility(View.GONE);
           /* policy_pdf_web.loadUrl("javascript:(function() { " +
                    "document.getElementsByClassName('ndfHFb-c4YZDc-GSQQnc-LgbsSe ndfHFb-c4YZDc-to915-LgbsSe VIpgJd-TzA9Ye-eEGnhe ndfHFb-c4YZDc-LgbsSe')[0].style.display='none'; })()");*/
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            progressDialog.setVisibility(View.GONE);
        }


    }

    @Override
    public void onBackPressed() {
        if(fromnotification!=null){
            finish();
            //Toast.makeText(mContext,"backpressed",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(mContext,FlipComplexLayoutActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else {
            super.onBackPressed();
        }
    }

    ProgressDialog progressDialog_custom=null;
    public class GetPolicydetail extends AsyncTask<String, Void, Void> {

        String responseString = "";
        SrijanPolicydetailManager srijanPolicydetailManager = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            if (progressDialog_custom == null) {
                progressDialog_custom = ProgressLoaderUtilities.getProgressDialog(mContext);
            }
            if (!progressDialog_custom.isShowing()) {
                progressDialog_custom.show();
            }

        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                srijanPolicydetailManager = new SrijanPolicydetailManager(mContext);
                responseString = srijanPolicydetailManager.callPdetail(policyid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog_custom.dismiss();

            if(responseString.equals("100")){
                PolicydetailModel policydetailModel=SrijanPolicydetailManager.policydetailModel;
                if(policydetailModel!=null) {
                    String pdfPathUrl = policydetailModel.getDocumentpathurl1();
                    setPPDF(pdfPathUrl);
                    downloadDOC(pdfPathUrl);
                    policy_name.setText(""+policydetailModel.getPolicyname());
                    policy_description.setText(""+policydetailModel.getPolicydescription());
                    date_txt.setText(policydetailModel.getPolicyaddeddatedisplayvalue());
                    like_count.setText(policydetailModel.getLikes());

                    String like_str="Like";
                    if(policydetailModel.getLikes().equals("1") ||policydetailModel.getLikes().equals("0"))like_str="Like";
                    else like_str="Likes";
                    like_users_count.setText(policydetailModel.getLikes()+" "+like_str);
                    cmt_count.setText(policydetailModel.getComments());
                    view_count.setText(policydetailModel.getViews());

                    if(policydetailModel.getIsloginuserliked().equals("1"))
                    like_icon.setImageResource(R.drawable.like_fill);
                    else like_icon.setImageResource(R.drawable.like);
                }
            }

            else{
                Toast.makeText(mContext,""+responseString,Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class PolicyLikeUnlikeTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        PolicyLikeUnlikeManager policyLikeUnlikeManager = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            if (progressDialog_custom == null) {
                progressDialog_custom = ProgressLoaderUtilities.getProgressDialog(mContext);
            }
            if (!progressDialog_custom.isShowing()) {
                progressDialog_custom.show();
            }

        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                policyLikeUnlikeManager = new PolicyLikeUnlikeManager(mContext);
                responseString = policyLikeUnlikeManager.callLikePolicy(policyid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog_custom.dismiss();

            if(responseString.equals("100")){

                like_count.setText(""+policyLikeUnlikeManager.likeCount);
                if(policyLikeUnlikeManager.isLike.equals("1"))
                    like_icon.setImageResource(R.drawable.like_fill);
                else like_icon.setImageResource(R.drawable.like);


                String like_str="Like";
                if(policyLikeUnlikeManager.isLike.equals("1") ||policyLikeUnlikeManager.isLike.equals("0"))like_str="Like";
                else like_str="Likes";
                like_users_count.setText(policyLikeUnlikeManager.likeCount+" "+like_str);

            }

            else{
                Toast.makeText(mContext,""+responseString,Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class PolicyEmoticonTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        PolicyEmoticonDetailsManager policyEmoticonDetailsManager = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            if (progressDialog_custom == null) {
                progressDialog_custom = ProgressLoaderUtilities.getProgressDialog(mContext);
            }
            if (!progressDialog_custom.isShowing()) {
                progressDialog_custom.show();
            }

        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                policyEmoticonDetailsManager = new PolicyEmoticonDetailsManager(mContext);
                responseString = policyEmoticonDetailsManager.callEmoticon(policyid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog_custom.dismiss();

            if(responseString.equals("100")){
                new EmoticonInterface(mContext).shareEmoticon(policyid,emoticon_ic,top_lay);

            }

            else{
                Toast.makeText(mContext,""+responseString,Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void downloadDOC(final String pdfPathUrl){
        download_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pdfPathUrl));
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        cmt_count.setText(PolicyCommentActivity.policycmt);
    }


   ProgressDialog progressDialog_new=null;
    public void LikesWondow(final String policyid) {

        final PopupWindow changeSortPopUp = new PopupWindow(mContext);
        changeSortPopUp.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.tagwindow_view, null);

        LinearLayout arraow_lay=(LinearLayout)layout.findViewById(R.id.arraow_lay);
        arraow_lay.setVisibility(View.INVISIBLE);
        ImageView cancelshare = (ImageView) layout.findViewById(R.id.cancelshare);
        final RecyclerView taglist = (RecyclerView) layout.findViewById(R.id.likelist);

        cancelshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSortPopUp.dismiss();
            }
        });


        new AsyncTask<Void, Void, Void>() {

            String responseString = "";
            String s = "Plase Wait...";

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                progressDialog_new = ProgressLoaderUtilities.getProgressDialog(mContext);
                progressDialog_new.show();
            }


            @Override
            protected Void doInBackground(Void... params) {
                try {
                    responseString =  new SrijanPolicyLikeManager(mContext).callLikedUser(policyid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                progressDialog_new.dismiss();
                if (responseString.equals("100")) {
                    TagPostAdapter tagPostAdapter = new TagPostAdapter(mContext);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                    taglist.setLayoutManager(layoutManager);
                    taglist.setItemAnimator(new DefaultItemAnimator());
                    taglist.setAdapter(tagPostAdapter);
                } else {

                }

            }


        }.execute();




        Rect rc = new Rect();
        like_count_task.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        like_count_task.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        changeSortPopUp.setAnimationStyle(R.style.DialogAnimation);
        changeSortPopUp.setContentView(layout);

        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        Log.i("height 1083",""+height);

        changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp.setHeight((int)(height/1.5));
        changeSortPopUp.setFocusable(true);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y =(int) (height-(height/1.5));
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);



    }

}
