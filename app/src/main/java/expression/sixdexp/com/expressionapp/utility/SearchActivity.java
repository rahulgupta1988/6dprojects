package expression.sixdexp.com.expressionapp.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import db.AllUsers;
import db.Award;
import expression.sixdexp.com.expressionapp.ProfileDetails_Search;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.adapter.SearchAdapter;
import expression.sixdexp.com.expressionapp.manager.AllAutoSearchmanager;
import expression.sixdexp.com.expressionapp.manager.AwardManager;
import expression.sixdexp.com.expressionapp.manager.SearchManager;
import expression.sixdexp.com.expressionapp.manager.UserInfoManager;
import expression.sixdexp.com.expressionapp.manager.UsersManager;

/**
 * Created by VINAY on 12/27/16.
 */
public class SearchActivity extends Activity implements OnClickListener
{
    Context mContext;
   // View view;
    RecyclerView searchlist;
    CustomAutoCompleteTextView searchpostedit;
    List<AllUsers> searchItems = new ArrayList<AllUsers>();

    List<AllUsers> allUserses;
    List<String> usersName;
    List<String> departments;
    List<String> location;
    List<String> award;
    String key_type = "all";
    String key_word = "";
    ImageView cancelsearch;

    String userIDstr = "";
    PopupWindow changeSortPopUp;
    SearchAdapter searchAdapter;
    ProgressDialog progressDialog;
    LinearLayout searchlay;
    ImageView search_nav;
    ListView recent_searchlist;
    ImageView recentsearchclear;
    LinearLayout recentsearchclearlay;
    LinearLayout hasprofilelay;
    TextView searcheduser;

