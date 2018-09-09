/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.expression.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.facebook.FacebookSdk;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Twitter;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import db.DaoSession;
import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.FeedBackActivity;
import expression.sixdexp.com.expressionapp.LoginActivity;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.RecogniseActivity;
import expression.sixdexp.com.expressionapp.ShareActivity;
import expression.sixdexp.com.expressionapp.TermsConditionsActivity;
import expression.sixdexp.com.expressionapp.adapter.MainAdapter;
import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.LogOutManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.VisitorCounter;
import expression.sixdexp.com.expressionapp.utility.FinishActiivity;
import expression.sixdexp.com.expressionapp.utility.NotificationCountService;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.SearchActivity;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;
import expression.sixdexp.com.expressionapp.utility.SharingInterface;
import expression.sixdexp.com.expressionapp.utility.ViewPagerTab;
import expression.sixdexp.com.expressionapp.xpassions.GPInfoActivity;
import expression.sixdexp.com.expressionapp.xpassions.GroupListActivity;

public class FlipComplexLayoutActivity extends FragmentActivity implements View.OnClickListener {

    public static String receviserFilter = "com.xpression.notify.count";
    Context mContext;
    Dialog dialog;
    private int currentPageSelected = 0;
    private PagerSlidingTabStrip tabs;
    //TabLayout tabs;
    public ViewPager pager;
    //private MyPagerAdapter adapter;
    private MainAdapter adapter;
    ImageView comment_img;
    private Drawable oldBackground = null;
    private int currentColor = 0xFF666666;
    DrawerLayout drawer_layout;
    LinearLayout whatstrending_drawer, logout_drawer, termscondition_drawer, feedback_drawer, srijan_drawer, opinion_drawer;
    TextView /*total_app_visitors,*/txt_username;
    CircularImageView user_profile_img;
    String Userprofileimage = "";
    android.app.FragmentManager frgManager;
    LinearLayout news_feed_homedrawer, shareandupdate_drawer, recognisesomeone_drawer, celebration_drawer,
            xpressway_drawer, opinionpoll_drawer, bulletin_xpressway_drawer, myprofile_drawer, notification_drawer;
    Intent intent;
    TextView digit1, digit2, digit3, digit4, digit5, digit6;
    TextView visitcounttxt, web_visitcounttxt;

    Timer timer = null;
    TimerTask timerTask = null;
    final Handler handler = new Handler();
    String userid = "";

