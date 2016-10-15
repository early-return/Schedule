package info.zhiqing.schedule.util;

import android.os.HandlerThread;

/**
 * Created by zhiqing on 16-10-15.
 */

public class Fetchr extends HandlerThread{
    private static final String TAG = "Fetchr";

    public Fetchr() {
        super(TAG);
    }
}
