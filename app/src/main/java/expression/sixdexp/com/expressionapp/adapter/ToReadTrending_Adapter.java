package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import expression.sixdexp.com.expressionapp.R;

/**
 * Created by Praveen on 20-Nov-17.
 */

public class ToReadTrending_Adapter  extends RecyclerView.Adapter<ToReadTrending_Adapter.CommentHolder> {

    Context mContext;
    RecyclerView recyclerView;

    public ToReadTrending_Adapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }


    int tepm_w = 0;

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trendingbook_card, parent, false);

        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        tepm_w = width;
        int temp_width = (int) (width - (width / 1.5));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(temp_width, ViewGroup.LayoutParams.MATCH_PARENT);
        itemView.setLayoutParams(layoutParams);
        return new CommentHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final CommentHolder holder, final int position) {

    }


    @Override
    public int getItemCount() {
        return 10;
    }

    public class CommentHolder extends RecyclerView.ViewHolder {

        ImageView book_img;
        public CommentHolder(final View convertView) {
            super(convertView);
            book_img=(ImageView)convertView.findViewById(R.id.book_img);

        }

    }





 }

