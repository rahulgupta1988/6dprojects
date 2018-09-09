package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import expression.sixdexp.com.expressionapp.R;

/**
 * Created by Praveen on 7/13/2016.
 */
public class ToAndCCListAdapter extends RecyclerView.Adapter<ToAndCCListAdapter.ToAndCCHolder> {

    Context mContext;
    List<String> selccto;
    private ToAndCCChangeListener listener;

    public interface ToAndCCChangeListener{
        public void changeCCAndTO(int pos);
    }

    public ToAndCCListAdapter(Context context,List<String> selccto,ToAndCCChangeListener listener){
        mContext=context;
        this.selccto=selccto;
        this.listener=listener;
    }

    @Override
    public ToAndCCListAdapter.ToAndCCHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.seltoccitem, parent, false);
        return new ToAndCCHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ToAndCCListAdapter.ToAndCCHolder holder, int position) {

        final int pos=position;
        holder.name.setText(selccto.get(position));

         holder.cancel.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 listener.changeCCAndTO(pos);
             }
         });

    }

    @Override
    public int getItemCount() {
        return selccto.size();
    }

    public class ToAndCCHolder extends RecyclerView.ViewHolder{

        TextView name;
        ImageView cancel;
        public ToAndCCHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            cancel=(ImageView)itemView.findViewById(R.id.cancel);

        }
    }
}
