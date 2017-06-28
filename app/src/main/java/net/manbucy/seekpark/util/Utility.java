package net.manbucy.seekpark.util;

import android.support.annotation.Nullable;

/**
 * 常用的工具集合类
 * Created by yang on 2017/6/23.
 */

public class Utility {

    /**
     * 空指针异常抛出
     * 检查是否为空 为空则抛出异常
     * @param reference 检查的引用
     * @return 该引用
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    /**
     * 空指针异常抛出
     * 检查是否为空 为空则抛出异常
     * @param reference 检测对象
     * @param errorMessage 错误信息提示
     * @return 该引用
     */
    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if(reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        } else {
            return reference;
        }
    }
}
