package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import db.Xpressway;
import expression.sixdexp.com.expressionapp.PostDetailsAcitivity;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.manager.XpresswayManager;

/**
 * Created by Praveen on 7/1/2016.
 */
public class ExpressionWayList extends BaseAdapter {

    Context mcoContext;
    List<Xpressway> xpresswayList;


    public ExpressionWayList(Context context) {
        mcoContext = context;
        xpresswayList=new XpresswayManager(context).getXpresswayDataList();
        Log.i("xpresswayList size",""+xpresswayList.size());
    }

    @Override
    public int getCount()
    {
        if(xpresswayList!=null && xpresswayList.size()>0) return xpresswayList.size();
        else return 0;
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
        final  int i=position;
        final HolderView holder;
        if (convertView == null) {
            holder = new HolderView();
            LayoutInflater inflater = (LayoutInflater) mcoContext.getSystemService(mcoContext.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.xwitem, null);
            holder.nominator_name=(TextView)convertView.findViewById(R.id.nominator_name);
            holder.subject=(TextView)convertView.findViewById(R.id.subject);
            holder.recognise_date=(TextView)convertView.findViewById(R.id.recognise_date);

            convertView.setTag(holder);
        } else {
            holder = (HolderView) convertView.getTag();
        }



        Xpressway xpressway=xpresswayList.get(position);

        String nominator_name=xpressway.getNominator_name();
        String subject=xpressway.getSubject();
        String recognise_date=xpressway.getRecognise_date();
        String image_url=xpressway.getImage_url();

        if(nominator_name!=null && !nominator_name.equalsIgnoreCase(""))
            holder.nominator_name.setText(nominator_name);
        if(subject!=null && !subject.equalsIgnoreCase(""))
            holder.subject.setText(subject);
        if(recognise_date!=null && !recognise_date.equalsIgnoreCase(""))
            holder.recognise_date.setText(recognise_date);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postid=xpresswayList.get(i).getRecognition_id();
                Intent detailsActitivty=new Intent(mcoContext, PostDetailsAcitivity.class);
                detailsActitivty.putExtra("postId",postid);
                mcoContext.startActivity(detailsActitivty);


            }
        });

        return convertView;
    }


    public class HolderView {

        TextView nominator_name,subject,recognise_date;

    }

}
