package com.expression.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aphidmobile.flip.FlipViewController;

import java.util.List;

import db.AllWebNotification;
import expression.sixdexp.com.expressionapp.EventdetailView;
import expression.sixdexp.com.expressionapp.OtherPollDetails_inner_notifyActivty;
import expression.sixdexp.com.expressionapp.PolicyCommentActivity;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.adapter.GetMorePostAdapter;
import expression.sixdexp.com.expressionapp.adapter.WebNotificationAdapter;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;
import expression.sixdexp.com.expressionapp.manager.SinglePostManager;
import expression.sixdexp.com.expressionapp.utility.InternetConnectionDetector;
import expression.sixdexp.com.expressionapp.utility.NotificationPostDeital_new;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;

public class TabFragment2 extends Fragment {
    private FlipViewController flipView;
    //List<GetMorePost> getMorePosts;
    GetMorePostAdapter getMorePostAdapter;
    LinearLayout linear;
    View view;
    ProgressDialog progressDialog;
    Context mContext;
    ListView notification_list;
    ImageView readall_not_ic;
    TextView nodata_txt;

    public TabFragment2(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        view= inflater.inflate(R.layout.notification_page, container, false);
        mContext = getActivity();
        linear = (LinearLayout)view.findViewById(R.id.linear);
        notification_list = (ListView)view.findViewById(R.id.notification_list);
        readall_not_ic=(ImageView)view.findViewById(R.id.readall_not_ic);
        nodata_txt=(TextView)view.findViewById(R.id.nodata_txt);
        notification_list.setDivider(null);
       /* if (new InternetConnectionDetector(mContext).isConnectingToInternet())
        {
            new NotificationListTask().execute();
        }
        else {
            showsnack(mContext.getString(R.string.nework_connect_error));

        }
*/

        readall_not_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postId="0";
                notificationmasterid="0";
                new GetReadNotification().execute();
            }
        });

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (new InternetConnectionDetector(mContext).isConnectingToInternet())
        {
            new NotificationListTask().execute();
        }
        else {
            showsnack(mContext.getString(R.string.nework_connect_error));

        }
    }


    public class NotificationListTask extends AsyncTask<String, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new GetMorePostManager(mContext).GetWebNotificationList();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try
            {
                if(progressDialog!=null)
                progressDialog.dismiss();
                if (responseString.equals("100"))
                {
                    final List<AllWebNotification> allWebNotifications;
                    allWebNotifications=new GetMorePostManager(getActivity()).getAllWebNotifications();

                    if(allWebNotifications.size()==0){
                        nodata_txt.setVisibility(View.VISIBLE);
                        notification_list.setVisibility(View.GONE);
                    }

                    else {
                        nodata_txt.setVisibility(View.GONE);
                        notification_list.setVisibility(View.VISIBLE);
                    }

                    notification_list.setAdapter(new WebNotificationAdapter(mContext));
                    notification_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            List<AllWebNotification> allWebNotifications = new GetMorePostManager(mContext).getAllWebNotifications();
                            AllWebNotification webNotification=allWebNotifications.get(position);
                            String notificationTypeId = webNotification.getNotificationTypeId();

                            //Toast.makeText(mContext,""+notificationTypeId,Toast.LENGTH_SHORT).show();

                            if (notificationTypeId.equalsIgnoreCase("1")
                                    || notificationTypeId.equalsIgnoreCase("2")
                                    ||notificationTypeId.equalsIgnoreCase("3")||
                                    notificationTypeId.equalsIgnoreCase("4")
                                    ||notificationTypeId.equalsIgnoreCase("13")){

                                Intent detailsActitivty = new Intent(mContext, NotificationPostDeital_new.class);
                                detailsActitivty.putExtra("postId", allWebNotifications.get(position).getId());
                                detailsActitivty.putExtra("notificationmasterid", allWebNotifications.get(position).getNotificationTypeId());
                                detailsActitivty.putExtra("recoid", allWebNotifications.get(position).getRecognition_id());
                                mContext.startActivity(detailsActitivty);
                            }

                            else if (notificationTypeId.equalsIgnoreCase("6") || notificationTypeId.equalsIgnoreCase("7")
                                    || notificationTypeId.equalsIgnoreCase("8")){

                                Intent detailsActitivty = new Intent(mContext, EventdetailView.class);
                                detailsActitivty.putExtra("postId", allWebNotifications.get(position).getId());
                                detailsActitivty.putExtra("notificationmasterid", allWebNotifications.get(position).getNotificationTypeId());
                                detailsActitivty.putExtra("recoid", allWebNotifications.get(position).getRecognition_id());
                                mContext.startActivity(detailsActitivty);
                            }
                            else if (notificationTypeId.equalsIgnoreCase("5")){
                                Intent pollIntent = new Intent(mContext,OtherPollDetails_inner_notifyActivty.class);
                                pollIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                pollIntent.putExtra("postId", allWebNotifications.get(position).getId());
                                pollIntent.putExtra("notificationmasterid", allWebNotifications.get(position).getNotificationTypeId());
                                pollIntent.putExtra("pollid", allWebNotifications.get(position).getRecognition_id());
                                mContext.startActivity(pollIntent);
                        }

                            else if (notificationTypeId.equalsIgnoreCase("11")){
                                Intent pollIntent = new Intent(mContext,PolicyCommentActivity.class);
                                pollIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                pollIntent.putExtra("postId", allWebNotifications.get(position).getId());
                                pollIntent.putExtra("notificationmasterid", allWebNotifications.get(position).getNotificationTypeId());
                                pollIntent.putExtra("policyid", allWebNotifications.get(position).getRecognition_id());
                                mContext.startActivity(pollIntent);
                            }


                            else{

                                Toast.makeText(mContext,"Coming Soon...",Toast.LENGTH_SHORT).show();
                                postId= allWebNotifications.get(position).getId();
                                notificationmasterid=allWebNotifications.get(position).getNotificationTypeId();
                                new GetReadNotification().execute();
                            }

                        }
                    });

                }
                else {

                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void showsnack(String msg)
    {
        Snackbar snackbar = Snackbar
                .make(linear,msg, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });

        snackbar.setActionTextColor(Color.RED);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    String postId,notificationmasterid;
    public class GetReadNotification extends AsyncTask<String, Void, Void> {
        String responseString = "";
        String s = "Please wait...";
        @Override
        protected void onPreExecute() {

                progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);


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

            if (responseString.equals("100")) {

                new NotificationListTask().execute();

            } else {
                progressDialog.dismiss();
                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
            }
        }
    }




}