    public static String isformbulletinpost = "no";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_main_new);
        Twitter.initialize(this);
        FacebookSdk.sdkInitialize(this);
        mContext = this;
        userid = new LoginManager(mContext).getUserInfo().get(0).getUserid();
        SharedPrefrenceManager.putUserID(mContext, userid);


        //	createNotification();
        // for notification count
        registerReceiver();
        createTimer();

        // call notification count service
        /*Intent intent = new Intent(this, NotificationCountService.class);
		intent.putExtra("userid",userid);
		startService(intent);*/


        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
		/*adapter = new MyPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);*/

        ArrayList<ViewPagerTab> tabsList = new ArrayList<>();
        tabsList.add(new ViewPagerTab("homeselector", 0));
        tabsList.add(new ViewPagerTab("celebrateselector", 0));
        tabsList.add(new ViewPagerTab("srijanselector", 0));
        tabsList.add(new ViewPagerTab("xpresswayselector", 0));
        tabsList.add(new ViewPagerTab("xpressbulletinselector", 0));
        tabsList.add(new ViewPagerTab("whatstredingselector", 0));
        tabsList.add(new ViewPagerTab("pollselector", 0));
        tabsList.add(new ViewPagerTab("notificationselector", 0));
        tabsList.add(new ViewPagerTab("myproflieselector", 0));


        adapter = new MainAdapter(getSupportFragmentManager(), tabsList, mContext);

        //adapter = new MainAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);


        if (GPInfoActivity.isFromNotify.equals("yes")) {
            pager.setCurrentItem(8, true);
            GPInfoActivity.isFromNotify = "no";
        }
        if (isformbulletinpost.equals("yes")) {
            pager.setCurrentItem(4, true);
            isformbulletinpost = "no";
        }

        if (isformbulletinpost.equals("yes")) {
            pager.setCurrentItem(4, true);
            isformbulletinpost = "no";
        }


        String ispollORpolicy = "";
        Intent notifyIntent = getIntent();
        if (notifyIntent != null) {
            ispollORpolicy = notifyIntent.getStringExtra("ispollORpolicy");

            Log.i("3243243", "" + ispollORpolicy);
            if (ispollORpolicy != null) {
                if (ispollORpolicy.equals("yes")) {
                    pager.setCurrentItem(7, true);
                }
            }
        }

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);

        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout.closeDrawers();

        fabMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        setFlot();
        frameLayout.getBackground().setAlpha(0);

        View mSlidingView = findViewById(R.id.DrawerLinear);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) mSlidingView.getLayoutParams();
        params.width = metrics.widthPixels;
        mSlidingView.setLayoutParams(params);

        comment_img = (ImageView) findViewById(R.id.comment_img);
        comment_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetVisitorCounter().execute();
                drawer_layout.openDrawer(GravityCompat.START);
            }
        });


        String counter_val = SharedPrefrenceManager.getVisitorCount(mContext);//AppData.getSharedAppData().getSavedPreference(Constant.SHARED_PREFERENCE_Visitors_Counter);
        String vistor_counter = counter_val;

        String web_counter_val = SharedPrefrenceManager.getWebVisitorCount(mContext);//AppData.getSharedAppData().getSavedPreference(Constant.SHARED_PREFERENCE_Visitors_Counter);
        String web_vistor_counter = web_counter_val;

        Log.i("counter", "" + vistor_counter);
        //Toast.makeText(getApplicationContext(), "Visitors2=" + vistor_counter,Toast.LENGTH_SHORT).show();
		/*total_app_visitors=(TextView)findViewById(R.id.total_app_visitors);
		total_app_visitors.setText(vistor_counter);*/

        opinion_drawer = (LinearLayout) findViewById(R.id.opinion_drawer);
        opinion_drawer.setOnClickListener(this);
        srijan_drawer = (LinearLayout) findViewById(R.id.srijan_drawer);
        whatstrending_drawer = (LinearLayout) findViewById(R.id.whatstrending_drawer);
        srijan_drawer.setOnClickListener(this);
        whatstrending_drawer.setOnClickListener(this);
        logout_drawer = (LinearLayout) findViewById(R.id.logout_drawer);
        logout_drawer.setOnClickListener(this);
        termscondition_drawer = (LinearLayout) findViewById(R.id.termscondition_drawer);
        termscondition_drawer.setOnClickListener(this);
        feedback_drawer = (LinearLayout) findViewById(R.id.feedback_drawer);
        feedback_drawer.setOnClickListener(this);
        news_feed_homedrawer = (LinearLayout) findViewById(R.id.news_feed_homedrawer);
        news_feed_homedrawer.setOnClickListener(this);
        shareandupdate_drawer = (LinearLayout) findViewById(R.id.shareandupdate_drawer);
        shareandupdate_drawer.setOnClickListener(this);
        recognisesomeone_drawer = (LinearLayout) findViewById(R.id.recognisesomeone_drawer);
        recognisesomeone_drawer.setOnClickListener(this);
        celebration_drawer = (LinearLayout) findViewById(R.id.celebration_drawer);
        celebration_drawer.setOnClickListener(this);
        xpressway_drawer = (LinearLayout) findViewById(R.id.xpressway_drawer);
        xpressway_drawer.setOnClickListener(this);
        bulletin_xpressway_drawer = (LinearLayout) findViewById(R.id.bulletin_xpressway_drawer);
        bulletin_xpressway_drawer.setOnClickListener(this);
	/*	opinionpoll_drawer=(LinearLayout)findViewById(R.id.opinionpoll_drawer);
		opinionpoll_drawer.setOnClickListener(this);*/
        notification_drawer = (LinearLayout) findViewById(R.id.notification_drawer);
        notification_drawer.setOnClickListener(this);
	/*	myprofile_drawer=(LinearLayout)findViewById(R.id.myprofile_drawer);
		myprofile_drawer.setOnClickListener(this);*/


        user_profile_img = (CircularImageView) findViewById(R.id.user_profile_img);
        user_profile_img.setOnClickListener(this);
        txt_username = (TextView) findViewById(R.id.txt_username);
        txt_username.setOnClickListener(this);

        visitcounttxt = (TextView) findViewById(R.id.visitcounttxt);
        web_visitcounttxt = (TextView) findViewById(R.id.web_visitcounttxt);
        visitcounttxt.setText(vistor_counter + "\nGuest");
        web_visitcounttxt.setText("Employee visits " + web_vistor_counter);




		/*digit1=(TextView)findViewById(R.id.digit1);
		digit2=(TextView)findViewById(R.id.digit2);
		digit3=(TextView)findViewById(R.id.digit3);
		digit4=(TextView)findViewById(R.id.digit4);
		digit5=(TextView)findViewById(R.id.digit5);
		digit6=(TextView)findViewById(R.id.digit6);
		digit1.setVisibility(View.GONE);
		digit2.setVisibility(View.GONE);
		digit3.setVisibility(View.GONE);
		digit4.setVisibility(View.GONE);
		digit5.setVisibility(View.GONE);
		digit6.setVisibility(View.GONE);



		//String vistor_counter=VisitorCounter.vistor_counter;
		Log.i("counter", "" + vistor_counter);
		if(vistor_counter!=null && !vistor_counter.equalsIgnoreCase("")){

			int length_of_counter=vistor_counter.length();
			Log.i("length_of_counter",""+length_of_counter);
			for (int i=0;i<length_of_counter;i++){
				char digit=vistor_counter.charAt(i);
				if(i==0) {
					digit1.setVisibility(View.VISIBLE);
					digit1.setText("" + digit);

				}
				if(i==1) {
					digit2.setVisibility(View.VISIBLE);
					digit2.setText("" + digit);
				}
				if(i==2){
					digit3.setVisibility(View.VISIBLE);
					digit3.setText(""+digit);
				}
				if(i==3) {
					digit4.setVisibility(View.VISIBLE);
					digit4.setText("" + digit);
				}

				if(i==4) {
					digit5.setVisibility(View.VISIBLE);
					digit5.setText("" + digit);
				}
				if(i==5) {
					digit6.setVisibility(View.VISIBLE);
					digit6.setText("" + digit);
				}

			}
		}
*/


        updateUserInfo();


        Field field = null;
		/*try {
			field = PagerSlidingTabStrip.class.getDeclaredField("tabsContainer");
			field.setAccessible(true);
			LinearLayout tabsContainer = (LinearLayout) field.get(tabs);
			tabsContainer.getChildAt(0).setSelected(true);

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}*/


        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int currentPageSelected = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.v("onPageSelected", tabs.getClass().toString());

                Field field = null;
				/*try {
					field = PagerSlidingTabStrip.class.getDeclaredField("tabsContainer");
					field.setAccessible(true);
					LinearLayout tabsContainer = (LinearLayout) field.get(tabs);
					tabsContainer.getChildAt(currentPageSelected).setSelected(false);
					currentPageSelected = position;
					tabsContainer.getChildAt(position).setSelected(true);

				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
*/

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentColor", currentColor);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentColor = savedInstanceState.getInt("currentColor");
    }

    private Drawable.Callback drawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            getActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            handler.postAtTime(what, when);
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            handler.removeCallbacks(what);
        }
    };

	/*public class MyPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider
	{


		final int PAGE_COUNT = 7;
		private final int[] SWITCH_ICONS = { R.drawable.homeselector,
				R.drawable.celebrateselector, R.drawable.xpresswayselector,
				R.drawable.xpressbulletinselector,R.drawable.whatstredingselector,
				 R.drawable.notificationselector,R.drawable.myproflieselector};

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return PAGE_COUNT;
		}

		*/

    /**
     * PAGERSLIDINGTABSTRIPS
     *//*
		@Override
		public int getPageIconResId(int position) {
			// TODO Auto-generated method stub
			return SWITCH_ICONS[position];
		}

	*//*	@Override
		public int getCount() {
			return SWITCH_ICONS.length;
		}

		@Override
		public int getPageIconResId(int position) {
			return SWITCH_ICONS[position];
		}*//*




		@Override
		public Fragment getItem(int position)
		{
			switch (position)
			{
				case 0:
					// home
					TabFragment1 tab1 = new TabFragment1();
					return tab1;
				case 1:
					// celebration
					TabFragment3 tab3 = new TabFragment3();
					return tab3;
				case 2:
					// Xpressway
					TabFragment4 tab4 = new TabFragment4();
				    return tab4;
				case 3:
					// xpress bulletin
					TabFragment5 tab5 = new TabFragment5();
					return tab5;
				case 4:
					// what's trending
					TabFragment6 tab6 = new TabFragment6();
					return tab6;
				case 5:
					// notification
					TabFragment2 tab2 = new TabFragment2();
					return tab2;

				case 6:
					// Profile
					//TabFragment8_new tab8=new TabFragment8_new();
					TabFragment8 tab8 = new TabFragment8();
					return tab8;
				default:
					return null;



			}

		}
	}*/
    @Override
    public void onClick(View v) {

        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        switch (v.getId()) {
/*
			case R.id.search_drawer:
				SharedPrefrenceManager.putSelectedTab(mContext, "searchtab");
				Intent searchactiivity = new Intent(mContext, SearchActivity.class);
				startActivity(searchactiivity);
				break;
*/

            case R.id.shareandupdate_drawer:
                Intent shareactvityintent = new Intent(mContext, ShareActivity.class);
                mContext.startActivity(shareactvityintent);
                break;

            case R.id.recognisesomeone_drawer:
                Intent recognizeActivityIntent = new Intent(mContext, RecogniseActivity.class);
                mContext.startActivity(recognizeActivityIntent);
                break;

            case R.id.news_feed_homedrawer:
                drawer_layout.closeDrawers();
                pager.setCurrentItem(0, true);
                break;

            case R.id.celebration_drawer:
                drawer_layout.closeDrawers();
                pager.setCurrentItem(1, true);
                break;

            case R.id.xpressway_drawer:
                drawer_layout.closeDrawers();
                pager.setCurrentItem(3, true);
                break;

            case R.id.bulletin_xpressway_drawer:
                drawer_layout.closeDrawers();
                pager.setCurrentItem(4, true);
                break;

            case R.id.whatstrending_drawer:
                drawer_layout.closeDrawers();
                pager.setCurrentItem(5, true);
                break;

            case R.id.notification_drawer:
                drawer_layout.closeDrawers();
                pager.setCurrentItem(7, true);
                break;


            case R.id.user_profile_img:
                drawer_layout.closeDrawers();
                pager.setCurrentItem(8, true);
                break;

            case R.id.opinion_drawer:
                drawer_layout.closeDrawers();
                pager.setCurrentItem(6, true);
                break;

            case R.id.srijan_drawer:
                drawer_layout.closeDrawers();
                pager.setCurrentItem(2, true);
                break;


			/*case R.id.txt_username:
				drawer_layout.closeDrawers();
				pager.setCurrentItem(7, true);
				break;
*/
            case R.id.logout_drawer:
                logoutDailogBox(mContext, "Are you sure,  You want to Logout? ");
                break;

            case R.id.termscondition_drawer:
                Intent terms = new Intent(mContext, TermsConditionsActivity.class);
                mContext.startActivity(terms);
                break;

            case R.id.feedback_drawer:
                Intent feedback = new Intent(mContext, FeedBackActivity.class);
                mContext.startActivity(feedback);
                break;
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
            }
        });
        tvYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (dialog != null) {
                    dialog.dismiss();
                    dialog = null;
                }

                new TaskLogout().execute();


            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {

        ExitDailogBox(mContext, "Are you sure\n You want to Exit? ");


    }

    public void ExitDailogBox(Context mCtx, final String msg) {
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
            }
        });
        tvYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
				/*DaoSession daoSession= BaseManager.getDBSessoin(mContext);
				daoSession.getGetMorePostDao().deleteAll();
				daoSession.getGetXpresswayPostDao().deleteAll();
				daoSession.getAwardDao().deleteAll();
				daoSession.getRecentActivityDao().deleteAll();
				daoSession.getExpossorDao().deleteAll();*/

			/*	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				finish();
				System.exit(0);
				android.os.Process.killProcess(android.os.Process.myPid());*/

                Intent intent = new Intent(FlipComplexLayoutActivity.this, FinishActiivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
// closing Entire Application
        unRegisterReceiver();
        stoptimertask();
        if (mp != null)
            mp.stop();
        finish();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //createNotification();
        fabMenu.close(false);
        //fabMenu.close(true);
        fabMenu.getMenuIconView().setImageResource(fabMenu.isOpened()
                ? R.drawable.ic_close : R.drawable.floating_icon);
    }

    FloatingActionMenu fabMenu;
    FrameLayout frameLayout;

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

    private void createCustomAnimation() {


        final FloatingActionButton actionA = (FloatingActionButton) findViewById(R.id.fab_search);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fabMenu.close(false);
                SharedPrefrenceManager.putSelectedTab(mContext, "searchtab");
                Intent searchactiivity = new Intent(mContext, SearchActivity.class);
                mContext.startActivity(searchactiivity);
            }
        });

        final FloatingActionButton fab_xpassion = (FloatingActionButton) findViewById(R.id.fab_xpassion);
        fab_xpassion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fabMenu.close(false);
                //SharedPrefrenceManager.putSelectedTab(mContext, "xpassion");
                Intent searchactiivity = new Intent(mContext, GroupListActivity.class);
                mContext.startActivity(searchactiivity);
            }
        });

        final FloatingActionButton actionB = (FloatingActionButton) findViewById(R.id.fab_recognize);
        actionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fabMenu.close(false);
                Intent recognizeActivityIntent = new Intent(mContext, RecogniseActivity.class);
                mContext.startActivity(recognizeActivityIntent);

            }
        });

        final FloatingActionButton actionC = (FloatingActionButton) findViewById(R.id.fab_shareupdate);
        actionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fabMenu.close(false);
                Intent shareactvityintent = new Intent(mContext, ShareActivity.class);
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

    // to update notification count
    public void notifyTabStripChanged(int position, int notificationsCount) {
        LinearLayout tabHost = (LinearLayout) tabs.getChildAt(0);
        RelativeLayout tabLayout = (RelativeLayout) tabHost.getChildAt(position);
        TextView badge = (TextView) tabLayout.findViewById(R.id.badge);

        if (lastCount != latestCount) {
            lastCount = latestCount;
            Log.i("bounce 4325", "bounce");
            if (animY != null) animY.start();
            else doBounceAnimation(tabLayout);
        }

        if (notificationsCount > 0) {
            badge.setVisibility(View.VISIBLE);
            badge.setText(String.valueOf(notificationsCount));
        } else {
            badge.setVisibility(View.GONE);
        }

        tabLayout.setSelected(true);
    }

    public static int lastCount = 0;
    int latestCount = 0;

    public class NotificationCountReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String notifyCount = intent.getStringExtra("notifyCount");
            latestCount = Integer.parseInt(notifyCount);

            Log.i("hello99001", notifyCount);
            try {
                if (lastCount != latestCount) {
                    if (mp != null) {
                        Log.i("sound 2324", notifyCount);
                        mp.start();
                    }
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            notifyTabStripChanged(7, Integer.parseInt(notifyCount));

        }
    }

    NotificationCountReceiver broadCastReceiver = null;

    public void registerReceiver() {

        broadCastReceiver = new NotificationCountReceiver();
        IntentFilter intentFilter = new IntentFilter(receviserFilter);
        mContext.registerReceiver(broadCastReceiver, new IntentFilter(
                intentFilter));

    }

    public void unRegisterReceiver() {
        if (broadCastReceiver != null)
            mContext.unregisterReceiver(broadCastReceiver);
    }


    public void createTimer() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 0, 5000);
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {

            public void run() {

                handler.post(new Runnable() {

                    public void run() {
                        Intent intent = new Intent(mContext, NotificationCountService.class);
                        intent.putExtra("userid", userid);
                        startService(intent);

                    }

                });

            }

        };

    }

    public void stoptimertask() {

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

    }


    MediaPlayer mp = null;

    public void createNotification() {
        mp = MediaPlayer.create(mContext, R.raw.notifycounat);
    }


    ObjectAnimator animY = null;

    private void doBounceAnimation(View targetView) {
        ObjectAnimator animY = ObjectAnimator.ofFloat(targetView, "translationY", -50f, 0f);
        animY.setDuration(500);//1sec
        animY.setInterpolator(new BounceInterpolator());
        animY.setRepeatCount(0);
        animY.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mp = null;
    }

    String userID = "";

    public void updateUserInfo() {

        List<UserLoginInfo> userDatas = null;
        userDatas = new LoginManager(getApplicationContext()).getUserInfo();
        Userprofileimage = userDatas.get(0).getImageurl();

        userID = userDatas.get(0).getUserid();
        String profile_img_url = userDatas.get(0).getImageurl();
        String user_name = userDatas.get(0).getName();


        if (user_name != null && !user_name.equalsIgnoreCase("")) {
			/*String[] strArray = user_name.split(" ");
			StringBuilder builder = new StringBuilder();
			for (String s : strArray) {
				String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
				builder.append(cap + " ");
			}
			txt_username.setText(builder.toString());*/
            txt_username.setText(user_name.toString());

        }
        URI uri = null;
        try {
            uri = new URI(profile_img_url.replace(" ", "%20"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String profile_img_url1 = uri.toString();
        if (!profile_img_url.equalsIgnoreCase("null") && !profile_img_url.equalsIgnoreCase("")
                && !profile_img_url.equalsIgnoreCase(" ")) {
            Picasso.with(mContext)
                    .load(profile_img_url1)
                    .resize(800, 800)
                    .centerInside()
                    .placeholder(R.drawable.icon_profile_drawer_bg)
                    .error(R.drawable.icon_profile_drawer_bg)
                    .into(user_profile_img);
        }


    }

    public class TaskLogout extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";
        ProgressDialog progressDialog = null;

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
                responseString = new LogOutManager(mContext).logoutUser(userID);
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
                if (responseString.equals("100")) {
                    DaoSession daoSession = BaseManager.getDBSessoin(mContext);
                    daoSession.getUserLoginInfoDao().deleteAll();
                    SharedPrefrenceManager.putSelectedTab(mContext, "");
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                    SharedPrefrenceManager.putUserID(mContext, null);
                    SharedPrefrenceManager.putPassword(mContext, null);
                    SharedPrefrenceManager.putUsername(mContext, null);
                } else {
                    Toast.makeText(mContext, "" + responseString, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    public class GetVisitorCounter extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new VisitorCounter().callVisitorCounter();
                //insetUserdata();
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
                if (responseString.equals("100")) {
                    String vistor_counter = VisitorCounter.vistor_counter;
                    String web_user_count = VisitorCounter.web_user_count;
                    Log.i("counter", "" + vistor_counter);
                    //visitcounttxt.setText(vistor_counter+"\nGuest");
                    web_visitcounttxt.setText("Employee visits  " + web_user_count);

                } else {

                    Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SharingInterface.TWEETER_REQ_CODE) {
            Toast.makeText(mContext, "Shared successfully.", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == SharingInterface.whatsappCode) {
            Toast.makeText(mContext, "Shared successfully.", Toast.LENGTH_SHORT).show();
        }
    }
}