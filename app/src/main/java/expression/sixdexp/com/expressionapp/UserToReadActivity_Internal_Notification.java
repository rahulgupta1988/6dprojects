package expression.sixdexp.com.expressionapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import expression.sixdexp.com.expressionapp.adapter.ToReadUserAdapter;
import expression.sixdexp.com.expressionapp.manager.ToReadBooksManager;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import model.Book;

/**
 * Created by Praveen on 15-Jan-18.
 */

public class UserToReadActivity_Internal_Notification extends AppCompatActivity {

    Context mContext;
    ImageView toread_back;
    RecyclerView wish_list;
    TextView toread_user;
    String user_name="",userid="";
    public List<Book> wishedBooks=new ArrayList<>();
    String isread="0";
    LinearLayout list_lay,text_lay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usertoreadactivity);
        mContext = this;
        Intent intent=getIntent();
        if(intent!=null){
            userid=intent.getStringExtra("userid");
        }
        initViews();
        new getBooks().execute();
    }


    public void initViews() {
        list_lay=(LinearLayout)findViewById(R.id.list_lay);
        text_lay=(LinearLayout)findViewById(R.id.text_lay);
        toread_back = (ImageView) findViewById(R.id.toread_back);
        toread_user=(TextView)findViewById(R.id.toread_user);
        wish_list = (RecyclerView) findViewById(R.id.wish_list);
        toread_user.setText("Wishlist");
        toread_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setWishList();
    }


    ToReadUserAdapter toReadUserAdapter=null;
    private void setWishList() {
        toReadUserAdapter=new ToReadUserAdapter(mContext,wishedBooks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        wish_list.setLayoutManager(layoutManager);
        wish_list.setItemAnimator(new DefaultItemAnimator());
        wish_list.setAdapter(toReadUserAdapter);
    }


    ProgressDialog progressDialog = null;
    class getBooks extends AsyncTask<Void,Void,Void> {

        ToReadBooksManager toReadBooksManager;
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
            toReadBooksManager=new ToReadBooksManager(mContext,userid);
            responseString =toReadBooksManager.getBooks(isread);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (responseString.equals("100")) {
                wishedBooks.clear();




                wishedBooks.addAll(toReadBooksManager.wishedBooks);
                toReadUserAdapter.notifyDataSetChanged();

                if(wishedBooks.size()>0){
                    text_lay.setVisibility(View.GONE);
                    list_lay.setVisibility(View.VISIBLE);
                }

                else{
                    list_lay.setVisibility(View.GONE);
                    text_lay.setVisibility(View.VISIBLE);

                }




            } else {

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
