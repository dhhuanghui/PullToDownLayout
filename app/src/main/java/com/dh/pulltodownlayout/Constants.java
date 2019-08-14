package com.dh.pulltodownlayout;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Constants {
    @IntDef({FragmentType.ALMANAC, FragmentType.LECTURE, FragmentType.CS, FragmentType.MY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FragmentType {
        int ALMANAC = 0;
        int LECTURE = 1;
        int CS = 2;
        int MY = 3;
//        int ZX = 3;
    }
}
