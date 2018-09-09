package expression.sixdexp.com.expressionapp.xpassions.gpadapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.adapter.GroupListAdapter;
import expression.sixdexp.com.expressionapp.xpassions.ThemeActivity;

/**
 * Created by Praveen on 09-Jan-18.
 */

public class ViewPagerAdapter extends PagerAdapter {

    public Context mContext;
    private int[] mResources;

    public ViewPagerAdapter(Context mContext, int[] mResources) {
        this.mContext = mContext;
        this.mResources = mResources;
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.themeadapterview, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
        Button btn_apply=(Button)itemView.findViewById(R.id.btn_apply);
        imageView.setImageResource(mResources[position]);

        if(position==0)
            btn_apply.setBackgroundColor(Color.parseColor("#E74C3C"));

        if(position==1)
            btn_apply.setBackgroundColor(Color.parseColor("#16A085"));

        if(position==2)
            btn_apply.setBackgroundColor(Color.parseColor("#E67E22"));

        if(position==3)
            btn_apply.setBackgroundColor(Color.parseColor("#3398DB"));

        if(position==4)
            btn_apply.setBackgroundColor(Color.parseColor("#34495E"));

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //Toast.makeText(mContext,""+position,Toast.LENGTH_SHORT).show();
                ((ThemeActivity)mContext).updateTheam(""+(position+1));
            }
        });

        container.addView(itemView);
        return itemView;
    }

    @Override
    public float getPageWidth(int position) {
          return 0.9f;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }



}
