package com.zlb.memo.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zlb.memo.App;
import com.zlb.memo.R;

import java.io.File;
import java.math.BigDecimal;

/**
 * Description:图片加载工具
 * author: zhangsan on 16/11/21 下午5:35.
 */

public class ImageUtil {
    public static void loadLocalImage(Context context, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .into(imageView);
    }

    public static void loadLocalImage(Context context, ImageView imageView, String path) {
        Glide.with(context)
                .load(new File(path))
                .into(imageView);
    }

    public static void loadLocalMap(Context context, ImageView imageView, String path) {
        Glide.with(context)
                .load(new File(path))
                .placeholder(R.drawable.default_img)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }

    public static void loadImage(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.default_img)
                .into(imageView);
    }

    public static void loadImage(Context context, ImageView imageView, String url, int DEFAULT, Boolean B) {
        Glide.with(context)
                .load(url)
                .placeholder(DEFAULT)
                .into(imageView);
    }

    /**
     * DiskCacheStrategy.NONE禁止缓存
     *
     * @param context
     * @param imageView
     * @param url
     */
    public static void loadGifImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url)
                .asGif().diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView);
    }

    public static void loadImage(Context context, ImageView imageView, String url, @DrawableRes int errorResId) {
        Glide.with(context)
                .load(url)
                .error(errorResId)
                .into(imageView);
    }

    public static void loadImage(Context context, String imgUrl, ImageView imageView, @DrawableRes int defaultBg) {
        Glide.with(context)
                .load(imgUrl)
                .placeholder(defaultBg)
                .error(defaultBg)
                .into(imageView);
    }

    public static void loadImage(Context context, ImageView imageView, String url, @DrawableRes int placeHolder, @DrawableRes int errorResId) {
        Glide.with(context)
                .load(url)
                .error(errorResId)
                .placeholder(placeHolder)
                .into(imageView);
    }

    /**
     * 清除图片所有缓存
     */
    public static void clearImageAllCache() {
        clearImageDiskCache();
    }

    /**
     * 清除图片磁盘缓存
     */
    public static void clearImageDiskCache() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(App.getContext()).clearDiskCache();

                    }
                }).start();
            } else {
                Glide.get(App.getContext()).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public static String getCacheSize() {
        try {
            return getFormatSize(getFolderSize(Glide.getPhotoCacheDir(App.getContext())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    public static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


}
