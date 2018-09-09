package expression.sixdexp.com.expressionapp.xpassions;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.adapter.GroupListAdapter;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.ViewPagerTab;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GPDeleteManager;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GropuActionManager;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GroupJoinRequestManager;

/**
 * Created by Praveen on 05-Jan-18.
 */

public class GroupHome extends FragmentActivity implements View.OnClickListener{

    Context mContext;
    private PagerSlidingTabStrip tabs;
    public  ViewPager pager;
    ImageView setting;
    LinearLayout header1,header2;
    de.hdodenhof.circleimageview.CircleImageView gpimag;
    ImageView back_ic;
    TextView gp_name,member_count,view_count;

    String coi_gid="",coi_gtitle="",noofmember="",noofview="",coi_gicon="",isgpAdminID="";
    FloatingActionMenu fabMenu;
    FrameLayout frameLayout;
    public  static boolean IsfromGroupInfo=false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grouphome);
        mContext=this;
        initViews();
        new RequestGPAction().execute();


    }

    public void initViews(){
        back_ic=(ImageView)findViewById(R.id.back_ic);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        setting=(ImageView)findViewById(R.id.setting);

        header1=(LinearLayout)findViewById(R.id.header1);
        header2=(LinearLayout)findViewById(R.id.header2);



        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingMenu();
            }
        });
        back_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fabMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        setFlot();
        frameLayout.getBackground().setAlpha(0);
    }

    public void setFlot() {
        createCustomAnimation();
        fabMenu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                fabMenu.getMenuIconView().setImageResource(fabMenu.isOpened()
                        ? R.drawable.ic_close : R.drawable.floating_icon);
                if (opened) {
                    fabMenu.setClickable(true);
                    frameLayout.getBackground().setAlpha(240);
                } else {
                    fabMenu.setClickable(false);
                    frameLayout.getBackground().setAlpha(0);
                }
            }
        });
    }


    public void setGpInfo(){
        if(GropuActionManager.gpActionModel==null){
           return;
        }

        isgpAdminID=GropuActionManager.gpActionModel.getIsadmin();
        coi_gid=GropuActionManager.gpActionModel.getCoi_gid();
        coi_gtitle=GropuActionManager.gpActionModel.getCoi_gtitle();
        noofmember=GropuActionManager.gpActionModel.getNoofmember();
        noofview=GropuActionManager.gpActionModel.getNoofview();
        coi_gicon=GropuActionManager.gpActionModel.getCoi_gicon();

        gpimag=(de.hdodenhof.circleimageview.CircleImageView)findViewById(R.id.gpimag);
        gp_name=(TextView)findViewById(R.id.gp_name);
        member_count=(TextView)findViewById(R.id.member_count);
        view_count=(TextView)findViewById(R.id.view_count);

        gp_name.setText(coi_gtitle);
        member_count.setText(noofmember);
        view_count.setText(noofview);


        String image_url =coi_gicon;
        URI gp_uri = null;
        try {
            gp_uri = new URI(image_url.replace(" ", "%20"));
            if (gp_uri.toString().length()!=0){
                /*Glide.with(mContext)
                        .load(gp_uri.toString())
                        .placeholder(R.drawable.group_icon_default)
                        .error(R.drawable.group_icon_default)
                        .crossFade()
                        .thumbnail(0.1f)
                        //.centerCrop()
                        .into(gpimag);*/


                Picasso.with(mContext)
                        .load(gp_uri.toString())
                        .resize(100, 100)
                        .centerInside()
                        .placeholder(R.drawable.group_icon_default)
                        .into(gpimag);

            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    public void setTheam(){
        if(GropuActionManager.gpActionModel.getThemeid().equals("1")){
            header1.setBackgroundColor(Color.parseColor("#E74C3C"));
            header2.setBackgroundColor(Color.parseColor("#E74C3C"));
        }

        else if(GropuActionManager.gpActionModel.getThemeid().equals("2")){
            header1.setBackgroundColor(Color.parseColor("#16A085"));
            header2.setBackgroundColor(Color.parseColor("#16A085"));
        }

        else if(GropuActionManager.gpActionModel.getThemeid().equals("3")){
            header1.setBackgroundColor(Color.parseColor("#E67E22"));
            header2.setBackgroundColor(Color.parseColor("#E67E22"));
        }

        else if(GropuActionManager.gpActionModel.getThemeid().equals("4")){
            header1.setBackgroundColor(Color.parseColor("#3398DB"));
            header2.setBackgroundColor(Color.parseColor("#3398DB"));
        }

        else if(GropuActionManager.gpActionModel.getThemeid().equals("5")){
            header1.setBackgroundColor(Color.parseColor("#34495E"));
            header2.setBackgroundColor(Color.parseColor("#34495E"));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(IsfromGroupInfo){
            IsfromGroupInfo=false;
            new RequestGPAction().execute();
        }



    }

    @Override
    public void onClick(View v) {

    }

    TabAdapter adapter=null;

    public void createTabs(){
        ArrayList<ViewPagerTab> tabsList = new ArrayList<>();

        String isnoatmember=GropuActionManager.gpActionModel.getIsnoatmember();

        if(isnoatmember.equalsIgnoreCase("yes")){
            frameLayout.setVisibility(View.VISIBLE);
        }

        else{
            frameLayout.setVisibility(View.GONE);
            guestAlert();
        }

        tabsList.add(new ViewPagerTab("homeselector", 0));
        tabsList.add(new ViewPagerTab("xpresswayselector", 0));
        tabsList.add(new ViewPagerTab("whatstredingselector", 0));
        tabsList.add(new ViewPagerTab("pollselector", 0));

        adapter = new TabAdapter(getSupportFragmentManager(), tabsList, mContext);


        pager.setAdapter(adapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);


    }

    PopupWindow changeSortPopUp=null;
    String actionType="";
    String gpdelmsg="";
    public void settingMenu() {
        changeSortPopUp = new PopupWindow(mContext);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.groupsettingmenu, null);

        LinearLayout gp_info=(LinearLayout)layout.findViewById(R.id.gp_info);
        LinearLayout themes=(LinearLayout)layout.findViewById(R.id.themes);
        LinearLayout delete_gp=(LinearLayout)layout.findViewById(R.id.delete_gp);
        TextView delt_txt=(TextView)layout.findViewById(R.id.delt_txt);


        String coi_gispublic=GropuActionManager.gpActionModel.getCoi_gispublic();
        String privategsatus=GropuActionManager.gpActionModel.getPrivategsatus();
        String isadmin=GropuActionManager.gpActionModel.getIsadmin();
        String isnoatmember=GropuActionManager.gpActionModel.getIsnoatmember();

        String gpCreatorID=GroupListAdapter.groupObject.getIscreatedby();
        String logedInUserID=new LoginManager(mContext).getUserInfo().get(0).getUserid();


        if(isnoatmember.equalsIgnoreCase("no")){
            themes.setEnabled(false);
        }




        //Suspend Group
        if((coi_gispublic.equalsIgnoreCase("Public")||coi_gispublic.equalsIgnoreCase("Close"))
                && privategsatus.equals("1")
                &&isadmin.equalsIgnoreCase("yes")
                && isnoatmember.equalsIgnoreCase("yes")){

            if(gpCreatorID.equals(logedInUserID)){
                delt_txt.setText("Suspend Group");
                actionType="suspend";
            }

            else{
                delt_txt.setText("Leave Group");
                actionType="leave";
            }

        }

        //Leave Group
        else if((coi_gispublic.equalsIgnoreCase("Public")||coi_gispublic.equalsIgnoreCase("Close"))
                && privategsatus.equals("1")
                &&isadmin.equalsIgnoreCase("no")
                && isnoatmember.equalsIgnoreCase("yes")){
            delt_txt.setText("Leave Group");
            actionType="leave";
        }

        //Requested Group
        else if(coi_gispublic.equalsIgnoreCase("Close")
                && privategsatus.equals("-1")
                &&isadmin.equalsIgnoreCase("no")
                && isnoatmember.equalsIgnoreCase("no")){
            delt_txt.setText("Requested");
            actionType="requested";

        }


        //Deactive Group
        else if(coi_gispublic.equalsIgnoreCase("Close")
                && privategsatus.equals("1")
                &&isadmin.equalsIgnoreCase("no")
                && isnoatmember.equalsIgnoreCase("no")){
            delt_txt.setText("Deactive");
            actionType="deactive";

        }


        else {

            delt_txt.setText("Join");
            actionType="join";


        }


        gp_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IsfromGroupInfo=true;
                Intent gpinfoIntent= new Intent(mContext,GPInfoActivity.class);
                startActivity(gpinfoIntent);
                changeSortPopUp.dismiss();
            }
        });

        themes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent themeIntent= new Intent(mContext,ThemeActivity.class);
                startActivityForResult(themeIntent,701);
                changeSortPopUp.dismiss();
            }
        });


        delete_gp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSortPopUp.dismiss();
                if(actionType.equals("join") || actionType.equals("leave") || actionType.equals("suspend"))
                new RequestGP("").execute();
                //deleteDailogBox(mContext,gpdelmsg);
            }
        });


        Rect rc = new Rect();
        setting.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        setting.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        // Creating the PopupWindow


        changeSortPopUp.setAnimationStyle(R.style.animationName);
        changeSortPopUp.setContentView(layout);
        changeSortPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeSortPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeSortPopUp.setFocusable(true);

        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y = (int) setting.getY();

        // Clear the default translucent background
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    class DeleteGroup extends AsyncTask<Void,Void,Void> {
        GPDeleteManager deleteManager;
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
            deleteManager=new GPDeleteManager(mContext);
            responseString =deleteManager.deleteGP(coi_gid);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (responseString.equals("100")) {
                Toast.makeText(mContext,"Group has been Suspended successfully.",Toast.LENGTH_SHORT).show();
                GroupListActivity.isfromCreateGP=true;
                onBackPressed();
            } else {

            }
        }
    }

    ProgressDialog progressDialog = null;
    class RequestGP extends AsyncTask<String,Void,Void> {
        GroupJoinRequestManager groupJoinRequestManager;
        String responseString = "";
        String s = "Plase Wait...";
        String requestUserID="";
        int position;
        String reason_str="";

        public RequestGP(String reason_str){
            this.reason_str=reason_str;
        }
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
            requestUserID = new LoginManager(mContext).getUserInfo().get(0).getUserid();
        }

        @Override
        protected Void doInBackground(String... params) {
            groupJoinRequestManager=new GroupJoinRequestManager(mContext);
            responseString =groupJoinRequestManager.putAcceptRequest(requestUserID,coi_gid,actionType,reason_str);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (responseString.equals("100")) {


                String coi_gispublic=GropuActionManager.gpActionModel.getCoi_gispublic();
                String privategsatus=GropuActionManager.gpActionModel.getPrivategsatus();
                String isadmin=GropuActionManager.gpActionModel.getIsadmin();
                String isnoatmember=GropuActionManager.gpActionModel.getIsnoatmember();

                //Suspend Group
                if((coi_gispublic.equalsIgnoreCase("Public")||coi_gispublic.equalsIgnoreCase("Close"))
                        && privategsatus.equals("1")
                        &&isadmin.equalsIgnoreCase("yes")
                        && isnoatmember.equalsIgnoreCase("yes")){
                    Toast.makeText(mContext,"Group has been Suspended successfully.",Toast.LENGTH_SHORT).show();

                }

                //Leave Group
                else if((coi_gispublic.equalsIgnoreCase("Public")||coi_gispublic.equalsIgnoreCase("Close"))
                        && privategsatus.equals("1")
                        &&isadmin.equalsIgnoreCase("no")
                        && isnoatmember.equalsIgnoreCase("yes")){
                    Toast.makeText(mContext,"Group has been Leaved successfully.",Toast.LENGTH_SHORT).show();
                }

                //Requested Group
                else if(coi_gispublic.equalsIgnoreCase("Close")
                        && privategsatus.equals("-1")
                        &&isadmin.equalsIgnoreCase("no")
                        && isnoatmember.equalsIgnoreCase("no")){
                    Toast.makeText(mContext,"Your joining request has been sent to Group Admin for approval.",Toast.LENGTH_SHORT).show();

                }


                else {

                    Toast.makeText(mContext,"You have been added to the group.",Toast.LENGTH_SHORT).show();

                }


                GroupListActivity.isfromCreateGP=true;
                onBackPressed();

            } else {
                Toast.makeText(mContext,""+responseString,Toast.LENGTH_SHORT).show();
            }
        }
    }

    Dialog dialog;
    public void deleteDailogBox(final Context mCtx, final String msg) {
        dialog = new Dialog(mCtx);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.gpsuspend_dialog);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final EditText reason_txt=(EditText)window.findViewById(R.id.reason_txt);
        TextView tvMsg = (TextView) window.findViewById(R.id.msg_yesno);
        TextView tvNO = (TextView) window.findViewById(R.id.no_yesno);
        TextView tvYes = (TextView) window.findViewById(R.id.yes_yesno);
        tvMsg.setText(msg);

        tvNO.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvYes.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {

                //new DeleteGroup().execute();

                String reason_str=reason_txt.getText().toString();
                if(reason_str.length()!=0) {
                    dialog.dismiss();
                    new RequestGP(reason_str).execute();
                }
                else{
                    Toast.makeText(mCtx,"Please enter reason",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void createCustomAnimation() {


        final FloatingActionButton actionC = (FloatingActionButton) findViewById(R.id.fab_shareupdate);
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                Intent shareactvityintent = new Intent(mContext, GPSharePost.class);
                mContext.startActivity(shareactvityintent);

            }
        });


        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

		/*scaleInX.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				fabMenu.getMenuIconView().setImageResource(fabMenu.isOpened()
						? R.drawable.floating_icon : R.drawable.ic_close);
			}
		});*/

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        fabMenu.setIconToggleAnimatorSet(set);
    }




    class RequestGPAction extends AsyncTask<String,Void,Void> {
        GropuActionManager gropuActionManager;
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
        protected Void doInBackground(String... params) {
            gropuActionManager=new GropuActionManager(mContext);
            responseString =gropuActionManager.getGPAction();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (responseString.equals("100")) {
                createTabs();
                setTheam();
                setGpInfo();
            } else {

            }
        }
    }


    public void guestAlert(){

        String coi_gispublic=GropuActionManager.gpActionModel.getCoi_gispublic();
        String privategsatus=GropuActionManager.gpActionModel.getPrivategsatus();
        String isadmin=GropuActionManager.gpActionModel.getIsadmin();
        String isnoatmember=GropuActionManager.gpActionModel.getIsnoatmember();

        String titleText="Guest User";

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                titleText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        builder.setTitle(ssBuilder);
        builder.setCancelable(false);


        //Requested Group
        if(coi_gispublic.equalsIgnoreCase("Close")
                && privategsatus.equals("-1")
                &&isadmin.equalsIgnoreCase("no")
                && isnoatmember.equalsIgnoreCase("no")){
            builder.setMessage("Your join request is pending...");
            builder.setPositiveButton("Ok",null);
        }

        else{
            builder.setMessage("Please join this group to perform any activities(i.e. like and comment)");
            builder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    actionType="join";
                    new RequestGP("").execute();
                }
            });

            builder.setNegativeButton("Cancel", null);
        }




        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==701){
            setTheam();
        }
    }
}
