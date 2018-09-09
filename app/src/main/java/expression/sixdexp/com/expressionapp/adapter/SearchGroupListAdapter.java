package expression.sixdexp.com.expressionapp.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.xpassions.GroupHome;
import expression.sixdexp.com.expressionapp.xpassions.GroupListActivity;
import expression.sixdexp.com.expressionapp.xpassions.SearchGPDetails;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GPVisitorManager;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GPWallPostManager;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.XPGPWallPostManager;
import model.GroupObject;
import model.SearchGroupObject;

/**
 * Created by Praveen on 04-Jan-18.
 */

public class SearchGroupListAdapter extends RecyclerView.Adapter<SearchGroupListAdapter.RecyclerViewHolders> {

    private List<SearchGroupObject> itemList;
    private Context context;

    public SearchGroupListAdapter(Context context, List<SearchGroupObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public SearchGroupListAdapter.RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_group_card_view, null);
        SearchGroupListAdapter.RecyclerViewHolders rcv = new SearchGroupListAdapter.RecyclerViewHolders(layoutView);
        return rcv;
    }



    @Override
    public void onBindViewHolder(SearchGroupListAdapter.RecyclerViewHolders holder, final int position) {

        GradientDrawable rect_Shape = (GradientDrawable) holder.btn_lay.getBackground();






        SearchGroupObject groupObject=itemList.get(position);
        holder.group_name.setText(groupObject.getCoi_gtitle());
        holder.member_count.setText(groupObject.getNoofmember());
        holder.post_count.setText(groupObject.getNoofpost());



        if(groupObject.getThemeid().equals("1"))
            holder.color_line.setBackgroundColor(Color.parseColor("#E74C3C"));
        else if(groupObject.getThemeid().equals("2"))
            holder.color_line.setBackgroundColor(Color.parseColor("#16A085"));
        else if(groupObject.getThemeid().equals("3"))
            holder.color_line.setBackgroundColor(Color.parseColor("#E67E22"));
        else if(groupObject.getThemeid().equals("4"))
            holder.color_line.setBackgroundColor(Color.parseColor("#3398DB"));
        else if(groupObject.getThemeid().equals("5"))
            holder.color_line.setBackgroundColor(Color.parseColor("#34495E"));

         if(groupObject.getCoi_gispublic().equalsIgnoreCase("Close") || groupObject.getCoi_gispublic().equalsIgnoreCase("1")){
           /* holder.btn_txt.setText("Request to Join");
            rect_Shape.setColor(Color.parseColor("#E67F24"));
            holder.gp_type_ic.setImageResource(R.drawable.request_gp_ic);*/
            holder.isprivate_lock.setVisibility(View.VISIBLE);
        }

        else{
             holder.isprivate_lock.setVisibility(View.GONE);
         }

       /* if(groupObject.getCoi_gispublic().equalsIgnoreCase("Public") || groupObject.getCoi_gispublic().equalsIgnoreCase("0")){
           *//* holder.btn_txt.setText("Join");
            rect_Shape.setColor(Color.parseColor("#3BAE8C"));
            holder.gp_type_ic.setImageResource(R.drawable.join_gp_ic);*//*
        }

        else if(groupObject.getCoi_gispublic().equalsIgnoreCase("Close") || groupObject.getCoi_gispublic().equalsIgnoreCase("1")){
           *//* holder.btn_txt.setText("Request to Join");
            rect_Shape.setColor(Color.parseColor("#E67F24"));
            holder.gp_type_ic.setImageResource(R.drawable.request_gp_ic);*//*
            holder.isprivate_lock.setVisibility(View.VISIBLE);
        }

        else if(groupObject.getCoi_gispublic().equalsIgnoreCase("requested")){
           *//* holder.btn_txt.setText("Requested");
            rect_Shape.setColor(Color.parseColor("#E74D3E"));
            holder.gp_type_ic.setImageResource(R.drawable.requested_gp_ic);*//*
        }*/

     /*   holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SearchGroupObject groupObject=itemList.get(position);
                Intent intent=new Intent(context, SearchGPDetails.class);
                intent.putExtra("coi_gid",groupObject.getCoi_gid());
                ((Activity)context).startActivity(intent);

                *//*SearchGroupObject groupObject=itemList.get(position);

                String coi_isprivate=groupObject.getCoi_gisprivate();
                if(coi_isprivate.equalsIgnoreCase("Public")){
                    coi_isprivate="0";
                    ((GroupListActivity)context).sendJoinRequest(groupObject.getCoi_gid(),coi_isprivate);
                }
                else if(coi_isprivate.equalsIgnoreCase("Close")){
                    coi_isprivate="1";
                    ((GroupListActivity)context).sendJoinRequest(groupObject.getCoi_gid(),coi_isprivate);
                }

                else if(coi_isprivate.equalsIgnoreCase("requested")){
                    Toast.makeText(context,"Request is pending...",Toast.LENGTH_SHORT).show();
                }
*//*
            }
        });*/





       String image_url = groupObject.getCoi_gicon();
        URI gp_uri = null;
        try {
            gp_uri = new URI(image_url.replace(" ", "%20"));
            if (gp_uri.toString().length()!=0){
                Glide.with(context)
                        .load(gp_uri.toString())
                        .placeholder(R.drawable.group_icon_default)
                        .error(R.drawable.group_icon_default)
                        .crossFade()
                        .thumbnail(0.1f)
                        .centerCrop()
                        .into(holder.gp_photo);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    int position=0;
    String gpID;
    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView group_name,member_count,btn_txt,post_count;
        public ImageView gp_photo,gp_type_ic,isprivate_lock;
        RelativeLayout btn_lay;
        View color_line;
        LinearLayout card_view;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            group_name = (TextView)itemView.findViewById(R.id.group_name);
            member_count = (TextView)itemView.findViewById(R.id.member_count);
            post_count= (TextView)itemView.findViewById(R.id.post_count);
            btn_txt= (TextView)itemView.findViewById(R.id.btn_txt);
            gp_photo = (ImageView)itemView.findViewById(R.id.gp_photo);
            isprivate_lock= (ImageView)itemView.findViewById(R.id.isprivate_lock);
            gp_type_ic= (ImageView)itemView.findViewById(R.id.gp_type_ic);
            btn_lay=(RelativeLayout)itemView.findViewById(R.id.btn_lay);
            color_line=(View)itemView.findViewById(R.id.color_line);
            card_view=(LinearLayout)itemView.findViewById(R.id.card_view);
        }

        @Override
        public void onClick(View view) {
            //Toast.makeText(view.getContext(), "Clicked Country Position = " + getPosition(), Toast.LENGTH_SHORT).show();

            position=getPosition();
            gpID=itemList.get(position).getCoi_gid();
            new GetWallPostTask().execute();
        }
    }



    ProgressDialog progressDialog;

    public class GetWallPostTask extends AsyncTask<String, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(context);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new GPWallPostManager(context).callGPWallPost(gpID);
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
            try {
                new GetXPWallPostTask().execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public class GetXPWallPostTask extends AsyncTask<String, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new XPGPWallPostManager(context).callxpGPWallPost(gpID);
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
            try {
                progressDialog.dismiss();

                SearchGroupObject searchGroupObject=itemList.get(position);;
                GroupObject groupObject_tem=new GroupObject();


                String coi_gid=searchGroupObject.getCoi_gid();
                String coi_gtitle=searchGroupObject.getCoi_gtitle();
                String coi_gdescription=searchGroupObject.getCoi_gdescription();
                String coi_gicon=searchGroupObject.getCoi_gicon();
                String coi_gisactive=searchGroupObject.getCoi_gisactive();
                String coi_gadminid=searchGroupObject.getCoi_gadminid();
                String coi_gispublic=searchGroupObject.getCoi_gispublic();
                String coi_gaddeddate=searchGroupObject.getCoi_gaddeddate();
                String noofmember=searchGroupObject.getNoofmember();
                String noofpendingrequest=searchGroupObject.getNoofpendingrequest();
                String noofview=searchGroupObject.getNoofview();
                String themeid=searchGroupObject.getThemeid();
                String noofpost=searchGroupObject.getNoofpost();
                String iscreatedby=searchGroupObject.getIscreatedby();

                groupObject_tem.setCoi_gid(coi_gid);
                groupObject_tem.setCoi_gtitle(coi_gtitle);
                groupObject_tem.setCoi_gdescription(coi_gdescription);
                groupObject_tem.setCoi_gicon(coi_gicon);
                groupObject_tem.setCoi_gisactive(coi_gisactive);
                groupObject_tem.setCoi_gadminid(coi_gadminid);
                groupObject_tem.setCoi_gispublic(coi_gispublic);
                groupObject_tem.setCoi_gaddeddate(coi_gaddeddate);
                groupObject_tem.setNoofmember(noofmember);
                groupObject_tem.setNoofpendingrequest(noofpendingrequest);
                groupObject_tem.setNoofview(noofview);
                groupObject_tem.setThemeid(themeid);
                groupObject_tem.setNoofpost(noofpost);
                groupObject_tem.setIscreatedby(iscreatedby);

                GroupListAdapter.groupObject=groupObject_tem;

                Intent intent=new Intent(context, GroupHome.class);
                context.startActivity(intent);
                /*if (responseString.equals("100")) {
                    groupObject=itemList.get(position);
                    Intent intent=new Intent(context, GroupHome.class);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, responseString, Toast.LENGTH_LONG).show();

                }*/
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
        }
    }

    public class GetGPVisitorCount extends AsyncTask<String, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new GPVisitorManager(context).callGPVisitorCount();
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
            try {

                Intent intent=new Intent(context, GroupHome.class);
                context.startActivity(intent);
                /*if (responseString.equals("100")) {
                    groupObject=itemList.get(position);
                    Intent intent=new Intent(context, GroupHome.class);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, responseString, Toast.LENGTH_LONG).show();

                }*/
            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
            }
        }
    }

}

