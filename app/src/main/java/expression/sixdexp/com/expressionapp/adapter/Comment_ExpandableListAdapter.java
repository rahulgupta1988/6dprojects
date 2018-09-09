package expression.sixdexp.com.expressionapp.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import expression.sixdexp.com.expressionapp.PolicyCommentActivity;
import expression.sixdexp.com.expressionapp.ProfileDetails_Search;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.TagActivty;
import expression.sixdexp.com.expressionapp.manager.CommentTagManager;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.SrijanCommentReadManager;
import expression.sixdexp.com.expressionapp.manager.SrijanPolicyCommentsManager;
import expression.sixdexp.com.expressionapp.manager.SrijanSecondLevelCommentManager;
import expression.sixdexp.com.expressionapp.manager.SrisanTaggedUserManager;
import expression.sixdexp.com.expressionapp.manager.UserInfoManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;

/**
 * Created by hp on 8/12/2017.
 */

public class Comment_ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    String isrijan="0";
    String policyid;
    public static List<SrijanPolicyCommentsManager.ParentCommentModel> _listDataHeader; // header titles
    // child data in format of header title, child title
    public static Map<SrijanPolicyCommentsManager.ParentCommentModel,List<SrijanPolicyCommentsManager.ChildCommentModel>> _listDataChild;

    public Comment_ExpandableListAdapter(Context context,String policyid,List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        isrijan=new LoginManager(context).getUserInfo().get(0).getIsrijan();
        this.policyid=policyid;
        this._listDataHeader = SrijanPolicyCommentsManager.listDataHeader;
        this._listDataChild = SrijanPolicyCommentsManager.listDataChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        SrijanPolicyCommentsManager.ChildCommentModel childCommentModel=(SrijanPolicyCommentsManager.ChildCommentModel)getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.srijan_comment_chuld_view, null);
        }


        View child_devider=(View)convertView.findViewById(R.id.child_devider);
        final ImageView check_read=(ImageView)convertView.findViewById(R.id.check_read);
        ImageView user_img=(ImageView) convertView.findViewById(R.id.user_img);
        TextView user_name=(TextView)convertView.findViewById(R.id.user_name);
        TextView comment_txt=(TextView) convertView.findViewById(R.id.comment_txt);
        TextView date_time=(TextView)convertView.findViewById(R.id.date_time);
        final FrameLayout tag_lay=(FrameLayout)convertView.findViewById(R.id.tag_lay);
        TextView tagcount_txt=(TextView)convertView.findViewById(R.id.tagcount_txt);

        String user_image_url=childCommentModel.getUserprofileimage();
        String user_nm=childCommentModel.getUsername();
        String comt_txt=childCommentModel.getCommenttext();
        String dt_tn=childCommentModel.getCommentdatedisplayvalue();
        String time_tn=childCommentModel.getCommentDateTimeDisplayValue();
        String tag_count=childCommentModel.getTaguser();

        if (!tag_count.equalsIgnoreCase("null") && tag_count!=null) {
            if(tag_count.equals("0"))
                tag_lay.setVisibility(View.GONE);
            else {
                tag_lay.setVisibility(View.VISIBLE);
                tagcount_txt.setText("" + tag_count);
                tag_lay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String parentID =((SrijanPolicyCommentsManager.ChildCommentModel) getChild(groupPosition, childPosition)).getParentcommentid();
                        String commentlevel ="2";
                        String childcommentid=((SrijanPolicyCommentsManager.ChildCommentModel) getChild(groupPosition, childPosition)).getCommentid();
                        tagWondow(parentID,policyid,commentlevel,childcommentid,tag_lay, 300);
                    }
                });

            }
        }


        user_name.setText(user_nm);
        comment_txt.setText(Html.fromHtml(comt_txt));
        date_time.setText(dt_tn+" "+time_tn);

        Log.i("imageurl 435435",""+user_image_url);

        user_image_url=user_image_url.replace(" ", "%20");

        Picasso.with(_context)
                .load(user_image_url)
                .resize(70,70)
                .onlyScaleDown()
                .centerCrop()
                .placeholder(R.drawable.default_square_user)
                .error(R.drawable.default_square_user)
                .into(user_img);



        if(childCommentModel.getSrijanadminfeedbackstatus().equals("1")){
            check_read.setImageResource(R.drawable.check_read_srijan);
        }

        else{
            if(isrijan.equals("1")) {
                check_read.setImageResource(R.drawable.uncheck_read_srijan);
            }

            else{
                check_read.setVisibility(View.GONE);
            }
        }

        if(isrijan.equals("1")){
            check_read.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String commentID= (String)((SrijanPolicyCommentsManager.ChildCommentModel) getChild(groupPosition, childPosition)).getCommentid();
                    String commentLevel="2";
                    String feedbackID= (String)((SrijanPolicyCommentsManager.ChildCommentModel) getChild(groupPosition, childPosition)).getSrijanadminfeedbackstatus();
                    if(feedbackID.equals("0")) feedbackID="1";
                    else feedbackID="0";
                    postCommentRead(commentID,commentLevel,feedbackID);
                }
            });
        }

        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUserID= (String)((SrijanPolicyCommentsManager.ChildCommentModel) getChild(groupPosition, childPosition)).getUserid();
                new GetUserProfile().execute();

            }
        });

        if(isLastChild) {

            child_devider.setVisibility(View.VISIBLE);

        } else {

            child_devider.setVisibility(View.INVISIBLE);

        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        SrijanPolicyCommentsManager.ParentCommentModel parentCommentModel = (SrijanPolicyCommentsManager.ParentCommentModel) getGroup(groupPosition);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.srijan_commment_parent_item, null);
        }

        final LinearLayout parent_lay=(LinearLayout)convertView.findViewById(R.id.parent_lay);
        View parent_divider=(View)convertView.findViewById(R.id.parent_divider);
        final ImageView check_read=(ImageView)convertView.findViewById(R.id.check_read);
        ImageView attached_ic=(ImageView)convertView.findViewById(R.id.attached_ic);
        ImageView user_img=(ImageView) convertView.findViewById(R.id.user_img);
        TextView user_name=(TextView)convertView.findViewById(R.id.user_name);
        TextView comment_txt=(TextView) convertView.findViewById(R.id.comment_txt);
        final ImageView replay_icon=(ImageView)convertView.findViewById(R.id.replay_icon);
        TextView date_time=(TextView)convertView.findViewById(R.id.date_time);
        final FrameLayout tag_lay=(FrameLayout)convertView.findViewById(R.id.tag_lay);
        TextView tagcount_txt=(TextView)convertView.findViewById(R.id.tagcount_txt);


        String user_image_url=parentCommentModel.getUserprofileimage();
        String user_nm=parentCommentModel.getUsername();
        String comt_txt=parentCommentModel.getCommenttext();
        String dt_tn=parentCommentModel.getCommentdatedisplayvalue();
        String time_tn=parentCommentModel.getCommentdatetimedisplayvalue();

        String tag_count=parentCommentModel.getTaguser();
        if (!tag_count.equalsIgnoreCase("null") && tag_count!=null) {
            if(tag_count.equals("0"))
                tag_lay.setVisibility(View.GONE);
            else {
                tag_lay.setVisibility(View.VISIBLE);
                tagcount_txt.setText("" + tag_count);
                tag_lay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String parentID =((SrijanPolicyCommentsManager.ParentCommentModel) getGroup(groupPosition)).getParentcommentid();
                        String commentlevel ="1";
                        String childcommentid="0";
                        tagWondow(parentID,policyid,commentlevel,childcommentid,tag_lay, 300);
                    }
                });

            }
        }

        if(parentCommentModel.getCommentdocumentpath().length()>0){
            attached_ic.setVisibility(View.VISIBLE);
        }
        else {
            attached_ic.setVisibility(View.INVISIBLE);
        }

        attached_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doc_url=  (String)((SrijanPolicyCommentsManager.ParentCommentModel) getGroup(groupPosition)).getCommentdocumentpath();

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(doc_url));
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                _context.startActivity(intent);
            }
        });

        user_name.setText(user_nm);
        comment_txt.setText(Html.fromHtml(comt_txt));
        date_time.setText(dt_tn+" "+time_tn);


        user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUserID= (String)((SrijanPolicyCommentsManager.ParentCommentModel) getGroup(groupPosition)).getUserid();
                new GetUserProfile().execute();

            }
        });

        user_image_url=user_image_url.replace(" ", "%20");

        Picasso.with(_context)
                .load(user_image_url)
                .resize(70,70)
                .onlyScaleDown()
                .centerCrop()
                .placeholder(R.drawable.default_square_user)
                .error(R.drawable.default_square_user)
                .into(user_img);

        final ExpandableListView eLV = (ExpandableListView) parent;

        replay_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent_lay.setBackgroundColor(Color.parseColor("#e5e5e5"));
                eLV.setSelectedGroup(groupPosition);
                String parentcommentId= (String)((SrijanPolicyCommentsManager.ParentCommentModel) getGroup(groupPosition)).getParentcommentid();
                commentWondow(policyid,parentcommentId,parent_lay);
            }
        });

        if(parentCommentModel.getSrijanadminfeedbackstatus().equals("1")){
            check_read.setImageResource(R.drawable.check_read_srijan);
        }

        else{
            if(isrijan.equals("1")) {
                check_read.setImageResource(R.drawable.uncheck_read_srijan);
            }

            else{
                check_read.setVisibility(View.INVISIBLE);
            }
        }

        if(isrijan.equals("1")){
            check_read.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    String commentID= (String)((SrijanPolicyCommentsManager.ParentCommentModel) getGroup(groupPosition)).getParentcommentid();
                    String commentLevel="1";
                    String feedbackID= (String)((SrijanPolicyCommentsManager.ParentCommentModel) getGroup(groupPosition)).getSrijanadminfeedbackstatus();
                     if(feedbackID.equals("0")) feedbackID="1";
                     else feedbackID="0";
                    postCommentRead(commentID,commentLevel,feedbackID);
                }
            });
        }

        List<SrijanPolicyCommentsManager.ChildCommentModel>  childCommentModelslist= _listDataChild.get(parentCommentModel);
        if(childCommentModelslist.size()>0){
            parent_divider.setVisibility(View.GONE);
        }
        else {
            parent_divider.setVisibility(View.VISIBLE);
        }

        eLV.expandGroup(groupPosition);
        eLV.setDividerHeight(0);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    View window_layout=null;
    PopupWindow changeSortPopUp=null;
    String comment_txt="";


    public void commentWondow(final String policyid, final String parentcommentId,final View parent_lay) {
        if(changeSortPopUp==null){
            changeSortPopUp = new PopupWindow(_context);
            LayoutInflater layoutInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            window_layout = layoutInflater.inflate(R.layout.srijan_policy_comment_window, null);

            changeSortPopUp.setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        }


        ImageView tagicon=(ImageView)window_layout.findViewById(R.id.tagicon);
        TextView tagcount_txt=(TextView)window_layout.findViewById(R.id.tagcount_txt);
        Constant.tag_txt=tagcount_txt;
        tagicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(_context,TagActivty.class);
                ((Activity)_context).startActivity(intent);
            }
        });

        ImageView subcombtn=(ImageView)window_layout.findViewById(R.id.subcombtn);
        final EditText commenttxt=(EditText)window_layout.findViewById(R.id.commenttxt);


        subcombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_txt=commenttxt.getText().toString().trim();
                if(!comment_txt.equals("")){
                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager)(_context.getSystemService(Context.INPUT_METHOD_SERVICE));
                        imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                    }

                    ((PolicyCommentActivity)_context).postComment(parentcommentId,comment_txt,policyid);
                    changeSortPopUp.dismiss();

                }
                else{
                    Toast.makeText(_context,"Please Enter Comment",Toast.LENGTH_SHORT).show();
                }
            }
        });




        changeSortPopUp.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Constant.tag_txt.setText("");
                Constant.tag_txt.setVisibility(View.GONE);
                TagActivty.taged_users.clear();
                parent_lay.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

        Rect rc = new Rect();
        commenttxt.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        commenttxt.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        changeSortPopUp.setAnimationStyle(R.style.DialogAnimation);
        changeSortPopUp.setContentView(window_layout);

        DisplayMetrics displayMetrics=_context.getResources().getDisplayMetrics();
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
    }

    public void postCommentRead(final String commentid,final String commentlevel,final String feedbackid) {

        new AsyncTask<String, Void, Void>(){

            String responseString = "";
            SrijanCommentReadManager srijanCommentReadManager = null;

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                if (progressDialog == null) {
                    progressDialog = ProgressLoaderUtilities.getProgressDialog(_context);
                }
                if (!progressDialog.isShowing()) {
                    progressDialog.show();
                }

            }

            @Override
            protected Void doInBackground(String... params) {
                // TODO Auto-generated method stub
                try {
                    srijanCommentReadManager = new SrijanCommentReadManager(_context);
                    responseString = srijanCommentReadManager.callCommentRead(commentid,commentlevel,feedbackid);
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
                    //Toast.makeText(_context, "Comment saved successfully!", Toast.LENGTH_SHORT).show();
                    ((PolicyCommentActivity)_context).getAllComments();
                }
                else {
                    Toast.makeText(_context, "" + responseString, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


    String searchUserID="0";
    public class GetUserProfile extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(_context);
            progressDialog.show();
            Log.i("AutoTextSearch", "AutoTextSearch");
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                //responseString = new SearchManager(mContext).callForSearch(userIDstr);
                responseString = new UserInfoManager(_context).userInfo(searchUserID);
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

              /*  Intent proifleIntent = new Intent(mContext, UserProfileActivity.class);*/
                Intent proifleIntent = new Intent(_context, ProfileDetails_Search.class);
                ((Activity)_context).startActivity(proifleIntent);

            } else {


            }

        }


    }
    ProgressDialog progressDialog=null;
    public void tagWondow(final String parentID,final String policyid,final String commentlevel,
                          final String childcommentid,FrameLayout likeicon, int windowsize) {
        final PopupWindow changeSortPopUp = new PopupWindow(_context);
        changeSortPopUp.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        LayoutInflater layoutInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                progressDialog = ProgressLoaderUtilities.getProgressDialog(_context);
                progressDialog.show();
            }


            @Override
            protected Void doInBackground(Void... params) {
                try {
                    responseString = new SrisanTaggedUserManager(_context).callTagedUser(parentID, policyid,commentlevel,childcommentid);
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
                    TagPostAdapter tagPostAdapter = new TagPostAdapter(_context);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(_context);
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

        DisplayMetrics displaymetrics = _context.getResources().getDisplayMetrics();
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
