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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import expression.sixdexp.com.expressionapp.manager.OpinionPollQuestionAnswerManager;
import expression.sixdexp.com.expressionapp.manager.PollDetailsManager;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import model.PollOptionModel;

/**
 * Created by Praveen on 05-Sep-17.
 */

public class OtherPollDetailsActivty extends Activity {

    Context mContext;
    String[] colorArr = {"#9c0959", "#9c7d2c", "#4145ee", "#038995", "#4d1787", "#a5b40f", "#3a97d9"};
    TextView current_poll_qt_txt,voter_count,date_time_txt;
    String pollid="0";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otherpollactivity_lay);
        mContext=this;

        Intent intent=getIntent();
        if(intent!=null)
            pollid=intent.getStringExtra("pollid");

        initViews();
    }

    public void initViews(){
        current_poll_qt_txt=(TextView)findViewById(R.id.current_poll_qt_txt);
        voter_count=(TextView)findViewById(R.id.voter_count);
        date_time_txt=(TextView)findViewById(R.id.date_time_txt);
        if(!pollid.equals("0"))
        new GetPollQA().execute();
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

            if(responseString.equals("100")){

                if(PollDetailsManager.pollQAModel.getPollOptionModels()!=null) {
                    if (PollDetailsManager.pollQAModel.getPollOptionModels().size() > 0) {
                        String opinionpollid = PollDetailsManager.pollQAModel.getOpinionpollid();
                        String questiontitle = PollDetailsManager.pollQAModel.getQuestiontitle();
                        String noofparticipants = PollDetailsManager.pollQAModel.getNoofparticipants();
                        String isanswer = PollDetailsManager.pollQAModel.getIsanswer();
                        String polltype = PollDetailsManager.pollQAModel.getPolltype();
                        String end_date = PollDetailsManager.pollQAModel.getEnd_date();
                        current_poll_qt_txt.setText("" + questiontitle);
                        voter_count.setText("" + noofparticipants);
                        date_time_txt.setText("" + end_date);

                        if (isanswer.equals("answered")) {
                            if (polltype.equals("text")) {
                                setTextAnserList();
                            }

                            if (polltype.equals("image")) {
                                setImageAnserList();
                            }
                        }


                    }

                }


            }


            else{
                Toast.makeText(mContext,""+responseString,Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setTextAnserList(){
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
            circle_Shape.setColor(Color.parseColor("#ffffff"));
            circle_txt.setTextColor(Color.parseColor("#A9352D"));


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

                    int temp_pro=Math.round(percentage_prog);
                    if(temp_pro==100 || temp_pro==99) {
                        Log.i("1987 100",""+Math.round(percentage_prog));
                        bgShape.setCornerRadii(new float[]{20.0f, 20.0f, 20.0f, 20.0f, 20.0f, 20.0f, 20.0f, 20.0f});
                    }

                 /*   if(temp_pro<99) {
                        Log.i("1987 98",""+Math.round(percentage_prog));
                        bgShape.setCornerRadii(new float[]{20.0f, 20.0f, 0.0f, 0.0f,0.0f, 0.0f, 20.0f, 20.0f});
                    }*/

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

    public void setImageAnserList(){
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
            circle_Shape.setColor(Color.parseColor("#ffffff"));
            circle_txt.setTextColor(Color.parseColor("#A9352D"));


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
                   /* if(percentage_prog>0 && percentage_prog<=10)
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

}
