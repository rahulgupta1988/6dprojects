package expression.sixdexp.com.expressionapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import db.AllUsers;
import expression.sixdexp.com.expressionapp.adapter.SearchTag_Adapter;
import expression.sixdexp.com.expressionapp.adapter.TaggingAdapter;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.SayThanksManager;
import expression.sixdexp.com.expressionapp.manager.UsersManager;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.utility.Utils;

/**
 * Created by Praveen on 02-Jan-18.
 */

public class SayThanksForBook extends AppCompatActivity {

    Context mContext;
    RoundedImageView bookimg ;
    EditText thanks_msg;
    EditText search_edit;
    Button btndone;
    Button btncancel;
    String bookid="",bookimgurl="";
    public  List<AllUsers> taged_users=new ArrayList<>();
    ListView taged_user_list;
    LinearLayout auto_view_lay;
    View top_lay;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saythanksdialog);
        mContext=this;
        Intent intent=getIntent();
        if(intent!=null){
            bookid=intent.getStringExtra("bookid");
            bookimgurl=intent.getStringExtra("bookimgurl");
        }

        initViews();
        fillAutoTextList();
        setTeagedUserList();
    }



    public void initViews(){
        top_lay=(View)findViewById(R.id.top_lay);
        progress_show=(ProgressBar)findViewById(R.id.progress_show);
        auto_view_lay=(LinearLayout)findViewById(R.id.auto_view_lay);
        taged_user_list=(ListView)findViewById(R.id.taged_user_list);
          bookimg = (RoundedImageView)findViewById(R.id.bookimg);
          thanks_msg=(EditText)findViewById(R.id.thanks_msg);

          btndone=(Button)findViewById(R.id.btndone);
             btncancel=(Button)findViewById(R.id.btncancel);

        bookimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)(mContext.getSystemService(Context.INPUT_METHOD_SERVICE));
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                }

                onBackPressed();

            }
        });

        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(thanks_msg.getText().toString().length()==0){
                    Toast.makeText(mContext,"Please enter thanks message.",Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(taged_users.size()==0){
                    Toast.makeText(mContext,"Please select people to say thanks.",Toast.LENGTH_SHORT).show();
                    return;
                }

               new submitThanks().execute();
            }
        });



        URI book_uri = null;
        try {
            book_uri = new URI(bookimgurl.replace(" ", "%20"));
            if (book_uri.toString().length()!=0){
                Glide.with(mContext)
                        .load(book_uri.toString())
                        .placeholder(R.drawable.default_book_ic)
                        .error(R.drawable.default_book_ic)
                        .crossFade()
                        .thumbnail(0.1f)
                        .into(bookimg);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }



    ProgressDialog progressDialog = null;
    public class submitThanks extends AsyncTask<Void,Void,Void>{
            String responseString = "";
            String s = "Plase Wait...";
            String donername_str="";
            String thanks_msg_str="";

            @Override
            protected void onPreExecute() {
                // TODO Auto-generated method stub
                super.onPreExecute();
                for(int k=0;k<taged_users.size();k++){
                    if(k==0)
                        donername_str =taged_users.get(k).getUser_id();
                    else
                    donername_str =donername_str+","+taged_users.get(k).getUser_id();
                }

                 thanks_msg_str=thanks_msg.getText().toString();
                progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
                progressDialog.show();
            }


            @Override
            protected Void doInBackground(Void... params) {
                try {
                    responseString = new SayThanksManager(mContext).sayThaksCall(thanks_msg_str,donername_str,bookid);
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

                    View v=getCurrentFocus();
                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager)(mContext.getSystemService(Context.INPUT_METHOD_SERVICE));
                        imm.hideSoftInputFromWindow(v.getWindowToken(),0);


                    }

                    Toast.makeText(mContext,"Your thank you has been posted on the common wall.",Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else {

                }

            }


        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void fillAutoTextList(){
        search_edit=(EditText)findViewById(R.id.search_edit);
        search_edit.addTextChangedListener(new ListenToText());

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

    ProgressBar progress_show;

    public class FilterTask extends AsyncTask<String,Void,Void>{

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
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

            progress_show.setVisibility(View.GONE);
            if(search_edit.getText().toString().equals("")){
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
    public void settingMenu() {


        int OFFSET_X = 0;
        int OFFSET_Y = 0;

        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

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
                    for (int i = 0; i < taged_users.size(); i++) {
                        if (fillteredUsers.get(pos).getUser_id().equals(taged_users.get(i).getUser_id())) {
                            Toast.makeText(mContext, "Already added.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    if(taged_users.size()<25) {
                        taged_users.add(fillteredUsers.get(pos));
                        taggingAdapter.notifyDataSetChanged();
                        Utils.getListViewSize(taged_user_list);
                        changeSortPopUp.dismiss();
                        search_edit.setText("");
                    }
                    else{
                        changeSortPopUp.dismiss();
                        search_edit.setText("");
                        Toast.makeText(mContext,"Maximum of 25 employees can be tagged.",Toast.LENGTH_SHORT).show();
                    }


                }
            });

            searc_dialog_lis.setAdapter(searchAdapter);



            rc = new Rect();
            top_lay.getWindowVisibleDisplayFrame(rc);
            int[] xy = new int[2];
            top_lay.getLocationInWindow(xy);
            rc.offset(xy[0], xy[1]);
            // Creating the PopupWindow



            //changeSortPopUp.setAnimationStyle(R.style.animationName);
            changeSortPopUp.setContentView(layout);
            changeSortPopUp.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            changeSortPopUp.setHeight(500);
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


    List<AllUsers> fillteredUsers=new ArrayList<>();
    public void getFilterList(String keyWord){
        String userid = new LoginManager(mContext).getUserInfo().get(0).getUserid();
        fillteredUsers.clear();
        int temp_pos=0;
        List<AllUsers> allUserses =new UsersManager(mContext).getAllUserList();
        Collections.sort(allUserses,usersComparator);
        for (AllUsers people : allUserses) {
                   /* String temp1=("Mr "+constraint.toString()).toLowerCase();
                    String temp2=("Ms "+constraint.toString()).toLowerCase();*/


            if(people.getUser_id().equals(userid))
                continue;


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

