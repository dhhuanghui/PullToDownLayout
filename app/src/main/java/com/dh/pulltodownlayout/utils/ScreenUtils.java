package com.dh.pulltodownlayout.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import java.io.FileOutputStream;
import java.lang.reflect.Method;

/**
 * 获得屏幕相关的辅助类
 * 哈哈哈哈啊哈
 */
public class ScreenUtils {
    private ScreenUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

//    /**
//     * 获得状态栏的高度
//     *
//     * @param context
//     * @return
//     */
//    public static int getStatusHeight(Context context) {
//
//        int statusHeight = -1;
//        try {
//            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
//            Object object = clazz.newInstance();
//            int height = Integer.parseInt(clazz.getField("status_bar_height")
//                    .get(object).toString());
//            statusHeight = context.getResources().getDimensionPixelSize(height);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return statusHeight;
//    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

//    /**
//     * 返回屏幕可用高度
//     * 当显示了虚拟按键时，会自动减去虚拟按键高度
//     */
//    public static int getAvailableScreenHeight(Activity activity) {
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        return displayMetrics.heightPixels;
//    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static float getDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

//
//    /**
//     * 截取屏幕保存到sd卡
//     *
//     * @param v
//     * @return
//     */
//    public static Bitmap screen(Context context, View v) {
//        String fname = Cons.SCREEN_PATH;
//        View view = v.getRootView();
//        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache();
//        Bitmap bitmap = view.getDrawingCache();
//        if (bitmap != null) {
//            System.out.println("bitmap got !");
//            if (bitmap != null) {
//                try {
//                    FileOutputStream out = new FileOutputStream(fname);
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
//                    System.out.println("file " + fname + " output done.");
//                    return bitmap;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return null;
//                }
//            } else {
//                return null;
//            }
//        } else {
//            System.out.println("bitmap is NULL !");
//            return null;
//        }
//    }

    public static void saveBitmapToSD(Bitmap bitmap, String fname) {
        if (bitmap != null) {
            System.out.println("bitmap got !");
            if (bitmap != null) {
                try {
                    FileOutputStream out = new FileOutputStream(fname);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    System.out.println("file " + fname + " output done.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
            }
        } else {
            System.out.println("bitmap is NULL !");
        }
    }

    //图片缩放比例
    private static final float BITMAP_SCALE = 0.4f;

    public static int dpToPxInt(Context context, float dp) {
        return (int) (dpToPx(context, dp) + 0.5f);
    }


    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);

    }

    /**
     * 将dp转换成px
     *
     * @param dp
     * @return
     */
    public static float dpToPx(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int spToPx(Context context, int sp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics);
    }


    public static boolean isItemScrollCenter(Context context, int top, int bottom, int firstItem, float titleBarHeight) {
        int screenMiddle = 0;
        if (firstItem == 0) {
            screenMiddle = (int) (ScreenUtils.getScreenHeight(context) / 2 + titleBarHeight);//屏幕中间坐标
        } else {
            screenMiddle = ScreenUtils.getScreenHeight(context) / 2;//屏幕中间坐标
        }
        int barHeight = ScreenUtils.getScreenHeight(context) / 4 / 2;
        if ((screenMiddle - barHeight <= top && top <= screenMiddle + barHeight)
                || (screenMiddle - barHeight <= bottom && bottom <= screenMiddle + barHeight)
                || (top <= screenMiddle - barHeight && screenMiddle + barHeight <= bottom)) {
            return true;
        }
        return false;
    }

    /**
     * 获取虚拟按键的高度
     *
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && hasNavigationBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }

    /**
     * 是否存在虚拟按键
     *
     * @return
     */
    private static boolean hasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }

}
