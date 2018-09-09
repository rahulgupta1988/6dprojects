package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import db.AllUsers;
import db.UserLoginInfo;
import expression.sixdexp.com.expressionapp.adapter.TagCustomerAdapter;
import expression.sixdexp.com.expressionapp.adapter.TaggingAdapter;
import expression.sixdexp.com.expressionapp.manager.LoginManager;
import expression.sixdexp.com.expressionapp.manager.UsersManager;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.CustomAutoCompleteTextView;
import expression.sixdexp.com.expressionapp.utility.Utils;

/**
 * Created by Praveen on 06-Sep-17.
 */

public class TagActivity_new extends Activity {

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tag_activity_lay);
        mContext=this;
        initViews();

    }


    public void initViews(){
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
                backBydone=true;
                if(Constant.tag_txt!=null){
                    Constant.tag_txt.setText(""+taged_users.size());
                    if(taged_users.size()>0)
                        Constant.tag_txt.setVisibility(View.VISIBLE);
                    else Constant.tag_txt.setVisibility(View.GONE);
                }
                onBackPressed();
            }
        });
        List<UserLoginInfo> userDatas = new LoginManager(mContext).getUserInfo();
        user_email=userDatas.get(0).getEmailId();
        //  auto_view=(AutoCompleteTextView)findViewById(R.id.auto_view);
        auto_view=createAutoTextSearch();
        auto_view.addTextChangedListener(new ListenToText());
        auto_view_lay.addView(auto_view);


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




    // ArrayAdapter adapter1=null;

    TagCustomerAdapter tagCustomerAdapter=null;
    public void fillAutoTextList(){
        if (tagCustomerAdapter != null) tagCustomerAdapter = null;
        List<AllUsers> allUserses =new UsersManager(mContext).getAllUserList();//Constant.allUserses;
        for (int i = 0; i < allUserses.size(); i++)
        {
            if(!allUserses.get(i).getEmail().equals(user_email)) {
                autolist.add(allUserses.get(i).getName());
            }

        }

        /*adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, autolist);*/

        Collections.sort(allUserses,usersComparator);
        Log.i("users 2345",""+allUserses.toString());

        tagCustomerAdapter=new TagCustomerAdapter(mContext,allUserses);
        auto_view.setAdapter(tagCustomerAdapter);

        auto_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for(int i=0;i<taged_users.size();i++){
                    if(tagCustomerAdapter.getItem(position).getUser_id().equals(taged_users.get(i).getUser_id())){
                        Toast.makeText(mContext,"Already added.",Toast.LENGTH_SHORT).show();
                        auto_view.setText("");
                        return;
                    }
                }

                taged_users.add(tagCustomerAdapter.getItem(position));
                taggingAdapter.notifyDataSetChanged();
                Utils.getListViewSize(taged_user_list);
                auto_view.setText("");
            }
        });

        tagCustomerAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                Log.i("00000000",""+"onChanged");
            }

            @Override
            public void onInvalidated() {
                super.onInvalidated();
                Log.i("11111111",""+"onInvalidated");
            }
        });
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
            if (s.toString().length() == 3) {

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


