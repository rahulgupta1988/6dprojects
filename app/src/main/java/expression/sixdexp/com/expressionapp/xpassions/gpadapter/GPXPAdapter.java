package expression.sixdexp.com.expressionapp.xpassions.gpadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.aphidmobile.flip.FlipViewController;
import com.aphidmobile.utils.AphidLog;

import expression.sixdexp.com.expressionapp.R;
import expression.sixdexp.com.expressionapp.utility.Constant;
import expression.sixdexp.com.expressionapp.utility.DynamicImageView;
import expression.sixdexp.com.expressionapp.xpassions.gpfragment.GPXPWayFragment;
import expression.sixdexp.com.expressionapp.xpassions.gpfragment.WallFragment;

/**
 * Created by Praveen on 05-Jan-18.
 */

public class GPXPAdapter extends BaseAdapter {
    GPXPWayFragment gpxpWayFragment;

    Context context;

    public GPXPAdapter(Context context, FlipViewController flipView, GPXPWayFragment gpxpWayFragment) {
        this.gpxpWayFragment=gpxpWayFragment;
        this.context=context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout notification_ll;
        View layout = convertView;
        if (convertView == null) {
            new DynamicImageView(context);
            if (Constant.sc_height < 900) {
                layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.gpxpadapterview, parent, false);
            } else {
                layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.gpxpadapterview, parent, false);
            }

            AphidLog.d("created new view from adapter: %d", position);
        }

        return layout;
    }
}

