/*
package com.expression.ui.flipanimation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


 import expression.sixdexp.com.expressionapp.R;

public class ViewPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider
{
    // Declare the number of ViewPager pages
//  final int PAGE_COUNT = 5;
    final int PAGE_COUNT = 3;

    */
/**
     * PAGERSLIDINGTABSTRIPS
     *//*

   */
/* private final int[] ICONS = { R.drawable.tab_icon_zname_contact_selector, R.drawable.tab_icon_zname_friends_selector,
            R.drawable.tab_icon_zname_call_log_selector };*//*


    private final int[] ICONS = { R.drawable.iconselector, R.drawable.homeselector,
            R.drawable.celebrateselector };

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    */
/* (non-Javadoc)
     * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
     *//*

    @Override
    public Fragment getItem(int item) {
        switch (item) {

            // Open HomeFragment.java
            case 0:
//                ContactsFragment homeFragment = new ContactsFragment();
//                return homeFragment;
            // Open PlaceOrderFragment.java
            case 1:
//                GroupsFragment groupsFragment = new GroupsFragment();
//                return groupsFragment;
            case 2:
//                CallLogsFragment callLogsFragment = new CallLogsFragment();
//                return callLogsFragment;
        }
        return null;
    }

    */
/* (non-Javadoc)
     * @see android.support.v4.view.PagerAdapter#getCount()
     *//*

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
        return ICONS[position];
    }

}*/
