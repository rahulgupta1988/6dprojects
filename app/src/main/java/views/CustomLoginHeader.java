package views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomLoginHeader extends TextView {
    private Context context;
    public CustomLoginHeader(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CustomLoginHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CustomLoginHeader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init() {
        String otfName = "fonts/Museo_Slab_500.otf";
        Typeface font = Typeface.createFromAsset(context.getAssets(),otfName);
        this.setTypeface(font);
    }


}
