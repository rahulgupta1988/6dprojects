package expression.sixdexp.com.expressionapp.utility;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import org.apache.commons.lang.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Praveen on 8/2/2016.
 */
public class TextChangeListe implements TextWatcher {

    EditText etxt;
    public  TextChangeListe(EditText etxt){
        this.etxt=etxt;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        try {

            String toServerUnicodeEncoded = StringEscapeUtils.escapeJava(s.toString());
            Log.i("share txt1010",""+toServerUnicodeEncoded);

            String strMsg1=  StringEscapeUtils.unescapeJava(toServerUnicodeEncoded);
            Log.i("share txt2020",""+strMsg1);
            if(s.toString().equalsIgnoreCase(" "))
            etxt.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void afterTextChanged(Editable s) {

    }
}
