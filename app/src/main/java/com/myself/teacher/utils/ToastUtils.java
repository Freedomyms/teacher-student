package com.myself.teacher.utils;

import android.widget.Toast;

import com.myself.teacher.base.MyApplication;


/**
 * Created by admin on 2018/10/25.
 */

public class ToastUtils {
    public static Toast toast;
    /**
     * 吐丝的方法，可以避免重复吐丝。当你点击多次按钮的时候，吐丝只出现一次。
     * @paramcontext 上下文对象
     * @paramstring    吐丝的内容
     */
    public static void showShort(String toastString) {
        // TODO Auto-generated method stub
        if(toast == null){
            // 如果Toast对象为空了，那么需要创建一个新的Toast对象
            toast = Toast.makeText(MyApplication.getContextObject(), toastString, Toast.LENGTH_SHORT);
        }else {
            // 如果toast对象还存在，那么就不再创建新的Toast对象
            toast.setText(toastString);
        }

        // 最后调用show方法吐丝
        toast.show();
    }

    public static void showLong(String toastString) {
        // TODO Auto-generated method stub
        if(toast == null){
            // 如果Toast对象为空了，那么需要创建一个新的Toast对象
            toast = Toast.makeText(MyApplication.getContextObject(), toastString, Toast.LENGTH_LONG);
        }else {
            // 如果toast对象还存在，那么就不再创建新的Toast对象
            toast.setText(toastString);
        }

        // 最后调用show方法吐丝
        toast.show();
    }

}
