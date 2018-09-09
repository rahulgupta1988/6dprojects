package com.expression.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import db.BulletinCategory;
import db.BulletinList;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.adapter.XPBulletinAdapter;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.manager.XPBulletinManager;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;

public class TabFragment5 extends Fragment
{
    View view;
    Context mContext;
    Spinner select_category;
    EditText searc_edit;
    ImageView searc_icon;
    TextView no_data_txt;
    LinearLayout progress_lay;
    private AlertDialog myDialog;
    List<BulletinCategory> allbulletinCategories;
    Map<String,String> catMap=new LinkedHashMap<>();
    String select_category_id="";
    ListView bulletin_list;
    boolean isSearch=false;
    String searchKey="";
    int loadinMore=0;
    int startindex=1;
    int pagesize=10;



    List<BulletinList>  bulletinLists;
    public TabFragment5(){};

    int flag=1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view= inflater.inflate(R.layout.tab_fragment_5, container, false);
        allbulletinCategories=new GetMorePostManager(mContext).getBulletinCategories();
        for (int x=0;x<allbulletinCategories.size();x++)
        {
            Log.i("cat list 32525",""+allbulletinCategories.get(x).getName());
            catMap.put(allbulletinCategories.get(x).getName(),allbulletinCategories.get(x).getId());
        }

        initViews();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public void initViews(){
        progress_lay=(LinearLayout)view.findViewById(R.id.progress_lay);
        no_data_txt=(TextView)view.findViewById(R.id.no_data_txt);
        searc_icon=(ImageView) view.findViewById(R.id.searc_icon);
        searc_edit=(EditText)view.findViewById(R.id.searc_edit);
        bulletin_list=(ListView)view.findViewById(R.id.bulletin_list);
        select_category=(Spinner)view.findViewById(R.id.select_category);


        searc_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchKey=searc_edit.getText().toString().trim();
                if(!searchKey.equals("")){
                    isSearch=true;
                    loadinMore=2;
                    startindex=1;
                    new BulletinServerTask().execute();
                }
            }
        });


        bulletin_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int mLastFirstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int lastItem = firstVisibleItem + visibleItemCount;

                if((lastItem == totalItemCount && (mLastFirstVisibleItem<firstVisibleItem) && !isSearch))
                {
                    isSearch=false;
                    loadinMore=1;
                    startindex=startindex+1;
                    disableViews();
                    new BulletinServerTaskByBottomProgress().execute();
                }

                mLastFirstVisibleItem=firstVisibleItem;
            }
        });

        setCategory();
    }

    public void setCategory(){

        List<String> productTitle1=new ArrayList<String>(catMap.keySet());
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext,R.layout.spennertext_item, productTitle1);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_category.setAdapter(dataAdapter);
        select_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   String catName=dataAdapter.getItem(position);
                    select_category_id=catMap.get(catName);
                    isSearch=false;
                    loadinMore=0;
                    startindex=1;
                   /* if(flag>1)*/
                    new BulletinServerTask().execute();
                    //else flag++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    XPBulletinAdapter xpBulletinAdapter=null;
    public class BulletinServerTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        ProgressDialog progressDialog=null;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                if(isSearch){
                    responseString= new XPBulletinManager(mContext).callBulletinListBySearchKey(searchKey,loadinMore);
                }
                else  {
                        responseString= new XPBulletinManager(mContext).callBulletinList(select_category_id,loadinMore,
                                String.valueOf(startindex),String.valueOf(pagesize));
                  }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            try {
                progressDialog.dismiss();
                if (responseString.equals("100")){
                    searc_edit.setText("");
                    bulletinLists=new XPBulletinManager(mContext).getBulletinList();
                    if(loadinMore==0 ||loadinMore==2){
                        if(bulletinLists!=null && bulletinLists.size()>0){
                            no_data_txt.setVisibility(View.GONE);
                            bulletin_list.setVisibility(View.VISIBLE);
                            xpBulletinAdapter=new XPBulletinAdapter(mContext,bulletinLists);
                            bulletin_list.setAdapter(xpBulletinAdapter);
                        }
                        else{
                            bulletin_list.setVisibility(View.GONE);
                            no_data_txt.setVisibility(View.VISIBLE);
                        }
                    }

                    else{
                        bulletin_list.deferNotifyDataSetChanged();
                    }


                }

                else {
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public class BulletinServerTaskByBottomProgress extends AsyncTask<String, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progress_lay.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                if(isSearch){
                    responseString= new XPBulletinManager(mContext).callBulletinListBySearchKey(searchKey,loadinMore);
                }
                else  {
                    responseString= new XPBulletinManager(mContext).callBulletinList(select_category_id,loadinMore,
                            String.valueOf(startindex),String.valueOf(pagesize));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            try {
                enableViews();
                progress_lay.setVisibility(View.GONE);
                if (responseString.equals("100")){
                    searc_edit.setText("");
                    int tempSize=bulletinLists.size();
                    if(new XPBulletinManager(mContext).getBulletinList().size()>tempSize){
                       List<BulletinList> temp_list= new XPBulletinManager(mContext).getBulletinList();
                        for(int i=tempSize;i<(temp_list.size());i++){
                            Log.i("iterate",""+i);
                            bulletinLists.add(temp_list.get(i));
                        }
                    }
                    else{
                        startindex=startindex-1;
                    }

                        Log.i("data changed","12345");
                        xpBulletinAdapter.notifyDataSetChanged ();



                }

                else {
                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                    startindex=startindex-1;

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void enableViews(){
        select_category.setEnabled(true);
        searc_edit.setEnabled(true);
        searc_icon.setEnabled(true);
    }
    public void disableViews(){
        select_category.setEnabled(false);
        searc_edit.setEnabled(false);
        searc_icon.setEnabled(false);
    }





}