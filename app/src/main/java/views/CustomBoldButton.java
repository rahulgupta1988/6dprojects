package views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;


public class CustomBoldButton extends Button {
	private Context context;
	public CustomBoldButton(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public CustomBoldButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	public CustomBoldButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}

	private void init() {
		String otfName = "fonts/Roboto-Regular.ttf";
		Typeface font = Typeface.createFromAsset(context.getAssets(),otfName);
		this.setTypeface(font);
	}
	
	
}
