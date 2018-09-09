package expression.sixdexp.com.expressionapp.xpassions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.xpassions.gpadapter.UserGPListAdapter;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.UsersGroupsManager;
import model.GroupObject;
import model.SearchGroupObject;

/**
 * Created by Praveen on 21-Feb-18.
 */

public class UserGPListActivity extends AppCompatActivity implements View.OnClickListener{

    Context mContext;
    TabLayout tabs;
    ImageView toread_back;
    RecyclerView groups_list,create_groups_list;
    LinearLayout list_lay,no_group_txt;
    TextView groups_count;
    List<SearchGroupObject> groupObjects = new ArrayList<>();
    List<SearchGroupObject> created_groupObjects = new ArrayList<>();
    String user_name="",userid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usergplistactivity_view);
        mContext=this;
        Intent intent=getIntent();
        if(intent!=null){
            user_name=intent.getStringExtra("user_name");
            userid=intent.getStringExtra("userid");
        }
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GroupListActivity.isfromCreateGP=false;
    }

    public void initViews(){
        groups_count=(TextView)findViewById(R.id.groups_count);
        no_group_txt=(LinearLayout)findViewById(R.id.no_group_txt);
        list_lay=(LinearLayout)findViewById(R.id.list_lay);
        groups_list=(RecyclerView)findViewById(R.id.groups_list);
        create_groups_list=(RecyclerView)findViewById(R.id.create_groups_list);
        tabs=(TabLayout)findViewById(R.id.tabs);
        toread_back=(ImageView)findViewById(R.id.toread_back);
        toread_back.setOnClickListener(this);

        groups_count.setText(user_name+"-Groups");

        setTabs();
        new getGroups().execute();
        setGroupList();
    }

    public void setTabs() {
        tabs.setTabTextColors(Color.parseColor("#FAB8B3"), Color.WHITE);
        tabs.addTab(tabs.newTab().setText("All Groups"));
        tabs.addTab(tabs.newTab().setText("Created Groups"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    create_groups_list.setVisibility(View.GONE);
                    groups_list.setVisibility(View.VISIBLE);

                    if(groupObjects.size()==0){
                        list_lay.setVisibility(View.GONE);
                        no_group_txt.setVisibility(View.VISIBLE);
                    }

                    else{
                        no_group_txt.setVisibility(View.GONE);
                        list_lay.setVisibility(View.VISIBLE);

                    }

                }
                else{
                    groups_list.setVisibility(View.GONE);
                    create_groups_list.setVisibility(View.VISIBLE);

                    if(created_groupObjects.size()==0){
                        list_lay.setVisibility(View.GONE);
                        no_group_txt.setVisibility(View.VISIBLE);
                    }

                    else{
                        no_group_txt.setVisibility(View.GONE);
                        list_lay.setVisibility(View.VISIBLE);

                    }

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

       }



    UserGPListAdapter groupListAdapter=null;
    public void setGroupList(){
        GridLayoutManager lLayout = new GridLayoutManager(this, 2);
        groups_list.setHasFixedSize(true);
        groups_list.setLayoutManager(lLayout);
        groupListAdapter=new UserGPListAdapter(mContext,groupObjects,userid);
        groups_list.setAdapter(groupListAdapter);

        setSearchGroupList();

    }

    UserGPListAdapter created_GroupListAdapter=null;
    public void setSearchGroupList(){
        GridLayoutManager lLayout = new GridLayoutManager(this, 2);
        create_groups_list.setHasFixedSize(true);
        create_groups_list.setLayoutManager(lLayout);
        created_GroupListAdapter=new UserGPListAdapter(mContext,created_groupObjects,userid);
        create_groups_list.setAdapter(created_GroupListAdapter);

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.toread_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    ProgressDialog progressDialog = null;

    class getGroups extends AsyncTask<Void,Void,Void> {
        UsersGroupsManager groupsManager;
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
            groupsManager=new UsersGroupsManager(mContext);
            responseString =groupsManager.getGroups(userid);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (responseString.equals("100")) {

                groupObjects.clear();
                groupObjects.addAll(groupsManager.searchGroupObjects);
                groupListAdapter.notifyDataSetChanged();

                if(groupObjects.size()==0){
                    list_lay.setVisibility(View.GONE);
                    no_group_txt.setVisibility(View.VISIBLE);
                }

                else{
                    no_group_txt.setVisibility(View.GONE);
                    list_lay.setVisibility(View.VISIBLE);
                }


            } else {
                Toast.makeText(mContext,""+responseString,Toast.LENGTH_SHORT).show();
            }
        }
    }

}
