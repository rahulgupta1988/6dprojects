package expression.sixdexp.com.expressionapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import db.AllUsers;
import db.Award;
import db.RecognitionGiven;
import expression.sixdexp.com.expressionapp.adapter.RecognitionGivenAndReceivedListAdapter;
import expression.sixdexp.com.expressionapp.manager.AwardManager;
import expression.sixdexp.com.expressionapp.manager.RecognitionRecivedAndGiven;
import expression.sixdexp.com.expressionapp.manager.RecognitionRecivedAndGivenListManager;
import expression.sixdexp.com.expressionapp.manager.UsersManager;
import expression.sixdexp.com.expressionapp.utility.CustomAutoCompleteTextView;

/**
 * Created by Praveen on 7/21/2016.
 */
public class RecognitionGiveActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Context mContext;
    ProgressDialog progressDialog;
    String arwadID = "0", userIDstr = "0", datefromstr = "", datetostr = "";
    List<Award> awards;
    RecyclerView recognisitionlist;
    Spinner spinaward;
    TextView datefrom, dateto;
    CustomAutoCompleteTextView searchpostedit;
    ImageView filterit, cancelshare;
    LinearLayout searchlay;
    TextView title;
    CoordinatorLayout searchlayparent;

    TextView thankcount, drivetoleadcount, cheertopeercount, ivaluecount, applausecount, totalcount;
    boolean recognitionGiven = true;

    LinearLayout thanklay, drivetoleadlay, cheertopeerlay, ivaluelay, applauselay;

    TextView givenreceivedtiitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recognitiongivenview);
        mContext = this;
        Intent intent = getIntent();
        recognitionGiven = intent.getBooleanExtra("recognitionGiven", true);

        init();
    }


    public void init() {
        searchlayparent=(CoordinatorLayout)findViewById(R.id.searchlayparent);
        title = (TextView) findViewById(R.id.title);
        givenreceivedtiitle = (TextView) findViewById(R.id.givenreceivedtiitle);

        if (recognitionGiven) {
            title.setText("Recognition Given");
            givenreceivedtiitle.setText("Recognition Given To");
        } else {
            title.setText("Recognition Received");
            givenreceivedtiitle.setText("Recognition Received");

        }
        thankcount = (TextView) findViewById(R.id.thankcount);
        drivetoleadcount = (TextView) findViewById(R.id.drivetoleadcount);
        cheertopeercount = (TextView) findViewById(R.id.cheertopeercount);
        ivaluecount = (TextView) findViewById(R.id.ivaluecount);
        applausecount = (TextView) findViewById(R.id.applausecount);
        totalcount = (TextView) findViewById(R.id.totalcount);

        searchlay = (LinearLayout) findViewById(R.id.searchlay);
        cancelshare = (ImageView) findViewById(R.id.cancelshare);
        cancelshare.setOnClickListener(this);
        filterit = (ImageView) findViewById(R.id.filterit);
        filterit.setOnClickListener(this);
        datefrom = (TextView) findViewById(R.id.datefrom);
        dateto = (TextView) findViewById(R.id.dateto);
        datefrom.setOnClickListener(this);
        dateto.setOnClickListener(this);

        recognisitionlist = (RecyclerView) findViewById(R.id.recognisitionlist);

        thanklay = (LinearLayout) findViewById(R.id.thanklay);
        drivetoleadlay = (LinearLayout) findViewById(R.id.drivetoleadlay);
        cheertopeerlay = (LinearLayout) findViewById(R.id.cheertopeerlay);
        ivaluelay = (LinearLayout) findViewById(R.id.ivaluelay);
        applauselay = (LinearLayout) findViewById(R.id.applauselay);

        setRecognitionList();
        setAward();
        fillAutoTextList();
        setAwardCount();
    }


    public void setAwardCount() {
        applauselay.setAlpha((float) 0.3);
        cheertopeerlay.setAlpha((float) 0.3);
        drivetoleadlay.setAlpha((float) 0.3);
        thanklay.setAlpha((float) 0.3);
        ivaluelay.setAlpha((float) 0.3);

        applausecount.setText("");
        cheertopeercount.setText("");
        drivetoleadcount.setText("");
        thankcount.setText("");
        ivaluecount.setText("");


        List<RecognitionGiven> recognitionGivens = new RecognitionRecivedAndGiven(mContext, recognitionGiven).getRecognitionGivenList();
        if (recognitionGivens != null && recognitionGivens.size() > 0) {

            for (RecognitionGiven recognitionGiven : recognitionGivens) {

                String awardid = recognitionGiven.getAwardid();
                String awardName = recognitionGiven.getAwardName();
                String count = recognitionGiven.getCount();
                String total = recognitionGiven.getTotal();

                if (!awardid.equalsIgnoreCase("null") && awardid.equals("3")) {
                    applauselay.setAlpha(1);
                    applausecount.setText("" + count);
                } else if (!awardid.equalsIgnoreCase("null") && awardid.equals("5")) {
                    cheertopeerlay.setAlpha(1);
                    cheertopeercount.setText("" + count);
                } else if (!awardid.equalsIgnoreCase("null") && awardid.equals("4")) {
                    drivetoleadlay.setAlpha(1);
                    drivetoleadcount.setText("" + count);
                } else if (!awardid.equalsIgnoreCase("null") && awardid.equals("7")) {
                    Log.i("id4543", "" + awardid + " " + count);
                    ivaluelay.setAlpha(1);
                    ivaluecount.setText("" + count);

                } else if (!awardid.equalsIgnoreCase("null") && awardid.equals("6")) {
                    thanklay.setAlpha(1);
                    thankcount.setText("" + count);
                }

                if (!total.equalsIgnoreCase("null"))
                    totalcount.setText("" + total);
            }

        }


    }

    public CustomAutoCompleteTextView createAutoTextSearch() {
        CustomAutoCompleteTextView searchpostedit1 = new CustomAutoCompleteTextView(mContext);
        searchpostedit1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        searchpostedit1.setThreshold(3);
        searchpostedit1.setTextSize(12);
        searchpostedit1.setHintTextColor(Color.parseColor("#8e8d8d"));
        searchpostedit1.setTextColor(Color.parseColor("#8e8d8d"));
        searchpostedit1.setBackgroundColor(Color.parseColor("#00000000"));
        searchpostedit1.setHint("Search for people");
        searchpostedit1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0);
        searchpostedit1.setPadding(5, 0, 0, 0);
        return searchpostedit1;

        //8e8d8d
    }

    public void setRecognitionList() {
        recognisitionlist.invalidate();
        RecognitionGivenAndReceivedListAdapter recognitionGivenAndReceivedListAdapter = new RecognitionGivenAndReceivedListAdapter(mContext, recognitionGiven);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recognisitionlist.setLayoutManager(layoutManager);
        recognisitionlist.setItemAnimator(new DefaultItemAnimator());
        recognisitionlist.setAdapter(recognitionGivenAndReceivedListAdapter);
    }

    public void setAward() {

        spinaward = (Spinner) findViewById(R.id.spinaward);
        awards = new AwardManager(mContext).getAwardList();
        List<String> awardName = null;


        if (awards != null && awards.size() > 0) {
            awardName = new ArrayList<String>();
            awardName.add("All");
            for (int i = 0; i < awards.size(); i++) {

                awardName.add(awards.get(i).getAwardname());
            }

            ArrayAdapter<String> awardApdapterAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, awardName);
            awardApdapterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinaward.setAdapter(awardApdapterAdapter);
            spinaward.setOnItemSelectedListener(this);
        }

    }

    ArrayList<String> autoTolist = new ArrayList<String>();

    public void fillAutoTextList() {
        searchpostedit = createAutoTextSearch();
        List<AllUsers> allUserses =new UsersManager(mContext).getAllUserList();//Constant.allUserses;
        for (int i = 0; i < allUserses.size(); i++) {
            autoTolist.add(allUserses.get(i).getName());
        }

        final ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, autoTolist);
        searchpostedit.setAdapter(adapter);

        searchpostedit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(mContext, "Select " + adapter.getItem(position).toString(), Toast.LENGTH_SHORT).show();
                setUserID(adapter.getItem(position).toString());

            }
        });

        searchlay.addView(searchpostedit);
    }

    public void setUserID(String userName) {
        List<AllUsers> allUserses = new UsersManager(mContext)
                .getUserListByName(userName);
        userIDstr = allUserses.get(0).getUser_id();

    }

    public void initdate(final TextView datesel, final String toORFrom) {

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                datesel.setText(sdf.format(myCalendar.getTime()));
                if (toORFrom.equals("to")) datetostr = sdf.format(myCalendar.getTime());
                if (toORFrom.equals("from")) datefromstr = sdf.format(myCalendar.getTime());


            }

        };

        DatePickerDialog mDatePicker = new DatePickerDialog(mContext, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        mDatePicker.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0)
            arwadID = "0";
        else
            arwadID = awards.get(position - 1).getAwardid();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.datefrom:
                initdate(datefrom, "from");
                break;

            case R.id.dateto:
                initdate(dateto, "to");
                break;

            case R.id.filterit:

                //arwadID,datefromstr="",datetostr="";userIDstr

                if (arwadID != null && !arwadID.equalsIgnoreCase("")) {

                   /* if (userIDstr != null && !userIDstr.equalsIgnoreCase("")) {*/

                        if (datefromstr != null && !datefromstr.equalsIgnoreCase("")) {

                            if (datetostr != null && !datetostr.equalsIgnoreCase("")) {
                                View view = getCurrentFocus();
                                if (view != null) {
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                                }
                                new RecognitionGivenTask().execute();

                            } else {
                                //Toast.makeText(mContext, "Please select end date", Toast.LENGTH_LONG).show();
                                showsnack("Please select end date");
                            }
                        } else {
                            //Toast.makeText(mContext, "Please select start date", Toast.LENGTH_LONG).show();
                            showsnack("Please select start date");
                        }
                    /* }else {
                        Toast.makeText(mContext, "Please select a user.", Toast.LENGTH_LONG).show();
                    }*/
                } else {
                    //Toast.makeText(mContext, "Please select award.", Toast.LENGTH_LONG).show();
                    showsnack("Please select award");
                }


                break;

            case R.id.cancelshare:
                onBackPressed();
                break;


        }
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


    public class RecognitionGivenTask extends AsyncTask<String, Void, Void> {


        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = initProgressDialog();
            progressDialog.show();
            //progressDialog= ProgressLoaderUtilities.getProgressDialog(mContext);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {

                responseString = new RecognitionRecivedAndGiven(mContext, recognitionGiven).callRecognitionRecivedAndGiven(userIDstr, arwadID, datefromstr, datetostr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //progressDialog.dismiss();
            if (responseString.equals("100")) {
                searchpostedit.setText("");
                datefrom.setText("");
                dateto.setText("");
                setAwardCount();
                new RecognitionGivenListTask().execute();
            } else {
                progressDialog.dismiss();
                //Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                showsnack(responseString);
            }

        }

    }


    public class RecognitionGivenListTask extends AsyncTask<String, Void, Void> {


        String responseString = "";

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            //progressDialog= ProgressLoaderUtilities.getProgressDialog(mContext);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            try {
                responseString = new RecognitionRecivedAndGivenListManager(mContext, recognitionGiven).callRecognitionRecivedAndGivenListManager(userIDstr, arwadID, datefromstr, datetostr);
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
                spinaward.setSelection(0);
                userIDstr = "";
                datefromstr = "";
                datetostr = "";
                setRecognitionList();

            } else {
                progressDialog.dismiss();
                //Toast.makeText(mContext, responseString, Toast.LENGTH_LONG).show();
                showsnack(responseString);
            }

        }


    }

    public ProgressDialog initProgressDialog() {
        String s = "Please wait...";
        SpannableString ss2 = new SpannableString(s);
        ss2.setSpan(new RelativeSizeSpan(1.3f), 0, ss2.length(), 0);
        ss2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), 0, ss2.length(), 0);
        ProgressDialog progressDialog = new ProgressDialog(mContext,
                android.R.style.Theme_DeviceDefault_Light_Dialog);
        Window window = progressDialog.getWindow();
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setMessage(ss2);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        return progressDialog;
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
}
