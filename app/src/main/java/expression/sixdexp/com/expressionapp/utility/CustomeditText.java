package expression.sixdexp.com.expressionapp.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

/**
 * Created by Praveen on 8/10/2016.
 */
public class CustomeditText extends EditText {


    public CustomeditText(Context context) {
        super(context);
    }

    public CustomeditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomeditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_ENTER)
        {
            // Just ignore the [Enter] key
            return false;
        }
        // Handle all other keys in the default way
        return super.onKeyDown(keyCode, event);
    }
}
