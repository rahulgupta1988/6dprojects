package com.expression.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.fragments.EventFR;
import expression.sixdexp.com.expressionapp.fragments.ProfileFR;
import expression.sixdexp.com.expressionapp.fragments.RecentFR;
import expression.sixdexp.com.expressionapp.utility.OnSwipeTouchListener;

/**
 * Created by Praveen on 17-Jan-17.
 */

public class TabFragment8_new extends Fragment implements View.OnClickListener {

    Context mContext;
    View view;
    FragmentManager frgManager;
    TextView profiletab,recentacttab,eventtab;
    FrameLayout frameLayout;
    public TabFragment8_new(){};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup profiletab_container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tabfrag8_new, profiletab_container, false);
        initView();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    public void initView(){
        frameLayout=(FrameLayout) view.findViewById(R.id.profiletab_container);
        profiletab=(TextView)view.findViewById(R.id.profiletab);
        recentacttab=(TextView)view.findViewById(R.id.recentacttab);
        eventtab=(TextView)view.findViewById(R.id.eventtab);
        profiletab.setOnClickListener(this);
        recentacttab.setOnClickListener(this);
        eventtab.setOnClickListener(this);

        fillContanierInitially();
        getTouch();
    }




    public void getTouch(){

        frameLayout.setOnTouchListener(new OnSwipeTouchListener(mContext) {

            public void onSwipeTop() {
                Toast.makeText(mContext, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                Toast.makeText(mContext, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                Toast.makeText(mContext, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                Toast.makeText(mContext, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void fillContanierInitially(){
        currentTab="profileFR";
        ProfileFR profileFR=new ProfileFR();
        frgManager = getFragmentManager();
        FragmentTransaction ft = frgManager.beginTransaction();
        ft.add(frameLayout.getId(),profileFR,"profileFR");
        ft.commit();


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.profiletab:
                tabManager("profileFR");
                break;

            case R.id.recentacttab:
                tabManager("recentFR");
                break;

            case R.id.eventtab:
                tabManager("eventFR");
                break;
        }
    }

    String currentTab="profileFR";
    String lastTab="profileFR";

    public void tabManager(String currentSelTab){

        if(currentSelTab.equals("profileFR") && !lastTab.equals("profileFR")){
            releseFragment();
            lastTab="profileFR";
            ProfileFR profileFR=new ProfileFR();
            frgManager = getFragmentManager();
            FragmentTransaction ft = frgManager.beginTransaction();
            ft.add(frameLayout.getId(),profileFR,"profileFR");
            ft.commit();
        }

        else if(currentSelTab.equals("recentFR") && !lastTab.equals("recentFR")){
            releseFragment();
            lastTab="recentFR";
            RecentFR recentFR=new RecentFR();
            frgManager = getFragmentManager();
            FragmentTransaction ft = frgManager.beginTransaction();
            ft.add(frameLayout.getId(),recentFR,"recentFR");
            ft.commit();
        }

        else if(currentSelTab.equals("eventFR") && !lastTab.equals("eventFR")){
            releseFragment();
            lastTab="eventFR";
            EventFR eventFR=new EventFR();
            frgManager = getFragmentManager();
            FragmentTransaction ft = frgManager.beginTransaction();
            ft.add(frameLayout.getId(),eventFR,"eventFR");
            ft.commit();
        }
    }

    public void releseFragment() {
        frgManager = getFragmentManager();
        Fragment profileFR = frgManager.findFragmentByTag("profileFR");
        Fragment recentFR = frgManager.findFragmentByTag("recentFR");
        Fragment eventFR = frgManager.findFragmentByTag("eventFR");


        if (profileFR != null) {
            frgManager.beginTransaction().remove(profileFR).commit();
        }
        else if (recentFR != null) {
            frgManager.beginTransaction().remove(recentFR).commit();
        }

        else if (eventFR != null) {
            frgManager.beginTransaction().remove(eventFR).commit();
        }


    }
}
