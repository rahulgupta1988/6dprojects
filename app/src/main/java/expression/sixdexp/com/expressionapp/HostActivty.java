package expression.sixdexp.com.expressionapp;

import android.animation.AnimatorSet;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import db.CurrentOpinion;
import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.adapter.RecentactivityAdapter;
import expression.sixdexp.com.expressionapp.fragments.OpinionPollFragment;
import expression.sixdexp.com.expressionapp.fragments.PollDetailsFragment;
import expression.sixdexp.com.expressionapp.fragments.SearchFragementNew;
import expression.sixdexp.com.expressionapp.fragments.XWFragement;
import expression.sixdexp.com.expressionapp.manager.CurrentPollManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.PollResultManager;
import expression.sixdexp.com.expressionapp.manager.RecognitionRecivedAndGiven;
import expression.sixdexp.com.expressionapp.manager.RecognitionRecivedAndGivenListManager;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.ServiceTocheckUserAuth;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;

/**
 * Created by Praveen on 6/30/2016.
 */
public class HostActivty extends Activity implements View.OnClickListener
{
    private MyBroadcastReceiver myBroadcastReceiver;
    Context mContext;
    ProgressDialog progressDialog;
    android.support.v7.widget.RecyclerView recentactivitylist;
    CircularImageView userimg11;
    TextView username11;
    LinearLayout hometab, profiletab, xwtab, searchtab, settingtab;
    FragmentManager frgManager;
    DrawerLayout drawer_layout;
    ImageView changeviewlist, myrecentactivitiesbtn;
    View constantlist;
    boolean tab = true;
    public String currentTab = "hometab";
    Dialog dialog;
    View hometab_selected, profiletab_selected, xwtab_selected, searchtab_selected, settingtab_selected;
    LinearLayout logout, poll, recognitiongiven, recognitionreceived, visitrocounter,whatstrending;
    int exitornot = 0;
    boolean recognitionGiven = true;
    ImageView settingbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hostview);
        mContext = this;

        registerReceiver();
        String userid = new LoginManager(mContext).getUserInfo().get(0).getUserid();
        SharedPrefrenceManager.putUserID(mContext, userid);
       /* if (SharedPrefrenceManager.getUserID(mContext) != null && !SharedPrefrenceManager.getUserID(mContext).equalsIgnoreCase("")) {*/
        initview();
        /*} else {
            ProgressDialog initProgressDialog = initProgressDialog();
            initProgressDialog.show();
            List<UserLoginInfo> userDatas = new ArrayList<UserLoginInfo>();
            userDatas = new LoginManager(mContext).getUserInfo();
            SharedPrefrenceManager.putUserID(mContext, userDatas.get(0).getUserid());

            new InitCall(mContext, initProgressDialog);
        }*/

    }


    public void initview() {


        settingbtn = (ImageView) findViewById(R.id.settingbtn);
        hometab_selected = (View) findViewById(R.id.hometab_selected);
        profiletab_selected = (View) findViewById(R.id.profiletab_selected);
        xwtab_selected = (View) findViewById(R.id.xwtab_selected);
        searchtab_selected = (View) findViewById(R.id.searchtab_selected);
        settingtab_selected = (View) findViewById(R.id.settingtab_selected);
        hometab_selected.setVisibility(View.VISIBLE);


        hometab = (LinearLayout) findViewById(R.id.hometab);
        profiletab = (LinearLayout) findViewById(R.id.profiletab);
        xwtab = (LinearLayout) findViewById(R.id.xwtab);
        searchtab = (LinearLayout) findViewById(R.id.searchtab);
        settingtab = (LinearLayout) findViewById(R.id.settingtab);

        //set listener
        hometab.setOnClickListener(this);
        profiletab.setOnClickListener(this);
        xwtab.setOnClickListener(this);
        searchtab.setOnClickListener(this);
        settingtab.setOnClickListener(this);
        fillContanierInitially();
        initnavigationdrawer();

    }

    AnimatorSet set;

    public void initnavigationdrawer() {

        constantlist = (View) findViewById(R.id.constantlist);
        myrecentactivitiesbtn = (ImageView) findViewById(R.id.myrecentactivitiesbtn);
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout.closeDrawers();
        userimg11 = (CircularImageView) findViewById(R.id.userimg11);
        username11 = (TextView) findViewById(R.id.username11);
        changeviewlist = (ImageView) findViewById(R.id.changeviewlist);
        logout = (LinearLayout) findViewById(R.id.logout);
        poll = (LinearLayout) findViewById(R.id.poll);
        recognitiongiven = (LinearLayout) findViewById(R.id.recognitiongiven);
        recognitionreceived = (LinearLayout) findViewById(R.id.recognitionreceived);
        visitrocounter = (LinearLayout) findViewById(R.id.visitrocounter);
        whatstrending=(LinearLayout)findViewById(R.id.whatstrending);
        whatstrending.setOnClickListener(this);
        visitrocounter.setOnClickListener(this);
        logout.setOnClickListener(this);
        recognitionreceived.setOnClickListener(this);
        poll.setOnClickListener(this);
        recognitiongiven.setOnClickListener(this);
        List<UserLoginInfo> userDatas = new ArrayList<UserLoginInfo>();
        userDatas = new LoginManager(mContext).getUserInfo();
        SharedPrefrenceManager.putUserID(mContext, userDatas.get(0).getUserid());

        String user_name = userDatas.get(0).getName();
        String profile_img_url = userDatas.get(0).getImageurl();

        if (user_name != null && !user_name.equalsIgnoreCase("")) {
            username11.setText(user_name);
        }
        URI uri = null;
        try {
            uri = new URI(profile_img_url.replace(" ", "%20"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String profile_img_url1 = uri.toString();
        if (!profile_img_url.equalsIgnoreCase("null") && !profile_img_url.equalsIgnoreCase("")
                && !profile_img_url.equalsIgnoreCase(" "))
        {


            Picasso.with(mContext)
                    .load(profile_img_url1)
                    .resize(100, 100)
                    .centerCrop()
                    .placeholder(R.drawable.default_profile_picture)
                    .error(R.drawable.default_profile_picture)
                    .into(userimg11);
        }


        myrecentactivitiesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constantlist.setVisibility(View.GONE);
                changeviewlist.setVisibility(View.VISIBLE);
                recentactivitylist.setVisibility(View.VISIBLE);
            }
        });

        changeviewlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeviewlist.setVisibility(View.INVISIBLE);
                recentactivitylist.setVisibility(View.GONE);
                constantlist.setVisibility(View.VISIBLE);
            }
        });


        setRecentactivity();
    }

    public void setRecentactivity() {
        recentactivitylist = (android.support.v7.widget.RecyclerView) findViewById(R.id.recentactivitylist);

        RecentactivityAdapter recentactivityAdapter = new RecentactivityAdapter(mContext);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recentactivitylist.setLayoutManager(layoutManager);
        recentactivitylist.setItemAnimator(new DefaultItemAnimator());
        recentactivitylist.setAdapter(recentactivityAdapter);
        //Utils.getListViewSize(recentactivitylist);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecentactivity();
    }

    @Override
    public void onClick(View v) {

        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        switch (v.getId()) {

            case R.id.hometab:
                if (tab) {
                if (!currentTab.equals("hometab")) {
                    currentTab = "hometab";
                    SharedPrefrenceManager.putSelectedTab(mContext, "hometab");
                    hometab_selected.setVisibility(View.VISIBLE);
                    profiletab_selected.setVisibility(View.INVISIBLE);
                    xwtab_selected.setVisibility(View.INVISIBLE);
                    searchtab_selected.setVisibility(View.INVISIBLE);
                    settingtab_selected.setVisibility(View.INVISIBLE);

                    Fragment fragment1 = frgManager.findFragmentByTag("profileFragment");
                    Fragment fragment2 = frgManager.findFragmentByTag("xwFragement");
                    Fragment fragment3 = frgManager.findFragmentByTag("searchFragement");
                    Fragment fragment4 = frgManager.findFragmentByTag("settingFragment");
                    if (fragment1 != null) {
                        Log.i("fragment1", "fragment1");
                        frgManager.beginTransaction().remove(fragment1).commit();
                    }
                    if (fragment2 != null) {
                        Log.i("fragment2", "fragment2");
                        frgManager.beginTransaction().remove(fragment2).commit();
                    }

                    if (fragment3 != null) {
                        Log.i("fragment3", "fragment3");
                        frgManager.beginTransaction().remove(fragment3).commit();
                    }
                    if (fragment4 != null) {
                        Log.i("fragment4", "fragment4");
                        frgManager.beginTransaction().remove(fragment4).commit();
                    }

                   /* HomeFragment homeFragment = new HomeFragment();
                    frgManager = getFragmentManager();
                    FragmentTransaction ft = frgManager.beginTransaction();
                    ft.add(R.id.container, homeFragment, "homeFragment");
                    ft.commit();*/
                }
            }
                break;
            case R.id.profiletab:

                if (tab) {

                    if (!currentTab.equals("profiletab"))
                    {
                        currentTab = "profiletab";
                        SharedPrefrenceManager.putSelectedTab(mContext, "profiletab");
                        hometab_selected.setVisibility(View.INVISIBLE);
                        profiletab_selected.setVisibility(View.VISIBLE);
                        xwtab_selected.setVisibility(View.INVISIBLE);
                        searchtab_selected.setVisibility(View.INVISIBLE);
                        settingtab_selected.setVisibility(View.INVISIBLE);
                        new GetPoll().execute();
                    }
                }
                break;
            case R.id.xwtab:
                if (tab) {
                    if (!currentTab.equals("xwtab")) {
                        currentTab = "xwtab";
                        SharedPrefrenceManager.putSelectedTab(mContext, "xwtab");
                        hometab_selected.setVisibility(View.INVISIBLE);
                        profiletab_selected.setVisibility(View.INVISIBLE);
                        xwtab_selected.setVisibility(View.VISIBLE);
                        searchtab_selected.setVisibility(View.INVISIBLE);
                        settingtab_selected.setVisibility(View.INVISIBLE);

                        Fragment fragment1 = frgManager.findFragmentByTag("homeFragment");
                        Fragment fragment2 = frgManager.findFragmentByTag("profileFragment");
                        Fragment fragment3 = frgManager.findFragmentByTag("searchFragement");
                        Fragment fragment4 = frgManager.findFragmentByTag("settingFragment");
                        if (fragment1 != null) {
                            Log.i("fragment1", "fragment1");
                            frgManager.beginTransaction().remove(fragment1).commit();
                        }
                        if (fragment2 != null) {
                            Log.i("fragment2", "fragment2");
                            frgManager.beginTransaction().remove(fragment2).commit();
                        }

                        if (fragment3 != null) {
                            Log.i("fragment3", "fragment3");
                            frgManager.beginTransaction().remove(fragment3).commit();
                        }
                        if (fragment4 != null) {
                            Log.i("fragment4", "fragment4");
                            frgManager.beginTransaction().remove(fragment4).commit();
                        }


                        XWFragement xwFragement = new XWFragement();
                        frgManager = getFragmentManager();
                        FragmentTransaction ft2 = frgManager.beginTransaction();
                        ft2.add(R.id.container, xwFragement, "xwFragement");
                        ft2.commit();
                    }
                }
                break;
            case R.id.searchtab:
                if (tab) {
                    if (!currentTab.equals("searchtab")) {
                        currentTab = "searchtab";
                        SharedPrefrenceManager.putSelectedTab(mContext, "searchtab");
                        hometab_selected.setVisibility(View.INVISIBLE);
                        profiletab_selected.setVisibility(View.INVISIBLE);
                        xwtab_selected.setVisibility(View.INVISIBLE);
                        searchtab_selected.setVisibility(View.VISIBLE);
                        settingtab_selected.setVisibility(View.INVISIBLE);

                        Fragment fragment1 = frgManager.findFragmentByTag("homeFragment");
                        Fragment fragment2 = frgManager.findFragmentByTag("profileFragment");
                        Fragment fragment3 = frgManager.findFragmentByTag("xwFragement");
                        Fragment fragment4 = frgManager.findFragmentByTag("settingFragment");
                        if (fragment1 != null) {
                            Log.i("fragment1", "fragment1");
                            frgManager.beginTransaction().remove(fragment1).commit();
                        }
                        if (fragment2 != null) {
                            Log.i("fragment2", "fragment2");
                            frgManager.beginTransaction().remove(fragment2).commit();
                        }

                        if (fragment3 != null) {
                            Log.i("fragment3", "fragment3");
                            frgManager.beginTransaction().remove(fragment3).commit();
                        }
                        if (fragment4 != null) {
                            Log.i("fragment4", "fragment4");
                            frgManager.beginTransaction().remove(fragment4).commit();
                        }


                        //SearchFragement searchFragement = new SearchFragement();
                        SearchFragementNew searchFragement = new SearchFragementNew();
                        frgManager = getFragmentManager();
                        FragmentTransaction ft3 = frgManager.beginTransaction();
                        ft3.add(R.id.container, searchFragement, "searchFragement");
                        ft3.commit();
                    }
                }
                break;
            case R.id.settingtab:
                drawer_layout.openDrawer(GravityCompat.START);
                break;

            case R.id.logout:
                logoutDailogBox(mContext, "Are you sure,  You want to Logout? ");
                exitornot = 2;
                break;

            case R.id.poll:
                new GetPoll().execute();
                break;


            case R.id.recognitionreceived:
                recognitionGiven = false;
                new RecognitionGivenTask().execute();
                break;

            case R.id.recognitiongiven:
                recognitionGiven = true;
                new RecognitionGivenTask().execute();
                break;



            case R.id.visitrocounter:
                Intent visitor = new Intent(mContext, VisitorCounterActivty.class);
                startActivity(visitor);
                break;

            case R.id.whatstrending:
                Intent whatstrendingIntent = new Intent(mContext, WahtsTrending.class);
                startActivity(whatstrendingIntent);
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment2 = frgManager.findFragmentByTag("profileFragment");

        if (fragment2 != null) {
            fragment2.onActivityResult(requestCode, resultCode, data);
        }

    }


    public ProgressDialog initProgressDialog() {
        String s = "Please wait...";
        SpannableString ss2 = new SpannableString(s);
        ss2.setSpan(new RelativeSizeSpan(1.3f), 0, ss2.length(), 0);
        ss2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, ss2.length(), 0);
        ProgressDialog progressDialog = new ProgressDialog(mContext,
                android.R.style.Theme_DeviceDefault_Light_Dialog);
        Window window = progressDialog.getWindow();
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setMessage(ss2);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        return progressDialog;
    }


    public void fillContanierInitially() {
        releseFragment();

        if (SharedPrefrenceManager.getSelectedTab(mContext).equals("hometab") ||
                SharedPrefrenceManager.getSelectedTab(mContext).equals("") ||
                SharedPrefrenceManager.getSelectedTab(mContext) == null) {

            currentTab = "hometab";
            hometab_selected.setVisibility(View.VISIBLE);
            profiletab_selected.setVisibility(View.INVISIBLE);
            xwtab_selected.setVisibility(View.INVISIBLE);
            searchtab_selected.setVisibility(View.INVISIBLE);
            settingtab_selected.setVisibility(View.INVISIBLE);

       /*     HomeFragment homeFragment = new HomeFragment();
            frgManager = getFragmentManager();
            FragmentTransaction ft = frgManager.beginTransaction();
            ft.add(R.id.container, homeFragment, "homeFragment");
            ft.commit();*/


        }
        if (SharedPrefrenceManager.getSelectedTab(mContext).equals("profiletab")) {
            currentTab = "profiletab";

            hometab_selected.setVisibility(View.INVISIBLE);
            profiletab_selected.setVisibility(View.VISIBLE);
            xwtab_selected.setVisibility(View.INVISIBLE);
            searchtab_selected.setVisibility(View.INVISIBLE);
            settingtab_selected.setVisibility(View.INVISIBLE);

            new GetPoll().execute();

           /* ProfileFragment profiletab = new ProfileFragment();
            frgManager = getFragmentManager();
            FragmentTransaction ft = frgManager.beginTransaction();
            ft.add(R.id.container, profiletab, "profileFragment");
            ft.commit();*/


        }

        if (SharedPrefrenceManager.getSelectedTab(mContext).equals("xwtab")) {
            currentTab = "xwtab";
            hometab_selected.setVisibility(View.INVISIBLE);
            profiletab_selected.setVisibility(View.INVISIBLE);
            xwtab_selected.setVisibility(View.VISIBLE);
            searchtab_selected.setVisibility(View.INVISIBLE);
            settingtab_selected.setVisibility(View.INVISIBLE);

            XWFragement xwtab = new XWFragement();
            frgManager = getFragmentManager();
            FragmentTransaction ft = frgManager.beginTransaction();
            ft.add(R.id.container, xwtab, "xwFragement");
            ft.commit();


        }
        if (SharedPrefrenceManager.getSelectedTab(mContext).equals("searchtab")) {
            currentTab = "searchtab";
            hometab_selected.setVisibility(View.INVISIBLE);
            profiletab_selected.setVisibility(View.INVISIBLE);
            xwtab_selected.setVisibility(View.INVISIBLE);
            searchtab_selected.setVisibility(View.VISIBLE);
            settingtab_selected.setVisibility(View.INVISIBLE);

            //SearchFragement searchtab = new SearchFragement();
            SearchFragementNew searchtab = new SearchFragementNew();
            frgManager = getFragmentManager();
            FragmentTransaction ft = frgManager.beginTransaction();
            ft.add(R.id.container, searchtab, "searchFragement");
            ft.commit();


        }


    }

    public void releseFragment() {
        frgManager = getFragmentManager();
        Fragment fragment1 = frgManager.findFragmentByTag("homeFragment");
        Fragment fragment2 = frgManager.findFragmentByTag("profileFragment");
        Fragment fragment3 = frgManager.findFragmentByTag("xwFragement");
        Fragment fragment4 = frgManager.findFragmentByTag("settingFragment");

        if (fragment1 != null) {
            Log.i("fragment1", "fragment1");
            frgManager.beginTransaction().remove(fragment1).commit();
        }
        if (fragment2 != null) {
            Log.i("fragment2", "fragment2");
            frgManager.beginTransaction().remove(fragment2).commit();
        }

        if (fragment3 != null) {
            Log.i("fragment3", "fragment3");
            frgManager.beginTransaction().remove(fragment3).commit();
        }
        if (fragment4 != null) {
            Log.i("fragment4", "fragment4");
            frgManager.beginTransaction().remove(fragment4).commit();
        }

    }


    public class GetPoll extends AsyncTask<String, Void, Void> {


        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);/*initProgressDialog();*/
            progressDialog.show();
            //progressDialog= ProgressLoaderUtilities.getProgressDialog(LoginActivity.this);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new CurrentPollManager(mContext).callCurrentPoll();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            if (responseString.equals("100")) {

                List<CurrentOpinion> opinions = new CurrentPollManager(mContext).getCurrentPoll();
                if (opinions == null || opinions.size() == 0)
                {
                    new GetPollResults().execute();
                }
                else {
                    progressDialog.dismiss();

                    setOpinionPollFragment("opinion");
                    /*Intent pollntent = new Intent(mContext, PollActivity.class);
                    startActivity(pollntent);*/
                }


            } else {
                new GetPollResults().execute();
            }

        }


    }

    public class GetPollResults extends AsyncTask<String, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //progressDialog= ProgressLoaderUtilities.getProgressDialog(LoginActivity.this);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new PollResultManager(mContext).pollResultCall();
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
            if (responseString.equals("100")) {


                   /* Intent pollntent = new Intent(mContext, PollDetailsActivity.class);
                    startActivity(pollntent);*/

                setOpinionPollFragment("opinionResultList");


            } else {

                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();

            }

        }


    }

    @Override
    public void onBackPressed() {
        if (exitornot == 1) {
            //super.onBackPressed();
            finish();
        } else {
            logoutDailogBox(mContext, "Are you sure,  You want to Exit? ");
            exitornot = 0;
        }


        //finish();
    }


    public class RecognitionGivenTask extends AsyncTask<String, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);//initProgressDialog();
            progressDialog.show();
            //progressDialog= ProgressLoaderUtilities.getProgressDialog(mContext);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new RecognitionRecivedAndGiven(mContext, recognitionGiven).callRecognitionRecivedAndGiven("", "", "", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            // progressDialog.dismiss();
            if (responseString.equals("100")) {

                new RecognitionGivenListTask().execute();

            } else {
                progressDialog.dismiss();
                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
            }

        }


    }


    public class RecognitionGivenListTask extends AsyncTask<String, Void, Void> {

        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            //progressDialog= ProgressLoaderUtilities.getProgressDialog(mContext);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new RecognitionRecivedAndGivenListManager(mContext, recognitionGiven).callRecognitionRecivedAndGivenListManager("", "", "", "");
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
            if (responseString.equals("100"))
            {
                Intent recognitionGiveIntent = new Intent(mContext, RecognitionGiveActivity.class);
                recognitionGiveIntent.putExtra("recognitionGiven", recognitionGiven);
                startActivity(recognitionGiveIntent);
            }
            else {
                progressDialog.dismiss();
                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
            }

        }


    }

    public void logoutDailogBox(Context mCtx, final String msg) {
        dialog = new Dialog(mCtx);
        Window window = dialog.getWindow();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.confirmdialog);
        window.setType(WindowManager.LayoutParams.FIRST_SUB_WINDOW);
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView tvMsg = (TextView) window.findViewById(R.id.msg_yesno);
        TextView tvNO = (TextView) window.findViewById(R.id.no_yesno);
        TextView tvYes = (TextView) window.findViewById(R.id.yes_yesno);
        tvMsg.setText(msg);

        tvNO.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }
                exitornot = 0;
            }
        });
        tvYes.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                if (exitornot == 0) {
                    if (dialog != null) {
                        dialog.dismiss();
                        dialog = null;
                    }
                    exitornot = 1;
                    onBackPressed();
                }
                if (exitornot == 2) {
                    if (dialog != null) {
                        dialog.dismiss();
                        dialog = null;
                    }
                    SharedPrefrenceManager.putSelectedTab(mContext, "");
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                    SharedPrefrenceManager.putUserID(mContext, null);
                    SharedPrefrenceManager.putPassword(mContext, null);
                    SharedPrefrenceManager.putUsername(mContext, null);
                }
            }
        });
        dialog.show();
    }


    public void registerReceiver() {
        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(ServiceTocheckUserAuth.ACTION_UserAuthService);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            SharedPrefrenceManager.putSelectedTab(mContext, "");
            Intent loginIntent = new Intent(mContext, LoginActivity.class);
            startActivity(loginIntent);
            finish();
            SharedPrefrenceManager.putUserID(mContext, null);
            SharedPrefrenceManager.putPassword(mContext, null);
            SharedPrefrenceManager.putUsername(mContext, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

    public void setOpinionPollFragment(String opinioORresult) {

        Fragment fragment1 = frgManager.findFragmentByTag("homeFragment");
        Fragment fragment2 = frgManager.findFragmentByTag("xwFragement");
        Fragment fragment3 = frgManager.findFragmentByTag("searchFragement");
        Fragment fragment4 = frgManager.findFragmentByTag("settingFragment");
        if (fragment1 != null) {
            Log.i("fragment1", "fragment1");
            frgManager.beginTransaction().remove(fragment1).commit();
        }
        if (fragment2 != null) {
            Log.i("fragment2", "fragment2");
            frgManager.beginTransaction().remove(fragment2).commit();
        }

        if (fragment3 != null) {
            Log.i("fragment3", "fragment3");
            frgManager.beginTransaction().remove(fragment3).commit();
        }
        if (fragment4 != null) {
            Log.i("fragment4", "fragment4");
            frgManager.beginTransaction().remove(fragment4).commit();
        }


        if (opinioORresult.equals("opinion")) {
            OpinionPollFragment opinionPollFragment = new OpinionPollFragment();
            frgManager = getFragmentManager();
            FragmentTransaction ft1 = frgManager.beginTransaction();
            ft1.add(R.id.container, opinionPollFragment, "profileFragment");
            ft1.commit();
        }
        if (opinioORresult.equals("opinionResultList")) {
            PollDetailsFragment pollDetailsFragment = new PollDetailsFragment();
            frgManager = getFragmentManager();
            FragmentTransaction ft1 = frgManager.beginTransaction();
            ft1.add(R.id.container, pollDetailsFragment, "profileFragment");
            ft1.commit();
        }

    }
}