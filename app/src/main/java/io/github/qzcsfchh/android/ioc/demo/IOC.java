package io.github.qzcsfchh.android.ioc.demo;

import android.content.pm.PackageManager;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.hunter.library.debug.HunterDebugClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

import io.github.qzcsfchh.android.ioc.dex.ClassUtils;

/**
 * @author huanghao
 * @date 2022-07-04 19:58
 */
@HunterDebugClass
public class IOC {
    private static final String TAG = "IOC";
    private static final Map<Class<?>, ServiceLoader<?>> sLoaders = new ConcurrentHashMap<>();

    public static <T> List<T> getServiceBySPI(Class<T> clz){
        List<T> services = new ArrayList<>();
        ServiceLoader<T> loader = (ServiceLoader<T>) sLoaders.get(clz);
        if (loader == null) {
            loader = ServiceLoader.load(clz, IOC.class.getClassLoader());
            if (loader == null) {
                throw new NullPointerException();
            }
            sLoaders.put(clz, loader);
        } else {
            loader.reload();
        }

        for (T service : loader) {
            Log.d(TAG, "getServiceBySPI: ==> " + service.getClass().getName());
            services.add(service);
        }
        return Collections.unmodifiableList(services);
    }

    /**
     * dex有个问题是暂不支持apply changes， instant run支持也不是很好，会导致新增服务无法立即被检测到
     * @return
     */
    public static <T> List<T> getServiceByDex(Class<T> c){
        List<T> services = new ArrayList<>();
        try {
            for (String s : ClassUtils.getFileNameByPackageName(Utils.getApp(), c.getPackage().getName())) {
                if (s.contains("$")) continue;
                try {
                    Class<?> clz = Class.forName(s);
                    if (clz== c) continue;
                    if (!c.isAssignableFrom(clz)) continue;
                    Log.d(TAG, "getServiceByDex: ==> " + clz.getName());
                    services.add((T) clz.newInstance());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Collections.unmodifiableList(services);
    }
}
