package expression.sixdexp.com.expressionapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.xpassions.GroupHome;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GPVisitorManager;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GPWallPostManager;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.XPGPWallPostManager;
import model.GroupObject;

/**
 * Created by Praveen on 03-Jan-18.
 */

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.RecyclerViewHolders> {

    private List<GroupObject> itemList;
    private Context context;
    public static GroupObject groupObject=null;


    public GroupListAdapter(Context context, List<GroupObject> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_card_view, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {

        GroupObject groupObject=itemList.get(position);
        holder.group_name.setText(groupObject.getCoi_gtitle());
        holder.member_count.setText(groupObject.getNoofmember());
        holder.post_count.setText(groupObject.getNoofpost());


        if(groupObject.getCoi_gispublic().equalsIgnoreCase("Close") || groupObject.getCoi_gispublic().equalsIgnoreCase("1")){
           /* holder.btn_txt.setText("Request to Join");
            rect_Shape.setColor(Color.parseColor("#E67F24"));
            holder.gp_type_ic.setImageResource(R.drawable.request_gp_ic);*/
            holder.isprivate_lock.setVisibility(View.VISIBLE);
        }

        else{
            holder.isprivate_lock.setVisibility(View.GONE);
        }




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

        public TextView group_name,member_count,post_count;
        public ImageView gp_photo,isprivate_lock;
        View color_line;

        public RecyclerViewHolders(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            group_name = (TextView)itemView.findViewById(R.id.group_name);
            member_count= (TextView)itemView.findViewById(R.id.member_count);
            post_count= (TextView)itemView.findViewById(R.id.post_count);
            gp_photo = (ImageView)itemView.findViewById(R.id.gp_photo);
            color_line=(View)itemView.findViewById(R.id.color_line);
            isprivate_lock= (ImageView)itemView.findViewById(R.id.isprivate_lock);
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
            try {
                progressDialog.dismiss();
                groupObject=itemList.get(position);
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

                groupObject=itemList.get(position);
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
