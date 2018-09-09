package com.expression.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import db.SrijanTOPComment;
import expression.sixdexp.com.expressionapp.PolicyCommentActivity;
import expression.sixdexp.com.expressionapp.ProfileDetails_Search;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.TagActivty;
import expression.sixdexp.com.expressionapp.adapter.ClusterListAdapter;
import expression.sixdexp.com.expressionapp.adapter.SrijanLatestCommentAdapter;
import expression.sixdexp.com.expressionapp.adapter.SrijanPolicyAdapter;
import expression.sixdexp.com.expressionapp.adapter.TagPostAdapter;
import expression.sixdexp.com.expressionapp.manager.ClusterManager;
import expression.sixdexp.com.expressionapp.manager.SerachPolicyManager;
import expression.sixdexp.com.expressionapp.manager.SrijanPolicyListManager;
import expression.sixdexp.com.expressionapp.manager.SrijanSecondLevelCommentManager;
import expression.sixdexp.com.expressionapp.manager.SrijanTOPCommentsManager;
import expression.sixdexp.com.expressionapp.manager.SrisanTaggedUserManager;
import expression.sixdexp.com.expressionapp.manager.UserInfoManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;

/**
 * Created by Praveen on 28-Jul-17.
 */

public class SrijanFragment extends Fragment {
    View view;
    Context mContext;
    RelativeLayout srijan_tab, policy_tab, cluster_tab;
    RecyclerView srijan_comment_list;
    RecyclerView poliecies_list;
    RecyclerView cluster_list;
    LinearLayout srijan_tab_lay, policies_tab_lay, cluster_tab_lay;
    View srijan_tab_line, policy_tab_line, cluster_tab_line;
    ImageView search_ic;
    EditText policy_search;
    String search_key = "";
    TextView srijan_tab_txt,policy_tab_txt,cluster_tab_txt;

    LinearLayout top_comment_card;
    RelativeLayout bottom_list_lay;
    TextView no_comt_txt;

    //params
    public static boolean isPolicyTab=false;
    public static boolean isSrijanTab=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        view = inflater.inflate(R.layout.srijan_lay, container, false);
        initView();
        new GetTopComments().execute();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(Constant.tag_txt!=null) {
            if (TagActivty.taged_users.size() > 0) {
                if (Constant.tag_txt != null) {
                    Constant.tag_txt.setVisibility(View.VISIBLE);
                    Constant.tag_txt.setText("" + TagActivty.taged_users.size());
                }
            } else {
                Constant.tag_txt.setVisibility(View.GONE);
            }
        }

        if(isPolicyTab){
            isPolicyTab=false;
            new GetPolicies().execute();
        }

