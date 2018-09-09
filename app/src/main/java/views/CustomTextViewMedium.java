package views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomTextViewMedium extends TextView {
	private Context context;
	public CustomTextViewMedium(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public CustomTextViewMedium(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public CustomTextViewMedium(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}

	private void init() {
		String otfName = "fonts/Roboto-Medium.ttf";
		Typeface font = Typeface.createFromAsset(context.getAssets(),otfName);
		this.setTypeface(font);
	}
	
	
}
