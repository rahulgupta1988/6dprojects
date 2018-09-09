package expression.sixdexp.com.expressionapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.adapter.PollResultAdapter;

/**
 * Created by Praveen on 9/5/2016.
 */
public class PollDetailsFragment extends Fragment {

    Context mContext;
    View view;
    android.support.v7.widget.RecyclerView polllist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.polldetailsview, container, false);
        init();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = (Context) activity;
    }

    public void init() {

        initPollList();
    }

    public void initPollList() {
        polllist = (android.support.v7.widget.RecyclerView) view.findViewById(R.id.polllist);
        PollResultAdapter pollResultAdapter = new PollResultAdapter(mContext);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        polllist.setLayoutManager(layoutManager);
        polllist.setItemAnimator(new DefaultItemAnimator());
        polllist.setAdapter(pollResultAdapter);
    }


}


