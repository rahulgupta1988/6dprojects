package com.expression.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.utility.Constant;

public class TabFragment7 extends Fragment
{
    View view;
    public TabFragment7(){};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view= inflater.inflate(R.layout.tab_fragment_7, container, false);
        return view;
    }
}