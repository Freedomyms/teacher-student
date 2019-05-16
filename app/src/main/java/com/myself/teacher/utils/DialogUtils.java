package com.myself.teacher.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;

/**
 * Created by admin on 2019/2/19.
 */

public class DialogUtils {
    public interface OnOkOrCancelClickListener {
        void clickLeftCancelButton();

        void clickRightOKButton();
    }

    public static void createDialogForPortrait(Context context, String content, final OnOkOrCancelClickListener onOkOrCancelClickListener) {
        final NormalDialog dialog = new NormalDialog(context);
        dialog.isTitleShow(false)//
                .bgColor(Color.parseColor("#383838"))//
                .cornerRadius(5)//
                .content(content)//
                .contentGravity(Gravity.CENTER)//
                .contentTextColor(Color.parseColor("#ffffff"))//
                .dividerColor(Color.parseColor("#222222"))//
                .btnTextSize(15.5f, 15.5f)//
                .btnTextColor(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"))//
                .btnPressColor(Color.parseColor("#2B2B2B"))//
                .widthScale(0.8f)//
                .autoDismiss(false)
                .show();
        dialog.setCanceledOnTouchOutside(false);//点击屏幕外是否小时dialog,true消失，false不消失
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        onOkOrCancelClickListener.clickLeftCancelButton();
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        onOkOrCancelClickListener.clickRightOKButton();
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
    }

    public static void createDialogForPortrait(Context context, String[] btnTexts, String content, final OnOkOrCancelClickListener onOkOrCancelClickListener) {
        //如果需要显示
        final NormalDialog dialog = new NormalDialog(context);
        dialog.isTitleShow(false)//
                .bgColor(Color.parseColor("#383838"))//
                .cornerRadius(5)//
                .content(content)//
                .contentGravity(Gravity.CENTER)//
                .contentTextColor(Color.parseColor("#ffffff"))//
                .dividerColor(Color.parseColor("#222222"))//
                .btnTextSize(15.5f, 15.5f)//
                .btnTextColor(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"))//
                .btnPressColor(Color.parseColor("#2B2B2B"))//
                .widthScale(0.80f)//
                .btnText(btnTexts)
                .autoDismiss(false)
                .show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnBtnClickL(
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        onOkOrCancelClickListener.clickLeftCancelButton();
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                },
                new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        onOkOrCancelClickListener.clickRightOKButton();
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
    }
}
