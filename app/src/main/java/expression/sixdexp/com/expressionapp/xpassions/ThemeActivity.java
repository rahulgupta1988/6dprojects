package expression.sixdexp.com.expressionapp.xpassions;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.adapter.GroupListAdapter;
import expression.sixdexp.com.expressionapp.manager.CreateGroupManager;
import expression.sixdexp.com.expressionapp.utility.ProgressLoaderUtilities;
import expression.sixdexp.com.expressionapp.xpassions.gpadapter.ViewPagerAdapter;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GPTheamManager;
import expression.sixdexp.com.expressionapp.xpassions.gpmanager.GropuActionManager;

/**
 * Created by Praveen on 09-Jan-18.
 */

public class ThemeActivity extends AppCompatActivity  implements ViewPager.OnPageChangeListener{
    Context mContext;
    ViewPager intro_images;
    private ImageView[] dots;
    private LinearLayout pager_indicator;
    String themeid;

    private int[] mImageResources = {
            R.drawable.theme1,
            R.drawable.theme2,
            R.drawable.theme3,
            R.drawable.theme4,
            R.drawable.theme5,
            //R.drawable.theme6
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themeactivty);
        mContext=this;
        initView();
    }

    public void initView(){
        pager_indicator = (LinearLayout)findViewById(R.id.viewPagerCountDots);
        setthemeList();
    }


    public void updateTheam(String themeid){
        this.themeid=themeid;
        new TheamUpdateTask().execute();
    }

    ViewPagerAdapter mAdapter=null;
    public void setthemeList() {
        intro_images = (ViewPager) findViewById(R.id.theme_list);
        mAdapter = new ViewPagerAdapter(mContext,mImageResources);
        intro_images.setAdapter(mAdapter);
        intro_images.setCurrentItem(0);
        intro_images.setOnPageChangeListener(this);
        setUiPageViewController();


    }

    private int dotsCount;
    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.themedot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(8, 0, 8, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selected_themedot));
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //Toast.makeText(mContext,""+position,Toast.LENGTH_SHORT).show();
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.themedot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selected_themedot));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    ProgressDialog progressDialog = null;

    class TheamUpdateTask extends AsyncTask<Void,Void,Void> {
        GPTheamManager gpTheamManager;
        String responseString = "";
        String s = "Plase Wait...";
        String gid;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = ProgressLoaderUtilities.getProgressDialog(mContext);
            progressDialog.show();


            if(GroupListAdapter.groupObject==null){
                return;
            }

            gid=GroupListAdapter.groupObject.getCoi_gid();


        }

        @Override
        protected Void doInBackground(Void... params) {
            gpTheamManager=new GPTheamManager(mContext);
            responseString =gpTheamManager.updateTheam(gid,themeid);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (responseString.equals("100")) {
                if(GropuActionManager.gpActionModel!=null){
                    GroupListAdapter.groupObject.setThemeid(themeid);
                    GropuActionManager.gpActionModel.setThemeid(themeid);
                }
                Intent intent = new Intent();
                setResult(701, intent);
                finish();//finishing activity

                Toast.makeText(mContext,"Group Theme has been Updated successfully.",Toast.LENGTH_SHORT).show();
                //onBackPressed();
            } else {

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
