package command;

import android.util.Log;

/**
 * Created by RENT on 2017-03-16.
 */

public class DisplayNameFirstCommad implements  Command {
    @Override
    public void execute() {
        Log.d("result","Tomek");
    }
}
