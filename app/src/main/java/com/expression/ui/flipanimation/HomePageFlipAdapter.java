package com.expression.ui.flipanimation;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aphidmobile.utils.AphidLog;
import com.aphidmobile.utils.IO;

import java.util.ArrayList;
import java.util.List;

import db.GetMorePost;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.manager.GetMorePostManager;


public class HomePageFlipAdapter extends BaseAdapter
{
  Context mcontext;
  private LayoutInflater inflater;
  private int repeatCount = 1;
  private List<Travels.Data> travelData;
  List<GetMorePost> getMorePosts;

  public HomePageFlipAdapter(Context context)
  {
    this.mcontext=context;
    inflater = LayoutInflater.from(context);
    travelData = new ArrayList<Travels.Data>(Travels.IMG_DESCRIPTIONS);

    getMorePosts = new GetMorePostManager(context).getMorePosts();
  //  Toast.makeText(context, "AdapterPostSize=" + getMorePosts.size(), Toast.LENGTH_SHORT).show();
  }

  @Override
  public int getCount() {
    return travelData.size() * repeatCount;
  }

  public int getRepeatCount() {
    return repeatCount;
  }

  public void setRepeatCount(int repeatCount) {
    this.repeatCount = repeatCount;
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
  public View getView(int position, View convertView, ViewGroup parent)
  {
      final ImageView photo,notification_img,share_img,email_img;
      TextView description;
      View layout = convertView;
      if (convertView == null)
      {
      /* layout = inflater.inflate(R.layout.homepageadapterflip, null);  //homepageflip*/
          layout = inflater.inflate(R.layout.homepageflipadapter, null);  //homepageflip
       AphidLog.d("created new view from adapter: %d", position);
      }
      final Travels.Data data = travelData.get(position % travelData.size());

      description=(TextView)layout.findViewById(R.id.description);
      notification_img=(ImageView)layout.findViewById(R.id.notification_img);
      photo=(ImageView)layout.findViewById(R.id.photo);

      photo.setImageBitmap(IO.readBitmap(inflater.getContext().getAssets(), data.imageFilename));
      description.setText(Html.fromHtml(data.description));
      share_img=(ImageView)layout.findViewById(R.id.share_img);
      email_img=(ImageView)layout.findViewById(R.id.email_img);
      share_img.setVisibility(View.GONE);
      email_img.setVisibility(View.GONE);

      notification_img.setBackgroundResource(R.drawable.more_disable);

      notification_img.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v)
          {

              if (share_img.getVisibility() == View.VISIBLE)
              {
                  // first visible
                  share_img.setVisibility(View.GONE);
                  email_img.setVisibility(View.GONE);
                  notification_img.setBackgroundResource(R.drawable.more_disable);
              }
              else
              {
                  // first gone
                  notification_img.setBackgroundResource(R.drawable.more_enable);
                  share_img.setVisibility(View.VISIBLE);
                  email_img.setVisibility(View.VISIBLE);

              }
          }
      });


    return layout;
  }

  public void removeData(int index) {
    if (travelData.size() > 1) {
      travelData.remove(index);
    }
  }
}