        if(isSrijanTab){
            isSrijanTab=false;
            new GetTopComments().execute();
        }

    }

    public void initView() {
        no_comt_txt=(TextView)view.findViewById(R.id.no_comt_txt);
        bottom_list_lay=(RelativeLayout) view.findViewById(R.id.bottom_list_lay);
        top_comment_card=(LinearLayout)view.findViewById(R.id.top_comment_card);
        // policy search
        search_ic = (ImageView) view.findViewById(R.id.search_ic);
        policy_search = (EditText) view.findViewById(R.id.policy_search);

        srijan_tab_lay = (LinearLayout) view.findViewById(R.id.srijan_tab_lay);
        policies_tab_lay = (LinearLayout) view.findViewById(R.id.policies_tab_lay);
        cluster_tab_lay = (LinearLayout) view.findViewById(R.id.cluster_tab_lay);

        srijan_tab_line = (View) view.findViewById(R.id.srijan_tab_line);
        policy_tab_line = (View) view.findViewById(R.id.policy_tab_line);
        cluster_tab_line = (View) view.findViewById(R.id.cluster_tab_line);

        srijan_tab = (RelativeLayout) view.findViewById(R.id.srijan_tab);
        policy_tab = (RelativeLayout) view.findViewById(R.id.policy_tab);
        cluster_tab = (RelativeLayout) view.findViewById(R.id.cluster_tab);

        srijan_tab_txt=(TextView)view.findViewById(R.id.srijan_tab_txt);
        policy_tab_txt=(TextView)view.findViewById(R.id.policy_tab_txt);
        cluster_tab_txt=(TextView)view.findViewById(R.id.cluster_tab_txt);

        srijan_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(srijan_tab_line.getVisibility()!=View.VISIBLE){

                    srijan_tab_line.setVisibility(View.VISIBLE);
                    policy_tab_line.setVisibility(View.GONE);
                    cluster_tab_line.setVisibility(View.GONE);

                    srijan_tab_lay.setVisibility(View.VISIBLE);
                    policies_tab_lay.setVisibility(View.GONE);
                    cluster_tab_lay.setVisibility(View.GONE);

                   /* srijan_tab.setBackgroundColor(Color.parseColor("#F4F6F6"));
                    policy_tab.setBackgroundColor(Color.parseColor("#ffffff"));
                    cluster_tab.setBackgroundColor(Color.parseColor("#ffffff"));*/

                   /* srijan_tab_txt.setTextColor(Color.parseColor("#ffffff"));
                    policy_tab_txt.setTextColor(Color.parseColor("#2b2b2b"));
                    cluster_tab_txt.setTextColor(Color.parseColor("#2b2b2b"));*/

                  /*  srijan_tab_txt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    policy_tab_txt.setTypeface(Typeface.DEFAULT);
                    cluster_tab_txt.setTypeface(Typeface.DEFAULT);*/

                    new GetTopComments().execute();
                }

            }
        });

        policy_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(policy_tab_line.getVisibility()!=View.VISIBLE){

                    srijan_tab_line.setVisibility(View.GONE);
                    policy_tab_line.setVisibility(View.VISIBLE);
                    cluster_tab_line.setVisibility(View.GONE);

                    srijan_tab_lay.setVisibility(View.GONE);
                    policies_tab_lay.setVisibility(View.VISIBLE);
                    cluster_tab_lay.setVisibility(View.GONE);


                 /*   srijan_tab.setBackgroundColor(Color.parseColor("#ffffff"));
                    policy_tab.setBackgroundColor(Color.parseColor("#F4F6F6"));
                    cluster_tab.setBackgroundColor(Color.parseColor("#ffffff"));*/

                /*    srijan_tab_txt.setTextColor(Color.parseColor("#2b2b2b"));
                    policy_tab_txt.setTextColor(Color.parseColor("#ffffff"));
                    cluster_tab_txt.setTextColor(Color.parseColor("#2b2b2b"));
*/
                   /* srijan_tab_txt.setTypeface(Typeface.DEFAULT);
                    policy_tab_txt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    cluster_tab_txt.setTypeface(Typeface.DEFAULT);*/
                    new GetPolicies().execute();

                }


            }
        });

        cluster_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cluster_tab_line.getVisibility()!=View.VISIBLE){


                    srijan_tab_line.setVisibility(View.GONE);
                    policy_tab_line.setVisibility(View.GONE);
                    cluster_tab_line.setVisibility(View.VISIBLE);

                    srijan_tab_lay.setVisibility(View.GONE);
                    policies_tab_lay.setVisibility(View.GONE);
                    cluster_tab_lay.setVisibility(View.VISIBLE);


               /*     srijan_tab.setBackgroundColor(Color.parseColor("#ffffff"));
                    policy_tab.setBackgroundColor(Color.parseColor("#ffffff"));
                    cluster_tab.setBackgroundColor(Color.parseColor("#F4F6F6"));*/

                    /*srijan_tab_txt.setTextColor(Color.parseColor("#2b2b2b"));
                    policy_tab_txt.setTextColor(Color.parseColor("#2b2b2b"));
                    cluster_tab_txt.setTextColor(Color.parseColor("#ffffff"));*/
/*
                   srijan_tab_txt.setTypeface(Typeface.DEFAULT);
                    policy_tab_txt.setTypeface(Typeface.DEFAULT);
                    cluster_tab_txt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));*/

                    new GetClusters().execute();
                }

            }
        });

        search_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search_key = policy_search.getText().toString().trim();
                if (search_key.length() > 0) {
                    new SearchPolicies().execute();
                }

            }
        });


    }

    int temp_pos=0;
    public void setCommentList() {
        srijan_comment_list = (RecyclerView) view.findViewById(R.id.srijan_comment_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        srijan_comment_list.setLayoutManager(layoutManager);
        srijan_comment_list.setItemAnimator(new DefaultItemAnimator());
        srijan_comment_list.setAdapter(new SrijanLatestCommentAdapter(mContext, new SrijanLatestCommentAdapter.ListCallBack() {
            @Override
            public void setPosition(int pos) {
                //Toast.makeText(mContext,""+pos,Toast.LENGTH_SHORT).show();
                setSrijanTopComment(pos);
                if(temp_pos!=pos)
                setAnimation();
                temp_pos=pos;

            }

            @Override
            public void commentPost(String parentcommentId, String comment, String policyid) {
                postComment(parentcommentId,comment,policyid);
            }
        }));
    }

    public void setPolicyList() {
        poliecies_list = (RecyclerView) view.findViewById(R.id.poliecies_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        poliecies_list.setLayoutManager(layoutManager);
        poliecies_list.setItemAnimator(new DefaultItemAnimator());
        poliecies_list.setAdapter(new SrijanPolicyAdapter(mContext));
    }

    public void setclusterList() {
        cluster_list = (RecyclerView) view.findViewById(R.id.cluster_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        cluster_list.setLayoutManager(layoutManager);
        cluster_list.setItemAnimator(new DefaultItemAnimator());
        cluster_list.setAdapter(new ClusterListAdapter(mContext));
    }

    String policyid="0";
    public void setSrijanTopComment(final int pos) {
        RelativeLayout userview=(RelativeLayout)view.findViewById(R.id.userview);
        ImageView cmt_ic=(ImageView)view.findViewById(R.id.cmt_ic);
        TextView policyname = (TextView) view.findViewById(R.id.policyname);
        ImageView userimage = (ImageView) view.findViewById(R.id.userimage);
        TextView username = (TextView) view.findViewById(R.id.username);
        TextView date_txt = (TextView) view.findViewById(R.id.date_txt);
        TextView time_txt = (TextView) view.findViewById(R.id.time_txt);
        TextView description = (TextView) view.findViewById(R.id.description);
        RelativeLayout rpl_lay = (RelativeLayout) view.findViewById(R.id.rpl_lay);
        final FrameLayout tag_lay=(FrameLayout)view.findViewById(R.id.tag_lay);
        TextView tagcount_txt=(TextView)view.findViewById(R.id.tagcount_txt);

        final SrijanTOPComment srijanTOPComment = new SrijanTOPCommentsManager(mContext).getTopComments().get(pos);

        policyid=srijanTOPComment.getPolicyid();

        policyname.setText("" + srijanTOPComment.getPolicynamedisplayvalue());
        username.setText("" + srijanTOPComment.getCommentusername());
        date_txt.setText("" + srijanTOPComment.getCommentdatedisplayvalue());
        time_txt.setText("" + srijanTOPComment.getCommenttimedisplayvalue());
        description.setText("" + srijanTOPComment.getUsercomment());
        final String searchUserID=srijanTOPComment.getCommentuserid();


        userview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetUserProfile().execute(searchUserID);
            }
        });

     String tag_count=srijanTOPComment.getTaguser();

        if (!tag_count.equalsIgnoreCase("null") && tag_count!=null) {
            if(tag_count.equals("0"))
                tag_lay.setVisibility(View.GONE);
            else {
                tag_lay.setVisibility(View.VISIBLE);
                tagcount_txt.setText("" + tag_count);
                tag_lay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String parentID=srijanTOPComment.getParentcommentid();
                        String policyid=srijanTOPComment.getPolicyid();
                        String commentlevel=srijanTOPComment.getCommentlevel();
                        String childcommentid=srijanTOPComment.getChildcommentid();

                        tagWondow(parentID,policyid,commentlevel,childcommentid,tag_lay, 300);
                    }
                });

            }
        }



        final String policyid=srijanTOPComment.getPolicyid();
        final String parentcommentId=srijanTOPComment.getParentcommentid();

        String profile_img_url = srijanTOPComment.getCommentuserprofilepic();
        profile_img_url=profile_img_url.replace(" ", "%20");

        if (!profile_img_url.equalsIgnoreCase("null") && !profile_img_url.equalsIgnoreCase("")
                && !profile_img_url.equalsIgnoreCase(" ")) {
            Picasso.with(mContext)
                    .load(profile_img_url)
                    .resize(800, 800)
                    .centerInside()
                    .placeholder(R.drawable.icon_profile_drawer_bg)
                    .error(R.drawable.icon_profile_drawer_bg)
                    .into(userimage);
        }

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSrijanTab=true;
                String policyid = new SrijanTOPCommentsManager(mContext).getTopComments().get(pos).getPolicyid();
                Log.i("polidc 9090", "" + policyid);
                Intent intent = new Intent(mContext, PolicyCommentActivity.class);
                intent.putExtra("policyid", policyid);
                startActivity(intent);
            }
        });

        cmt_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String policyid = new SrijanTOPCommentsManager(mContext).getTopComments().get(pos).getPolicyid();
                Log.i("polidc 9090", "" + policyid);
                Intent intent = new Intent(mContext, PolicyCommentActivity.class);
                intent.putExtra("policyid", policyid);
                startActivity(intent);
            }
        });


        if (srijanTOPComment.getCommentlevel().equals("1")) {
            rpl_lay.setVisibility(View.VISIBLE);
        } else {
            rpl_lay.setVisibility(View.GONE);
        }

        rpl_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentWondow(policyid,parentcommentId);
            }
        });
    }

    public class GetTopComments extends AsyncTask<String, Void, Void> {

        String responseString = "";
        SrijanTOPCommentsManager srijanTOPCommentsManager = null;

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
                srijanTOPCommentsManager = new SrijanTOPCommentsManager(mContext);
                responseString = srijanTOPCommentsManager.callSrijanTopComment();
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
                if(new SrijanTOPCommentsManager(mContext).getTopComments().size() > 0) {
                    srijan_tab_lay.setVisibility(View.VISIBLE);
                    no_comt_txt.setVisibility(View.GONE);
                    setSrijanTopComment(0);
                    setCommentList();
                }
                else{
                    srijan_tab_lay.setVisibility(View.GONE);
                    no_comt_txt.setVisibility(View.VISIBLE);
                }


            } else {
                Toast.makeText(mContext, "" + responseString, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class GetClusters extends AsyncTask<String, Void, Void> {

        String responseString = "";
        ClusterManager clusterManager = null;

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
                clusterManager = new ClusterManager(mContext);
                responseString = clusterManager.callCluster();
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

                if(clusterManager.getClusters().size()>0) {
                    cluster_tab_lay.setVisibility(View.VISIBLE);
                    no_comt_txt.setVisibility(View.GONE);
                    setclusterList();
                }
                else{
                    cluster_tab_lay.setVisibility(View.GONE);
                    no_comt_txt.setVisibility(View.VISIBLE);
                }



            } else {
                Toast.makeText(mContext, "" + responseString, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class GetPolicies extends AsyncTask<String, Void, Void> {

        String responseString = "";
        SrijanPolicyListManager srijanPolicyListManager = null;

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
                srijanPolicyListManager = new SrijanPolicyListManager(mContext);
                responseString = srijanPolicyListManager.callSrijanPolicy();
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
                if(SrijanPolicyListManager.srijanPolicyListModels.size()>0) {
                    policies_tab_lay.setVisibility(View.VISIBLE);
                    no_comt_txt.setVisibility(View.GONE);
                    setPolicyList();
                }
                else{
                    policies_tab_lay.setVisibility(View.GONE);
                    no_comt_txt.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(mContext, "" + responseString, Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class SearchPolicies extends AsyncTask<String, Void, Void> {

        String responseString = "";
        SerachPolicyManager serachPolicyManager = null;

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
                serachPolicyManager = new SerachPolicyManager(mContext);
                responseString = serachPolicyManager.callSrijanPolicy(search_key);
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
                policy_search.setText("");
                setPolicyList();
            } else {
                Toast.makeText(mContext, "" + responseString, Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void postComment(final String parentcommentId,final String comment,final String policyid) {


         new AsyncTask<String, Void, Void>(){
             StringBuilder taggedUSerID=null;
            String responseString = "";
            SrijanSecondLevelCommentManager srijanSecondLevelCommentManager = null;

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

                taggedUSerID=new StringBuilder();
                for(int i=0;i<TagActivty.taged_users.size();i++){
                    taggedUSerID.append(TagActivty.taged_users.get(i).getUser_id());
                    if(i<(TagActivty.taged_users.size()-1))
                        taggedUSerID.append(",");
                }

            }

            @Override
            protected Void doInBackground(String... params) {
                // TODO Auto-generated method stub
                try {
                    srijanSecondLevelCommentManager = new SrijanSecondLevelCommentManager(mContext);
                    responseString = srijanSecondLevelCommentManager.callSecondLevelComment(parentcommentId,comment,policyid,taggedUSerID.toString());
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
                    Constant.tag_txt.setText("");
                    Constant.tag_txt.setVisibility(View.GONE);
                    TagActivty.taged_users.clear();
                    Toast.makeText(mContext, "Comment saved successfully!", Toast.LENGTH_SHORT).show();
                    new GetTopComments().execute();
                }
                else {
                    Toast.makeText(mContext, "" + responseString, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }





    View window_layout=null;
    PopupWindow changeSortPopUp=null;
    String comment_txt="";
    public void commentWondow(final String policyid, final String parentcommentId) {
        if(changeSortPopUp==null){
            changeSortPopUp = new PopupWindow(mContext);
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            window_layout = layoutInflater.inflate(R.layout.srijan_policy_comment_window, null);

            changeSortPopUp.setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        }



        ImageView tagicon=(ImageView)window_layout.findViewById(R.id.tagicon);
        TextView tagcount_txt=(TextView)window_layout.findViewById(R.id.tagcount_txt);
        Constant.tag_txt=tagcount_txt;
        tagicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(mContext,TagActivty.class);
                mContext.startActivity(intent);
            }
        });

        ImageView subcombtn=(ImageView)window_layout.findViewById(R.id.subcombtn);
        final EditText commenttxt=(EditText)window_layout.findViewById(R.id.commenttxt);
        commenttxt.setText("");

        subcombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_txt=commenttxt.getText().toString().trim();
                if(!comment_txt.equals("")){
                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager)(mContext.getSystemService(Context.INPUT_METHOD_SERVICE));
                        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                    }

                    postComment(parentcommentId,comment_txt,policyid);
                    changeSortPopUp.dismiss();

                }
                else{
                    Toast.makeText(mContext,"Please Enter Comment",Toast.LENGTH_SHORT).show();
                }
            }
        });




        Rect rc = new Rect();
        commenttxt.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        commenttxt.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        changeSortPopUp.setAnimationStyle(R.style.DialogAnimation);
        changeSortPopUp.setContentView(window_layout);

        DisplayMetrics displayMetrics=mContext.getResources().getDisplayMetrics();
        int height=displayMetrics.heightPixels;

        changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp.setHeight((int)(height/3));
        changeSortPopUp.setFocusable(true);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y =(int) (height-(height/3));
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(window_layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);

        changeSortPopUp.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                TagActivty.taged_users.clear();
                Constant.tag_txt.setVisibility(View.GONE);
               /* Constant.tag_txt.setText("");
                Constant.tag_txt.setVisibility(View.GONE);
                TagActivty.taged_users.clear();
                Constant.tag_txt=null;*/
            }
        });
    }

    ProgressDialog progressDialog=null;
    public void tagWondow(final String commentid,final String policyid,final String commentlevel,final String childcommentid,FrameLayout likeicon, int windowsize) {
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
                progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
                progressDialog.show();
            }


            @Override
            protected Void doInBackground(Void... params) {
                try {
                    responseString =  new SrisanTaggedUserManager(mContext).callTagedUser(commentid,policyid,commentlevel,childcommentid);
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
        likeicon.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        likeicon.getLocationInWindow(xy);
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


    public class GetUserProfile extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";
        String searchUserID="0";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
            Log.i("AutoTextSearch", "AutoTextSearch");
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                searchUserID=params[0];
                //responseString = new SearchManager(mContext).callForSearch(userIDstr);
                responseString = new UserInfoManager(mContext).userInfo(searchUserID);
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

              /*  Intent proifleIntent = new Intent(mContext, UserProfileActivity.class);*/
                Intent proifleIntent = new Intent(mContext, ProfileDetails_Search.class);
                startActivity(proifleIntent);

            } else {


            }

        }
    }




    public void setAnimation(){
        Animation myAnim1 = AnimationUtils.loadAnimation(mContext,R.anim.srijan_comment_anim);
        myAnim1.setFillAfter(true);
        myAnim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        top_comment_card.startAnimation(myAnim1);
    }


}
