package expression.sixdexp.com.expressionapp.xpassions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import expression.sixdexp.com.expressionapp.FullimageActivity;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.adapter.GroupListAdapter;
import expression.sixdexp.com.expressionapp.manager.GroupsManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.Utils;
import expression.sixdexp.com.expressionapp.xpassions.gpadapter.GPInfoPendingUserListAdapter;
import expression.sixdexp.com.expressionapp.xpassions.gpadapter.GPInfoUserListAdapter;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GPDetailsManager;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GroupJoinRequestManager;
import model.GroupInfoModel;
import model.GroupUser;

/**
 * Created by Praveen on 12-Feb-18.
 */

public class SearchGPDetails extends AppCompatActivity {

    Context mContext;
    ImageView back_img,edit_img,up_down_arrow,add_user_ic;
    RoundedImageView gp_img;
    TextView gp_name,gp_descp,pending_count,count_txt;
    RelativeLayout pendlist_lay,gpuserlist_lay;
    ListView pendinguser_list,gpuser_list;
    Button usergpstatus;


    GroupInfoModel groupInfoModel=new GroupInfoModel();
    public List<GroupUser> groupUsers=new ArrayList<>();
    public List<GroupUser> groupUsers_pending=new ArrayList<>();


    String coi_gid="",coi_gtitle="",noofmember="",noofview="",coi_gicon="",noofpendingrequest="",coi_gdescription="";
    String actiontype="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchgpinfo);
        mContext=this;

        Intent intent=getIntent();
        if(intent!=null)
            coi_gid=intent.getStringExtra("coi_gid");

