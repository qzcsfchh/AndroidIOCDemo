package io.github.qzcsfchh.android.ioc.impl2;

import android.util.Log;


import io.github.qzcsfchh.android.ioc.IService;

/**
 * @author huanghao
 * @date 2022-07-04 19:13
 */
public class ServiceImpl3 implements IService {
    private final String TAG = getClass().getSimpleName();

    @Override
    public void sayHi() {
        Log.d(TAG, "sayHi: " + getClass().getSimpleName());
    }

    @Override
    public String doJob() {
        return getClass().getName();
    }
}
