package com.dh.pulltodownlayout.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/7/16.
 */
public class UIViewUtil {
    /**
     * 设置View的显示和隐藏
     *
     * @param view
     * @param v
     */
    public static void setViewVisible(View view, int v) {
        if (view.getVisibility() != v) {
            view.setVisibility(v);
        }
    }

    public static void setViewSelect(View view, boolean b) {
        if (view.isSelected() != b) {
            view.setSelected(b);
        }
    }

    public static void setViewChecked(CheckBox view, boolean b) {
        if (view.isChecked() != b) {
            view.setChecked(b);
        }
    }

    public static void setViewEnable(View view, boolean b) {
        if (view.isEnabled() != b) {
            view.setEnabled(b);
        }
    }

    public static void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null && view.getVisibility() != View.GONE) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    public static void invisible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null && view.getVisibility() != View.INVISIBLE) {
                    view.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    public static void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null && view.getVisibility() != View.VISIBLE) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    public static void goneNoNetLayout(View noNetLayout) {
        if (noNetLayout != null && noNetLayout.getVisibility() == View.VISIBLE) {
            noNetLayout.setVisibility(View.GONE);
        }
    }

    public static void setTextViewDrawable(Context context, TextView textView, int resId, DrawablePosition drawablePosition) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        switch (drawablePosition) {
            case LEFT:
                textView.setCompoundDrawables(drawable, null, null, null);
                break;
            case TOP:
                textView.setCompoundDrawables(null, drawable, null, null);
                break;
            case RIGHT:
                textView.setCompoundDrawables(null, null, drawable, null);
                break;
            case BOTTOM:
                textView.setCompoundDrawables(null, null, null, drawable);
                break;
        }
    }

    public enum DrawablePosition {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM
    }


    public static void clearTextViewDrawable(Context context, TextView textView) {
        textView.setCompoundDrawables(null, null, null, null);
    }

    public static boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    public static boolean isGone(View view) {
        return view.getVisibility() == View.GONE;
    }

    public static boolean imageViewReused(ImageView imageView, String imageUri) {
        String path = (String) imageView.getTag();
        if (path != null && path.equals(imageUri)) {
            return true;
        }
        return false;
    }

}
