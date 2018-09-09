package expression.sixdexp.com.expressionapp.utility;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import expression.sixdexp.com.expressionapp.R;

/**
 * Created by Praveen on 8/10/2016.
 */
public class AnchorePopupWindow implements View.OnClickListener{

    Context mContext;
    PopupWindow changeSortPopUp;
    View settingicon;
    KeyCheckedListener keyCheckedListener;

    @Override
    public void onClick(View v) {
        changeSortPopUp.dismiss();
        switch (v.getId()){
            case R.id.all:
                keyCheckedListener.checkedkey("all");
                break;

            case R.id.employee:
                keyCheckedListener.checkedkey("employee");
                break;

            case R.id.department:
                keyCheckedListener.checkedkey("department");
                break;

            case R.id.location:
                keyCheckedListener.checkedkey("location");
                break;

            case R.id.award:
                keyCheckedListener.checkedkey("award");
                break;
        }
    }

    public interface KeyCheckedListener{
        void checkedkey(String keyName);
    }

    public AnchorePopupWindow(Context mContext, View settingicon,KeyCheckedListener keyCheckedListener) {
        this.mContext = mContext;
        this.settingicon = settingicon;
        this.keyCheckedListener=keyCheckedListener;
    }



    public void settingMenu() {
        changeSortPopUp = new PopupWindow(mContext);
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.settingmenu, null);

        LinearLayout all=(LinearLayout)layout.findViewById(R.id.all);
        LinearLayout employee=(LinearLayout)layout.findViewById(R.id.employee);
        LinearLayout department=(LinearLayout)layout.findViewById(R.id.department);
        LinearLayout location=(LinearLayout)layout.findViewById(R.id.location);
        LinearLayout award=(LinearLayout)layout.findViewById(R.id.award);
        all.setOnClickListener(this);
        employee.setOnClickListener(this);
        department.setOnClickListener(this);
        location.setOnClickListener(this);
        award.setOnClickListener(this);


        Rect rc = new Rect();
        settingicon.getWindowVisibleDisplayFrame(rc);
        int[] xy = new int[2];
        settingicon.getLocationInWindow(xy);
        rc.offset(xy[0], xy[1]);
        // Creating the PopupWindow


        changeSortPopUp.setAnimationStyle(R.style.animationName);
        changeSortPopUp.setContentView(layout);
        changeSortPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeSortPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeSortPopUp.setFocusable(true);

        // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
        int OFFSET_X = 0;
        int OFFSET_Y = 15;

        // Clear the default translucent background
        changeSortPopUp.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        changeSortPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, rc.left + OFFSET_X, rc.top + OFFSET_Y);
    }

}
