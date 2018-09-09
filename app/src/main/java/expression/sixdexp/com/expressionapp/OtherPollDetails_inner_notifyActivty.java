package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import db.DaoSession;
import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.adapter.OtherPollAdapter;
import expression.sixdexp.com.expressionapp.manager.BaseManager;
import expression.sixdexp.com.expressionapp.manager.LogOutManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.OpinionPollQuestionAnswerManager;
import expression.sixdexp.com.expressionapp.manager.OtherPoolMamager;
import expression.sixdexp.com.expressionapp.manager.PollDetailsManager;
import expression.sixdexp.com.expressionapp.manager.SinglePostManager;
import expression.sixdexp.com.expressionapp.manager.VotePollManager;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.SharedPrefrenceManager;
import model.PollOptionModel;

/**
 * Created by Praveen on 28-Aug-17.
 */

public class OtherPollDetails_inner_notifyActivty extends Activity{

    Context mContext;
    RelativeLayout current_poll_tab,other_poll_tab;
    TextView submit_vote;
    View current_tab_line,other_tab_line;
    ScrollView scroll_view;
    RelativeLayout current_poll_tab_lay,other_poll_tab_lay;
    RecyclerView other_poll_list;

    TextView current_poll_qt_txt,voter_count,date_time_txt;
    TextView no_data_txt;
    LinearLayout poll_lay;
    String postId;
    String notificationmasterid;
    String OptionSelectedID="0";
    String pollid="0";

    String[] colorArr = {"#9c0959", "#9c7d2c", "#4145ee", "#038995", "#4d1787", "#a5b40f", "#3a97d9"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_other_poll_lay);
        mContext=this;


        postId =getIntent().getStringExtra("postId");
        notificationmasterid=getIntent().getStringExtra("notificationmasterid");
        Intent intent=getIntent();
        if(intent!=null)
            pollid=intent.getStringExtra("pollid");

