package expression.sixdexp.com.expressionapp.xpassions;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.utility.ViewPagerTab;
import expression.sixdexp.com.expressionapp.xpassions.gpfragment.GPOpinionFragment;
import expression.sixdexp.com.expressionapp.xpassions.gpfragment.GPWTFragment;
import expression.sixdexp.com.expressionapp.xpassions.gpfragment.GPXPWayFragment;
import expression.sixdexp.com.expressionapp.xpassions.gpfragment.WallFragment;

/**
 * Created by Praveen on 05-Jan-18.
 */

public class TabAdapter extends FragmentPagerAdapter
        implements PagerSlidingTabStrip.CustomTabProvider {

    String []fragname={WallFragment.class.getName(),GPXPWayFragment.class.getName(),GPWTFragment.class.getName(),GPOpinionFragment.class.getName()};



    ArrayList<ViewPagerTab> tabs;
    Animation zoomin,zoomout;
    Context context;
    public TabAdapter(FragmentManager fm, ArrayList<ViewPagerTab> tabs, Context context) {
        super(fm);
        this.context= context;
        this.tabs = tabs;
        zoomin = AnimationUtils.loadAnimation(context, R.anim.zoomeffect);
        zoomout = AnimationUtils.loadAnimation(context,R.anim.zoomout);

    }



    @Override
    public View getCustomTabView(ViewGroup viewGroup, int i) {
        RelativeLayout tabLayout = (RelativeLayout)
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customtabview, null);

        ImageView tabTitle = (ImageView) tabLayout.findViewById(R.id.tab_title);
        TextView badge = (TextView) tabLayout.findViewById(R.id.badge);

        ViewPagerTab tab = tabs.get(i);

        String imageName=tab.imagename;
        Log.i("imagename 56456",""+imageName);
        int id1 = context.getResources().getIdentifier("expression.sixdexp.com.expressionapp:drawable/"+imageName, null, null);
        tabTitle.setImageResource(id1);
        if (tab.notifications_count > 0) {
            badge.setVisibility(View.VISIBLE);
            badge.setText(String.valueOf(tab.notifications_count));
        } else {
            badge.setVisibility(View.GONE);
        }


        return tabLayout;
    }

    RelativeLayout temp_tabLayout=null;
    @Override
    public void tabSelected(View view) {
        final RelativeLayout tabLayout = (RelativeLayout) view;



        if(temp_tabLayout==null) {
            tabLayout.startAnimation(zoomin);
            temp_tabLayout=tabLayout;
        }

        else{
            if(!temp_tabLayout.equals(tabLayout)){
                tabLayout.startAnimation(zoomin);
                temp_tabLayout=tabLayout;
            }
        }

        zoomin.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tabLayout.startAnimation(zoomout);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        TextView badge = (TextView) tabLayout.findViewById(R.id.badge);
        tabLayout.setSelected(true);



        Log.i("091234","00000000000000000");

        // badge.setVisibility(View.GONE);
    }



    @Override
    public void tabUnselected(View view) {
        RelativeLayout tabLayout = (RelativeLayout) view;
        tabLayout.setSelected(false);
        tabLayout.setAnimation(null);
    }

    @Override
    public Fragment getItem(int position) {
        /*switch (position) {
            case 0:
                // home
                TabFragment1 tab1 = new TabFragment1();
                return tab1;
            case 1:
                // celebration
                TabFragment3 tab3 = new TabFragment3();
                return tab3;

            case 2:
                // Srijan
                SrijanFragment srijanFragment = new SrijanFragment();
                return srijanFragment;

            case 3:
                // Xpressway
                TabFragment4 tab4 = new TabFragment4();
                return tab4;
            case 4:
                // xpress bulletin
                TabFragment5 tab5 = new TabFragment5();
                return tab5;
            case 5:
                // what's trending
                TabFragment6 tab6 = new TabFragment6();
                return tab6;
            case 6:
                // notification
                TabFragment2 tab2 = new TabFragment2();
                return tab2;

            case 7:
                // Profile
                //TabFragment8_new tab8=new TabFragment8_new();
                TabFragment8 tab8 = new TabFragment8();
                return tab8;

            case 8:
                // Opinion Poll
                OpinionPollFragment tab9 = new OpinionPollFragment();
                return tab9;

            default:
                return null;

        }*/

        return Fragment.instantiate(context, fragname[position]);

    }

    @Override
    public int getCount() {
        return tabs.size();
    }



}
