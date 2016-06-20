package com.whunf.netexam;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2016/6/20.
 */
public class BitmapCache implements ImageLoader.ImageCache {


    LruCache<String, Bitmap> mCache;

    public BitmapCache() {
        int maxCacheSize = 1024 * 1024 * 8;//8mb
        mCache = new LruCache<String, Bitmap>(maxCacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //需要重写该方法，默认是1，所以需要根据业务的需要重写,LruCache类内部会调用此方法用于计算缓存数量是否达到了maxCacheSize
                return value.getRowBytes() * value.getHeight();
            }
        };
    }


    @Override
    public Bitmap getBitmap(String url) {//从缓存中去
        return mCache.get(url);
    }

    @Override//缓存在内存中
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }
}
