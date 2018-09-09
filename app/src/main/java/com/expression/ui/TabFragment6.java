package com.expression.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.RecogniseActivity;
import expression.sixdexp.com.expressionapp.ShareActivity;
import expression.sixdexp.com.expressionapp.fragments.GeneralTrendframent;
import expression.sixdexp.com.expressionapp.fragments.PowerTrendFragment;
import expression.sixdexp.com.expressionapp.fragments.SportsTrendFragment;
import expression.sixdexp.com.expressionapp.fragments.TataTrendFragment;
import expression.sixdexp.com.expressionapp.fragments.TechnologyTrendFragment;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.SearchActivity;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;

public class TabFragment6 extends Fragment implements View.OnClickListener
{
    View view;
    Context mContext;
   // ImageView cancelshare;
    LinearLayout tatatrendtab, powertrendtab, generaltrendtab, technotrendtab, sportstrendtab;
   // View hometab_selected, profiletab_selected, xwtab_selected, searchtab_selected, settingtab_selected;

    android.support.v4.app.FragmentManager frgManager;
    public String currentTab = "tatatrendtab";
    boolean tab = true;
    ImageView tataworld_tab,power_tab,general_tab,technology_tab,sports_tab;
    public TabFragment6(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
       // view= inflater.inflate(R.layout.tab_fragment_6, container, false);whatstrendingview
//        view= inflater.inflate(R.layout.whatstrendingview, container, false);
        view= inflater.inflate(R.layout.whatstrendingview_test, container, false);
        mContext = getActivity();
        initview();

        return view;
    }

    public void initview()
    {
      /*  cancelshare=(ImageView)view.findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(this);*/

        tataworld_tab=(ImageView)view.findViewById(R.id.tataworld_tab);
        power_tab=(ImageView)view.findViewById(R.id.power_tab);
        general_tab=(ImageView)view.findViewById(R.id.general_tab);
        technology_tab=(ImageView)view.findViewById(R.id.technology_tab);
        sports_tab=(ImageView)view.findViewById(R.id.sports_tab);


        tatatrendtab = (LinearLayout)view.findViewById(R.id.tatatrendtab);
        powertrendtab = (LinearLayout)view.findViewById(R.id.powertrendtab);
        generaltrendtab = (LinearLayout)view.findViewById(R.id.generaltrendtab);
        technotrendtab = (LinearLayout)view.findViewById(R.id.technotrendtab);
        sportstrendtab = (LinearLayout)view.findViewById(R.id.sportstrendtab);


//        hometab_selected = (View)view.findViewById(R.id.hometab_selected);
//        profiletab_selected = (View)view.findViewById(R.id.profiletab_selected);
//        xwtab_selected = (View)view.findViewById(R.id.xwtab_selected);
//        searchtab_selected = (View)view.findViewById(R.id.searchtab_selected);
//        settingtab_selected = (View)view.findViewById(R.id.settingtab_selected);
//        hometab_selected.setVisibility(View.VISIBLE);
       // tataworld_tab.setBackgroundResource(R.drawable.tata_world_enable);

        //set listener
        tatatrendtab.setOnClickListener(this);
        powertrendtab.setOnClickListener(this);
        generaltrendtab.setOnClickListener(this);
        technotrendtab.setOnClickListener(this);
        sportstrendtab.setOnClickListener(this);
        fillContanierInitially();





    }