    CoordinatorLayout searchlayparent;
    String searchUserID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchfragmentviewnew);
        mContext=this;
        searchlist = (RecyclerView)findViewById(R.id.searchlist);

        cancelsearch = (ImageView) findViewById(R.id.cancelsearch);
        cancelsearch.setOnClickListener(this);
        searchlayparent = (CoordinatorLayout)findViewById(R.id.searchlayparent);
        recent_searchlist = (ListView)findViewById(R.id.recent_searchlist);
        recentsearchclear = (ImageView)findViewById(R.id.recentsearchclear);
        recentsearchclearlay = (LinearLayout)findViewById(R.id.recentsearchclearlay);
        hasprofilelay=(LinearLayout)findViewById(R.id.hasprofilelay);
        hasprofilelay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    searchUserID=new UsersManager(mContext).getUserListByName(key_word).get(0).getUser_id();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(searchUserID!=null && !searchUserID.equalsIgnoreCase("")) {
                    new GetUserProfile().execute();
                }
                else{
                    showsnack("User Profile Not Available");
                }
            }
        });
        searcheduser=(TextView)findViewById(R.id.searcheduser);
        Constant.searchResultList.clear();

        setResentSearchs();
        recentsearchclear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefrenceManager.clearRecentSearch(mContext);
                recentsearchclearlay.setVisibility(View.GONE);
                recent_searchlist.setVisibility(View.GONE);
                searchlist.setVisibility(View.VISIBLE);
            }
        });

        initAllKeyDataInList();
        searchlay = (LinearLayout)findViewById(R.id.searchlay);
        searchpostedit = createAutoTextSearch();
        searchpostedit.addTextChangedListener(new ListenToText());
        searchlay.addView(searchpostedit);
        initSearchNav();
    }

    public void intiSearchAdapter() {
        recentsearchclearlay.setVisibility(View.GONE);
        recent_searchlist.setVisibility(View.GONE);
        searchlist.setVisibility(View.VISIBLE);

    }

  /*  @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = (Context) activity;
    }*/


    ArrayAdapter adapter1;


    public CustomAutoCompleteTextView createAutoTextSearch() {
        CustomAutoCompleteTextView searchpostedit1 = new CustomAutoCompleteTextView(mContext);
        searchpostedit1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        searchpostedit1.setThreshold(3);
        searchpostedit1.setBackgroundColor(Color.parseColor("#00000000"));
        searchpostedit1.setHint("Search for all");
        searchpostedit1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0);
        searchpostedit1.setPadding(15, 0, 0, 0);
        return searchpostedit1;
    }

    public void fillAutoTextList(List<String> autoTolist) {
        if (adapter1 != null) {
            adapter1.clear();
            adapter1 = null;
        }

        adapter1 = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_1, autoTolist);
        searchpostedit.setAdapter(adapter1);

        searchpostedit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("setOnItemClickListener", "setOnItemClickListener");
                //Toast.makeText(mContext, "" + adapter1.getItem(position).toString(), Toast.LENGTH_SHORT).show();

                intiSearchAdapter();
                setUserID(adapter1.getItem(position).toString());
                SharedPrefrenceManager.putRecentSearch(mContext, adapter1.getItem(position).toString());
                searchpostedit.setText("");
            }
        });


    }

    public void setUserID(String search_str) {
        key_word = search_str;
      /*  InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);*/

        if(getCurrentFocus()!=null)
        {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        new SearchTask().execute();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.cancelsearch:
                onBackPressed();
                break;
        }
    }


    public class SearchTask extends AsyncTask<String, Void, Void> {
        String responseString = "";

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
                responseString = new SearchManager(mContext).callForSearch(key_word, key_type);
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

            List<AllUsers> allUserses=new UsersManager(mContext).getUserListByName(key_word);
            if(allUserses!=null && allUserses.size()>0){
                hasprofilelay.setVisibility(View.VISIBLE);
                String  text = "<font color=#ffffff><b>" + key_word + " </b></font> <font color=#ffffff><b>   has a profile </b></font>";
                searcheduser.setText(Html.fromHtml(text));
            }

            else{
                hasprofilelay.setVisibility(View.GONE);
            }

            if (changeSortPopUp != null) {
                changeSortPopUp.dismiss();
                changeSortPopUp = null;
            }
            if (responseString.equals("100")) {
                searchAdapter.notifyDataSetChanged();
                if(Constant.searchResultList.size()==0)
                    showsnack("No Data Available !");
            } else {

                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
            }
        }
    }


    public void initAllKeyDataInList() {

        try {

            UsersManager usersManager = new UsersManager(mContext);
            // Employees
            usersName = new ArrayList<String>();
            allUserses =new UsersManager(mContext).getAllUserList();//Constant.allUserses;
            for (int i = 0; i < allUserses.size(); i++) {
                usersName.add(allUserses.get(i).getName());
            }

            // Department
            departments = new ArrayList<String>();
            departments = usersManager.getdepartments();

            // Location
            location = new ArrayList<String>();
            location = usersManager.getlocation();

            //Award
            setAward();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void setAward() {
        award = new ArrayList<String>();
        List<Award> awards = new AwardManager(mContext).getAwardList();
        if (awards != null && awards.size() > 0) {
            award = new ArrayList<String>();
            for (int i = 0; i < awards.size(); i++) {

                award.add(awards.get(i).getAwardname());
            }

        }

    }

    public class AutoTextSearch extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            // progressDialog.show();
            Log.i("AutoTextSearch", "AutoTextSearch");
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                //responseString = new SearchManager(mContext).callForSearch(userIDstr);
                responseString = new AllAutoSearchmanager(mContext).callAutoText(key_word, key_type);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            // progressDialog.dismiss();
            if (responseString.equals("100")) {

                fillAutoTextList(AllAutoSearchmanager.autosearchresultList);
                searchpostedit.showDropDown();

            } else {

                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
            }

        }


    }


    void setResentSearchs() {
        Set<String> recent_searchs = SharedPrefrenceManager.getRecentSearch(mContext);
        if (recent_searchs != null && recent_searchs.size() > 0) {
            recentsearchclearlay.setVisibility(View.VISIBLE);
            Log.i("recent_searchs", recent_searchs.toString());
            List<String> selexpressorstr = new ArrayList<String>();
            selexpressorstr.addAll(recent_searchs);
            Collections.reverse(selexpressorstr);
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, selexpressorstr);
            recent_searchlist.setAdapter(adapter);

            recent_searchlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    intiSearchAdapter();
                    setUserID(adapter.getItem(position).toString());
                }
            });

        }
    }


    void initSearchNav() {
        search_nav = (ImageView)findViewById(R.id.search_nav);
        searchAdapter = new SearchAdapter(mContext);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        searchlist.setLayoutManager(layoutManager);
        searchlist.setItemAnimator(new DefaultItemAnimator());
        searchlist.setAdapter(searchAdapter);
        search_nav.setOnClickListener(new ClickListener());
    }

    class ClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {

      /*      InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);*/

            if(getCurrentFocus()!=null)
            {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }

            new AnchorePopupWindow(mContext, searchlay, new AnchorePopupWindow.KeyCheckedListener() {
                @Override
                public void checkedkey(String keyName) {
                    if (keyName.equals("all")) {
                        searchpostedit.setHint("Search for all");
                        key_type = "all";
                    } else if (keyName.equals("employee")) {
                        searchpostedit.setHint("Search for employee");
                        key_type = "usr";
                        fillAutoTextList(usersName);
                    } else if (keyName.equals("department")) {
                        searchpostedit.setHint("Search for department");
                        key_type = "dep";
                        fillAutoTextList(departments);
                    } else if (keyName.equals("location")) {
                        searchpostedit.setHint("Search for location");
                        key_type = "loc";
                        fillAutoTextList(location);
                    } else if (keyName.equals("award")) {
                        searchpostedit.setHint("Search for award");
                        key_type = "awr";
                        fillAutoTextList(award);
                    }
                }
            }).settingMenu();
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
            if (key_type.equals("all")) {
                if (s.toString().length() >= 3) {
                    key_word = s.toString();
                    new AutoTextSearch().execute();
                }
            }
        }
    }

    public class GetUserProfile extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();
            Log.i("AutoTextSearch", "AutoTextSearch");
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                //responseString = new SearchManager(mContext).callForSearch(userIDstr);
                responseString = new UserInfoManager(mContext).userInfo(searchUserID);
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
                Intent proifleIntent = new Intent(mContext, ProfileDetails_Search.class);
                startActivity(proifleIntent);

            } else {

                showsnack("User Profile Not Available");
            }

        }
    }



    public void showsnack(String msg){

        Snackbar snackbar = Snackbar
                .make(searchlayparent, msg, Snackbar.LENGTH_LONG);

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTypeface(Typeface.create("sans-serif-condensed", Typeface.NORMAL));
        textView.setTextColor(Color.parseColor("#ffffff"));

        snackbar.show();

    }

    @Override
    public void onBackPressed() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        super.onBackPressed();
    }

}
