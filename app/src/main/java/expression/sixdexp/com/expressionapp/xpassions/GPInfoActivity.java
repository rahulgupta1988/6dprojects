package expression.sixdexp.com.expressionapp.xpassions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import expression.sixdexp.com.expressionapp.FullimageActivity;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.adapter.GroupListAdapter;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.Utils;
import expression.sixdexp.com.expressionapp.xpassions.gpadapter.GPInfoPendingUserListAdapter;
import expression.sixdexp.com.expressionapp.xpassions.gpadapter.GPInfoUserListAdapter;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GPDetailsManager;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GroupJoinRequestManager;
import model.GroupInfoModel;
import model.GroupUser;

/**
 * Created by USER on 1/7/2018.
 */

public class GPInfoActivity extends AppCompatActivity {

    Context mContext;
    ImageView back_img,edit_img,up_down_arrow,add_user_ic;
    RoundedImageView gp_img;
    TextView gp_name,gp_descp,pending_count,count_txt;
    RelativeLayout pendlist_lay,gpuserlist_lay;
    ListView pendinguser_list,gpuser_list;
    RelativeLayout pendinglist_lay;

    GroupInfoModel groupInfoModel=new GroupInfoModel();
    public List<GroupUser> groupUsers=new ArrayList<>();
    public List<GroupUser> groupUsers_pending=new ArrayList<>();

    public static boolean isfromGpinfo=false;
    public static String isFromNotify="no";

    String coi_gid="0",coi_gtitle="",noofmember="",noofview="",coi_gicon="",noofpendingrequest="",coi_gdescription="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gpinfoactivity);
        mContext=this;

        Intent intent=getIntent();
        if(intent!=null){
            isFromNotify=intent.getStringExtra("fromnotification");
        }

        if(isFromNotify!=null){
            if(isFromNotify.equals("yes")){
                if(intent!=null) {
                    coi_gid = intent.getStringExtra("gpID");


                }
            }
            else if(GroupListAdapter.groupObject!=null){
                coi_gid=GroupListAdapter.groupObject.getCoi_gid();
            };
        }

        else if(GroupListAdapter.groupObject!=null){
            coi_gid=GroupListAdapter.groupObject.getCoi_gid();
        };

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isfromGpinfo){
            isfromGpinfo=false;
            new GetGPDetails().execute();
        }

    }

    public void setGpInfo(){

        if(groupInfoModel==null){
            return;
        }

        coi_gid=groupInfoModel.getCoi_gid();
        coi_gtitle=groupInfoModel.getCoi_gtitle();
        coi_gicon=groupInfoModel.getCoi_gicon();
        coi_gdescription=groupInfoModel.getCoi_gdescription();
        noofmember=String.valueOf(groupUsers.size());
        noofpendingrequest=String.valueOf(groupUsers_pending.size());

        if(groupInfoModel.getIsadmin().equalsIgnoreCase("yes")){
            edit_img.setVisibility(View.VISIBLE);
            add_user_ic.setVisibility(View.VISIBLE);
        }
        else{
            edit_img.setVisibility(View.GONE);
            add_user_ic.setVisibility(View.GONE);
        }


        if(groupInfoModel.getIsadmin().equalsIgnoreCase("yes") && groupInfoModel.getCoi_gispublic().equals("Close")){
            pendinglist_lay.setVisibility(View.VISIBLE);
        }
        else{
            pendinglist_lay.setVisibility(View.GONE);
        }

        gp_name.setText(""+coi_gtitle);
        count_txt.setText(""+noofmember);
        pending_count.setText(""+noofpendingrequest);
        gp_descp.setText(""+coi_gdescription);

        final  String image_url =coi_gicon;
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

        setGP_UserList(groupUsers);
        setGP_PendingUserList(groupUsers_pending);

    }



    public void initViews(){
      pendinglist_lay=(RelativeLayout)findViewById(R.id.pendinglist_lay);
      back_img=(ImageView)findViewById(R.id.back_img);
      edit_img=(ImageView)findViewById(R.id.edit_img);
      up_down_arrow=(ImageView)findViewById(R.id.up_down_arrow);
      add_user_ic=(ImageView)findViewById(R.id.add_user_ic);
      gp_img=(RoundedImageView) findViewById(R.id.gp_img);
      gp_name=(TextView)findViewById(R.id.gp_name);
      gp_descp=(TextView)findViewById(R.id.gp_descp);
      pending_count=(TextView)findViewById(R.id.pending_count);
      count_txt=(TextView)findViewById(R.id.count_txt);
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

        edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update_intent=new Intent(mContext,UpdateGroupActivity.class);
                update_intent.putParcelableArrayListExtra("gpusers", (ArrayList<? extends Parcelable>) groupUsers);
                startActivity(update_intent);
            }
        });

        add_user_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update_intent=new Intent(mContext,UpdateGroupActivity.class);
                update_intent.putParcelableArrayListExtra("gpusers", (ArrayList<? extends Parcelable>) groupUsers);
                startActivity(update_intent);
            }
        });

      back_img.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              onBackPressed();
          }
      });

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


    public void acceptJoinRequest(String requestUserID,String actionType){
        new RequestGPAccept(requestUserID,actionType).execute();
    }

    class RequestGPAccept extends AsyncTask<String,Void,Void> {
        GroupJoinRequestManager groupJoinRequestManager;
        String responseString = "";
        String s = "Plase Wait...";
        String requestUserID="";
        String actionType="";
        public RequestGPAccept(String requestUserID,String actionType){
            this.requestUserID=requestUserID;
            this.actionType=actionType;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            groupJoinRequestManager=new GroupJoinRequestManager(mContext);
            responseString =groupJoinRequestManager.putAcceptRequest(requestUserID,coi_gid,actionType);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (responseString.equals("100")) {
                new GetGPDetails().execute();
                //Toast.makeText(mContext,"Join request wait for admin approval.",Toast.LENGTH_SHORT).show();


            } else {

            }
        }
    }


    @Override
    public void onBackPressed() {
        if (isFromNotify!=null){
            if (isFromNotify.equals("yes")) {
                finish();
                //Toast.makeText(mContext,"backpressed",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext, GroupListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("isFromNotify",isFromNotify);
                startActivity(intent);
            }

            else{
                super.onBackPressed();
            }
        }
        else{
            super.onBackPressed();
        }
    }
}
