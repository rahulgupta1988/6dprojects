package expression.sixdexp.com.expressionapp.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import expression.sixdexp.com.expressionapp.R;

/**
 * Created by Praveen on 09-Feb-17.
 */

public class EmojiAdapter extends BaseAdapter {

    private Context mContext;

    // Keep all Images in array
    public Integer[] mThumbIds = {
             R.drawable.a0,  R.drawable.a1,    R.drawable.a2,R.drawable.a3,  R.drawable.a4,  R.drawable.a5,
             R.drawable.a6,  R.drawable.a7,  R.drawable.a8,R.drawable.a9,  R.drawable.a10, R.drawable.a11,
             R.drawable.a12, R.drawable.a13, R.drawable.a14, R.drawable.a15, R.drawable.a16, R.drawable.a17,
             R.drawable.a18, R.drawable.a19, R.drawable.a20, R.drawable.a21, R.drawable.a22, R.drawable.a23,
             R.drawable.a24, R.drawable.a25, R.drawable.a26, R.drawable.a27, R.drawable.a28,  R.drawable.a29,
             R.drawable.a30, R.drawable.a31, R.drawable.a32, R.drawable.a33, R.drawable.a34,  R.drawable.a35,
             R.drawable.a36, R.drawable.a37, R.drawable.a38,  R.drawable.a39, R.drawable.a40,  R.drawable.a41,
             R.drawable.a42, R.drawable.a43, R.drawable.a44,  R.drawable.a45,  R.drawable.a46, R.drawable.a47,
             R.drawable.a48, R.drawable.a49, R.drawable.a50, R.drawable.a51, R.drawable.a52, R.drawable.a53,
             R.drawable.a54, R.drawable.a55, R.drawable.a56, R.drawable.a57, R.drawable.a58, R.drawable.a59,
             R.drawable.a60, R.drawable.a61, R.drawable.a62, R.drawable.a63, R.drawable.a64, R.drawable.a65,
             R.drawable.a66, R.drawable.a67, R.drawable.a68, R.drawable.a69, R.drawable.a70, R.drawable.a71,
             R.drawable.a72, R.drawable.a73, R.drawable.a74, R.drawable.a75, R.drawable.a76, R.drawable.a77,
             R.drawable.a78, R.drawable.a79, R.drawable.a80, R.drawable.a81, R.drawable.a82, R.drawable.a83,
             R.drawable.a84, R.drawable.a85, R.drawable.a86, R.drawable.a87, R.drawable.a88, R.drawable.a89,
             R.drawable.a90, R.drawable.a91, R.drawable.a92, R.drawable.a93, R.drawable.a94, R.drawable.a95,
             R.drawable.a96, R.drawable.a97, R.drawable.a98, R.drawable.a99, R.drawable.a100,
             R.drawable.a101, R.drawable.a102, R.drawable.a103, R.drawable.a104, R.drawable.a105, R.drawable.a106,
             R.drawable.a107, R.drawable.a108, R.drawable.a109, R.drawable.a110, R.drawable.a111, R.drawable.a112,
             R.drawable.a113, R.drawable.a114, R.drawable.a115, R.drawable.a116, R.drawable.a117, R.drawable.a118,
             R.drawable.a119, R.drawable.a120, R.drawable.a121, R.drawable.a122, R.drawable.a123, R.drawable.a124,
             R.drawable.a125, R.drawable.a126, R.drawable.a127, R.drawable.a128, R.drawable.a129, R.drawable.a130,
             R.drawable.a131, R.drawable.a132, R.drawable.a133, R.drawable.a134, R.drawable.a135, R.drawable.a136,
             R.drawable.a137, R.drawable.a138, R.drawable.a139, R.drawable.a140, R.drawable.a141, R.drawable.a142,
             R.drawable.a143, R.drawable.a144, R.drawable.a145, R.drawable.a146, R.drawable.a147, R.drawable.a148,
             R.drawable.a149, R.drawable.a150, R.drawable.a151, R.drawable.a152, R.drawable.a153, R.drawable.a154,
            R.drawable.a155, R.drawable.a156, R.drawable.a157, R.drawable.a158, R.drawable.a159, R.drawable.a160,
            R.drawable.a161, R.drawable.a162, R.drawable.a163, R.drawable.a164, R.drawable.a165, R.drawable.a166,
            R.drawable.a167, R.drawable.a168, R.drawable.a169, R.drawable.a170, R.drawable.a171, R.drawable.a172,
            R.drawable.a173, R.drawable.a174, R.drawable.a175, R.drawable.a176, R.drawable.a177, R.drawable.a178,
            R.drawable.a179, R.drawable.a180, R.drawable.a181, R.drawable.a182, R.drawable.a183, R.drawable.a184,
            R.drawable.a185, R.drawable.a186, R.drawable.a187, R.drawable.a188, R.drawable.a189, R.drawable.a190,
            R.drawable.a191, R.drawable.a192, R.drawable.a193, R.drawable.a194, R.drawable.a195, R.drawable.a196,
            R.drawable.a197, R.drawable.a198, R.drawable.a199, R.drawable.a200, R.drawable.a201, R.drawable.a202,
            R.drawable.a203, R.drawable.a204, R.drawable.a205, R.drawable.a206,
    };

    // Constructor
    public EmojiAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if(getdeviceInch()>4.5 && getdeviceInch()<=5.0){
            imageView.setLayoutParams(new GridView.LayoutParams(70, 70));
        }

        else if(getdeviceInch()>5.0){
            imageView.setLayoutParams(new GridView.LayoutParams(80,80));
        }
        else{
            imageView.setLayoutParams(new GridView.LayoutParams(50, 50));
        }

        return imageView;
    }


    public double getdeviceInch(){
        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        double wi=(double)width/(double)displaymetrics.xdpi;
        double hi=(double)height/(double)displaymetrics.ydpi;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        double screenInches = Math.sqrt(x+y);
        //Toast.makeText(mContext,""+screenInches,Toast.LENGTH_SHORT).show();
        return screenInches;
    }
}
