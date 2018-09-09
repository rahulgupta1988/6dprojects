package expression.sixdexp.com.expressionapp.utility;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import expression.sixdexp.com.expressionapp.R;

/**
 * Created by Praveen on 9/6/2016.
 */
public class ProgressLoaderUtilities {

    public static ProgressDialog getProgressDialog(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        Window window = progressDialog.getWindow();
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        progressDialog.show();
        progressDialog.setContentView(R.layout.customprogressbar);

        return progressDialog;
    }
}
