package io.github.qzcsfchh.android.ioc.impl1;

import android.util.Log;

import com.google.auto.service.AutoService;

import io.github.qzcsfchh.android.ioc.AbsService;

/**
 * @author huanghao
 * @date 2022-08-04 15:41
 */
@AutoService(AbsService.class)
public class AbsServiceImpl1 extends AbsService {
    private static final String TAG = "AbsServiceImpl1";
    @Override
    protected void doJob() {
        Log.d(TAG, "doJob: ");
    }
}
