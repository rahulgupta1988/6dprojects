
package views;

        import android.content.Context;
        import android.graphics.Typeface;
        import android.util.AttributeSet;
        import android.widget.TextView;

public class CustomTextListView extends TextView {
    private Context context;
    public CustomTextListView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CustomTextListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public CustomTextListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init() {
        String otfName = "fonts/CooperHewitt-ThinItalic.otf";
        Typeface font = Typeface.createFromAsset(context.getAssets(),otfName);
        this.setTypeface(font);
    }


}
