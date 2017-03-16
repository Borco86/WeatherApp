package observator;

import android.util.Log;

/**
 * Created by RENT on 2017-03-16.
 */

public class SomethingObserver implements  Observer {


    @Override
    public void notifyMe() {
        Log.d("result","coś się stało");
    }
}
