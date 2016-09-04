package com.nannong.mall.tool;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

import java.io.File;

import cn.nj.www.my_module.tools.FileSystemManager;

/**
 * Created by huqing on 2016/8/22.
 */
public class GlideConfigModule implements GlideModule
{

    @Override
    public void applyOptions(final Context context, GlideBuilder builder) {
        //设置缓存位置
        builder.setDiskCache(new DiskCache.Factory() {
            @Override
            public DiskCache build() {
                File cacheLocation = new File(FileSystemManager.getTemporaryPath(context));
                cacheLocation.mkdirs();
                return DiskLruCacheWrapper.get(cacheLocation, 50 * 1024 * 1024);
            }
        });
        //指定内存缓存大小
        builder.setMemoryCache(new LruResourceCache(4 * 1024 * 1024));
        //全部的内存缓存用来作为图片缓存
        builder.setBitmapPool(new LruBitmapPool(4 * 1024 * 1024));
        builder.setDecodeFormat(DecodeFormat.PREFER_RGB_565);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }
}