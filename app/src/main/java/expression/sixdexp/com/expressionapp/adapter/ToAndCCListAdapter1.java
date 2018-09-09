package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import db.AllUsers;
import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.RecogniseActivity;

/**
 * Created by Praveen on 7/13/2016.
 */
public class ToAndCCListAdapter1 extends BaseAdapter {

    Context mContext;
    List<AllUsers> selccto;
    private ToAndCCChangeListener listener;

    public interface ToAndCCChangeListener{
        public void changeCCAndTO(int pos);
    }

    public ToAndCCListAdapter1(Context context, List<AllUsers> selccto, ToAndCCChangeListener listener){
        mContext=context;
        this.selccto=selccto;
        this.listener=listener;
    }


    @Override
    public int getCount() {
        return selccto.size();
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
        if(convertView==null) {
            holder=new HolderView();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.seltoccitem, parent, false);
            holder.name=(TextView)convertView.findViewById(R.id.name);
            holder.cancel=(ImageView)convertView.findViewById(R.id.cancel);
            convertView.setTag(holder);
        }

        else{
            holder=(HolderView)convertView.getTag();
        }


        final int pos=position;
        if(RecogniseActivity.check==1)
            holder.name.setText(selccto.get(position).getEmail());
        else
        holder.name.setText(selccto.get(position).getName());

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.changeCCAndTO(pos);
            }
        });

        return  convertView;
    }

    public class HolderView{

        TextView name;
        ImageView cancel;

    }
}
