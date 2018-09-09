package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.manager.WhatsTrendingBean;
import expression.sixdexp.com.expressionapp.manager.WhatsTrendingManager;

/**
 * Created by Praveen on 9/13/2016.
 */
public class WhatsTrendingAdapter extends RecyclerView.Adapter<WhatsTrendingAdapter.TrendingViewHolder> {

    Context mContext;
    Typeface typeface;

    public WhatsTrendingAdapter(Context context) {
        mContext = context;
        typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/OpenSans-Regular.ttf");
    }

    @Override
    public WhatsTrendingAdapter.TrendingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.whatstrendingitem, parent, false);
        return new TrendingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrendingViewHolder holder, int position) {
        final int i=position;
        WhatsTrendingBean whatsTrendingBean=WhatsTrendingManager.whatsTrendingBeans.get(position);
        String title=whatsTrendingBean.getTitle();
        holder.titletxt.setText(title);
        holder.parentlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link=WhatsTrendingManager.whatsTrendingBeans.get(i).getLink();
                Intent webIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(link));
                webIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                mContext.startActivity(webIntent);

            }
        });

        if(position==WhatsTrendingManager.whatsTrendingBeans.size()-1)
            holder.border.setVisibility(View.GONE);
        else
            holder.border.setVisibility(View.VISIBLE);


    }


    @Override
    public int getItemCount() {
        if (WhatsTrendingManager.whatsTrendingBeans != null && WhatsTrendingManager.whatsTrendingBeans.size() > 0)
            return WhatsTrendingManager.whatsTrendingBeans.size();
        return 0;
    }

    public class TrendingViewHolder extends RecyclerView.ViewHolder {

        TextView titletxt;
        LinearLayout parentlay,border;

        public TrendingViewHolder(View itemView) {
            super(itemView);
            titletxt = (TextView) itemView.findViewById(R.id.titletxt);
            parentlay=(LinearLayout)itemView.findViewById(R.id.parentlay);
            border=(LinearLayout)itemView.findViewById(R.id.border);
            titletxt.setTypeface(typeface);
        }
    }
}
