package expression.sixdexp.com.expressionapp.utility;

import android.app.IntentService;
import android.content.Intent;

import expression.sixdexp.com.expressionapp.manager.VisitorCounter;

/**
 * Created by Praveen on 9/5/2016.
 */
public class VisitorCounterService extends IntentService {

    public VisitorCounterService(){
        super(VisitorCounterService.class.getName());
    }
    @Override
    protected void onHandleIntent(Intent intent) {

        new VisitorCounter().callVisitorCounter();
    }
}
