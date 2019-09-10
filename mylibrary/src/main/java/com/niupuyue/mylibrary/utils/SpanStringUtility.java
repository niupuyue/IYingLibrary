package com.niupuyue.mylibrary.utils;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.niupuyue.mylibrary.callbacks.ITextviewClickable;
import com.niupuyue.mylibrary.model.SpanModel;
import com.niupuyue.mylibrary.widgets.SpanClickable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Coder: niupuyue (牛谱乐)
 * Date: 2019-07-08
 * Time: 22:24
 * Desc: 字符串相关工具类
 * Version:
 */
public class SpanStringUtility {

    /**
     * 可同时设置字体颜色、是否下划线、是否加粗、文字大小和点击事件
     *
     * @param tv           TextView
     * @param text         要显示的全部文本
     * @param hotWordModel @see SpanModel
     * @param listener     点击监听
     */
    public static void setSpanText(TextView tv, CharSequence text, List<SpanModel> hotWordModel, ITextviewClickable listener) {
        if (tv == null || BaseUtility.isEmpty(text) || BaseUtility.isEmpty(hotWordModel)) {
            return;
        }
        try {
            SpannableStringBuilder textBuilder = new SpannableStringBuilder(text);

            List<Integer> lis;
            for (int i = 0; i < hotWordModel.size(); i++) {
                lis = getStrIndex(text + "", hotWordModel.get(i).text);
                for (int j = 0; j < lis.size(); j++) {
                    textBuilder.setSpan(new SpanClickable(listener, i, hotWordModel.get(i).isUnderline, hotWordModel.get(i).color, hotWordModel.get(i).isBold, hotWordModel.get(i).textSize),
                            lis.get(j),
                            lis.get(j) + hotWordModel.get(i).text.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            tv.setMovementMethod(LinkMovementMethod.getInstance());
            tv.setHighlightColor(Color.TRANSPARENT);
            tv.setText(textBuilder);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param tv
     * @param text            Text
     * @param arrHotWords     关键词
     * @param colorResourceID 关键词颜色
     * @param isBold          关键词是否加粗
     * @param textSize        关键字文字大小
     * @param listener        关键字点击回调
     */
    public static void setHotWordsText(TextView tv, CharSequence text, String[] arrHotWords, int colorResourceID, boolean isBold, float textSize, ITextviewClickable listener) {
        if (tv == null || BaseUtility.isEmpty(text) || BaseUtility.isEmpty(arrHotWords)) {
            return;
        }
        try {
            List<SpanModel> hotWordModels = new ArrayList<>();
            SpanModel spanModel;
            for (String arrHotWord : arrHotWords) {
                spanModel = new SpanModel();
                spanModel.text = arrHotWord;
                spanModel.isUnderline = false;
                spanModel.color = ContextCompat.getColor(LibraryConstants.getContext(), colorResourceID);
                spanModel.isBold = isBold;
                spanModel.textSize = textSize;
                hotWordModels.add(spanModel);
            }
            setSpanText(tv, text, hotWordModels, listener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param tv
     * @param text            Text
     * @param arrHotWords     关键词
     * @param colorResourceID 关键词颜色
     * @param isBold          关键词是否加粗
     * @param listener        关键字点击回调
     */
    public static void setHotWordsText(TextView tv, CharSequence text, String[] arrHotWords, int colorResourceID, boolean isBold, ITextviewClickable listener) {
        setHotWordsText(tv, text, arrHotWords, colorResourceID, isBold, -1, listener);
    }

    /**
     * @param tv
     * @param text            Text
     * @param arrHotWords     关键词
     * @param colorResourceID 关键词颜色
     * @param listener        关键字点击回调
     */
    public static void setHotWordsText(TextView tv, CharSequence text, String[] arrHotWords, int colorResourceID, ITextviewClickable listener) {
        setHotWordsText(tv, text, arrHotWords, colorResourceID, false, listener);
    }

    /**
     * @param tv
     * @param text            Text
     * @param hotWords        关键词
     * @param colorResourceID 关键词颜色
     * @param listener        关键字点击回调
     */
    public static void setHotWordsText(TextView tv, CharSequence text, String hotWords, int colorResourceID, ITextviewClickable listener) {
        setHotWordsText(tv, text, new String[]{hotWords}, colorResourceID, listener);
    }

    /**
     * @param tv
     * @param text            Text
     * @param hotWords        关键词
     * @param colorResourceID 关键词颜色
     */
    public static void setHotWordsText(TextView tv, CharSequence text, String hotWords, int colorResourceID) {
        setHotWordsText(tv, text, hotWords, colorResourceID, null);
    }

    /**
     * @param tv
     * @param text            Text
     * @param arrHotWords     关键词
     * @param colorResourceID 关键词颜色
     */
    public static void setHotWordsText(TextView tv, CharSequence text, String[] arrHotWords, int colorResourceID) {
        setHotWordsText(tv, text, arrHotWords, colorResourceID, null);
    }

    /**
     * 获取热词在字符串中位置的集合(忽略大小写)
     *
     * @param source
     * @param hotWord
     * @return
     */
    public static List<Integer> getStrIndex(String source, String hotWord) {
        List<Integer> lis = new ArrayList<>();

        // 忽略大小写
        final String tempLowerCaseStr = source.toLowerCase();
        final String tempLowerHotWord = hotWord.toLowerCase();

        int indexOf = tempLowerCaseStr.indexOf(tempLowerHotWord);
        if (indexOf != -1) {
            lis.add(indexOf);
        }

        while (indexOf != -1) {

            indexOf = tempLowerCaseStr.indexOf(tempLowerHotWord, indexOf + 1);

            if (indexOf != -1) {
                lis.add(indexOf);
            }
        }

        return lis;
    }

    /**
     * 将对象转换成String类型
     *
     * @param obj
     * @return
     */
    public static String serializeToString(Object obj) {
        String serStr;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            serStr = byteArrayOutputStream.toString("ISO-8859-1");
            serStr = URLEncoder.encode(serStr, "UTF-8");
            objectOutputStream.close();
            byteArrayOutputStream.close();
        } catch (Exception var4) {
            var4.printStackTrace();
            serStr = "";
        }

        return serStr;
    }

    /**
     * String 字符串转换成Object对象
     *
     * @param str
     * @return
     */
    public static Object deSerializationToObject(String str) {
        Object obj;
        try {
            String redStr = URLDecoder.decode(str, "UTF-8");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes(StandardCharsets.ISO_8859_1));
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            obj = objectInputStream.readObject();
            objectInputStream.close();
            byteArrayInputStream.close();
        } catch (Exception var5) {
            obj = null;
        }

        return obj;
    }

    /**
     * 将String集合设置成固定字符串格式
     */
    public static String stringListFormatToString(List<String> res, String tag) {
        if (BaseUtility.isEmpty(res) || BaseUtility.isEmpty(tag)) return null;
        String result = "";
        try {
            for (int i = 0; i < BaseUtility.size(res); i++) {
                if (i != 0) {
                    result += tag;
                }
                result += res.get(i);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String stringListFormatToString(String[] res, String tag) {
        if (BaseUtility.isEmpty(res) || BaseUtility.isEmpty(tag)) return null;
        return stringListFormatToString(Arrays.asList(res), tag);
    }

}
