package expression.sixdexp.com.expressionapp.xpassions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.expression.ui.FlipComplexLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.adapter.GroupListAdapter;
import expression.sixdexp.com.expressionapp.adapter.SearchGroupListAdapter;
import expression.sixdexp.com.expressionapp.manager.GroupsManager;
import expression.sixdexp.com.expressionapp.manager.SearchGroupsManager;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import model.GroupObject;
import model.SearchGroupObject;

/**
 * Created by Praveen on 03-Jan-18.
 */

public class GroupListActivity extends AppCompatActivity implements View.OnClickListener{

    Context mContext;
    public static boolean isfromCreateGP=false;
    public static boolean isfromUpdateGP=false;
    RelativeLayout search_lay;
    EditText grp_src;
    TextView groups_count;
    ImageView searc_ic,toread_back,create_group;
    LinearLayout list_lay,no_group_txt;
    RecyclerView groups_list,search_groups_list;
    List<GroupObject> groupObjects = new ArrayList<>();
    List<SearchGroupObject> searchGroupObjects = new ArrayList<>();

    LinearLayout progress_lay,progress_lay_search;

    TabLayout tabs;

    String glastcount="0";
    String glastcount_search="0";




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grouplistactivity);
        mContext=this;
        initViews();
    }



    @Override
    protected void onResume() {
        super.onResume();
        //if(isfromCreateGP){
          //  isfromCreateGP=false;
        groupObjects.clear();
        searchGroupObjects.clear();
            new getGroups().execute();
       // }






    }

    public void initViews(){
        progress_lay=(LinearLayout)findViewById(R.id.progress_lay);
        progress_lay_search=(LinearLayout)findViewById(R.id.progress_lay_search);
        tabs=(TabLayout)findViewById(R.id.tabs);
        create_group=(ImageView)findViewById(R.id.create_group);
        create_group.setOnClickListener(this);
        groups_count=(TextView)findViewById(R.id.groups_count);
        no_group_txt=(LinearLayout)findViewById(R.id.no_group_txt);
        searc_ic=(ImageView)findViewById(R.id.searc_ic);
        toread_back=(ImageView)findViewById(R.id.toread_back);
        searc_ic.setOnClickListener(this);
        toread_back.setOnClickListener(this);
        grp_src=(EditText)findViewById(R.id.grp_src);
        list_lay=(LinearLayout)findViewById(R.id.list_lay);
        search_lay=(RelativeLayout)findViewById(R.id.search_lay);
        groups_list=(RecyclerView)findViewById(R.id.groups_list);
        search_groups_list=(RecyclerView)findViewById(R.id.search_groups_list);
        setTabs();
       //new getGroups().execute();
        setGroupList();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    public void setTabs(){
        tabs.setTabTextColors(Color.parseColor("#FAB8B3"),Color.WHITE);
        tabs.addTab(tabs.newTab().setText("Groups You're In"));
        tabs.addTab(tabs.newTab().setText("All Groups"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                 if(tab.getPosition()==0){

                     LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                             LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.82f);
                     param.setMargins(10,10,10,10);
                     list_lay.setLayoutParams(param);

                     search_lay.setVisibility(View.GONE);
                     search_groups_list.setVisibility(View.GONE);
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

                     LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
                             LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.74f);
                     param1.setMargins(10,10,10,10);
                     list_lay.setLayoutParams(param1);
                     search_lay.setVisibility(View.VISIBLE);
                     groups_list.setVisibility(View.GONE);
                     search_groups_list.setVisibility(View.VISIBLE);

                     if(searchGroupObjects.size()==0){
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

    @Override
    public void onClick(View v) {

        switch (v.getId()){


            case R.id.searc_ic:

                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                String search_str=grp_src.getText().toString();
                if(search_str.length()!=0){
                    if(searchGroupListAdapter.getItemCount()>0)
                     search_groups_list.scrollToPosition(0);
                     new getSearchedGroups().execute();
                }

                break;

            case R.id.toread_back:

               onBackPressed();

                break;

            case R.id.create_group:
                Intent createIntent=new Intent(mContext,CreateGroup.class);
                startActivity(createIntent);
                break;



        }
    }

    GroupListAdapter groupListAdapter=null;
    GridLayoutManager lLayout=null;
    public void setGroupList(){
          lLayout = new GridLayoutManager(this, 2);
         groups_list.setHasFixedSize(true);
         groups_list.setLayoutManager(lLayout);
         groupListAdapter=new GroupListAdapter(mContext,groupObjects);
         groups_list.setAdapter(groupListAdapter);



        groups_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleThreshold=1;
                int totalItemCount = lLayout.getItemCount();
                int lastVisibleItem = lLayout.findLastVisibleItemPosition();

                Log.i("totalItemCount",""+totalItemCount);
                Log.i("lastVisibleItem",""+(lastVisibleItem+visibleThreshold));

                if ((progress_lay.getVisibility()!=View.VISIBLE) && (totalItemCount <= (lastVisibleItem+visibleThreshold))){
                    Log.i("lastVisibleItem23",""+lastVisibleItem);
                    progress_lay.setVisibility(View.VISIBLE);
                    new getGroupsExtra().execute();
                }



            }
        });


        setSearchGroupList();

    }

    GridLayoutManager l1Layout=null;
    SearchGroupListAdapter searchGroupListAdapter=null;
    public void setSearchGroupList(){
        l1Layout = new GridLayoutManager(this, 2);
        search_groups_list.setHasFixedSize(true);
        search_groups_list.setLayoutManager(l1Layout);
        searchGroupListAdapter=new SearchGroupListAdapter(mContext,searchGroupObjects);
        search_groups_list.setAdapter(searchGroupListAdapter);


        search_groups_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleThreshold=1;
                int totalItemCount = l1Layout.getItemCount();
                int lastVisibleItem = l1Layout.findLastVisibleItemPosition();

                Log.i("totalItemCount",""+totalItemCount);
                Log.i("lastVisibleItem",""+(lastVisibleItem+visibleThreshold));

                if ((progress_lay_search.getVisibility()!=View.VISIBLE) && (totalItemCount <= (lastVisibleItem+visibleThreshold))){
                    Log.i("lastVisibleItem23",""+lastVisibleItem);
                    progress_lay_search.setVisibility(View.VISIBLE);
                    new getSearchedGroupsExtra().execute();
                }



            }
        });

    }


    ProgressDialog progressDialog = null;

    class getGroups extends AsyncTask<Void,Void,Void> {
        GroupsManager groupsManager;
        String responseString = "";
        String s = "Plase Wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();

            glastcount=String.valueOf(groupObjects.size());
        }

        @Override
        protected Void doInBackground(Void... params) {
            groupsManager=new GroupsManager(mContext);
            responseString =groupsManager.getGroups(glastcount);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (responseString.equals("100")) {

               // groupObjects.clear();
                groupObjects.addAll(groupsManager.groups);
                groupListAdapter.notifyDataSetChanged();

                if(groupObjects.size()==0){
                    list_lay.setVisibility(View.GONE);
                    no_group_txt.setVisibility(View.VISIBLE);
                }

                else{
                    no_group_txt.setVisibility(View.GONE);
                    list_lay.setVisibility(View.VISIBLE);
                    groups_count.setText("Groups ("+groupObjects.size()+")");
                }
                new getSearchedGroupsExtra().execute();

            } else {
                groups_count.setText("Groups (0)");
            }
        }
    }




    class getGroupsExtra extends AsyncTask<Void,Void,Void> {
        GroupsManager groupsManager;
        String responseString = "";
        String s = "Plase Wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            glastcount=String.valueOf(groupObjects.size());
        }

        @Override
        protected Void doInBackground(Void... params) {
            groupsManager=new GroupsManager(mContext);
            responseString =groupsManager.getGroups(glastcount);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress_lay.setVisibility(View.GONE);
            if (responseString.equals("100")) {

                // groupObjects.clear();
                groupObjects.addAll(groupsManager.groups);
                groupListAdapter.notifyDataSetChanged();

                if(groupObjects.size()==0){
                    list_lay.setVisibility(View.GONE);
                    no_group_txt.setVisibility(View.VISIBLE);
                }

                else{
                    no_group_txt.setVisibility(View.GONE);
                    list_lay.setVisibility(View.VISIBLE);
                    groups_count.setText("Groups ("+groupObjects.size()+")");
                }



            } else {
                groups_count.setText("Groups (0)");
            }
        }
    }


    class getSearchedGroups extends AsyncTask<Void,Void,Void> {
        SearchGroupsManager searchGroupsManager;
        String responseString = "";
        String s = "Plase Wait...";
        String search_str="";
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            search_str=grp_src.getText().toString();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            searchGroupsManager=new SearchGroupsManager(mContext);
            responseString =searchGroupsManager.getGroups(search_str,"0");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            grp_src.setText("");
            if (responseString.equals("100")) {

                searchGroupObjects.clear();
                searchGroupObjects.addAll(searchGroupsManager.searchGroupObjects);
                searchGroupListAdapter.notifyDataSetChanged();

                if(searchGroupObjects.size()==0){
                    list_lay.setVisibility(View.GONE);
                    no_group_txt.setVisibility(View.VISIBLE);
                }

                else{
                    no_group_txt.setVisibility(View.GONE);
                    list_lay.setVisibility(View.VISIBLE);

                }

            } else {

            }
        }
    }


    class getSearchedGroupsExtra extends AsyncTask<Void,Void,Void> {
        SearchGroupsManager searchGroupsManager;
        String responseString = "";
        String s = "Plase Wait...";
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            glastcount_search=String.valueOf(searchGroupObjects.size());
        }

        @Override
        protected Void doInBackground(Void... params) {
            searchGroupsManager=new SearchGroupsManager(mContext);
            responseString =searchGroupsManager.getGroups("",glastcount_search);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progress_lay_search.setVisibility(View.GONE);
            if (responseString.equals("100")) {

                //searchGroupObjects.clear();
                searchGroupObjects.addAll(searchGroupsManager.searchGroupObjects);
                searchGroupListAdapter.notifyDataSetChanged();

                if(searchGroupObjects.size()==0){
                    list_lay.setVisibility(View.GONE);
                    no_group_txt.setVisibility(View.VISIBLE);
                }

                else{
                    no_group_txt.setVisibility(View.GONE);
                    list_lay.setVisibility(View.VISIBLE);

                }

            } else {

            }
        }
    }



    public void sendJoinRequest(String coi_gid,String coi_isprivate){

    }






    @Override
    public void onBackPressed() {

        if(GPInfoActivity.isFromNotify!=null) {
            if(GPInfoActivity.isFromNotify.equals("yes")) {
                finish();
                //Toast.makeText(mContext,"backpressed",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(mContext,FlipComplexLayoutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
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
