package io.github.qzcsfchh.android.ioc.impl1;

import android.util.Log;

import com.google.auto.service.AutoService;

import io.github.qzcsfchh.android.ioc.IService;

/**
 * @author huanghao
 * @date 2022-07-04 19:13
 */
@AutoService(IService.class)
public class ServiceImpl2 implements IService {
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
