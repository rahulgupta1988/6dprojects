package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import db.AllUsers;
import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.adapter.SearchTag_Adapter;
import expression.sixdexp.com.expressionapp.adapter.TaggingAdapter;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.UsersManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.CustomAutoCompleteTextView;
import expression.sixdexp.com.expressionapp.utility.Utils;

/**
 * Created by Praveen on 06-Oct-17.
 */

public class TagActivty extends Activity {
    Context mContext;
    public static List<AllUsers> taged_users=new ArrayList<>();
    ArrayList<String> autolist = new ArrayList<String>();
    AutoCompleteTextView auto_view;
    ListView taged_user_list;
    String user_email="";
    ImageView cancelshare;
    TextView tagdone_btn;
    LinearLayout auto_view_lay;
    boolean backBydone=false;
    ProgressBar progress_show;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_activty_lay_new);
        mContext=this;
        initViews();

    }


    public void initViews(){
        progress_show=(ProgressBar)findViewById(R.id.progress_show);
        auto_view_lay=(LinearLayout)findViewById(R.id.auto_view_lay);
        tagdone_btn=(TextView)findViewById(R.id.tagdone_btn);
        cancelshare=(ImageView)findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tagdone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Log.i("tagged users", "" + taged_users.size());
                    backBydone = true;
                    if (Constant.tag_txt != null) {
                        Constant.tag_txt.setText("" + taged_users.size());
                        if (taged_users.size() > 0)
                            Constant.tag_txt.setVisibility(View.VISIBLE);
                        else Constant.tag_txt.setVisibility(View.GONE);
                    }
                    onBackPressed();


            }
        });
        List<UserLoginInfo> userDatas = new LoginManager(mContext).getUserInfo();
        user_email=userDatas.get(0).getEmailId();
        //  auto_view=(AutoCompleteTextView)findViewById(R.id.auto_view);
       /* auto_view=createAutoTextSearch();
        auto_view.addTextChangedListener(new ListenToText());
        auto_view_lay.addView(auto_view);*/


        taged_user_list=(ListView)findViewById(R.id.taged_user_list);
        fillAutoTextList();
        setTeagedUserList();
    }

    public CustomAutoCompleteTextView createAutoTextSearch() {
        CustomAutoCompleteTextView searchpostedit1 = new CustomAutoCompleteTextView(mContext);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        searchpostedit1.setLayoutParams(params);
        searchpostedit1.setThreshold(3);
        searchpostedit1.setBackgroundColor(Color.parseColor("#00000000"));
        searchpostedit1.setHint("Search for all");
        searchpostedit1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0);
        searchpostedit1.setPadding(0, 0, 0, 0);
        searchpostedit1.setCompoundDrawablePadding(10);
        return searchpostedit1;
    }

    TaggingAdapter taggingAdapter=null;
    public void setTeagedUserList(){
        taggingAdapter=new TaggingAdapter(mContext, taged_users, new TaggingAdapter.TaggingCallback() {
            @Override
            public void setPosition(int pos) {
                taged_users.remove(pos);
                taggingAdapter.notifyDataSetChanged();
                Utils.getListViewSize(taged_user_list);
            }
        });
        taged_user_list.setAdapter(taggingAdapter);
        Utils.getListViewSize(taged_user_list);
    }

    EditText search_edit;
    public void fillAutoTextList(){
        search_edit=(EditText)findViewById(R.id.search_edit);
        search_edit.addTextChangedListener(new ListenToText());

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(tagdone_btn.getWindowToken(),0);
        if(!backBydone) {
            taged_users.clear();
            if(Constant.tag_txt!=null)
                Constant.tag_txt.setText(""+taged_users.size());
            if(Constant.tag_txt!=null)  Constant.tag_txt.setVisibility(View.GONE);

        }
    }
    FilterTask filterTask=null;
    class ListenToText implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            Log.i("onTextChanged", "" + s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

            Log.i("afterTextChanged", "" + s.toString());
            if (s.toString().length() >= 3) {
                if(filterTask!=null)
                filterTask.cancel(true);

                filterTask=new FilterTask();
                filterTask.execute(s.toString());
                //getFilterList(s.toString());

            }

            else{
                if(changeSortPopUp!=null)
                    changeSortPopUp.dismiss();
            }
        }
    }






    PopupWindow changeSortPopUp=null;
    RecyclerView searc_dialog_lis=null;
    SearchTag_Adapter searchAdapter=null;
    View layout=null;
    Rect rc=null;
    public boolean isClickable=false;
    public void settingMenu() {

        int OFFSET_X = 0;
        int OFFSET_Y = 100;


        if(changeSortPopUp==null) {
            changeSortPopUp = new PopupWindow(mContext);
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = layoutInflater.inflate(R.layout.tag_search_dialog, null);

            searc_dialog_lis = (RecyclerView) layout.findViewById(R.id.searc_dialog_lis);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            searc_dialog_lis.setLayoutManager(layoutManager);
            searc_dialog_lis.setItemAnimator(new DefaultItemAnimator());


            searchAdapter = new SearchTag_Adapter(mContext, fillteredUsers, new SearchTag_Adapter.ItemClickedListener() {
                @Override
                public void item(int pos) {
                    if(isClickable){
                    for (int i = 0; i < taged_users.size(); i++) {
                        if (fillteredUsers.get(pos).getUser_id().equals(taged_users.get(i).getUser_id())) {
                            Toast.makeText(mContext, "Already added.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    if (taged_users.size() < 25) {
                        taged_users.add(fillteredUsers.get(pos));
                        taggingAdapter.notifyDataSetChanged();
                        Utils.getListViewSize(taged_user_list);
                        changeSortPopUp.dismiss();
                        search_edit.setText("");
                    } else {
                        changeSortPopUp.dismiss();
                        search_edit.setText("");
                        Toast.makeText(mContext, "Maximum of 25 employees can be tagged.", Toast.LENGTH_SHORT).show();
                    }


                }
            }
            });

            searc_dialog_lis.setAdapter(searchAdapter);

            DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;

            rc = new Rect();
            auto_view_lay.getWindowVisibleDisplayFrame(rc);
            int[] xy = new int[2];
            auto_view_lay.getLocationInWindow(xy);
            rc.offset(xy[0], xy[1]);
            // Creating the PopupWindow


            //changeSortPopUp.setAnimationStyle(R.style.animationName);
            changeSortPopUp.setContentView(layout);
            changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            changeSortPopUp.setHeight((height - (height / 4)));
            changeSortPopUp.setFocusable(false);
            changeSortPopUp.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

            // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.


            // Clear the default translucent background
            changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

            // Displaying the popup at the specified location, + offsets.
            changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);
        }
        else{
            searchAdapter.notifyDataSetChanged();
            changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);
        }
    }



    public class FilterTask extends AsyncTask<String,Void,Void>{

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            isClickable=false;
            progress_show.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... strings) {
            String keyWord=strings[0];
            getFilterList(keyWord);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            settingMenu();
            isClickable=true;
            progress_show.setVisibility(View.GONE);
            if(search_edit.getText().toString().equals("")){
                if(changeSortPopUp!=null)
                    changeSortPopUp.dismiss();
            }


        }
    }

    List<AllUsers> fillteredUsers=new ArrayList<>();
    public void getFilterList(String keyWord){
        fillteredUsers.clear();
        int temp_pos=0;
        List<AllUsers> allUserses =new UsersManager(mContext).getAllUserList();
        Collections.sort(allUserses,usersComparator);
        for (AllUsers people : allUserses) {
                   /* String temp1=("Mr "+constraint.toString()).toLowerCase();
                    String temp2=("Ms "+constraint.toString()).toLowerCase();*/




            if (people.getName().toString().toLowerCase().contains(keyWord.toString().toLowerCase()))
                            /*|| people.getName().toString().toLowerCase().startsWith(temp2)*/
            {
                if (people.getName().toString().toLowerCase().startsWith(keyWord.toString().toLowerCase()))
                {
                    fillteredUsers.add(temp_pos,people);
                    temp_pos++;
                }
                else{
                    fillteredUsers.add(people);
                }
            }



            else{

                String temp_str[]=keyWord.toString().split("\\s+");
                if(temp_str.length>1) {
                    if (people.getName().toString().toLowerCase().contains(temp_str[0].toLowerCase())
                            && people.getName().toString().toLowerCase().contains(temp_str[1].toLowerCase())) {

                        fillteredUsers.add(people);
                    }
                }

            }



        }


    }

    public Comparator<AllUsers> usersComparator = new Comparator<AllUsers>() {

        public int compare(AllUsers s1, AllUsers s2) {
            String cluster1 = s1.getName().toUpperCase();
            String cluster2 = s2.getName().toUpperCase();

            //ascending order
            return cluster1.compareTo(cluster2);

            //descending order
            // return cluster2.compareTo(cluster1);
        }};



}
