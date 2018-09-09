package expression.sixdexp.com.expressionapp.xpassions.gpadapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import db.AllUsers;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.adapter.GroupListAdapter;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.xpassions.GPInfoActivity;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GroupJoinRequestManager;


/**
 * Created by Praveen on 13-Feb-18.
 */

public class GPUserListAdapter extends BaseAdapter {

    Context mContext;
    List<AllUsers> tagusers;
    String coi_gid="0";
    public interface TaggingCallback{
        public void setPosition(int pos);
    }

    GPUserListAdapter.TaggingCallback taggingCallback;


    public GPUserListAdapter(Context context, List<AllUsers> tagusers, GPUserListAdapter.TaggingCallback taggingCallback){
        mContext=context;
        this.tagusers=tagusers;
        this.taggingCallback=taggingCallback;
        if(GroupListAdapter.groupObject!=null){
            coi_gid=GroupListAdapter.groupObject.getCoi_gid();
        }


    }


    @Override
    public int getCount() {
        return tagusers.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final GPUserListAdapter.HolderView holder;


        if(convertView==null) {
            holder=new GPUserListAdapter.HolderView();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gpuserlist, parent, false);
            holder.parent_lay=(LinearLayout)convertView.findViewById(R.id.parent_lay);
            holder.name=(TextView)convertView.findViewById(R.id.name);
            holder.user_desg=(TextView)convertView.findViewById(R.id.user_desg);
            holder.cancel=(ImageView)convertView.findViewById(R.id.cancel);
            holder.user_img=(CircularImageView) convertView.findViewById(R.id.user_img);
            convertView.setTag(holder);
        }

        else{
            holder=(GPUserListAdapter.HolderView)convertView.getTag();
        }


        final int pos=position;
        holder.name.setText(""+tagusers.get(position).getName());
        holder.user_desg.setText(""+tagusers.get(position).getDesignation());


        String reUserID=tagusers.get(position).getUser_id();
        if(GroupListAdapter.groupObject.getIscreatedby().equals(reUserID)){
            holder.cancel.setVisibility(View.GONE);
        }

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //taggingCallback.setPosition(pos);
                String requestUserID=tagusers.get(position).getUser_id();
                gpUseredit(holder.parent_lay,requestUserID,pos);

            }
        });


        try {

            String image_url = tagusers.get(position).getImageurl();
            String user_image_url = image_url.replace(" ", "%20");
            Log.i("457669 url",user_image_url);
            Picasso.with(mContext)
                    .load(user_image_url)
                    .centerInside()
                    .resize(50,50)
                    .onlyScaleDown()
                    .placeholder(R.drawable.user_default)
                    .error(R.drawable.user_default)
                    .into(holder.user_img);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return  convertView;
    }

    public class HolderView{

        TextView name,user_desg;
        ImageView cancel;
        CircularImageView user_img;
        LinearLayout parent_lay;

    }
    String action_user="";
    String action_xp="";
    PopupWindow changeSortPopUp1=null;
    public void gpUseredit(View pinView, final String requestUserID, final int position){

        changeSortPopUp1 = new PopupWindow(mContext);
        changeSortPopUp1.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.gpusereditopdialog, null);


        TextView del_user=(TextView) layout.findViewById(R.id.del_user);
        TextView make_admin=(TextView) layout.findViewById(R.id.make_admin);
        TextView xpress_yesno=(TextView) layout.findViewById(R.id.xpress_yesno);

        AllUsers allUsers=tagusers.get(position);

        if(allUsers.getMxway().equals("1")){
            xpress_yesno.setText("Xpressway Disable");
            action_xp="xpressway_no";
        }
        else{
            xpress_yesno.setText("Xpressway Enable");
            action_xp="xpressway_yes";
        }

        if(allUsers.getCoiMisAdmin().equals("1")){
            make_admin.setText("Make Normal User");
            action_user="normal";
        }
        else{
            make_admin.setText("Make Admin");
            action_user="isadmin";
        }

        del_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             changeSortPopUp1.dismiss();
             new RequestGP(requestUserID,"del",position).execute();
            }
        });

        make_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSortPopUp1.dismiss();
                new RequestGP(requestUserID,action_user,position).execute();
            }
        });

        xpress_yesno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSortPopUp1.dismiss();
                new RequestGP(requestUserID,action_xp,position).execute();
            }
        });

        Rect rc = new Rect();
        pinView.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        pinView.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        changeSortPopUp1.setAnimationStyle(R.style.socialshareanim);
        changeSortPopUp1.setContentView(layout);

        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;



        changeSortPopUp1.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
       /* changeSortPopUp1.setHeight((int) (height / 1.3));*/
        changeSortPopUp1.setFocusable(true);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y =  -100;
        changeSortPopUp1.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp1.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);

        changeSortPopUp1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
    }


    ProgressDialog progressDialog = null;
    class RequestGP extends AsyncTask<String,Void,Void> {
        GroupJoinRequestManager groupJoinRequestManager;
        String responseString = "";
        String s = "Plase Wait...";
        String requestUserID="";
        String actionType="";
        int position;
        public RequestGP(String requestUserID,String actionType,int position){
            this.requestUserID=requestUserID;
            this.actionType=actionType;
            this.position=position;
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
                if(actionType.equalsIgnoreCase("del")) {
                    taggingCallback.setPosition(position);
                }

                else {
                    AllUsers allUsers = tagusers.get(position);

                    if (actionType.equals("xpressway_no")) {
                        allUsers.setMxway("0");
                    } else if (actionType.equals("xpressway_yes")) {
                        allUsers.setMxway("1");
                    } else if (actionType.equals("normal")) {
                        allUsers.setCoiMisAdmin("0");
                    } else if (actionType.equals("isadmin")) {
                        allUsers.setCoiMisAdmin("1");
                    }

                }

                GPInfoActivity.isfromGpinfo=true;

               // new GPInfoActivity.GetGPDetails().execute();
                //Toast.makeText(mContext,"Join request wait for admin approval.",Toast.LENGTH_SHORT).show();


            } else {

            }
        }
    }


}


