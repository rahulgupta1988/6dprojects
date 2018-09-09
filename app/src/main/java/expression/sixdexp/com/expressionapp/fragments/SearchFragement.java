package expression.sixdexp.com.expressionapp.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import db.AllUsers;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.adapter.SearchAdapter;
import expression.sixdexp.com.expressionapp.manager.UsersManager;

/**
 * Created by Praveen on 6/30/2016.
 */
public class SearchFragement extends Fragment {

    Context mContext;
    View view;
    RecyclerView searchlist;
    EditText searchpostedit;
    List<AllUsers> searchItems = new ArrayList<AllUsers>();
    List<AllUsers> allUserses;
    String userIDstr = "";
    PopupWindow changeSortPopUp;
    SearchAdapter searchAdapter;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.searchfragementview, container, false);
        searchlist = (RecyclerView) view.findViewById(R.id.searchlist);
        searchAdapter = new SearchAdapter(mContext);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        searchlist.setLayoutManager(layoutManager);
        searchlist.setItemAnimator(new DefaultItemAnimator());
        searchlist.setAdapter(searchAdapter);

        allUserses =new UsersManager(mContext).getAllUserList();//Constant.allUserses;
        searchpostedit = (EditText) view.findViewById(R.id.searchpostedit);

        initSearch();
        return view;
    }


    public void initSearch() {
        searchpostedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("beforeTextChanged", "beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("onTextChanged", "onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("afterTextChanged", "afterTextChanged");

                if (s.toString().length() >= 3) {
                    searchItems.clear();
                    for (AllUsers user : allUserses) {

                        //if (user.getName().toLowerCase().contains(s.toString().toLowerCase())) {
                        if (Pattern.compile(Pattern.quote(s.toString()), Pattern.CASE_INSENSITIVE).matcher(user.getName()).find()) {
                            //Log.i("name marh", "" + user.getName());
                            searchItems.add(user);
                        }
                    }
                    Log.i("searchItems", "" + searchItems.toString());

                    if (changeSortPopUp != null) {
                        if (!changeSortPopUp.isShowing()) {
                            if (searchItems.size() > 0) {
                                showSearchList();
                            } else
                                changeSortPopUp.dismiss();
                        } else {
                            searchItemAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (searchItems.size() > 0)
                            showSearchList();
                    }
                } else {
                    if (changeSortPopUp != null)
                        changeSortPopUp.dismiss();
                }
            }
        });
    }

    SearchItemAdapter searchItemAdapter;

    public void showSearchList() {

        changeSortPopUp = new PopupWindow(mContext);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.searchlistview, null);
        final ListView searhitemlist = (ListView) layout.findViewById(R.id.searhitemlist);
        searchItemAdapter = new SearchItemAdapter();
        searhitemlist.setAdapter(searchItemAdapter);

        Rect rc = new Rect();
        searchpostedit.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        searchpostedit.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        // Creating the PopupWindow

        changeSortPopUp.setAnimationStyle(R.style.animationName);
        changeSortPopUp.setContentView(layout);
        changeSortPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeSortPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeSortPopUp.setFocusable(false);

        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 15;
        int OFFSET_Y = 40;

        // Clear the default translucent background
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mContext = (Context) activity;
    }


    public class SearchItemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return searchItems.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            HolderView holder;
            if (convertView == null) {
                holder = new HolderView();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchitem, parent, false);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.searchitemlay = (LinearLayout) convertView.findViewById(R.id.searchitemlay);
                convertView.setTag(holder);
            } else {
                holder = (HolderView) convertView.getTag();
            }

            final int pos = position;

            String name = searchItems.get(pos).getName();
            holder.name.setText(name);

            holder.searchitemlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    userIDstr = searchItems.get(pos).getUser_id();
                    searchpostedit.setText("");
                    searchpostedit.append(searchItems.get(pos).getName());
                    new SearchTask().execute();
                    //Log.i("UserID", "" + userIDstr);
                    //Log.i("searchpostedit",""+searchpostedit.getText());
                }
            });

            return convertView;
        }

        public class HolderView {

            TextView name;
            LinearLayout searchitemlay;


        }
    }


    public class SearchTask extends AsyncTask<String, Void, Void> {

        String responseString = "";
        String s = "Please wait...";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            SpannableString ss2 = new SpannableString(s);
            ss2.setSpan(new RelativeSizeSpan(1.3f), 0, ss2.length(), 0);
            ss2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, ss2.length(), 0);
            progressDialog = new ProgressDialog(mContext,
                    android.R.style.Theme_DeviceDefault_Light_Dialog);
            Window window = progressDialog.getWindow();
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(
                    new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setMessage(ss2);
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                //responseString = new SearchManager(mContext).callForSearch(userIDstr);
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
            if (changeSortPopUp != null) {
                changeSortPopUp.dismiss();
                changeSortPopUp = null;
            }
            if (responseString.equals("100")) {

                searchAdapter.notifyDataSetChanged();

            } else {

                Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }

        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("onDestroy", "onDestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("onPause", "onPause");
        if (changeSortPopUp != null)
            changeSortPopUp.dismiss();
    }
}
