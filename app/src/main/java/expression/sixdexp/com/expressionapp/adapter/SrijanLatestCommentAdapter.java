package expression.sixdexp.com.expressionapp.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import db.SrijanTOPComment;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.TagActivty;
import expression.sixdexp.com.expressionapp.manager.SrijanTOPCommentsManager;
import expression.sixdexp.com.expressionapp.manager.SrisanTaggedUserManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;

/**
 * Created by Praveen on 28-Jul-17.
 */

public class SrijanLatestCommentAdapter  extends RecyclerView.Adapter<SrijanLatestCommentAdapter.CommentHolder> {

    Context mContext;
    List<SrijanTOPComment> srijanTOPComments;
    ListCallBack listCallBack;
    TextView temp_text=null;
    RelativeLayout temp_card_lay=null;
    RecyclerView recyclerView;

    public interface ListCallBack{
       void setPosition(int pos);
       void commentPost(String parentcommentId,String comment,String policyid);
    }

    public  SrijanLatestCommentAdapter(Context mContext,ListCallBack listCallBack){
        this.mContext=mContext;
        this.listCallBack=listCallBack;
        srijanTOPComments=new SrijanTOPCommentsManager(mContext).getTopComments();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView=recyclerView;
    }

    int tepm_w=0;;

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.srijanlatestcommentadapter_item, parent, false);

        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        tepm_w=width;
        int temp_width=(int)(width-(width/3.4));

        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(temp_width, ViewGroup.LayoutParams.MATCH_PARENT);
        itemView.setLayoutParams(layoutParams);
        return new SrijanLatestCommentAdapter.CommentHolder(itemView);
    }

    LinearLayout temp_lay=null,temp_tag_view=null;
    int temp_pos=0;
    @Override
    public void onBindViewHolder(final CommentHolder holder, final int position) {

         SrijanTOPComment srijanTOPComment=srijanTOPComments.get(position);
         holder.date_txt.setText(srijanTOPComment.getCommentdatedisplayvalue());
         holder.time_txt.setText(srijanTOPComment.getCommenttimedisplayvalue());



        String tag_count=srijanTOPComment.getTaguser();

        if (!tag_count.equalsIgnoreCase("null") && tag_count!=null) {
            if(tag_count.equals("0"))
                holder.tag_lay.setVisibility(View.GONE);
            else {
                holder.tag_lay.setVisibility(View.VISIBLE);
                holder.tagcount_txt.setText("" + tag_count);
                holder.tag_lay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String parentID=srijanTOPComments.get(position).getParentcommentid();
                        String policyid=srijanTOPComments.get(position).getPolicyid();
                        String commentlevel=srijanTOPComments.get(position).getCommentlevel();
                        String childcommentid=srijanTOPComments.get(position).getChildcommentid();

                        tagWondow(parentID,policyid,commentlevel,childcommentid,holder.tag_lay, 300);
                    }
                });

            }
        }



       if(position%2==0){

               holder.comment_txt.setBackgroundColor(Color.parseColor("#5179E4"));
               holder.card_footer.setBackgroundColor(Color.parseColor("#5179E4"));
           holder.tag_view.setBackgroundColor(Color.parseColor("#5179E4"));

           String text = "<font color=#F4D722><b>@"+srijanTOPComment.getCommentusername()+"</b></font>"+"&nbsp;-&nbsp;"+srijanTOPComment.getUsercommentdisplayvalue();
           holder.comment_txt.setText(Html.fromHtml(text));
        }

        else{
            holder.comment_txt.setBackgroundColor(Color.parseColor("#5179E4"));
            holder.card_footer.setBackgroundColor(Color.parseColor("#5179E4"));
           holder.tag_view.setBackgroundColor(Color.parseColor("#5179E4"));
           String text = "<font color=#FFDF04><b>@"+srijanTOPComment.getCommentusername()+"</b></font>"+"&nbsp;-&nbsp;"+srijanTOPComment.getUsercommentdisplayvalue();
           holder.comment_txt.setText(Html.fromHtml(text));
        }

        holder.prent_lay.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

               /* if(position!=temp_pos) {


                    //recyclerView.scrollToPosition(position);
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    linearLayoutManager.setSmoothScrollbarEnabled(true);
                    linearLayoutManager.scrollToPositionWithOffset(position, tepm_w/6);

                    if (temp_lay != null)
                        setAnimationZoomout(temp_lay);
                    setAnimation(holder.prent_lay);
                    temp_lay = holder.prent_lay;
                }*/
                 if(temp_text!=null) {
                     temp_text.setBackgroundColor(Color.parseColor("#5179E4"));
                     temp_card_lay.setBackgroundColor(Color.parseColor("#5179E4"));
                     temp_tag_view.setBackgroundColor(Color.parseColor("#5179E4"));
                 }
                 temp_text=holder.comment_txt;
                 temp_card_lay=holder.card_footer;
                 temp_tag_view=holder.tag_view;
                 holder.comment_txt.setBackgroundColor(Color.parseColor("#0A40D1"));
                 holder.card_footer.setBackgroundColor(Color.parseColor("#0A40D1"));
                 holder.tag_view.setBackgroundColor(Color.parseColor("#0A40D1"));
                 listCallBack.setPosition(position);

                 temp_pos=position;
             }
         });

        if(srijanTOPComment.getCommentlevel().equals("1")){
            holder.rpl_lay.setVisibility(View.VISIBLE);
        }
        else {
            holder.rpl_lay.setVisibility(View.INVISIBLE);
        }

        holder.rpl_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentWondow(srijanTOPComments.get(position).getPolicyid(),srijanTOPComments.get(position).getParentcommentid());

            }
        });

       /* if(position==0){
            setAnimation(holder.prent_lay);
            temp_lay = holder.prent_lay;
        }*/
    }


    @Override
    public int getItemCount() {
        return srijanTOPComments.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder {

        TextView comment_txt,date_txt,time_txt;
        RelativeLayout rpl_lay,card_footer;
        LinearLayout prent_lay,child_lay;
        FrameLayout tag_lay;
        TextView tagcount_txt;
        LinearLayout tag_view;
        public CommentHolder(final View convertView) {
            super(convertView);
            comment_txt=(TextView)convertView.findViewById(R.id.comment_txt);
            date_txt=(TextView)convertView.findViewById(R.id.date_txt);
            time_txt=(TextView)convertView.findViewById(R.id.time_txt);
            rpl_lay=(RelativeLayout) convertView.findViewById(R.id.rpl_lay);
            card_footer=(RelativeLayout) convertView.findViewById(R.id.card_footer);
            prent_lay=(LinearLayout)convertView.findViewById(R.id.prent_lay);
            child_lay=(LinearLayout)convertView.findViewById(R.id.child_lay);
            tag_lay=(FrameLayout)convertView.findViewById(R.id.tag_lay);
            tagcount_txt=(TextView)convertView.findViewById(R.id.tagcount_txt);
            tag_view=(LinearLayout)convertView.findViewById(R.id.tag_view);

        }

    }


    View window_layout=null;
    PopupWindow changeSortPopUp=null;
    String comment_txt="";
    public void commentWondow(final String policyid, final String parentcommentId) {
        if(changeSortPopUp==null){
            changeSortPopUp = new PopupWindow(mContext);
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            window_layout = layoutInflater.inflate(R.layout.srijan_policy_comment_window, null);

            changeSortPopUp.setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        }


        ImageView subcombtn=(ImageView)window_layout.findViewById(R.id.subcombtn);
        ImageView tagicon=(ImageView)window_layout.findViewById(R.id.tagicon);
        TextView tagcount_txt=(TextView)window_layout.findViewById(R.id.tagcount_txt);
        final EditText commenttxt=(EditText)window_layout.findViewById(R.id.commenttxt);
        commenttxt.setText("");

        subcombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_txt=commenttxt.getText().toString().trim();
                if(!comment_txt.equals("")){
                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager)(mContext.getSystemService(Context.INPUT_METHOD_SERVICE));
                        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                    }

                    listCallBack.commentPost(parentcommentId,comment_txt,policyid);
                    changeSortPopUp.dismiss();

                }
                else{
                    Toast.makeText(mContext,"Please Enter Comment",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Constant.tag_txt=tagcount_txt;
        tagicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(mContext,TagActivty.class);
                mContext.startActivity(intent);
            }
        });




        Rect rc = new Rect();
        commenttxt.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        commenttxt.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        changeSortPopUp.setAnimationStyle(R.style.DialogAnimation);
        changeSortPopUp.setContentView(window_layout);

        DisplayMetrics displayMetrics=mContext.getResources().getDisplayMetrics();
        int height=displayMetrics.heightPixels;

        changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp.setHeight((int)(height/3));
        changeSortPopUp.setFocusable(true);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y =(int) (height-(height/3));
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(window_layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);
        changeSortPopUp.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                TagActivty.taged_users.clear();
                Constant.tag_txt.setText("");
                Constant.tag_txt.setVisibility(View.GONE);
                TagActivty.taged_users.clear();
               // Constant.tag_txt=null;
            }
        });

    }

    public void setAnimation(LinearLayout linearLayout){
        Animation myAnim1 = AnimationUtils.loadAnimation(mContext,R.anim.srijan_zoomin);
        myAnim1.setFillAfter(true);
        myAnim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        linearLayout.startAnimation(myAnim1);
    }

    public void setAnimationZoomout(LinearLayout linearLayout){
        Animation myAnim1 = AnimationUtils.loadAnimation(mContext,R.anim.srijan_zoomout);
        myAnim1.setFillAfter(true);
        myAnim1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        linearLayout.startAnimation(myAnim1);
    }

    ProgressDialog progressDialog=null;
    public void tagWondow(final String commentid,final String policyid,final String commentlevel,final String childcommentid,FrameLayout likeicon, int windowsize) {
        final PopupWindow changeSortPopUp = new PopupWindow(mContext);
        changeSortPopUp.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.tagwindow_view, null);

        LinearLayout arraow_lay=(LinearLayout)layout.findViewById(R.id.arraow_lay);
        arraow_lay.setVisibility(View.INVISIBLE);
        ImageView cancelshare = (ImageView) layout.findViewById(R.id.cancelshare);
        final RecyclerView taglist = (RecyclerView) layout.findViewById(R.id.likelist);

        cancelshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSortPopUp.dismiss();
            }
        });


        new AsyncTask<Void, Void, Void>() {

            String responseString = "";
            String s = "Plase Wait...";

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
                progressDialog.show();
            }


            @Override
            protected Void doInBackground(Void... params) {
                try {
                    responseString =  new SrisanTaggedUserManager(mContext).callTagedUser(commentid,policyid,commentlevel,childcommentid);
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
                    TagPostAdapter tagPostAdapter = new TagPostAdapter(mContext);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                    taglist.setLayoutManager(layoutManager);
                    taglist.setItemAnimator(new DefaultItemAnimator());
                    taglist.setAdapter(tagPostAdapter);
                } else {

                }

            }


        }.execute();




        Rect rc = new Rect();
        likeicon.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        likeicon.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        changeSortPopUp.setAnimationStyle(R.style.DialogAnimation);
        changeSortPopUp.setContentView(layout);

        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        Log.i("height 1083",""+height);

        changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        changeSortPopUp.setHeight((int)(height/1.5));
        changeSortPopUp.setFocusable(true);
        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y =(int) (height-(height/1.5));
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);

    }

}
