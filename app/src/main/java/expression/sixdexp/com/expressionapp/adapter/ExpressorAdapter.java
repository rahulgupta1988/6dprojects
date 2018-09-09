package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import java.util.List;

import db.Expossor;
import expression.sixdexp.com.expressionapp.R;

/**
 * Created by Praveen on 7/20/2016.
 */
public class ExpressorAdapter extends BaseAdapter {

    Context mContext;
    List<Expossor> selccto;
    private ExpressorListener listener;

    public interface ExpressorListener{
        public void selExpressor(String expressorstr);
    }

    public ExpressorAdapter(Context context, List<Expossor> expressorlist, ExpressorListener listener){
        mContext=context;
        this.selccto=expressorlist;
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
        final int pos=position;
        HolderView holder;
        if(convertView==null) {
            holder=new HolderView();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.xpressoritem, parent, false);
            holder.selexpreesor=(CheckBox)convertView.findViewById(R.id.selexpreesor);
            convertView.setTag(holder);
        }

        else{
            holder=(HolderView)convertView.getTag();
        }

        Expossor expossor=selccto.get(position);

        String expossor_name=expossor.getXpressor_text();
        holder.selexpreesor.setText(expossor_name);

        holder.selexpreesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.selExpressor(selccto.get(pos).getXpressor_text());
            }
        });



        return  convertView;
    }

    public class HolderView{

        CheckBox selexpreesor;


    }
}
