package io.github.qzcsfchh.android.ioc.demo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @author huanghao
 * @date 2022-08-05 10:23
 */
@GlideModule
public class GApp extends AppGlideModule {
    private static final String TAG = "GApp";
    @Override
    public boolean isManifestParsingEnabled() {
        return super.isManifestParsingEnabled();
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        Log.d(TAG, "applyOptions: ");
    }
}
