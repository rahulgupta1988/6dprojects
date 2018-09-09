package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import expression.sixdexp.com.expressionapp.adapter.PollResultAdapter;

/**
 * Created by Praveen on 7/27/2016.
 */
public class PollDetailsActivity extends Activity implements View.OnClickListener{

    Context mContext;
    android.support.v7.widget.RecyclerView polllist;
    ImageView cancelshare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.polldetailsviewactivity);
        mContext = this;
        init();
    }

    public void init(){
        cancelshare=(ImageView)findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(this);
        initPollList();
    }

    public void initPollList() {
        polllist = (android.support.v7.widget.RecyclerView)findViewById(R.id.polllist);
        PollResultAdapter pollResultAdapter = new PollResultAdapter(mContext);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        polllist.setLayoutManager(layoutManager);
        polllist.setItemAnimator(new DefaultItemAnimator());
        polllist.setAdapter(pollResultAdapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
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
