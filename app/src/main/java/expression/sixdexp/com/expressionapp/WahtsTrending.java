package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import expression.sixdexp.com.expressionapp.fragments.GeneralTrendframent;
import expression.sixdexp.com.expressionapp.fragments.PowerTrendFragment;
import expression.sixdexp.com.expressionapp.fragments.SportsTrendFragment;
import expression.sixdexp.com.expressionapp.fragments.TataTrendFragment;
import expression.sixdexp.com.expressionapp.fragments.TechnologyTrendFragment;


/**
 * Created by Praveen on 9/13/2016.
 */
public class WahtsTrending extends Activity implements View.OnClickListener {

    Context mContext;
    ImageView cancelshare;
    LinearLayout tatatrendtab, powertrendtab, generaltrendtab, technotrendtab, sportstrendtab;
    View hometab_selected, profiletab_selected, xwtab_selected, searchtab_selected, settingtab_selected;

    FragmentManager frgManager;
    public String currentTab = "tatatrendtab";
    boolean tab = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whatstrendingview);
        initview();
    }


    public void initview() {

        cancelshare=(ImageView)findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(this);

        tatatrendtab = (LinearLayout) findViewById(R.id.tatatrendtab);
        powertrendtab = (LinearLayout) findViewById(R.id.powertrendtab);
        generaltrendtab = (LinearLayout) findViewById(R.id.generaltrendtab);
        technotrendtab = (LinearLayout) findViewById(R.id.technotrendtab);
        sportstrendtab = (LinearLayout) findViewById(R.id.sportstrendtab);


        hometab_selected = (View) findViewById(R.id.hometab_selected);
        profiletab_selected = (View) findViewById(R.id.profiletab_selected);
        xwtab_selected = (View) findViewById(R.id.xwtab_selected);
        searchtab_selected = (View) findViewById(R.id.searchtab_selected);
        settingtab_selected = (View) findViewById(R.id.settingtab_selected);
        hometab_selected.setVisibility(View.VISIBLE);


        //set listener
        tatatrendtab.setOnClickListener(this);
        powertrendtab.setOnClickListener(this);
        generaltrendtab.setOnClickListener(this);
        technotrendtab.setOnClickListener(this);
        sportstrendtab.setOnClickListener(this);
        fillContanierInitially();

    }

    public void fillContanierInitially() {

        hometab_selected.setVisibility(View.VISIBLE);
        profiletab_selected.setVisibility(View.INVISIBLE);
        xwtab_selected.setVisibility(View.INVISIBLE);
        searchtab_selected.setVisibility(View.INVISIBLE);
        settingtab_selected.setVisibility(View.INVISIBLE);

          /*  TataTrendFragment tataTrendFragment = new TataTrendFragment();
            frgManager = getFragmentManager();
            FragmentTransaction ft = frgManager.beginTransaction();
            ft.add(R.id.container, tataTrendFragment, "tataTrendFragment");
            ft.commit();*/
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.tatatrendtab:
                if (tab) {
                    if (!currentTab.equals("tatatrendtab")) {
                        currentTab = "tatatrendtab";
                        hometab_selected.setVisibility(View.VISIBLE);
                        profiletab_selected.setVisibility(View.INVISIBLE);
                        xwtab_selected.setVisibility(View.INVISIBLE);
                        searchtab_selected.setVisibility(View.INVISIBLE);
                        settingtab_selected.setVisibility(View.INVISIBLE);

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

                 /*       TataTrendFragment tataTrendFragment = new TataTrendFragment();
                        frgManager = getFragmentManager();
                        FragmentTransaction ft = frgManager.beginTransaction();
                        ft.add(R.id.container, tataTrendFragment, "tataTrendFragment");
                        ft.commit();*/
                    }
                }
                break;

            case R.id.powertrendtab:
                if (tab) {

                    if (!currentTab.equals("powertrendtab")) {
                        currentTab = "powertrendtab";
                        hometab_selected.setVisibility(View.INVISIBLE);
                        profiletab_selected.setVisibility(View.VISIBLE);
                        xwtab_selected.setVisibility(View.INVISIBLE);
                        searchtab_selected.setVisibility(View.INVISIBLE);
                        settingtab_selected.setVisibility(View.INVISIBLE);


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


                    /*    PowerTrendFragment powerTrendFragment = new PowerTrendFragment();
                        frgManager = getFragmentManager();
                        FragmentTransaction ft1 = frgManager.beginTransaction();
                        ft1.add(R.id.container, powerTrendFragment, "powerTrendFragment");
                        ft1.commit();*/
                    }
                }

                break;

            case R.id.generaltrendtab:
                if (tab) {
                    if (!currentTab.equals("generaltrendtab")) {
                        currentTab = "generaltrendtab";
                        hometab_selected.setVisibility(View.INVISIBLE);
                        profiletab_selected.setVisibility(View.INVISIBLE);
                        xwtab_selected.setVisibility(View.VISIBLE);
                        searchtab_selected.setVisibility(View.INVISIBLE);
                        settingtab_selected.setVisibility(View.INVISIBLE);

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


                      /*  GeneralTrendframent generalTrendframent = new GeneralTrendframent();
                        frgManager = getFragmentManager();
                        FragmentTransaction ft2 = frgManager.beginTransaction();
                        ft2.add(R.id.container, generalTrendframent, "generalTrendframent");
                        ft2.commit();*/
                    }
                }
                break;

            case R.id.technotrendtab:
                if (tab) {
                    if (!currentTab.equals("technotrendtab")) {
                        currentTab = "technotrendtab";
                        hometab_selected.setVisibility(View.INVISIBLE);
                        profiletab_selected.setVisibility(View.INVISIBLE);
                        xwtab_selected.setVisibility(View.INVISIBLE);
                        searchtab_selected.setVisibility(View.VISIBLE);
                        settingtab_selected.setVisibility(View.INVISIBLE);

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


                       /* TechnologyTrendFragment technologyTrendFragment = new TechnologyTrendFragment();
                        frgManager = getFragmentManager();
                        FragmentTransaction ft3 = frgManager.beginTransaction();
                        ft3.add(R.id.container, technologyTrendFragment, "technologyTrendFragment");
                        ft3.commit();*/
                    }
                }
                break;

            case R.id.sportstrendtab:
                if (tab) {
                    if (!currentTab.equals("sportstrendtab")) {
                        currentTab = "sportstrendtab";
                        hometab_selected.setVisibility(View.INVISIBLE);
                        profiletab_selected.setVisibility(View.INVISIBLE);
                        xwtab_selected.setVisibility(View.INVISIBLE);
                        searchtab_selected.setVisibility(View.INVISIBLE);
                        settingtab_selected.setVisibility(View.VISIBLE);

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


                       /* SportsTrendFragment sportsTrendFragment = new SportsTrendFragment();
                        frgManager = getFragmentManager();
                        FragmentTransaction ft3 = frgManager.beginTransaction();
                        ft3.add(R.id.container, sportsTrendFragment, "sportsTrendFragment");
                        ft3.commit();*/
                    }
                }
                break;

            case R.id.cancelshare:
                onBackPressed();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
