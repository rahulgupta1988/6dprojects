package expression.sixdexp.com.expressionapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import db.CurrentOpinion;
import db.CurrentOpinionDao;
import db.DaoSession;
import db.PollResult;
import expression.sixdexp.com.expressionapp.PollDetailsActivity;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.CurrentPollManager;
import expression.sixdexp.com.expressionapp.manager.PollResultManager;
import expression.sixdexp.com.expressionapp.manager.PostOpinion;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;

/**
 * Created by Praveen on 9/5/2016.
 */
public class OpinionPollFragment extends Fragment implements View.OnClickListener {

    Context mContext;
    ProgressDialog progressDialog;
    Button pollresultbtn;
    ImageView cancelshare;


    // poll question views
    TextView pollquest, closeddateofpoll;
    RadioButton radioyes, radiono, radiocantsay;
    Button submitopinion;

    // poll result views
    TextView yestxt, notxt, cantsayxt, yesprogtxt, noprogtxt, cantsayprogtxt;
    ProgressBar yesprog, noprog, cantsayprog;

    // layout views
    LinearLayout pollquestlay, pollresltlay;
    String opinionid = "", response = "";
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pollresultitem, container, false);
        init();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = (Context) activity;
    }

    public void init() {
        pollquestlay = (LinearLayout) view.findViewById(R.id.pollquestlay);
        pollresltlay = (LinearLayout) view.findViewById(R.id.pollresltlay);
        pollresultbtn = (Button) view.findViewById(R.id.pollresultbtn);
        pollresultbtn.setOnClickListener(this);
        cancelshare = (ImageView) view.findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(this);
        setOpinion();
    }

    public void setOpinion() {

        List<CurrentOpinion> opinions = new CurrentPollManager(mContext).getCurrentPoll();
        if (opinions != null && opinions.size() > 0) {
            pollquestlay.setVisibility(View.VISIBLE);
            pollresltlay.setVisibility(View.GONE);
            CurrentOpinion currentOpinion = opinions.get(0);

            opinionid = currentOpinion.getOpinionid();
            submitopinion = (Button) view.findViewById(R.id.submitopinion);
            submitopinion.setOnClickListener(this);
            pollquest = (TextView) view.findViewById(R.id.pollquest);
            closeddateofpoll = (TextView) view.findViewById(R.id.closeddateofpoll);
            radioyes = (RadioButton) view.findViewById(R.id.radioyes);
            radiono = (RadioButton) view.findViewById(R.id.radiono);
            radiocantsay = (RadioButton) view.findViewById(R.id.radiocantsay);

            yestxt = (TextView) view.findViewById(R.id.yestxt);
            notxt = (TextView) view.findViewById(R.id.notxt);
            cantsayxt = (TextView) view.findViewById(R.id.cantsayxt);


            String question = currentOpinion.getQuestion();
            if (question != null && !question.equalsIgnoreCase(""))
                pollquest.setText("" + question);

            String enddate = currentOpinion.getEnddate();
            if (enddate != null && !enddate.equalsIgnoreCase("")) {
                String text = "<font color=#d33308><b>Ending On: </b></font><font color=#3c3c3c>" + enddate + "</b></font>";
                closeddateofpoll.setText(Html.fromHtml(text));
            }

            String answertype = currentOpinion.getAnswertype();
            if (answertype != null && !answertype.equalsIgnoreCase("") && answertype.equalsIgnoreCase("1")) {

                radioyes.setText("Agree");
                radiono.setText("Disagree");
                radiocantsay.setText("Can't say");

                yestxt.setText("Agree");
                notxt.setText("Disagree");
                cantsayxt.setText("Can't say");


            }


            radioyes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    radiono.setChecked(false);
                    radiocantsay.setChecked(false);
                    response = "0";
                }
            });

            radiono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    radioyes.setChecked(false);
                    radiocantsay.setChecked(false);
                    response = "1";
                }
            });

            radiocantsay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    radioyes.setChecked(false);
                    radiono.setChecked(false);
                    response = "2";
                }
            });
        } else {
            setOpinionResult();
        }


    }

    public void setOpinionResult() {

        yesprogtxt = (TextView) view.findViewById(R.id.yesprogtxt);
        noprogtxt = (TextView) view.findViewById(R.id.noprogtxt);
        cantsayprogtxt = (TextView) view.findViewById(R.id.cantsayprogtxt);

        yesprog = (ProgressBar) view.findViewById(R.id.yesprog);
        noprog = (ProgressBar) view.findViewById(R.id.noprog);
        cantsayprog = (ProgressBar) view.findViewById(R.id.cantsayprog);

        List<PollResult> pollResults = new PollResultManager(mContext).getPollResultList();
        if (pollResults != null && pollResults.size() > 0) {

            pollquestlay.setVisibility(View.GONE);
            pollresltlay.setVisibility(View.VISIBLE);
            PollResult pollResult = pollResults.get(0);

            String yesprog_val = pollResult.getAgree_perc();
            String noprog_val = pollResult.getDisagree_perc();
            String cantsayprog_val = pollResult.getCantsay_perc();

            if (yesprog_val != null && !yesprog_val.equalsIgnoreCase("")) {
                int temp_prog = (int) Double.parseDouble(yesprog_val);
                yesprog.setProgress(temp_prog);
                yesprogtxt.setText("" + temp_prog + "%");
            }

            if (noprog_val != null && !noprog_val.equalsIgnoreCase("")) {
                int temp_prog = (int) Double.parseDouble(noprog_val);
                noprog.setProgress(temp_prog);
                noprogtxt.setText("" + temp_prog + "%");
            }

            if (cantsayprog_val != null && !cantsayprog_val.equalsIgnoreCase("")) {
                int temp_prog = (int) Double.parseDouble(cantsayprog_val);
                cantsayprog.setProgress(temp_prog);
                cantsayprogtxt.setText("" + temp_prog + "%");
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pollresultbtn:
                new GetPollResults().execute();
                break;


            case R.id.submitopinion:

                if (response != null && !response.equalsIgnoreCase("") && opinionid != null && !opinionid.equalsIgnoreCase("")) {
                    new PostOpinionTask().execute();
                } else {
                    Toast.makeText(mContext, "Please select your opinon.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public class PostOpinionTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";
        String comment = "";

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
                responseString = new PostOpinion(mContext).postOpinion(opinionid, response);
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
                //Toast.makeText(mContext, "" + responseString, Toast.LENGTH_LONG).show();
                setOpinionResult();
                DaoSession daoSession = new BaseManager().getDBSessoin(mContext);
                CurrentOpinionDao currentOpinionDao = daoSession.getCurrentOpinionDao();
                currentOpinionDao.deleteAll();
            } else {

                Toast.makeText(mContext, "" + responseString, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

        }


    }


    public class GetPollResults extends AsyncTask<String, Void, Void> {

        String responseString = "";
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog  = ProgressLoaderUtilities.getProgressDialog(mContext);//initProgressDialog();
            progressDialog.show();
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


                Intent pollResultIntent = new Intent(mContext, PollDetailsActivity.class);
                startActivity(pollResultIntent);


            } else {

                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();

            }

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

}
