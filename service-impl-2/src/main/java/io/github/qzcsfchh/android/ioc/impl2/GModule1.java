package io.github.qzcsfchh.android.ioc.impl2;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.LibraryGlideModule;

/**
 * @author huanghao
 * @date 2022-08-05 10:21
 */
@GlideModule
public class GModule1 extends LibraryGlideModule {
    private static final String TAG = "GModule1";

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        Log.d(TAG, "registerComponents: ");
    }
}
