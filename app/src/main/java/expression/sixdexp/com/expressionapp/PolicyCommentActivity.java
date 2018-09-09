package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import expression.sixdexp.com.expressionapp.adapter.Comment_ExpandableListAdapter;
import expression.sixdexp.com.expressionapp.manager.PolicyLikeUnlikeManager;
import expression.sixdexp.com.expressionapp.manager.SinglePostManager;
import expression.sixdexp.com.expressionapp.manager.SrijanPolicyCommentsManager;
import expression.sixdexp.com.expressionapp.manager.SrijanSecondLevelCommentManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.SetPhotoNew;

/**
 * Created by hp on 8/12/2017.
 */

public class PolicyCommentActivity extends Activity {
    Context mContext;
    SetPhotoNew selPhoto;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Comment_ExpandableListAdapter comment_expandableListAdapter;
    ImageView back_ic;
    ExpandableListView comment_list;
    String policyid="0";
    public  static String policycmt="0";
    public  static String policy_desc="";
    Button cocreate_btn;
    TextView cmt_count;
    EditText comment_txt;
    RelativeLayout add_file_lay;
    LinearLayout linklay;
    TextView videodoclink;
    ImageView cancelattachment;
    ImageView tagicon;
    TextView tagcount_txt,policy_description;

    String viddoclink = "";
    String base64 = "";
    String ftype = "";
    String file_extension = "";
    String content_type = "";
    String file_name = "";
    String notificationmasterid,postId;
    public String temp_policyid="0";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.policycomment_view);
        mContext=this;
        Intent intent=getIntent();
        if(intent!=null){
            postId =getIntent().getStringExtra("postId");
            notificationmasterid=getIntent().getStringExtra("notificationmasterid");
            policyid=intent.getStringExtra("policyid");
            getAllComments();
        }
        initViews();

    }

    public void initViews() {
        tagcount_txt=(TextView)findViewById(R.id.tagcount_txt);
        policy_description=(TextView)findViewById(R.id.policy_description);
        policy_description.setMovementMethod(new ScrollingMovementMethod());
        tagicon=(ImageView)findViewById(R.id.tagicon);
        tagicon.setVisibility(View.VISIBLE);
        tagicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.tag_txt=tagcount_txt;
                Intent  intent=new Intent(mContext,TagActivty.class);
                startActivity(intent);
            }
        });

        comment_txt=(EditText)findViewById(R.id.comment_txt);
        cocreate_btn=(Button)findViewById(R.id.cocreate_btn);
        cancelattachment=(ImageView)findViewById(R.id.cancelattachment);
        linklay=(LinearLayout)findViewById(R.id.linklay);
        videodoclink=(TextView)findViewById(R.id.videodoclink);
        add_file_lay=(RelativeLayout)findViewById(R.id.add_file_lay);
        cmt_count=(TextView)findViewById(R.id.cmt_count);
        comment_list = (ExpandableListView) findViewById(R.id.comment_list);
        back_ic = (ImageView) findViewById(R.id.back_ic);
        back_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cmt_count.setText(""+policycmt);

        add_file_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selPhoto = new SetPhotoNew(mContext, videodoclink, linklay);
            }
        });



        cancelattachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftype = "";
                content_type = "";
                file_extension = "";
                file_name = "";
                base64 = "";
                viddoclink = "";
                videodoclink.setText("");
                linklay.setVisibility(View.GONE);
                selPhoto=null;
            }
        });

        cocreate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment_str = comment_txt.getText().toString().trim();
                if (comment_str.length() > 0) {

                if (selPhoto != null) {
                        base64 = selPhoto.getbase64();
                        file_extension = selPhoto.getextention();
                        if (file_extension == null) file_extension = "";
                        content_type = selPhoto.getcontentType();
                        file_name = selPhoto.getFileName();
                        if (file_name == null) file_name = "";


                    /*    if (!(base64.length()>0
                                &&file_extension.length() > 0
                                &&content_type.length() > 0
                                &file_name.length() > 0)) {
                            Toast.makeText(mContext, "Attachement is not valid...", Toast.LENGTH_SHORT).show();
                            return;

                        }*/


                }


                    postFirstLevelComment(comment_str,policyid);
            }

                else{
                    Toast.makeText(mContext, "Enter Comment...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(TagActivty.taged_users.size()>0){
            if(Constant.tag_txt!=null && Constant.tag_txt==tagcount_txt) {
                tagcount_txt.setVisibility(View.VISIBLE);
                tagcount_txt.setText("" + TagActivty.taged_users.size());
            }
        }
        else{
            tagcount_txt.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selPhoto.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        Constant.tag_txt=null;
        TagActivty.taged_users.clear();
        super.onBackPressed();

    }

    ProgressDialog progressDialog_custom=null;


    public void getAllComments() {
         new AsyncTask<String, Void, Void>() {

            String responseString = "";
            SrijanPolicyCommentsManager srijanPolicyCommentsManager = null;

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
                    srijanPolicyCommentsManager = new SrijanPolicyCommentsManager(mContext);
                    responseString = srijanPolicyCommentsManager.callSrijanPolicyComments(policyid);
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

                if (responseString.equals("100")) {
                    cmt_count.setText("" + policycmt);
                    policy_description.setText(""+policy_desc);
                    comment_expandableListAdapter = new Comment_ExpandableListAdapter(mContext, policyid, listDataHeader, listDataChild);
                    comment_list.setAdapter(comment_expandableListAdapter);

                } else {
                    Toast.makeText(mContext, "" + responseString, Toast.LENGTH_SHORT).show();
                }

                if(notificationmasterid!=null)
                    new GetReadNotification().execute();
            }
        }.execute();
    }


    ProgressDialog progressDialog;
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
                    Constant.tag_txt=null;
                    TagActivty.taged_users.clear();

                    Toast.makeText(mContext, "Comment saved successfully!", Toast.LENGTH_SHORT).show();
                    getAllComments();

                }
                else {
                    Toast.makeText(mContext, "" + responseString, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public void postFirstLevelComment(final String comment,final String policyid) {


        new AsyncTask<String, Void, Void>(){
            StringBuilder taggedUSerID=null;
            String responseString = "";
            FirstLevelCommentManager firstLevelCommentManager = null;

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
                    firstLevelCommentManager = new FirstLevelCommentManager(mContext);
                    responseString = firstLevelCommentManager.callSecondLevelComment(comment,policyid,base64,file_name,file_extension,content_type,taggedUSerID.toString());
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
                    tagcount_txt.setText("");
                    tagcount_txt.setVisibility(View.GONE);
                    TagActivty.taged_users.clear();

                    ftype = "";
                    content_type = "";
                    file_extension = "";
                    file_name = "";
                    base64 = "";
                    viddoclink = "";
                    videodoclink.setText("");
                    linklay.setVisibility(View.GONE);
                    selPhoto=null;
                    comment_txt.setText("");
                    Toast.makeText(mContext, "Comment saved successfully!", Toast.LENGTH_SHORT).show();
                    getAllComments();

                }
                else {
                    Toast.makeText(mContext, "" + responseString, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


    public class GetReadNotification extends AsyncTask<String, Void, Void> {
        String responseString = "";
        String s = "Please wait...";
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Void doInBackground(String... params) {
// TODO Auto-generated method stub
            try {
                responseString = new SinglePostManager(mContext).callReadNotification(postId,notificationmasterid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            progressDialog_custom.dismiss();
            if (responseString.equals("100")) {


            } else {
                Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
            }
        }
    }


}
