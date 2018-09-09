package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import expression.sixdexp.com.expressionapp.R;

/**
 * Created by Praveen on 13-Oct-17.
 */

public class ShareBulletinAdapter  extends BaseAdapter {

    Context mContext;
    List<String> selccto;
    private ShareBulletinAdapter.ToAndCCChangeListener listener;

    public interface ToAndCCChangeListener{
        public void changeCCAndTO(int pos);
    }

    public ShareBulletinAdapter(Context context, List<String> selccto, ShareBulletinAdapter.ToAndCCChangeListener listener){
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
        holder.name.setText(selccto.get(position));

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