    public void fillContanierInitially() {

      //  hometab_selected.setVisibility(View.VISIBLE);
        tataworld_tab.setBackgroundResource(R.drawable.tata_world_enable);
      //  profiletab_selected.setVisibility(View.INVISIBLE);
        power_tab.setBackgroundResource(R.drawable.power_disable);
      //  xwtab_selected.setVisibility(View.INVISIBLE);
        general_tab.setBackgroundResource(R.drawable.general_disable);
      //  searchtab_selected.setVisibility(View.INVISIBLE);
        technology_tab.setBackgroundResource(R.drawable.technology_disable);
       // settingtab_selected.setVisibility(View.INVISIBLE);
        sports_tab.setBackgroundResource(R.drawable.sports_disable);

        TataTrendFragment tataTrendFragment = new TataTrendFragment();
        frgManager = getFragmentManager();
        FragmentTransaction ft = frgManager.beginTransaction();
        ft.add(R.id.container, tataTrendFragment, "tataTrendFragment");
        ft.commit();
/*
        PowerTrendFragment powerTrendFragment = new PowerTrendFragment();
        frgManager = getFragmentManager();
        FragmentTransaction ft = frgManager.beginTransaction();
        ft.add(R.id.container, powerTrendFragment, "powerTrendFragment");
        ft.commit();*/
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.tatatrendtab:
                if (tab) {
                    if (!currentTab.equals("tatatrendtab")) {
                        currentTab = "tatatrendtab";
                     //   hometab_selected.setVisibility(View.VISIBLE);
                        tataworld_tab.setBackgroundResource(R.drawable.tata_world_enable);
                      //  profiletab_selected.setVisibility(View.INVISIBLE);
                        power_tab.setBackgroundResource(R.drawable.power_disable);
                      //  xwtab_selected.setVisibility(View.INVISIBLE);
                        general_tab.setBackgroundResource(R.drawable.general_disable);
                       // searchtab_selected.setVisibility(View.INVISIBLE);
                        technology_tab.setBackgroundResource(R.drawable.technology_disable);
                       // settingtab_selected.setVisibility(View.INVISIBLE);
                        sports_tab.setBackgroundResource(R.drawable.sports_disable);

                        Fragment fragment1 = frgManager.findFragmentByTag("powerTrendFragment");
                        Fragment fragment2 = frgManager.findFragmentByTag("generalTrendframent");
                        Fragment fragment3 = frgManager.findFragmentByTag("technologyTrendFragment");
                        Fragment fragment4 = frgManager.findFragmentByTag("sportsTrendFragment");
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

                        TataTrendFragment tataTrendFragment = new TataTrendFragment();
                        frgManager = getFragmentManager();
                        FragmentTransaction ft = frgManager.beginTransaction();
                        ft.add(R.id.container, tataTrendFragment, "tataTrendFragment");
                        ft.commit();
                    }
                }
                break;

            case R.id.powertrendtab:
                if (tab) {

                    if (!currentTab.equals("powertrendtab")) {
                        currentTab = "powertrendtab";
                      //  hometab_selected.setVisibility(View.INVISIBLE);
                        tataworld_tab.setBackgroundResource(R.drawable.tata_world_disable);
                       // profiletab_selected.setVisibility(View.VISIBLE);
                        power_tab.setBackgroundResource(R.drawable.power_enable);
                       // xwtab_selected.setVisibility(View.INVISIBLE);
                        general_tab.setBackgroundResource(R.drawable.general_disable);
                      //  searchtab_selected.setVisibility(View.INVISIBLE);
                        technology_tab.setBackgroundResource(R.drawable.technology_disable);
                      //  settingtab_selected.setVisibility(View.INVISIBLE);
                        sports_tab.setBackgroundResource(R.drawable.sports_disable);


                        Fragment fragment1 = frgManager.findFragmentByTag("tataTrendFragment");
                        Fragment fragment2 = frgManager.findFragmentByTag("generalTrendframent");
                        Fragment fragment3 = frgManager.findFragmentByTag("technologyTrendFragment");
                        Fragment fragment4 = frgManager.findFragmentByTag("sportsTrendFragment");
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


                        PowerTrendFragment powerTrendFragment = new PowerTrendFragment();
                        frgManager = getFragmentManager();
                        FragmentTransaction ft1 = frgManager.beginTransaction();
                        ft1.add(R.id.container, powerTrendFragment, "powerTrendFragment");
                        ft1.commit();
                    }
                }

                break;

            case R.id.generaltrendtab:
                if (tab) {
                    if (!currentTab.equals("generaltrendtab")) {
                        currentTab = "generaltrendtab";
                      //  hometab_selected.setVisibility(View.INVISIBLE);
                        tataworld_tab.setBackgroundResource(R.drawable.tata_world_disable);
                       // profiletab_selected.setVisibility(View.INVISIBLE);
                        power_tab.setBackgroundResource(R.drawable.power_disable);
                      //  xwtab_selected.setVisibility(View.VISIBLE);
                        general_tab.setBackgroundResource(R.drawable.general_enable);
                      //  searchtab_selected.setVisibility(View.INVISIBLE);
                        technology_tab.setBackgroundResource(R.drawable.technology_disable);
                      //  settingtab_selected.setVisibility(View.INVISIBLE);
                        sports_tab.setBackgroundResource(R.drawable.sports_disable);

                        Fragment fragment1 = frgManager.findFragmentByTag("tataTrendFragment");
                        Fragment fragment2 = frgManager.findFragmentByTag("powerTrendFragment");
                        Fragment fragment3 = frgManager.findFragmentByTag("technologyTrendFragment");
                        Fragment fragment4 = frgManager.findFragmentByTag("sportsTrendFragment");
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


                        GeneralTrendframent generalTrendframent = new GeneralTrendframent();
                        frgManager = getFragmentManager();
                        FragmentTransaction ft2 = frgManager.beginTransaction();
                        ft2.add(R.id.container, generalTrendframent, "generalTrendframent");
                        ft2.commit();
                    }
                }
                break;

            case R.id.technotrendtab:
                if (tab) {
                    if (!currentTab.equals("technotrendtab")) {
                        currentTab = "technotrendtab";
                     //   hometab_selected.setVisibility(View.INVISIBLE);
                        tataworld_tab.setBackgroundResource(R.drawable.tata_world_disable);
                     //   profiletab_selected.setVisibility(View.INVISIBLE);
                        power_tab.setBackgroundResource(R.drawable.power_disable);
                     //   xwtab_selected.setVisibility(View.INVISIBLE);
                        general_tab.setBackgroundResource(R.drawable.general_disable);
                      //  searchtab_selected.setVisibility(View.VISIBLE);
                        technology_tab.setBackgroundResource(R.drawable.technology_enable);
                     //   settingtab_selected.setVisibility(View.INVISIBLE);
                        sports_tab.setBackgroundResource(R.drawable.sports_disable);

                        Fragment fragment1 = frgManager.findFragmentByTag("tataTrendFragment");
                        Fragment fragment2 = frgManager.findFragmentByTag("powerTrendFragment");
                        Fragment fragment3 = frgManager.findFragmentByTag("generalTrendframent");
                        Fragment fragment4 = frgManager.findFragmentByTag("sportsTrendFragment");
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


                        TechnologyTrendFragment technologyTrendFragment = new TechnologyTrendFragment();
                        frgManager = getFragmentManager();
                        FragmentTransaction ft3 = frgManager.beginTransaction();
                        ft3.add(R.id.container, technologyTrendFragment, "technologyTrendFragment");
                        ft3.commit();
                    }
                }
                break;

            case R.id.sportstrendtab:
                if (tab) {
                    if (!currentTab.equals("sportstrendtab")) {
                        currentTab = "sportstrendtab";
                     //   hometab_selected.setVisibility(View.INVISIBLE);
                        tataworld_tab.setBackgroundResource(R.drawable.tata_world_disable);
                   //     profiletab_selected.setVisibility(View.INVISIBLE);
                        power_tab.setBackgroundResource(R.drawable.power_disable);
                    //    xwtab_selected.setVisibility(View.INVISIBLE);
                        general_tab.setBackgroundResource(R.drawable.general_disable);
                     //   searchtab_selected.setVisibility(View.INVISIBLE);
                        technology_tab.setBackgroundResource(R.drawable.technology_disable);
                     //   settingtab_selected.setVisibility(View.VISIBLE);
                        sports_tab.setBackgroundResource(R.drawable.sports_enable);

                        Fragment fragment1 = frgManager.findFragmentByTag("tataTrendFragment");
                        Fragment fragment2 = frgManager.findFragmentByTag("powerTrendFragment");
                        Fragment fragment3 = frgManager.findFragmentByTag("generalTrendframent");
                        Fragment fragment4 = frgManager.findFragmentByTag("technologyTrendFragment");
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


                        SportsTrendFragment sportsTrendFragment = new SportsTrendFragment();
                        frgManager = getFragmentManager();
                        FragmentTransaction ft3 = frgManager.beginTransaction();
                        ft3.add(R.id.container, sportsTrendFragment, "sportsTrendFragment");
                        ft3.commit();
                    }
                }
                break;

         /*   case R.id.cancelshare:
                onBackPressed();
                break; */

        }

    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
    }*/



}