        initViews();




    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setGpInfo(){
        if(groupInfoModel==null){
            return;
        }

        coi_gtitle=groupInfoModel.getCoi_gtitle();
        noofmember=String.valueOf(groupUsers.size());
        coi_gicon=groupInfoModel.getCoi_gicon();
        noofpendingrequest=String.valueOf(groupUsers_pending.size());
        coi_gdescription=groupInfoModel.getCoi_gdescription();

        gp_name.setText(""+coi_gtitle);
        count_txt.setText(""+noofmember);
        pending_count.setText(""+noofpendingrequest);
        gp_descp.setText(""+coi_gdescription);

        final String image_url =coi_gicon;
        URI gp_uri = null;
        try {
            gp_uri = new URI(image_url.replace(" ", "%20"));
            if (gp_uri.toString().length()!=0){
                Glide.with(mContext)
                        .load(gp_uri.toString())
                        .placeholder(R.drawable.group_icon_default)
                        .error(R.drawable.group_icon_default)
                        .crossFade()
                        .thumbnail(0.1f)
                        .centerCrop()
                        .into(gp_img);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


        gp_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!image_url.equalsIgnoreCase("null") && !image_url.equalsIgnoreCase("")
                        && !image_url.equalsIgnoreCase(" ")) {
                    Intent intent = new Intent(mContext, FullimageActivity.class);
                    intent.putExtra("imageval", image_url);
                    mContext.startActivity(intent);

                }
            }
        });


        String coi_gispublic=groupInfoModel.getCoi_gispublic();
        String privategsatus=groupInfoModel.getPrivategsatus();
        String isadmin=groupInfoModel.getIsadmin();
        String isnoatmember=groupInfoModel.getIsnoatmember();


        //Suspend Group
        if((coi_gispublic.equalsIgnoreCase("Public")||coi_gispublic.equalsIgnoreCase("Close"))
                && privategsatus.equals("1")
                &&isadmin.equalsIgnoreCase("yes")
                && isnoatmember.equalsIgnoreCase("yes")){
            usergpstatus.setText("Suspend");
            usergpstatus.setVisibility(View.VISIBLE);
            usergpstatus.setEnabled(true);
            usergpstatus.setTextColor(Color.parseColor("#ffffff"));
            actiontype="suspend";
        }

        //Leave Group
        else if((coi_gispublic.equalsIgnoreCase("Public")||coi_gispublic.equalsIgnoreCase("Close"))
                && privategsatus.equals("1")
                &&isadmin.equalsIgnoreCase("no")
                && isnoatmember.equalsIgnoreCase("yes")){
            usergpstatus.setTextColor(Color.parseColor("#ffffff"));
            usergpstatus.setText("Leave");
            usergpstatus.setVisibility(View.VISIBLE);
            usergpstatus.setEnabled(true);
            actiontype="leave";
        }

        //Requested Group
        else if(coi_gispublic.equalsIgnoreCase("Close")
                && privategsatus.equals("-1")
                &&isadmin.equalsIgnoreCase("no")
                && isnoatmember.equalsIgnoreCase("no")){
            usergpstatus.setTextColor(Color.parseColor("#ffffff"));
            usergpstatus.setText("Requested");
            usergpstatus.setVisibility(View.VISIBLE);
            usergpstatus.setEnabled(false);

        }


       //Deactive Group
        else if(coi_gispublic.equalsIgnoreCase("Close")
                && privategsatus.equals("1")
                &&isadmin.equalsIgnoreCase("no")
                && isnoatmember.equalsIgnoreCase("no")){
            usergpstatus.setText("");
            usergpstatus.setVisibility(View.GONE);
            usergpstatus.setEnabled(false);

        }


        else {
            usergpstatus.setText("Join");
            usergpstatus.setVisibility(View.VISIBLE);
            usergpstatus.setEnabled(true);
            usergpstatus.setTextColor(Color.parseColor("#ffffff"));
            actiontype="join";
        }

    }



    public void initViews(){

        back_img=(ImageView)findViewById(R.id.back_img);
        edit_img=(ImageView)findViewById(R.id.edit_img);
        up_down_arrow=(ImageView)findViewById(R.id.up_down_arrow);
        add_user_ic=(ImageView)findViewById(R.id.add_user_ic);
        gp_img=(RoundedImageView) findViewById(R.id.gp_img);
        gp_name=(TextView)findViewById(R.id.gp_name);
        gp_descp=(TextView)findViewById(R.id.gp_descp);
        pending_count=(TextView)findViewById(R.id.pending_count);
        count_txt=(TextView)findViewById(R.id.count_txt);
        usergpstatus=(Button) findViewById(R.id.usergpstatus);
        pendlist_lay=(RelativeLayout)findViewById(R.id.pendlist_lay);
        gpuserlist_lay=(RelativeLayout)findViewById(R.id.gpuserlist_lay);
        pendinguser_list=(ListView) findViewById(R.id.pendinguser_list);
        gpuser_list=(ListView)findViewById(R.id.gpuser_list);

        up_down_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pendlist_lay.getVisibility()==View.GONE) {
                    pendlist_lay.setVisibility(View.VISIBLE);
                    up_down_arrow.setImageResource(R.drawable.arrow_down_gp);
                }
                else {
                    up_down_arrow.setImageResource(R.drawable.arrow_up_gp);
                    pendlist_lay.setVisibility(View.GONE);
                }
            }
        });




        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        usergpstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendJoinRequest();
            }
        });

        setGP_UserList(groupUsers);
        setGP_PendingUserList(groupUsers_pending);
        new GetGPDetails().execute();


    }
    GPInfoUserListAdapter gpInfoUserListAdapter=null;
    public  void setGP_UserList(List<GroupUser> usersList){
        gpInfoUserListAdapter=new GPInfoUserListAdapter(mContext,usersList);
        gpuser_list.setAdapter(gpInfoUserListAdapter);
        Utils.getListViewSize(gpuser_list);

    }
    GPInfoPendingUserListAdapter gpInfoPendingUserListAdapter=null;
    public  void setGP_PendingUserList(List<GroupUser> groupUsers_pending){
        gpInfoPendingUserListAdapter=new GPInfoPendingUserListAdapter(mContext,groupUsers_pending);
        pendinguser_list.setAdapter(gpInfoPendingUserListAdapter);
        Utils.getListViewSize(pendinguser_list);

    }


    ProgressDialog progressDialog = null;
    public class GetGPDetails extends AsyncTask<Void,Void,Void> {

        GPDetailsManager gpDetailsManager;
        String responseString = "";
        String s = "Plase Wait...";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            gpDetailsManager=new GPDetailsManager(mContext,groupInfoModel,groupUsers,groupUsers_pending);
            responseString =gpDetailsManager.getGPDetails(coi_gid);
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (responseString.equals("100")) {

                setGpInfo();
                gpInfoUserListAdapter.notifyDataSetChanged();
                Utils.getListViewSize(gpuser_list);
                gpInfoPendingUserListAdapter.notifyDataSetChanged();
                Utils.getListViewSize(pendinguser_list);

            } else {

            }
        }
    }

    public void sendJoinRequest(){
        new RequestGPJoin().execute();
    }

    class RequestGPJoin extends AsyncTask<String,Void,Void> {
        GroupJoinRequestManager groupJoinRequestManager;
        String responseString = "";
        String s = "Plase Wait...";
        String userid="";
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
            userid = new LoginManager(mContext).getUserInfo().get(0).getUserid();
        }

        @Override
        protected Void doInBackground(String... params) {
            groupJoinRequestManager=new GroupJoinRequestManager(mContext);
            responseString =groupJoinRequestManager.putJoinRequest(coi_gid,actiontype,userid);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (responseString.equals("100")) {

                String coi_gispublic="";
                if(groupInfoModel!=null){
                    coi_gispublic=groupInfoModel.getCoi_gispublic();
                }

                if(actiontype.equals("leave")){
                    Toast.makeText(mContext,"Leave request submitted.",Toast.LENGTH_SHORT).show();
                }

                else if(actiontype.equals("join")){
                    Toast.makeText(mContext,"Join request submitted.",Toast.LENGTH_SHORT).show();
                }

                else if(actiontype.equals("join") && coi_gispublic.equalsIgnoreCase("Close") ){
                    Toast.makeText(mContext,"Join request wait for admin approval.",Toast.LENGTH_SHORT).show();

                }


                GroupListActivity.isfromCreateGP=true;
                onBackPressed();

            } else {

            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

