package expression.sixdexp.com.expressionapp.utility;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.manager.EmoticonLikeUnlikeManager;
import expression.sixdexp.com.expressionapp.manager.PolicyEmoticonDetailsManager;
import expression.sixdexp.com.expressionapp.manager.PolicyLikeUnlikeManager;
import model.PolicyModel;

/**
 * Created by Praveen on 11-Aug-17.
 */

public class EmoticonInterface {

    Context mContext;
    ImageView share_img;
    String policyid;
    String emoId;

    TextView emoji_a_txt,emoji_b_txt,emoji_c_txt,emoji_d_txt,emoji_e_txt,emoji_f_txt;
    public EmoticonInterface(Context mContext){
        this.mContext=mContext;
    }

    public void shareEmoticon(String policyid,ImageView share_img, final LinearLayout top_lay) {

        this.policyid=policyid;
        this.share_img=share_img;

        final PopupWindow changeSortPopUp1 = new PopupWindow(mContext);

        changeSortPopUp1.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View convertView = layoutInflater.inflate(R.layout.srijanpolicy_emoticoan_dialog, null);

        ImageView emoji_a,emoji_b,emoji_c,emoji_d,emoji_e,emoji_f;
        emoji_a_txt=(TextView)convertView.findViewById(R.id.emoji_a_txt);
        emoji_b_txt=(TextView)convertView.findViewById(R.id.emoji_b_txt);
        emoji_c_txt=(TextView)convertView.findViewById(R.id.emoji_c_txt);
        emoji_d_txt=(TextView)convertView.findViewById(R.id.emoji_d_txt);
        emoji_e_txt=(TextView)convertView.findViewById(R.id.emoji_e_txt);
        emoji_f_txt=(TextView)convertView.findViewById(R.id.emoji_f_txt);

        emoji_a=(ImageView)convertView.findViewById(R.id.emoji_a);
        emoji_b=(ImageView)convertView.findViewById(R.id.emoji_b);
        emoji_c=(ImageView)convertView.findViewById(R.id.emoji_c);
        emoji_d=(ImageView)convertView.findViewById(R.id.emoji_d);
        emoji_e=(ImageView)convertView.findViewById(R.id.emoji_e);
        emoji_f=(ImageView)convertView.findViewById(R.id.emoji_f);

        emoji_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emoId="3";
                new EmoticonLikeUnlikeTask().execute();
            }
        });

        emoji_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emoId="4";
                new EmoticonLikeUnlikeTask().execute();
            }
        });

        emoji_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emoId="5";
                new EmoticonLikeUnlikeTask().execute();
            }
        });


        emoji_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emoId="6";
                new EmoticonLikeUnlikeTask().execute();
            }
        });


        emoji_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emoId="8";
                new EmoticonLikeUnlikeTask().execute();
            }
        });


        emoji_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emoId="9";
                new EmoticonLikeUnlikeTask().execute();
            }
        });

        for(int i = 0; i< PolicyEmoticonDetailsManager.emoticonModelList.size(); i++){
            PolicyEmoticonDetailsManager.EmoticonModel emoticonModel =PolicyEmoticonDetailsManager.emoticonModelList.get(i);
            String emoid=emoticonModel.getEmoid();
            String emopercentage=emoticonModel.getEmopercentage();
            String emocount=emoticonModel.getEmocount();

            if(emoid.equals("3")){
                emoji_a_txt.setText(""+emocount+"/"+emopercentage+"%");
            }

            else if(emoid.equals("4")){
                emoji_b_txt.setText(""+emocount+"/"+emopercentage+"%");
            }

            else if(emoid.equals("5")){
                emoji_c_txt.setText(""+emocount+"/"+emopercentage+"%");
            }

            else if(emoid.equals("6")){
                emoji_d_txt.setText(""+emocount+"/"+emopercentage+"%");
            }

            else if(emoid.equals("8")){
                emoji_e_txt.setText(""+emocount+"/"+emopercentage+"%");
            }

            else if(emoid.equals("9")){
                emoji_f_txt.setText(""+emocount+"/"+emopercentage+"%");
            }

        }

        Rect rc = new Rect();
        share_img.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        share_img.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        // changeSortPopUp1.setAnimationStyle(R.style.DialogAnimation);
        changeSortPopUp1.setContentView(convertView);

        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        Log.i("height 1083",""+height);

        changeSortPopUp1.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //changeSortPopUp1.setHeight((int)(height/1.3));
        changeSortPopUp1.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeSortPopUp1.setFocusable(true);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y =-height;
        changeSortPopUp1.setBackgroundDrawable(new BitmapDrawable());


        final View container=(View)top_lay.getRootView();
        final WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        final WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.alpha=0.3f;
        // p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        //p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
        changeSortPopUp1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                p.alpha=1.0f;
                wm.updateViewLayout(container, p);
            }
        });


        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp1.showAtLocation(convertView, Gravity.BOTTOM, rc.left + OFFSET_X, rc.top + OFFSET_Y);
    }


    ProgressDialog progressDialog_custom=null;
    public class EmoticonLikeUnlikeTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        EmoticonLikeUnlikeManager emoticonLikeUnlikeManager = null;

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
                emoticonLikeUnlikeManager = new EmoticonLikeUnlikeManager(mContext);
                responseString = emoticonLikeUnlikeManager.callLikeEmoticon(policyid,emoId);
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

                for(int i = 0; i< EmoticonLikeUnlikeManager.emoticonModelList.size(); i++){
                    EmoticonLikeUnlikeManager.EmoticonModel emoticonModel =EmoticonLikeUnlikeManager.emoticonModelList.get(i);
                    String emoid=emoticonModel.getEmoid();
                    String emopercentage=emoticonModel.getEmopercentage();
                    String emocount=emoticonModel.getEmocount();

                    if(emoid.equals("3")){
                        emoji_a_txt.setText(""+emocount+"/"+emopercentage+"%");
                    }

                    else if(emoid.equals("4")){
                        emoji_b_txt.setText(""+emocount+"/"+emopercentage+"%");
                    }

                    else if(emoid.equals("5")){
                        emoji_c_txt.setText(""+emocount+"/"+emopercentage+"%");
                    }

                    else if(emoid.equals("6")){
                        emoji_d_txt.setText(""+emocount+"/"+emopercentage+"%");
                    }

                    else if(emoid.equals("8")){
                        emoji_e_txt.setText(""+emocount+"/"+emopercentage+"%");
                    }

                    else if(emoid.equals("9")){
                        emoji_f_txt.setText(""+emocount+"/"+emopercentage+"%");
                    }

                }
            }

            else{
                Toast.makeText(mContext,""+responseString,Toast.LENGTH_SHORT).show();
            }
        }
    }


}
