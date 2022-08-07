package io.github.qzcsfchh.android.ioc.impl2;

import android.util.Log;


import io.github.qzcsfchh.android.ioc.AbsService;

/**
 * @author huanghao
 * @date 2022-08-04 15:41
 */
public class AbsServiceImpl2 extends AbsService {
    private static final String TAG = "AbsServiceImpl2";
    @Override
    protected void doJob() {
        Log.d(TAG, "doJob: ");
    }
}