        initView();
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    public void initView() {
        poll_lay=(LinearLayout)findViewById(R.id.poll_lay);
        no_data_txt=(TextView)findViewById(R.id.no_data_txt);
        current_poll_qt_txt=(TextView)findViewById(R.id.current_poll_qt_txt);
        voter_count=(TextView)findViewById(R.id.voter_count);
        date_time_txt=(TextView)findViewById(R.id.date_time_txt);

        current_poll_tab_lay=(RelativeLayout)findViewById(R.id.current_poll_tab_lay);
        other_poll_tab_lay=(RelativeLayout)findViewById(R.id.other_poll_tab_lay);

        scroll_view=(ScrollView)findViewById(R.id.scroll_view);
        submit_vote=(TextView)findViewById(R.id.submit_vote);






        submit_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!OptionSelectedID.equals("0"))
                    new SubmitPoll().execute();
                else Toast.makeText(mContext,"Please select an option.",Toast.LENGTH_SHORT).show();
            }
        });

        if(!pollid.equals("0"))
            new GetPollQA().execute();
    }

    CardView lastClickedCard=null;
    TextView lastclickedTxt=null;
    TextView lastclickedQTxt=null;
    ImageView lastClickedImage=null;

    public void setQuestionList(){

        submit_vote.setVisibility(View.VISIBLE);
        LinearLayout question_lay=(LinearLayout)findViewById(R.id.question_lay);
        question_lay.removeAllViews();
        LayoutInflater layoutInflater=(LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        List<PollOptionModel> pollOptionModels= PollDetailsManager.pollQAModel.getPollOptionModels();

        for (int i=0;i<pollOptionModels.size();i++){

            PollOptionModel option=pollOptionModels.get(i);

            final View question_item=layoutInflater.inflate(R.layout.poll_question_item,null);
            final TextView circle_txt=(TextView)question_item.findViewById(R.id.circle_txt);
            final TextView question_txt=(TextView)question_item.findViewById(R.id.question_txt);
            final ImageView circle_img=(ImageView) question_item.findViewById(R.id.circle_img);
            circle_txt.setText(""+(i+1));
            question_item.setTag(option.getOpinionanswerid());

            question_txt.setText(""+option.getAnswertitle());
            GradientDrawable circle_Shape = (GradientDrawable) circle_txt.getBackground();
            circle_Shape.setColor(Color.parseColor("#ffffff"));
            circle_txt.setTextColor(Color.parseColor("#A9352D"));


            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.leftMargin=40;
            params.rightMargin=10;
            params.addRule(RelativeLayout.RIGHT_OF, circle_txt.getId());
            question_txt.setLayoutParams(params);

            question_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lastClickedCard!=null){
                        lastClickedCard.setCardBackgroundColor(Color.parseColor("#FDE64B"));
                        lastclickedQTxt.setTextColor(Color.parseColor("#A9352D"));
                        lastClickedImage.setVisibility(View.GONE);
                        lastclickedTxt.setVisibility(View.VISIBLE);

                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                        params.leftMargin=40;
                        params.rightMargin=10;
                        params.addRule(RelativeLayout.RIGHT_OF, lastclickedTxt.getId());
                        lastclickedQTxt.setLayoutParams(params);
                    }

                    CardView item_card=(CardView)question_item.findViewById(R.id.item_card);
                    lastClickedCard=item_card;
                    lastclickedTxt=circle_txt;
                    lastClickedImage=circle_img;
                    lastclickedQTxt=question_txt;
                    item_card.setCardBackgroundColor(Color.parseColor("#4CC609"));
                    lastclickedQTxt.setTextColor(Color.parseColor("#ffffff"));
                    circle_txt.setVisibility(View.GONE);
                    circle_img.setVisibility(View.VISIBLE);

                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    params.leftMargin=40;
                    params.rightMargin=10;
                    params.addRule(RelativeLayout.RIGHT_OF, circle_img.getId());
                    question_txt.setLayoutParams(params);

                    OptionSelectedID=question_item.getTag().toString();

                }
            });


            question_lay.addView(question_item);
        }

    }

    public void setTextAnserList(){
        submit_vote.setVisibility(View.GONE);
        ScrollView.LayoutParams params=new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.MATCH_PARENT);
        scroll_view.setLayoutParams(params);
        scroll_view.fullScroll(ScrollView.FOCUS_UP);
        LinearLayout question_lay=(LinearLayout)findViewById(R.id.question_lay);
        question_lay.removeAllViews();
        LayoutInflater layoutInflater=(LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        List<PollOptionModel> pollOptionModels= PollDetailsManager.pollQAModel.getPollOptionModels();

        for (int i=0;i<pollOptionModels.size();i++){
            final PollOptionModel option=pollOptionModels.get(i);


            final View answer_item = layoutInflater.inflate(R.layout.poll_txt_answer_lay, null);
            final View progress_view=answer_item.findViewById(R.id.progress_view);
            final GradientDrawable bgShape = (GradientDrawable) progress_view.getBackground();
            final FrameLayout fram_lay=(FrameLayout) answer_item.findViewById(R.id.fram_lay);
            final TextView question_txt = (TextView) answer_item.findViewById(R.id.question_txt);
            final TextView circle_txt=(TextView)answer_item.findViewById(R.id.circle_txt);


            question_txt.setText(""+option.getAnswertitle());

            GradientDrawable circle_Shape = (GradientDrawable) circle_txt.getBackground();
            circle_Shape.setColor(Color.parseColor("#A9352D"));
            circle_txt.setTextColor(Color.parseColor("#ffffff"));

            fram_lay.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    fram_lay.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int card_width = fram_lay.getWidth();

                    float percentage_prog=0.0f;
                    if(!option.getPercentage().equals("")){
                        percentage_prog=Float.valueOf(option.getPercentage());
                    }

                    float progress_width=(float) (card_width/100.0)*percentage_prog;
                    if(percentage_prog==100)  bgShape.setCornerRadii(new float[]{20.0f, 20.0f, 20.0f, 20.0f,20.0f, 20.0f, 20.0f, 20.0f});
                    circle_txt.setText(""+ ( Math.round(percentage_prog))+"%");
                    FrameLayout.LayoutParams params=new FrameLayout.LayoutParams((int) progress_width, FrameLayout.LayoutParams.MATCH_PARENT);
                    progress_view.setLayoutParams(params);

                    bgShape.setColor(Color.parseColor("#4CC609"));

                    /*if(percentage_prog>0 && percentage_prog<=10)
                        bgShape.setColor(Color.parseColor(colorArr[0]));
                    else if(percentage_prog>11 && percentage_prog<=20)
                        bgShape.setColor(Color.parseColor(colorArr[1]));
                    else if(percentage_prog>21 && percentage_prog<=30)
                        bgShape.setColor(Color.parseColor(colorArr[2]));
                    else if(percentage_prog>31 && percentage_prog<=40)
                        bgShape.setColor(Color.parseColor(colorArr[3]));
                    else if(percentage_prog>41 && percentage_prog<=50)
                        bgShape.setColor(Color.parseColor(colorArr[4]));
                    else if(percentage_prog>51 && percentage_prog<=60)
                        bgShape.setColor(Color.parseColor(colorArr[5]));
                    else if(percentage_prog>61)
                        bgShape.setColor(Color.parseColor(colorArr[5]));*/


                }
            });

            question_lay.addView(answer_item);
        }
    }


    ImageView lastClickedImageItem=null;
    ImageView lastClickedCheck_Image=null;

    public void setImageQuestionList(){
        submit_vote.setVisibility(View.VISIBLE);
        LinearLayout question_lay=(LinearLayout)findViewById(R.id.question_lay);
        question_lay.removeAllViews();
        LayoutInflater layoutInflater=(LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        List<PollOptionModel> pollOptionModels= PollDetailsManager.pollQAModel.getPollOptionModels();

        for (int i=0;i<pollOptionModels.size();i++){

            PollOptionModel option=pollOptionModels.get(i);

            final View question_item = layoutInflater.inflate(R.layout.poll_image_question_item, null);
            final TextView question_txt = (TextView) question_item.findViewById(R.id.question_txt);
            final RoundedImageView qst_img = (RoundedImageView) question_item.findViewById(R.id.qst_img);
            final ImageView checked_img=(ImageView)question_item.findViewById(R.id.checked_img);
            final CardView item_card=(CardView)question_item.findViewById(R.id.item_card);
            question_item.setTag(option.getOpinionanswerid());

            question_txt.setText(""+option.getAnswertitle());

            String profile_img_url1=option.getImageurl().toString();
            final String profile_img_url=profile_img_url1.replace(" ", "%20");

            if (!profile_img_url.equalsIgnoreCase("null") && !profile_img_url.equalsIgnoreCase("")
                    && !profile_img_url.equalsIgnoreCase(" ")) {
                Picasso.with(mContext)
                        .load(profile_img_url)
                        .resize(100, 100)
                        .centerInside()
                        .placeholder(R.drawable.icon_profile_drawer_bg)
                        .error(R.drawable.icon_profile_drawer_bg)
                        .into(qst_img);
            }

            if (!profile_img_url.equalsIgnoreCase("null") && !profile_img_url.equalsIgnoreCase("")
                    && !profile_img_url.equalsIgnoreCase(" ")) {

                qst_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(mContext, FullimageActivity.class);
                        intent.putExtra("imageval", profile_img_url);
                        mContext.startActivity(intent);
                    }
                });

            }


            question_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lastClickedImageItem!=null){
                        lastClickedCheck_Image.setVisibility(View.GONE);
                        lastClickedImageItem.setAlpha(1.0f);
                        lastClickedCard.setCardBackgroundColor(Color.parseColor("#ffffff"));
                        lastclickedQTxt.setTextColor(Color.parseColor("#000000"));
                    }

                    lastclickedQTxt=question_txt;
                    item_card.setCardBackgroundColor(Color.parseColor("#4CC609"));
                    lastclickedQTxt.setTextColor(Color.parseColor("#ffffff"));
                    lastClickedCard=item_card;
                    lastClickedImageItem=qst_img;
                    lastClickedCheck_Image=checked_img;
                    qst_img.setAlpha(0.5f);
                    checked_img.setVisibility(View.VISIBLE);
                    OptionSelectedID=question_item.getTag().toString();
                }
            });

            question_lay.addView(question_item);
        }
    }

    public void setImageAnserList(){
        submit_vote.setVisibility(View.GONE);
        ScrollView.LayoutParams params=new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.MATCH_PARENT);
        scroll_view.setLayoutParams(params);
        scroll_view.fullScroll(ScrollView.FOCUS_UP);
        LinearLayout question_lay=(LinearLayout)findViewById(R.id.question_lay);
        question_lay.removeAllViews();
        LayoutInflater layoutInflater=(LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        List<PollOptionModel> pollOptionModels= PollDetailsManager.pollQAModel.getPollOptionModels();

        for (int i=0;i<pollOptionModels.size();i++){
            final PollOptionModel option=pollOptionModels.get(i);

            final View answer_item = layoutInflater.inflate(R.layout.poll_image_answer_item, null);
            final View progress_view=answer_item.findViewById(R.id.progress_view);
            //final GradientDrawable bgShape = (GradientDrawable) progress_view.getBackground();
            final FrameLayout fram_lay=(FrameLayout) answer_item.findViewById(R.id.fram_lay);
            final TextView circle_txt=(TextView)answer_item.findViewById(R.id.circle_txt);

            final TextView question_txt = (TextView) answer_item.findViewById(R.id.question_txt);
            final RoundedImageView ans_img = (RoundedImageView) answer_item.findViewById(R.id.ans_img);


            question_txt.setText(""+option.getAnswertitle());

            GradientDrawable circle_Shape = (GradientDrawable) circle_txt.getBackground();
            circle_Shape.setColor(Color.parseColor("#A9352D"));
            circle_txt.setTextColor(Color.parseColor("#ffffff"));

            String profile_img_url1=option.getImageurl().toString();
            final String profile_img_url=profile_img_url1.replace(" ", "%20");

            if (!profile_img_url.equalsIgnoreCase("null") && !profile_img_url.equalsIgnoreCase("")
                    && !profile_img_url.equalsIgnoreCase(" ")) {
                Picasso.with(mContext)
                        .load(profile_img_url)
                        .resize(100, 100)
                        .centerInside()
                        .placeholder(R.drawable.icon_profile_drawer_bg)
                        .error(R.drawable.icon_profile_drawer_bg)
                        .into(ans_img);
            }


            if (!profile_img_url.equalsIgnoreCase("null") && !profile_img_url.equalsIgnoreCase("")
                    && !profile_img_url.equalsIgnoreCase(" ")) {

                ans_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(mContext, FullimageActivity.class);
                        intent.putExtra("imageval", profile_img_url);
                        mContext.startActivity(intent);
                    }
                });

            }

            fram_lay.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    fram_lay.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int card_width = fram_lay.getWidth();

                    float percentage_prog=0.0f;
                    if(!option.getPercentage().equals("")){
                        percentage_prog=Float.valueOf(option.getPercentage());
                    }


                    float progress_width=(float) (card_width/100.0)*percentage_prog;
                    circle_txt.setText(""+Math.round(percentage_prog)+"%");
                    FrameLayout.LayoutParams params=new FrameLayout.LayoutParams((int) progress_width, FrameLayout.LayoutParams.MATCH_PARENT);
                    progress_view.setLayoutParams(params);

                    progress_view.setBackgroundColor(Color.parseColor("#4CC609"));
                    /*if(percentage_prog>0 && percentage_prog<=10)
                        progress_view.setBackgroundColor(Color.parseColor(colorArr[0]));
                    else if(percentage_prog>11 && percentage_prog<=20)
                        progress_view.setBackgroundColor(Color.parseColor(colorArr[1]));
                    else if(percentage_prog>21 && percentage_prog<=30)
                        progress_view.setBackgroundColor(Color.parseColor(colorArr[2]));
                    else if(percentage_prog>31 && percentage_prog<=40)
                        progress_view.setBackgroundColor(Color.parseColor(colorArr[3]));
                    else if(percentage_prog>41 && percentage_prog<=50)
                        progress_view.setBackgroundColor(Color.parseColor(colorArr[4]));
                    else if(percentage_prog>51 && percentage_prog<=60)
                        progress_view.setBackgroundColor(Color.parseColor(colorArr[5]));
                    else if(percentage_prog>61)
                        progress_view.setBackgroundColor(Color.parseColor(colorArr[5]));*/


                }
            });

            question_lay.addView(answer_item);
        }
    }


    public void setOtherPollList(){
        other_poll_list=(RecyclerView)findViewById(R.id.other_poll_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        other_poll_list.setLayoutManager(layoutManager);
        other_poll_list.setItemAnimator(new DefaultItemAnimator());
        other_poll_list.setAdapter(new OtherPollAdapter(mContext));
    }



    ProgressDialog progressDialog_custom=null;
    public class GetPollQA extends AsyncTask<String, Void, Void> {

        String responseString = "";
        PollDetailsManager pollDetailsManager = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            if (progressDialog_custom == null) {
                progressDialog_custom = ProgressLoaderUtilities.getProgressDialog(mContext);
            }
            if (!progressDialog_custom.isShowing()) {
                progressDialog_custom.show();
            }

        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                pollDetailsManager = new PollDetailsManager(mContext);
                responseString = pollDetailsManager.callOpinionDetails(pollid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog_custom.dismiss();
            //  Toast.makeText(mContext,"212"+responseString,Toast.LENGTH_SHORT).show();
            if(responseString.equals("100")){

                if(PollDetailsManager.pollQAModel.getPollOptionModels()!=null) {
                    if(PollDetailsManager.pollQAModel.getPollOptionModels().size()>0) {
                        no_data_txt.setVisibility(View.GONE);
                        poll_lay.setVisibility(View.VISIBLE);
                        String opinionpollid = PollDetailsManager.pollQAModel.getOpinionpollid();
                        String questiontitle = PollDetailsManager.pollQAModel.getQuestiontitle();
                        String noofparticipants = PollDetailsManager.pollQAModel.getNoofparticipants();
                        String isanswer = PollDetailsManager.pollQAModel.getIsanswer();
                        String polltype = PollDetailsManager.pollQAModel.getPolltype();
                        String end_date = PollDetailsManager.pollQAModel.getEnd_date();
                        current_poll_qt_txt.setText("" + questiontitle);
                        voter_count.setText("" + noofparticipants);
                        date_time_txt.setText("" + end_date);
                        pollid = opinionpollid;

                        if (isanswer.equals("question")) {
                            if (polltype.equals("text")) {
                                setQuestionList();
                            }

                            if (polltype.equals("image")) {
                                setImageQuestionList();
                            }
                        } else if (isanswer.equals("answered")) {
                            if (polltype.equals("text")) {
                                setTextAnserList();
                            }

                            if (polltype.equals("image")) {
                                setImageAnserList();
                            }
                        }

                    }
                    else{
                        poll_lay.setVisibility(View.GONE);
                        no_data_txt.setVisibility(View.VISIBLE);
                    }

                }

                else{
                    poll_lay.setVisibility(View.GONE);
                    no_data_txt.setVisibility(View.VISIBLE);
                }

                if(notificationmasterid!=null)
                    new GetReadNotification().execute();
            }


            else{
                Toast.makeText(mContext,""+responseString,Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class SubmitPoll extends AsyncTask<String, Void, Void> {

        String responseString = "";
        VotePollManager votePollManager = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            if (progressDialog_custom == null) {
                progressDialog_custom = ProgressLoaderUtilities.getProgressDialog(mContext);
            }
            if (!progressDialog_custom.isShowing()) {
                progressDialog_custom.show();
            }

        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                votePollManager = new VotePollManager(mContext);
                responseString = votePollManager.callVotePoll(OptionSelectedID,pollid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog_custom.dismiss();

            if(responseString.equals("100")){

                Toast.makeText(mContext,"Vote Submitted.",Toast.LENGTH_SHORT).show();
                if(OpinionPollQuestionAnswerManager.pollQAModel.getPollOptionModels()!=null) {
                    no_data_txt.setVisibility(View.GONE);
                    poll_lay.setVisibility(View.VISIBLE);
                    String opinionpollid = OpinionPollQuestionAnswerManager.pollQAModel.getOpinionpollid();
                    String questiontitle = OpinionPollQuestionAnswerManager.pollQAModel.getQuestiontitle();
                    String noofparticipants = OpinionPollQuestionAnswerManager.pollQAModel.getNoofparticipants();
                    String isanswer = OpinionPollQuestionAnswerManager.pollQAModel.getIsanswer();
                    String polltype = OpinionPollQuestionAnswerManager.pollQAModel.getPolltype();
                    String end_date = OpinionPollQuestionAnswerManager.pollQAModel.getEnd_date();
                    current_poll_qt_txt.setText("" + questiontitle);
                    voter_count.setText("" + noofparticipants);
                    date_time_txt.setText("" + end_date);


                    if (isanswer.equals("question")) {
                        if (polltype.equals("text")) {
                            setQuestionList();
                        }

                        if (polltype.equals("image")) {
                            setImageQuestionList();
                        }
                    } else if (isanswer.equals("answered")) {
                        if (polltype.equals("text")) {
                            setTextAnserList();
                        }

                        if (polltype.equals("image")) {
                            setImageAnserList();
                        }
                    }


                }

                else{
                    poll_lay.setVisibility(View.GONE);
                    no_data_txt.setVisibility(View.VISIBLE);
                }


            }

            else{
                if(responseString.equals("1001")) {
                    Toast.makeText(mContext, "You have already voted for this Poll.", Toast.LENGTH_SHORT).show();
                    new GetPollQA().execute();
                }
                else if(responseString.equals("1002")) {
                    Toast.makeText(mContext, "Unauthorized: Access is denied due to invalid credentials.", Toast.LENGTH_SHORT).show();
                    new TaskLogout().execute();
                }
                else if(responseString.equals("1003")) {
                    Toast.makeText(mContext, "This poll has already finished please view/vote on current poll.", Toast.LENGTH_SHORT).show();
                    new GetPollQA().execute();
                }
            }
        }
    }



    public class GetOtherPolls extends AsyncTask<String, Void, Void> {

        String responseString = "";
        OtherPoolMamager otherPoolMamager = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            if (progressDialog_custom == null) {
                progressDialog_custom = ProgressLoaderUtilities.getProgressDialog(mContext);
            }
            if (!progressDialog_custom.isShowing()) {
                progressDialog_custom.show();
            }

        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                otherPoolMamager = new OtherPoolMamager(mContext);
                responseString = otherPoolMamager.callOtherPoll();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog_custom.dismiss();

            if(responseString.equals("100")){
                setOtherPollList();
            }


            else{
                Toast.makeText(mContext,""+responseString,Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class GetReadNotification extends AsyncTask<String, Void, Void> {
        String responseString = "";
        String s = "Please wait...";
        @Override
        protected void onPreExecute() {
        }
        @Override
        protected Void doInBackground(String... params) {
// TODO Auto-generated method stub
            try {
                responseString = new SinglePostManager(mContext).callReadNotification(postId,notificationmasterid);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            progressDialog_custom.dismiss();
            if (responseString.equals("100")) {


            } else {
                Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
            }
        }
    }


    public class TaskLogout extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";
        ProgressDialog progressDialog=null;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog= ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                UserLoginInfo userLoginInfo = new LoginManager(mContext).getUserInfo().get(0);
                String user_id = userLoginInfo.getUserid();
                responseString = new LogOutManager(mContext).logoutUser(user_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try
            {
                progressDialog.dismiss();
                if (responseString.equals("100"))
                {
                    DaoSession daoSession = BaseManager.getDBSessoin(mContext);
                    daoSession.getUserLoginInfoDao().deleteAll();
                    SharedPrefrenceManager.putSelectedTab(mContext, "");
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(loginIntent);
                    ((Activity)mContext).finish();
                    SharedPrefrenceManager.putUserID(mContext, null);
                    SharedPrefrenceManager.putPassword(mContext, null);
                    SharedPrefrenceManager.putUsername(mContext, null);
                }
                else
                {
                    Toast.makeText(mContext,""+responseString,Toast.LENGTH_LONG).show();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

}
